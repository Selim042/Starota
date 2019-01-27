package us.myles_selim.starota.webserver.webhooks;

public class WebhookData {

	public static Class<? extends WebhookData> getDataClassForType(String type) {
		switch (type.toLowerCase()) {
		case WebhookGym.TYPE:
			return WebhookGym.class;
		case WebhookGymDetails.TYPE:
			return WebhookGymDetails.class;
		case WebhookPokemon.TYPE:
			return WebhookPokemon.class;
		case WebhookPokestop.TYPE:
			return WebhookPokestop.class;
		case WebhookRaid.TYPE:
			return WebhookRaid.class;
		default:
			throw new IllegalArgumentException("cannot find type for " + type);
		}
	}

}
