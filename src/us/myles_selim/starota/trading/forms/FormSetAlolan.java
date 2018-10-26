package us.myles_selim.starota.trading.forms;

import us.myles_selim.starota.trading.EnumPokemon;

public class FormSetAlolan extends FormSet {

	public static final FormAlolan ALOLAN = new FormAlolan();
	public static final FormKantonian KANTONIAN = new FormKantonian();
	public static final FormSetAlolan FORM_SET = new FormSetAlolan();

	private FormSetAlolan() {
		this.addForm(ALOLAN);
		this.addForm(KANTONIAN);
	}

	@Override
	public Form getDefaultForm() {
		return KANTONIAN;
	}

	private static class FormAlolan extends Form {

		@Override
		public boolean canBeShiny(EnumPokemon pokemon) {
			return false;
		}

		@Override
		public String toString() {
			return "Alolan";
		}

	}

	private static class FormKantonian extends Form {

		@Override
		public String toString() {
			return "Kantonian";
		}

	}

}
