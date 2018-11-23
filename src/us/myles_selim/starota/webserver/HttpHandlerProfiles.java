package us.myles_selim.starota.webserver;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IUser;
import us.myles_selim.starota.EnumTeam;
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.profiles.PlayerProfile;
import us.myles_selim.starota.profiles.ProfileManager;

public class HttpHandlerProfiles implements HttpHandler {

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		try {

			// Headers responseHeaders = exchange.getResponseHeaders();
			// List<String> cookies = new ArrayList<>();
			// cookies.add("accessToken=" + "j6iX5i9sOnXqyrNp7dWx1qz4qdjm0A" +
			// ";
			// Path=/;");
			// responseHeaders.put("Set-Cookie", cookies);

			Headers requestHeaders = exchange.getRequestHeaders();
			if (!requestHeaders.containsKey("Cookie")) {
				WebServerUtils.notLoggedIn(exchange);
				return;
			}
			List<String> requestCookies = requestHeaders.get("Cookie");
			String accessToken = null;
			for (String c : requestCookies) {
				if (!c.startsWith("accessToken"))
					continue;
				accessToken = c.substring(c.indexOf("=") + 1, c.length());
				break;
			}
			if (accessToken == null) {
				WebServerUtils.notLoggedIn(exchange);
				return;
			}
			List<Long> servers = OAuthUtils.getUserGuilds(accessToken);

			String[] uriParams = exchange.getRequestURI().toString().split("/");
			long guildId = Long.parseLong(uriParams[2]);
			long userId = Long.parseLong(uriParams[3]);
			IUser user = Starota.getUser(userId);
			IGuild server = Starota.getGuild(guildId);

			if (!servers.contains(guildId) || user == null || server == null) {
				String response = "403 error";
				exchange.sendResponseHeaders(403, response.length());
				OutputStream stream = exchange.getResponseBody();
				stream.write(response.getBytes());
				stream.flush();
				stream.close();
				exchange.close();
				return;
			}

			BufferedReader reader = new BufferedReader(new FileReader(new File("html", "profile.html")));
			String response = "";
			String line = reader.readLine();
			while (line != null) {
				response += line + "\n";
				line = reader.readLine();
			}
			reader.close();

			response = response.replaceAll("\\[DISCORD_NAME\\]", user.getName());
			response = response.replaceAll("\\[DISCORD_SERVER\\]", server.getName());
			PlayerProfile profile = ProfileManager.getProfile(guildId, userId);
			if (profile != null) {
				response = response.replaceAll("\\[POGO_NAME\\]", profile.getPoGoName());
				response = response.replaceAll("\\[POGO_LEVEL\\]", Integer.toString(profile.getLevel()));
				if (profile.getRealName() != null)
					response = response.replaceAll("\\[REAL_NAME\\]", profile.getRealName());
				if (profile.getTrainerCode() > 0)
					response = response.replaceAll("\\[T_CODE_STRING\\]",
							profile.getTrainerCodeString());
				EnumTeam team = profile.getTeam();
				response = response.replaceAll("\\[TEAM_NAME\\]", profile.getTeam().getName());
				response = response.replaceAll("\\[TEAM_ICON\\]", team.getIcon());
				response = response.replaceAll("\\[TEAM_COLOR\\]", Integer.toString(team.getColor()));
				// Map<String, Long> alts = profile.getAlts();
				// if (alts != null && !alts.isEmpty()) {
				// response += "Alternate accounts:<br>\n";
				// for (Entry<String, Long> e : alts.entrySet())
				// response += " - " + e.getKey() + ": "
				// + ProfileManager.getTrainerCodeString(e.getValue()) +
				// "<br>\n";
				// }
				response = response.replaceAll("\\[LAST_UPDATED\\]",
						profile.getLastUpdated().toString());
			}

			exchange.sendResponseHeaders(200, response.length());
			OutputStream output = exchange.getResponseBody();
			output.write(response.getBytes());
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
