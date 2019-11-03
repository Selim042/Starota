package us.myles_selim.starota.pvp.pokebattler;

public class PokemonConditionCPLimit extends PokemonCondition {

	private PokemonConditionCPLimit() {}

	private Data withPokemonCpLimit;

	public Data getData() {
		return withPokemonCpLimit;
	}

	public class Data {

		private int maxCp;

		public int getMaxCp() {
			return maxCp;
		}

	}

}
