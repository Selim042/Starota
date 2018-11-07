package us.myles_selim.starota.trading.forms;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import us.myles_selim.starota.trading.EnumPokemon;
import us.myles_selim.starota.trading.EnumPokemonType;
import us.myles_selim.starota.trading.FormManager;

public abstract class FormSet {

	private final List<Form> forms = new LinkedList<>();

	protected Form addForm(Form form) {
		this.forms.add(form);
		return form;
	}

	public abstract Form getDefaultForm();

	public int getNumForms() {
		return this.forms.size();
	}

	public List<Form> getForms() {
		return Collections.unmodifiableList(this.forms);
	}

	public Form getForm(String form) {
		form = form.replaceAll("_", " ");
		for (Form f : forms)
			if (f.toString().equalsIgnoreCase(form))
				return f;
		return null;
	}

	public static abstract class Form {

		public boolean canBeShiny(EnumPokemon pokemon) {
			return FormManager.isShinyable(pokemon.getId());
		}

		public EnumPokemonType getType1(EnumPokemon pokemon) {
			return pokemon.getType1();
		}

		public EnumPokemonType getType2(EnumPokemon pokemon) {
			return pokemon.getType2();
		}

		public String getSpritePostfix(EnumPokemon pokemon) {
			return null;
		}

		@Override
		public abstract String toString();

	}

}
