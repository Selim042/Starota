package us.myles_selim.starota.trading;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import us.myles_selim.starota.CachedData;
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.enums.EnumPokemon;
import us.myles_selim.starota.trading.forms.FormSet;

public class FormManager {

	private static CachedData<List<EnumPokemon>> AVAILABLE;
	private static CachedData<List<EnumPokemon>> SHINYABLE;

	private static final String silphDex = "https://thesilphroad.com/catalog";
	private static final Pattern pokemonGeneralPattern = Pattern
			.compile("<div class=\"pokemonOption (sighted|notSighted).*?</div>");
	private static final Pattern dexNumPattern = Pattern.compile("<span>#[0-9]{0,3}</span>");

	private static void checkCaches() {
		if (AVAILABLE == null || AVAILABLE.hasPassed(86400000L) || SHINYABLE == null
				|| SHINYABLE.hasPassed(86400000L)) { // 1 day
			AVAILABLE = new CachedData<>(new LinkedList<>());
			SHINYABLE = new CachedData<>(new LinkedList<>());

			try {
				URL url = new URL(silphDex);
				URLConnection conn = url.openConnection();
				conn.setRequestProperty("User-Agent", Starota.HTTP_USER_AGENT);
				BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				String html = "";
				String line = null;
				while ((line = in.readLine()) != null)
					html += line;
				Matcher generalMatcher = pokemonGeneralPattern.matcher(html);
				while (generalMatcher.find()) {
					String match = generalMatcher.group();
					if (match.contains("data-released=\"1\"")) {
						Matcher dexNumMatcher = dexNumPattern.matcher(match);
						if (dexNumMatcher.find()) {
							String dexMatch = dexNumMatcher.group();
							EnumPokemon pokemon = EnumPokemon.getPokemon(
									Integer.parseInt(dexMatch.substring(7, dexMatch.length() - 7)));
							if (pokemon == null)
								continue;
							AVAILABLE.getValue().add(pokemon);
							if (match.contains("data-shiny-released=\"1\""))
								SHINYABLE.getValue().add(pokemon);
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static boolean isAvailable(EnumPokemon pokemon) {
		checkCaches();
		return pokemon != null && AVAILABLE.getValue().contains(pokemon);
	}

	public static boolean isAvailable(int id) {
		return isAvailable(EnumPokemon.getPokemon(id));
	}

	public static boolean isShinyable(EnumPokemon pokemon) {
		checkCaches();
		if (pokemon == null || !SHINYABLE.getValue().contains(pokemon))
			return false;
		FormSet set = pokemon.getFormSet();
		if (set == null)
			return true;
		return set.getDefaultForm().canBeShiny(pokemon);
	}

	public static boolean isShinyable(int id) {
		return isShinyable(EnumPokemon.getPokemon(id));
	}

}
