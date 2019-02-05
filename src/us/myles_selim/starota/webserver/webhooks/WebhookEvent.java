package us.myles_selim.starota.webserver.webhooks;

import sx.blah.discord.handle.impl.events.guild.GuildEvent;
import sx.blah.discord.handle.obj.IGuild;

public class WebhookEvent extends GuildEvent {

	private WebhookClass<?> webhookClass;

	public WebhookEvent(IGuild guild, WebhookClass<?> webhookClass) {
		super(guild);
		this.webhookClass = webhookClass;
	}

	public WebhookClass<?> getWebhookClass() {
		return this.webhookClass;
	}

	public EnumWebhookType getType() {
		return this.webhookClass.type;
	}

}
