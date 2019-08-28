package us.myles_selim.starota.trading.forms;

import us.myles_selim.starota.enums.EnumPokemon;

public class FormSetCherrim extends FormSet {

	private static final FormString OVERCAST = new FormString(-1, "Overcast");
	public static final FormSetCherrim FORM_SET = new FormSetCherrim();

	private FormSetCherrim() {
		this.addForm(OVERCAST);
		this.addForm(new FormString(1, "Sunny") {

			@Override
			public String getSpritePostfix(EnumPokemon pokemon) {
				return "sunny";
			}
		});
	}

	@Override
	public Form getDefaultForm() {
		return OVERCAST;
	}

}
