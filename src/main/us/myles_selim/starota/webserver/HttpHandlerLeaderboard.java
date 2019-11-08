package us.myles_selim.starota.webserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Member;
import discord4j.core.object.util.Snowflake;
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.leaderboards.Leaderboard;
import us.myles_selim.starota.leaderboards.LeaderboardEntry;
import us.myles_selim.starota.webserver.OAuthUtils.OAuthUser;
import us.myles_selim.starota.webserver.WebServer.Cookie;
import us.myles_selim.starota.wrappers.StarotaServer;

public class HttpHandlerLeaderboard implements HttpHandler {

	private static final String BOARD_CARD;
	private static final String BOARD_CARD_ENTRY;

	static {
		BOARD_CARD = WebServer.getTemplate("leaderboard_card.html");
		BOARD_CARD_ENTRY = WebServer.getTemplate("leaderboard_card_entry.html");
	}

	@Override
	public void handle(HttpExchange ex) throws IOException {
		try {
			if (!WebServer.isLoggedIn(ex)) {
				// WebServer.return404(ex, "Please " +
				// WebServer.getLoginHTML(ex, "login"));
				WebServer.redirect(ex, "/login");
				return;
			}
			Cookie tokenCookie = WebServer.getCookies(ex).get("token");
			Snowflake serverId = WebServer.getGuild(ex);
			boolean inGuild = Starota.getClient().getGuilds().any((g) -> g.getId().equals(serverId))
					.block();
			if (!inGuild) {
				WebServer.returnServer404(ex, "Please select a server");
				return;
			}
			Guild guild = inGuild ? Starota.getGuild(serverId) : null;
			StarotaServer server = inGuild ? StarotaServer.getServer(guild) : null;
			OAuthUser loggedIn = OAuthUtils.getUser(tokenCookie.value);
			Snowflake userId = Snowflake.of(loggedIn.id);
			Member member = guild.getMemberById(userId).block();

			InputStream file = WebServer.getResourceFile("http/leaderboards.html");
			BufferedReader profTempFile = new BufferedReader(new InputStreamReader(file));
			StringBuilder tempBuilder = new StringBuilder();
			profTempFile.lines().forEach(tempBuilder::append);
			profTempFile.close();
			String temp = tempBuilder.toString();

			// fill stuff
			temp = WebServer.fillBaseStuff(ex, tokenCookie.value, temp);
			temp = fillCardsInfo(temp, server, member);

			WebServer.setContentType(ex, "http/tradeboards.html");
			OutputStream response = ex.getResponseBody();
			byte[] out = temp.getBytes("UTF-8");
			ex.sendResponseHeaders(200, out.length);
			response.write(out);
			response.close();
		} catch (Exception e) {
			e.printStackTrace();
			WebServer.returnError(ex, e);
		}
	}

	protected static String getCard(StarotaServer server, Leaderboard board) {
		String card = BOARD_CARD;
		card = card.replace("{BOARD_NAME}", board.getDisplayName());

		long maxEntry = 0;
		StringBuilder entries = new StringBuilder();
		for (LeaderboardEntry entry : board.getEntries()) {
			if (entry.getValue() > maxEntry)
				maxEntry = entry.getValue();
			String entryS = BOARD_CARD_ENTRY;
			Member member = entry.getDiscordMember(server.getDiscordGuild());
			entryS = entryS.replace("{PLAYER_NAME}",
					member.getUsername() + "#" + member.getDiscriminator());
			entryS = entryS.replace("{BOARD_ENTRY_VALUE}", Long.toString(entry.getValue()));
			entryS = entryS.replace("{BOARD_ENTRY_VALUE_DISP}", String.format("%,d", entry.getValue()));
			entries.append(entryS);
		}
		card = card.replace("{BOARD_ENTRIES}", entries.toString());
		card = card.replace("{BOARD_MAX_VALUE}", Long.toString(maxEntry));
		card = card.replace("{BOARD_COLOR}", Integer.toHexString(board.getColor()).toUpperCase());
		return card;
	}

	private String fillCardsInfo(String template, StarotaServer server, Member member) {
		StringBuilder cards = new StringBuilder();

		if (server != null)
			for (Leaderboard board : server.getLeaderboards())
				if (board.isEnabled())
					cards.append(getCard(server, board));

		if (cards.length() > 0)
			template = template.replace("{BOARD_CARDS}", cards.toString());
		else
			template = template.replace("{BOARD_CARDS}", "<div class=\"col-md-6 col-xl-3 mb-4\">\r\n"
					+ "    <p class=\"text-center\">No leaderboards found</p>\r\n" + "</div>");
		return template;
	}

}
