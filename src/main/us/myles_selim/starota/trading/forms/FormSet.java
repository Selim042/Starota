package us.myles_selim.starota.trading.forms;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import us.myles_selim.starota.enums.EnumPokemon;
import us.myles_selim.starota.enums.EnumPokemonType;

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
		for (Form f : forms) {
			String formName = f.toString().replaceAll("\\.", "").replaceAll(" ", "_");
			String postfix = f.getSpritePostfix(null);
			if ((formName.equalsIgnoreCase(form) || formName.split("_")[0].equalsIgnoreCase(form)
					|| formName.replaceAll("_", "").equalsIgnoreCase(form))
					|| (postfix != null && (postfix.equalsIgnoreCase(form)
							|| postfix.split("_")[0].equalsIgnoreCase(form)
							|| postfix.replaceAll("_", "").equalsIgnoreCase(form))))
				return f;
		}
		return null;
	}

	public Form getForm(int form) {
		for (Form f : forms)
			if (f.goHubId == form)
				return f;
		return null;
	}

	public static abstract class Form {

		private int goHubId;

		public Form(int goHubId) {
			this.goHubId = goHubId;
		}

		public int getGoHubId(EnumPokemon pokemon) {
			return this.goHubId;
		}

		public boolean canBeShiny(EnumPokemon pokemon) {
			return pokemon.isShinyable();
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
