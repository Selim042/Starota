package us.myles_selim.starota.trading.forms;

public class FormSetDeoxys extends FormSet {

	private static final FormString NORMAL = new FormString("Normal");
	public static final FormSetDeoxys FORM_SET = new FormSetDeoxys();

	private FormSetDeoxys() {
		this.addForm(NORMAL);
		this.addForm(new FormString("Attack"));
		this.addForm(new FormString("Defense"));
		this.addForm(new FormString("Speed"));
	}

	@Override
	public Form getDefaultForm() {
		return NORMAL;
	}

}
