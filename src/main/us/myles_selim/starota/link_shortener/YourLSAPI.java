package us.myles_selim.starota.link_shortener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import com.google.gson.Gson;

import us.myles_selim.starota.misc.utils.StarotaConstants;

public class YourLSAPI {

	private static final Gson GSON = new Gson();
	private static final String API_ENDPOINT = "http://s.myles-selim.us/yourls-api.php?";
	private static String SIGNATURE;

	public static void setSignature(String signature) {
		if (SIGNATURE == null)
			SIGNATURE = signature;
	}

	public static YourLSResponse getShortLink(String urlS) {
		try {
			@SuppressWarnings("deprecation")
			URL url = new URL(API_ENDPOINT + "signature=" + SIGNATURE
					+ "&format=json&action=shorturl&url=" + URLEncoder.encode(urlS));
			URLConnection conn = url.openConnection();
			conn.setRequestProperty("User-Agent", StarotaConstants.HTTP_USER_AGENT);
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String resp = "";
			String line = null;
			while ((line = in.readLine()) != null)
				resp += line;
			return GSON.fromJson(resp, YourLSResponse.class);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

}
