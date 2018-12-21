package us.myles_selim.starota.profiles;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import us.myles_selim.starota.CachedData;

public class SilphRoadUtils {

	private static final String API_URL = "https://sil.ph/";
	private static final JsonParser PARSER = new JsonParser();

	private static final Map<String, CachedData<Boolean>> CARD_CACHE = new HashMap<>();

	public static boolean hasCard(String pogoName) {
		if (CARD_CACHE.containsKey(pogoName)) {
			CachedData<Boolean> cData = CARD_CACHE.get(pogoName);
			if (!cData.hasPassed(60000)) // 10 mins
				return cData.getValue();
		}
		try {
			URL url = new URL(API_URL + pogoName + ".json");
			URLConnection conn = url.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String json = "";
			String line = null;
			while ((line = in.readLine()) != null)
				json += line;
			JsonObject root = PARSER.parse(json).getAsJsonObject();
			boolean hasCard = root.get("data") != null;
			CARD_CACHE.put(pogoName, new CachedData<>(hasCard));
			return hasCard;
		} catch (IOException e) {
			return false;
		}
	}

	public static String getCardAvatar(String pogoName) {
		try {
			URL url = new URL(API_URL + pogoName + ".json");
			URLConnection conn = url.openConnection();
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

	public static String getCard(String pogoName) {
		if (!hasCard(pogoName))
			return null;
		return API_URL + pogoName;
	}

}
