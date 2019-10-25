package us.myles_selim.starota.webserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Member;
import discord4j.core.object.util.Snowflake;
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.enums.EnumGender;
import us.myles_selim.starota.enums.EnumPokemon;
import us.myles_selim.starota.forms.Form;
import us.myles_selim.starota.misc.utils.ImageHelper;
import us.myles_selim.starota.trading.TradeboardPost;
import us.myles_selim.starota.webserver.OAuthUtils.OAuthUser;
import us.myles_selim.starota.webserver.WebServer.Cookie;
import us.myles_selim.starota.wrappers.StarotaServer;

@SuppressWarnings("restriction")
public class HttpHandlerTradeboard implements HttpHandler {

	private static final String TRADE_CARD = "<div class=\"col-md-6 col-xl-3 mb-4\">"
			+ "<div class=\"card shadow border-left-{TYPE} py-2\"><div class=\"card-body\">"
			+ "<a href=\"/profile/{POSTER_ID}\"><div class=\"row\"><div class=\"col-auto\" style=\"width: 40px;\">"
			+ "<img class=\"rounded-circle\" src=\"{POSTER_AVATAR}\" style=\"width: 32px;\" /></div>"
			+ "<div class=\"col\"><p class=\"text-secondary\" style=\"margin-top: 6px;margin-bottom: 6px;\">"
			+ "{POSTER_DISPLAY_NAME}</p></div></div></a><div class=\"row\"><div class=\"col\">"
			+ "<h1 style=\"font-size: 26px;\"><strong>Post #{POST_NUMBER}</strong></h1></div><div class=\"col\">"
			+ "<img class=\"float-right\" src=\"{POKEMON_IMAGE}\" style=\"width: 64px;\" /></div>"
			+ "</div><div class=\"row\"><div class=\"col\">"
			+ "<h1 style=\"font-size: 16px;\"><strong>Trade Type:</strong></h1>"
			+ "<p>{TRADE_TYPE}</p></div><div class=\"col\">"
			+ "<h1 style=\"font-size: 16px;\"><strong>Discord User:</strong></h1>"
			+ "<p>{POSTER_USERNAME}#{POSTER_DISCRIM}</p></div></div><div class=\"row\">"
			+ "<div class=\"col\"><h1 style=\"font-size: 16px;\"><strong>Pokemon:</strong></h1>"
			+ "<p>{POKEMON_NAME}</p></div><div class=\"col\">"
			+ "<h1 style=\"font-size: 16px;\"><strong>Form:</strong></h1>"
			+ "<p>{POKEMON_FORM}</p></div></div><div class=\"row\"><div class=\"col\">"
			+ "<h1 style=\"font-size: 16px;\"><strong>Gender:</strong></h1>"
			+ "<p>{POKEMON_GENDER}</p></div><div class=\"col\">"
			+ "<h1 style=\"font-size: 16px;\"><strong>Legacy:</strong></h1>"
			+ "<p>{POKEMON_LEGACY}</p></div></div><div class=\"row\"><div class=\"col\">"
			+ "<h1 style=\"font-size: 16px;\"><strong>Shiny:</strong></h1>" + "<p>{POKEMON_SHINY}</p>"
			+ "</div>" + "<div class=\"col\">" + "<form action=\"/tradeboard\" method=\"post\">"
			+ "<button class=\"btn btn-primary\" type=\"submit\">I'm Interested!</button>"
			+ "<input type=\"hidden\" class=\"form-control\" name=\"id-int\" value=\"{POST_NUMBER}\" /></form>"
			+ "</div></div></div></div></div>";

