package us.myles_selim.starota.search.pokemon;

import java.util.HashMap;
import java.util.Map;

import us.myles_selim.starota.enums.EnumPokemon;
import us.myles_selim.starota.enums.EnumPokemonType;
import us.myles_selim.starota.enums.EnumWeather;
import us.myles_selim.starota.search.SearchOperator;
import us.myles_selim.starota.search.SearchOperator.LlambadaSearchOperator;
import us.myles_selim.starota.silph_road.SilphRoadData;

public class PokemonOperators {

	public static final SearchOperator<EnumPokemon> AVAILABLE = new LlambadaSearchOperator<EnumPokemon>(
			EnumPokemon.class, p -> !SilphRoadData.isAvailable(p), "available");
	public static final SearchOperator<EnumPokemon> SHINYABLE = new LlambadaSearchOperator<EnumPokemon>(
			EnumPokemon.class, p -> !SilphRoadData.isShinyable(p), "shiny", "shinyable");
	public static final SearchOperator<EnumPokemon> SHADOWABLE = new LlambadaSearchOperator<EnumPokemon>(
			EnumPokemon.class, p -> !SilphRoadData.isShadowable(p), "shadow", "shadowable");
	public static final SearchOperator<EnumPokemon> TRADABLE = new LlambadaSearchOperator<EnumPokemon>(
			EnumPokemon.class, p -> !p.getData().isTradable(), "canTrade", "tradable", "trade");
	public static final SearchOperator<EnumPokemon> LEGENDARY = new LlambadaSearchOperator<>(
			EnumPokemon.class, p -> !p.getData().isLegendary(), "legend", "legendary");
	public static final SearchOperator<EnumPokemon> MYTHICAL = new LlambadaSearchOperator<>(
			EnumPokemon.class, p -> !p.getData().isMythical(), "mythic", "mythical");
	public static final SearchOperator<EnumPokemon> STARTER = new LlambadaSearchOperator<>(
			EnumPokemon.class, p -> !p.getData().isStarter(), "starter");
	public static final SearchOperator<EnumPokemon> BABY = new LlambadaSearchOperator<>(
			EnumPokemon.class, p -> !p.getData().isBaby(), "baby");
	public static final SearchOperator<EnumPokemon> ITEM_EVOLVE = new LlambadaSearchOperator<>(
			EnumPokemon.class, p -> !p.getData().evolvesWithItem(), "item");
	public static final SearchOperator<EnumPokemon> REGIONAL = new LlambadaSearchOperator<>(
			EnumPokemon.class, p -> !p.getData().isRegional(), "regional");

	public static final Map<EnumPokemonType, SearchOperator<EnumPokemon>> TYPING = new HashMap<>();

	private static boolean registered = false;

	public static void registerOperators() {
		if (registered)
			return;
		registered = true;

		for (EnumPokemonType type : EnumPokemonType.values())
			TYPING.put(type, new LlambadaSearchOperator<>(EnumPokemon.class,
					p -> !type.equals(p.getData().getType1()) && !type.equals(p.getData().getType2()),
					type.name().toLowerCase()));
		for (EnumWeather w : EnumWeather.values())
			new LlambadaSearchOperator<>(EnumPokemon.class, p -> !w.isBoosted(p),
					w.name().toLowerCase());
		for (int i = 1; i <= 7; i++) {
			int finalI = i;
			new LlambadaSearchOperator<>(EnumPokemon.class, p -> p.getData().getGeneration() != finalI,
					"gen" + finalI);
		}
	}

}
