package us.myles_selim.starota.trading.forms;

public class FormSetSquirtleSquad extends FormSet {

	private static final FormString NO_GLASSES = new FormString(-1, "No Glasses");
	public static final FormSetSquirtleSquad FORM_SET = new FormSetSquirtleSquad();

	private FormSetSquirtleSquad() {
		this.addForm(NO_GLASSES);
		this.addForm(new FormString(-1, "Glasses"));
	}

	@Override
	public Form getDefaultForm() {
		return NO_GLASSES;
	}

}
