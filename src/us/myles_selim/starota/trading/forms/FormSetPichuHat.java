package us.myles_selim.starota.trading.forms;

import us.myles_selim.starota.trading.EnumPokemon;

public class FormSetPichuHat extends FormSet {

	protected static final FormString NO_HAT = new FormString("No Hat");
	public static final FormSetPichuHat FORM_SET = new FormSetPichuHat();

	protected FormSetPichuHat() {
		this.addForm(NO_HAT);
		this.addForm(new FormString("Santa Hat") {

			@Override
			public boolean canBeShiny(EnumPokemon pokemon) {
				return false;
			}
		});
		this.addForm(new FormString("Party Hat"));
		this.addForm(new FormString("Witch Hat"));
		this.addForm(new FormString("Summer Hat") {

			@Override
			public boolean canBeShiny(EnumPokemon pokemon) {
				return false;
			}
		});
		this.addForm(new FormString("Ash Hat") {

			@Override
			public boolean canBeShiny(EnumPokemon pokemon) {
				return false;
			}
		});
	}

	@Override
	public Form getDefaultForm() {
		return NO_HAT;
	}

}
