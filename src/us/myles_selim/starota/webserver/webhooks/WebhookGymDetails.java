package us.myles_selim.starota.webserver.webhooks;

public class WebhookGymDetails extends WebhookData {

	public static final String TYPE = "gym_details";

	public String id;
	public String name;
	public String url;

	public double latitude;
	public double longitude;

	public int team;
	public int slots_available;
	public boolean sponsor_id;

}
