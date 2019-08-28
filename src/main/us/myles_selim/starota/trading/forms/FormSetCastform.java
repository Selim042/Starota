package us.myles_selim.starota.trading.forms;

import us.myles_selim.starota.enums.EnumPokemon;
import us.myles_selim.starota.enums.EnumPokemonType;

public class FormSetCastform extends FormSet {

	private static final FormString NORMAL = new FormString(-1, "Normal");
	public static final FormSetCastform FORM_SET = new FormSetCastform();

	private FormSetCastform() {
		this.addForm(NORMAL);
		this.addForm(new FormString(-1, "Sunny") {

			@Override
			public boolean canBeShiny(EnumPokemon pokemon) {
				return false;
			}

			@Override
			public EnumPokemonType getType1(EnumPokemon pokemon) {
				return EnumPokemonType.FIRE;
			}

			@Override
			public String getSpritePostfix(EnumPokemon pokemon) {
				return "sunny";
			}
		});
		this.addForm(new FormString(-1, "Rainy") {

			@Override
			public boolean canBeShiny(EnumPokemon pokemon) {
				return false;
			}

			@Override
			public EnumPokemonType getType1(EnumPokemon pokemon) {
				return EnumPokemonType.WATER;
			}

			@Override
			public String getSpritePostfix(EnumPokemon pokemon) {
				return "rainy";
			}
		});
		this.addForm(new FormString(-1, "Snowy") {

			@Override
			public boolean canBeShiny(EnumPokemon pokemon) {
				return false;
			}

			@Override
			public EnumPokemonType getType1(EnumPokemon pokemon) {
				return EnumPokemonType.ICE;
			}

			@Override
			public String getSpritePostfix(EnumPokemon pokemon) {
				return "snowy";
			}
		});
	}

	@Override
	public Form getDefaultForm() {
		return NORMAL;
	}

}
