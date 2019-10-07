package us.myles_selim.starota.weather.api;

import us.myles_selim.starota.enums.EnumWeather;
import us.myles_selim.starota.weather.api.WeatherWind.WindGust;

public class WeatherForecast {

	protected WeatherForecast() {}

	private String DateTime;
	private long EpochDateTime;

	private int WeatherIcon;
	private String IconPhrase;
	private boolean HasPrecipitation;
	private boolean IsDaylight;

	private WeatherUnit Temperature;
	private WeatherUnit RealFeelTemperature;
	private WeatherUnit WebBulbTemperature;
	private WeatherUnit DewPoint;

	private WeatherWind Wind;
	private WindGust WindGust;

	private int RelativeHumidity;

	private WeatherUnit Visibility;
	private WeatherUnit Ceiling;

	private int UVIndex;
	private String UVIndexText;

	private int PrecipitationProbability;
	private int RainProbability;
	private int SnowProbability;
	private int IceProbability;

	private WeatherUnit TotalLiquid;
	private WeatherUnit Rain;
	private WeatherUnit Snow;
	private WeatherUnit Ice;

	private int CloudCover;

	private String MobileLink;
	private String Link;

	private static final int WINDY_SPEED_LIMIT = 15;
	private static final int GUSTY_SPEED_LIMIT = 20;

	// according to
	// https://www.reddit.com/r/TheSilphRoad/comments/amb4ki/predicting_ingame_weather_yes_you_can/
	public EnumWeather getPoGoWeather() {
		switch (WeatherIcon) {
		case 1: // sunny
		case 2: // mostly sunny
		case 33: // clear
		case 34: // mostly clear
			if (Wind.getSpeed().getValue() > WINDY_SPEED_LIMIT
					|| WindGust.getSpeed().getValue() > GUSTY_SPEED_LIMIT)
				return EnumWeather.WINDY;
			return EnumWeather.CLEAR;
		case 30: // hot
			return EnumWeather.CLEAR;
		case 3: // partly sunny
		case 4: // intermittent clouds
		case 35: // partly cloudy
		case 36: // intermittent clouds
			if (Wind.getSpeed().getValue() > WINDY_SPEED_LIMIT
					|| WindGust.getSpeed().getValue() > GUSTY_SPEED_LIMIT)
				return EnumWeather.WINDY;
			return EnumWeather.PARTLY_CLOUDY;
		case 14: // partly cloud w/ showers
		case 17: // partly cloudy w/ t-storms
		case 21: // partly sunny w/ flurries
		case 39: // partly cloudy w/ showers
		case 41: // partly cloudy w/ t-storms
			return EnumWeather.PARTLY_CLOUDY;
		case 5: // hazy sunshines
		case 6: // mostly cloudy
		case 7: // cloudy
		case 8: // dreary (overcast)
		case 37: // hazy moonlight
		case 38: // mostly cloudy
			if (Wind.getSpeed().getValue() > WINDY_SPEED_LIMIT
					|| WindGust.getSpeed().getValue() > GUSTY_SPEED_LIMIT)
				return EnumWeather.WINDY;
			return EnumWeather.CLOUDY;
		case 13: // mostly cloudy w/ showers
		case 16: // mostly cloudy w/ t-storms
		case 20: // mostly cloudy w/ flurries
		case 23: // mostly cloudy w/ snow
		case 40: // mostly cloudy w/ showers
		case 42: // mostly cloudy w/ t-storms
		case 43: // mostly cloudy w/ flurries
		case 44: // mostly cloudy w/ snow
			return EnumWeather.CLOUDY;
		case 11: // fog
			return EnumWeather.FOG;
		case 12: // showers
		case 15: // t-storms
		case 18: // rain
		case 25: // sleet
		case 26: // freezing rain
			return EnumWeather.RAIN;
		case 19: // flurries
		case 22: // snow
		case 24: // ice
		case 29: // rain and snow
		case 31: // snow (uncertain)
			return EnumWeather.SNOW;
		case 32: // windy
			return EnumWeather.WINDY;
		}
		return null;
	}

	public String getDateTime() {
		return DateTime;
	}

	public long getEpochDateTime() {
		return EpochDateTime;
	}

	public int getWeatherIcon() {
		return WeatherIcon;
	}

	public String getIconPhrase() {
		return IconPhrase;
	}

	public boolean isHasPrecipitation() {
		return HasPrecipitation;
	}

	public boolean isIsDaylight() {
		return IsDaylight;
	}

	public WeatherUnit getTemperature() {
		return Temperature;
	}

	public WeatherUnit getRealFeelTemperature() {
		return RealFeelTemperature;
	}

	public WeatherUnit getWebBulbTemperature() {
		return WebBulbTemperature;
	}

	public WeatherUnit getDewPoint() {
		return DewPoint;
	}

	public WeatherWind getWind() {
		return Wind;
	}

	public WindGust getWindGust() {
		return WindGust;
	}

	public int getRelativeHumidity() {
		return RelativeHumidity;
	}

	public WeatherUnit getVisibility() {
		return Visibility;
	}

	public WeatherUnit getCeiling() {
		return Ceiling;
	}

	public int getUVIndex() {
		return UVIndex;
	}

	public String getUVIndexText() {
		return UVIndexText;
	}

	public int getPrecipitationProbability() {
		return PrecipitationProbability;
	}

	public int getRainProbability() {
		return RainProbability;
	}

	public int getSnowProbability() {
		return SnowProbability;
	}

	public int getIceProbability() {
		return IceProbability;
	}

	public WeatherUnit getTotalLiquid() {
		return TotalLiquid;
	}

	public WeatherUnit getRain() {
		return Rain;
	}

	public WeatherUnit getSnow() {
		return Snow;
	}

	public WeatherUnit getIce() {
		return Ice;
	}

	public int getCloudCover() {
		return CloudCover;
	}

	public String getMobileLink() {
		return MobileLink;
	}

	public String getLink() {
		return Link;
	}

}