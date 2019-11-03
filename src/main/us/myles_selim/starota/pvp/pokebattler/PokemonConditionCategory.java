package us.myles_selim.starota.pvp.pokebattler;

public class PokemonConditionCategory extends PokemonCondition {

	private PokemonConditionCategory() {}

	private Data withPokemonCategory;

	public Data getData() {
		return withPokemonCategory;
	}

	public class Data {

		private String categoryName;
		private String[] pokemonIds;

		public String getCategoryName() {
			return categoryName;
		}

		public String[] getPokemonIds() {
			return pokemonIds;
		}

	}

}
