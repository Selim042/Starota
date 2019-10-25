package us.myles_selim.starota.weather.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.Gson;

import us.myles_selim.starota.misc.utils.StarotaConstants;
import us.myles_selim.starota.wrappers.BotServer;

public class AccuWeatherAPI {

	private static final Gson GSON = new Gson();

	private static final String GEOPOSITION_SEARCH = "http://dataservice.accuweather.com/locations/v1/cities/geoposition/search";
	private static final String FORECAST_12_HOUR = "http://dataservice.accuweather.com/forecasts/v1/hourly/12hour/";

	public static WeatherForecast[] getForecast(BotServer server) {
		String weatherToken = server.getSetting(StarotaConstants.Settings.WEATHER_API_TOKEN);
		WeatherLocation location = server.getLocation();
		if (weatherToken != null && !weatherToken.equals("null") && location != null) {
			try {
				URL url = new URL(FORECAST_12_HOUR
						+ String.format(location.getKey() + "?apikey=%s&details=true", weatherToken));
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setRequestProperty("User-Agent", StarotaConstants.HTTP_USER_AGENT);
				conn.connect();
				if (conn.getResponseCode() != 200)
					return new WeatherForecast[0];
				BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				String json = "";
				String line = null;
				while ((line = in.readLine()) != null)
					json += line;
				in.close();
				return GSON.fromJson(json, WeatherForecast[].class);
			} catch (IOException e) {
				e.printStackTrace();
				return new WeatherForecast[0];
			}
		}
		return new WeatherForecast[0];
	}

	public static WeatherLocation getLocation(BotServer server, String coords) {
		String weatherToken = server.getSetting(StarotaConstants.Settings.WEATHER_API_TOKEN);
		if (weatherToken == null || weatherToken.equals("null") || coords == null
				|| coords.equals("null"))
			return null;
		try {
			URL url = new URL(
					GEOPOSITION_SEARCH + String.format("?apikey=%s&q=%s", weatherToken, coords));
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestProperty("User-Agent", StarotaConstants.HTTP_USER_AGENT);
			conn.connect();
			if (conn.getResponseCode() != 200)
				return null;
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String json = "";
			String line = null;
			while ((line = in.readLine()) != null)
				json += line;
			in.close();
			return GSON.fromJson(json, WeatherLocation.class);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

}
