package us.myles_selim.starota.trading.forms;

import us.myles_selim.starota.enums.EnumPokemon;
import us.myles_selim.starota.enums.EnumPokemonType;

public class FormSetBurmyFamily extends FormSet {

	private static final FormString PLANT = new FormString(0, "Plant") {

		@Override
		public int getGoHubId(EnumPokemon pokemon) {
			if (pokemon.equals(EnumPokemon.BURMY))
				return -1;
			return super.getGoHubId(pokemon);
		}
	};
	public static final FormSetBurmyFamily FORM_SET = new FormSetBurmyFamily();

	private FormSetBurmyFamily() {
		this.addForm(PLANT);
		this.addForm(new FormString(1, "Sandy") {

			@Override
			public int getGoHubId(EnumPokemon pokemon) {
				if (pokemon.equals(EnumPokemon.BURMY))
					return -1;
				return super.getGoHubId(pokemon);
			}

			@Override
			public EnumPokemonType getType2(EnumPokemon pokemon) {
				switch (pokemon) {
				case WORMADAM:
					return EnumPokemonType.GROUND;
				default:
					return super.getType2(pokemon);
				}
			}
		});
		this.addForm(new FormString(2, "Trash") {

			@Override
			public int getGoHubId(EnumPokemon pokemon) {
				if (pokemon.equals(EnumPokemon.BURMY))
					return -1;
				return super.getGoHubId(pokemon);
			}

			@Override
			public EnumPokemonType getType2(EnumPokemon pokemon) {
				switch (pokemon) {
				case WORMADAM:
					return EnumPokemonType.STEEL;
				default:
					return super.getType2(pokemon);
				}
			}
		});
	}

	@Override
	public Form getDefaultForm() {
		return PLANT;
	}

}
