package us.myles_selim.starota.trading.forms;

public class FormSetRotom extends FormSet {

	private static final FormString NORMAL = new FormString("Normal");
	public static final FormSetRotom FORM_SET = new FormSetRotom();

	private FormSetRotom() {
		this.addForm(NORMAL);
		this.addForm(new FormString("Heat"));
		this.addForm(new FormString("Wash"));
		this.addForm(new FormString("Frost"));
		this.addForm(new FormString("Fan"));
		this.addForm(new FormString("Mow"));
	}

	@Override
	public Form getDefaultForm() {
		return NORMAL;
	}

}
