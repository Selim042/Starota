package us.myles_selim.starota.webserver;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.pdfbox.io.IOUtils;

import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.TextChannel;
import discord4j.core.object.entity.User;
import discord4j.core.object.util.Snowflake;
import reactor.core.publisher.Flux;
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.commands.credits.Credit;
import us.myles_selim.starota.commands.credits.CreditSet;
import us.myles_selim.starota.commands.credits.Creditable;
import us.myles_selim.starota.commands.credits.EnumCreditType;
import us.myles_selim.starota.enums.EnumDonorPerm;
import us.myles_selim.starota.enums.EnumWeather;
import us.myles_selim.starota.misc.utils.EmojiServerHelper;
import us.myles_selim.starota.misc.utils.MiscUtils;
import us.myles_selim.starota.misc.utils.StarotaConstants;
import us.myles_selim.starota.webserver.OAuthUtils.OAuthGuildPart;
import us.myles_selim.starota.webserver.OAuthUtils.OAuthUser;
import us.myles_selim.starota.webserver.webhook_data.TopGGVote;
import us.myles_selim.starota.wrappers.StarotaServer;

// https://www.reddit.com/r/discordapp/comments/82p8i6/a_basic_tutorial_on_how_to_get_the_most_out_of/
@SuppressWarnings("restriction")
public class WebServer {

	public static int PORT = 7366;
	public static final String USER_AGENT = "Starota HTTP Access/" + StarotaConstants.VERSION;

	private static final long[] OTHER_IGNORED_SERVERS = new long[] { //
			481646364716040202L, // Bot Test
			517546213520965662L, // Bot Debug
			264445053596991498L, // Discord Bot List
	};

	private static boolean inited = false;
	private static final Properties PROPERTIES = new Properties();
	private static final Gson GSON = new Gson();

	protected static String CLIENT_SECRET;
	protected static String TOP_GG_AUTH;

