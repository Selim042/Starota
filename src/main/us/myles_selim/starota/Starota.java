package us.myles_selim.starota;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Timer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Predicate;

import org.discordbots.api.client.DiscordBotListAPI;

import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.IShard;
import sx.blah.discord.api.events.EventDispatcher;
import sx.blah.discord.api.internal.json.objects.EmbedObject;
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
import us.myles_selim.starota.assistants.CommandBots;
import us.myles_selim.starota.assistants.points.PointBot;
import us.myles_selim.starota.assistants.pokedex.PokedexBot;
import us.myles_selim.starota.assistants.registration.RegistrationBot;
import us.myles_selim.starota.commands.CommandArticleMessage;
import us.myles_selim.starota.commands.CommandChangelog;
import us.myles_selim.starota.commands.CommandCredits;
import us.myles_selim.starota.commands.CommandGenerateCommandWiki;
import us.myles_selim.starota.commands.CommandGetTimezones;
import us.myles_selim.starota.commands.CommandInvite;
import us.myles_selim.starota.commands.CommandPing;
import us.myles_selim.starota.commands.CommandStatus;
import us.myles_selim.starota.commands.CommandSupportBot;
import us.myles_selim.starota.commands.CommandTest;
import us.myles_selim.starota.commands.CommandVote;
import us.myles_selim.starota.commands.registry.PrimaryCommandHandler;
import us.myles_selim.starota.commands.registry.java.JavaCommandHandler;
import us.myles_selim.starota.commands.selim_pm.SelimPMCommandHandler;
import us.myles_selim.starota.commands.settings.CommandSettings;
import us.myles_selim.starota.commands.settings.types.SettingBoolean;
import us.myles_selim.starota.commands.settings.types.SettingChannel;
import us.myles_selim.starota.commands.settings.types.SettingTimeZone;
import us.myles_selim.starota.commands.tutorial.commands.CommandTutorial;
import us.myles_selim.starota.debug_server.DebugServer;
import us.myles_selim.starota.leaderboards.commands.CommandEditLeaderboard;
import us.myles_selim.starota.leaderboards.commands.CommandGetLeaderboard;
import us.myles_selim.starota.leaderboards.commands.CommandListLeaderboards;
import us.myles_selim.starota.leaderboards.commands.CommandNewLeaderboard;
import us.myles_selim.starota.leaderboards.commands.CommandUpdateLeaderboard;
import us.myles_selim.starota.leek_duck.ditto.CommandDitto;
import us.myles_selim.starota.leek_duck.events.CommandEvents;
import us.myles_selim.starota.lua.LuaEventHandler;
import us.myles_selim.starota.lua.LuaUtils;
import us.myles_selim.starota.lua.commands.CommandUploadScript;
import us.myles_selim.starota.lua.commands.LuaCommandHandler;
import us.myles_selim.starota.misc.data_types.BotServer;
import us.myles_selim.starota.misc.data_types.NullChannel;
import us.myles_selim.starota.misc.utils.MiscUtils;
import us.myles_selim.starota.misc.utils.StarotaConstants;
import us.myles_selim.starota.misc.utils.StatusUpdater;
import us.myles_selim.starota.misc.utils.StatusUpdater.PresenceData;
import us.myles_selim.starota.misc.utils.TwitterHelper;
import us.myles_selim.starota.misc.utils.WikiGenerator;
import us.myles_selim.starota.modules.BaseModules;
import us.myles_selim.starota.modules.CommandModules;
import us.myles_selim.starota.pokedex.CommandPokedex;
import us.myles_selim.starota.profiles.commands.CommandGetProfilelessPlayers;
import us.myles_selim.starota.profiles.commands.CommandProfile;
import us.myles_selim.starota.profiles.commands.CommandProfileHelp;
import us.myles_selim.starota.profiles.commands.CommandRegister;
import us.myles_selim.starota.profiles.commands.CommandSelfRegister;
import us.myles_selim.starota.profiles.commands.CommandUpdateProfile;
import us.myles_selim.starota.pvp.CommandBattleReady;
import us.myles_selim.starota.pvp.CommandFindBattles;
import us.myles_selim.starota.pvp.CommandNotReady;
import us.myles_selim.starota.raids.CommandRaid;
import us.myles_selim.starota.raids.CommandRaidBosses;
import us.myles_selim.starota.raids.CommandSetRaidEChannel;
import us.myles_selim.starota.reaction_messages.ReactionMessageRegistry;
import us.myles_selim.starota.role_management.commands.CommandAddGroup;
import us.myles_selim.starota.role_management.commands.CommandGetGroups;
import us.myles_selim.starota.role_management.commands.CommandRemoveGroup;
import us.myles_selim.starota.role_management.commands.CommandSetAsGroup;
import us.myles_selim.starota.search.events.CommandSearchEvents;
import us.myles_selim.starota.search.events.EventOperators;
import us.myles_selim.starota.search.pokemon.CommandSearchPoke;
import us.myles_selim.starota.search.pokemon.PokemonOperators;
import us.myles_selim.starota.silph_road.CommandSilphCard;
import us.myles_selim.starota.silph_road.eggs.CommandEggHatches;
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
import us.myles_selim.starota.wrappers.StarotaServer;

