package us.myles_selim.starota.trading.forms;

import us.myles_selim.starota.enums.EnumPokemon;

public class FormSetGiratina extends FormSet {

	private static final FormString ALTERED = new FormString(0, "Altered") {

		@Override
		public String getSpritePostfix(EnumPokemon pokemon) {
			return "Altered";
		}
	};
	public static final FormSetGiratina FORM_SET = new FormSetGiratina();

	private FormSetGiratina() {
		this.addForm(ALTERED);
		this.addForm(new FormString(1, "Origin") {

			@Override
			public String getSpritePostfix(EnumPokemon pokemon) {
				return "Origin";
			}
		});
	}

	@Override
	public Form getDefaultForm() {
		return ALTERED;
	}

}
