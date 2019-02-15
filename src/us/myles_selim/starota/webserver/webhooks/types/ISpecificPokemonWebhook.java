package us.myles_selim.starota.webserver.webhooks.types;

import us.myles_selim.starota.enums.EnumWeightHeightModifier;
import us.myles_selim.starota.pokedex.GoHubDatabase;

public interface ISpecificPokemonWebhook extends IPokemonWebhook {

	public boolean isWeatherBoosted();

	public int getCP();

	public default boolean hasIVs() {
		return getAttackIV() != 0 || getDefenseIV() != 0 || getStaminaIV() != 0;
	}

	public int getAttackIV();

	public int getDefenseIV();

	public int getStaminaIV();

	public default float getIVPercent() {
		int sum = getAttackIV() + getDefenseIV() + getStaminaIV();
		if (sum == 0)
			return -1f;
		return sum / 45.0f;
	}

	public double getWeight();

	public double getHeight();

	public default EnumWeightHeightModifier getWeightModifier() {
		return getModifier(this.getWeight(), GoHubDatabase.getEntry(this.getPokemon()).weight);
	}

	public default EnumWeightHeightModifier getHeightModifier() {
		return getModifier(this.getHeight(), GoHubDatabase.getEntry(this.getPokemon()).height);
	}

	default EnumWeightHeightModifier getModifier(double invVal, double dexVal) {
		if (dexVal == 0)
			return EnumWeightHeightModifier.AVERAGE;
		double mod = invVal / dexVal;
		System.out.println(mod);
		if (mod > 1.25)
			return EnumWeightHeightModifier.EXTRA_LARGE;
		if (mod < 0.75)
			return EnumWeightHeightModifier.EXTRA_SMALL;
		return EnumWeightHeightModifier.AVERAGE;
	}

}
