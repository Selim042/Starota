package us.myles_selim.starota.webserver.webhooks.types;

public interface ISpecificPokemonWebhook extends IPokemonWebhook {

	public boolean isWeatherBoosted();

	public int getAttackIV();

	public int getDefenseIV();

	public int getStaminaIV();

	public double getWeight();

	public double getHeight();

}
