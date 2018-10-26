package us.myles_selim.starota.trading.forms;

import us.myles_selim.starota.trading.EnumPokemon;

public class FormSetPikachuHat extends FormSetPichuHat {

	protected static final FormString NO_HAT = new FormString("No Hat");
	public static final FormSetPikachuHat FORM_SET = new FormSetPikachuHat();

	protected FormSetPikachuHat() {
		this.addForm(new FormString("Ash Hat") {

			@Override
			public boolean canBeShiny(EnumPokemon pokemon) {
				return false;
			}
		});
		this.addForm(new FormString("H.F. Hat"));
	}

	@Override
	public Form getDefaultForm() {
		return NO_HAT;
	}

}
