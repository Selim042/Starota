package us.myles_selim.starota.weather.api;

public class WeatherRegion {

	protected WeatherRegion() {}

	private String ID;
	private String LocalizedName;
	private String EnglishName;

	public String getID() {
		return ID;
	}

	public String getLocalizedName() {
		return LocalizedName;
	}

	public String getEnglishName() {
		return EnglishName;
	}

}
