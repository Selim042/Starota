package us.myles_selim.starota.forms;

import java.util.Iterator;
import java.util.NoSuchElementException;

import us.myles_selim.starota.enums.EnumPokemon;
import us.myles_selim.starota.forms.Form.NormalDefaultForm;

public class FormSet implements Iterable<Form> {

	@SuppressWarnings("unused")
	private final int version = 1;
	private Form defaultForm;
	private Form[] otherForms;

	protected FormSet() {}

	public boolean isDefaultOnly() {
		return otherForms.length == 0;
	}

	public Form getDefaultForm() {
		return this.defaultForm;
	}

	public Form[] getOtherForms() {
		return this.otherForms;
	}

	public int getFormId(Form form) {
		if (defaultForm.equals(form))
			return 0;
		for (int i = 0; i < otherForms.length; i++)
			if (otherForms[i].equals(form))
				return i + 1;
		return -1;
	}

	public Form getFormById(int id) {
		if (id == 0)
			return defaultForm;
		if (id > 0 && id - 1 < otherForms.length)
			return otherForms[id - 1];
		return null;
	}

	/**
	 * Just for conversion from old setup
	 */
	protected void setDefaultForm(Form defaultForm) {
		this.defaultForm = defaultForm;
	}

	/**
	 * Just for conversion from old setup
	 */
	protected void setOtherForms(Form[] otherForms) {
		this.otherForms = otherForms;
	}

	public Form getForm(String form) {
		if (form == null || form.equalsIgnoreCase("Normal") || form.equalsIgnoreCase("null"))
			return getDefaultForm();
		for (Form f : this) {
			String formName = f.toString().replaceAll("\\.", "").replaceAll(" ", "_");
			String postfix = f.getEmojiPostfix();
			if ((formName.equalsIgnoreCase(form) || formName.split("_")[0].equalsIgnoreCase(form)
					|| formName.replaceAll("_", "").equalsIgnoreCase(form)))
				return f;
			if ((postfix != null
					&& (postfix.equalsIgnoreCase(form) || postfix.split("_")[0].equalsIgnoreCase(form)
							|| postfix.replaceAll("_", "").equalsIgnoreCase(form))))
				return f;
		}
		return null;
	}

	public Form getForm(int form) {
		for (Form f : this)
			if (f.getGoHubFormId() == form)
				return f;
		return null;
	}

	@Override
	public Iterator<Form> iterator() {
		return new FormSetIterator();
	}

	protected static class DefaultFormSet extends FormSet {

		public DefaultFormSet(EnumPokemon pokemon) {
			setDefaultForm(new NormalDefaultForm(pokemon));
			setOtherForms(new Form[0]);
		}

	}

	private class FormSetIterator implements Iterator<Form> {

		private int index = -1;

		@Override
		public boolean hasNext() {
			if (index == -1)
				return true;
			return index < otherForms.length;
		}

		@Override
		public Form next() {
			if (!hasNext())
				throw new NoSuchElementException();
			if (index == -1) {
				index++;
				return defaultForm;
			}
			return otherForms[index++];
		}

	}

}
