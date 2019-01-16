package us.myles_selim.starota.trading.forms;

import us.myles_selim.starota.enums.EnumPokemon;
import us.myles_selim.starota.enums.EnumPokemonType;

public class FormSetShaymin extends FormSet {

	private static final FormString LAND = new FormString(0, "Land");
	public static final FormSetShaymin FORM_SET = new FormSetShaymin();

	private FormSetShaymin() {
		this.addForm(LAND);
		this.addForm(new FormString(1, "Sky") {

			@Override
			public EnumPokemonType getType2(EnumPokemon pokemon) {
				return EnumPokemonType.FLYING;
			}

			@Override
			public String getSpritePostfix(EnumPokemon pokemon) {
				return "sky";
			}
		});
	}

	@Override
	public Form getDefaultForm() {
		return LAND;
	}

}
