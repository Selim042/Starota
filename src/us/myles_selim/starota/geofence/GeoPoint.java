package us.myles_selim.starota.geofence;

public class GeoPoint {

	private double latitude;
	private double longitude;

	public GeoPoint(double lat, double lon) {
		this.latitude = lat;
		this.longitude = lon;
	}

	public double getLatitude() {
		return this.latitude;
	}

	public double getLongitude() {
		return this.longitude;
	}

}