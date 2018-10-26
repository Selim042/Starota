package us.myles_selim.starota.trading.forms;

import us.myles_selim.starota.trading.forms.FormSet.Form;

public class FormString extends Form {

	private final String name;

	public FormString(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}

}
