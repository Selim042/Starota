package us.myles_selim.starota;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.Timer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
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
import us.myles_selim.starota.assistants.points.PointBot;
import us.myles_selim.starota.assistants.pokedex.PokedexBot;
import us.myles_selim.starota.assistants.registration.RegistrationBot;
import us.myles_selim.starota.commands.CommandArticleMessage;
import us.myles_selim.starota.commands.CommandChangelog;
import us.myles_selim.starota.commands.CommandCredits;
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
import us.myles_selim.starota.misc.data_types.BotServer;
import us.myles_selim.starota.misc.utils.EmbedBuilder;
import us.myles_selim.starota.misc.utils.MiscUtils;
import us.myles_selim.starota.misc.utils.StarotaConstants;
import us.myles_selim.starota.misc.utils.StatusUpdater;
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

	private static DiscordClient CLIENT;
	private static DiscordBotListAPI BOT_LIST;
	// private static Socket MANAGER_SOCKET;
	private static final Properties PROPERTIES = new Properties();

	public final static boolean DEBUG = false;
	public static boolean IS_DEV;
	public static boolean FULLY_STARTED = false;
	public final static String BOT_NAME = "Starota";
	public final static String CHANGELOG = "Changelog for v" + StarotaConstants.VERSION + "\n"
			+ "Public changes:\n"
			+ " + Pokedex now displays min/max CP for Pokemon currently in eggs or raids\n"
			+ " + New Discord bot, Registration Bot (see .bots) that registers users via OCR\n"
			+ " * Remove webhook stuff for now, more information incoming later\n"
			+ " * Fix some weird stuff going on in .sRegister";
	public final static File DATA_FOLDER = new File("starotaData");

	public static PrimaryCommandHandler COMMAND_HANDLER;
	public static ReactionMessageRegistry REACTION_MESSAGES_REGISTRY;
	public static final ExecutorService EXECUTOR = Executors.newCachedThreadPool();

	@SuppressWarnings("deprecation")
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
			DiscordClientBuilder clientBuilder = new DiscordClientBuilder(
					PROPERTIES.getProperty("token"));
			IS_DEV = Boolean.parseBoolean(PROPERTIES.getProperty("is_dev"));
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
			PokedexBot.start();
			RegistrationBot.start();
			// PointBot.start();
			submitStats();

			System.out.println("v" + StarotaConstants.VERSION + (DEBUG || IS_DEV ? "d" : ""));
			StatusUpdater statusUpdater = new StatusUpdater(CLIENT);
			statusUpdater.addPresence(Presence.online(
					Activity.playing("v" + StarotaConstants.VERSION + (DEBUG || IS_DEV ? "d" : ""))));
			statusUpdater.addPresence(
					Presence.online(Activity.watching("people organize raids with `raid`")));
			statusUpdater.addPresence(
					Presence.online(Activity.listening("to people search for events and Pokemon")));
			statusUpdater.addPresence(Presence.online(Activity.playing("with the new event system")));
			statusUpdater.addPresence(Presence.online(Activity.watching(".bots for new bots")));
			statusUpdater.start();

			EXECUTOR.execute(new Runnable() {

				@Override
				public void run() {
					boolean sentToAll = true;
					List<Guild> guilds = new ArrayList<>(CLIENT.getGuilds().collectList().block());
					if (!IS_DEV)
						guilds.addAll(PokedexBot.CLIENT.getGuilds().collectList().block());
					for (Guild g : guilds) {
						StarotaServer server = StarotaServer.getServer(g);
						TextChannel changesChannel = server
								.getSetting(StarotaConstants.Settings.CHANGES_CHANNEL);
						if (changesChannel == null)
							continue;
						String latestChangelog = (String) server.getDataValue("changesVersion");
						if (!StarotaConstants.VERSION.equalsIgnoreCase(latestChangelog)) {
							changesChannel.createMessage("```" + CHANGELOG + "```");
							server.setDataValue("changesVersion", StarotaConstants.VERSION);
						} else
							sentToAll = false;
					}
					if (!IS_DEV && sentToAll)
						TwitterHelper.sendTweet(CHANGELOG);
				}
			});

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
				DebugServer.getUsedPermission().getRawValue()));
		jCmdHandler.registerCommand(new CommandPing());
		jCmdHandler.registerCommand(new CommandVote(Starota.BOT_NAME, StarotaConstants.STAROTA_ID));

		jCmdHandler.registerCommand("Administrative", new CommandStatus());
		jCmdHandler.registerCommand("Administrative", new CommandVotePerks());
		jCmdHandler.registerCommand("Administrative", new CommandSettings());
		jCmdHandler.registerCommand("Administrative", new CommandGetTimezones());
		if (IS_DEV) {
			jCmdHandler.registerCommand("Debug", new CommandTest());
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
		} catch (NumberFormatException e) {}
		String user = name.replaceAll("@", "").replaceAll("#\\d{4}", "");
		if (user.matches("<\\d{18}>")) {
			long userId = Long.parseLong(user.substring(1, 19));
			Guild guild = Starota.getGuild(serverId);
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
				if (idUser != null) {
					return idUser;
				}
			} catch (NumberFormatException e) {}
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
			} catch (NumberFormatException e) {}
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
		reportChannel.createEmbed(builder.build());
	}

	public static void submitStats() {
		try {
			getBotListAPI();
			if (!IS_DEV) {
				System.out.println("Submitting server info to the bot list");
				BOT_LIST.setStats(CLIENT.getGuilds().collectList().block().size())
						.whenComplete((v, e) -> {
							if (e != null)
								e.printStackTrace();
							else
								System.out.println("Starota Submitted");
						});
				if (PokedexBot.CLIENT != null) {
					PokedexBot.getBotListAPI()
							.setStats(PokedexBot.CLIENT.getGuilds().collectList().block().size())
							.whenComplete((v, e) -> {
								if (e != null)
									e.printStackTrace();
								else
									System.out.println("Pokedex Submitted");
							});
				}
				if (RegistrationBot.CLIENT != null) {
					RegistrationBot.getBotListAPI()
							.setStats(RegistrationBot.CLIENT.getGuilds().collectList().block().size())
							.whenComplete((v, e) -> {
								if (e != null)
									e.printStackTrace();
								else
									System.out.println("RegBot Submitted");
							});
				}
				if (PointBot.CLIENT != null) {
					PointBot.getBotListAPI()
							.setStats(PointBot.CLIENT.getGuilds().collectList().block().size())
							.whenComplete((v, e) -> {
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
		Guild supportServer = getSupportServer();
		Snowflake ownerRole = Snowflake.of(539645716583284776L);
		List<Member> currentOwners = new ArrayList<>();
		for (Guild g : CLIENT.getGuilds().collectList().block()) {
			Member owner = supportServer.getMemberById(g.getOwnerId()).block();
			if (owner == null)
				continue;
			if (!owner.getRoleIds().contains(ownerRole))
				owner.addRole(ownerRole);
			currentOwners.add(owner);
		}
		for (Member u : MiscUtils.getMembersByRole(supportServer, ownerRole))
			if (!currentOwners.contains(u))
				u.removeRole(ownerRole);
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
					if (author.getId().asLong() == owner.getId().asLong()
							|| alreadySent.contains(owner.getId().asLong()))
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

}
