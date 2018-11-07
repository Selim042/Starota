package us.myles_selim.starota;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventDispatcher;
import sx.blah.discord.handle.obj.ActivityType;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.handle.obj.StatusType;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.RequestBuffer;
import us.myles_selim.starota.commands.CommandChangelog;
import us.myles_selim.starota.commands.CommandChangelogChannel;
import us.myles_selim.starota.commands.CommandCredits;
import us.myles_selim.starota.commands.CommandGetTop;
import us.myles_selim.starota.commands.CommandStatus;
import us.myles_selim.starota.commands.CommandTest;
import us.myles_selim.starota.commands.registry.CommandRegistry;
import us.myles_selim.starota.profiles.ProfileManager;
import us.myles_selim.starota.profiles.commands.CommandGetProfilelessPlayers;
import us.myles_selim.starota.profiles.commands.CommandProfile;
import us.myles_selim.starota.profiles.commands.CommandRegister;
import us.myles_selim.starota.profiles.commands.CommandSelfRegister;
import us.myles_selim.starota.profiles.commands.CommandUpdateProfile;
import us.myles_selim.starota.research.CommandSetResearchChannel;
import us.myles_selim.starota.research.ResearchTracker;
import us.myles_selim.starota.role_management.commands.CommandAddGroup;
import us.myles_selim.starota.role_management.commands.CommandGetGroups;
import us.myles_selim.starota.role_management.commands.CommandRemoveGroup;
import us.myles_selim.starota.role_management.commands.CommandSetAsGroup;
import us.myles_selim.starota.trading.Tradeboard;
import us.myles_selim.starota.trading.commands.CommandFindTrade;
import us.myles_selim.starota.trading.commands.CommandForTrade;
import us.myles_selim.starota.trading.commands.CommandGetForms;
import us.myles_selim.starota.trading.commands.CommandGetShinies;
import us.myles_selim.starota.trading.commands.CommandGetTrade;
import us.myles_selim.starota.trading.commands.CommandGetUserTrades;

public class Starota {

	// TODO: Finish tradeboard

	private static IDiscordClient CLIENT;
	private static final Properties PROPERTIES = new Properties();

	public static final long SELIM_USER_ID = 134855940938661889L;
	public static final String SUPPORT_SERVER_LINK = "http://discord.gg/jRf4Pzh";

	// public static final long TEST_SERVER = 481646364716040202L;
	// public static final long TEST_MONITOR = 489249695571509249L;
	// public static final long TEST_REPORT = 489249345485537301L;

	public static final long PVILLE_SERVER = 314733127027130379L;
	// public static final long PVILLE_MONITOR = 430353385716187146L;
	// public static final long PVILLE_REPORT = 336237827149004800L;
	// public static final long PVILLE_ADMIN_CHAT = 336237827149004800L;
	// public static final long PVILLE_LOCAL_UPDATES = 314784664944377856L;
	// public static final long PVILLE_CHAT_ROOM = 314733127027130379L;
	// public static final long PVILLE_MANUAL_CALLOUTS = 314736446915215360L;

	public final static boolean DEBUG = false;
	public static boolean IS_DEV;
	public final static String BOT_NAME = "Starota";
	public final static String VERSION = "1.0.6";
	public final static String CHANGELOG = "Changelog for v" + VERSION + "\n"
			+ "Public facing changes:\n * Display trade post and profile update date\n"
			+ " * Set trade embed color to poster team color\n * Correct team icon URLs";
	public final static File DATA_FOLDER = new File("starotaData");

