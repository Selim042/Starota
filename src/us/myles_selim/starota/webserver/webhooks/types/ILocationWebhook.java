package us.myles_selim.starota.webserver.webhooks.types;

import us.myles_selim.starota.geofence.GeoPoint;

public interface ILocationWebhook {

	public double getLatitude();

	public double getLongitude();

	public default GeoPoint getPoint() {
		return new GeoPoint(getLatitude(), getLongitude());
	}

}
