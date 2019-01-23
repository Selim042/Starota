package us.myles_selim.starota.webserver.webhooks;

public class WebhookRaid extends WebhookData {

	public static final String TYPE = "raid";

	public String gym_id;
	public String gym_name;
	public double latitude;
	public double longitude;

	public int team_id;
	public long spawn;
	public long start;
	public long end;
	
	public int level;
	public int pokemon_id;
	public int cp;
	public int form;
	
	public int move_1;
	public int move_2;
	public int sponsor_id;

	public boolean is_exclusive;

}
