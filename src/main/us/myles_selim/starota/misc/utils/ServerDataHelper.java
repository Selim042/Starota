package us.myles_selim.starota.misc.utils;

import java.io.File;

import discord4j.core.object.entity.Guild;
import us.myles_selim.ebs.DataType;
import us.myles_selim.ebs.EBList;
import us.myles_selim.ebs.EBStorage;
import us.myles_selim.ebs.IOHelper;
import us.myles_selim.ebs.callbacks.ClassNotFoundCallback;
import us.myles_selim.ebs.callbacks.OnWriteCallback;
import us.myles_selim.ebs.data_types.DataTypeEBStorage;

public class ServerDataHelper {

	public static EBList<EBStorage> getEBSsFromFolder(Guild server, File folder) {
		if (!folder.exists() || !folder.isDirectory())
			return new EBList<>(new DataTypeEBStorage());
		EBList<EBStorage> ebss = new EBList<>(new DataTypeEBStorage());
		File sFolder = new File(folder, server.getId().asString());
		if (!sFolder.exists())
			return new EBList<>(new DataTypeEBStorage());
		File[] nestFiles = sFolder.listFiles(IOHelper.EBS_FILE_FILTER);
		if (nestFiles == null || nestFiles.length == 0)
			return new EBList<>(new DataTypeEBStorage());
		for (File nFile : nestFiles)
			ebss.addWrapped(
					IOHelper.readEBStorage(nFile).setOnWriteCallback(new FileWriteCallback(nFile)));
		return ebss;
	}

	public static EBStorage getEBSFromFolder(Guild server, File folder) {
		File ebsFile = new File(folder, server.getId().asString() + IOHelper.EBS_EXTENSION);
		if (!ebsFile.exists() || !folder.exists() || !folder.isDirectory())
			return new EBStorage().registerPrimitives()
					.setOnWriteCallback(new FileWriteCallback(ebsFile));
		return IOHelper.readEBStorage(ebsFile, new ClassNotFoundCallback() {

			@Override
			public boolean shouldJustDelete(String oldPath) {
				return oldPath.equals("us.myles_selim.starota.wrappers.InfoChannel$DataTypeInfoChannel");
			}
		}).registerPrimitives().setOnWriteCallback(new FileWriteCallback(ebsFile));
	}

	@SuppressWarnings("unchecked")
	public static <T, D extends DataType<T>> EBList<T> getEBListFromFolder(Guild server, File folder,
			DataType<T> type) {
		File eblFile = new File(folder, server.getId().asString() + IOHelper.EBS_LIST_EXTENSION);
		if (!eblFile.exists() || !folder.exists() || !folder.isDirectory())
			return new EBList<>(type).setOnWriteCallback(new FileWriteCallback(eblFile));
		return (EBList<T>) IOHelper.readEBList(eblFile)
				.setOnWriteCallback(new FileWriteCallback(eblFile));
	}

	private static class FileWriteCallback extends OnWriteCallback {

		private final File file;

		public FileWriteCallback(File file) {
			this.file = file;
		}

		@Override
		public void onWriteEBS(EBStorage ebs) {
			IOHelper.writeEBStorage(ebs, this.file);
		}

		@Override
		public void onWriteEBL(EBList<?> ebl) {
			IOHelper.writeEBList(ebl, this.file);
		}

	}

}
