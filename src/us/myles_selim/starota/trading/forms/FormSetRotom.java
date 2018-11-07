package us.myles_selim.starota.trading.forms;

import us.myles_selim.starota.trading.EnumPokemon;
import us.myles_selim.starota.trading.EnumPokemonType;

public class FormSetRotom extends FormSet {

	private static final FormString NORMAL = new FormString("Normal");
	public static final FormSetRotom FORM_SET = new FormSetRotom();

	private FormSetRotom() {
		this.addForm(NORMAL);
		this.addForm(new FormString("Heat") {

			@Override
			public EnumPokemonType getType2(EnumPokemon pokemon) {
				return EnumPokemonType.FIRE;
			}

			@Override
			public String getSpritePostfix(EnumPokemon pokemon) {
				return "heat";
			}
		});
		this.addForm(new FormString("Wash") {

			@Override
			public EnumPokemonType getType2(EnumPokemon pokemon) {
				return EnumPokemonType.WATER;
			}

			@Override
			public String getSpritePostfix(EnumPokemon pokemon) {
				return "wash";
			}
		});
		this.addForm(new FormString("Frost") {

			@Override
			public EnumPokemonType getType2(EnumPokemon pokemon) {
				return EnumPokemonType.ICE;
			}

			@Override
			public String getSpritePostfix(EnumPokemon pokemon) {
				return "frost";
			}
		});
		this.addForm(new FormString("Fan") {

			@Override
			public EnumPokemonType getType2(EnumPokemon pokemon) {
				return EnumPokemonType.FLYING;
			}

			@Override
			public String getSpritePostfix(EnumPokemon pokemon) {
				return "fan";
			}
		});
		this.addForm(new FormString("Mow") {

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
