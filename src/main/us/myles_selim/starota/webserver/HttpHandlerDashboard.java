package us.myles_selim.starota.webserver;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import discord4j.core.object.util.Snowflake;
import us.myles_selim.starota.trading.TradeboardPost;
import us.myles_selim.starota.webserver.OAuthUtils.OAuthUser;
import us.myles_selim.starota.wrappers.StarotaServer;

public class HttpHandlerDashboard implements HttpHandler {

	private static final double ONE_WEEK = 6.048e8;

	@Override
	public void handle(HttpExchange ex) throws IOException {
		WebServer.handleBaseGET(ex);
		if (!WebServer.isLoggedIn(ex)) {
			WebServer.redirect(ex, "/login");
			return;
		}
		StarotaServer server = StarotaServer.getServer(WebServer.getGuild(ex));
		Map<String, String> replace = new HashMap<>();
		List<TradeboardPost> posts = server.getPosts();
		OAuthUser user = OAuthUtils.getUser(WebServer.getCookies(ex).get("token").value);
		replace.put("{SERVER_OPEN_TRADES}", String.format("%,d", posts.size()));

		int personalOpenTrades = 0;
		int newServerTradesWeek = 0;
		int personalOldTradesWeek = 0;
		for (TradeboardPost post : posts) {
			long timeDiff = System.currentTimeMillis() - post.getTimePosted().toEpochMilli();
			if (post.getOwnerSnowflake().equals(Snowflake.of(user.id))) {
				personalOpenTrades++;
				if (timeDiff > ONE_WEEK)
					personalOldTradesWeek++;
			}
			if (timeDiff < ONE_WEEK)
				newServerTradesWeek++;
		}
		replace.put("{PERSONAL_OPEN_TRADES}", String.format("%,d", personalOpenTrades));
		replace.put("{NEW_SERVER_TRADES}", String.format("%,d", newServerTradesWeek));
		replace.put("{PERSONAL_OLD_TRADES}", String.format("%,d", personalOldTradesWeek));
		WebServer.returnTextFile(ex, "http/index.html", replace);
	}

}
