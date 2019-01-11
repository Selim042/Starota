package us.myles_selim.starota.trading.forms;

import us.myles_selim.starota.enums.EnumPokemon;

public class FormSetDeoxys extends FormSet {

	private static final FormString NORMAL = new FormString("Normal");
	public static final FormSetDeoxys FORM_SET = new FormSetDeoxys();

	private FormSetDeoxys() {
		this.addForm(NORMAL);
		this.addForm(new FormString("Attack") {

			@Override
			public String getSpritePostfix(EnumPokemon pokemon) {
				return "attack";
			}
		});
		this.addForm(new FormString("Defense") {

			@Override
			public String getSpritePostfix(EnumPokemon pokemon) {
				return "defense";
			}
		});
		this.addForm(new FormString("Speed") {

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
