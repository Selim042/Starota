package us.myles_selim.starota.webserver;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.Properties;

import com.sun.net.httpserver.HttpServer;

<<<<<<< HEAD
public class WebServer {

	public static int PORT = 7366;
	public static final String USER_AGENT = "Mozilla/5.0";

	private static boolean inited = false;
	private static final Properties PROPERTIES = new Properties();
	protected static String CLIENT_SECRET;
	protected static String CLIENT_ID;
=======
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.webserver.webhooks.HttpHandlerWebhooks;

// https://www.reddit.com/r/discordapp/comments/82p8i6/a_basic_tutorial_on_how_to_get_the_most_out_of/
@SuppressWarnings("restriction")
public class WebServer {

	public static int PORT = 7366;
	public static final String USER_AGENT = "Starota HTTP Access/" + Starota.VERSION;
	// "Mozilla/5.0";

	private static boolean inited = false;
	private static final Properties PROPERTIES = new Properties();
>>>>>>> a4a82c7032bcb3f84869f46a284022295961fee0

	public static void init() {
		if (inited)
			return;
		inited = true;
		try {
			PROPERTIES.load(new FileInputStream("starota.properties"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
<<<<<<< HEAD
		CLIENT_SECRET = PROPERTIES.getProperty("client_secret");
		CLIENT_ID = PROPERTIES.getProperty("client_id");
=======
>>>>>>> a4a82c7032bcb3f84869f46a284022295961fee0

		try {
			HttpServer server = HttpServer
					.create(new InetSocketAddress(InetAddress.getByName("0.0.0.0"), PORT), 100);
<<<<<<< HEAD
			server.createContext("/profiles/", new HttpHandlerProfiles());
			server.createContext("/login/", new HttpHandlerLogin());
=======
			server.createContext("/webhooks/", new HttpHandlerWebhooks());
>>>>>>> a4a82c7032bcb3f84869f46a284022295961fee0
			server.setExecutor(null);
			server.start();
		} catch (IOException e) {
			e.printStackTrace();
			inited = false;
		}
	}

	public static boolean isRunning() {
		return inited;
	}

	public static void main(String... args) {
		init();
	}

}
