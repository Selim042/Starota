package us.myles_selim.starota.webserver.webhooks;

import sx.blah.discord.api.internal.json.objects.EmbedObject;
import sx.blah.discord.util.EmbedBuilder;
import us.myles_selim.starota.webserver.webhooks.types.IGymWebhook;

public class WebhookGymDetails extends WebhookData implements IGymWebhook {

	public static final String TYPE = "gym_details";
	public static final EnumWebhookType TYPE_ENUM = EnumWebhookType.GYM_DETAILS;

	public String id;
	public String name;
	public String url;

	public double latitude;
	public double longitude;

	public int team;
	public int slots_available;
	public boolean sponsor_id;

	@Override
	public String getGymId() {
		return this.id;
	}

	@Override
	public String getGymName() {
		return this.name;
	}

	@Override
	public int getTeamId() {
		return this.team;
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

	@Override
	public EmbedObject toEmbed() {
		return new EmbedBuilder().build();
	}

}
