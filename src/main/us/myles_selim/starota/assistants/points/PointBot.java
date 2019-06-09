package us.myles_selim.starota.assistants.points;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.EnumSet;
import java.util.List;
import java.util.Properties;
import java.util.Timer;
import java.util.function.Predicate;

import org.discordbots.api.client.DiscordBotListAPI;

import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.ActivityType;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IRole;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.handle.obj.Permissions;
import sx.blah.discord.handle.obj.StatusType;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.RequestBuffer;
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.assistants.CommandBots;
import us.myles_selim.starota.assistants.points.commands.CommandGetQuests;
import us.myles_selim.starota.commands.CommandGetTimezones;
import us.myles_selim.starota.commands.registry.PrimaryCommandHandler;
import us.myles_selim.starota.commands.registry.java.JavaCommandHandler;
import us.myles_selim.starota.commands.settings.CommandSettings;
import us.myles_selim.starota.misc.data_types.BotServer;
import us.myles_selim.starota.misc.utils.StarotaConstants;
import us.myles_selim.starota.misc.utils.StatusUpdater;
import us.myles_selim.starota.misc.utils.StatusUpdater.PresenceData;

public class PointBot {

	public static IDiscordClient CLIENT;

	public static final String BOT_NAME = "Point Bot";
	public static final EnumSet<Permissions> USED_PERMISSIONS = EnumSet.of(Permissions.SEND_MESSAGES,
			Permissions.READ_MESSAGES, Permissions.MANAGE_MESSAGES, Permissions.USE_EXTERNAL_EMOJIS,
			Permissions.ADD_REACTIONS);
	public final static File DATA_FOLDER = new File("pointData");

	private static Properties PROPERTIES = new Properties();
	private static DiscordBotListAPI BOT_LIST;
	private static boolean started = false;

	public static void start() {
		// if (started || Starota.IS_DEV)
		if (started)
			return;
		started = true;

		ClientBuilder builder = new ClientBuilder();
		try {
			PROPERTIES.load(new FileInputStream("starota.properties"));
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		builder.withToken(PROPERTIES.getProperty("point_bot"));
		try {
			CLIENT = builder.login();
		} catch (DiscordException e) {
			e.printStackTrace();
			System.err.println("failed to start " + BOT_NAME);
			return;
		}
		try {
			while (!CLIENT.isReady())
				Thread.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		BotServer.registerServerType(PointBot.CLIENT, PointServer.class);
		StatusUpdater statuses = new StatusUpdater(CLIENT);
		statuses.addPresence(new PresenceData(StatusType.ONLINE, ActivityType.PLAYING,
				"v" + StarotaConstants.VERSION + (Starota.DEBUG || Starota.IS_DEV ? "d" : "")));
		statuses.addPresence(new PresenceData(StatusType.ONLINE, ActivityType.WATCHING, "quest data"));
		statuses.addPresence(
				new PresenceData(StatusType.ONLINE, ActivityType.WATCHING, "players complete quests"));
		statuses.start();

		CLIENT.getDispatcher().registerListener(new PointEventHandler());
		PrimaryCommandHandler cmdHandler = new PrimaryCommandHandler(CLIENT);
		JavaCommandHandler jCmdHandler = cmdHandler
				.registerCommandHandler(new JavaCommandHandler(CLIENT));

		jCmdHandler.registerCommand("Administrative", new CommandSettings());
		jCmdHandler.registerCommand("Administrative", new CommandGetTimezones());

		jCmdHandler.registerCommand("Quests", new CommandGetQuests());

		jCmdHandler.registerCommand("Misc", new CommandBots());

		CLIENT.getDispatcher().registerListener(cmdHandler);

		// hacky, weird thing just to hide the deprecation warning
		@SuppressWarnings("deprecation")
		Predicate<Void> f = (v) -> {
			Timer timer = new Timer();
			Date nextHour = new Date();
			nextHour.setHours(0);
			nextHour.setMinutes(0);
			timer.scheduleAtFixedRate(new SimpleQuestUpdateTask(), nextHour, (long) 3.6e+6);
			return false;
		};
		f.test(null);
	}

	public static IUser getOurUser() {
		if (!started)
			return null;
		return CLIENT.getOurUser();
	}

	public static String getOurName(IGuild guild) {
		if (CLIENT == null)
			return null;
		if (guild == null)
			return getOurUser().getName();
		return getOurUser().getDisplayName(guild);
	}

	public static DiscordBotListAPI getBotListAPI() {
		if (BOT_LIST == null && PROPERTIES.containsKey("point_bot_list_token"))
			BOT_LIST = new DiscordBotListAPI.Builder()
					.token(PROPERTIES.getProperty("point_bot_list_token"))
					.botId(CLIENT.getOurUser().getStringID()).build();
		return BOT_LIST;
	}

	public static void updateOwners() {
		if (Starota.IS_DEV)
			return;
		IRole ownerRole = CLIENT.getRoleByID(580813099624431636L);
		List<IUser> currentOwners = new ArrayList<>();
		for (IGuild g : CLIENT.getGuilds()) {
			IUser owner = CLIENT.getGuildByID(StarotaConstants.SUPPORT_SERVER)
					.getUserByID(g.getOwnerLongID());
			if (owner == null)
				continue;
			if (!owner.hasRole(ownerRole))
				RequestBuffer.request(() -> owner.addRole(ownerRole));
			currentOwners.add(owner);
		}
		for (IUser u : CLIENT.getGuildByID(StarotaConstants.SUPPORT_SERVER).getUsersByRole(ownerRole))
			if (!currentOwners.contains(u))
				u.removeRole(ownerRole);
	}

}
