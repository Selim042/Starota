package us.myles_selim.starota.profiles;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import us.myles_selim.starota.Starota;

public class SilphRoadUtils {

	private static final String API_URL = "https://sil.ph/";
	private static final JsonParser PARSER = new JsonParser();

	public static boolean hasCard(String pogoName) {
		try {
			URL url = new URL(API_URL + pogoName + ".json");
			URLConnection conn = url.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String json = "";
			String line = null;
			while ((line = in.readLine()) != null)
				json += line;
			JsonObject root = PARSER.parse(json).getAsJsonObject();
			return root.get("data") != null;
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
