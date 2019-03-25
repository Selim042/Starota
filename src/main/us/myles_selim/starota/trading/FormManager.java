package us.myles_selim.starota.trading;

import us.myles_selim.starota.enums.EnumPokemon;

@Deprecated
public class FormManager {

	/**
	 * @deprecated Use {@link EnumPokemon#isShinyable(EnumPokemon)} directly
	 */
	@Deprecated
	public static boolean isAvailable(EnumPokemon pokemon) {
		return pokemon.isAvailable();
	}

	/**
	 * @deprecated Use {@link EnumPokemon#isShinyable(EnumPokemon)} directly
	 */
	@Deprecated
	public static boolean isAvailable(int id) {
		EnumPokemon pokemon = EnumPokemon.getPokemon(id);
		return pokemon != null && pokemon.isAvailable();
	}

	/**
	 * @deprecated Use {@link EnumPokemon#isShinyable()} directly
	 */
	@Deprecated
	public static boolean isShinyable(EnumPokemon pokemon) {
		return pokemon.isShinyable();
	}

	/**
	 * @deprecated Use {@link EnumPokemon#isShinyable()} directly
	 */
	@Deprecated
	public static boolean isShinyable(int id) {
		EnumPokemon pokemon = EnumPokemon.getPokemon(id);
		return pokemon != null && pokemon.isShinyable();
	}

}
