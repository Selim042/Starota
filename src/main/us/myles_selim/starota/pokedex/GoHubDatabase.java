package us.myles_selim.starota.pokedex;

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import sx.blah.discord.api.internal.json.objects.EmbedObject;
import sx.blah.discord.util.EmbedBuilder;
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.cache.CachedData;
import us.myles_selim.starota.cache.ClearCache;
import us.myles_selim.starota.enums.EnumPokemon;
import us.myles_selim.starota.enums.EnumPokemonType;
import us.myles_selim.starota.enums.EnumWeather;
import us.myles_selim.starota.misc.utils.EmojiServerHelper;
import us.myles_selim.starota.pokedex.PokedexEntry.DexCounter;
import us.myles_selim.starota.pokedex.PokedexEntry.DexMove;
import us.myles_selim.starota.pokedex.PokedexEntry.DexMoveset;

public class GoHubDatabase {

	public static final EmbedObject LOADING_EMBED;

	static {
		EmbedBuilder builder = new EmbedBuilder();
		builder.appendDesc(
				"Loading Pokémon Go Hub Database... " + EmojiServerHelper.getEmoji("loading"));
		LOADING_EMBED = builder.build();
	}

	private static final String POKEMON_API = "https://db.pokemongohub.net/api/pokemon/";
	private static final String POKEMON_MOVES_API = "https://db.pokemongohub.net/api/moves/with-pokemon/";
	private static final String MOVES_API = "https://db.pokemongohub.net/api/moves/";
	// private static final String FAST_MOVES_LIST_API =
	// "https://db.pokemongohub.net/api/moves/with-filter/fast";
	// private static final String CHARGE_MOVES_LIST_API =
	// "https://db.pokemongohub.net/api/moves/with-filter/charge";
	private static final String MOVESETS_API = "https://db.pokemongohub.net/api/movesets/with-pokemon/";
	private static final String COUNTERS_API = "https://db.pokemongohub.net/api/pokemon/counters/";
	private static final Gson GSON;
	private static final JsonParser PARSER = new JsonParser();

	private static final Map<String, CachedData<PokedexEntry>> POKEMON_CACHE = new HashMap<>();
	private static final Map<String, CachedData<DexMove[]>> POKEMON_MOVES_CACHE = new HashMap<>();
	private static final Map<Integer, CachedData<DexMove>> MOVE_CACHE = new HashMap<>();
	private static final Map<String, CachedData<DexMoveset[]>> MOVESET_CACHE = new HashMap<>();
	private static final Map<String, CachedData<DexCounter[]>> COUNTER_CACHE = new HashMap<>();

	private static <K, V> boolean isCached(Map<K, CachedData<V>> cache, K key) {
		if (!cache.containsKey(key))
			return false;
		if (!cache.get(key).hasPassed(360000)) // 60 mins
			return true;
		return false;
	}

	static {
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(EnumPokemonType.class, new JsonDeserializer<EnumPokemonType>() {

			@Override
			public EnumPokemonType deserialize(JsonElement json, Type typeOfT,
					JsonDeserializationContext context) throws JsonParseException {
				return EnumPokemonType.valueOf(json.getAsString().toUpperCase());
			}
		});
		builder.registerTypeAdapter(EnumWeather.class, new JsonDeserializer<EnumWeather>() {

			@Override
			public EnumWeather deserialize(JsonElement json, Type typeOfT,
					JsonDeserializationContext context) throws JsonParseException {
				String asString = json.getAsString().toUpperCase();
				if (asString.equals("SUNNY"))
					return EnumWeather.CLEAR_SUNNY;
				if (asString.equals("PARTLYCLOUDY"))
					return EnumWeather.PARTY_CLOUDY;
				return EnumWeather.valueOf(asString);
			}
		});
		builder.registerTypeAdapter(DexCounter.class, new JsonDeserializer<DexCounter>() {

			@Override
			public DexCounter deserialize(JsonElement json, Type typeOfT,
					JsonDeserializationContext context) throws JsonParseException {
				if (!json.isJsonArray())
					return null;
				JsonArray jarr = json.getAsJsonArray();
				DexCounter counter = new DexCounter();
				counter.pokemonId = jarr.get(0).getAsInt();
				counter.name = jarr.get(1).getAsString();
				if (!jarr.get(2).isJsonNull())
					counter.formName = jarr.get(2).getAsString();
				counter.quickMove = GoHubDatabase.getMove(jarr.get(3).getAsInt());
				counter.chargedMove = GoHubDatabase.getMove(jarr.get(6).getAsInt());
				if (!jarr.get(9).isJsonNull())
					counter.deaths = Integer.parseInt(jarr.get(9).getAsString());
				if (!jarr.get(10).isJsonNull())
					counter.ttw = jarr.get(10).getAsFloat();
				if (!jarr.get(11).isJsonNull())
					counter.score = jarr.get(11).getAsFloat();
				return counter;
			}
		});
		GSON = builder.create();
	}

	public static boolean isEntryLoaded(EnumPokemon pokemon) {
		return POKEMON_CACHE.containsKey(pokemon.toString().toLowerCase() + "Normal");
	}

	public static PokedexEntry getEntry(EnumPokemon pokemon) {
		return getEntry(pokemon, "Normal");
	}

