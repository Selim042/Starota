package us.myles_selim.starota.webserver.webhooks;

public class WebhookPokestop extends WebhookData {

	public static final String TYPE = "pokestop";

	public String pokestop_id;
	public double latitude;
	public double longitute;
	public String name;
	public String url;

	public long lure_expiration;
	public long last_modified;

	public boolean enabled;
	public long updated;

}
