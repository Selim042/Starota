package us.myles_selim.starota;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import sx.blah.discord.handle.obj.IGuild;
import us.myles_selim.ebs.DataType;
import us.myles_selim.ebs.EBList;
import us.myles_selim.ebs.EBStorage;
import us.myles_selim.ebs.IOHelper;
import us.myles_selim.ebs.callbacks.OnWriteCallback;

public class ServerDataHelper {

	public static List<EBStorage> getEBSsFromFolder(IGuild server, File folder) {
		if (!folder.exists() || !folder.isDirectory())
			return Collections.emptyList();
		List<EBStorage> ebss = new ArrayList<>();
		File sFolder = new File(folder, server.getStringID());
		if (!sFolder.exists())
			return Collections.emptyList();
		File[] nestFiles = sFolder.listFiles(IOHelper.EBS_FILE_FILTER);
		if (nestFiles == null || nestFiles.length == 0)
			return Collections.emptyList();
		for (File nFile : nestFiles)
			ebss.add(IOHelper.readEBStorage(nFile).setOnWriteCallback(new FileWriteCallback(nFile)));
		return ebss;
	}

	public static EBStorage getEBSFromFolder(IGuild server, File folder) {
		File ebsFile = new File(folder, server.getStringID() + IOHelper.EBS_EXTENSION);
		if (!ebsFile.exists() || !folder.exists() || !folder.isDirectory())
			return new EBStorage().registerPrimitives()
					.setOnWriteCallback(new FileWriteCallback(ebsFile));
		return IOHelper.readEBStorage(ebsFile).setOnWriteCallback(new FileWriteCallback(ebsFile));
	}

	@SuppressWarnings("unchecked")
	public static <T, D extends DataType<T>> EBList<T> getEBListFromFolder(IGuild server, File folder,
			DataType<T> type) {
		File eblFile = new File(folder, server.getStringID() + IOHelper.EBS_LIST_EXTENSION);
		if (!eblFile.exists() || !folder.exists() || !folder.isDirectory())
			return new EBList<>(type);
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
