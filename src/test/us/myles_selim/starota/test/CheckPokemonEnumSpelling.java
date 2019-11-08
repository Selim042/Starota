package us.myles_selim.starota.test;

import us.myles_selim.starota.enums.EnumPokemon;
import us.myles_selim.starota.pokedex.GoHubDatabase;
import us.myles_selim.starota.pokedex.PokedexEntry;

public class CheckPokemonEnumSpelling {

	public static void main(String... args) {
		int prevGen = 0;
		for (EnumPokemon pokemon : EnumPokemon.values()) {
			if (prevGen != pokemon.getData().getGeneration())
				System.out.println("testing gen" + ++prevGen);
			if (pokemon.getData().getId() % 50 == 0)
				System.out.println(pokemon.getData().getId() + "/" + EnumPokemon.values().length);
			PokedexEntry entry = GoHubDatabase.getEntry(pokemon);
			if (!entry.name.equals(pokemon.getData().getName()))
				System.out.println("bad Pokemon found: " + pokemon);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) { /* */ }
		}
		System.out.println("done");
	}

}
