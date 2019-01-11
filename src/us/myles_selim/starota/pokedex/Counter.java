package us.myles_selim.starota.pokedex;

import us.myles_selim.starota.pokedex.PokedexEntry.Move;

public class Counter {

	public int pokemonId;
	public String name;
	public String formName;
	public Move quickMove;
	public Move chargedMove;
	public int deaths;
	public float ttw;
	public float score;

	@Override
	public String toString() {
		return String.format("**%s%s**: %s/%s\n", formName == null ? "" : formName + " ", name,
				quickMove, chargedMove);
	}

}
