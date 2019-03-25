package us.myles_selim.starota.trading.forms;

import us.myles_selim.starota.enums.EnumPokemon;

public class FormSetDeoxys extends FormSet {

	private static final FormString NORMAL = new FormString(0, "Normal");
	public static final FormSetDeoxys FORM_SET = new FormSetDeoxys();

	private FormSetDeoxys() {
		this.addForm(NORMAL);
		this.addForm(new FormString(1, "Attack") {

			@Override
			public String getSpritePostfix(EnumPokemon pokemon) {
				return "attack";
			}
		});
		this.addForm(new FormString(2, "Defense") {

			@Override
			public String getSpritePostfix(EnumPokemon pokemon) {
				return "defense";
			}
		});
		this.addForm(new FormString(3, "Speed") {

			@Override
			public String getSpritePostfix(EnumPokemon pokemon) {
				return "speed";
			}
		});
	}

	@Override
	public Form getDefaultForm() {
		return NORMAL;
	}

}
