package us.myles_selim.starota;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Timer;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Predicate;

import org.discordbots.api.client.DiscordBotListAPI;

import discord4j.core.DiscordClient;
import discord4j.core.DiscordClientBuilder;
import discord4j.core.event.EventDispatcher;
import discord4j.core.object.entity.Channel;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.GuildChannel;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.TextChannel;
import discord4j.core.object.entity.User;
import discord4j.core.object.presence.Activity;
import discord4j.core.object.presence.Presence;
import discord4j.core.object.util.Snowflake;
import discord4j.core.spec.EmbedCreateSpec;
import us.myles_selim.starota.assistants.CommandBots;
import us.myles_selim.starota.assistants.registration.RegistrationBot;
import us.myles_selim.starota.commands.CommandArticleMessage;
import us.myles_selim.starota.commands.CommandChangelog;
import us.myles_selim.starota.commands.CommandGetTimezones;
import us.myles_selim.starota.commands.CommandInvite;
import us.myles_selim.starota.commands.CommandPing;
import us.myles_selim.starota.commands.CommandRegionals;
import us.myles_selim.starota.commands.CommandStatus;
import us.myles_selim.starota.commands.CommandSupportBot;
import us.myles_selim.starota.commands.CommandTest;
import us.myles_selim.starota.commands.CommandVote;
import us.myles_selim.starota.commands.catchers_cup.CommandCatcherCup;
import us.myles_selim.starota.commands.catchers_cup.CommandCatcherCupAddPoints;
import us.myles_selim.starota.commands.catchers_cup.CommandCreateCupChannel;
import us.myles_selim.starota.commands.credits.CommandCredits;
import us.myles_selim.starota.commands.credits.Credit;
import us.myles_selim.starota.commands.credits.CreditSet;
import us.myles_selim.starota.commands.credits.Creditable;
import us.myles_selim.starota.commands.credits.EnumCreditType;
import us.myles_selim.starota.commands.registry.PrimaryCommandHandler;
import us.myles_selim.starota.commands.registry.java.JavaCommandHandler;
import us.myles_selim.starota.commands.selim_pm.SelimPMCommandHandler;
import us.myles_selim.starota.commands.settings.CommandSettings;
import us.myles_selim.starota.commands.settings.types.SettingBoolean;
import us.myles_selim.starota.commands.settings.types.SettingChannelStarota;
import us.myles_selim.starota.commands.settings.types.SettingString;
import us.myles_selim.starota.commands.settings.types.SettingTimeZone;
import us.myles_selim.starota.debug_server.DebugServer;
import us.myles_selim.starota.enums.EnumTeam;
import us.myles_selim.starota.events.CommandEvents;
import us.myles_selim.starota.github.GitHubAPI;
import us.myles_selim.starota.github.GitHubContributor;
import us.myles_selim.starota.leaderboards.commands.CommandEditLeaderboard;
import us.myles_selim.starota.leaderboards.commands.CommandGetLeaderboard;
import us.myles_selim.starota.leaderboards.commands.CommandListLeaderboards;
import us.myles_selim.starota.leaderboards.commands.CommandNewLeaderboard;
import us.myles_selim.starota.leaderboards.commands.CommandUpdateLeaderboard;
import us.myles_selim.starota.leek_duck.ditto.CommandDitto;
import us.myles_selim.starota.link_shortener.YourLSAPI;
import us.myles_selim.starota.misc.data_types.Pair;
import us.myles_selim.starota.misc.utils.EmbedBuilder;
import us.myles_selim.starota.misc.utils.IndexHolder;
import us.myles_selim.starota.misc.utils.MiscUtils;
import us.myles_selim.starota.misc.utils.StarotaConstants;
import us.myles_selim.starota.misc.utils.StatusUpdater;
import us.myles_selim.starota.misc.utils.TwitterHelper;
import us.myles_selim.starota.misc.utils.WikiGenerator;
import us.myles_selim.starota.modules.BaseModules;
import us.myles_selim.starota.modules.CommandModules;
import us.myles_selim.starota.pokedex.CommandCPTable;
import us.myles_selim.starota.pokedex.CommandPokedex;
import us.myles_selim.starota.profiles.PlayerProfile;
import us.myles_selim.starota.profiles.commands.CommandGetProfilelessPlayers;
import us.myles_selim.starota.profiles.commands.CommandProfile;
import us.myles_selim.starota.profiles.commands.CommandProfileHelp;
import us.myles_selim.starota.profiles.commands.CommandRegister;
import us.myles_selim.starota.profiles.commands.CommandSelfRegister;
import us.myles_selim.starota.profiles.commands.CommandUpdateProfile;
import us.myles_selim.starota.pvp.battle_discovery.CommandBattleReady;
import us.myles_selim.starota.pvp.battle_discovery.CommandFindBattles;
import us.myles_selim.starota.pvp.battle_discovery.CommandNotReady;
import us.myles_selim.starota.pvp.pokebattler.commands.CommandCupCounters;
import us.myles_selim.starota.raids.CommandRaid;
import us.myles_selim.starota.raids.CommandRaidBosses;
import us.myles_selim.starota.raids.CommandSetRaidEChannel;
import us.myles_selim.starota.raids.raid_train.CommandRaidTrain;
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
import us.myles_selim.starota.trading.TradeboardPost;
import us.myles_selim.starota.trading.commands.CommandFindTrade;
import us.myles_selim.starota.trading.commands.CommandForTrade;
import us.myles_selim.starota.trading.commands.CommandGetTrade;
import us.myles_selim.starota.trading.commands.CommandGetUserTrades;
import us.myles_selim.starota.trading.commands.CommandLookingFor;
import us.myles_selim.starota.trading.commands.CommandRemoveTrade;
import us.myles_selim.starota.trading.commands.CommandTradeboardHelp;
import us.myles_selim.starota.vote_rewards.CommandVotePerks;
import us.myles_selim.starota.vote_rewards.VoteReminderThread;
import us.myles_selim.starota.weather.CommandCreateWeatherChannel;
import us.myles_selim.starota.weather.CommandWeather;
import us.myles_selim.starota.webserver.WebServer;
import us.myles_selim.starota.wrappers.BotServer;
import us.myles_selim.starota.wrappers.StarotaServer;

