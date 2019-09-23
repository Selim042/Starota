package us.myles_selim.starota.weather.api;

public class WeatherWind {

	protected WeatherWind() {}

	private WeatherUnit Speed;
	private WindDirection Direction;

	public WeatherUnit getSpeed() {
		return Speed;
	}

	public WindDirection getDirection() {
		return Direction;
	}

	public static class WindDirection {

		protected WindDirection() {}

		private int Degrees;
		private String Localized;
		private String English;

		public int getDegrees() {
			return Degrees;
		}

		public String getLocalized() {
			return Localized;
		}

		public String getEnglish() {
			return English;
		}

	}

	public static class WindGust {

		protected WindGust() {}

		private WeatherUnit Speed;

		public WeatherUnit getSpeed() {
			return Speed;
		}

	}

}
