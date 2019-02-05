package us.myles_selim.starota.webserver.webhooks;

public class WebhookGym extends WebhookData {

	public static final String TYPE = "gym";
	public static final EnumWebhookType TYPE_ENUM = EnumWebhookType.GYM;

	public String gym_id;
	public String gym_name;
	public double latitude;
	public double longitude;
	public String url;

	public boolean enabled;
	public int team_id;

	public long last_modified;
	public int guard_pokemon_id;
	public int slots_available;
	public long raid_active_until;
	public boolean sponsor_id;

}
