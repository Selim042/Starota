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

import us.myles_selim.starota.CachedData;
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.enums.EnumPokemon;
import us.myles_selim.starota.enums.EnumPokemonType;
import us.myles_selim.starota.enums.EnumWeather;
import us.myles_selim.starota.pokedex.PokedexEntry.Move;
import us.myles_selim.starota.pokedex.PokedexEntry.Moveset;

public class GoHubDatabase {

	private static final String POKEMON_API = "https://db.pokemongohub.net/api/pokemon/";
	private static final String POKEMON_MOVES_API = "https://db.pokemongohub.net/api/moves/with-pokemon/";
	private static final String MOVES_API = "https://db.pokemongohub.net/api/moves/";
	private static final String MOVESETS_API = "https://db.pokemongohub.net/api/movesets/with-pokemon/";
	private static final String COUNTERS_API = "https://db.pokemongohub.net/api/pokemon/counters/";
	private static final Gson GSON;
	private static final JsonParser PARSER = new JsonParser();

	private static final Map<Integer, CachedData<PokedexEntry>> POKEMON_CACHE = new HashMap<>();
	private static final Map<Integer, CachedData<Move[]>> POKEMON_MOVES_CACHE = new HashMap<>();
	private static final Map<Integer, CachedData<Move>> MOVE_CACHE = new HashMap<>();
	private static final Map<Integer, CachedData<Moveset[]>> MOVESET_CACHE = new HashMap<>();
	private static final Map<Integer, CachedData<Counter[]>> COUNTER_CACHE = new HashMap<>();

	private static <V> boolean isCached(Map<Integer, CachedData<V>> cache, int key) {
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
				if (json.getAsString().toLowerCase().equals("sunny"))
					return EnumWeather.CLEAR_SUNNY;
				return EnumWeather.valueOf(json.getAsString().toUpperCase());
			}
		});
		builder.registerTypeAdapter(Counter.class, new JsonDeserializer<Counter>() {

			@Override
			public Counter deserialize(JsonElement json, Type typeOfT,
					JsonDeserializationContext context) throws JsonParseException {
				if (!json.isJsonArray())
					return null;
				JsonArray jarr = json.getAsJsonArray();
				Counter counter = new Counter();
				counter.pokemonId = jarr.get(0).getAsInt();
				counter.name = jarr.get(1).getAsString();
				if (!jarr.get(2).isJsonNull())
					counter.formName = jarr.get(2).getAsString();
				counter.quickMove = GoHubDatabase.getMove(jarr.get(3).getAsInt());
				counter.chargedMove = GoHubDatabase.getMove(jarr.get(6).getAsInt());
				counter.deaths = Integer.parseInt(jarr.get(9).getAsString());
				counter.ttw = jarr.get(10).getAsFloat();
				counter.score = jarr.get(11).getAsFloat();
				return counter;
			}
		});
		GSON = builder.create();
	}

	public static PokedexEntry getEntry(EnumPokemon pokemon) {
		if (isCached(POKEMON_CACHE, pokemon.getId()))
			return POKEMON_CACHE.get(pokemon.getId()).getValue();
		try {
			URL url = new URL(POKEMON_API + pokemon.getId());
			URLConnection conn = url.openConnection();
			conn.setRequestProperty("User-Agent", Starota.HTTP_USER_AGENT);
			PokedexEntry entry = GSON.fromJson(
					PARSER.parse(new InputStreamReader(conn.getInputStream())), PokedexEntry.class);
			POKEMON_CACHE.put(pokemon.getId(), new CachedData<>(entry));
			return entry;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Move[] getMoves(EnumPokemon pokemon) {
		if (isCached(POKEMON_MOVES_CACHE, pokemon.getId()))
			return POKEMON_MOVES_CACHE.get(pokemon.getId()).getValue();
		try {
			URL url = new URL(POKEMON_MOVES_API + pokemon.getId());
			URLConnection conn = url.openConnection();
			conn.setRequestProperty("User-Agent", Starota.HTTP_USER_AGENT);
			Move[] moves = GSON.fromJson(PARSER.parse(new InputStreamReader(conn.getInputStream())),
					Move[].class);
			POKEMON_MOVES_CACHE.put(pokemon.getId(), new CachedData<>(moves));
			return moves;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Move getMove(int moveId) {
		if (isCached(MOVE_CACHE, moveId))
			return MOVE_CACHE.get(moveId).getValue();
		try {
			URL url = new URL(MOVES_API + moveId);
			URLConnection conn = url.openConnection();
			conn.setRequestProperty("User-Agent", Starota.HTTP_USER_AGENT);
			Move move = GSON.fromJson(PARSER.parse(new InputStreamReader(conn.getInputStream())),
					Move.class);
			MOVE_CACHE.put(moveId, new CachedData<>(move));
			return move;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Moveset[] getMovesets(EnumPokemon pokemon) {
		if (isCached(MOVESET_CACHE, pokemon.getId()))
			return MOVESET_CACHE.get(pokemon.getId()).getValue();
		try {
			URL url = new URL(MOVESETS_API + pokemon.getId());
			URLConnection conn = url.openConnection();
			conn.setRequestProperty("User-Agent", Starota.HTTP_USER_AGENT);
			Moveset[] movesets = GSON.fromJson(
					PARSER.parse(new InputStreamReader(conn.getInputStream())), Moveset[].class);
			MOVESET_CACHE.put(pokemon.getId(), new CachedData<>(movesets));
			return movesets;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Counter[] getCounters(EnumPokemon pokemon) {
		if (isCached(COUNTER_CACHE, pokemon.getId()))
			return COUNTER_CACHE.get(pokemon.getId()).getValue();
		try {
			URL url = new URL(COUNTERS_API + pokemon.getId());
			URLConnection conn = url.openConnection();
			conn.setRequestProperty("User-Agent", Starota.HTTP_USER_AGENT);
			Counter[] counters = GSON.fromJson(
					PARSER.parse(new InputStreamReader(conn.getInputStream())), Counter[].class);
			COUNTER_CACHE.put(pokemon.getId(), new CachedData<>(counters));
			return counters;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

}
