package us.myles_selim.starota;

import us.myles_selim.starota.enums.EnumPokemon;
import us.myles_selim.starota.trading.forms.FormSet.Form;

public class RaidBoss {

	private final EnumPokemon pokemon;
	private final Form form;
	private final int tier;

	public RaidBoss(EnumPokemon pokemon, Form form, int tier) {
		this.pokemon = pokemon;
		this.form = form;
		this.tier = tier;
	}

	public EnumPokemon getPokemon() {
		return this.pokemon;
	}

	public Form getForm() {
		return this.form;
	}

	public int getTier() {
		return this.tier;
	}

	public final int getColor() {
		return getColor(tier, pokemon);
	}

	public static int getColor(int tier, EnumPokemon pokemon) {
		switch (tier) {
		case 6:
		case 5:
			return 0xa9a2de;
		case 4:
		case 3:
			return 0xfbee74;
		case 2:
		case 1:
			return 0xf79eee;
		default:
			if (pokemon == null)
				return 0x000000;
			return pokemon.getType1().getColor();
		}
	}

}