public class Starota {

	private static DiscordClient CLIENT;
	private static DiscordBotListAPI BOT_LIST;
	// private static Socket MANAGER_SOCKET;
	private static final Properties PROPERTIES = new Properties();

	public final static boolean DEBUG = false;
	public static boolean IS_DEV;
	public static boolean ENABLE_AUTO_REBOOT;
	public static boolean FULLY_STARTED = false;
	public final static String BOT_NAME = "Starota";
	public final static String CHANGELOG = "Changelog for v" + StarotaConstants.VERSION + "\n"
			+ "Public changes:\n" + " + Add vote notifications in Discord"
			+ "\n + Add weather and Catcher's Cup info channels"
			+ "\n + Add regional command and display where regional Pokemon can be caught in the Pokedex"
			+ "\n + Show Silph Arena stats in silph command" + "\n + Add raid train command/feature"
			+ "\n + Add command to see latest Silph Arena cup counters";
	public final static File DATA_FOLDER = new File("starotaData");

	public static PrimaryCommandHandler COMMAND_HANDLER;
	public static ReactionMessageRegistry REACTION_MESSAGES_REGISTRY;
	public static final ScheduledExecutorService EXECUTOR = Executors.newScheduledThreadPool(5);

	private static ScheduledFuture<Void> RESTART_TIMER;
	private static final Callable<Void> RESTART = new Callable<Void>() {

		@Override
		public Void call() throws Exception {
			CLIENT.getUserById(StarotaConstants.SELIM_USER_ID).block().getPrivateChannel().block()
					.createMessage("Restarting due to inactivity").block();
			if (!IS_DEV)
				Runtime.getRuntime().exec("start cmd /k java -jar botManager/bots/Starota.jar", null,
						new File(System.getProperty("user.dir")));
			else
				Runtime.getRuntime().exec("start cmd /k java -jar Starota.jar", null,
						new File(System.getProperty("user.dir")));
			Runtime.getRuntime().exit(1);
			return null;
		}
	};

	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		// register shutdown stuff
		Runtime.getRuntime().addShutdownHook(new Thread() {

			@Override
			public void run() {
				System.out.println("[" + new Date() + "] running shutdown thread");
				if (CLIENT.isConnected()) {
					CLIENT.getUserById(StarotaConstants.SELIM_USER_ID).block().getPrivateChannel()
							.block().createMessage("Shutting down").block();
					CLIENT.logout().block();
				}
				EXECUTOR.shutdown();
			}
		});

