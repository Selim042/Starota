package us.myles_selim.starota.misc.data_types;

import java.awt.Color;

import us.myles_selim.starota.enums.EnumPokemon;
import us.myles_selim.starota.trading.forms.FormSet.Form;

public class RaidBoss {

	private static final Color TIER_1_2 = new Color(0xf79eee);
	private static final Color TIER_3_4 = new Color(0xfbee74);
	private static final Color TIER_5_6 = new Color(0xa9a2de);

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

	public final Color getColor() {
		return getColor(tier, pokemon);
	}

	public static Color getColor(int tier, EnumPokemon pokemon) {
		switch (tier) {
		case 6:
		case 5:
			return TIER_5_6;
		case 4:
		case 3:
			return TIER_3_4;
		case 2:
		case 1:
			return TIER_1_2;
		default:
			if (pokemon == null)
				return null;
			return pokemon.getType1().getColor();
		}
	}

}
