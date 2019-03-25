package us.myles_selim.starota.webserver.webhooks;

import sx.blah.discord.api.internal.json.objects.EmbedObject;
import sx.blah.discord.util.EmbedBuilder;
import us.myles_selim.starota.webserver.webhooks.types.ILocationWebhook;

public class WebhookPokestop extends WebhookData implements ILocationWebhook {

	public static final String TYPE = "pokestop";
	public static final EnumWebhookType TYPE_ENUM = EnumWebhookType.POKESTOP;

	public String pokestop_id;
	public double latitude;
	public double longitude;
	public String name;
	public String url;

	public long lure_expiration;
	public long last_modified;

	public boolean enabled;
	public long updated;

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