	private static final String TRADE_CARD_DELETE = "<div class=\"col-md-6 col-xl-3 mb-4\">"
			+ "<div class=\"card shadow border-left-{TYPE} py-2\"><div class=\"card-body\">"
			+ "<a href=\\\"/profile/{POSTER_ID}\\\"><div class=\"row\"><div class=\"col-auto\" style=\"width: 40px;\">"
			+ "<img class=\"rounded-circle\" src=\"{POSTER_AVATAR}\" style=\"width: 32px;\" /></div>"
			+ "<div class=\"col\"><p class=\\\"text-secondary\\\" style=\"margin-top: 6px;margin-bottom: 6px;\">{POSTER_DISPLAY_NAME}</p>"
			+ "</div></div></a><div class=\"row\"><div class=\"col\">"
			+ "<h1 style=\"font-size: 26px;\"><strong>Post #{POST_NUMBER}</strong></h1></div><div class=\"col\">"
			+ "<img class=\"float-right\" src=\"{POKEMON_IMAGE}\" style=\"width: 64px;\" /></div>"
			+ "</div><div class=\"row\"><div class=\"col\">"
			+ "<h1 style=\"font-size: 16px;\"><strong>Trade Type:</strong></h1>"
			+ "<p>{TRADE_TYPE}</p></div><div class=\"col\">"
			+ "<h1 style=\"font-size: 16px;\"><strong>Discord User:</strong></h1>"
			+ "<p>{POSTER_USERNAME}#{POSTER_DISCRIM}</p></div></div><div class=\"row\">"
			+ "<div class=\"col\"><h1 style=\"font-size: 16px;\"><strong>Pokemon:</strong></h1>"
			+ "<p>{POKEMON_NAME}</p></div><div class=\"col\">"
			+ "<h1 style=\"font-size: 16px;\"><strong>Form:</strong></h1>"
			+ "<p>{POKEMON_FORM}</p></div></div><div class=\"row\"><div class=\"col\">"
			+ "<h1 style=\"font-size: 16px;\"><strong>Gender:</strong></h1>"
			+ "<p>{POKEMON_GENDER}</p></div><div class=\"col\">"
			+ "<h1 style=\"font-size: 16px;\"><strong>Legacy:</strong></h1>"
			+ "<p>{POKEMON_LEGACY}</p></div></div><div class=\"row\"><div class=\"col\">"
			+ "<h1 style=\"font-size: 16px;\"><strong>Shiny:</strong></h1>"
			+ "<p>{POKEMON_SHINY}</p></div><div class=\"col\">"
			+ "<form action=\"/tradeboard\" method=\"post\">"
			+ "<button class=\"btn btn-danger\" type=\"submit\">Delete Trade</button>"
			+ "<input type=\"hidden\" class=\"form-control\" name=\"id-del\" value=\"{POST_NUMBER}\" /></form>"
			+ "</div></div></div></div></div>";

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

			InputStream file = WebServer.getResourceFile("http/tradeboard.html");
			BufferedReader profTempFile = new BufferedReader(new InputStreamReader(file));
			StringBuilder tempBuilder = new StringBuilder();
			profTempFile.lines().forEach(tempBuilder::append);
			profTempFile.close();
			String temp = tempBuilder.toString();

			Map<String, String> post = WebServer.getPOST(ex);
			if (post.containsKey("id-del")) {
				String idS = post.get("id-del");
				if (idS.matches("\\d{4}")) {
					int id = Integer.parseInt(idS);
					TradeboardPost tPost = server.getPost(id);
					if (tPost != null && userId.asLong() == tPost.getOwner()) {
						server.removePost(id);
						temp = temp.replaceAll("\\{ALERT_COLOR\\}", "danger");
						temp = temp.replaceAll("\\{ALERT_TEXT\\}",
								String.format("Tradeboard post #%s was deleted", id));
						temp = temp.replaceAll("\\{ALERT_HIDDEN\\}", "");
					}
				}
			} else if (post.containsKey("id-int")) {
				String idS = post.get("id-int");
				if (idS.matches("\\d{4}")) {
					int id = Integer.parseInt(idS);
					TradeboardPost tPost = server.getPost(id);
					if (tPost != null && userId.asLong() != tPost.getOwner()) {
						Member poster = server.getDiscordGuild()
								.getMemberById(Snowflake.of(tPost.getOwner())).block();
						Member interested = server.getDiscordGuild().getMemberById(userId).block();
						String nickname = interested.asMember(server.getDiscordGuild().getId()).block()
								.getDisplayName();
						if (nickname != null)
							nickname += " (_" + interested.getUsername() + "#"
									+ interested.getDiscriminator() + "_)";
						else
							nickname = interested.getUsername() + "#" + interested.getDiscriminator();
						final String finalNick = nickname;
						poster.getPrivateChannel().block().createMessage((e) -> e.setContent(finalNick
								+ " from " + server.getDiscordGuild().getName()
								+ " is interested in your trade. Please contact them for more information.")
								.setEmbed(tPost.getPostEmbed(server, false))).block();
						temp = temp.replaceAll("\\{ALERT_COLOR\\}", "success");
						temp = temp.replaceAll("\\{ALERT_TEXT\\}",
								String.format("Contacted %s", poster.getDisplayName()));
						temp = temp.replaceAll("\\{ALERT_HIDDEN\\}", "");
					}
				}
			}

