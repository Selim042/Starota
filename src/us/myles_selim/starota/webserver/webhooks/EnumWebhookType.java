package us.myles_selim.starota.webserver.webhooks;

public enum EnumWebhookType {
	GYM(WebhookGym.class),
	GYM_DETAILS(WebhookGymDetails.class),
	POKEMON(WebhookPokemon.class),
	POKESTOP(WebhookPokestop.class),
	RAID(WebhookRaid.class),
	TASK(WebhookTask.class);

	private Class<? extends WebhookData> dataClass;

	EnumWebhookType(Class<? extends WebhookData> dataClass) {
		this.dataClass = dataClass;
	}

	public Class<? extends WebhookData> getDataClass() {
		return dataClass;
	}

}
