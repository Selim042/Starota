package us.myles_selim.starota.leaderboard;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import us.myles_selim.ebs.IOHelper;
import us.myles_selim.starota.Starota;

public class LeaderboardManager {

	private static boolean inited = false;
	private static final Map<Long, List<Leaderboard>> LEADERBOARDS = new HashMap<>();

	public static void init() {
		if (inited)
			return;
		inited = true;

		if (!Starota.DATA_FOLDER.exists())
			Starota.DATA_FOLDER.mkdirs();
		File[] files = new File(Starota.DATA_FOLDER, "leaderboard").listFiles(new FileFilter() {

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
				if (nestFiles == null)
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

}
