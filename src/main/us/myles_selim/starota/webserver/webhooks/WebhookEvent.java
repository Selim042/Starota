package us.myles_selim.starota.webserver.webhooks;

import discord4j.core.event.domain.guild.GuildEvent;
import discord4j.core.object.entity.Guild;

// TODO: find solution for GuildEvent not having guild
public class WebhookEvent extends GuildEvent {

	private WebhookClass<?> webhookClass;

	public WebhookEvent(Guild guild, WebhookClass<?> webhookClass) {
		// super(guild);
		super(guild.getClient());
		this.webhookClass = webhookClass;
	}

	public WebhookClass<?> getWebhookClass() {
		return this.webhookClass;
	}

	public EnumWebhookType getType() {
		return this.webhookClass.type;
	}

	public WebhookData getWebhookData() {
		return this.webhookClass.message;
	}

}
