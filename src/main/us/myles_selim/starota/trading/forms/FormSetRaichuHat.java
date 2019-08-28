package us.myles_selim.starota.trading.forms;

import us.myles_selim.starota.enums.EnumPokemon;

public class FormSetRaichuHat extends FormSetPichuHat {

	public static final FormSetRaichuHat FORM_SET = new FormSetRaichuHat();

	private FormSetRaichuHat() {
		this.addForm(new FormString(-1, "H.F. Hat"));
		this.addForm(new FormString(-1, "Flower"));
		this.addForm(new FormString(-1, "Detective"));
		this.addForm(new FormString(1, "Alolan") {

			@Override
			public boolean canBeShiny(EnumPokemon pokemon) {
				return false;
			}

			@Override
			public String getSpritePostfix(EnumPokemon pokemon) {
				return "Alola";
			}
		});
	}

	@Override
	public Form getDefaultForm() {
		return NO_HAT;
	}

}
