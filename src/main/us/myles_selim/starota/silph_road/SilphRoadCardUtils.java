package us.myles_selim.starota.silph_road;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import us.myles_selim.starota.misc.data_types.cache.CachedData;
import us.myles_selim.starota.misc.data_types.cache.ClearCache;
import us.myles_selim.starota.misc.utils.StarotaConstants;
import us.myles_selim.starota.silph_road.SilphCard.SilphBadgeData;
import us.myles_selim.starota.silph_road.SilphCard.SilphCheckin;

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
					JsonDeserializationContext ctx) throws JsonParseException {
				List<SilphCheckin> vals = new ArrayList<SilphCheckin>();
				if (json.isJsonArray()) {
					for (JsonElement e : json.getAsJsonArray()) {
						vals.add((SilphCheckin) ctx.deserialize(e, SilphCheckin.class));
					}
				} else if (json.isJsonObject()) { /* */ } else {
					throw new RuntimeException("Unexpected JSON type: " + json.getClass());
				}
				return vals.toArray(new SilphCheckin[0]);
			}
		});
		builder.registerTypeAdapter(SilphBadgeData[].class, new JsonDeserializer<SilphBadgeData[]>() {

			@Override
			public SilphBadgeData[] deserialize(JsonElement json, Type typeOfT,
					JsonDeserializationContext ctx) throws JsonParseException {
				List<SilphBadgeData> vals = new ArrayList<SilphBadgeData>();
				if (json.isJsonArray()) {
					for (JsonElement e : json.getAsJsonArray()) {
						vals.add((SilphBadgeData) ctx.deserialize(e, SilphBadgeData.class));
					}
				} else if (json.isJsonObject()) { /* */ } else {
					throw new RuntimeException("Unexpected JSON type: " + json.getClass());
				}
				return vals.toArray(new SilphBadgeData[0]);
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
			conn.setRequestProperty("User-Agent", StarotaConstants.HTTP_USER_AGENT);
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
			conn.setRequestProperty("User-Agent", StarotaConstants.HTTP_USER_AGENT);
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
		CARD_CACHE.remove(pogoName);
		if (!hasCard(pogoName) && HAS_CARD_CACHE.get(pogoName) != null
				&& HAS_CARD_CACHE.get(pogoName).getValue() == false)
			return null;
		if (isCached(CARD_CACHE, pogoName))
			return CARD_CACHE.get(pogoName).getValue();
		try {
			URL url = new URL(API_URL + pogoName + ".json");
			URLConnection conn = url.openConnection();
			conn.setRequestProperty("User-Agent", StarotaConstants.HTTP_USER_AGENT);
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
