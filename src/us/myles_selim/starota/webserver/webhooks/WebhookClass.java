package us.myles_selim.starota.webserver.webhooks;

public class WebhookClass<D extends WebhookData> {

	public EnumWebhookType type;
	public D message;
	public String secret;

}
