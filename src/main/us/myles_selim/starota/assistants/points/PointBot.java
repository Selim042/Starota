package us.myles_selim.starota.assistants.points;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Timer;
import java.util.function.Predicate;

import org.discordbots.api.client.DiscordBotListAPI;

import discord4j.core.DiscordClient;
import discord4j.core.DiscordClientBuilder;
import discord4j.core.event.EventDispatcher;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.User;
import discord4j.core.object.presence.Activity;
import discord4j.core.object.presence.Presence;
import discord4j.core.object.util.Permission;
import discord4j.core.object.util.PermissionSet;
import discord4j.core.object.util.Snowflake;
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.assistants.CommandBots;
import us.myles_selim.starota.assistants.points.commands.CommandGetQuests;
import us.myles_selim.starota.commands.CommandGetTimezones;
import us.myles_selim.starota.commands.registry.PrimaryCommandHandler;
import us.myles_selim.starota.commands.registry.java.JavaCommandHandler;
import us.myles_selim.starota.commands.settings.CommandSettings;
import us.myles_selim.starota.misc.utils.MiscUtils;
import us.myles_selim.starota.misc.utils.StarotaConstants;
import us.myles_selim.starota.misc.utils.StatusUpdater;
import us.myles_selim.starota.wrappers.BotServer;

public class PointBot {

	public static DiscordClient CLIENT;

	public static final String BOT_NAME = "Point Bot";
	public static final PermissionSet USED_PERMISSIONS = PermissionSet.of(Permission.SEND_MESSAGES,
			Permission.VIEW_CHANNEL, Permission.MANAGE_MESSAGES, Permission.USE_EXTERNAL_EMOJIS,
			Permission.ADD_REACTIONS);
	public final static File DATA_FOLDER = new File("pointData");

	private static Properties PROPERTIES = new Properties();
	private static DiscordBotListAPI BOT_LIST;
	private static boolean started = false;

	@SuppressWarnings("deprecation")
	public static void start() {
		// if (started || Starota.IS_DEV)
		if (started || !started)
			return;
		started = true;

		try {
			PROPERTIES.load(new FileInputStream("starota.properties"));
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		DiscordClientBuilder builder = new DiscordClientBuilder(PROPERTIES.getProperty("point_bot"));
		CLIENT = builder.build();
		Thread botRun = new Thread() {

			@Override
			public void run() {
				CLIENT.login().block();
			}
		};
		botRun.start();
		try {
			while (!CLIENT.isConnected())
				Thread.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		BotServer.registerServerType(PointBot.CLIENT, PointServer.class);
		StatusUpdater statuses = new StatusUpdater(CLIENT);
		statuses.addPresence(Presence.online(Activity.playing(
				"v" + StarotaConstants.VERSION + (Starota.DEBUG || Starota.IS_DEV ? "d" : ""))));
		statuses.addPresence(Presence.online(Activity.watching("quest data")));
		statuses.addPresence(Presence.online(Activity.watching("players complete quests")));
		statuses.start();

		EventDispatcher dispatcher = CLIENT.getEventDispatcher();
		new PointEventHandler().setup(dispatcher);
		PrimaryCommandHandler cmdHandler = new PrimaryCommandHandler(CLIENT);
		JavaCommandHandler jCmdHandler = cmdHandler
				.registerCommandHandler(new JavaCommandHandler(CLIENT));

		jCmdHandler.registerCommand("Administrative", new CommandSettings());
		jCmdHandler.registerCommand("Administrative", new CommandGetTimezones());

		jCmdHandler.registerCommand("Quests", new CommandGetQuests());

		jCmdHandler.registerCommand("Misc", new CommandBots());

		cmdHandler.setup(dispatcher);

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

	public static User getOurUser() {
		if (!started)
			return null;
		return CLIENT.getSelf().block();
	}

	public static String getOurName(Guild guild) {
		if (CLIENT == null)
			return null;
		if (guild == null)
			return getOurUser().getUsername();
		return getOurUser().asMember(guild.getId()).block().getDisplayName();
	}

	public static DiscordBotListAPI getBotListAPI() {
		if (BOT_LIST == null && PROPERTIES.containsKey("point_bot_list_token"))
			BOT_LIST = new DiscordBotListAPI.Builder()
					.token(PROPERTIES.getProperty("point_bot_list_token"))
					.botId(getOurUser().getId().asString()).build();
		return BOT_LIST;
	}

	public static void updateOwners() {
		if (Starota.IS_DEV)
			return;
		Snowflake ownerRole = Snowflake.of(580813099624431636L);
		List<User> currentOwners = new ArrayList<>();
		for (Guild g : CLIENT.getGuilds().collectList().block()) {
			Member owner = g.getOwner().block().asMember(StarotaConstants.SUPPORT_SERVER).block();
			if (owner == null)
				continue;
			if (!owner.getRoleIds().contains(ownerRole))
				owner.addRole(ownerRole).block();
			currentOwners.add(owner);
		}
		for (Member u : MiscUtils.getMembersByRole(
				CLIENT.getGuildById(StarotaConstants.SUPPORT_SERVER).block(), ownerRole))
			if (!currentOwners.contains(u))
				u.removeRole(ownerRole);
	}

}
