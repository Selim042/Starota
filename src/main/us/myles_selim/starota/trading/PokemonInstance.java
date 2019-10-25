package us.myles_selim.starota.trading;

import us.myles_selim.starota.enums.EnumGender;
import us.myles_selim.starota.enums.EnumPokemon;
import us.myles_selim.starota.forms.Form;
import us.myles_selim.starota.forms.FormSet;

public class PokemonInstance {

	private final EnumPokemon pokemon;
	private final Form form;
	private final boolean shiny;
	private final EnumGender gender;
	private final boolean legacy;

	public PokemonInstance(EnumPokemon pokemon, Form form, boolean shiny, EnumGender gender,
			boolean legacy) {
		this.pokemon = pokemon;
		this.form = form;
		this.shiny = shiny;
		this.gender = gender;
		this.legacy = legacy;
	}

	public EnumPokemon getPokemon() {
		return this.pokemon;
	}

	public Form getForm() {
		if (this.pokemon == null)
			return null;
		if (this.form == null)
			return this.pokemon.getData().getDefaultForm();
		return this.form;
	}

	public boolean getShiny() {
		return this.shiny;
	}

	public EnumGender getGender() {
		return this.gender;
	}

	public boolean isLegacy() {
		return this.legacy;
	}

	public static PokemonInstance getInstance(String msg) {
		return getInstance(msg.split(" "));
	}

	public static PokemonInstance getInstance(String[] args) {
		EnumPokemon pokemon = null;
		Form form = null;
		boolean shiny = false;
		EnumGender gender;
		boolean legacy = false;
		pokemon = EnumPokemon.getPokemon(args[1]);
		if (pokemon == null)
			return new PokemonInstance(pokemon, form, shiny, EnumGender.EITHER, legacy);
		gender = pokemon.getData().getGenderPossible();

		for (int i = 2; i < args.length; i++) {
			String arg = args[i];
			if (gender == EnumGender.EITHER) {
				switch (arg.toLowerCase()) {
				case "male":
				case "m":
				case "♂":
					gender = EnumGender.MALE;
					break;
				case "female":
				case "f":
				case "♀":
					gender = EnumGender.FEMALE;
					break;
				}
				if (gender != EnumGender.EITHER)
					continue;
			}
			if (!shiny) {
				switch (arg.toLowerCase()) {
				case "shiny":
				case "s":
					shiny = true;
					break;
				}
				if (shiny)
					continue;
			}
			if (!legacy) {
				switch (arg.toLowerCase()) {
				case "legacy":
				case "l":
				case "old":
					legacy = true;
					break;
				}
				if (legacy)
					continue;
			}
			if (form == null) {
				FormSet forms = pokemon.getData().getFormSet();
				if (forms != null)
					form = forms.getForm(arg);
				if (form != null)
					continue;
			}
			return new ErrorPokemonInstance(arg);
		}

		return new PokemonInstance(pokemon, form, shiny, gender, legacy);
	}

	public static class ErrorPokemonInstance extends PokemonInstance {

		public String badArg;

		protected ErrorPokemonInstance(String arg) {
			super(null, null, false, null, false);
			this.badArg = arg;
		}

	}

}
