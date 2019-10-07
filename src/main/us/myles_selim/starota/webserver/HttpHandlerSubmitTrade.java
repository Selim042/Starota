package us.myles_selim.starota.webserver;

import java.io.IOException;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import discord4j.core.object.entity.Guild;
import discord4j.core.object.util.Snowflake;
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.enums.EnumGender;
import us.myles_selim.starota.enums.EnumPokemon;
import us.myles_selim.starota.trading.TradeboardPost;
import us.myles_selim.starota.trading.forms.FormSet;
import us.myles_selim.starota.webserver.WebServer.Cookie;
import us.myles_selim.starota.wrappers.StarotaServer;

@SuppressWarnings("restriction")
public class HttpHandlerSubmitTrade implements HttpHandler {

	@Override
	public void handle(HttpExchange ex) throws IOException {
		try {
			if (!WebServer.isLoggedIn(ex)) {
				WebServer.return404(ex, "Please " + WebServer.getLoginHTML(ex, "login"));
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

			EnumPokemon pokemon = EnumPokemon.getPokemon(post.get("pokemon"));
			if (pokemon == null) {
				WebServer.returnError(ex, 400);
				return;
			}
			FormSet.Form form = null;
			if (!post.get("pokemon_form").equals("-1")) {
				FormSet formSet = pokemon.getFormSet();
				if (formSet != null) {
					String formName = post.get("pokemon_form");
					for (FormSet.Form f : formSet.getForms()) {
						if (f.toString().equals(formName)) {
							form = f;
							continue;
						}
					}
				}
			}
			boolean shiny = post.containsKey("shiny");
			int genderInt;
			try {
				genderInt = Integer.parseInt(post.get("gender"));
			} catch (NumberFormatException e) {
				WebServer.returnError(ex, 400);
				return;
			}
			EnumGender gender = EnumGender.values()[genderInt];
			boolean legacy = post.containsKey("legacy");
			TradeboardPost newPost = server.newPost(post.get("trade_type").equals("1"),
					Snowflake.of(OAuthUtils.getUser(tokenCookie.value).id).asLong(), pokemon, form,
					shiny, gender, legacy);
			if (newPost != null) {
				WebServer.redirect(ex, "/tradeboard");
				return;
			} else {
				WebServer.redirect(ex, "/new_trade", 400);
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
			WebServer.returnError(ex, e);
		}
	}

}
