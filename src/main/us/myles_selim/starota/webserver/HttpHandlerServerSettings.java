package us.myles_selim.starota.webserver;

import java.io.IOException;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Member;
import discord4j.core.object.util.Permission;
import discord4j.core.object.util.Snowflake;
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.webserver.OAuthUtils.OAuthUser;
import us.myles_selim.starota.webserver.WebServer.Cookie;
import us.myles_selim.starota.wrappers.StarotaServer;

@SuppressWarnings("restriction")
public class HttpHandlerServerSettings implements HttpHandler {

	@Override
	public void handle(HttpExchange ex) throws IOException {
		try {
			if (!WebServer.isLoggedIn(ex)) {
				WebServer.redirect(ex, "/login");
				return;
			}
			Cookie tokenCookie = WebServer.getCookies(ex).get("token");
			Cookie serverCookie = WebServer.getCookies(ex).get("current_server");
			if (serverCookie == null) {
				WebServer.returnServer404(ex, "Please select a server");
				return;
			}
			Snowflake serverId = Snowflake.of(serverCookie.value);
			boolean inGuild = Starota.getClient().getGuilds().any((g) -> g.getId().equals(serverId))
					.block();
			if (!inGuild) {
				WebServer.return404(ex);
				return;
			}
			Guild guild = Starota.getGuild(serverId);
			StarotaServer server = StarotaServer.getServer(guild);
			Map<String, String> post = WebServer.getPOST(ex);

			OAuthUser loggedIn = OAuthUtils.getUser(tokenCookie.value);
			Snowflake userId = Snowflake.of(loggedIn.id);
			Member member = guild.getMemberById(userId).block();
			if (!member.getBasePermissions().block().contains(Permission.ADMINISTRATOR)) {
				WebServer.return404(ex);
				return;
			}
			WebServer.returnTextFile(ex, "http/server_settings.html");
		} catch (Exception e) {
			e.printStackTrace();
			WebServer.returnError(ex, e);
		}
	}

}
