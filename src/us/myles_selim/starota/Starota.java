package us.myles_selim.starota;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.discordbots.api.client.DiscordBotListAPI;

import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.IShard;
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
import us.myles_selim.starota.assistants.CommandInviteAssistants;
import us.myles_selim.starota.assistants.StarotaAssistants;
import us.myles_selim.starota.assistants.pokedex.PokedexBot;
import us.myles_selim.starota.commands.CommandChangelog;
import us.myles_selim.starota.commands.CommandChangelogChannel;
import us.myles_selim.starota.commands.CommandCredits;
import us.myles_selim.starota.commands.CommandGenerateCommandWiki;
import us.myles_selim.starota.commands.CommandGetTop;
import us.myles_selim.starota.commands.CommandInvite;
import us.myles_selim.starota.commands.CommandStatus;
import us.myles_selim.starota.commands.CommandSupportStarota;
import us.myles_selim.starota.commands.CommandTest;
import us.myles_selim.starota.commands.pvp.CommandBattleReady;
import us.myles_selim.starota.commands.pvp.CommandFindBattles;
import us.myles_selim.starota.commands.pvp.CommandNotReady;
import us.myles_selim.starota.commands.registry.PrimaryCommandHandler;
import us.myles_selim.starota.commands.registry.java.JavaCommandHandler;
import us.myles_selim.starota.debug_server.DebugServer;
import us.myles_selim.starota.enums.EnumPatreonPerm;
import us.myles_selim.starota.events.CommandEvents;
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
import us.myles_selim.starota.pokedex.CommandPokedex;
import us.myles_selim.starota.profiles.commands.CommandGetProfilelessPlayers;
import us.myles_selim.starota.profiles.commands.CommandProfile;
import us.myles_selim.starota.profiles.commands.CommandProfileHelp;
import us.myles_selim.starota.profiles.commands.CommandRegister;
import us.myles_selim.starota.profiles.commands.CommandSelfRegister;
import us.myles_selim.starota.profiles.commands.CommandUpdateProfile;
import us.myles_selim.starota.raids.CommandRaid;
import us.myles_selim.starota.raids.CommandSetRaidEChannel;
import us.myles_selim.starota.reaction_messages.ReactionMessageRegistry;
import us.myles_selim.starota.research.CommandSetResearchChannel;
import us.myles_selim.starota.research.ResearchTracker;
import us.myles_selim.starota.role_management.commands.CommandAddGroup;
import us.myles_selim.starota.role_management.commands.CommandGetGroups;
import us.myles_selim.starota.role_management.commands.CommandRemoveGroup;
import us.myles_selim.starota.role_management.commands.CommandSetAsGroup;
import us.myles_selim.starota.silph_road.CommandSilphCard;
import us.myles_selim.starota.trading.FormManager;
import us.myles_selim.starota.trading.commands.CommandFindTrade;
import us.myles_selim.starota.trading.commands.CommandForTrade;
import us.myles_selim.starota.trading.commands.CommandGetForms;
import us.myles_selim.starota.trading.commands.CommandGetShinies;
import us.myles_selim.starota.trading.commands.CommandGetTrade;
import us.myles_selim.starota.trading.commands.CommandGetUserTrades;
import us.myles_selim.starota.trading.commands.CommandLookingFor;
import us.myles_selim.starota.trading.commands.CommandRemoveTrade;
import us.myles_selim.starota.trading.commands.CommandTradeboardHelp;
import us.myles_selim.starota.webserver.WebServer;
import us.myles_selim.starota.webserver.WebhookEventHandler;
import us.myles_selim.starota.wrappers.StarotaServer;

public class Starota {

	private static IDiscordClient CLIENT;
	private static DiscordBotListAPI BOT_LIST;
	// private static Socket MANAGER_SOCKET;
	private static final Properties PROPERTIES = new Properties();

	public static final long STAROTA_ID = 489245655710040099L;
	public static final long STAROTA_DEV_ID = 504088307148521475L;
	public static final long SELIM_USER_ID = 134855940938661889L;
	public static final long SUPPORT_SERVER = 436614503606779914L;
	public static final String SUPPORT_SERVER_LINK = "https://discord.gg/NxverNw";
	public static final String HTTP_USER_AGENT = "Mozilla/5.0; Starota/" + Starota.VERSION;

