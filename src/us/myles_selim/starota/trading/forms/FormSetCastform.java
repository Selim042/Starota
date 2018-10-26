package us.myles_selim.starota.trading.forms;

public class FormSetCastform extends FormSet {

	private static final FormString NORMAL = new FormString("Normal");
	public static final FormSetCastform FORM_SET = new FormSetCastform();

	private FormSetCastform() {
		this.addForm(NORMAL);
		this.addForm(new FormString("Sunny"));
		this.addForm(new FormString("Rainy"));
		this.addForm(new FormString("Snowy"));
	}

	@Override
	public Form getDefaultForm() {
		return NORMAL;
	}

}
