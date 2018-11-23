package us.myles_selim.starota.webserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class OAuthUtils {

	private static final String API_URL = "https://discordapp.com/api/v6";

	public static long getUserId(String accessToken) {
		try {
			URL tokenUrl = new URL(API_URL + "/users/@me");
			HttpsURLConnection con = (HttpsURLConnection) tokenUrl.openConnection();

			con.setRequestMethod("GET");
			con.setRequestProperty("User-Agent", WebServer.USER_AGENT);
			con.setRequestProperty("Authorization", "Bearer " + accessToken);

			int responseCode = con.getResponseCode();
			BufferedReader in;
			if (responseCode >= 400)
				return -1;
			else
				in = new BufferedReader(new InputStreamReader(con.getInputStream()));

			String inputLine;
			String oAuthResponse = "";

			while ((inputLine = in.readLine()) != null)
				oAuthResponse += inputLine;
			in.close();
			JsonElement root = new JsonParser().parse(oAuthResponse);
			return root.getAsJsonObject().get("id").getAsLong();
		} catch (IOException e) {
			e.printStackTrace();
			return -1;
		}
	}

	public static List<Long> getUserGuilds(String accessToken) {
		try {
			URL tokenUrl = new URL(API_URL + "/users/@me/guilds");
			HttpsURLConnection con = (HttpsURLConnection) tokenUrl.openConnection();

			con.setRequestMethod("GET");
			con.setRequestProperty("User-Agent", WebServer.USER_AGENT);
			con.setRequestProperty("Authorization", "Bearer " + accessToken);

			int responseCode = con.getResponseCode();
			BufferedReader in;
			if (responseCode >= 400)
				return null;
			else
				in = new BufferedReader(new InputStreamReader(con.getInputStream()));

			String inputLine;
			String oAuthResponse = "";

			while ((inputLine = in.readLine()) != null)
				oAuthResponse += inputLine;
			in.close();
			// System.out.println(oAuthResponse);
			List<Long> servers = new ArrayList<>();
			JsonElement root = new JsonParser().parse(oAuthResponse);
			JsonArray arr = root.getAsJsonArray();
			for (int i = 0; i < arr.size(); i++) {
				JsonObject s = arr.get(i).getAsJsonObject();
				servers.add(s.get("id").getAsLong());
			}
			return Collections.unmodifiableList(servers);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

}
