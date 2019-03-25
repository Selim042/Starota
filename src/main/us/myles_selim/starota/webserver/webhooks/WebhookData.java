package us.myles_selim.starota.webserver.webhooks;

import sx.blah.discord.api.internal.json.objects.EmbedObject;

public abstract class WebhookData {

	public abstract EmbedObject toEmbed();

}