	public static void main(String[] args) {
		try {
			PROPERTIES.load(new FileInputStream("starota.properties"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		ClientBuilder clientBuilder = new ClientBuilder();
		clientBuilder.withToken(PROPERTIES.getProperty("token"));
		CLIENT = null;
		try {
			CLIENT = clientBuilder.login();
		} catch (DiscordException e) {
			e.printStackTrace();
		}
		if (CLIENT == null) {
			System.err.println("Failed to login, exiting");
			return;
		}
		IS_DEV = Boolean.parseBoolean(PROPERTIES.getProperty("is_dev"));
		EventDispatcher dispatcher = CLIENT.getDispatcher();
		try {
			while (!CLIENT.isReady())
				Thread.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		CLIENT.changePresence(StatusType.ONLINE, ActivityType.PLAYING, "registering commands...");

		CommandRegistry.registerCommand(new CommandChangelog());
		CommandRegistry.registerCommand(new CommandCredits());

		CommandRegistry.registerCommand("Administrative", new CommandStatus());
		CommandRegistry.registerCommand("Administrative", new CommandSetResearchChannel());
		CommandRegistry.registerCommand("Administrative", new CommandChangelogChannel());
		if (IS_DEV) {
			CommandRegistry.registerCommand("Testing", new CommandGetTop());
			CommandRegistry.registerCommand("Testing", new CommandTest());
		}

		CommandRegistry.registerCommand("Profiles", new CommandRegister());
		CommandRegistry.registerCommand("Profiles", new CommandUpdateProfile());
		CommandRegistry.registerCommand("Profiles", new CommandProfile());
		CommandRegistry.registerCommand("Profiles", new CommandSelfRegister());
		CommandRegistry.registerCommand("Profiles", new CommandGetProfilelessPlayers());

		CommandRegistry.registerCommand("Groups", new CommandGetGroups());
		CommandRegistry.registerCommand("Groups", new CommandAddGroup());
		CommandRegistry.registerCommand("Groups", new CommandRemoveGroup());
		CommandRegistry.registerCommand("Groups", new CommandSetAsGroup());

		if (IS_DEV) {
			CommandRegistry.registerCommand("Tradeboard", new CommandGetForms());
			CommandRegistry.registerCommand("Tradeboard", new CommandGetShinies());
			CommandRegistry.registerCommand("Tradeboard", new CommandForTrade());
			CommandRegistry.registerCommand("Tradeboard", new CommandGetUserTrades());
			CommandRegistry.registerCommand("Tradeboard", new CommandFindTrade());
			CommandRegistry.registerCommand("Tradeboard", new CommandGetTrade());
		}

		try {
			Thread.sleep(2500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		CLIENT.changePresence(StatusType.IDLE, ActivityType.PLAYING,
				"starting threads and loading settings...");

		ServerOptions.init();
		ResearchTracker.init();
		ProfileManager.init();
		Tradeboard.init();

		// WebServer.init();

		// Thread reportThread = new ThreadReport(TEST_SERVER);
		// reportThread.start();
		// Thread reportThread2 = new ThreadReport(PVILLE_SERVER);
		// reportThread2.start();

		Thread saveThread = new Thread() {

			@Override
			public void run() {
				while (true) {
					try {
						Thread.sleep(36000);
					} catch (InterruptedException e) {}
					ResearchTracker.flush();
				}
			}
		};
		saveThread.start();

		try {
			Thread.sleep(2500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		CLIENT.changePresence(StatusType.DND, ActivityType.PLAYING,
				"v" + VERSION + (DEBUG || IS_DEV ? "d" : ""));

		Thread changesThread = new Thread() {

			@Override
			public void run() {
				for (IGuild s : CLIENT.getGuilds()) {
					if (!ServerOptions.hasKey(s, CommandChangelogChannel.CHANGES_CHANNEL))
						continue;
					IChannel changesChannel = CLIENT.getChannelByID(
							(long) ServerOptions.getValue(s, CommandChangelogChannel.CHANGES_CHANNEL));
					if (changesChannel == null)
						continue;
					String latestChangelog = (String) ServerOptions.getValue(s, "changesVersion");
					if (!VERSION.equalsIgnoreCase(latestChangelog)) {
						RequestBuffer.request(() -> {
							changesChannel.sendMessage("```" + CHANGELOG + "```");
						});
						ServerOptions.setValue(s, "changesVersion", VERSION);
					}
				}
			}
		};
		changesThread.start();
		dispatcher.registerListener(new EventHandler());
	}

	public static IDiscordClient getClient() {
		return CLIENT;
	}

	public static IGuild getGuild(long guildId) {
		return CLIENT.getGuildByID(guildId);
	}

	public static IChannel getChannel(long guildId, long channelId) {
		return getGuild(guildId).getChannelByID(channelId);
	}

	public static IChannel getChannel(long channelId) {
		return CLIENT.getChannelByID(channelId);
	}

	public static IUser getUser(long userId) {
		return CLIENT.fetchUser(userId);
	}

	public static IUser getOurUser() {
		return CLIENT.getOurUser();
	}

	public static String getOurName(long guildId) {
		return getOurName(getGuild(guildId));
	}

	public static String getOurName(IGuild guild) {
		if (CLIENT == null)
			return null;
		if (guild == null)
			return getOurUser().getName();
		return getOurUser().getDisplayName(guild);
	}

	public static IUser findUser(String name) {
		return findUser(-1, name);
	}

	public static IUser findUser(long serverId, String name) {
		try {
			long userId = Long.parseLong(name);
			IUser user = getUser(userId);
			if (user != null)
				return user;
		} catch (NumberFormatException e) {}
		String user = name.replaceAll("@", "").replaceAll("#\\d{4}", "");
		String discrim = null;
		if (name.matches(".*#\\d{4}")) {
			int hash = name.indexOf("#");
			discrim = name.substring(hash + 1, hash + 5);
		} else if (name.matches("<@!\\d*>")) {
			try {
				long id = Long.parseLong(name.substring(3, name.length() - 1));
				IUser idUser = getUser(id);
				if (idUser != null) {
					return idUser;
				}
			} catch (NumberFormatException e) {}
		}
		IDiscordClient client = Starota.getClient();
		List<IUser> users;
		if (serverId != -1)
			users = client.getGuildByID(serverId).getUsersByName(user);
		else
			users = client.getUsersByName(user);
		for (IUser u : users)
			if (discrim == null || u.getDiscriminator().equals(discrim))
				return u;
		if (serverId != -1) {
			IGuild server = client.getGuildByID(serverId);
			for (IUser u : server.getUsers()) {
				String nickname = u.getNicknameForGuild(server);
				if (u.getName().equals(user) || u.getDisplayName(server).equals(user)
						|| (nickname != null && nickname.equals(user)))
					return u;
			}
		}
		return null;
	}

	public static IChannel findChannel(String name) {
		return findChannel(-1, name);
	}

	public static IChannel findChannel(long serverId, String name) {
		if (name == null)
			return null;
		if (name.matches("<#\\d*>")) {
			try {
				long id = Long.parseLong(name.substring(2, name.length() - 1));
				IChannel idChannel = getChannel(id);
				if (idChannel != null)
					return idChannel;
			} catch (NumberFormatException e) {}
		}
		name = name.substring(1);
		IDiscordClient client = Starota.getClient();
		List<IChannel> channels;
		if (serverId != -1)
			channels = client.getGuildByID(serverId).getChannelsByName(name);
		else {
			channels = client.getChannels(true);
			for (IChannel ch : channels)
				if (ch.getName().equalsIgnoreCase(name))
					return ch;
		}
		if (serverId != -1) {
			IGuild server = client.getGuildByID(serverId);
			for (IChannel ch : server.getChannels())
				if (ch.getName().equalsIgnoreCase(name))
					return ch;
		}
		return null;
	}

	// public static void sendTestMessage(String message) {
	// if (CLIENT != null && CLIENT.isReady())
	// getChannel(SELIM_MODDING, TEST_TEXT).sendMessage(message);
	// }

}
