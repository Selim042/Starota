package us.myles_selim.starota;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Timer;

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
import sx.blah.discord.handle.obj.Permissions;
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
import us.myles_selim.starota.commands.CommandSupportBot;
import us.myles_selim.starota.commands.CommandTest;
import us.myles_selim.starota.commands.pvp.CommandBattleReady;
import us.myles_selim.starota.commands.pvp.CommandFindBattles;
import us.myles_selim.starota.commands.pvp.CommandNotReady;
import us.myles_selim.starota.commands.registry.PrimaryCommandHandler;
import us.myles_selim.starota.commands.registry.java.JavaCommandHandler;
import us.myles_selim.starota.debug_server.DebugServer;
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
import us.myles_selim.starota.modules.BaseModules;
import us.myles_selim.starota.modules.CommandModules;
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
import us.myles_selim.starota.research.ResearchTracker;
import us.myles_selim.starota.role_management.commands.CommandAddGroup;
import us.myles_selim.starota.role_management.commands.CommandGetGroups;
import us.myles_selim.starota.role_management.commands.CommandRemoveGroup;
import us.myles_selim.starota.role_management.commands.CommandSetAsGroup;
import us.myles_selim.starota.search.CommandSearchPoke;
import us.myles_selim.starota.search.PokemonOperators;
import us.myles_selim.starota.silph_road.CommandSilphCard;
import us.myles_selim.starota.trading.commands.CommandFindTrade;
import us.myles_selim.starota.trading.commands.CommandForTrade;
import us.myles_selim.starota.trading.commands.CommandGetForms;
import us.myles_selim.starota.trading.commands.CommandGetShinies;
import us.myles_selim.starota.trading.commands.CommandGetTrade;
import us.myles_selim.starota.trading.commands.CommandGetUserTrades;
import us.myles_selim.starota.trading.commands.CommandLookingFor;
import us.myles_selim.starota.trading.commands.CommandRemoveTrade;
import us.myles_selim.starota.trading.commands.CommandTradeboardHelp;
import us.myles_selim.starota.vote_rewards.CommandVotePerks;
import us.myles_selim.starota.vote_rewards.VoteReminderThread;
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
	public final static String VERSION = "2.7.14";
	public final static String CHANGELOG = "Changelog for v" + VERSION + "\n" + "Public changes:\n"
			+ " * Hopefully fix another bug with updateProfile";
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
		jCmdHandler.registerCommand(new CommandSupportBot(Starota.BOT_NAME, Starota.STAROTA_ID));
		jCmdHandler.registerCommand(new CommandInvite(Starota.BOT_NAME,
				Permissions.generatePermissionsNumber(DebugServer.USED_PERMISSIONS)));

		jCmdHandler.registerCommand("Administrative", new CommandStatus());
		// jCmdHandler.registerCommand("Administrative", new
		// CommandSetResearchChannel());
		jCmdHandler.registerCommand("Administrative", new CommandChangelogChannel());
		jCmdHandler.registerCommand("Administrative", new CommandInviteAssistants());
		jCmdHandler.registerCommand("Administrative", new CommandVotePerks());
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

		jCmdHandler.registerCommand("Search", new CommandSearchPoke());

		try {
			Thread.sleep(2500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		CLIENT.changePresence(StatusType.IDLE, ActivityType.PLAYING,
				"starting threads and loading settings...");

		// FormManager.init();
		// ResearchTracker.init();

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
				List<IGuild> guilds = new ArrayList<>(CLIENT.getGuilds());
				if (!IS_DEV)
					guilds.addAll(PokedexBot.POKEDEX_CLIENT.getGuilds());
				for (IGuild g : guilds) {
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

		// register search operators for Pokemon
		PokemonOperators.registerOperators();

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

		Timer timer = new Timer();
		Date midnight = new Date();
		midnight.setHours(0);
		midnight.setMinutes(0);
		timer.scheduleAtFixedRate(new VoteReminderThread(), midnight, (long) 2.592e+8);
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
			IGuild guild = Starota.getGuild(serverId);
			if (guild != null) {
				IUser userD = guild.getUserByID(userId);
				if (userD != null)
					return userD;
			}
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
