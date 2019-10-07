package us.myles_selim.starota.weather.api;

public class WeatherGeoPosition {

	protected WeatherGeoPosition() {}

	private String Latitude;
	private String Longitude;

	private WeatherElevation Elevation;

	
	public String getLatitude() {
		return Latitude;
	}

	
	public String getLongitude() {
		return Longitude;
	}

	
	public WeatherElevation getElevation() {
		return Elevation;
	}

	public static class WeatherElevation {

		protected WeatherElevation() {}

		private WeatherUnit Metric;
		private WeatherUnit Imperial;

		public WeatherUnit getMetric() {
			return Metric;
		}

		public WeatherUnit getImperial() {
			return Imperial;
		}

	}

}
