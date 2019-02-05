package us.myles_selim.starota.assistants.pokedex;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.discordbots.api.client.DiscordBotListAPI;

import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventDispatcher;
import sx.blah.discord.handle.obj.ActivityType;
import sx.blah.discord.handle.obj.StatusType;
import sx.blah.discord.util.DiscordException;
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.reaction_messages.ReactionMessageRegistry;

public class PokedexBot {

	public static IDiscordClient POKEDEX_CLIENT;

	private static Properties PROPERTIES = new Properties();;
	private static DiscordBotListAPI BOT_LIST;
	private static boolean started = false;

	public static void start(ReactionMessageRegistry reactionRegistry) {
		if (started || Starota.IS_DEV)
			return;
		started = true;

		ClientBuilder builder = new ClientBuilder();
		try {
			PROPERTIES.load(new FileInputStream("starota.properties"));
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		builder.withToken(PROPERTIES.getProperty("pokedex_bot"));
		try {
			POKEDEX_CLIENT = builder.login();
		} catch (DiscordException e) {
			e.printStackTrace();
			System.err.println("failed to start Pokedex bot");
			return;
		}
		try {
			while (!POKEDEX_CLIENT.isReady())
				Thread.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Thread statusUpdater = new Thread("PokedexStatusUpdater") {

			@Override
			public void run() {
				while (true) {
					POKEDEX_CLIENT.changePresence(StatusType.ONLINE, ActivityType.PLAYING,
							"v" + Starota.VERSION + (Starota.DEBUG || Starota.IS_DEV ? "d" : ""));
					try {
						Thread.sleep(3600000); // 1 hour
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		statusUpdater.start();

		EventDispatcher dispatcher = POKEDEX_CLIENT.getDispatcher();
		dispatcher.registerListener(new PokedexEventHandler());
		dispatcher.registerListener(reactionRegistry);
	}

	public static DiscordBotListAPI getBotListAPI() {
		if (BOT_LIST == null && PROPERTIES.containsKey("bot_list_token"))
			BOT_LIST = new DiscordBotListAPI.Builder().token(PROPERTIES.getProperty("bot_list_token"))
					.botId(POKEDEX_CLIENT.getOurUser().getStringID()).build();
		return BOT_LIST;
	}

}
