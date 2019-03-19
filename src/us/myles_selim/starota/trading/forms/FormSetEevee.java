package us.myles_selim.starota.trading.forms;

public class FormSetEevee extends FormSet {

	private static final FormString NO_FLOWERS = new FormString(-1, "Normal");
	public static final FormSetEevee FORM_SET = new FormSetEevee();

	private FormSetEevee() {
		this.addForm(NO_FLOWERS);
		this.addForm(new FormString(-1, "Flowers"));
	}

	@Override
	public Form getDefaultForm() {
		return NO_FLOWERS;
	}

}
