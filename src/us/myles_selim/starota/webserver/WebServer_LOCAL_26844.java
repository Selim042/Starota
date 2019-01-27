package us.myles_selim.starota.webserver;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.Properties;

import com.sun.net.httpserver.HttpServer;

public class WebServer {

	public static int PORT = 7366;
	public static final String USER_AGENT = "Mozilla/5.0";

	private static boolean inited = false;
	private static final Properties PROPERTIES = new Properties();
	protected static String CLIENT_SECRET;
	protected static String CLIENT_ID;

	public static void init() {
		if (inited)
			return;
		inited = true;
		try {
			PROPERTIES.load(new FileInputStream("starota.properties"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		CLIENT_SECRET = PROPERTIES.getProperty("client_secret");
		CLIENT_ID = PROPERTIES.getProperty("client_id");

		try {
			HttpServer server = HttpServer
					.create(new InetSocketAddress(InetAddress.getByName("0.0.0.0"), PORT), 100);
			server.createContext("/profiles/", new HttpHandlerProfiles());
			server.createContext("/login/", new HttpHandlerLogin());
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
