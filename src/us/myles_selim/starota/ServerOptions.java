package us.myles_selim.starota;

import java.io.File;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import sx.blah.discord.handle.obj.IGuild;
import us.myles_selim.ebs.EBStorage;
import us.myles_selim.ebs.IOHelper;
import us.myles_selim.ebs.callbacks.OnWriteCallback;

public class ServerOptions {

	private static boolean inited = false;
	private static final Map<Long, EBStorage> OPTIONS = new ConcurrentHashMap<>();

	public static void init() {
		if (inited)
			return;
		inited = true;

		if (!Starota.DATA_FOLDER.exists())
			Starota.DATA_FOLDER.mkdirs();
		File[] files = new File(Starota.DATA_FOLDER, "options").listFiles(IOHelper.FILE_FILTER);
		if (files != null) {
			for (File file : files) {
				String name = file.getName().substring(0,
						file.getName().length() - IOHelper.EBS_EXTENSION.length());
				long serverId = Long.parseLong(name);
				EBStorage options = IOHelper.readEBStorage(file)
						.setOnWriteCallback(getFlushCallback(serverId));
				OPTIONS.put(serverId, options);
			}
		}
	}

	public static void flush() {
		for (Long l : OPTIONS.keySet())
			flush(l);
	}

	public static void flush(IGuild server) {
		if (server != null)
			flush(server.getLongID());
	}

	private static void flush(long id) {
		if (!OPTIONS.containsKey(id))
			return;
		File optionsFolder = new File(Starota.DATA_FOLDER, "options");
		if (!optionsFolder.exists())
			optionsFolder.mkdirs();
		EBStorage options = OPTIONS.get(id);
		IOHelper.writeEBStorage(options, new File(optionsFolder, id + IOHelper.EBS_EXTENSION));
	}

	public static EBStorage getOptions(IGuild server) {
		long id = server.getLongID();
		if (!OPTIONS.containsKey(id))
			OPTIONS.put(id,
					new EBStorage().registerPrimitives().setOnWriteCallback(getFlushCallback(id)));
		return OPTIONS.get(id);
	}

	public static boolean hasKey(IGuild server, String key) {
		long id = server.getLongID();
		if (!OPTIONS.containsKey(id))
			return false;
		return OPTIONS.get(id).containsKey(key);
	}

	public static boolean hasKey(IGuild server, String key, Class<?> type) {
		long id = server.getLongID();
		if (!OPTIONS.containsKey(id))
			return false;
		return OPTIONS.get(id).containsKey(key) && type != null
				&& type.isInstance(OPTIONS.get(id).get(key));
	}

	public static boolean hasOptions(IGuild server) {
		return OPTIONS.containsKey(server.getLongID());
	}

	public static Object getValue(IGuild server, String key) {
		long id = server.getLongID();
		if (!OPTIONS.containsKey(id))
			return null;
		return OPTIONS.get(id).get(key);
	}

	public static void setValue(IGuild server, String key, Object val) {
		long id = server.getLongID();
		if (!OPTIONS.containsKey(id))
			OPTIONS.put(id,
					new EBStorage().registerPrimitives().setOnWriteCallback(getFlushCallback(id)));
		EBStorage options = OPTIONS.get(id);
		options.set(key, val);
	}

	private static final OnWriteCallback getFlushCallback(long id) {
		return new OnWriteCallback() {

			@Override
			public void onWrite() {
				flush(id);
			}
		};
	}

}