			Map<String, String> get = WebServer.getGET(ex);
			TradeboardSearch search = new TradeboardSearch();
			if (get.containsKey("q")) {
				String query = get.get("q");
				temp = temp.replaceAll("\\{SEARCH_PLACEHOLDER\\}", query);
				search.fillValues(query);
			} else
				temp = temp.replaceAll("\\{SEARCH_PLACEHOLDER\\}", "");
			if (get.containsKey("t"))
				search.lookingFor = get.get("t").equals("0");

			if (search.lookingFor != null) {
				if (search.lookingFor)
					temp = temp.replaceAll("\\{LOOKING_FOR_SELECTED\\}", "selected")
							.replaceAll("\\{FOR_TRADE_SELECTED\\}", "");
				else
					temp = temp.replaceAll("\\{LOOKING_FOR_SELECTED\\}", "")
							.replaceAll("\\{FOR_TRADE_SELECTED\\}", "selected");
			}
			temp = temp.replaceAll("\\{ALERT_HIDDEN\\}", "d-none");

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

	protected static String getCard(Member browseMember, Member postMember, TradeboardPost post) {
		String card;
		EnumPokemon pokemon = post.getPokemon();
		if (postMember.equals(browseMember))
			card = TRADE_CARD_DELETE.replaceAll("\\{TYPE\\}",
					pokemon.getData().getType1().name().toLowerCase());
		else
			card = TRADE_CARD.replaceAll("\\{TYPE\\}",
					pokemon.getData().getType1().name().toLowerCase());
		Form form = post.getForm();

		card = card.replaceAll("\\{POSTER_ID\\}", postMember.getId().asString());
		card = card.replaceAll("\\{POSTER_AVATAR\\}", postMember.getAvatarUrl());
		card = card.replaceAll("\\{POSTER_DISPLAY_NAME\\}", postMember.getDisplayName());
		card = card.replaceAll("\\{POSTER_USERNAME\\}", postMember.getUsername());
		card = card.replaceAll("\\{POSTER_DISCRIM\\}", postMember.getDiscriminator());

		card = card.replaceAll("\\{TRADE_TYPE\\}", post.isLookingFor() ? "Looking for" : "For trade");

		card = card.replaceAll("\\{POST_NUMBER\\}", post.getIdString());
		card = card.replaceAll("\\{POKEMON_IMAGE\\}", ImageHelper.getOfficalArtwork(pokemon, form));
		card = card.replaceAll("\\{POKEMON_NAME\\}", pokemon.getData().getName());
		card = card.replaceAll("\\{POKEMON_FORM\\}", form == null ? "-" : form.getName());
		EnumGender gender = post.getGender();
		card = card.replaceAll("\\{POKEMON_GENDER\\}",
				gender == null ? pokemon.getData().getGenderPossible().toString() : gender.toString());

		String isLegacyS = Boolean.toString(post.isLegacy());
		card = card.replaceAll("\\{POKEMON_LEGACY\\}",
				Character.toUpperCase(isLegacyS.charAt(0)) + isLegacyS.substring(1));
		String isShinyS = Boolean.toString(post.isShiny());
		card = card.replaceAll("\\{POKEMON_SHINY\\}",
				Character.toUpperCase(isShinyS.charAt(0)) + isShinyS.substring(1));

		return card;
	}

