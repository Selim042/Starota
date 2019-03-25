package us.myles_selim.starota.trading.forms;

import us.myles_selim.starota.enums.EnumPokemon;
import us.myles_selim.starota.enums.EnumPokemonType;

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
			super(-1);
			this.type = type;
		}

		@Override
		public int getGoHubId(EnumPokemon pokemon) {
			switch (type) {
			case BUG:
				return 0;
			case DARK:
				return 1;
			case DRAGON:
				return 2;
			case ELECTRIC:
				return 3;
			case FAIRY:
				return 4;
			case FIGHTING:
				return 5;
			case FIRE:
				return 6;
			case FLYING:
				return 7;
			case GHOST:
				return 8;
			case GRASS:
				return 9;
			case GROUND:
				return 10;
			case ICE:
				return 11;
			case NORMAL:
				return 12;
			case POISON:
				return 13;
			case PSYCHIC:
				return 14;
			case ROCK:
				return 15;
			case STEEL:
				return 16;
			case WATER:
				return 17;
			default:
				return -1;
			}
		}

		@Override
		public EnumPokemonType getType1(EnumPokemon pokemon) {
			return type;
		}

		@Override
		public String toString() {
			return this.type.toString();
		}

	}

}
