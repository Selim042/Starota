package us.myles_selim.starota;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventDispatcher;
import sx.blah.discord.handle.obj.ActivityType;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IRole;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.handle.obj.StatusType;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.EmbedBuilder;
import sx.blah.discord.util.RequestBuffer;
import us.myles_selim.starota.commands.CommandChangelog;
import us.myles_selim.starota.commands.CommandChangelogChannel;
import us.myles_selim.starota.commands.CommandCredits;
import us.myles_selim.starota.commands.CommandGetTop;
import us.myles_selim.starota.commands.CommandStatus;
import us.myles_selim.starota.commands.CommandSupportStarota;
import us.myles_selim.starota.commands.CommandTest;
import us.myles_selim.starota.commands.registry.PrimaryCommandHandler;
import us.myles_selim.starota.commands.registry.java.JavaCommandHandler;
import us.myles_selim.starota.leaderboards.LeaderboardManager;
import us.myles_selim.starota.leaderboards.commands.CommandEditLeaderboard;
import us.myles_selim.starota.leaderboards.commands.CommandGetLeaderboard;
import us.myles_selim.starota.leaderboards.commands.CommandListLeaderboards;
import us.myles_selim.starota.leaderboards.commands.CommandNewLeaderboard;
import us.myles_selim.starota.leaderboards.commands.CommandUpdateLeaderboard;
import us.myles_selim.starota.lua.LuaEventHandler;
import us.myles_selim.starota.lua.LuaUtils;
import us.myles_selim.starota.lua.commands.CommandUploadScript;
import us.myles_selim.starota.lua.commands.LuaCommandHandler;
import us.myles_selim.starota.modules.CommandModules;
import us.myles_selim.starota.modules.StarotaModule;
import us.myles_selim.starota.profiles.ProfileManager;
import us.myles_selim.starota.profiles.commands.CommandGetProfilelessPlayers;
import us.myles_selim.starota.profiles.commands.CommandProfile;
import us.myles_selim.starota.profiles.commands.CommandProfileHelp;
import us.myles_selim.starota.profiles.commands.CommandRegister;
import us.myles_selim.starota.profiles.commands.CommandSelfRegister;
import us.myles_selim.starota.profiles.commands.CommandUpdateProfile;
import us.myles_selim.starota.research.CommandSetResearchChannel;
import us.myles_selim.starota.research.ResearchTracker;
import us.myles_selim.starota.role_management.commands.CommandAddGroup;
import us.myles_selim.starota.role_management.commands.CommandGetGroups;
import us.myles_selim.starota.role_management.commands.CommandRemoveGroup;
import us.myles_selim.starota.role_management.commands.CommandSetAsGroup;
import us.myles_selim.starota.trading.FormManager;
import us.myles_selim.starota.trading.Tradeboard;
import us.myles_selim.starota.trading.commands.CommandFindTrade;
import us.myles_selim.starota.trading.commands.CommandForTrade;
import us.myles_selim.starota.trading.commands.CommandGetForms;
import us.myles_selim.starota.trading.commands.CommandGetShinies;
import us.myles_selim.starota.trading.commands.CommandGetTrade;
import us.myles_selim.starota.trading.commands.CommandGetUserTrades;
import us.myles_selim.starota.trading.commands.CommandLookingFor;
import us.myles_selim.starota.trading.commands.CommandRemoveTrade;
import us.myles_selim.starota.trading.commands.CommandTradeboardHelp;

public class Starota {

	private static IDiscordClient CLIENT;
	private static final Properties PROPERTIES = new Properties();

	public static final long SELIM_USER_ID = 134855940938661889L;
	public static final long SUPPORT_SERVER = 436614503606779914L;
	public static final String SUPPORT_SERVER_LINK = "https://discord.gg/NxverNw";

	public static final long PVILLE_SERVER = 314733127027130379L;

	public final static boolean DEBUG = false;
	public static boolean IS_DEV;
	public static boolean FULLY_STARTED = false;
	public final static String BOT_NAME = "Starota";
	public final static String VERSION = "2.2.2";
	public final static String CHANGELOG = "Changelog for v" + VERSION + "\n"
			+ "Public changes:\n * Fix issue with profile command";
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
		BaseModules.registerModules();
		DebugServer debug = new DebugServer();
		debug.start();

