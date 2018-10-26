package us.myles_selim.starota.trading.forms;

import us.myles_selim.starota.trading.EnumPokemonType;

public class FormSetArceus extends FormSet {

	private static final FormArceus NORMAL = new FormArceus(EnumPokemonType.NORMAL);
	public static final FormSetArceus FORM_SET = new FormSetArceus();

	private FormSetArceus() {
		for (EnumPokemonType t : EnumPokemonType.values())
			if (t == EnumPokemonType.NORMAL)
				this.addForm(NORMAL);
			else
				this.addForm(new FormArceus(t));
	}

	@Override
	public Form getDefaultForm() {
		return NORMAL;
	}

	private static class FormArceus extends Form {

		private final EnumPokemonType type;

		public FormArceus(EnumPokemonType type) {
			this.type = type;
		}

		@Override
		public String toString() {
			return this.type.toString();
		}

	}

}
