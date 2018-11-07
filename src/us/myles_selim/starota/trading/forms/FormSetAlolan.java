package us.myles_selim.starota.trading.forms;

import us.myles_selim.starota.trading.EnumPokemon;
import us.myles_selim.starota.trading.EnumPokemonType;

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
			switch (pokemon) {
			case MAROWAK:
				return true;
			default:
				return false;
			}
		}

		@Override
		public EnumPokemonType getType1(EnumPokemon pokemon) {
			switch (pokemon) {
			case RATTATA:
			case RATICATE:
				return EnumPokemonType.DARK;
			case SANDSHREW:
			case SANDSLASH:
				return EnumPokemonType.ICE;
			case VULPIX:
			case NINETALES:
				return EnumPokemonType.ICE;
			case MEOWTH:
			case PERSIAN:
				return EnumPokemonType.DARK;
			case MAROWAK:
				return EnumPokemonType.FIRE;
			default:
				return super.getType1(pokemon);
			}
		}

		@Override
		public EnumPokemonType getType2(EnumPokemon pokemon) {
			switch (pokemon) {
			case RATTATA:
			case RATICATE:
				return EnumPokemonType.NORMAL;
			case RAICHU:
				return EnumPokemonType.PSYCHIC;
			case SANDSHREW:
			case SANDSLASH:
				return EnumPokemonType.STEEL;
			case NINETALES:
				return EnumPokemonType.FAIRY;
			case DIGLETT:
			case DUGTRIO:
				return EnumPokemonType.STEEL;
			case GEODUDE:
			case GRAVELER:
			case GOLEM:
				return EnumPokemonType.ELECTRIC;
			case GRIMER:
			case MUK:
				return EnumPokemonType.DARK;
			case EXEGGUTOR:
				return EnumPokemonType.DRAGON;
			case MAROWAK:
				return EnumPokemonType.GHOST;
			default:
				return super.getType1(pokemon);
			}
		}

		@Override
		public String getSpritePostfix(EnumPokemon pokemon) {
			return "alola";
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