		try {
			try {
				PROPERTIES.load(new FileInputStream("starota.properties"));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			DiscordClientBuilder clientBuilder = new DiscordClientBuilder(
					PROPERTIES.getProperty("token"));
			IS_DEV = Boolean.parseBoolean(PROPERTIES.getProperty("is_dev"));
			ENABLE_AUTO_REBOOT = Boolean.parseBoolean(PROPERTIES.getProperty("enable_auto_reboot"));
			YourLSAPI.setSignature(PROPERTIES.getProperty("yourls_signature"));
			CLIENT = clientBuilder.build();
			Thread botRun = new Thread() {

				@Override
				public void run() {
					CLIENT.login().block();
				}
			};
			botRun.start();
			CLIENT.updatePresence(Presence.invisible());
			if (CLIENT == null) {
				System.err.println("Failed to login, exiting");
				return;
			}
			EventDispatcher dispatcher = CLIENT.getEventDispatcher();
			BotServer.registerServerType(CLIENT, StarotaServer.class);

			// default settings, do this before anything else with StarotaServer
			StarotaServer.setDefaultValue(
					new SettingChannelStarota(null, StarotaConstants.Settings.CHANGES_CHANNEL,
							"Channel where " + BOT_NAME + " prints out changelogs each update."));
			StarotaServer.setDefaultValue(new SettingChannelStarota(null,
					StarotaConstants.Settings.NEWS_CHANNEL,
					"Channel where " + BOT_NAME + " prints out news articles when they are published."));
			StarotaServer.setDefaultValue(new SettingBoolean(StarotaConstants.Settings.PROFILE_NICKNAME,
					"Sets nickname to PoGo username when profile is made.", true));
			StarotaServer.setDefaultValue(new SettingTimeZone(StarotaConstants.Settings.TIMEZONE,
					"Sets the server timezone.  For options, use `getTimezones`.  "
							+ "Used if weather is not setup.",
					null));
			StarotaServer.setDefaultValue(new SettingString(StarotaConstants.Settings.WEATHER_API_TOKEN,
					"Sets the server's weather API token.", null));
			StarotaServer.setDefaultValue(new SettingString(StarotaConstants.Settings.COORDS,
					"Sets the community's coordinates.", null));
			// StarotaServer
			// .setDefaultValue(new
			// SettingBoolean(StarotaConstants.Settings.EASY_HOUSE_CUP_ENTRY,
			// "Allows users with the necessary permissions to distribute House
			// Cup "
			// + "points by saying things like \"10 points for instinct!\"."
			// + "**NOTE**: Not currently enabled.",
			// true));
			StarotaServer.setDefaultValue(new SettingBoolean(StarotaConstants.Settings.CLOCK_24H,
					"If true, uses 24 hour clock (1-24) rather than 12 hour clock.", false));

			// default leaderboards
			StarotaServer.registerDefaultLeaderboards();

			// load all StarotaServer data
			for (Guild g : CLIENT.getGuilds().collectList().block())
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
			System.out.println("starting threads and loading settings...");

			COMMAND_HANDLER.setup(dispatcher);
			REACTION_MESSAGES_REGISTRY.setup(dispatcher);
			new EventHandler().setup(dispatcher);
			// ReactionMessageRegistry.init();
			WebServer.init();
			if (!IS_DEV)
				RegistrationBot.start();
			// PointBot.start();
			submitStats();

			System.out.println("v" + StarotaConstants.VERSION + (DEBUG || IS_DEV ? "d" : ""));
			StatusUpdater statusUpdater = new StatusUpdater(CLIENT);
			statusUpdater.addPresence(() -> Presence.online(
					Activity.playing("v" + StarotaConstants.VERSION + (DEBUG || IS_DEV ? "d" : ""))));
			statusUpdater.addPresence(() -> Presence
					.online(Activity.watching("people organize raids with .raid and .raidTrain")));
			statusUpdater.addPresence(
					() -> Presence.online(Activity.listening("people search for events and Pokemon")));
			// statusUpdater.addPresence(Presence.online(Activity.watching(".bots
			// for new bots")));
			statusUpdater.addPresence(() -> Presence
					.online(Activity.watching("people research counters & regionals with .dex")));
			statusUpdater.addPresence(() -> Presence
					.online(Activity.listening(getHighestTeamCup() + ", global Catcher's Cup leader")));
			statusUpdater.start();

			if (!IS_DEV) {
				EXECUTOR.execute(new Runnable() {

					@Override
					public void run() {
						boolean sentToAll = !CLIENT.getGuilds().all((g) -> {
							StarotaServer server = StarotaServer.getServer(g);
							TextChannel changesChannel = server
									.getSetting(StarotaConstants.Settings.CHANGES_CHANNEL);
							if (changesChannel == null)
								return true;
							String latestChangelog = (String) server.getDataValue("changesVersion");
							if (!StarotaConstants.VERSION.equalsIgnoreCase(latestChangelog)) {
								changesChannel.createMessage("```" + CHANGELOG + "```").block();
								server.setDataValue("changesVersion", StarotaConstants.VERSION);
								return true;
							} else
								return false;
						}).block();
						if (!IS_DEV && sentToAll)
							TwitterHelper.sendTweet(CHANGELOG);
					}
				});
			}

			// LuaUtils.registerConverters();
			// new LuaEventHandler().setup(dispatcher);
			// COMMAND_HANDLER.registerCommandHandler(new
			// LuaCommandHandler(CLIENT));

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

				private boolean isReady = CLIENT.isConnected();

				@Override
				public void run() {
					while (true) {
						boolean inReady = CLIENT.isConnected();
						if (!isReady && !inReady)
							System.exit(1);
						isReady = inReady;
						try {
							Thread.sleep(60000); // 1 min
						} catch (InterruptedException e) { /* */ }
					}
				}
			};
			discord4JWatchdog.start();

			updateOwners();

			// hacky, weird thing just to hide the deprecation warning
			Predicate<Void> f = (v) -> {
				Timer timer = new Timer();
				Date midnight = new Date();
				midnight.setHours(0);
				midnight.setMinutes(0);
				timer.scheduleAtFixedRate(new VoteReminderThread(), midnight, (long) 2.592e+8);
				return false;
			};
			f.test(null);

			// start pulling weather
			EXECUTOR.scheduleAtFixedRate(() -> {
				try {
					CLIENT.getGuilds().doOnEach((g) -> {
						StarotaServer server = StarotaServer.getServer(g.get());
						if (server == null)
							return;
						server.updateWeather();
					}).collectList().block();
					// CLIENT.getUserById(StarotaConstants.SELIM_USER_ID).block().getPrivateChannel()
					// .block().createMessage("Updated weather").block();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}, 0, 1, TimeUnit.HOURS);

			// update weather info channel
			int minsUntilHour = 60 - LocalDateTime.now().getMinute();
			Runnable updateWeather = new Runnable() {

				@Override
				public void run() {
					// System.out.println("updating weather channels");
					CLIENT.getGuilds().doOnEach((g) -> {
						StarotaServer server = StarotaServer.getServer(g.get());
						if (server != null && server.isWeatherSetup() && server
								.hasInfoChannel(CommandCreateWeatherChannel.WEATHER_CHANNEL_KEY)) {
							// System.out.println("updating weather channel on
							// server: "
							// + server.getDiscordGuild().getName());
							CommandCreateWeatherChannel.updateChannelName(server);
						}
					}).onErrorContinue((e, o) -> e.printStackTrace()).collectList().block();
				}
			};
			updateWeather.run();
			// fix delay & interval
			EXECUTOR.scheduleAtFixedRate(updateWeather, minsUntilHour, 30, TimeUnit.MINUTES);

			// basic raid usage counts
			EXECUTOR.scheduleAtFixedRate(() -> {
				try {
					StringBuilder rates = new StringBuilder();
					if (RAID_USAGE.isEmpty())
						rates.append("no raid usage");
					else
						for (Entry<Snowflake, Integer> e : RAID_USAGE.entrySet())
							rates.append(String.format("%s: %d\n",
									CLIENT.getGuildById(e.getKey()).block().getName(), e.getValue()));
					CLIENT.getUserById(StarotaConstants.SELIM_USER_ID).block().getPrivateChannel()
							.block()
							.createEmbed(e -> e.setTitle("Raid Usage:").setDescription(rates.toString()))
							.block();
					RAID_USAGE.clear();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}, 0, 12, TimeUnit.HOURS);

			// auto restart
			boolean restartEnabled = restartRestartTimer();
			CLIENT.getUserById(StarotaConstants.SELIM_USER_ID).block().getPrivateChannel().block()
					.createMessage("Auto reboot is " + (restartEnabled ? "enabled" : "disabled"))
					.block();
		} catch (Exception e) {
			System.err.println("+-------------------------------------------------------------------+");
			System.err.println("| Starota failed to start properly. Printing exception then exiting |");
			System.err.println("+-------------------------------------------------------------------+");
			e.printStackTrace();
			Runtime.getRuntime().exit(0);
		}
	}

	private static final Map<Snowflake, Integer> RAID_USAGE = new HashMap<>();

	public static void incRaids(Guild guild) {
		Snowflake id = guild.getId();
		if (RAID_USAGE.containsKey(id))
			RAID_USAGE.put(id, RAID_USAGE.get(id) + 1);
		else
			RAID_USAGE.put(id, 1);
	}

	@SuppressWarnings("deprecation")
	private static void registerCommands(JavaCommandHandler jCmdHandler) {
		jCmdHandler.registerDefaultCommands();

		jCmdHandler.registerCommand(new CommandChangelog());
		jCmdHandler.registerCommand(new CommandCredits());
		jCmdHandler
				.registerCommand(new CommandSupportBot(Starota.BOT_NAME, StarotaConstants.STAROTA_ID));
		jCmdHandler.registerCommand(new CommandInvite(Starota.BOT_NAME, StarotaConstants.STAROTA_ID,
				DebugServer.getUsedPermission().getRawValue()));
		jCmdHandler.registerCommand(new CommandPing());
		jCmdHandler.registerCommand(new CommandVote(Starota.BOT_NAME, StarotaConstants.STAROTA_ID));

		jCmdHandler.registerCommand("Administrative", new CommandStatus());
		jCmdHandler.registerCommand("Administrative", new CommandVotePerks());
		jCmdHandler.registerCommand("Administrative", new CommandSettings());
		jCmdHandler.registerCommand("Administrative", new CommandGetTimezones());
		if (IS_DEV) { jCmdHandler.registerCommand("Debug", new CommandTest()); }

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
		jCmdHandler.registerCommand("Tradeboard", new CommandForTrade());
		jCmdHandler.registerCommand("Tradeboard", new CommandGetUserTrades());
		jCmdHandler.registerCommand("Tradeboard", new CommandFindTrade());
		jCmdHandler.registerCommand("Tradeboard", new CommandGetTrade());
		jCmdHandler.registerCommand("Tradeboard", new CommandLookingFor());
		jCmdHandler.registerCommand("Tradeboard", new CommandRemoveTrade());

		// jCmdHandler.registerCommand("Lua", new CommandUploadScript());

		jCmdHandler.registerCommand("Leaderboard", new CommandEditLeaderboard());
		jCmdHandler.registerCommand("Leaderboard", new CommandUpdateLeaderboard());
		jCmdHandler.registerCommand("Leaderboard", new CommandNewLeaderboard());
		jCmdHandler.registerCommand("Leaderboard", new CommandGetLeaderboard());
		jCmdHandler.registerCommand("Leaderboard", new CommandListLeaderboards());

		jCmdHandler.registerCommand("Modules", new CommandModules());

		jCmdHandler.registerCommand("PvP", new CommandBattleReady());
		jCmdHandler.registerCommand("PvP", new CommandNotReady());
		jCmdHandler.registerCommand("PvP", new CommandFindBattles());
		jCmdHandler.registerCommand("PvP", new CommandCupCounters());

		jCmdHandler.registerCommand("Pokedex", new CommandPokedex());
		jCmdHandler.registerCommand("Pokedex", new CommandCPTable());
		jCmdHandler.registerCommand("Pokedex", new CommandRegionals());

		jCmdHandler.registerCommand("Raids", new CommandRaid());
		jCmdHandler.registerCommand("Raids", new CommandSetRaidEChannel());
		jCmdHandler.registerCommand("Raids", new CommandRaidBosses());
		jCmdHandler.registerCommand("Raids", new CommandRaidTrain());
		// if (IS_DEV)
		// jCmdHandler.registerCommand("Raids", new CommandRaidParty());

		jCmdHandler.registerCommand("Search", new CommandSearchPoke());
		jCmdHandler.registerCommand("Search", new CommandSearchEvents());

		jCmdHandler.registerCommand("Weather", new CommandWeather());
		jCmdHandler.registerCommand("Weather", new CommandCreateWeatherChannel());

		jCmdHandler.registerCommand("Catcher Cup", new CommandCatcherCup());
		CommandCatcherCupAddPoints addPointsCmd = new CommandCatcherCupAddPoints();
		jCmdHandler.registerCommand("Catcher Cup", addPointsCmd);
		EventDispatcher dispatcher = CLIENT.getEventDispatcher();
		addPointsCmd.new EasyEntryHandler().setup(dispatcher);
		jCmdHandler.registerCommand("Catcher Cup", new CommandCreateCupChannel());

		jCmdHandler.registerCommand("Misc", new CommandSilphCard());
		jCmdHandler.registerCommand("Misc", new CommandEvents());
		jCmdHandler.registerCommand("Misc", new CommandEggHatches());
		jCmdHandler.registerCommand("Misc", new CommandDitto());
		jCmdHandler.registerCommand("Misc", new CommandArticleMessage());
		jCmdHandler.registerCommand("Misc", new CommandBots());

		// jCmdHandler.registerCommand("Tutorial", new CommandTutorial());
	}

	public static boolean restartRestartTimer() {
		if (!ENABLE_AUTO_REBOOT)
			return false;
		if (RESTART_TIMER != null)
			RESTART_TIMER.cancel(false);
		RESTART_TIMER = EXECUTOR.schedule(RESTART, 1, TimeUnit.HOURS);
		return true;
	}

	public static DiscordClient getClient() {
		return CLIENT;
	}

	public static DiscordBotListAPI getBotListAPI() {
		if (BOT_LIST == null && PROPERTIES.containsKey("bot_list_token"))
			BOT_LIST = new DiscordBotListAPI.Builder().token(PROPERTIES.getProperty("bot_list_token"))
					.botId(CLIENT.getSelf().block().getId().asString()).build();
		return BOT_LIST;
	}

	public static Guild getGuild(Snowflake guildId) {
		return getGuild(guildId.asLong());
	}

	public static Guild getGuild(long guildId) {
		return CLIENT.getGuildById(Snowflake.of(guildId)).block();
	}

	public static TextChannel getChannel(long guildId, long channelId) {
		GuildChannel ch = getGuild(guildId).getChannelById(Snowflake.of(channelId)).block();
		if (ch instanceof TextChannel)
			return (TextChannel) ch;
		return null;
	}

	public static TextChannel getChannel(long channelId) {
		Channel ch = CLIENT.getChannelById(Snowflake.of(channelId)).block();
		if (ch instanceof TextChannel)
			return (TextChannel) ch;
		return null;
	}

	public static User getUser(long userId) {
		return CLIENT.getUserById(Snowflake.of(userId)).block();
	}

	public static User getOurUser() {
		return CLIENT.getSelf().block();
	}

	public static Member getOurUserAsMember(Guild guild) {
		return CLIENT.getSelf().block().asMember(guild.getId()).block();
	}

	public static String getOurName(long guildId) {
		return getOurName(getGuild(guildId));
	}

	public static String getOurName(Guild guild) {
		if (CLIENT == null)
			return null;
		if (guild == null)
			return getOurUser().getUsername();
		return getOurUser().asMember(guild.getId()).block().getDisplayName();
	}

	public static Member findMember(Guild guild, String name) {
		User u = findUser(guild.getId().asLong(), name);
		if (u == null)
			return null;
		return u.asMember(guild.getId()).block();
	}

	public static User findUser(String name) {
		return findUser(-1, name);
	}

	public static User findUser(long serverId, String name) {
		try {
			long userId = Long.parseLong(name);
			User user = getUser(userId);
			if (user != null)
				return user;
		} catch (NumberFormatException e) { /* */ }
		String user = name.replaceAll("@", "").replaceAll("#\\d{4}", "");
		if (user.matches("<\\d{18}>")) {
			long userId = Long.parseLong(user.substring(1, 19));
			Guild guild = null;
			if (serverId != -1)
				guild = Starota.getGuild(serverId);
			if (guild != null) {
				Member userD = guild.getMemberById(Snowflake.of(userId)).block();
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
				User idUser = getUser(id);
				if (idUser != null)
					return idUser;
			} catch (NumberFormatException e) { /* */ }
		}
		DiscordClient client = Starota.getClient();
		List<User> users;
		if (serverId != -1) {
			users = new LinkedList<>();
			for (Member m : MiscUtils
					.getMembersByName(client.getGuildById(Snowflake.of(serverId)).block(), user))
				users.add(m);
		} else
			users = MiscUtils.getUsersByName(client, user, false);
		for (User u : users)
			if (discrim == null || u.getDiscriminator().equals(discrim))
				return u;
		if (serverId != -1) {
			Guild server = client.getGuildById(Snowflake.of(serverId)).block();
			for (Member u : server.getMembers().collectList().block()) {
				String nickname = u.getDisplayName();
				if (u.getUsername().equalsIgnoreCase(user) || u.getDisplayName().equalsIgnoreCase(user)
						|| (nickname != null && nickname.equalsIgnoreCase(user)))
					return u;
			}
		}
		return null;
	}

	public static TextChannel findChannel(String name) {
		return findChannel(-1, name);
	}

	public static TextChannel findChannel(long serverId, String name) {
		if (name == null)
			return null;
		if (name.matches("<#\\d*>")) {
			try {
				long id = Long.parseLong(name.substring(2, name.length() - 1));
				TextChannel idChannel = getChannel(id);
				if (idChannel != null)
					return idChannel;
			} catch (NumberFormatException e) { /* */ }
		}
		name = name.substring(1);
		DiscordClient client = Starota.getClient();
		List<TextChannel> channels;
		if (serverId != -1) {
			channels = new ArrayList<>();
			for (GuildChannel ch : MiscUtils
					.getChannelsByName(client.getGuildById(Snowflake.of(serverId)).block(), name)) {
				if (ch instanceof TextChannel)
					channels.add((TextChannel) ch);
			}
		} else {
			channels = new ArrayList<>();
			for (Guild g : client.getGuilds().collectList().block())
				for (GuildChannel ch : g.getChannels().collectList().block())
					if (ch instanceof TextChannel)
						channels.add((TextChannel) ch);
			for (TextChannel ch : channels)
				if (ch.getName().equalsIgnoreCase(name))
					return ch;
		}
		if (serverId != -1) {
			Guild server = client.getGuildById(Snowflake.of(serverId)).block();
			for (GuildChannel ch : server.getChannels().collectList().block())
				if (ch instanceof TextChannel && ch.getName().equalsIgnoreCase(name))
					return (TextChannel) ch;
		}
		return null;
	}

	public static Guild getSupportServer() {
		return getGuild(StarotaConstants.SUPPORT_SERVER.asLong());
	}

	public static void submitError(Throwable e) {
		submitError(null, e);
	}

	public static void submitError(String message, Throwable e) {
		if (IS_DEV)
			e.printStackTrace();
		if (IS_DEV || CLIENT == null || !CLIENT.isConnected())
			return;
		long reportChannelId = 522805019326677002L;
		TextChannel reportChannel = null;
		Channel ch = CLIENT.getChannelById(Snowflake.of(reportChannelId)).block();
		if (ch instanceof TextChannel)
			reportChannel = (TextChannel) ch;
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
		reportChannel.createEmbed(builder.build()).block();
	}

	public static void submitStats() {
		if (!FULLY_STARTED)
			return;
		try {
			getBotListAPI();
			if (IS_DEV)
				System.out.println("attempting to submit stats (but in dev)");
			else {
				System.out.println("Submitting server info to the bot list");
				BOT_LIST.setStats(CLIENT.getGuilds().collectList().block().size())
						.whenComplete((v, e) -> {
							if (e != null)
								e.printStackTrace();
							else
								System.out.println("Starota Submitted");
						});
				// if (RegistrationBot.CLIENT != null) {
				// RegistrationBot.getBotListAPI()
				// .setStats(RegistrationBot.CLIENT.getGuilds().collectList().block().size())
				// .whenComplete((v, e) -> {
				// if (e != null)
				// e.printStackTrace();
				// else
				// System.out.println("RegBot Submitted");
				// });
				// }
				// if (PointBot.CLIENT != null) {
				// PointBot.getBotListAPI()
				// .setStats(PointBot.CLIENT.getGuilds().collectList().block().size())
				// .whenComplete((v, e) -> {
				// if (e != null)
				// e.printStackTrace();
				// else
				// System.out.println("Pokedex Submitted");
				// });
				// }
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void updateOwners() {
		if (IS_DEV)
			return;
		Guild supportServer = getSupportServer();
		Snowflake ownerRole = Snowflake.of(539645716583284776L);
		List<Member> currentOwners = new ArrayList<>();
		for (Guild g : CLIENT.getGuilds().collectList().block()) {
			if (!supportServer.getMembers().any((m) -> m.getId().equals(g.getOwnerId())).block())
				continue;
			Member owner = supportServer.getMemberById(g.getOwnerId()).block();
			if (owner == null)
				continue;
			if (!owner.getRoleIds().contains(ownerRole))
				owner.addRole(ownerRole).block();
			currentOwners.add(owner);
		}
		for (Member u : MiscUtils.getMembersByRole(supportServer, ownerRole))
			if (!currentOwners.contains(u))
				u.removeRole(ownerRole).block();
	}

	private static final Long[] SKIPPED_SERVERS = new Long[] { //
			408997776672948224L, // Bot Emojis
			264445053596991498L, // Discord Bot List
	};

	public static void sendOwnersMessage(String msg, User author) {
		sendOwnersMessage(msg, null, author);
	}

	public static void sendOwnersMessage(Consumer<? super EmbedCreateSpec> embed, User author) {
		sendOwnersMessage(null, embed, author);
	}

	public static void sendOwnersMessage(String msg, Consumer<? super EmbedCreateSpec> embed,
			User author) {
		EXECUTOR.execute(new Runnable() {

			@Override
			public void run() {
				final Consumer<? super EmbedCreateSpec> fEmbed = embed == null ? null : embed;
				List<Long> alreadySent = new ArrayList<>();
				for (Guild g : CLIENT.getGuilds().collectList().block()) {
					if (MiscUtils.arrContains(SKIPPED_SERVERS, g.getId().asLong()))
						continue;
					Member owner = g.getOwner().block();
					if (alreadySent.contains(owner.getId().asLong()))
						continue;
					alreadySent.add(owner.getId().asLong());
					if ((msg == null || msg.isEmpty()) && fEmbed != null)
						owner.getPrivateChannel().block().createEmbed(fEmbed).block();
					else if ((msg != null && !msg.isEmpty()) && fEmbed == null)
						owner.getPrivateChannel().block().createMessage(msg).block();
					else if ((msg != null && !msg.isEmpty()) && fEmbed != null)
						owner.getPrivateChannel().block()
								.createMessage((m) -> m.setContent(msg).setEmbed(fEmbed)).block();
					owner.getPrivateChannel().block().createEmbed(getAuthorEmbed(author)).block();
				}
			}
		});
	}

	public static void sendArticle(String msg, User author) {
		sendArticle(msg, null, author);
	}

	public static void sendArticle(Consumer<? super EmbedCreateSpec> embed, User author) {
		sendArticle(null, embed, author);
	}

	public static void sendArticle(String msg, Consumer<? super EmbedCreateSpec> embed, User author) {
		EXECUTOR.execute(new Runnable() {

			@Override
			public void run() {
				final Consumer<? super EmbedCreateSpec> fEmbed = embed == null ? null : embed;
				for (Guild g : CLIENT.getGuilds().collectList().block()) {
					if (MiscUtils.arrContains(SKIPPED_SERVERS, g.getId().asLong()))
						continue;
					StarotaServer server = StarotaServer.getServer(g);
					TextChannel ch = server.getSetting(StarotaConstants.Settings.NEWS_CHANNEL);
					if (ch == null)
						continue;
					if ((msg == null || msg.isEmpty()) && fEmbed != null)
						ch.createEmbed(fEmbed).block();
					else if ((msg != null && !msg.isEmpty()) && fEmbed == null)
						ch.createMessage(msg).block();
					else if ((msg != null && !msg.isEmpty()) && fEmbed != null)
						ch.createMessage((m) -> m.setContent(msg).setEmbed(fEmbed)).block();
					ch.createEmbed(getAuthorEmbed(author)).block();
				}
			}
		});
	}

	private static Consumer<? super EmbedCreateSpec> getAuthorEmbed(User author) {
		EmbedBuilder builder = new EmbedBuilder();
		builder.withFooterIcon(author.getAvatarUrl()).withFooterText(author.getUsername());
		builder.appendDesc("Sent by:");
		return builder.build();
	}

	private static final String URL_BASE = "http://starota.myles-selim.us/";
	private static final String DEV_URL_BASE = "http://dev.myles-selim.us:7366/";

	public static String getStarotaURL(PlayerProfile profile) {
		if (IS_DEV)
			return DEV_URL_BASE + "profile/" + profile.getDiscordId();
		return URL_BASE + "profile/" + profile.getDiscordId();
	}

	public static String getStarotaURL(TradeboardPost post) {
		if (IS_DEV)
			return DEV_URL_BASE + "tradeboard/";
		return URL_BASE + "tradeboard/";
	}

	public static String getCreditsURL() {
		if (IS_DEV)
			return DEV_URL_BASE + "credits";
		return URL_BASE + "credits";
	}

	public static List<Creditable> getCredits() {
		List<Creditable> ret = new LinkedList<>();
		CreditSet creditSet = new CreditSet(EnumCreditType.CONTRIBUTOR);
		ret.add(creditSet);
		for (GitHubContributor u : GitHubAPI.getContributors("Selim042", "Starota"))
			creditSet.add(new Credit.Builder(EnumCreditType.CONTRIBUTOR).withName(u.getLogin())
					.withLink(u.getHtmlUrl()).build());

		ret.add(new Credit.Builder(EnumCreditType.OTHER).withTitle("Discord Java Library")
				.withName("Discord4J").withLink("https://github.com/Discord4J/Discord4J/").build());
		ret.add(new Credit.Builder(EnumCreditType.OTHER).withTitle("Pokemon Go Database")
				.withName("Pokemon Go Hub").withLink("https://pokemongohub.net/").build());
		ret.add(new Credit.Builder(EnumCreditType.OTHER)
				.withTitle("Raid Boss, Field Research, and Egg Information").withName("The Silph Road")
				.withLink("https://thesilphroad.com/").build());
		ret.add(new Credit.Builder(EnumCreditType.OTHER).withTitle("Ditto Information")
				.withName("Leek Duck").withLink("https://leekduck.com").build());
		ret.add(new Credit.Builder(EnumCreditType.OTHER).withTitle("Weather Predictions")
				.withName("AccuWeather").withLink("https://accuweather.com/").build());
		ret.add(new Credit.Builder(EnumCreditType.OTHER).withTitle("PvP Counters")
				.withName("Pokebattler").withLink("https://pokebattler.com").build());

		creditSet = new CreditSet(EnumCreditType.EDITOR);
		ret.add(creditSet);
		Guild supportServer = Starota.getSupportServer();
		for (Member u : MiscUtils.getMembersByRole(supportServer, StarotaConstants.EDITOR_ROLE_ID))
			creditSet.add(new Credit.Builder(EnumCreditType.EDITOR)
					.withName(String.format("%s#%s", u.getUsername(), u.getDiscriminator())).build());

		creditSet = new CreditSet(EnumCreditType.BETA_TESTER);
		ret.add(creditSet);
		Guild testServer = Starota.getGuild(StarotaConstants.BETA_TEST_SERVER);
		for (Member u : MiscUtils.getMembersByRole(testServer, Snowflake.of(637851117203488785L)))
			creditSet.add(new Credit.Builder(EnumCreditType.BETA_TESTER)
					.withName(String.format("%s#%s", u.getUsername(), u.getDiscriminator())).build());
		return ret;
	}

	/** House cup stuff */
	public static List<Pair<EnumTeam, Float>> getCupRankings() {
		IndexHolder count = new IndexHolder(0);
		Map<EnumTeam, Float> vals = new HashMap<>();
		for (EnumTeam t : EnumTeam.values())
			vals.put(t, 0.0f);
		CLIENT.getGuilds().doOnEach((g) -> {
			StarotaServer server = StarotaServer.getServer(g.get());
			if (server != null) {
				int total = 0;
				List<Pair<EnumTeam, Integer>> rankings = server.getRankings();
				for (Pair<EnumTeam, Integer> p : rankings)
					total += p.right;
				if (total > 0) {
					count.value++;
					for (Pair<EnumTeam, Integer> p : rankings) {
						float oldV = vals.get(p.left);
						float newV = (p.right / (float) total);
						vals.put(p.left, oldV + newV);
					}
				}
			}
		}).collectList().block();
		if (count.value == 0)
			for (EnumTeam t : EnumTeam.values())
				vals.put(t, 0.0f);
		else
			for (EnumTeam t : EnumTeam.values())
				vals.put(t, vals.get(t) / count.value);
		List<Pair<EnumTeam, Float>> ret = new ArrayList<>();
		for (Entry<EnumTeam, Float> e : vals.entrySet())
			ret.add(new Pair<>(e.getKey(), e.getValue()));
		ret.sort(new Comparator<Pair<EnumTeam, Float>>() {

			@Override
			public int compare(Pair<EnumTeam, Float> o1, Pair<EnumTeam, Float> o2) {
				return Float.compare(o2.right, o1.right);
			}
		});
		return Collections.unmodifiableList(ret);
	}

	/** House cup stuff */
	public static float getTeamCupPoints(EnumTeam team) {
		List<Pair<EnumTeam, Float>> rankings = getCupRankings();
		for (Pair<EnumTeam, Float> p : rankings)
			if (p.left == team)
				return p.right;
		return 0.0f;
	}

	/** House cup stuff */
	public static EnumTeam getHighestTeamCup() {
		return getCupRankings().get(0).left;
	}

}
