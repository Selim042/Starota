package us.myles_selim.starota.webserver.webhooks;

import us.myles_selim.starota.enums.EnumPokemon;
import us.myles_selim.starota.enums.EnumTeam;

public class WebhookRaid extends WebhookData {

	public static final String TYPE = "raid";
	public static final EnumWebhookType TYPE_ENUM = EnumWebhookType.RAID;

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
	public boolean sponsor_id;

	public boolean is_exclusive;

	public EnumPokemon getPokemon() {
		return EnumPokemon.getPokemon(pokemon_id);
	}

	public EnumTeam getTeam() {
		switch (team_id) {
		case 1:
			return EnumTeam.INSTINCT;
		case 2:
			return EnumTeam.MYSTIC;
		case 3:
			return EnumTeam.VALOR;
		default:
			return EnumTeam.NO_TEAM;
		}
	}

	public long getTimeRemainingDespawn() {
		return this.end - System.currentTimeMillis();
	}

	public long getTimeRemainingHatch() {
		return this.start - System.currentTimeMillis();
	}

}
