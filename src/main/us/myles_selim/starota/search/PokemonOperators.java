package us.myles_selim.starota.search;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import us.myles_selim.starota.enums.EnumPokemon;
import us.myles_selim.starota.enums.EnumPokemonType;
import us.myles_selim.starota.enums.EnumWeather;
import us.myles_selim.starota.search.SearchOperator.LlambadaSearchOperator;

public class PokemonOperators {

	public static final SearchOperator<EnumPokemon> AVAILABLE = new LlambadaSearchOperator<EnumPokemon>(
			EnumPokemon.class, p -> !p.isAvailable(), "available");

	public static final SearchOperator<EnumPokemon> SHINYABLE = new LlambadaSearchOperator<EnumPokemon>(
			EnumPokemon.class, p -> !p.isShinyable(), "shiny", "shinyable");

	public static final SearchOperator<EnumPokemon> TRADABLE = new LlambadaSearchOperator<EnumPokemon>(
			EnumPokemon.class, p -> !p.isTradable(), "canTrade", "tradable", "trade");

	public static final SearchOperator<EnumPokemon> LEGENDARY = new LlambadaSearchOperator<>(
			EnumPokemon.class, p -> !p.isLegendary(), "legend", "legendary");

	public static final SearchOperator<EnumPokemon> MYTHICAL = new LlambadaSearchOperator<>(
			EnumPokemon.class, p -> !p.isMythical(), "mythic", "mythical");

	public static final SearchOperator<EnumPokemon> STARTER = new LlambadaSearchOperator<>(
			EnumPokemon.class, p -> !p.isStarter(), "starter");

	public static final SearchOperator<EnumPokemon> BABY = new LlambadaSearchOperator<>(
			EnumPokemon.class, p -> !p.isBaby(), "baby");

	public static final Map<EnumPokemonType, SearchOperator<EnumPokemon>> TYPING = new HashMap<>();

	private static boolean registered = false;

	public static void registerOperators() {
		if (registered)
			return;
		registered = true;

		for (EnumPokemonType type : EnumPokemonType.values())
			TYPING.put(type, new TypeSearchOperator(type));
		for (EnumWeather w : EnumWeather.values())
			new LlambadaSearchOperator<>(EnumPokemon.class, p -> !w.isBoosted(p), w.name());
		for (int i = 1; i <= 7; i++) {
			int finalI = i;
			new LlambadaSearchOperator<>(EnumPokemon.class, p -> p.getGeneration() != finalI,
					"gen" + finalI);
		}
	}

	private static final class TypeSearchOperator extends SearchOperator<EnumPokemon> {

		private final EnumPokemonType type;

		public TypeSearchOperator(EnumPokemonType type) {
			super(EnumPokemon.class);
			this.type = type;
		}

		@Override
		public void filter(Collection<EnumPokemon> vals, Collection<EnumPokemon> removed) {
			for (EnumPokemon p : vals)
				if (!this.type.equals(p.getType1()) && !this.type.equals(p.getType2()))
					removed.add(p);
		}

		@Override
		public String[] getSearchTerms() {
			return new String[] { type.name() };
		}

	}

}
