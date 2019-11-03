package us.myles_selim.starota.pvp.pokebattler;

public class PokemonConditionType extends PokemonCondition {

	private PokemonConditionType() {}

	private Data withPokemonType;

	public Data getData() {
		return withPokemonType;
	}

	public class Data {

		private String[] pokemonType;

		public String[] getPokemonType() {
			return pokemonType;
		}

	}

}