	public static PokedexEntry getEntry(EnumPokemon pokemon, String form) {
		String key = pokemon.toString().toLowerCase() + form;
		if (isCached(POKEMON_CACHE, key))
			return POKEMON_CACHE.get(key).getValue();
		try {
			URL url;
			if (form == null || (form.equals("Normal") && !pokemon.equals(EnumPokemon.ARCEUS)))
				url = new URL(POKEMON_API + pokemon.getId());
			else
				url = new URL(POKEMON_API + pokemon.getId() + "?form=" + form);
			URLConnection conn = url.openConnection();
			conn.setRequestProperty("User-Agent", Starota.HTTP_USER_AGENT);
			PokedexEntry entry = GSON.fromJson(
					PARSER.parse(new InputStreamReader(conn.getInputStream())), PokedexEntry.class);
			POKEMON_CACHE.put(key, new CachedData<>(entry));
			return entry;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static DexMove[] getMoves(EnumPokemon pokemon) {
		return getMoves(pokemon, "Normal");
	}

	public static DexMove[] getMoves(EnumPokemon pokemon, String form) {
		String key = pokemon.toString().toLowerCase() + form;
		if (isCached(POKEMON_MOVES_CACHE, key))
			return POKEMON_MOVES_CACHE.get(key).getValue();
		try {
			URL url;
			if (form == null || (form.equals("Normal") && !pokemon.equals(EnumPokemon.ARCEUS)))
				url = new URL(POKEMON_MOVES_API + pokemon.getId());
			else
				url = new URL(POKEMON_MOVES_API + pokemon.getId() + "?form=" + form);
			URLConnection conn = url.openConnection();
			conn.setRequestProperty("User-Agent", Starota.HTTP_USER_AGENT);
			DexMove[] moves = GSON.fromJson(PARSER.parse(new InputStreamReader(conn.getInputStream())),
					DexMove[].class);
			POKEMON_MOVES_CACHE.put(key, new CachedData<>(moves));
			return moves;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static DexMove getMove(int moveId) {
		if (isCached(MOVE_CACHE, moveId))
			return MOVE_CACHE.get(moveId).getValue();
		try {
			URL url = new URL(MOVES_API + moveId);
			URLConnection conn = url.openConnection();
			conn.setRequestProperty("User-Agent", Starota.HTTP_USER_AGENT);
			DexMove move = GSON.fromJson(PARSER.parse(new InputStreamReader(conn.getInputStream())),
					DexMove.class);
			MOVE_CACHE.put(moveId, new CachedData<>(move));
			return move;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	// public static DexMove[] getAllMoves() {
	// try {
	// URL url = new URL(FAST_MOVES_LIST_API);
	// URLConnection conn = url.openConnection();
	// conn.setRequestProperty("User-Agent", Starota.HTTP_USER_AGENT);
	// DexMove[] moves = GSON.fromJson(PARSER.parse(new
	// InputStreamReader(conn.getInputStream())),
	// DexMove[].class);
	// // MOVE_CACHE.put(moveId, new CachedData<>(move));
	// return moves;
	// } catch (IOException e) {
	// e.printStackTrace();
	// return null;
	// }
	// }

	public static DexMoveset[] getMovesets(EnumPokemon pokemon) {
		return getMovesets(pokemon, "Normal");
	}

	public static DexMoveset[] getMovesets(EnumPokemon pokemon, String form) {
		String key = pokemon.toString().toLowerCase() + form;
		if (isCached(MOVESET_CACHE, key))
			return MOVESET_CACHE.get(key).getValue();
		try {
			URL url;
			if (form == null || (form.equals("Normal") && !pokemon.equals(EnumPokemon.ARCEUS)))
				url = new URL(MOVESETS_API + pokemon.getId());
			else
				url = new URL(MOVESETS_API + pokemon.getId() + "?form=" + form);
			URLConnection conn = url.openConnection();
			conn.setRequestProperty("User-Agent", Starota.HTTP_USER_AGENT);
			DexMoveset[] movesets = GSON.fromJson(
					PARSER.parse(new InputStreamReader(conn.getInputStream())), DexMoveset[].class);
			MOVESET_CACHE.put(key, new CachedData<>(movesets));
			return movesets;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static DexCounter[] getCounters(EnumPokemon pokemon) {
		return getCounters(pokemon, "Normal");
	}

	public static DexCounter[] getCounters(EnumPokemon pokemon, String form) {
		String key = pokemon.toString().toLowerCase() + form;
		if (isCached(COUNTER_CACHE, key))
			return COUNTER_CACHE.get(key).getValue();
		try {
			URL url;
			if (form == null || (form.equals("Normal") && !pokemon.equals(EnumPokemon.ARCEUS)))
				url = new URL(COUNTERS_API + pokemon.getId());
			else
				url = new URL(COUNTERS_API + pokemon.getId() + "?form=" + form);
			URLConnection conn = url.openConnection();
			conn.setRequestProperty("User-Agent", Starota.HTTP_USER_AGENT);
			DexCounter[] counters = GSON.fromJson(
					PARSER.parse(new InputStreamReader(conn.getInputStream())), DexCounter[].class);
			COUNTER_CACHE.put(key, new CachedData<>(counters));
			return counters;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	@ClearCache("gohub")
	public static void dumpCache() {
		POKEMON_CACHE.clear();
		POKEMON_MOVES_CACHE.clear();
		MOVE_CACHE.clear();
		MOVESET_CACHE.clear();
		COUNTER_CACHE.clear();
	}

}
