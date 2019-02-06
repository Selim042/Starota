package us.myles_selim.starota.webserver.webhooks.types;

import us.myles_selim.starota.enums.EnumPokemon;
import us.myles_selim.starota.pokedex.PokedexEntry.DexMove;
import us.myles_selim.starota.pokedex.PokedexEntry.DexMoveset;

public interface IPokemonWebhook {

	public EnumPokemon getPokemon();

	public int getForm();

	public DexMove getFastMove();

	public DexMove getChargedMove();

	public default DexMoveset getMoveset() {
		DexMoveset moves = new DexMoveset();
		moves.quickMove = getFastMove();
		moves.chargeMove = getChargedMove();
		return moves;
	}

}
