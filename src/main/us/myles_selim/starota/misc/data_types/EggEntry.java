package us.myles_selim.starota.misc.data_types;

import us.myles_selim.starota.enums.EnumPokemon;
import us.myles_selim.starota.forms.Form;

public class EggEntry {

	private final EnumPokemon pokemon;
	private final Form form;
	private final int distance;
	private final boolean shinyable;

	// private final int totalHatches;
	// private final int numHatched;

	public EggEntry(EnumPokemon pokemon, Form form, int distance,
			boolean shinyable/* , int totalHatches, int numHatched */) {
		this.pokemon = pokemon;
		this.form = form;
		this.distance = distance;
		// this.maxCP = maxCP;
		// this.minCP = minCP;
		this.shinyable = shinyable;

		// this.totalHatches = totalHatches;
		// this.numHatched = numHatched;
	}

	public EnumPokemon getPokemon() {
		return pokemon;
	}

	public Form getForm() {
		return form;
	}

	public int getDistance() {
		return distance;
	}

	public boolean isShinyable() {
		return shinyable;
	}

	// public int getTotalHatches() {
	// return totalHatches;
	// }
	//
	// public int getNumHatched() {
	// return numHatched;
	// }

}