		CLIENT.changePresence(StatusType.DND, ActivityType.PLAYING, "registering commands...");

		JavaCommandHandler.registerCommand(new CommandChangelog());
		JavaCommandHandler.registerCommand(new CommandCredits());
		JavaCommandHandler.registerCommand(new CommandSupportStarota());

		JavaCommandHandler.registerCommand("Administrative", new CommandStatus());
		JavaCommandHandler.registerCommand("Administrative", new CommandSetResearchChannel());
		JavaCommandHandler.registerCommand("Administrative", new CommandChangelogChannel());
		if (IS_DEV) {
			JavaCommandHandler.registerCommand("Debug", new CommandGetTop());
			JavaCommandHandler.registerCommand("Debug", new CommandTest());
			JavaCommandHandler.registerCommand("Debug", new CommandGenerateCommandWiki());
		}

		JavaCommandHandler.registerCommand("Profiles", new CommandRegister());
		JavaCommandHandler.registerCommand("Profiles", new CommandUpdateProfile());
		JavaCommandHandler.registerCommand("Profiles", new CommandProfile());
		JavaCommandHandler.registerCommand("Profiles", new CommandSelfRegister());
		JavaCommandHandler.registerCommand("Profiles", new CommandGetProfilelessPlayers());
		JavaCommandHandler.registerCommand("Profiles", new CommandProfileHelp());

		JavaCommandHandler.registerCommand("Groups", new CommandGetGroups());
		JavaCommandHandler.registerCommand("Groups", new CommandAddGroup());
		JavaCommandHandler.registerCommand("Groups", new CommandRemoveGroup());
		JavaCommandHandler.registerCommand("Groups", new CommandSetAsGroup());

		JavaCommandHandler.registerCommand("Tradeboard", new CommandTradeboardHelp());
		if (IS_DEV) {
			JavaCommandHandler.registerCommand("Debug", new CommandGetForms());
			JavaCommandHandler.registerCommand("Debug", new CommandGetShinies());
		}
		JavaCommandHandler.registerCommand("Tradeboard", new CommandForTrade());
		JavaCommandHandler.registerCommand("Tradeboard", new CommandGetUserTrades());
		JavaCommandHandler.registerCommand("Tradeboard", new CommandFindTrade());
		JavaCommandHandler.registerCommand("Tradeboard", new CommandGetTrade());
		JavaCommandHandler.registerCommand("Tradeboard", new CommandLookingFor());
		JavaCommandHandler.registerCommand("Tradeboard", new CommandRemoveTrade());

		JavaCommandHandler.registerCommand("Lua", new CommandUploadScript());

		JavaCommandHandler.registerCommand("Leaderboard", new CommandEditLeaderboard());
		JavaCommandHandler.registerCommand("Leaderboard", new CommandUpdateLeaderboard());
		JavaCommandHandler.registerCommand("Leaderboard", new CommandNewLeaderboard());
		JavaCommandHandler.registerCommand("Leaderboard", new CommandGetLeaderboard());
		JavaCommandHandler.registerCommand("Leaderboard", new CommandListLeaderboards());

		JavaCommandHandler.registerCommand("Modules", new CommandModules());