public class Starota {

	private static IDiscordClient CLIENT;
	private static DiscordBotListAPI BOT_LIST;
	// private static Socket MANAGER_SOCKET;
	private static final Properties PROPERTIES = new Properties();

	public final static boolean DEBUG = false;
	public static boolean IS_DEV;
	public static boolean FULLY_STARTED = false;
	public final static String BOT_NAME = "Starota";
	public final static String CHANGELOG = "Changelog for v" + StarotaConstants.VERSION + "\n"
			+ "Public changes:\n" + " * Fix timezone/setting error";
	public final static File DATA_FOLDER = new File("starotaData");

	public static PrimaryCommandHandler COMMAND_HANDLER;
	public static ReactionMessageRegistry REACTION_MESSAGES_REGISTRY;
	public static final ExecutorService EXECUTOR = Executors.newCachedThreadPool();

	public static void main(String[] args) {
		// register shutdown stuff
		Runtime.getRuntime().addShutdownHook(new Thread() {

			@Override
			public void run() {
				System.out.println("running shutdown thread");
				EXECUTOR.shutdown();
			}
		});

		try {
			try {
				PROPERTIES.load(new FileInputStream("starota.properties"));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			ClientBuilder clientBuilder = new ClientBuilder();
			clientBuilder.withToken(PROPERTIES.getProperty("token"));
			IS_DEV = Boolean.parseBoolean(PROPERTIES.getProperty("is_dev"));
			CLIENT = null;
			try {
				CLIENT = clientBuilder.login();
				CLIENT.changePresence(StatusType.INVISIBLE);
			} catch (DiscordException e) {
				e.printStackTrace();
			}
			if (CLIENT == null) {
				System.err.println("Failed to login, exiting");
				return;
			}
			EventDispatcher dispatcher = CLIENT.getDispatcher();
			try {
				while (!CLIENT.isReady())
					Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			BotServer.registerServerType(CLIENT, StarotaServer.class);

			// default settings, do this before anything else with StarotaServer
			StarotaServer
					.setDefaultValue(new SettingChannel(null, StarotaConstants.Settings.CHANGES_CHANNEL,
							"Channel where " + BOT_NAME + " prints out changelogs each update."));
			StarotaServer.setDefaultValue(new SettingChannel(null,
					StarotaConstants.Settings.NEWS_CHANNEL,
					"Channel where " + BOT_NAME + " prints out news articles when they are published."));
			StarotaServer.setDefaultValue(new SettingBoolean(StarotaConstants.Settings.PROFILE_NICKNAME,
					"Sets nickname to PoGo username when profile is made.", true));
			StarotaServer.setDefaultValue(new SettingTimeZone(StarotaConstants.Settings.TIMEZONE,
					"Sets the server timezone.  For options, use `getTimezones`.", null));

			// default leaderboards
			StarotaServer.registerDefaultLeaderboards();

			// load all StarotaServer data
			for (IGuild g : CLIENT.getGuilds())
				StarotaServer.getServer(g);

			COMMAND_HANDLER = new PrimaryCommandHandler(CLIENT);
			REACTION_MESSAGES_REGISTRY = new ReactionMessageRegistry(CLIENT);

			BaseModules.registerModules();
			// DebugServer debug = new DebugServer();
			// debug.start();
			DebugServer.init();

			System.out.println("registering commands");

			JavaCommandHandler jCmdHandler = new JavaCommandHandler(CLIENT);
			COMMAND_HANDLER.registerCommandHandler(jCmdHandler);
			registerCommands(jCmdHandler);

			SelimPMCommandHandler.init();

			try {
				Thread.sleep(2500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("starting threads and loading settings...");

			dispatcher.registerListener(COMMAND_HANDLER);
			dispatcher.registerListener(REACTION_MESSAGES_REGISTRY);
			dispatcher.registerListener(new EventHandler());
			// ReactionMessageRegistry.init();
			WebServer.init();
			PokedexBot.start();
			RegistrationBot.start();
			// PointBot.start();
			submitStats();

			try {
				Thread.sleep(2500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("v" + StarotaConstants.VERSION + (DEBUG || IS_DEV ? "d" : ""));
			StatusUpdater statusUpdater = new StatusUpdater(CLIENT);
			statusUpdater.addPresence(new PresenceData(StatusType.ONLINE, ActivityType.PLAYING,
					"v" + StarotaConstants.VERSION + (DEBUG || IS_DEV ? "d" : "")));
			statusUpdater.addPresence(new PresenceData(StatusType.ONLINE, ActivityType.WATCHING,
					"people organize raids with `raid`"));
			statusUpdater.addPresence(new PresenceData(StatusType.ONLINE, ActivityType.LISTENING,
					"to people search for events and Pokemon"));
			statusUpdater.addPresence(new PresenceData(StatusType.ONLINE, ActivityType.PLAYING,
					"with the new event system"));
			statusUpdater.addPresence(
					new PresenceData(StatusType.ONLINE, ActivityType.WATCHING, ".bots for new bots"));
			statusUpdater.start();

			EXECUTOR.execute(new Runnable() {

				@Override
				public void run() {
					boolean sentToAll = true;
					List<IGuild> guilds = new ArrayList<>(CLIENT.getGuilds());
					if (!IS_DEV)
						guilds.addAll(PokedexBot.CLIENT.getGuilds());
					for (IGuild g : guilds) {
						StarotaServer server = StarotaServer.getServer(g);
						IChannel changesChannel = server
								.getSetting(StarotaConstants.Settings.CHANGES_CHANNEL);
						if (changesChannel.equals(NullChannel.NULL_CHANNEL))
							continue;
						String latestChangelog = (String) server.getDataValue("changesVersion");
						if (!StarotaConstants.VERSION.equalsIgnoreCase(latestChangelog)) {
							RequestBuffer.request(
									() -> changesChannel.sendMessage("```" + CHANGELOG + "```"));
							server.setDataValue("changesVersion", StarotaConstants.VERSION);
						} else
							sentToAll = false;
					}
					if (!IS_DEV && sentToAll)
						TwitterHelper.sendTweet(CHANGELOG);
				}
			});

			LuaUtils.registerConverters();
			dispatcher.registerListener(new LuaEventHandler());
			COMMAND_HANDLER.registerCommandHandler(new LuaCommandHandler(CLIENT));

			FULLY_STARTED = true;
			DebugServer.update();

			if (IS_DEV) {
				EXECUTOR.execute(new Runnable() {

					@Override
					public void run() {
						WikiGenerator.generate();
					}
				});
			}

			// register search operators
			PokemonOperators.registerOperators();
			EventOperators.registerOperators();

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

			// hacky, weird thing just to hide the deprecation warning
			@SuppressWarnings("deprecation")
			Predicate<Void> f = (v) -> {
				Timer timer = new Timer();
				Date midnight = new Date();
				midnight.setHours(0);
				midnight.setMinutes(0);
				timer.scheduleAtFixedRate(new VoteReminderThread(), midnight, (long) 2.592e+8);
				return false;
			};
			f.test(null);
		} catch (Exception e) {
			System.err.println("+-------------------------------------------------------------------+");
			System.err.println("| Starota failed to start properly. Printing exception then exiting |");
			System.err.println("+-------------------------------------------------------------------+");
			e.printStackTrace();
			Runtime.getRuntime().exit(0);
		}
	}

	private static void registerCommands(JavaCommandHandler jCmdHandler) {
		jCmdHandler.registerDefaultCommands();

		jCmdHandler.registerCommand(new CommandChangelog());
		jCmdHandler.registerCommand(new CommandCredits());
		jCmdHandler
				.registerCommand(new CommandSupportBot(Starota.BOT_NAME, StarotaConstants.STAROTA_ID));
		jCmdHandler.registerCommand(new CommandInvite(Starota.BOT_NAME, StarotaConstants.STAROTA_ID,
				Permissions.generatePermissionsNumber(DebugServer.getUsedPermissions())));
		jCmdHandler.registerCommand(new CommandPing());
		jCmdHandler.registerCommand(new CommandVote(Starota.BOT_NAME, StarotaConstants.STAROTA_ID));

		jCmdHandler.registerCommand("Administrative", new CommandStatus());
		jCmdHandler.registerCommand("Administrative", new CommandVotePerks());
		jCmdHandler.registerCommand("Administrative", new CommandSettings());
		jCmdHandler.registerCommand("Administrative", new CommandGetTimezones());
		if (IS_DEV) {
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

		jCmdHandler.registerCommand("Raids", new CommandRaid());
		jCmdHandler.registerCommand("Raids", new CommandSetRaidEChannel());
		jCmdHandler.registerCommand("Raids", new CommandRaidBosses());

		jCmdHandler.registerCommand("Search", new CommandSearchPoke());
		jCmdHandler.registerCommand("Search", new CommandSearchEvents());

		jCmdHandler.registerCommand("Misc", new CommandSilphCard());
		jCmdHandler.registerCommand("Misc", new CommandEvents());
		jCmdHandler.registerCommand("Misc", new CommandEggHatches());
		jCmdHandler.registerCommand("Misc", new CommandDitto());
		jCmdHandler.registerCommand("Misc", new CommandArticleMessage());
		jCmdHandler.registerCommand("Misc", new CommandBots());

		jCmdHandler.registerCommand("Tutorial", new CommandTutorial());
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
				if (u.getName().equalsIgnoreCase(user) || u.getDisplayName(server).equalsIgnoreCase(user)
						|| (nickname != null && nickname.equalsIgnoreCase(user)))
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
		return getGuild(StarotaConstants.SUPPORT_SERVER);
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
				if (PokedexBot.CLIENT != null) {
					shards.clear();
					for (IShard s : PokedexBot.CLIENT.getShards())
						shards.add(s.getGuilds().size());
					PokedexBot.getBotListAPI().setStats(shards).whenComplete((v, e) -> {
						if (e != null)
							e.printStackTrace();
						else
							System.out.println("Pokedex Submitted");
					});
				}
				if (RegistrationBot.CLIENT != null) {
					shards.clear();
					for (IShard s : RegistrationBot.CLIENT.getShards())
						shards.add(s.getGuilds().size());
					RegistrationBot.getBotListAPI().setStats(shards).whenComplete((v, e) -> {
						if (e != null)
							e.printStackTrace();
						else
							System.out.println("Pokedex Submitted");
					});
				}
				if (PointBot.CLIENT != null) {
					shards.clear();
					for (IShard s : PointBot.CLIENT.getShards())
						shards.add(s.getGuilds().size());
					PointBot.getBotListAPI().setStats(shards).whenComplete((v, e) -> {
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

	private static final Long[] SKIPPED_SERVERS = new Long[] { //
			408997776672948224L, // Bot Emojis
			264445053596991498L, // Discord Bot List
	};

	public static void sendOwnersMessage(String msg, IUser author) {
		sendOwnersMessage(msg, null, author);
	}

	public static void sendOwnersMessage(EmbedObject embed, IUser author) {
		sendOwnersMessage(null, embed, author);
	}

	public static void sendOwnersMessage(String msg, EmbedObject embed, IUser author) {
		EXECUTOR.execute(new Runnable() {

			@Override
			public void run() {
				final EmbedObject fEmbed = embed == null || embed.equals(new EmbedObject()) ? null
						: embed;
				List<Long> alreadySent = new ArrayList<>();

				if (IS_DEV) {
					// testing channel on bot test server
					IChannel ch = CLIENT.getChannelByID(504089729143406595L);
					if ((msg == null || msg.isEmpty()) && fEmbed != null)
						RequestBuffer.request(() -> ch.sendMessage(fEmbed)).get();
					else if ((msg != null && !msg.isEmpty()) && fEmbed == null)
						RequestBuffer.request(() -> ch.sendMessage(msg)).get();
					else if ((msg != null && !msg.isEmpty()) && fEmbed != null)
						RequestBuffer.request(() -> ch.sendMessage(msg, fEmbed)).get();
					RequestBuffer.request(() -> ch.sendMessage(getAuthorEmbed(author)));
				}

				for (IGuild g : CLIENT.getGuilds()) {
					if (MiscUtils.arrContains(SKIPPED_SERVERS, g.getLongID()))
						continue;
					IUser owner = g.getOwner();
					if (author.getLongID() == owner.getLongID()
							|| alreadySent.contains(owner.getLongID()))
						continue;
					alreadySent.add(owner.getLongID());
					if ((msg == null || msg.isEmpty()) && fEmbed != null)
						RequestBuffer.request(() -> owner.getOrCreatePMChannel().sendMessage(fEmbed))
								.get();
					else if ((msg != null && !msg.isEmpty()) && fEmbed == null)
						RequestBuffer.request(() -> owner.getOrCreatePMChannel().sendMessage(msg)).get();
					else if ((msg != null && !msg.isEmpty()) && fEmbed != null)
						RequestBuffer
								.request(() -> owner.getOrCreatePMChannel().sendMessage(msg, fEmbed))
								.get();
					RequestBuffer.request(
							() -> owner.getOrCreatePMChannel().sendMessage(getAuthorEmbed(author)));
				}
			}
		});
	}

	public static void sendArticle(String msg, IUser author) {
		sendOwnersMessage(msg, null, author);
	}

	public static void sendArticle(EmbedObject embed, IUser author) {
		sendOwnersMessage(null, embed, author);
	}

	public static void sendArticle(String msg, EmbedObject embed, IUser author) {
		EXECUTOR.execute(new Runnable() {

			@Override
			public void run() {
				final EmbedObject fEmbed = embed == null || embed.equals(new EmbedObject()) ? null
						: embed;
				for (IGuild g : CLIENT.getGuilds()) {
					if (MiscUtils.arrContains(SKIPPED_SERVERS, g.getLongID()))
						continue;
					StarotaServer server = StarotaServer.getServer(g);
					IChannel ch = server.getSetting(StarotaConstants.Settings.NEWS_CHANNEL);
					if (ch == null)
						continue;
					if ((msg == null || msg.isEmpty()) && fEmbed != null)
						RequestBuffer.request(() -> ch.sendMessage(fEmbed)).get();
					else if ((msg != null && !msg.isEmpty()) && fEmbed == null)
						RequestBuffer.request(() -> ch.sendMessage(msg)).get();
					else if ((msg != null && !msg.isEmpty()) && fEmbed != null)
						RequestBuffer.request(() -> ch.sendMessage(msg, fEmbed)).get();
					RequestBuffer.request(() -> ch.sendMessage(getAuthorEmbed(author)));
				}
			}
		});
	}

	private static EmbedObject getAuthorEmbed(IUser author) {
		EmbedBuilder builder = new EmbedBuilder();
		builder.withFooterIcon(author.getAvatarURL()).withFooterText(author.getName());
		builder.appendDesc("Sent by:");
		return builder.build();
	}

}
