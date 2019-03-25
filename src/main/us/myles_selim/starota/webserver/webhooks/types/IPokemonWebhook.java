package us.myles_selim.starota.webserver.webhooks.types;

import us.myles_selim.starota.enums.EnumPokemon;
import us.myles_selim.starota.pokedex.PokedexEntry.DexMove;
import us.myles_selim.starota.pokedex.PokedexEntry.DexMoveset;
import us.myles_selim.starota.trading.forms.FormSet;

public interface IPokemonWebhook {

	public EnumPokemon getPokemon();

	public int getFormId();

	public default FormSet.Form getForm() {
		EnumPokemon pokemon = getPokemon();
		if (pokemon.getFormSet() == null)
			return null;
		return pokemon.getFormSet().getForm(getFormId());
	};

	public DexMove getFastMove();

	public DexMove getChargedMove();

	public default DexMoveset getMoveset() {
		DexMoveset moves = new DexMoveset();
		moves.quickMove = getFastMove();
		moves.chargeMove = getChargedMove();
		return moves;
	}

}
