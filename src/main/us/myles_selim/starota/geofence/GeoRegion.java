package us.myles_selim.starota.geofence;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import sx.blah.discord.handle.obj.IChannel;
import us.myles_selim.ebs.DataType;
import us.myles_selim.ebs.Storage;
import us.myles_selim.starota.webserver.webhooks.EnumWebhookType;

// used http://jsfiddle.net/geocodezip/qe68usv3/1/ to get polygon vertices
public class GeoRegion {

	private GeoPoint[] verts;
	private Map<EnumWebhookType, Long> hookChannels = new ConcurrentHashMap<>();

	public GeoRegion() {}

	public GeoRegion(GeoPoint... vertices) {
		this.verts = vertices;
	}

	public long getHookChannel(EnumWebhookType type) {
		if (!hookChannels.containsKey(type))
			return -1;
		return hookChannels.get(type);
	}

	public GeoRegion setHookChannel(EnumWebhookType type, IChannel channel) {
		return setHookChannel(type, channel.getLongID());
	}

	public GeoRegion setHookChannel(EnumWebhookType type, long channel) {
		hookChannels.put(type, channel);
		return this;
	}

	public GeoRegion clearHookChannel(EnumWebhookType type) {
		hookChannels.remove(type);
		return this;
	}

	public boolean coordinateInRegion(GeoPoint point) {
		int i, j = verts.length - 1;
		int sides = verts.length;
		boolean oddNodes = false;
		for (i = 0; i < sides; i++) {
			if ((verts[i].getLongitude() < point.getLongitude()
					&& verts[j].getLongitude() >= point.getLongitude()
					|| verts[j].getLongitude() < point.getLongitude()
							&& verts[i].getLongitude() >= point.getLongitude())
					&& (verts[i].getLatitude() <= point.getLatitude()
							|| verts[j].getLatitude() <= point.getLatitude())) {
				oddNodes ^= (verts[i].getLatitude() + (point.getLongitude() - verts[i].getLongitude())
						/ (verts[j].getLongitude() - verts[i].getLongitude())
						* (verts[j].getLatitude() - verts[i].getLatitude()) < point.getLatitude());
			}
			j = i;
		}
		return oddNodes;
	}

	public static class DataTypeGeoRegion extends DataType<GeoRegion> {

		private GeoRegion value;

		@Override
		public GeoRegion getValue() {
			return this.value;
		}

		@Override
		public void setValue(GeoRegion value) {
			this.value = value;
		}

		@Override
		protected void setValueObject(Object value) {
			if (this.acceptsValue(value))
				this.value = (GeoRegion) value;
		}

		@Override
		public Class<?>[] accepts() {
			return new Class[] { GeoRegion.class };
		}

		@Override
		public void toBytes(Storage stor) {
			stor.writeInt(value.verts.length);
			for (GeoPoint point : value.verts) {
				stor.writeDouble(point.getLatitude());
				stor.writeDouble(point.getLongitude());
			}
		}

		@Override
		public void fromBytes(Storage stor) {
			int length = stor.readInt();
			GeoPoint[] verts = new GeoPoint[length];
			// value.verts = new GeoPoint[length];
			for (int i = 0; i < length; i++)
				verts[i] = new GeoPoint(stor.readDouble(), stor.readDouble());
			this.value = new GeoRegion(verts);
		}

	}

}