	private String fillCardsInfo(String temp, StarotaServer server, Member member,
			TradeboardSearch search) {
		StringBuilder cards = new StringBuilder();

		if (server != null) {
			for (TradeboardPost post : server.getPosts()) {
				if (search != null && !search.matches(post))
					continue;
				if (server.getDiscordGuild().getMembers()
						.any((m) -> m.getId().equals(Snowflake.of(post.getOwner()))).block()) {
					Member owner = server.getDiscordGuild().getMemberById(Snowflake.of(post.getOwner()))
							.block();
					cards.append(getCard(member, owner, post));
				}
			}
		}

		if (cards.length() > 0)
			temp = temp.replaceAll("\\{CARDS\\}", cards.toString());
		else
			temp = temp.replaceAll("\\{CARDS\\}", "<div class=\"col-md-6 col-xl-3 mb-4\">\r\n"
					+ "    <p class=\"text-center\">No trades found</p>\r\n" + "</div>");
		return temp;
	}

	private class TradeboardSearch {

		public final List<Integer> ids = new LinkedList<>();
		public final List<String> otherTerms = new LinkedList<>();
		public EnumGender gender;
		public Boolean legacy;
		public Boolean shiny;
		public Boolean regional;
		public Boolean lookingFor;

		public boolean matches(TradeboardPost post) {
			if (post == null || post.getPokemon() == null)
				return false;
			if (lookingFor != null && lookingFor != post.isLookingFor())
				return false;
			if (ids.size() != 0 && !ids.contains(post.getId()))
				return false;
			if (gender != null) {
				EnumGender postGender = post.getGender();
				if (postGender == null)
					postGender = post.getPokemon().getData().getGenderPossible();
				switch (postGender) {
				case EITHER:
					if (gender == EnumGender.UNKNOWN)
						return false;
				case MALE:
				case FEMALE:
				case UNKNOWN:
					if (gender != postGender)
						return false;
				}
			}
			if (legacy != null && legacy != post.isLegacy())
				return false;
			if (shiny != null && shiny != post.isShiny())
				return false;
			if (regional != null && regional != post.getPokemon().getData().isRegional())
				return false;
			for (String t : otherTerms) {
				boolean matched = (post.getForm() != null
						&& post.getForm().toString().toLowerCase().startsWith(t.toLowerCase()));
				matched = matched || post.getPokemon().getData().getName().toLowerCase()
						.startsWith(t.toLowerCase());
				if (!matched)
					return false;
			}
			return true;
		}

		public void fillValues(String search) {
			for (String t : search.split(" ")) {
				if (t.isEmpty())
					continue;
				if (t.matches("\\d{4}")) {
					ids.add(Integer.parseInt(t));
					continue;
				}
				if (t.equalsIgnoreCase("legacy")) {
					legacy = true;
					continue;
				}
				if (t.equalsIgnoreCase("!legacy")) {
					legacy = false;
					continue;
				}
				if (t.equalsIgnoreCase("shiny")) {
					shiny = true;
					continue;
				}
				if (t.equalsIgnoreCase("!shiny")) {
					shiny = false;
					continue;
				}
				if (t.equalsIgnoreCase("regional")) {
					regional = true;
					continue;
				}
				if (t.equalsIgnoreCase("!regional")) {
					regional = false;
					continue;
				}
				EnumGender genderS;
				try {
					genderS = EnumGender.valueOf(t.toUpperCase());
				} catch (IllegalArgumentException e) {
					genderS = null;
				}
				if (genderS != null) {
					gender = genderS;
					continue;
				}
				otherTerms.add(t);
			}

		}

	}

}
