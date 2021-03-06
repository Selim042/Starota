package us.myles_selim.starota.webserver;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.Properties;

import com.sun.net.httpserver.HttpServer;

import us.myles_selim.starota.misc.utils.StarotaConstants;
import us.myles_selim.starota.webserver.api.HttpHandlerAPIProfiles;

// https://www.reddit.com/r/discordapp/comments/82p8i6/a_basic_tutorial_on_how_to_get_the_most_out_of/
@SuppressWarnings("restriction")
public class WebServer {

	public static int PORT = 7366;
	public static final String USER_AGENT = "Starota HTTP Access/" + StarotaConstants.VERSION;

	private static boolean inited = false;
	private static final Properties PROPERTIES = new Properties();

	public static void init() {
		if (inited)
			return;
		inited = true;
		try {
			PROPERTIES.load(new FileInputStream("starota.properties"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		try {
			HttpServer server = HttpServer
					.create(new InetSocketAddress(InetAddress.getByName("0.0.0.0"), PORT), 100);
			// server.createContext("/webhooks/", new HttpHandlerWebhooks());
			server.createContext("/api/profiles/", new HttpHandlerAPIProfiles());
			server.createContext("/settings/", new HttpHandlerSettings());
			server.setExecutor(null);
			server.start();
		} catch (IOException e) {
			e.printStackTrace();
			inited = false;
		}
	}

	// public static boolean isAuthorized(BotServer server, String token) {
	//
	// }

	public static boolean isRunning() {
		return inited;
	}

	public static void main(String... args) {
		init();
	}

}
