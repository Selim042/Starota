package us.myles_selim.starota.webserver.webhooks;

import us.myles_selim.starota.webserver.webhooks.types.ILocationWebhook;

public class WebhookQuest extends WebhookData implements ILocationWebhook {

	public static final String TYPE = "quest";

	public String pokestop_id;
	public double latitude;
	public double longitude;

	public String type;
	public String target;
	public String template;
	public String conditions;
	public String rewards;

	public long updated;

	public String pokestop_name;
	public String pokestop_url;

	@Override
	public double getLatitude() {
		return this.latitude;
	}

	@Override
	public double getLongitude() {
		return this.longitude;
	}

	public String data;

}
