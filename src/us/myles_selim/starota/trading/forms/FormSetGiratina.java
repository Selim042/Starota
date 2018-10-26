package us.myles_selim.starota.trading.forms;

public class FormSetGiratina extends FormSet {

	private static final FormString ALTERED = new FormString("Altered");
	public static final FormSetGiratina FORM_SET = new FormSetGiratina();

	private FormSetGiratina() {
		this.addForm(ALTERED);
		this.addForm(new FormString("Origin"));
	}

	@Override
	public Form getDefaultForm() {
		return ALTERED;
	}

}
