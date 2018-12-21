package us.myles_selim.starota.leaderboards;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import sx.blah.discord.handle.obj.IGuild;
import us.myles_selim.ebs.IOHelper;
import us.myles_selim.starota.DebugServer;
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.Starota.BaseModules;
import us.myles_selim.starota.modules.StarotaModule;

public class LeaderboardManager {

	private static boolean inited = false;
	private static final Map<Long, List<Leaderboard>> LEADERBOARDS = new HashMap<>();
	private static final File LEADERBOARD_FOLDER = new File(Starota.DATA_FOLDER, "leaderboard");

	public static void init() {
		if (inited)
			return;
		inited = true;

		if (!Starota.DATA_FOLDER.exists())
			Starota.DATA_FOLDER.mkdirs();
		File[] files = LEADERBOARD_FOLDER.listFiles(new FileFilter() {

			@Override
			public boolean accept(File pathname) {
				if (!pathname.isDirectory())
					return false;
				try {
					Long.parseLong(pathname.getName());
					return true;
				} catch (NumberFormatException e) {}
				return false;
			}
		});
		if (files != null) {
			for (File file : files) {
				File[] nestFiles = file.listFiles(IOHelper.EBS_FILE_FILTER);
				if (nestFiles == null || nestFiles.length == 0)
					continue;
				long serverId = Long.parseLong(file.getName());
				List<Leaderboard> boards = LEADERBOARDS.get(serverId);
				if (boards == null) {
					boards = new ArrayList<>();
					LEADERBOARDS.put(serverId, boards);
				}
				for (File nFile : nestFiles)
					boards.add(
							new Leaderboard(Starota.getGuild(serverId), IOHelper.readEBStorage(nFile)));
			}
		}
	}

	public static void flush() {
		if (!Starota.DATA_FOLDER.exists())
			Starota.DATA_FOLDER.mkdirs();
		for (Entry<Long, List<Leaderboard>> e : LEADERBOARDS.entrySet()) {
			File serverFolder = new File(LEADERBOARD_FOLDER, Long.toString(e.getKey()));
			if (!serverFolder.exists())
				serverFolder.mkdirs();
			for (Leaderboard b : e.getValue()) {
				IOHelper.writeEBStorage(b.toStorage(),
						new File(serverFolder, b.getDisplayName() + IOHelper.EBS_LIST_EXTENSION));
			}
		}
		DebugServer.update();
	}

	public static void flush(IGuild guild) {
		if (!LEADERBOARDS.containsKey(guild.getLongID()))
			return;
		List<Leaderboard> boards = LEADERBOARDS.get(guild.getLongID());
		File serverFolder = new File(LEADERBOARD_FOLDER, Long.toString(guild.getLongID()));
		if (!serverFolder.exists())
			serverFolder.mkdirs();
		for (Leaderboard b : boards) {
			IOHelper.writeEBStorage(b.toStorage(),
					new File(serverFolder, b.getDisplayName() + IOHelper.EBS_LIST_EXTENSION));
		}
	}

	public static Leaderboard newLeaderboard(IGuild guild, String name) {
		if (!StarotaModule.isModuleEnabled(guild, BaseModules.LEADERBOARDS))
			return null;
		Leaderboard testBoard = getLeaderboard(guild, name);
		if (testBoard != null)
			return null;
		List<Leaderboard> boards = LEADERBOARDS.get(guild.getLongID());
		if (boards == null) {
			boards = new ArrayList<>();
			LEADERBOARDS.put(guild.getLongID(), boards);
		}
		if (boards.size() >= Starota.getMaxLeaderboards(guild))
			return null;
		Leaderboard board = new Leaderboard(guild, name);
		board.addAlias(name.replaceAll(" ", "_"));
		boards.add(board);
		flush();
		return board;
	}

	public static int getLeaderboardCount(IGuild guild) {
		if (!StarotaModule.isModuleEnabled(guild, BaseModules.LEADERBOARDS)
				|| !LEADERBOARDS.containsKey(guild.getLongID()))
			return 0;
		return LEADERBOARDS.get(guild.getLongID()).size();
	}

	public static List<Leaderboard> getLeaderboards(IGuild guild) {
		if (!StarotaModule.isModuleEnabled(guild, BaseModules.LEADERBOARDS))
			return null;
		if (!LEADERBOARDS.containsKey(guild.getLongID()))
			return Collections.emptyList();
		return Collections.unmodifiableList(LEADERBOARDS.get(guild.getLongID()));
	}

	public static List<Leaderboard> getLeaderboardsActive(IGuild guild) {
		if (!StarotaModule.isModuleEnabled(guild, BaseModules.LEADERBOARDS))
			return null;
		if (!LEADERBOARDS.containsKey(guild.getLongID()))
			return Collections.emptyList();
		List<Leaderboard> boards = new ArrayList<>();
		for (Leaderboard b : LEADERBOARDS.get(guild.getLongID()))
			if (b.isActive())
				boards.add(b);
		return Collections.unmodifiableList(boards);
	}

	public static Leaderboard getLeaderboard(IGuild guild, String name) {
		if (!StarotaModule.isModuleEnabled(guild, BaseModules.LEADERBOARDS))
			return null;
		if (!LEADERBOARDS.containsKey(guild.getLongID()))
			return null;
		String[] names = new String[] { name, name.replaceAll(" ", "_"), name.replaceAll(" ", "-"),
				name.replaceAll(" ", "") };
		for (Leaderboard b : LEADERBOARDS.get(guild.getLongID())) {
			if (stringArrayContainsIgnoreCase(names, b.getDisplayName()))
				return b;
			for (String alias : b.getAliases())
				if (stringArrayContainsIgnoreCase(names, alias))
					return b;
		}
		return null;
	}

	public static Leaderboard getLeaderboardActive(IGuild guild, String name) {
		if (!StarotaModule.isModuleEnabled(guild, BaseModules.LEADERBOARDS))
			return null;
		Leaderboard board = getLeaderboard(guild, name);
		if (!board.isActive())
			return null;
		return board;
	}

	private static boolean stringArrayContainsIgnoreCase(String[] arr, String val) {
		for (String v : arr)
			if (v == val || v.equalsIgnoreCase(val))
				return true;
		return false;
	}

}
