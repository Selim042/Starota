package us.myles_selim.starota.pokedex;

import us.myles_selim.starota.pokedex.PokedexEntry.DexMove;

public class Counter {

	public int pokemonId;
	public String name;
	public String formName;
	public DexMove quickMove;
	public DexMove chargedMove;
	public int deaths;
	public float ttw;
	public float score;

	@Override
	public String toString() {
		return String.format("**%s%s**: %s/%s\n", formName == null ? "" : formName + " ", name,
				quickMove, chargedMove);
	}

}
