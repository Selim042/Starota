package us.myles_selim.starota.webserver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import us.myles_selim.starota.Starota;

public class HttpHandlerLogin implements HttpHandler {

	public static final String TEST_OAUTH_URI = "https://discordapp.com/api/oauth2/authorize?client_id=489245655710040099&redirect_uri=http%3A%2F%2Flocalhost%3A7366%2Flogin%2F&response_type=code&scope=identify%20guilds";
	public static final String OAUTH_URI = "https://discordapp.com/api/oauth2/authorize?client_id=489245655710040099&redirect_uri=http%3A%2F%2Fstarota.myles-selim.us%2Flogin%2F&response_type=code&scope=identify%20guilds";

	private static final String TOKEN_URL = "https://discordapp.com/api/oauth2/token";

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		String uri = exchange.getRequestURI().toString();
		if (!uri.contains("?code=")) {
			initialRedirect(exchange);
			return;
		}
		String code = uri.substring(uri.lastIndexOf("=") + 1, uri.length());
		URL tokenUrl = new URL(TOKEN_URL);
		HttpsURLConnection con = (HttpsURLConnection) tokenUrl.openConnection();

		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", WebServer.USER_AGENT);
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		con.setDoOutput(true);

		String params = "code=" + code + "&client_secret=" + WebServer.CLIENT_SECRET + "&" + "client_id="
				+ WebServer.CLIENT_ID + "&" + "grant_type=client_credentials&"
				+ "scope=identify%20guilds&" + "redirect_uri=http://starota.myles-selim.us/login/";
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(params);
		wr.flush();
		wr.close();

		int responseCode = con.getResponseCode();
		BufferedReader in;
		if (responseCode >= 400)
			in = new BufferedReader(new InputStreamReader(con.getErrorStream()));
		else
			in = new BufferedReader(new InputStreamReader(con.getInputStream()));

		String inputLine;
		String oAuthResponse = "";

		while ((inputLine = in.readLine()) != null)
			oAuthResponse += inputLine;
		in.close();
		// System.out.println(oAuthResponse);
		JsonObject root;
		try {
			root = new JsonParser().parse(oAuthResponse).getAsJsonObject();
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return;
		}

		if (!root.has("access_token")) {
			// System.out.println("no access token");
			System.out.println(oAuthResponse);
			// return;
		}
		String accessToken = root.get("access_token").getAsString();
		String response = accessToken + "\n";
		response += OAuthUtils.getUserId(accessToken) + "\n";
		for (long l : OAuthUtils.getUserGuilds(accessToken))
			response += l + "\n";

		Headers headers = exchange.getResponseHeaders();
		List<String> cookies = new ArrayList<>();
		cookies.add("accessToken=" + accessToken + "; Path=/; Expires="
				+ Instant.ofEpochSecond((System.currentTimeMillis() / 1000)
						+ root.getAsJsonObject().get("expires_in").getAsLong()));
		headers.put("Set-Cookie", cookies);
		exchange.sendResponseHeaders(200, response.length());
		OutputStream out = exchange.getResponseBody();
		out.write(response.getBytes());
		out.close();
		exchange.close();
	}

	private void initialRedirect(HttpExchange exchange) throws IOException {
		// Headers reqHeaders = exchange.getResponseHeaders();
		// reqHeaders.add("Location", OAUTH_URI);
		// exchange.sendResponseHeaders(307, 0);
		String response = "<html><body><a href=\"" + (Starota.IS_DEV ? TEST_OAUTH_URI : OAUTH_URI)
				+ "\">Login</a></body></html>";
		exchange.sendResponseHeaders(200, response.length());
		OutputStream output = exchange.getResponseBody();
		output.write(response.getBytes());
		output.close();
		exchange.close();
	}

}
