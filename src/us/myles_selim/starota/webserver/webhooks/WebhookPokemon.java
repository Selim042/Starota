package us.myles_selim.starota.webserver.webhooks;

public class WebhookPokemon extends WebhookData {

	public static final String TYPE = "pokemon";

	public String spawnpoint_id;
	public String pokestop_id;
	public String encounter_id;
	public int pokemon_id;

	public double latitude;
	public double longitude;

	public long disappear_time;
	public long first_seen;

	public boolean verified;
	public long last_modified_time;

	public int gender;
	public int cp;
	public int form;
	public int costume;

	public int individual_attack;
	public int individual_defense;
	public int individial_stamina;

	public int move_1;
	public int move_2;

	public double weight;
	public double height;

	public int weather;

}
