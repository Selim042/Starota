package us.myles_selim.starota.webserver.webhooks.types;

import us.myles_selim.starota.enums.EnumTeam;

public interface IGymWebhook extends ILocationWebhook {

	public String getGymId();

	public String getGymName();

	public int getTeamId();

	public EnumTeam getTeam();

	public boolean isSponsor();

}
