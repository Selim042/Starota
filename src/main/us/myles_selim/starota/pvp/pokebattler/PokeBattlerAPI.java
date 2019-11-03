package us.myles_selim.starota.pvp.pokebattler;

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URL;
import java.net.URLConnection;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import us.myles_selim.starota.misc.data_types.cache.CachedData;
import us.myles_selim.starota.misc.utils.StarotaConstants;

public class PokeBattlerAPI {

	private static final String LEAGUES_ENDPOINT = "https://fight.pokebattler.com/leagues";

	private static final JsonParser PARSER = new JsonParser();
	private static Gson GSON;

	private static CachedData<PokeBattlerLeague[]> LEAGUE_CACHE;

	private static void setupGson() {
		if (GSON != null)
			return;
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(PokemonCondition.class, new JsonDeserializer<PokemonCondition>() {

			@Override
			public PokemonCondition deserialize(JsonElement json, Type typeOfT,
					JsonDeserializationContext ctx) throws JsonParseException {
				EnumPokemonConditionType type = ctx.deserialize(json.getAsJsonObject().get("type"),
						EnumPokemonConditionType.class);
				return ctx.deserialize(json, type.getTypeClass());
			}
		});
		GSON = builder.create();
	}

	public static PokeBattlerLeague[] getLeagues() {
		if (LEAGUE_CACHE != null && !LEAGUE_CACHE.hasPassed(360000)) // 1 hr
			return LEAGUE_CACHE.getValue();
		setupGson();
		try {
			URL url = new URL(LEAGUES_ENDPOINT);
			URLConnection conn = url.openConnection();
			conn.setRequestProperty("User-Agent", StarotaConstants.HTTP_USER_AGENT);
			PokeBattlerLeague[] leagues = GSON
					.fromJson(PARSER.parse(new InputStreamReader(conn.getInputStream()))
							.getAsJsonObject().get("combatLeagues"), PokeBattlerLeague[].class);
			LEAGUE_CACHE = new CachedData<>(leagues);
			return leagues;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

}
