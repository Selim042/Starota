package us.myles_selim.starota.misc.data_types;

import us.myles_selim.starota.enums.EnumPokemon;
import us.myles_selim.starota.trading.forms.FormSet.Form;

public class RaidBoss {

	private final EnumPokemon pokemon;
	private final Form form;
	private final int tier;
	private final boolean shinyable;

	public RaidBoss(EnumPokemon pokemon, Form form, int tier) {
		this(pokemon, form, tier, false);
	}

	public RaidBoss(EnumPokemon pokemon, Form form, int tier, boolean shinyable) {
		this.pokemon = pokemon;
		this.form = form;
		this.tier = tier;
		this.shinyable = shinyable;
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

	public boolean isShinyable() {
		return this.shinyable;
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
