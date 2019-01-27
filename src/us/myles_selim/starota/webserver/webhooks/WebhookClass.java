package us.myles_selim.starota.webserver.webhooks;

public class WebhookClass<D extends WebhookData> {

	public String type;
	public D message;

}