		try {
			Thread.sleep(2500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		CLIENT.changePresence(StatusType.IDLE, ActivityType.PLAYING,
				"starting threads and loading settings...");

		FormManager.init();
		ServerOptions.init();
		ResearchTracker.init();
		ProfileManager.init();
		Tradeboard.init();
		LeaderboardManager.init();

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
		CLIENT.changePresence(StatusType.ONLINE, ActivityType.PLAYING,
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
		dispatcher.registerListener(new PrimaryCommandHandler());
		dispatcher.registerListener(new EventHandler());

		LuaUtils.registerConverters();
		dispatcher.registerListener(new LuaEventHandler());
		PrimaryCommandHandler.registerCommandHandler(new LuaCommandHandler());

		FULLY_STARTED = true;
		DebugServer.update();
	}

	public static class BaseModules {

		public static final StarotaModule PROFILES = new StarotaModule("Player Profiles", "Profiles");
		public static final StarotaModule GROUPS = new StarotaModule("Groups", "Groups");
		public static final StarotaModule TRADEBOARD = new StarotaModule("Tradeboard", "Tradeboard",
				PROFILES);
		public static final StarotaModule LUA = new StarotaModule("Lua", "Lua");
		public static final StarotaModule LEADERBOARDS = new StarotaModule("Leaderboards", "Leaderboard",
				PROFILES);

		private static void registerModules() {
			StarotaModule.registerModule(PROFILES);
			StarotaModule.registerModule(GROUPS);
			StarotaModule.registerModule(TRADEBOARD);
			StarotaModule.registerModule(LUA);
			StarotaModule.registerModule(LEADERBOARDS);
		}

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
		if (user.matches("<\\d{18}>")) {
			long userId = Long.parseLong(user.substring(1, 19));
			IUser userD = Starota.getClient().getGuildByID(serverId).getUserByID(userId);
			if (userD != null)
				return userD;
		}
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

	public static IGuild getSupportServer() {
		return getGuild(SUPPORT_SERVER);
	}

	public static boolean canUseLua(IGuild server) {
		if (server == null)
			return false;
		IUser owner = server.getOwner();
		IGuild supportServer = getGuild(SUPPORT_SERVER); // support server
		if (!supportServer.getUsers().contains(owner))
			return false;
		if (owner.getLongID() == supportServer.getOwnerLongID())
			return true;
		IRole requiredRole = supportServer.getRoleByID(436617921620606976L); // supporter
																				// role
		return owner.hasRole(requiredRole);
	}

	public static int getMaxLeaderboards(IGuild server) {
		int max = 3;
		for (EnumPatreonPerm p : getPatreonPerms(server)) {
			switch (p) {
			case LEADERBOARD_5:
				max = 5;
				break;
			case LEADERBOARD_10:
				max = 10;
				break;
			case LEADERBOARD_20:
				max = 20;
				break;
			case LEADERBOARD_100:
				max = 100;
				break;
			default:
				break;
			}
		}
		return max;
	}

	public static IRole getPatronRole(IUser user) {
		IGuild supportServer = getSupportServer();
		if (!supportServer.getUsers().contains(user))
			return null;
		List<IRole> supportRoles = supportServer.getRoles();
		List<IRole> patronRoles = new ArrayList<>();
		boolean inRange = false;
		for (IRole r : supportRoles) {
			if (r.getName().equals("MARKER")) {
				inRange = !inRange;
				continue;
			}
			if (inRange)
				patronRoles.add(r);
		}
		patronRoles.retainAll(user.getRolesForGuild(supportServer));
		if (patronRoles.size() > 0)
			return patronRoles.get(0);
		return null;
	}

	public static List<EnumPatreonPerm> getPatreonPerms(IGuild server) {
		if (server == null)
			return Collections.emptyList();
		IUser owner = server.getOwner();
		IGuild supportServer = getGuild(SUPPORT_SERVER); // support server
		if (!supportServer.getUsers().contains(owner))
			return Collections.emptyList();
		if (owner.getLongID() == supportServer.getOwnerLongID())
			return Arrays.asList(EnumPatreonPerm.values());
		List<EnumPatreonPerm> perms = new ArrayList<>();
		List<IRole> roles = owner.getRolesForGuild(supportServer);
		for (EnumPatreonPerm p : EnumPatreonPerm.values())
			if (roles.contains(p.getRole()))
				perms.add(p);
		return Collections.unmodifiableList(perms);
	}

	public static void submitError(Throwable e) {
		submitError(null, e);
	}

	public static void submitError(String message, Throwable e) {
		if (IS_DEV || CLIENT == null || !CLIENT.isReady())
			return;
		long reportChannelId = 522805019326677002L;
		IChannel reportChannel = CLIENT.getChannelByID(reportChannelId);
		if (reportChannel == null)
			return;
		EmbedBuilder builder = new EmbedBuilder();
		builder.withTitle("An " + e.getClass().getSimpleName() + " has been thrown"
				+ (message == null ? "" : ": " + message));
		String body = "";
		for (StackTraceElement t : e.getStackTrace())
			body += t + "\n";
		builder.appendDesc(body);
		RequestBuffer.request(() -> reportChannel.sendMessage(builder.build()));
	}

	// public static void sendTestMessage(String message) {
	// if (CLIENT != null && CLIENT.isReady())
	// getChannel(SELIM_MODDING, TEST_TEXT).sendMessage(message);
	// }

}
