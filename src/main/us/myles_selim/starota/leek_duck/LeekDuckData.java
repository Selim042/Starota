package us.myles_selim.starota.leek_duck;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import sx.blah.discord.api.internal.json.objects.EmbedObject;
import sx.blah.discord.util.EmbedBuilder;
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.enums.EnumPokemon;
import us.myles_selim.starota.misc.data_types.cache.CachedData;
import us.myles_selim.starota.misc.data_types.cache.ClearCache;
import us.myles_selim.starota.misc.utils.EmojiServerHelper;

public class LeekDuckData {

	public static final EmbedObject LOADING_EMBED;

	static {
		EmbedBuilder builder = new EmbedBuilder();
		builder.appendDesc("Loading Leek Duck... " + EmojiServerHelper.getEmoji("loading"));
		LOADING_EMBED = builder.build();
	}

	private static final String LEEK_URL = "https://leekduck.com";
	private static final String DITTO_URL = "https://leekduck.com/FindDitto/";

	private static final Pattern DITTOABLE_PATTERN = Pattern.compile("listlabel\">.*?<");
	private static CachedData<List<EnumPokemon>> DITTOABLES;

	public static List<EnumPokemon> getDittoablePokemon() {
		if (DITTOABLES != null && !DITTOABLES.hasPassed(86400000)) // 1 day
			return DITTOABLES.getValue();
		List<EnumPokemon> newDittos = new ArrayList<>();
		try {
			URL url = new URL(DITTO_URL);
			URLConnection conn = url.openConnection();
			conn.setRequestProperty("User-Agent", Starota.HTTP_USER_AGENT);
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String html = "";
			String line = null;
			while ((line = in.readLine()) != null)
				html += line;
			Matcher generalMatcher = DITTOABLE_PATTERN.matcher(html);
			while (generalMatcher.find()) {
				String match = generalMatcher.group();
				String pokemonName = match.substring(11, match.length() - 1);
				EnumPokemon pokemon = EnumPokemon.getPokemon(pokemonName);
				if (pokemon == null) {
					Starota.submitError("Cannot find dittoable named " + pokemonName,
							new IllegalArgumentException("Cannot find dittoable named " + pokemonName));
					continue;
				}
				newDittos.add(pokemon);
			}
			DITTOABLES = new CachedData<>(newDittos);
			return newDittos;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static boolean areDittoablesLoaded() {
		if (DITTOABLES != null && !DITTOABLES.hasPassed(86400000)) // 1 day
			return true;
		return false;
	}

	public static long getDittoableCacheTime() {
		if (DITTOABLES == null)
			return -1;
		return DITTOABLES.getCreationTime();
	}

	@ClearCache("leekduck")
	public static void clearCache() {
		DITTOABLES = null;
	}

}
