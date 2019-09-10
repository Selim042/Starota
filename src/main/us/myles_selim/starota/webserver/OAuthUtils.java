package us.myles_selim.starota.webserver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.annotation.Nullable;
import javax.net.ssl.HttpsURLConnection;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import us.myles_selim.starota.misc.utils.StarotaConstants;

public class OAuthUtils {

	private static final Gson GSON = new Gson();
	private static final String API_URL = "https://discordapp.com/api/v6";

	public static boolean isTokenValid(String token) {
		return getUser(token) != null;
	}

	public static OAuthUser getUser(String accessToken) {
		try {
			URL tokenUrl = new URL(API_URL + "/users/@me");
			HttpsURLConnection con = (HttpsURLConnection) tokenUrl.openConnection();

			con.setRequestMethod("GET");
			con.setRequestProperty("User-Agent", WebServer.USER_AGENT);
			con.setRequestProperty("Authorization", "Bearer " + accessToken);

			int responseCode = con.getResponseCode();
			BufferedReader in;
			if (responseCode != 200)
				return null;
			else
				in = new BufferedReader(new InputStreamReader(con.getInputStream()));

			String inputLine;
			String oAuthResponse = "";

			while ((inputLine = in.readLine()) != null)
				oAuthResponse += inputLine;
			in.close();
			JsonElement root = new JsonParser().parse(oAuthResponse);
			return GSON.fromJson(root, OAuthUser.class);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static OAuthGuildPart[] getUserGuilds(String accessToken) {
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
			JsonElement root = new JsonParser().parse(oAuthResponse);
			return GSON.fromJson(root, OAuthGuildPart[].class);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static OAuthLogin authenticate(String code, String redirect) {
		StringBuilder output = new StringBuilder();
		try {
			String params = String.format(//
					"client_id=%s&" + //
							"client_secret=%s&" + //
							"grant_type=authorization_code&" + //
							"code=%s&" + //
							"redirect_uri=%s&" + //
							"scope=identify email guilds",
					StarotaConstants.STAROTA_ID.asString(), WebServer.CLIENT_SECRET, code, redirect);
			byte[] paramsData = params.getBytes("UTF-8");
			HttpURLConnection conn = (HttpURLConnection) new URL(API_URL + "/oauth2/token")
					.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.setRequestProperty("User-Agent", StarotaConstants.HTTP_USER_AGENT);
			conn.setRequestProperty("Content-Length", Integer.toString(paramsData.length));
			conn.setUseCaches(false);
			try (DataOutputStream wr = new DataOutputStream(conn.getOutputStream())) {
				wr.write(paramsData);
			}
			BufferedReader out = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			out.lines().forEach((s) -> output.append(s + "\n"));
			if (conn.getResponseCode() != 200) {
				System.out
						.println(conn.getResponseCode() + " error encountered when authenticating user");
				System.out.println(output.toString());
				return null;
			}
		} catch (IOException e) {
			return null;
		}
		return GSON.fromJson(output.toString(), OAuthLogin.class);
	}

	protected static class OAuthUser {

		public String id;
		public String username;
		public String discriminator;
		@Nullable
		public String avatar;
		public boolean bot;
		public boolean mfa_enabled;
		public String locale;
		public boolean verified;
		public String email;
		public int flags;
		public int premium_type;

		public String getAvatarURL() {
			if (avatar == null)
				return null;
			if (avatar.substring(0, 2).equals("a_"))
				return "https://cdn.discordapp.com/avatars/" + id + "/" + avatar + ".gif";
			return "https://cdn.discordapp.com/avatars/" + id + "/" + avatar + ".png";
		}

	}

	protected static class OAuthGuildPart {

		public String id;
		public String name;
		@Nullable
		public String icon;
		public boolean owner;
		public int permissions;

	}

	protected static class OAuthLogin {

		public String access_token;
		public String scope;
		public String token_type;
		public long expires_in;
		public String refresh_token;

		public String[] getScopes() {
			return scope.split(" ");
		}

	}

}