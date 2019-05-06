package us.myles_selim.starota.webserver.webhooks;

import java.util.function.Consumer;

import discord4j.core.spec.EmbedCreateSpec;

public abstract class WebhookData {

	public abstract Consumer<EmbedCreateSpec> toEmbed();

}