	public static void init() {
		if (inited)
			return;
		inited = true;
		try {
			PROPERTIES.load(new FileInputStream("starota.properties"));
			CLIENT_SECRET = PROPERTIES.getProperty("oauth_client_secret");
			TOP_GG_AUTH = PROPERTIES.getProperty("top_gg_auth");
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
			server.createContext("/data/guilds", new HttpHandler() {

				@Override
				public void handle(HttpExchange ex) throws IOException {
					try {
						String ret = "{}";
						if (isLoggedIn(ex)) {
							Map<String, Cookie> cookies = getCookies(ex);
							OAuthGuildPart[] userGuilds = filterGuilds(
									OAuthUtils.getUserGuilds(cookies.get("token").value));
							ret = GSON.toJson(userGuilds);
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

			// TODO: for proper permission system
			// server.createContext("/data/permissions", new HttpHandler() {
			//
			// @Override
			// public void handle(HttpExchange ex) throws IOException {
			// try {
			// if (!isLoggedIn(ex)) {
			// notAllowed(ex);
			// return;
			// }
			// OAuthUser oUser =
			// OAuthUtils.getUser(getCookies(ex).get("token").value);
			// Snowflake guildId = getGuild(ex);
			// boolean inGuild = Starota.getClient().getGuilds()
			// .any((g) -> g.getId().equals(guildId)).block();
			// if (!inGuild) {
			// notAllowed(ex);
			// return;
			// }
			// Guild guild = Starota.getGuild(guildId);
			// if
			// (!guild.getMemberById(Snowflake.of(oUser.id)).block().getBasePermissions()
			// .block().contains(Permission.ADMINISTRATOR)) {
			// notAllowed(ex);
			// return;
			// }
			//
			// File permFile = new File(PermissionsIO.PERMISSION_FOLDER,
			// guildId.asString() + ".starota_perms");
			// String ret;
			// if (permFile.exists()) {
			// StringBuilder contents = new StringBuilder();
			// FileReader in = new FileReader(permFile);
			// for (char c = (char) in.read(); in.ready(); c = (char) in.read())
			// contents.append(c);
			// in.close();
			// ret = contents.toString();
			// } else
			// ret = "";
			//
			// byte[] dat = ret.toString().getBytes("UTF-8");
			// ex.sendResponseHeaders(200, dat.length);
			// OutputStream out = ex.getResponseBody();
			// out.write(dat);
			// out.close();
			// } catch (Exception e) {
			// e.printStackTrace();
			// returnError(ex, e);
			// }
			// }
			//
			// private void notAllowed(HttpExchange ex) {
			// try {
			// String ret = "{\"error_message\"=\"not allowed\"}";
			// byte[] dat = ret.getBytes("UTF-8");
			// ex.sendResponseHeaders(200, dat.length);
			// OutputStream out = ex.getResponseBody();
			// out.write(dat);
			// out.close();
			// } catch (IOException e) {
			// e.printStackTrace();
			// }
			// }
			//
			// });
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
			server.createContext("/leaderboards", new HttpHandlerLeaderboard());
			server.createContext("/server_select", new HttpHandler() {

				@Override
				public void handle(HttpExchange ex) throws IOException {
					returnTextFile(ex, "http/server_select.html", 200);
				}
			});
			server.createContext("/webhooks/top-gg", new HttpHandler() {

				@Override
				public void handle(HttpExchange ex) throws IOException {
					Headers reqHeaders = ex.getRequestHeaders();
					if (reqHeaders.containsKey("Authorization")) {
						if (reqHeaders.get("Authorization").contains(TOP_GG_AUTH)) {
							TopGGVote vote = GSON.fromJson(new InputStreamReader(ex.getRequestBody()),
									TopGGVote.class);
							if (Starota.FULLY_STARTED) {
								// Starota.getClient().getUserById(StarotaConstants.SELIM_USER_ID).block()
								// .getPrivateChannel().block().createMessage(vote.toString())
								// .block();
								User voter = vote.getUserObject();
								voter.getPrivateChannel().block().createMessage(vote.isWeekend()
										? "Thank you for voting for Starota on Top.gg on the weekend for double points!"
										: "Thank you for voting for Starota on Top.gg!").block();
								TextChannel voteNotifyChannel = ((TextChannel) Starota.getClient()
										.getChannelById(StarotaConstants.VOTE_NOTIFY_CHANNEL_ID)
										.block());
								// Message voteMsg;
								// if
								// (voter.asMember(voteNotifyChannel.getGuildId()).block()
								// != null)
								// voteMsg = voteNotifyChannel.createMessage(
								// "Thanks to " + voter.getMention() + " for
								// voting!");
								// else
								voteNotifyChannel.createMessage("Thanks to " + voter.getMention()
										+ " for voting!\n"
										+ "To vote for Starota on Top.gg visit https://top.gg/bot/489245655710040099/vote.\n"
										+ "Every vote helps!").block();
								// voteMsg.block();
							}
						}
					}
					ex.sendResponseHeaders(200, 1);
					OutputStream response = ex.getResponseBody();
					response.write('1');
					response.close();
				}
			});
			server.createContext("/credits", new HttpHandler() {

				@Override
				public void handle(HttpExchange ex) throws IOException {
					try {
						Map<String, String> replace = new HashMap<>();
						replace.put("{VERSION}",
								"v" + StarotaConstants.VERSION + (Starota.IS_DEV ? "d" : ""));

						StringBuilder builder = new StringBuilder();
						for (Creditable creditable : Starota.getCredits()) {
							EnumCreditType type = creditable.getType();
							if (creditable instanceof Credit) {
								Credit credit = (Credit) creditable;
								if (credit.getLink() == null)
									builder.append(String.format("<strong>%1$s</strong>: %2$s<br>",
											credit.getType() == EnumCreditType.OTHER ? credit.getTitle()
													: credit.getType().getDisplay(),
											credit.getName()));
								else
									builder.append(String.format(
											"<strong>%1$s</strong>: <a href=\"%3$s\">%2$s</a><br>",
											credit.getType() == EnumCreditType.OTHER ? credit.getTitle()
													: credit.getType().getDisplay(),
											credit.getName(), credit.getLink()));
							} else if (creditable instanceof CreditSet) {
								builder.append(
										String.format("<strong>%s</strong>:<br>", type.getDisplay()));
								for (Creditable credit2 : (CreditSet) creditable) {
									if (!(credit2 instanceof Credit))
										continue;
									Credit credit = (Credit) credit2;
									if (type == EnumCreditType.OTHER) {
										if (credit.getLink() == null)
											builder.append(String.format("- %1$s (%2$s)<br>",
													credit.getName(), credit.getTitle()));
										else
											builder.append(String.format(
													"- <a href=\"%2$s\">%1$s</a> (%3$s)<br>",
													credit.getName(), credit.getLink(),
													credit.getTitle()));
									} else {
										if (credit.getLink() == null)
											builder.append(
													String.format("- %1$s<br>", credit.getName()));
										else
											builder.append(
													String.format("- <a href=\"%2$s\">%1$s</a><br>",
															credit.getName(), credit.getLink()));
									}
								}
							}
						}
						replace.put("{CREDITS}", builder.toString());
						returnTextFile(ex, "http/credits.html", replace);
					} catch (Exception e) {
						returnError(ex, e);
						e.printStackTrace();
					}
				}
			});
			server.createContext("/server_settings", new HttpHandlerServerSettings());
			server.createContext("/", new HttpHandlerDashboard());
			server.setExecutor(null);
			server.start();
		} catch (IOException e) {
			e.printStackTrace();
			inited = false;
		}
	}

	@SuppressWarnings("deprecation")
	protected static String getLoginHTML(HttpExchange ex, String text) {
		try {
			return String.format("<a href=\"/login?p=%s\">%s</a>",
					URLEncoder.encode(ex.getRequestURI().toString(), "UTF-8"), text);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return String.format("<a href=\"/login?p=%s\">%s</a>",
					URLEncoder.encode(ex.getRequestURI().toString()), text);
		}
	}

	protected static InputStream getResourceFile(String path) {
		InputStream file = WebServer.class.getClassLoader().getResourceAsStream(path);
		if (file == null)
			throw new IllegalArgumentException("file " + path + " not found");
		return file;
	}

	protected static String getTemplate(String name) {
		try {
			InputStream file = getResourceFile("http/templates/" + name);

			BufferedReader reader = new BufferedReader(new InputStreamReader(file));
			StringBuilder data = new StringBuilder();
			int c = reader.read();
			while (c != -1) {
				data.append((char) c);
				c = reader.read();
			}
			reader.close();
			return data.toString();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	protected static Snowflake getGuild(HttpExchange ex) {
		handleBaseGET(ex);
		Cookie serverCookie = WebServer.getCookies(ex).get("current_server");
		if (serverCookie == null)
			return null;
		Snowflake id = Snowflake.of(serverCookie.value);
		if (id.asLong() == 314733127027130379L || Starota.IS_DEV)
			return id;
		return null;
	}

	protected static OAuthGuildPart[] filterGuilds(OAuthGuildPart[] userGuilds) {
		List<OAuthGuildPart> botGuilds = new ArrayList<>();
		if (userGuilds != null) {
			Flux<Guild> guilds = Starota.getClient().getGuilds();
			for (OAuthGuildPart gp : userGuilds) {
				if (MiscUtils.arrContains(OTHER_IGNORED_SERVERS, Long.parseLong(gp.id))
						|| EmojiServerHelper.isEmojiServer(Snowflake.of(gp.id)))
					continue;
				if (guilds.any((g) -> {
					if (!Starota.IS_DEV
							&& !StarotaServer.getServer(g).getVoteRewards().contains(EnumDonorPerm.HTTP))
						return false;
					return g.getId().asString().equals(gp.id);
				}).block())
					botGuilds.add(gp);
			}
		}
		return botGuilds.toArray(new OAuthGuildPart[0]);
	}

	protected static boolean isLoggedIn(HttpExchange ex) {
		Cookie token = getCookies(ex).get("token");
		if (token == null || !OAuthUtils.isTokenValid(token.value))
			return false;
		return true;
	}

	protected static void setContentType(HttpExchange ex, String fileName) {
		String mimeType = URLConnection.guessContentTypeFromName(fileName);
		if (fileName.contains(".css"))
			mimeType = "text/css";
		else if (fileName.contains(".js"))
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
		boolean inGuild;
		Cookie serverCookie = WebServer.getCookies(ex).get("current_server");
		Guild guild;
		Snowflake serverId;
		if (serverCookie == null) {
			inGuild = false;
			serverId = null;
		} else {
			guild = null;
			serverId = Snowflake.of(serverCookie.value);
			inGuild = Starota.getClient().getGuilds().any((g) -> g.getId().equals(serverId)).block();
		}
		EnumWeather[] boosts = new EnumWeather[0];

		if (inGuild) {
			guild = Starota.getGuild(serverId);
			template = template.replace("{SERVER_NAME}", guild.getName());
			StarotaServer server = StarotaServer.getServer(guild);
			boosts = server.getCurrentPossibleBoosts();
		} else
			template = template.replace("{SERVER_NAME}", "No server selected");

		if (boosts.length > 0) {
			template = template.replace("{WEATHER_ICON}", boosts[0].getEmoji().getImageUrl());
			template = template.replace("{WEATHER_VISIBILITY}", "");
		} else {
			template = template.replace("{WEATHER_ICON}", "");
			template = template.replace("{WEATHER_VISIBILITY}", "invisible");
		}
		if (user != null)
			template = template.replace("{LOGGED_IN_DISCORD_AVATAR}", user.getAvatarURL());
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

	protected static void returnTextFile(HttpExchange ex, String fileName) {
		returnTextFile(ex, fileName, new HashMap<>());
	}

	protected static void returnTextFile(HttpExchange ex, String fileName, int code) {
		returnTextFile(ex, fileName, code, new HashMap<>());
	}

	protected static void returnTextFile(HttpExchange ex, String fileName, Map<String, String> replace) {
		returnTextFile(ex, fileName, 200, replace);
	}

	protected static void returnTextFile(HttpExchange ex, String fileName, int code,
			Map<String, String> replace) {
		try {
			InputStream file = getResourceFile(fileName);
			setContentType(ex, fileName);

			OutputStream response = ex.getResponseBody();
			BufferedReader reader = new BufferedReader(new InputStreamReader(file));
			StringBuilder data = new StringBuilder();
			int c = reader.read();
			while (c != -1) {
				data.append((char) c);
				c = reader.read();
			}
			reader.close();
			String dataS = data.toString();
			for (Entry<String, String> e : replace.entrySet())
				dataS = dataS.replace(e.getKey(), e.getValue());
			Map<String, Cookie> cookies = getCookies(ex);
			dataS = fillBaseStuff(ex, cookies.containsKey("token") ? cookies.get("token").value : null,
					dataS);
			byte[] dataB = dataS.getBytes();
			ex.sendResponseHeaders(code, dataB.length);
			response.write(dataB);
			response.close();
		} catch (IOException e) {
			e.printStackTrace();
			returnError(ex, e);
		}
	}

	protected static void returnBinaryFile(HttpExchange ex, String fileName) {
		returnBinaryFile(ex, fileName, 200);
	}

	protected static void returnBinaryFile(HttpExchange ex, String fileName, int code) {
		try {
			InputStream file = getResourceFile(fileName);
			setContentType(ex, fileName);

			ex.sendResponseHeaders(code, file.available());
			OutputStream respBody = ex.getResponseBody();
			IOUtils.copy(file, respBody);
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
		rep.put("{ERROR}", message);
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
		rep.put("{ERROR}", message);
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
