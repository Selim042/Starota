package us.myles_selim.starota.webserver.webhooks.types;

import us.myles_selim.starota.enums.EnumTeam;

public interface IGymWebhook extends ILocationWebhook {

	public String getGymId();

	public String getGymName();

	public int getTeamId();

	// TODO: finish nagging Shawn for this
	public default EnumTeam getTeam() {
		return EnumTeam.NO_TEAM;
	}

	public boolean isSponsor();

}
