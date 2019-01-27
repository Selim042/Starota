package us.myles_selim.starota.research;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IUser;
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.research.CommandSetResearchChannel.EnumResearchChannel;
import us.myles_selim.starota.wrappers.StarotaServer;

public class ResearchTracker {

	private static boolean inited = false;
	private static final Map<Long, List<Researcher>> DATA = new ConcurrentHashMap<>();
	private static final File DATA_FOLDER = new File("starotaData");

	private static final FilenameFilter DAT_FILTER = new FilenameFilter() {

		@Override
		public boolean accept(File dir, String name) {
			return name.endsWith(".dat");
		}
	};

	@SuppressWarnings("deprecation")
	public static void init() {
		if (inited)
			return;
		inited = true;

		Date today = new Date();
		if (!DATA_FOLDER.exists())
			DATA_FOLDER.mkdirs();
		File[] files = new File(DATA_FOLDER, "data").listFiles(DAT_FILTER);
		if (files != null) {
			for (File file : files) {
				String name = file.getName().substring(0, file.getName().length() - 4);
				try {
					// System.out.println("parsing " + name);
					long serverId = Long.parseLong(name);
					List<Researcher> serverData = new CopyOnWriteArrayList<>();
					DATA.put(serverId, serverData);
					BufferedReader fileIn = new BufferedReader(new FileReader(file));
					long timestamp = Long.parseLong(fileIn.readLine());
					Date timestampDate = new Date(timestamp);
					if (today.getDay() > 0 && timestampDate.getDay() > today.getDay())
						continue;
					// System.out.println("read timestamp");
					String line = fileIn.readLine();
					while (line != null && !line.isEmpty()) {
						// System.out.println("reading line: " + line);
						int colonIndex = line.indexOf(":");
						// System.out.println("parsing userId " +
						// line.substring(0, colonIndex));
						long userId = Long.parseLong(line.substring(0, colonIndex));
						// System.out.println("parsing posts " +
						// line.substring(colonIndex + 1));
						int posts = Integer.parseInt(line.substring(colonIndex + 1));
						serverData.add(new Researcher(userId, posts));
						line = fileIn.readLine();
					}
					fileIn.close();
				} catch (NumberFormatException | IOException e) {
					System.out.println("Encountered a " + e.getClass().getSimpleName()
							+ " error while parsing file " + file.getName());
					Starota.submitError(e);
					continue;
				}
			}
		}
	}

	public static void flush() {
		File data = new File(DATA_FOLDER, "data");
		if (!data.exists())
			data.mkdirs();
		for (Entry<Long, List<Researcher>> entry : DATA.entrySet()) {
			try {
				BufferedWriter fileOut = new BufferedWriter(
						new FileWriter(new File(data, entry.getKey() + ".dat")));
				fileOut.write(System.currentTimeMillis() + "\n");
				for (Researcher r : entry.getValue())
					fileOut.write(r.getLongId() + ":" + r.getPosts() + '\n');
				fileOut.close();
			} catch (NumberFormatException | IOException e) {
				Starota.submitError(e);
				continue;
			}
		}
	}

	public static int addPost(IGuild server, IUser user) {
		return addPost(server.getLongID(), user.getLongID());
	}

	public static int addPost(long server, long user) {
		if (!DATA.containsKey(server))
			DATA.put(server, new CopyOnWriteArrayList<>());
		List<Researcher> serverData = DATA.get(server);
		Researcher researcher = null;
		for (Researcher r : serverData)
			if (r != null && r.getLongId() == user) {
				researcher = r;
				break;
			}
		if (researcher == null) {
			researcher = new Researcher(user);
			serverData.add(researcher);
		}
		researcher.incrementPosts();
		serverData.sort(null);
		return researcher.getPosts();
	}

	public static List<Researcher> getTopPosters(IGuild server) {
		return getTopPosters(server.getLongID());
	}

	public static List<Researcher> getTopPosters(long server) {
		if (!DATA.containsKey(server))
			return Collections.emptyList();
		List<Researcher> serverData = DATA.get(server);
		serverData.sort(null);
		List<Researcher> returnData = new ArrayList<>();
		for (int i = 0; i < 5 && i < serverData.size(); i++)
			returnData.add(i, serverData.get(i));
		return returnData;
	}

	public static void setResearchChannel(StarotaServer server, EnumResearchChannel rChannel,
			IChannel channel) {
		server.setValue(rChannel.getKey(), channel.getLongID());
	}

	public static IChannel getResearchChannel(StarotaServer server, EnumResearchChannel rChannel) {
		if (rChannel == null || !server.hasKey(rChannel.getKey(), long.class))
			return null;
		long channelId = (long) server.getValue(rChannel.getKey());
		IChannel channel = Starota.getChannel(server.getDiscordGuild().getLongID(), channelId);
		return channel;
	}

	protected static void clearPosters(IGuild server) {
		clearPosters(server.getLongID());
	}

	protected static void clearPosters(long server) {
		if (!DATA.containsKey(server))
			return;
		DATA.remove(server);
		flush();
	}

}
