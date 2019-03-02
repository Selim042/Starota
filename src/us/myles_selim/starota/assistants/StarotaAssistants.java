package us.myles_selim.starota.assistants;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.ActivityType;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IRole;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.handle.obj.Permissions;
import sx.blah.discord.handle.obj.StatusType;
import sx.blah.discord.util.DiscordException;
import us.myles_selim.starota.Starota;

public class StarotaAssistants {

	private static final String ROLE_NAME = "Starota Assistant";
	private static final Map<Long, IDiscordClient> CLIENT_MAP = new ConcurrentHashMap<>();
	private static final List<IDiscordClient> CLIENTS = new ArrayList<>();
	private static final List<Long> ASSISTANT_IDS = new ArrayList<>();
	private static boolean inited = false;

	public static final EnumSet<Permissions> USED_PERMISSIONS = EnumSet.of(Permissions.READ_MESSAGES,
			Permissions.SEND_MESSAGES, Permissions.USE_EXTERNAL_EMOJIS, Permissions.ADD_REACTIONS);

	public static void init() {
		if (inited)
			return;
		inited = true;

		if (Starota.IS_DEV)
			CLIENT_MAP.put(Starota.STAROTA_DEV_ID, Starota.getClient());
		else
			CLIENT_MAP.put(Starota.STAROTA_ID, Starota.getClient());
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
			ClientBuilder clientBuilder = new ClientBuilder();
			clientBuilder.withToken(token);
			IDiscordClient client = null;
			try {
				client = clientBuilder.login();
			} catch (DiscordException e) {
				e.printStackTrace();
			}
			if (client != null) {
				try {
					while (!client.isReady())
						Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				// client.changeAvatar(Image.forUser(Starota.getOurUser()));
				if (Starota.IS_DEV)
					client.changePresence(StatusType.IDLE, ActivityType.LISTENING,
							"to " + Starota.BOT_NAME);
				else
					client.changePresence(StatusType.INVISIBLE);
				CLIENT_MAP.put(client.getOurUser().getLongID(), client);
				CLIENTS.add(client);
				ASSISTANT_IDS.add(client.getOurUser().getLongID());
			}
		}
		new Thread("AssistantStatusUpdater") {

			@Override
			public void run() {
				while (true) {
					for (IDiscordClient client : CLIENTS) {
						if (Starota.IS_DEV)
							client.changePresence(StatusType.IDLE, ActivityType.LISTENING,
									"to " + Starota.BOT_NAME);
						else
							client.changePresence(StatusType.INVISIBLE);
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

	public static boolean areAllOnServer(IGuild guild) {
		for (Entry<Long, IDiscordClient> e : CLIENT_MAP.entrySet())
			if (guild.getUserByID(e.getKey()) == null)
				return false;
		return true;
	}

	public static void setPermissionsForChannel(IChannel channel) {
		channel = Starota.getChannel(channel.getGuild().getLongID(), channel.getLongID());
		IRole role = getAssistantRole(channel.getGuild());
		channel.overrideRolePermissions(role, USED_PERMISSIONS, EnumSet.noneOf(Permissions.class));
	}

	public static IRole getAssistantRole(IGuild guild) {
		IRole role = null;
		for (IRole r : guild.getRolesByName(ROLE_NAME)) {
			role = r;
			break;
		}
		if (role == null) {
			role = guild.createRole();
			role.changeName(ROLE_NAME);
			role.changePermissions(USED_PERMISSIONS);
		}
		return role;
	}

	public static void setAssistantRole(IGuild guild, IUser user) {
		if (!isAssistant(user))
			return;
		user.addRole(getAssistantRole(guild));
	}

	public static boolean isAssistant(IUser user) {
		return ASSISTANT_IDS.contains(user.getLongID());
	}

	public static List<IDiscordClient> getClients() {
		return Collections.unmodifiableList(CLIENTS);
	}

	public static IDiscordClient getResponsibleClient(IMessage msg) {
		long id = msg.getAuthor().getLongID();
		for (Entry<Long, IDiscordClient> e : CLIENT_MAP.entrySet())
			if (e.getKey().equals(id))
				return e.getValue();
		return null;
	}

}
