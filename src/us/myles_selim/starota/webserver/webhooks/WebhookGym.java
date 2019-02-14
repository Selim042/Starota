package us.myles_selim.starota.webserver.webhooks;

import us.myles_selim.starota.enums.EnumTeam;
import us.myles_selim.starota.webserver.webhooks.types.IGymWebhook;

public class WebhookGym extends WebhookData implements IGymWebhook {

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

	@Override
	public String getGymId() {
		return this.gym_id;
	}

	@Override
	public String getGymName() {
		return this.gym_name;
	}

	@Override
	public int getTeamId() {
		return this.team_id;
	}

	// TODO: nag Shawn
	@Override
	public EnumTeam getTeam() {
		return EnumTeam.NO_TEAM;
	}

	@Override
	public boolean isSponsor() {
		return this.sponsor_id;
	}

	@Override
	public double getLatitude() {
		return this.latitude;
	}

	@Override
	public double getLongitude() {
		return this.longitude;
	}

}
