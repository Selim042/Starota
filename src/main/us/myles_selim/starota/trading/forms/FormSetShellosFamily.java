package us.myles_selim.starota.trading.forms;

import us.myles_selim.starota.enums.EnumPokemon;

public class FormSetShellosFamily extends FormSet {

	private static final FormString WEST = new FormString(1, "West Sea");
	public static final FormSetShellosFamily FORM_SET = new FormSetShellosFamily();

	private FormSetShellosFamily() {
		this.addForm(WEST);
		this.addForm(new FormString(0, "East Sea") {

			@Override
			public String getSpritePostfix(EnumPokemon pokemon) {
				return "east";
			}
		});
	}

	@Override
	public Form getDefaultForm() {
		return WEST;
	}

}
