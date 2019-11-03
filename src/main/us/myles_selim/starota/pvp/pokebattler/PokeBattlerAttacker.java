package us.myles_selim.starota.pvp.pokebattler;

public class PokeBattlerAttacker {

	private String pokemonId;
	private int cp;
	private String numSims;
	private PokeBattlerAttackerMoveset[] byMove;
	private PokeBattlerStats stats;

	public String getPokemonId() {
		return pokemonId;
	}

	public int getCp() {
		return cp;
	}

	public String getNumSims() {
		return numSims;
	}

	public PokeBattlerAttackerMoveset[] getMovesets() {
		return byMove;
	}

	public PokeBattlerAttackerMoveset[] getByMove() {
		return byMove;
	}

	public PokeBattlerStats getStats() {
		return stats;
	}

	public class PokeBattlerAttackerMoveset {

		private String move1;
		private String move2;
		private String move3;

		public String getMove1() {
			return move1;
		}

		public String getMove2() {
			return move2;
		}

		public String getMove3() {
			return move3;
		}

	}

}
