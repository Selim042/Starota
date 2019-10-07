package us.myles_selim.starota.weather.api;

public class WeatherAdminArea extends WeatherRegion {

	protected WeatherAdminArea() {}

	private int Level;
	private String LocalizedType;
	private String EnglishType;
	private String CountryID;

	public int getLevel() {
		return Level;
	}

	public String getLocalizedType() {
		return LocalizedType;
	}

	public String getEnglishType() {
		return EnglishType;
	}

	public String getCountryID() {
		return CountryID;
	}

}
