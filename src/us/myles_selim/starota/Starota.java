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
import sx.blah.discord.api.events.EventSubscriber;
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
import us.myles_selim.starota.enums.EnumPatreonPerm;
import us.myles_selim.starota.enums.EnumPokemon;
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
import us.myles_selim.starota.webserver.webhooks.WebhookEvent;
import us.myles_selim.starota.webserver.webhooks.WebhookRaid;
import us.myles_selim.starota.wrappers.StarotaServer;

public class Starota {

	private static IDiscordClient CLIENT;
	private static DiscordBotListAPI BOT_LIST;
	// private static Socket MANAGER_SOCKET;
	private static final Properties PROPERTIES = new Properties();

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
	public final static String VERSION = "2.7.0";
	public final static String CHANGELOG = "Changelog for v" + VERSION + "\n" + "Public changes:\n"
			+ " + Add raid planning command\n" + " + Add event command\n"
			+ " * Change tradeboard photos to use the same image set as the Pokedex\n";
	public final static File DATA_FOLDER = new File("starotaData");

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
		IS_DEV = Boolean.parseBoolean(PROPERTIES.getProperty("is_dev"));
		EventDispatcher dispatcher = CLIENT.getDispatcher();
		try {
			while (!CLIENT.isReady())
				Thread.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		submitStats();
		BaseModules.registerModules();
		DebugServer debug = new DebugServer();
		debug.start();

		CLIENT.changePresence(StatusType.DND, ActivityType.PLAYING, "registering commands...");

		JavaCommandHandler.registerCommand(new CommandChangelog());
		JavaCommandHandler.registerCommand(new CommandCredits());
		JavaCommandHandler.registerCommand(new CommandSupportStarota());
		JavaCommandHandler.registerCommand(new CommandInvite());

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

		JavaCommandHandler.registerCommand("PvP", new CommandBattleReady());
		JavaCommandHandler.registerCommand("PvP", new CommandNotReady());
		JavaCommandHandler.registerCommand("PvP", new CommandFindBattles());

		JavaCommandHandler.registerCommand("Pokedex", new CommandPokedex());

		JavaCommandHandler.registerCommand("Silph Road", new CommandSilphCard());

		JavaCommandHandler.registerCommand("Raids", new CommandRaid());
		JavaCommandHandler.registerCommand("Raids", new CommandSetRaidEChannel());
		// JavaCommandHandler.registerCommand("Raids", new CommandRaidBosses());

		JavaCommandHandler.registerCommand("Events", new CommandEvents());

		try {
			Thread.sleep(2500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		CLIENT.changePresence(StatusType.IDLE, ActivityType.PLAYING,
				"starting threads and loading settings...");

		FormManager.init();
		ResearchTracker.init();

		dispatcher.registerListener(new ReactionMessageRegistry());
		dispatcher.registerListener(new PrimaryCommandHandler());
		dispatcher.registerListener(new EventHandler());
		dispatcher.registerListener(new Object() {

			@EventSubscriber
			public void testThingy(WebhookEvent event) {
				IChannel channel = Starota.getChannel(538156939868110854L);
				EmbedBuilder builder = new EmbedBuilder();
				switch (event.getType()) {
				case "raid":
					WebhookRaid raidHook = (WebhookRaid) event.getWebhookClass().message;
					boolean hasHatched = raidHook.pokemon_id != 0;
					builder.withTitle("Tier " + raidHook.level + " Raid at " + raidHook.gym_name);
					if (hasHatched) {
						builder.appendField("Boss:", "#**" + raidHook.pokemon_id + "** "
								+ EnumPokemon.getPokemon(raidHook.pokemon_id), false);
						builder.withThumbnail(
								ImageHelper.getOfficalArtwork(raidHook.getPokemon(), raidHook.form));
					} else
						builder.withThumbnail(ImageHelper.getRaidEgg(raidHook.level));
					builder.withImage(String.format(
							"https://api.mapbox.com/styles/v1/mapbox/satellite-streets-v11/static/pin-s+%06X(%2$f,%3$f)/%2$f,%3$f,16.5,0,0/600x300@2x?logo=false&access_token=pk.eyJ1Ijoic2VsaW0wNDIiLCJhIjoiY2pyOXpmM2g1MG16cTQzbndqZXk5dHNndCJ9.vsh20BzsPBgTcBBcKWBqQw",
							raidHook.getTeam().getColor(), raidHook.longitude, raidHook.latitude));
					break;
				}
				RequestBuffer.request(() -> channel.sendMessage(builder.build()));
			}
		});
		ReactionMessageRegistry.init();
		WebServer.init();

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
		CLIENT.changePresence(StatusType.ONLINE, ActivityType.PLAYING,
				"v" + VERSION + (DEBUG || IS_DEV ? "d" : ""));

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
		PrimaryCommandHandler.registerCommandHandler(new LuaCommandHandler());

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
	}

	public static class BaseModules {

		private static boolean registered = false;

		public static final StarotaModule PROFILES = new StarotaModule("PlayerProfiles", "Profiles");
		public static final StarotaModule GROUPS = new StarotaModule("Groups", "Groups");
		public static final StarotaModule TRADEBOARD = new StarotaModule("Tradeboard", "Tradeboard",
				PROFILES);
		public static final StarotaModule LUA = new StarotaModule("Lua", "Lua");
		public static final StarotaModule LEADERBOARDS = new StarotaModule("Leaderboards", "Leaderboard",
				PROFILES);
		public static final StarotaModule PVP = new StarotaModule("PvP", "PvP", PROFILES);
		public static final StarotaModule POKEDEX = new StarotaModule("Pokedex", "Pokedex");
		public static final StarotaModule SILPH_ROAD = new StarotaModule("SilphRoad", "Silph Road");

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
		if (!IS_DEV && PROPERTIES.containsKey("bot_list_token")) {
			System.out.println("Submitting shard info to the bot list");
			BOT_LIST = new DiscordBotListAPI.Builder().token(PROPERTIES.getProperty("bot_list_token"))
					.botId(CLIENT.getOurUser().getStringID()).build();
			List<Integer> shards = new ArrayList<>();
			for (IShard s : CLIENT.getShards())
				shards.add(s.getGuilds().size());
			BOT_LIST.setStats(shards).whenComplete((v, e) -> {
				if (e != null)
					e.printStackTrace();
				else
					System.out.println("Submitted");
			});
		} else
			System.out.println("BOT LIST TOKEN NOT FOUND");
	}

}
