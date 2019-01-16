package us.myles_selim.starota.trading.forms;

import us.myles_selim.starota.enums.EnumPokemon;

public class FormSetPichuHat extends FormSet {

	protected static final FormString NO_HAT = new FormString(-1, "No Hat");
	public static final FormSetPichuHat FORM_SET = new FormSetPichuHat();

	protected FormSetPichuHat() {
		this.addForm(NO_HAT);
		this.addForm(new FormString(-1, "Santa Hat"));
		this.addForm(new FormString(-1, "Party Hat"));
		this.addForm(new FormString(-1, "Witch Hat"));
		this.addForm(new FormString(-1, "Summer Hat") {

			@Override
			public boolean canBeShiny(EnumPokemon pokemon) {
				return false;
			}
		});
		this.addForm(new FormString(-1, "Ash Hat") {

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
