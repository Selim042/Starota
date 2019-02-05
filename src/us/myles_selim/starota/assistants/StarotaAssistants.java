package us.myles_selim.starota.assistants;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.ActivityType;
import sx.blah.discord.handle.obj.StatusType;
import sx.blah.discord.util.DiscordException;
import us.myles_selim.starota.Starota;

public class StarotaAssistants {

	private static final List<IDiscordClient> CLIENTS = new ArrayList<>();
	private static boolean inited = false;

	public static void init() {
		if (inited)
			return;
		inited = true;

		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream("starota.properties"));
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
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
				client.changePresence(StatusType.IDLE, ActivityType.LISTENING, "to " + Starota.BOT_NAME);
				CLIENTS.add(client);
			}
		}
	}

}
