package us.myles_selim.starota.webserver;

import java.awt.Color;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import discord4j.core.object.entity.TextChannel;
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.webserver.OAuthUtils.OAuthGuildPart;
import us.myles_selim.starota.webserver.OAuthUtils.OAuthLogin;
import us.myles_selim.starota.webserver.OAuthUtils.OAuthUser;

@SuppressWarnings("restriction")
public class HttpHandlerLogin implements HttpHandler {

	@Override
	public void handle(HttpExchange ex) throws IOException {
		try {
			Map<String, String> get = WebServer.getGET(ex);
			if (get.containsKey("code")) {
				OAuthLogin login = OAuthUtils.authenticate(get.get("code"), getRedirectURI(ex));
				Headers response = ex.getResponseHeaders();
				response.add("Set-Cookie", "token=" + login.access_token);

				TextChannel reportingChannel = Starota.getChannel(615735300836294657L);
				reportingChannel.createEmbed((e) -> {
					OAuthUser user = OAuthUtils.getUser(login.access_token);
					OAuthGuildPart[] guilds = OAuthUtils.getUserGuilds(login.access_token);
					if (Starota.IS_DEV) {
						e.setColor(Color.YELLOW);
						e.setTitle("New Dev Web Login");
					} else {
						e.setColor(Color.GREEN);
						e.setTitle("New Dev Web Login");
					}
					e.addField("User", String.format("%s#%s", user.username, user.discriminator), true);
					e.addField("ID", user.id, true);
					e.setThumbnail(user.getAvatarURL());
					e.addField("Email", user.email, true);

					StringBuilder servers = new StringBuilder();
					for (OAuthGuildPart gp : guilds) {
						if (Starota.getClient().getGuilds()
								.any((g) -> g.getId().asString().equals(gp.id)).block())
							servers.append(gp.name + "\n");
					}
					e.setDescription(servers.toString());
				}).block();

				OutputStream responseB = ex.getResponseBody();
				ex.getResponseHeaders().set("Location", "/");
				ex.sendResponseHeaders(302, 0);
				responseB.close();
			} else if (!WebServer.isLoggedIn(ex)) {
				Map<String, String> replace = new HashMap<>();
				replace.put("\\{REDIRECT_URL\\}", getRedirectURI(ex));
				WebServer.returnTextFile(ex, "http/login.html", replace);
				return;
			} else
				WebServer.redirect(ex, "/login");
		} catch (Exception e) {
			e.printStackTrace();
			WebServer.returnError(ex, e);
		}
	}

	private static String getRedirectURI(HttpExchange ex) {
		try {
			String path = ex.getRequestURI().toString();
			int index = path.indexOf("?");
			return URLEncoder.encode("http://" + ex.getRequestHeaders().get("Host").get(0)
					+ (index < 0 ? path : path.substring(0, index)), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

}
