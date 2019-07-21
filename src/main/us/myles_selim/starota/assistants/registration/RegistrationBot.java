package us.myles_selim.starota.assistants.registration;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.discordbots.api.client.DiscordBotListAPI;

import discord4j.core.DiscordClient;
import discord4j.core.DiscordClientBuilder;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.Role;
import discord4j.core.object.entity.User;
import discord4j.core.object.presence.Activity;
import discord4j.core.object.presence.Presence;
import discord4j.core.object.util.Permission;
import discord4j.core.object.util.PermissionSet;
import discord4j.core.object.util.Snowflake;
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.misc.utils.MiscUtils;
import us.myles_selim.starota.misc.utils.StarotaConstants;
import us.myles_selim.starota.misc.utils.StatusUpdater;

public class RegistrationBot {

	public static DiscordClient CLIENT;

	public static final String BOT_NAME = "Registration Bot";
	public static final PermissionSet USED_PERMISSIONS = PermissionSet.of(Permission.SEND_MESSAGES,
			Permission.VIEW_CHANNEL, Permission.MANAGE_MESSAGES, Permission.USE_EXTERNAL_EMOJIS,
			Permission.ADD_REACTIONS);

	private static Properties PROPERTIES = new Properties();
	private static DiscordBotListAPI BOT_LIST;
	private static boolean started = false;

	@SuppressWarnings("deprecation")
	public static void start() {
		if (started)
			return;
		started = true;

		try {
			PROPERTIES.load(new FileInputStream("starota.properties"));
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		DiscordClientBuilder builder = new DiscordClientBuilder(
				PROPERTIES.getProperty("registration_bot"));
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
		StatusUpdater statuses = new StatusUpdater(CLIENT);
		statuses.addPresence(Presence.online(Activity.playing(
				"v" + StarotaConstants.VERSION + (Starota.DEBUG || Starota.IS_DEV ? "d" : ""))));
		statuses.start();

		new RegistrationEventHandler().setup(CLIENT.getEventDispatcher());
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
		if (BOT_LIST == null && PROPERTIES.containsKey("registration_bot_list_token"))
			BOT_LIST = new DiscordBotListAPI.Builder()
					.token(PROPERTIES.getProperty("registration_bot_list_token"))
					.botId(getOurUser().getId().asString()).build();
		return BOT_LIST;
	}

	public static void updateOwners() {
		if (Starota.IS_DEV)
			return;
		Role ownerRole = CLIENT
				.getRoleById(StarotaConstants.SUPPORT_SERVER, Snowflake.of(578327458873475085L)).block();
		List<User> currentOwners = new ArrayList<>();
		for (Guild g : CLIENT.getGuilds().collectList().block()) {
			Member owner = CLIENT.getGuildById(StarotaConstants.SUPPORT_SERVER).block()
					.getMemberById(g.getOwnerId()).block();
			if (owner == null)
				continue;
			if (!owner.getRoleIds().contains(ownerRole.getId()))
				owner.addRole(ownerRole.getId());
			currentOwners.add(owner);
		}
		for (Member u : MiscUtils.getMembersByRole(
				CLIENT.getGuildById(StarotaConstants.SUPPORT_SERVER).block(), ownerRole))
			if (!currentOwners.contains(u))
				u.removeRole(ownerRole.getId());
	}

}
