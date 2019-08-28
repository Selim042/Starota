package us.myles_selim.starota.webserver;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.Role;
import discord4j.core.object.util.Snowflake;
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.misc.utils.MiscUtils;
import us.myles_selim.starota.profiles.PlayerProfile;
import us.myles_selim.starota.trading.TradeboardPost;
import us.myles_selim.starota.webserver.OAuthUtils.OAuthUser;
import us.myles_selim.starota.webserver.WebServer.Cookie;
import us.myles_selim.starota.wrappers.StarotaServer;

@SuppressWarnings("restriction")
public class HttpHandlerProfiles implements HttpHandler {

	private static final String PROFILE_CARD = "<div class=\"col-md-6 col-xl-3 mb-4\">"
			+ "<div class=\"card shadow border-left-{POGO_TEAM} py-2\">"
			+ "<a href=\"/profile/{DISCORD_ID}\" class=\"text-secondary\" style=\"text-decoration:none\">"
			+ "<div class=\"card-body\"><div class=\"row align-items-center no-gutters\">"
			+ "<div class=\"col\"><img class=\"rounded-circle\" src=\"{DISCORD_AVATAR}\" "
			+ "width=\"32px\" height=\"32px\" style=\"margin-bottom: 4px;margin-right: 4px;\" />"
			+ "<span style=\"padding: 2px;\">{DISCORD_USERNAME}</span></div></div>"
			+ "<div class=\"row\"><div class=\"col\" style=\"margin: 0px;margin-top: 4px;margin-bottom: 4px;\">"
			+ "<h1 style=\"font-size: 16px;\"><strong>Trainer Username:</strong></h1><span>{POGO_NAME}</span></div>"
			+ "</div><div class=\"row\"><div class=\"col\" "
			+ "style=\"margin: 0px;margin-top: 4px;margin-bottom: 4px;\"><h1 style=\"font-size: 16px;\">"
			+ "<strong>Trainer Level:</strong></h1><span>{POGO_LEVEL}</span></div>"
			+ "<div class=\"col\" style=\"margin: 4px;margin-right: 0px;margin-left: 0px;\">"
			+ "<h1 style=\"font-size: 16px;\"><strong>Real Name:</strong></h1><span>{REAL_NAME}</span></div>"
			+ "</div><div class=\"row\">"
			+ "<div class=\"col\" style=\"margin: 4px;margin-right: 0px;margin-left: 0px;\">"
			+ "<h1 style=\"font-size: 16px;\"><strong>Trainer Code:</strong></h1>"
			+ "<span>{POGO_TRAINER_CODE}</span></div></div></div></a></div></div>";

	private static final String ROLE_HEADER = "<div class=\"row\" style=\"width: 100%;\">"
			+ "<div class=\"col\" style=\"margin-left: 10px;\"><h4 class=\"text-secondary\">"
			+ "{HEADER}</h4></div></div>";

