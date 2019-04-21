package us.myles_selim.starota.silph_road;

import java.io.BufferedReader;
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
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import us.myles_selim.starota.Starota;
import us.myles_selim.starota.misc.data_types.cache.CachedData;
import us.myles_selim.starota.misc.data_types.cache.ClearCache;
import us.myles_selim.starota.silph_road.SilphCard.SilphCheckin;
import us.myles_selim.starota.silph_road.SilphCard.SilphCheckinTime;

public class SilphRoadCardUtils {

	private static final String API_URL = "https://sil.ph/";
	private static final JsonParser PARSER = new JsonParser();
	private static final Gson GSON;

	private static final Map<String, CachedData<Boolean>> HAS_CARD_CACHE = new HashMap<>();
	private static final Map<String, CachedData<SilphCard>> CARD_CACHE = new HashMap<>();

	static {
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(SilphCheckin[].class, new JsonDeserializer<SilphCheckin[]>() {

			@Override
			public SilphCheckin[] deserialize(JsonElement json, Type typeOfT,
					JsonDeserializationContext context) throws JsonParseException {
				if (!json.isJsonArray())
					return new SilphCheckin[0];
				JsonArray arr = json.getAsJsonArray();
				SilphCheckin[] ret = new SilphCheckin[arr.size()];
				for (int i = 0; i < arr.size(); i++) {
					SilphCheckin checkin = new SilphCheckin();
					JsonObject jObj = arr.get(i).getAsJsonObject();
					if (!jObj.get("name").isJsonNull())
						checkin.name = jObj.get("name").getAsString();
					if (!jObj.get("description").isJsonNull())
						checkin.description = jObj.get("description").getAsString();
					if (!jObj.get("image").isJsonNull())
						checkin.image = jObj.get("image").getAsString();
					if (!jObj.get("is_global").isJsonNull())
						checkin.is_global = jObj.get("is_global").getAsString();
					if (!jObj.get("start").isJsonNull())
						checkin.start = jObj.get("start").getAsString();
					if (!jObj.get("end").isJsonNull())
						checkin.end = jObj.get("end").getAsString();
					if (!jObj.get("EventCheckin").isJsonNull()) {
						checkin.EventCheckin = new SilphCheckinTime();
						checkin.EventCheckin.created = jObj.get("EventCheckin").getAsJsonObject()
								.get("created").getAsString();
					}
					ret[i] = checkin;
				}
				return ret;
			}
		});
		GSON = builder.create();
	}

	private static <K, V> boolean isCached(Map<K, CachedData<V>> cache, K key) {
		if (!cache.containsKey(key))
			return false;
		if (!cache.get(key).hasPassed(60000)) // 10 mins
			return true;
		return false;
	}

	public static boolean hasCard(String pogoName) {
		if (isCached(HAS_CARD_CACHE, pogoName))
			return HAS_CARD_CACHE.get(pogoName).getValue();
		try {
			URL url = new URL(API_URL + pogoName + ".json");
			URLConnection conn = url.openConnection();
			conn.setRequestProperty("User-Agent", Starota.HTTP_USER_AGENT);
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String json = "";
			String line = null;
			while ((line = in.readLine()) != null)
				json += line;
			JsonObject root = PARSER.parse(json).getAsJsonObject();
			boolean hasCard = root.get("data") != null;
			HAS_CARD_CACHE.put(pogoName, new CachedData<>(hasCard));
			return hasCard;
		} catch (IOException e) {
			return false;
		}
	}

	public static String getCardAvatar(String pogoName) {
		try {
			URL url = new URL(API_URL + pogoName + ".json");
			URLConnection conn = url.openConnection();
			conn.setRequestProperty("User-Agent", Starota.HTTP_USER_AGENT);
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String json = "";
			String line = null;
			while ((line = in.readLine()) != null)
				json += line;
			JsonObject root = PARSER.parse(json).getAsJsonObject();
			JsonElement dataE = root.get("data");
			if (dataE == null)
				return null;
			return dataE.getAsJsonObject().get("avatar").getAsString();
		} catch (IOException e) {
			return null;
		}
	}

	public static String getCardURL(String pogoName) {
		if (!hasCard(pogoName))
			return null;
		return API_URL + pogoName;
	}

	public static SilphCard getCard(String pogoName) {
		if (!hasCard(pogoName) && HAS_CARD_CACHE.get(pogoName).getValue() == false)
			return null;
		if (isCached(CARD_CACHE, pogoName))
			return CARD_CACHE.get(pogoName).getValue();
		try {
			URL url = new URL(API_URL + pogoName + ".json");
			URLConnection conn = url.openConnection();
			conn.setRequestProperty("User-Agent", Starota.HTTP_USER_AGENT);
			SilphCard card = GSON.fromJson(PARSER.parse(new InputStreamReader(conn.getInputStream())),
					SilphCard.class);
			CARD_CACHE.put(pogoName, new CachedData<>(card));
			return card;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	@ClearCache("silphroadcards")
	public static void dumpCache() {
		HAS_CARD_CACHE.clear();
		CARD_CACHE.clear();
	}

}
