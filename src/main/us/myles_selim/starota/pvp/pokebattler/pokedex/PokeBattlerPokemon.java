package us.myles_selim.starota.pvp.pokebattler.pokedex;

import us.myles_selim.starota.pvp.pokebattler.PokebattlerStats;

public class PokeBattlerPokemon {

	private PokeBattlerPokemon() {}

	private PokeBattlerPokedex pokedex;

	private String pokemonId;
	private String type;
	private String type2;
	private PokebattlerStats stats;

	private String[] quickMoves;
	private String[] cinematicMoves;

	private double pokedexHeightM;
	private double pokedexWeightKg;

	private float heightStdDev;
	private float weightStdDev;

	private String familyId;

	private int candyToEvolve;
	private PokeBattlerMoveset[] movesets;
	private PokeBattlerMoveset[] tmMovesets;
	private PokeBattlerMoveset[] currentMovesets;

	public class PokeBattlerMoveset {

		private PokeBattlerMoveset() {}

		private String quickMove;
		private String cinematicMove;

		public String getQuickMove() {
			return quickMove;
		}

		public String getCinematicMove() {
			return cinematicMove;
		}

	}

	public class PokeBattlerPokedex {

		private PokeBattlerPokedex() {}

		private String pokemonId;
		private int pokemonNum;

		public String getPokemonId() {
			return pokemonId;
		}

		public int getPokemonNum() {
			return pokemonNum;
		}

	}

}
