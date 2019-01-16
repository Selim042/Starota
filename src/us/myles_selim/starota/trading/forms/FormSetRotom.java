package us.myles_selim.starota.trading.forms;

import us.myles_selim.starota.enums.EnumPokemon;
import us.myles_selim.starota.enums.EnumPokemonType;

public class FormSetRotom extends FormSet {

	private static final FormString NORMAL = new FormString(0, "Normal");
	public static final FormSetRotom FORM_SET = new FormSetRotom();

	private FormSetRotom() {
		this.addForm(NORMAL);
		this.addForm(new FormString(3, "Heat") {

			@Override
			public EnumPokemonType getType2(EnumPokemon pokemon) {
				return EnumPokemonType.FIRE;
			}

			@Override
			public String getSpritePostfix(EnumPokemon pokemon) {
				return "heat";
			}
		});
		this.addForm(new FormString(5, "Wash") {

			@Override
			public EnumPokemonType getType2(EnumPokemon pokemon) {
				return EnumPokemonType.WATER;
			}

			@Override
			public String getSpritePostfix(EnumPokemon pokemon) {
				return "wash";
			}
		});
		this.addForm(new FormString(2, "Frost") {

			@Override
			public EnumPokemonType getType2(EnumPokemon pokemon) {
				return EnumPokemonType.ICE;
			}

			@Override
			public String getSpritePostfix(EnumPokemon pokemon) {
				return "frost";
			}
		});
		this.addForm(new FormString(1, "Fan") {

			@Override
			public EnumPokemonType getType2(EnumPokemon pokemon) {
				return EnumPokemonType.FLYING;
			}

			@Override
			public String getSpritePostfix(EnumPokemon pokemon) {
				return "fan";
			}
		});
		this.addForm(new FormString(4, "Mow") {

			@Override
			public EnumPokemonType getType2(EnumPokemon pokemon) {
				return EnumPokemonType.GRASS;
			}

			@Override
			public String getSpritePostfix(EnumPokemon pokemon) {
				return "mow";
			}
		});
	}

	@Override
	public Form getDefaultForm() {
		return NORMAL;
	}

}
