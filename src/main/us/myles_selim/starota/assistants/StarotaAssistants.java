package us.myles_selim.starota.assistants;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import discord4j.core.DiscordClient;
import discord4j.core.DiscordClientBuilder;
import discord4j.core.object.PermissionOverwrite;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.GuildChannel;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.Role;
import discord4j.core.object.presence.Activity;
import discord4j.core.object.presence.Presence;
import discord4j.core.object.util.Permission;
import discord4j.core.object.util.PermissionSet;
import discord4j.core.object.util.Snowflake;
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.misc.utils.MiscUtils;
import us.myles_selim.starota.misc.utils.StarotaConstants;

public class StarotaAssistants {

	private static final String ROLE_NAME = "Starota Assistant";
	private static final Map<Long, DiscordClient> CLIENT_MAP = new ConcurrentHashMap<>();
	private static final List<DiscordClient> CLIENTS = new ArrayList<>();
	private static final List<Long> ASSISTANT_IDS = new ArrayList<>();
	private static boolean inited = false;

	public static final PermissionSet USED_PERMISSIONS = PermissionSet.of(Permission.VIEW_CHANNEL,
			Permission.SEND_MESSAGES, Permission.USE_EXTERNAL_EMOJIS, Permission.ADD_REACTIONS);

	public static void init() {
		if (inited)
			return;
		inited = true;

		if (Starota.IS_DEV)
			CLIENT_MAP.put(StarotaConstants.STAROTA_DEV_ID.asLong(), Starota.getClient());
		else
			CLIENT_MAP.put(StarotaConstants.STAROTA_ID.asLong(), Starota.getClient());
		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream("starota.properties"));
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		if (!properties.containsKey("assistants"))
			return;
		for (String token : properties.getProperty("assistants").split(";")) {
			DiscordClientBuilder clientBuilder = new DiscordClientBuilder(token);
			DiscordClient client = null;
			client = clientBuilder.build();
			client.login();
			if (client != null) {
//				try {
//					while (!client.isConnected())
//						Thread.sleep(10);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
				// client.changeAvatar(Image.forUser(Starota.getSelf().block()));
				if (Starota.IS_DEV)
					client.updatePresence(Presence.idle(Activity.listening(Starota.BOT_NAME)));
				else
					client.updatePresence(Presence.invisible());
				CLIENT_MAP.put(client.getSelf().block().getId().asLong(), client);
				CLIENTS.add(client);
				ASSISTANT_IDS.add(client.getSelf().block().getId().asLong());
			}
		}
		new Thread("AssistantStatusUpdater") {

			@Override
			public void run() {
				while (true) {
					for (DiscordClient client : CLIENTS) {
						if (Starota.IS_DEV)
							client.updatePresence(Presence.idle(Activity.listening(Starota.BOT_NAME)));
						else
							client.updatePresence(Presence.invisible());
					}
					try {
						Thread.sleep(3600000); // 1 hour
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}.start();
	}

	public static boolean areAllOnServer(Guild guild) {
		for (Entry<Long, DiscordClient> e : CLIENT_MAP.entrySet())
			if (guild.getMemberById(Snowflake.of(e.getKey())) == null)
				return false;
		return true;
	}

	public static void setPermissionForChannel(GuildChannel channel) {
		channel = Starota.getChannel(channel.getGuild().block().getId().asLong(),
				channel.getId().asLong());
		Role role = getAssistantRole(channel.getGuild().block());
		channel.addRoleOverwrite(role.getId(),
				PermissionOverwrite.forRole(role.getId(), USED_PERMISSIONS, PermissionSet.none()));
	}

	public static Role getAssistantRole(Guild guild) {
		Role role = null;
		for (Role r : MiscUtils.getRoleByName(guild, ROLE_NAME)) {
			role = r;
			break;
		}
		if (role == null)
			role = guild.createRole((r) -> r.setName(ROLE_NAME).setPermissions(USED_PERMISSIONS))
					.block();
		return role;
	}

	public static void setAssistantRole(Guild guild, Member member) {
		if (!isAssistant(member))
			return;
		member.addRole(getAssistantRole(guild).getId());
	}

	public static boolean isAssistant(Member member) {
		return ASSISTANT_IDS.contains(member.getId().asLong());
	}

	public static List<DiscordClient> getClients() {
		return Collections.unmodifiableList(CLIENTS);
	}

	public static DiscordClient getResponsibleClient(Message msg) {
		long id = msg.getAuthor().get().getId().asLong();
		for (Entry<Long, DiscordClient> e : CLIENT_MAP.entrySet())
			if (e.getKey().equals(id))
				return e.getValue();
		return null;
	}

}
