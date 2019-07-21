package us.myles_selim.starota.trading.forms;

import us.myles_selim.starota.enums.EnumPokemon;

public class FormSetMewtwo extends FormSet {

	private static final FormString NORMAL = new FormString(0, "Normal");
	public static final FormSetMewtwo FORM_SET = new FormSetMewtwo();

	private FormSetMewtwo() {
		this.addForm(NORMAL);
		this.addForm(new FormString(4, "Armored") {

			@Override
			public String getSpritePostfix(EnumPokemon pokemon) {
				return "armored";
			}
		});
	}

	@Override
	public Form getDefaultForm() {
		return NORMAL;
	}

}
