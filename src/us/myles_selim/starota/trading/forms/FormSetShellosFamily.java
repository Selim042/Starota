package us.myles_selim.starota.trading.forms;

public class FormSetShellosFamily extends FormSet {

	private static final FormString WEST = new FormString("West Sea");
	public static final FormSetShellosFamily FORM_SET = new FormSetShellosFamily();

	private FormSetShellosFamily() {
		this.addForm(WEST);
		this.addForm(new FormString("East Sea"));
	}

	@Override
	public Form getDefaultForm() {
		return WEST;
	}

}
