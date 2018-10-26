package us.myles_selim.starota.trading.forms;

public class FormSetShaymin extends FormSet {

	private static final FormString LAND = new FormString("Land");
	public static final FormSetShaymin FORM_SET = new FormSetShaymin();

	private FormSetShaymin() {
		this.addForm(LAND);
		this.addForm(new FormString("Sky"));
	}

	@Override
	public Form getDefaultForm() {
		return LAND;
	}

}
