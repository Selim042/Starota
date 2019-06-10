package us.myles_selim.starota.misc.utils;

import us.myles_selim.starota.enums.EnumPokemon;

public interface IJournalEntry {

	public static class PokemonCatchEntry implements IJournalEntry {

		public final EnumPokemon pokemon;

		public PokemonCatchEntry(EnumPokemon pokemon) {
			this.pokemon = pokemon;
		}

		@Override
		public String toString() {
			return "pokemon-catch: " + pokemon;
		}
	}

	public static class PokemonHatchEntry implements IJournalEntry {

		public final EnumPokemon pokemon;

		public PokemonHatchEntry(EnumPokemon pokemon) {
			this.pokemon = pokemon;
		}

		@Override
		public String toString() {
			return "pokemon-hatch: " + pokemon;
		}
	}

	public static class RaidWinEntry implements IJournalEntry {

		@Override
		public String toString() {
			return "raid-win";
		}
	}

	public static class PokestopSpinEntry implements IJournalEntry {

		@Override
		public String toString() {
			return "pokestop-spin";
		}
	}

	public static class BattleWinEntry implements IJournalEntry {

		public final String opponent;

		public BattleWinEntry(String opponent) {
			this.opponent = opponent;
		}

		@Override
		public String toString() {
			return "battle-win: " + opponent;
		}
	}

	public static class BattleLoseEntry implements IJournalEntry {

		public final String opponent;

		public BattleLoseEntry(String opponent) {
			this.opponent = opponent;
		}

		@Override
		public String toString() {
			return "battle-lose: " + opponent;
		}
	}

	public static class GiftSendEntry implements IJournalEntry {

		public final String target;

		public GiftSendEntry(String target) {
			this.target = target;
		}

		@Override
		public String toString() {
			return "gift-send: " + target;
		}
	}

	public static class GiftOpenEntry implements IJournalEntry {

		public final String target;

		public GiftOpenEntry(String target) {
			this.target = target;
		}

		@Override
		public String toString() {
			return "gift-open: " + target;
		}
	}

	public static class CandyEntry implements IJournalEntry {

		@Override
		public String toString() {
			return "candy";
		}
	}

	public static class AdventureSyncEntry implements IJournalEntry {

		@Override
		public String toString() {
			return "adventure-sync";
		}
	}

	public static class PokemonTradeEntry implements IJournalEntry {

		public final EnumPokemon receivePokemon;
		public final EnumPokemon sendPokemon;

		public PokemonTradeEntry(EnumPokemon receivePokemon, EnumPokemon sendPokemon) {
			this.receivePokemon = receivePokemon;
			this.sendPokemon = sendPokemon;
		}

		@Override
		public String toString() {
			return "pokemon-trade: " + receivePokemon + ", " + sendPokemon;
		}
	}

	public static class PokemonRunEntry implements IJournalEntry {

		public final EnumPokemon pokemon;

		public PokemonRunEntry(EnumPokemon pokemon) {
			this.pokemon = pokemon;
		}

		@Override
		public String toString() {
			return "pokemon-run: " + pokemon;
		}
	}

}
