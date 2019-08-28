package us.myles_selim.starota.webserver;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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
import us.myles_selim.starota.misc.utils.ImageHelper;
import us.myles_selim.starota.trading.TradeboardPost;
import us.myles_selim.starota.trading.forms.FormSet.Form;
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
			+ "<input type=\"hidden\" class=\"form-control\" name=\"id\" value=\"{POST_NUMBER}\" /></form>"
			+ "</div></div></div></div></div>";

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

			BufferedReader profTempFile = new BufferedReader(new FileReader("http/tradeboard.html"));
			StringBuilder tempBuilder = new StringBuilder();
			profTempFile.lines().forEach(tempBuilder::append);
			profTempFile.close();
			String temp = tempBuilder.toString();

			Map<String, String> get = WebServer.getGET(ex);
			TradeboardSearch search = new TradeboardSearch();
			if (get.containsKey("q")) {
				String query = get.get("q");
				temp = temp.replaceAll("\\{SEARCH_PLACEHOLDER\\}", query);
				search.fillValues(query);
			} else
				temp = temp.replaceAll("\\{SEARCH_PLACEHOLDER\\}", "");
			if (get.containsKey("t") && get.get("t").matches("(01)"))
				search.lookingFor = get.get("t").equals("0");

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
			TradeboardSearch search) {
		StringBuilder cards = new StringBuilder();

		if (server != null) {
			for (TradeboardPost post : server.getPosts()) {
				if (search != null && !search.matches(post))
					continue;
				Member owner = server.getDiscordGuild().getMemberById(Snowflake.of(post.getOwner()))
						.block();
				EnumPokemon pokemon = post.getPokemon();
				String card;
				if (owner.equals(member))
					card = TRADE_CARD_DELETE.replaceAll("\\{TYPE\\}",
							pokemon.getType1().name().toLowerCase());
				else
					card = TRADE_CARD.replaceAll("\\{TYPE\\}", pokemon.getType1().name().toLowerCase());
				Form form = post.getForm();

				card = card.replaceAll("\\{POSTER_ID\\}", owner.getId().asString());
				card = card.replaceAll("\\{POSTER_AVATAR\\}", owner.getAvatarUrl());
				card = card.replaceAll("\\{POSTER_DISPLAY_NAME\\}", owner.getDisplayName());
				card = card.replaceAll("\\{POSTER_USERNAME\\}", owner.getUsername());
				card = card.replaceAll("\\{POSTER_DISCRIM\\}", owner.getDiscriminator());

				card = card.replaceAll("\\{TRADE_TYPE\\}",
						post.isLookingFor() ? "Looking for" : "For trade");

				card = card.replaceAll("\\{POST_NUMBER\\}", post.getIdString());
				card = card.replaceAll("\\{POKEMON_IMAGE\\}",
						ImageHelper.getOfficalArtwork(pokemon, form));
				card = card.replaceAll("\\{POKEMON_NAME\\}", pokemon.getName());
				card = card.replaceAll("\\{POKEMON_FORM\\}", form == null ? "-" : form.toString());
				EnumGender gender = post.getGender();
				card = card.replaceAll("\\{POKEMON_GENDER\\}",
						gender == null ? pokemon.getGenderPossible().toString() : gender.toString());

				String isLegacyS = Boolean.toString(post.isLegacy());
				card = card.replaceAll("\\{POKEMON_LEGACY\\}",
						Character.toUpperCase(isLegacyS.charAt(0)) + isLegacyS.substring(1));
				String isShinyS = Boolean.toString(post.isShiny());
				card = card.replaceAll("\\{POKEMON_SHINY\\}",
						Character.toUpperCase(isShinyS.charAt(0)) + isShinyS.substring(1));

				cards.append(card);
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
		public final List<EnumPokemon> pokemon = new LinkedList<>();
		public final List<String> forms = new LinkedList<>();
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
			if (pokemon.size() != 0 && !pokemon.contains(post.getPokemon()))
				return false;
			if (post.getForm() != null) {
				boolean foundForm = forms.size() != 1;
				for (String f : forms)
					if (post.getForm().toString().toLowerCase().contains(f.toLowerCase())) {
						foundForm = true;
						break;
					}
				if (!foundForm)
					return false;
			}
			if (gender != null) {
				EnumGender postGender = post.getGender();
				if (postGender == null)
					postGender = post.getPokemon().getGenderPossible();
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
			if (regional != null && regional != post.getPokemon().isRegional())
				return false;
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
				EnumPokemon pokemonS = EnumPokemon.getPokemon(t);
				if (pokemonS != null) {
					pokemon.add(pokemonS);
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
				forms.add(t);
			}

		}

	}

}