	@Override
	public void handle(HttpExchange ex) throws IOException {
		try {
			if (!WebServer.isLoggedIn(ex)) {
				WebServer.return404(ex, "Please login");
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
				WebServer.return404(ex, "Tradeboard not found");
				return;
			}
			Guild guild = inGuild ? Starota.getGuild(serverId) : null;
			StarotaServer server = inGuild ? StarotaServer.getServer(guild) : null;
			OAuthUser loggedIn = OAuthUtils.getUser(tokenCookie.value);
			Snowflake userId = Snowflake.of(loggedIn.id);
			Member member = guild.getMemberById(userId).block();

			Map<String, String> post = WebServer.getPOST(ex);
			if (post.containsKey("id")) {
				String idS = post.get("id");
				if (idS.matches("\\d{4}")) {
					int id = Integer.parseInt(idS);
					TradeboardPost tPost = server.getPost(id);
					if (tPost != null && userId.asLong() == tPost.getOwner())
						server.removePost(id);
				}
			}

			BufferedReader profTempFile = new BufferedReader(new FileReader("http/profiles.html"));
			StringBuilder tempBuilder = new StringBuilder();
			profTempFile.lines().forEach(tempBuilder::append);
			profTempFile.close();
			String temp = tempBuilder.toString();

			Map<String, String> get = WebServer.getGET(ex);
			ProfilesSearch search = new ProfilesSearch();
			// if (get.containsKey("q")) {
			// String query = get.get("q");
			// temp = temp.replaceAll("\\{SEARCH_PLACEHOLDER\\}", query);
			// search.fillValues(query);
			// } else
			// temp = temp.replaceAll("\\{SEARCH_PLACEHOLDER\\}", "");
			// if (get.containsKey("t") && get.get("t").matches("(01)"))
			// search.lookingFor = get.get("t").equals("0");

			// fill stuff
			temp = WebServer.fillBaseStuff(ex, tokenCookie.value, temp);
			temp = fillCardsInfo(temp, server, member, search);

			WebServer.setContentType(ex, "http/tradeboard.html");
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

	private String fillCardsInfo(String temp, StarotaServer server, Member member,
			ProfilesSearch search) {
		StringBuilder cards = new StringBuilder();

		if (server != null) {
			Map<Member, Role> highestRoles = server.getDiscordGuild().getMembers()
					.collectMap((m) -> m, MiscUtils::getSidebarRole).block();
			List<PlayerProfile> allProfiles = server.getProfiles();
			List<PlayerProfile> profiles = server.getDiscordGuild().getMembers()
					.sort((Member o1, Member o2) -> {
						Role r1 = highestRoles.get(o1);
						Role r2 = highestRoles.get(o2);
						if (r1.equals(r2)) {
							String n1 = o1.getDisplayName();
							String n2 = o2.getDisplayName();
							if (n1.equals(n2))
								return o1.getDiscriminator().compareTo(o2.getDiscriminator());
							return n1.compareTo(n2);
						}
						return Integer.compare(r2.getRawPosition(), r1.getRawPosition());
					}).filter((m) -> hasProfile(allProfiles, m)).map((m) -> getProfile(allProfiles, m))
					.collectList().block();
			Role lastHighest = null;
			for (PlayerProfile post : profiles) {
				if (search != null && !search.matches(post))
					continue;
				Member owner = server.getDiscordGuild().getMemberById(Snowflake.of(post.getDiscordId()))
						.block();
				Role ownerRole = highestRoles.get(owner);
				if (lastHighest == null || !lastHighest.equals(ownerRole)) {
					if (ownerRole.isEveryone())
						cards.append(ROLE_HEADER.replaceAll("\\{HEADER\\}", "@EVERYONE ELSE"));
					else
						cards.append(ROLE_HEADER.replaceAll("\\{HEADER\\}",
								ownerRole.getName().toUpperCase()));
					lastHighest = ownerRole;
				}
				String card = PROFILE_CARD.replaceAll("\\{DISCORD_AVATAR\\}", owner.getAvatarUrl());
				card = card.replaceAll("\\{DISCORD_ID\\}", Long.toString(post.getDiscordId()));

				card = card.replaceAll("\\{POGO_TEAM\\}", post.getTeam().toString().toLowerCase());
				card = card.replaceAll("\\{DISCORD_USERNAME\\}", owner.getUsername());
				card = card.replaceAll("\\{POGO_NAME\\}", post.getPoGoName());
				card = card.replaceAll("\\{POGO_LEVEL\\}", Integer.toString(post.getLevel()));
				if (post.getRealName() != null)
					card = card.replaceAll("\\{REAL_NAME\\}", post.getRealName());
				else
					card = card.replaceAll("\\{REAL_NAME\\}", "-");
				if (post.getTrainerCode() > 0)
					card = card.replaceAll("\\{POGO_TRAINER_CODE\\}", post.getTrainerCodeString());
				else
					card = card.replaceAll("\\{POGO_TRAINER_CODE\\}", "-");

				cards.append(card);
			}
		}

		if (cards.length() > 0)
			temp = temp.replaceAll("\\{CARDS\\}", cards.toString());
		else
			temp = temp.replaceAll("\\{CARDS\\}", "<div class=\"col-md-6 col-xl-3 mb-4\">\r\n"
					+ "    <p class=\"text-center\">No profiles found</p>\r\n" + "</div>");
		return temp;
	}

	private boolean hasProfile(List<PlayerProfile> profiles, Member member) {
		long id = member.getId().asLong();
		for (PlayerProfile p : profiles)
			if (p.getDiscordId() == id)
				return true;
		return false;
	}

	private PlayerProfile getProfile(List<PlayerProfile> profiles, Member member) {
		long id = member.getId().asLong();
		for (PlayerProfile p : profiles)
			if (p.getDiscordId() == id)
				return p;
		return null;
	}

	private class ProfilesSearch {

		public boolean matches(PlayerProfile post) {
			return true;
		}

		public void fillValues(String search) {}

	}

}
