package us.myles_selim.starota.trading.forms;

import us.myles_selim.starota.trading.EnumPokemon;

public class FormSetGiratina extends FormSet {

	private static final FormString ALTERED = new FormString("Altered");
	public static final FormSetGiratina FORM_SET = new FormSetGiratina();

	private FormSetGiratina() {
		this.addForm(ALTERED);
		this.addForm(new FormString("Origin") {

			@Override
			public String getSpritePostfix(EnumPokemon pokemon) {
				return "origin";
			}
		});
	}

	@Override
	public Form getDefaultForm() {
		return ALTERED;
	}

}