	public static final long PVILLE_SERVER = 314733127027130379L;

	public final static boolean DEBUG = false;
	public static boolean IS_DEV;
	public static boolean FULLY_STARTED = false;
	// public static EnumBotStatus STATUS = EnumBotStatus.UNKNOWN;
	public final static String BOT_NAME = "Starota";
	public final static String VERSION = "2.7.6";
	public final static String CHANGELOG = "Changelog for v" + VERSION + "\n" + "Public changes:\n"
			+ " * Fix issue with form names on raids";
	public final static File DATA_FOLDER = new File("starotaData");

	public static final PrimaryCommandHandler COMMAND_HANDLER = new PrimaryCommandHandler();

	public static void main(String[] args) {
		// STATUS = EnumBotStatus.INITIALIZING;
		// MANAGER_SOCKET = new Socket();
		// try {
		// MANAGER_SOCKET.bind(new InetSocketAddress("localhost",
		// Integer.parseInt(args[0])));
		// OutputStream out = MANAGER_SOCKET.getOutputStream();
		// out.write((BOT_NAME.toLowerCase() + '\0').getBytes());
		// out.write((args[1] + '\0').getBytes());
		// Thread socketHandler = new Thread() {
		//
		// @Override
		// public void run() {
		// try {
		// InputStream in = MANAGER_SOCKET.getInputStream();
		// OutputStream out = MANAGER_SOCKET.getOutputStream();
		// while (true) {
		// String cmd = "";
		// char c = (char) in.read();
		// while (in.available() > 0 && c != '\0') {
		// cmd += c;
		// c = (char) in.read();
		// }
		// switch (cmd) {
		// case "getStatus":
		// out.write((STATUS.name() + '\0').getBytes());
		// break;
		// default:
		// System.err.println("recieved cmd \"" + cmd
		// + "\" from manager, unsure how to handle");
		// }
		// }
		// } catch (IOException e) {
		// System.err.println("error in socket handler thread");
		// e.printStackTrace();
		// }
		// }
		// };
		// socketHandler.start();
		// } catch (Exception e) {
		// System.err.println("failed to connect to the bot manager");
		// e.printStackTrace();
		// }
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
		StarotaAssistants.init();
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

		JavaCommandHandler jCmdHandler = new JavaCommandHandler();
		COMMAND_HANDLER.registerCommandHandler(jCmdHandler);
		jCmdHandler.registerDefaultCommands();
		
		jCmdHandler.registerCommand(new CommandChangelog());
		jCmdHandler.registerCommand(new CommandCredits());
		jCmdHandler.registerCommand(new CommandSupportStarota());
		jCmdHandler.registerCommand(new CommandInvite());

		jCmdHandler.registerCommand("Administrative", new CommandStatus());
		jCmdHandler.registerCommand("Administrative", new CommandSetResearchChannel());
		jCmdHandler.registerCommand("Administrative", new CommandChangelogChannel());
		jCmdHandler.registerCommand("Administrative", new CommandInviteAssistants());
		if (IS_DEV) {
			jCmdHandler.registerCommand("Debug", new CommandGetTop());
			jCmdHandler.registerCommand("Debug", new CommandTest());
			jCmdHandler.registerCommand("Debug", new CommandGenerateCommandWiki());
		}

		jCmdHandler.registerCommand("Profiles", new CommandRegister());
		jCmdHandler.registerCommand("Profiles", new CommandUpdateProfile());
		jCmdHandler.registerCommand("Profiles", new CommandProfile());
		jCmdHandler.registerCommand("Profiles", new CommandSelfRegister());
		jCmdHandler.registerCommand("Profiles", new CommandGetProfilelessPlayers());
		jCmdHandler.registerCommand("Profiles", new CommandProfileHelp());

		jCmdHandler.registerCommand("Groups", new CommandGetGroups());
		jCmdHandler.registerCommand("Groups", new CommandAddGroup());
		jCmdHandler.registerCommand("Groups", new CommandRemoveGroup());
		jCmdHandler.registerCommand("Groups", new CommandSetAsGroup());

		jCmdHandler.registerCommand("Tradeboard", new CommandTradeboardHelp());
		if (IS_DEV) {
			jCmdHandler.registerCommand("Debug", new CommandGetForms());
			jCmdHandler.registerCommand("Debug", new CommandGetShinies());
		}
		jCmdHandler.registerCommand("Tradeboard", new CommandForTrade());
		jCmdHandler.registerCommand("Tradeboard", new CommandGetUserTrades());
		jCmdHandler.registerCommand("Tradeboard", new CommandFindTrade());
		jCmdHandler.registerCommand("Tradeboard", new CommandGetTrade());
		jCmdHandler.registerCommand("Tradeboard", new CommandLookingFor());
		jCmdHandler.registerCommand("Tradeboard", new CommandRemoveTrade());

		jCmdHandler.registerCommand("Lua", new CommandUploadScript());

		jCmdHandler.registerCommand("Leaderboard", new CommandEditLeaderboard());
		jCmdHandler.registerCommand("Leaderboard", new CommandUpdateLeaderboard());
		jCmdHandler.registerCommand("Leaderboard", new CommandNewLeaderboard());
		jCmdHandler.registerCommand("Leaderboard", new CommandGetLeaderboard());
		jCmdHandler.registerCommand("Leaderboard", new CommandListLeaderboards());

		jCmdHandler.registerCommand("Modules", new CommandModules());

		jCmdHandler.registerCommand("PvP", new CommandBattleReady());
		jCmdHandler.registerCommand("PvP", new CommandNotReady());
		jCmdHandler.registerCommand("PvP", new CommandFindBattles());

		jCmdHandler.registerCommand("Pokedex", new CommandPokedex());

		jCmdHandler.registerCommand("Silph Road", new CommandSilphCard());

		jCmdHandler.registerCommand("Raids", new CommandRaid());
		jCmdHandler.registerCommand("Raids", new CommandSetRaidEChannel());
		// jCmdHandler.registerCommand("Raids", new CommandRaidBosses());

		jCmdHandler.registerCommand("Events", new CommandEvents());

		try {
			Thread.sleep(2500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		CLIENT.changePresence(StatusType.IDLE, ActivityType.PLAYING,
				"starting threads and loading settings...");

		FormManager.init();
		ResearchTracker.init();

		ReactionMessageRegistry reactionRegistry = new ReactionMessageRegistry();
		dispatcher.registerListener(reactionRegistry);
		dispatcher.registerListener(COMMAND_HANDLER);
		dispatcher.registerListener(new EventHandler());
		dispatcher.registerListener(new WebhookEventHandler());
		ReactionMessageRegistry.init();
		WebServer.init();
		PokedexBot.start(reactionRegistry);
		submitStats();

		Thread saveThread = new Thread("ResearchFlusher") {

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
		Thread statusUpdater = new Thread("StarotaStatusUpdater") {

			@Override
			public void run() {
				while (true) {
					CLIENT.changePresence(StatusType.ONLINE, ActivityType.PLAYING,
							"v" + VERSION + (DEBUG || IS_DEV ? "d" : ""));
					try {
						Thread.sleep(3600000); // 1 hour
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		statusUpdater.start();

		Thread changesThread = new Thread("ChangelogThread") {

			@Override
			public void run() {
				boolean sentToAll = true;
				for (IGuild g : CLIENT.getGuilds()) {
					StarotaServer server = StarotaServer.getServer(g);
					if (!server.hasKey(CommandChangelogChannel.CHANGES_CHANNEL))
						continue;
					IChannel changesChannel = CLIENT.getChannelByID(
							(long) server.getValue(CommandChangelogChannel.CHANGES_CHANNEL));
					if (changesChannel == null)
						continue;
					String latestChangelog = (String) server.getValue("changesVersion");
					if (!VERSION.equalsIgnoreCase(latestChangelog)) {
						RequestBuffer
								.request(() -> changesChannel.sendMessage("```" + CHANGELOG + "```"));
						server.setValue("changesVersion", VERSION);
					} else
						sentToAll = false;
				}
				if (!IS_DEV && sentToAll)
					TwitterHelper.sendTweet(CHANGELOG);
			}
		};
		changesThread.start();

		LuaUtils.registerConverters();
		dispatcher.registerListener(new LuaEventHandler());
		COMMAND_HANDLER.registerCommandHandler(new LuaCommandHandler());

		FULLY_STARTED = true;
		// STATUS = EnumBotStatus.ONLINE;
		DebugServer.update();

		if (IS_DEV) {
			new Thread("wikiGen") {

				@Override
				public void run() {
					WikiGenerator.generate();
				}
			}.start();
		}

		Thread discord4JWatchdog = new Thread("D4JWatchdog") {

			private boolean isReady = CLIENT.isReady();

			@Override
			public void run() {
				while (true) {
					boolean inReady = CLIENT.isReady();
					if (!isReady && !inReady)
						System.exit(1);
					isReady = inReady;
					try {
						Thread.sleep(60000); // 1 min
					} catch (InterruptedException e) {}
				}
			}
		};
		discord4JWatchdog.start();

		updateOwners();
	}

	public static class BaseModules {

		private static boolean registered = false;

		public static final StarotaModule PROFILES = new StarotaModule("PlayerProfiles", "Profiles")
				.setDescription(
						"Player profiles are a feature of Starota that allows players to easily share some "
								+ "information about themselves with the community.  "
								+ "Profiles can show such features as your Pokemon Go username, level, and team, "
								+ "as well as optionally your real name, trainer code, and alternate accounts.\n\n"
								+ "Your profile will be default show your team logo, but if you also have a trainer "
								+ "card setup on [TheSilphRoad.com](http://thesilphroad.com/), it will instead show "
								+ "your avatar from your card and a link to your card.  "
								+ "If you are a patron of Starota, it will also show your patron tier.\n\n"
								+ "![Player Profile Sample](http://assets.myles-selim.us/starota/wiki/player_profile.png)");
		public static final StarotaModule GROUPS = new StarotaModule("Groups", "Groups").setDescription(
				"The groups feature lets server owners specify roles that users can \"self-assign\" to "
						+ "themselves and leave at will.");
		public static final StarotaModule TRADEBOARD = new StarotaModule("Tradeboard", "Tradeboard",
				PROFILES).setDescription(
						"The tradeboard allows users to list Pokemon they are either looking for or would like "
								+ "to get in a trade.  When posting a trade, you can specify if the Pokemon is shiny "
								+ "(if released), has legacy moves, or a specific form (Kanotian vs Alolan, sunny vs "
								+ "rainy vs snowy vs normal Castform, etc) as well as genders.\n\n"
								+ "If the poster of the trade has their trainer code on their profile, it will also be displayed on the trade.\n\n"
								+ "![Trade](http://assets.myles-selim.us/starota/wiki/trade.png)");
		public static final StarotaModule LUA = new StarotaModule("Lua", "Lua").setDescription(
				"The Lua module allows server admins to create custom commands and interactions on thier "
						+ "Discord server.  Using `.uploadScript` you can upload both scripts to be "
						+ "executed when a user executes a command as well as a \"event handler\" script "
						+ "that are executed when Discord4J events are fired.\n\n"
						+ "See [luaExamples](https://github.com/Selim042/Starota/tree/master/luaExamples) "
						+ "for some examples and more information at PR [#1](https://github.com/Selim042/Starota/pull/1).");
		public static final StarotaModule LEADERBOARDS = new StarotaModule("Leaderboards", "Leaderboard",
				PROFILES).setDescription(
						"Leaderboards allow server owners to create custom leaderboards that their users "
								+ "can compete on for various things like total XP, level, Pokedex completion, "
								+ "pretty much anything that can be tracked by a number, either incrementing or "
								+ "decrementing.\n\n"
								+ "![Leaderboard](http://assets.myles-selim.us/starota/wiki/leaderboard.png)\n\n"
								+ "![Leaderboard Options](http://assets.myles-selim.us/starota/wiki/leaderboard_options.png)");
		public static final StarotaModule PVP = new StarotaModule("PvP", "PvP", PROFILES).setDescription(
				"Starota has commands that helps coordinate PvP battles by allowing users to "
						+ "list themselves as \"battle ready\".");
		public static final StarotaModule POKEDEX = new StarotaModule("Pokedex", "Pokedex");
		public static final StarotaModule SILPH_ROAD = new StarotaModule("SilphRoad", "Silph Road");
		public static final StarotaModule HTTP = new StarotaModule("HTTP");

		public static void registerModules() {
			if (registered)
				return;
			registered = true;

			StarotaModule.registerModule(PROFILES);
			StarotaModule.registerModule(GROUPS);
			StarotaModule.registerModule(TRADEBOARD);
			StarotaModule.registerModule(LUA);
			StarotaModule.registerModule(LEADERBOARDS);
			StarotaModule.registerModule(PVP);
			StarotaModule.registerModule(POKEDEX);
			StarotaModule.registerModule(SILPH_ROAD);
			StarotaModule.registerModule(HTTP);
		}

	}

	public static IDiscordClient getClient() {
		return CLIENT;
	}

	public static DiscordBotListAPI getBotListAPI() {
		if (BOT_LIST == null && PROPERTIES.containsKey("bot_list_token"))
			BOT_LIST = new DiscordBotListAPI.Builder().token(PROPERTIES.getProperty("bot_list_token"))
					.botId(CLIENT.getOurUser().getStringID()).build();
		return BOT_LIST;
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
		if (IS_DEV)
			e.printStackTrace();
		if (IS_DEV || CLIENT == null || !CLIENT.isReady())
			return;
		long reportChannelId = 522805019326677002L;
		IChannel reportChannel = CLIENT.getChannelByID(reportChannelId);
		if (reportChannel == null)
			return;
		EmbedBuilder builder = new EmbedBuilder();
		builder.withTitle("An " + e.getClass().getSimpleName() + " has been thrown"
				+ (message == null ? "" : ": " + message));
		String body = e.getClass().getName() + ": " + e.getLocalizedMessage();
		for (StackTraceElement t : e.getStackTrace()) {
			String line = t.toString();
			if (body.length() + line.length() > 2048)
				break;
			body += line + "\n";
		}
		builder.appendDesc(body);
		RequestBuffer.request(() -> reportChannel.sendMessage(builder.build()));
	}

	public static void submitStats() {
		try {
			getBotListAPI();
			if (!IS_DEV) {
				System.out.println("Submitting shard info to the bot list");
				List<Integer> shards = new ArrayList<>();
				for (IShard s : CLIENT.getShards())
					shards.add(s.getGuilds().size());
				BOT_LIST.setStats(shards).whenComplete((v, e) -> {
					if (e != null)
						e.printStackTrace();
					else
						System.out.println("Starota Submitted");
				});
				if (PokedexBot.POKEDEX_CLIENT != null) {
					shards.clear();
					for (IShard s : PokedexBot.POKEDEX_CLIENT.getShards())
						shards.add(s.getGuilds().size());
					PokedexBot.getBotListAPI().setStats(shards).whenComplete((v, e) -> {
						if (e != null)
							e.printStackTrace();
						else
							System.out.println("Pokedex Submitted");
					});
				}
			} else
				System.out.println("BOT LIST TOKEN NOT FOUND");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void updateOwners() {
		if (IS_DEV)
			return;
		IRole ownerRole = CLIENT.getRoleByID(539645716583284776L);
		List<IUser> currentOwners = new ArrayList<>();
		for (IGuild g : CLIENT.getGuilds()) {
			IUser owner = getSupportServer().getUserByID(g.getOwnerLongID());
			if (owner == null)
				continue;
			if (!owner.hasRole(ownerRole))
				RequestBuffer.request(() -> owner.addRole(ownerRole));
			currentOwners.add(owner);
		}
		for (IUser u : getSupportServer().getUsersByRole(ownerRole))
			if (!currentOwners.contains(u))
				u.removeRole(ownerRole);
	}

}
