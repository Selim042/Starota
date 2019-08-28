package us.myles_selim.starota.webserver;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import com.google.common.io.Files;
import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import discord4j.core.object.entity.Guild;
import discord4j.core.object.util.Snowflake;
import reactor.core.publisher.Flux;
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.misc.utils.StarotaConstants;
import us.myles_selim.starota.webserver.OAuthUtils.OAuthGuildPart;
import us.myles_selim.starota.webserver.OAuthUtils.OAuthUser;

// https://www.reddit.com/r/discordapp/comments/82p8i6/a_basic_tutorial_on_how_to_get_the_most_out_of/
@SuppressWarnings("restriction")
public class WebServer {

	public static int PORT = 7366;
	public static final String USER_AGENT = "Starota HTTP Access/" + StarotaConstants.VERSION;

	private static boolean inited = false;
	private static final Properties PROPERTIES = new Properties();
	private static final Gson GSON = new Gson();

	protected static String CLIENT_SECRET;

	public static void init() {
		if (inited)
			return;
		inited = true;
		try {
			PROPERTIES.load(new FileInputStream("starota.properties"));
			CLIENT_SECRET = PROPERTIES.getProperty("oauth_client_secret");
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		try {
			HttpServer server = HttpServer
					.create(new InetSocketAddress(InetAddress.getByName("0.0.0.0"), PORT), 100);
			// server.createContext("/webhooks/", new HttpHandlerWebhooks());
			// server.createContext("/api/profiles/", new
			// HttpHandlerAPIProfiles());
			// server.createContext("/settings/", new HttpHandlerSettings());
			server.createContext("/profile", new HttpHandlerProfile());
			server.createContext("/guilds", new HttpHandler() {

				@Override
				public void handle(HttpExchange ex) throws IOException {
					try {
						String ret = "{}";
						if (isLoggedIn(ex)) {
							Map<String, Cookie> cookies = getCookies(ex);
							OAuthGuildPart[] userGuilds = OAuthUtils
									.getUserGuilds(cookies.get("token").value);
							List<OAuthGuildPart> botGuilds = new ArrayList<>();
							Flux<Guild> guilds = Starota.getClient().getGuilds();
							for (OAuthGuildPart gp : userGuilds)
								if (guilds.any((g) -> g.getId().asString().equals(gp.id)).block())
									botGuilds.add(gp);
							ret = GSON.toJson(botGuilds);
						}

						byte[] dat = ret.getBytes("UTF-8");
						ex.sendResponseHeaders(200, dat.length);
						OutputStream out = ex.getResponseBody();
						out.write(dat);
						out.close();
					} catch (Exception e) {
						e.printStackTrace();
						returnError(ex, e);
					}
				}
			});
			server.createContext("/assets", new HttpHandler() {

				@Override
				public void handle(HttpExchange ex) throws IOException {
					String oldPath = ex.getRequestURI().toString();
					String newPath = URLDecoder.decode(oldPath.replace("/assets", "http/assets"),
							"UTF-8");
					if (oldPath.startsWith("/assets/img"))
						returnBinaryFile(ex, newPath);
					else
						returnTextFile(ex, newPath);
				}
			});
			server.createContext("/login", new HttpHandlerLogin());
			server.createContext("/tradeboard", new HttpHandlerTradeboard());
			server.createContext("/new_trade", new HttpHandlerNewTrade());
			server.createContext("/submit_trade", new HttpHandlerSubmitTrade());
			server.createContext("/profiles", new HttpHandlerProfiles());
			server.createContext("/", new HttpHandler() {

				@Override
				public void handle(HttpExchange ex) throws IOException {
					returnTextFile(ex, "http/index.html");
				}
			});
			server.setExecutor(null);
			server.start();
		} catch (IOException e) {
			e.printStackTrace();
			inited = false;
		}
	}

	protected static boolean isLoggedIn(HttpExchange ex) {
		Cookie token = getCookies(ex).get("token");
		if (token == null || !OAuthUtils.isTokenValid(token.value))
			return false;
		return true;
	}

	protected static void setContentType(HttpExchange ex, String file) {
		String mimeType = URLConnection.guessContentTypeFromName(file);
		if (file.contains(".css"))
			mimeType = "text/css";
		else if (file.contains(".js"))
			mimeType = "application/javascript";
		Headers respHeaders = ex.getResponseHeaders();
		respHeaders.set("Content-Type", mimeType);
	}

	protected static void handleBaseGET(HttpExchange ex) {
		Headers resp = ex.getResponseHeaders();
		Map<String, String> get = getGET(ex);
		if (get.containsKey("server"))
			resp.set("Set-Cookie", "current_server=" + get.get("server") + "; Path=/");
	}

	protected static String fillBaseStuff(HttpExchange ex, String token, String template) {
		OAuthUser user = OAuthUtils.getUser(token);
		Cookie serverCookie = WebServer.getCookies(ex).get("current_server");
		Snowflake serverId = Snowflake.of(serverCookie.value);
		boolean inGuild = Starota.getClient().getGuilds().any((g) -> g.getId().equals(serverId)).block();
		if (inGuild)
			template = template.replaceAll("\\{SERVER_NAME\\}", Starota.getGuild(serverId).getName());
		else
			template = template.replaceAll("\\{SERVER_NAME\\}", "No server selected");
		template = template.replaceAll("\\{LOGGED_IN_DISCORD_AVATAR\\}", user.getAvatarURL());
		return template;
	}

	protected static void redirect(HttpExchange ex, String path) {
		redirect(ex, path, 302);
	}

	protected static void redirect(HttpExchange ex, String path, int code) {
		try {
			OutputStream response = ex.getResponseBody();
			ex.getResponseHeaders().set("Location", path);
			ex.sendResponseHeaders(code, 0);
			response.close();
		} catch (IOException e) {
			e.printStackTrace();
			returnError(ex, e);
		}
	}

	protected static void returnTextFile(HttpExchange ex, String file) {
		returnTextFile(ex, file, new HashMap<>());
	}

	protected static void returnTextFile(HttpExchange ex, String file, int code) {
		returnTextFile(ex, file, code, new HashMap<>());
	}

	protected static void returnTextFile(HttpExchange ex, String file, Map<String, String> replace) {
		returnTextFile(ex, file, 200, replace);
	}

	protected static void returnTextFile(HttpExchange ex, String file, int code,
			Map<String, String> replace) {
		try {
			setContentType(ex, file);

			OutputStream response = ex.getResponseBody();
			BufferedReader reader = new BufferedReader(new FileReader(file));
			StringBuilder data = new StringBuilder();
			int c = reader.read();
			while (c != -1) {
				data.append((char) c);
				c = reader.read();
			}
			reader.close();
			String dataS = data.toString();
			for (Entry<String, String> e : replace.entrySet())
				dataS = dataS.replaceAll(e.getKey(), e.getValue());
			byte[] dataB = dataS.getBytes();
			ex.sendResponseHeaders(code, dataB.length);
			response.write(dataB);
			response.close();
		} catch (IOException e) {
			e.printStackTrace();
			returnError(ex, e);
		}
	}

	protected static void returnBinaryFile(HttpExchange ex, String file) {
		returnBinaryFile(ex, file, 200);
	}

	protected static void returnBinaryFile(HttpExchange ex, String file, int code) {
		try {
			setContentType(ex, file);

			File f = new File(file);
			ex.sendResponseHeaders(code, f.length());
			OutputStream respBody = ex.getResponseBody();
			Files.copy(f, respBody);
			respBody.close();
		} catch (IOException e) {
			e.printStackTrace();
			returnError(ex, e);
		}
	}

	protected static void return404(HttpExchange ex) {
		return404(ex, "Page not found");
	}

	protected static void return404(HttpExchange ex, String message) {
		Map<String, String> rep = new HashMap<>();
		rep.put("\\{ERROR\\}", message);
		returnTextFile(ex, "http/404.html", 404, rep);
	}

	protected static void returnError(HttpExchange ex, int code) {
		try {
			ex.sendResponseHeaders(code, 0);
			ex.getResponseBody().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected static void returnServer404(HttpExchange ex) {
		return404(ex, "Page not found");
	}

	protected static void returnServer404(HttpExchange ex, String message) {
		Map<String, String> rep = new HashMap<>();
		rep.put("\\{ERROR\\}", message);
		returnTextFile(ex, "http/404_server.html", 404, rep);
	}

	protected static void returnError(HttpExchange ex, Throwable thrown) {
		String err = "An " + thrown.getClass().getCanonicalName()
				+ " was thrown when trying to complete your request.  Please report this error.";
		OutputStream response = ex.getResponseBody();
		try {
			ex.sendResponseHeaders(500, err.length());
			response.write(err.getBytes());
			response.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	protected static Map<String, String> getGET(HttpExchange ex) {
		Map<String, String> ret = new HashMap<>();
		String[] gets = ex.getRequestURI().toString().replaceFirst(".*?\\?", "").split("&");
		for (String g : gets) {
			String[] p = g.split("=");
			if (p.length == 2)
				try {
					ret.put(p[0], URLDecoder.decode(p[1], "UTF-8"));
				} catch (UnsupportedEncodingException e) {
					ret.put(p[0], p[1]);
				}
			else if (p.length > 2) {
				StringBuilder val = new StringBuilder(p[1]);
				for (int i = 2; i < p.length; i++)
					val.append("=" + p[i]);
				try {
					ret.put(p[0], URLDecoder.decode(val.toString(), "UTF-8"));
				} catch (UnsupportedEncodingException e) {
					ret.put(p[0], val.toString());
				}
			}
		}
		return ret;
	}

	public static Map<String, String> getPOST(HttpExchange ex) {
		Map<String, String> ret = new HashMap<>();
		BufferedReader reader = new BufferedReader(new InputStreamReader(ex.getRequestBody()));
		reader.lines().forEach((l) -> {
			for (String p : l.split("&")) {
				String[] ps = p.split("=");
				if (ps.length == 2)
					ret.put(ps[0], ps[1]);
				else if (ps.length > 2) {
					StringBuilder val = new StringBuilder(ps[1]);
					for (int i = 2; i < ps.length; i++)
						val.append("=" + ps[i]);
					ret.put(ps[0], val.toString());
				}
			}
		});
		return ret;
	}

	protected static Map<String, Cookie> getCookies(HttpExchange ex) {
		Map<String, Cookie> ret = new HashMap<>();
		List<String> cookies = ex.getRequestHeaders().get("Cookie");
		if (cookies == null)
			return ret;
		if (ex.getResponseHeaders().containsKey("Set-Cookie"))
			cookies.addAll(ex.getResponseHeaders().get("Set-Cookie"));
		for (String l : cookies) {
			for (String p : l.split("; ")) {
				String[] pair = p.split("=");
				ret.put(pair[0], new Cookie(pair[0], pair[1]));
			}
		}
		return ret;
	}

	protected static String getJSObject(Object obj) {
		StringBuilder out = new StringBuilder("{");
		try {
			for (Field f : obj.getClass().getDeclaredFields()) {
				Object val = f.get(obj);
				if (val == null)
					out.append(String.format("%s:null,", f.getName()));
				if (val instanceof String)
					out.append(String.format("%s:\"%s\",", f.getName(), val));
				if (val instanceof Number)
					out.append(String.format("%s:%s,", f.getName(), val.toString()));
			}
		} catch (IllegalArgumentException | IllegalAccessException e) {}
		return out.toString() + "}";
	}

	public static boolean isRunning() {
		return inited;
	}

	public static void main(String... args) {
		init();
	}

	protected static class Cookie {

		public String name;
		public String value;

		public Cookie() {}

		public Cookie(String name, String value) {
			this.name = name;
			this.value = value;
		}
	}

}
