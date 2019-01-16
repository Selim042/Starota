package us.myles_selim.starota.trading.forms;

import us.myles_selim.starota.trading.forms.FormSet.Form;

public class FormString extends Form {

	private final String name;

	public FormString(int goHubId, String name) {
		super(goHubId);
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}

}
