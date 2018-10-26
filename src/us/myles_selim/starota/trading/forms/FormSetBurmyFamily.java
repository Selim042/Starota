package us.myles_selim.starota.trading.forms;

public class FormSetBurmyFamily extends FormSet {

	private static final FormString PLANT = new FormString("Plant");
	public static final FormSetBurmyFamily FORM_SET = new FormSetBurmyFamily();

	private FormSetBurmyFamily() {
		this.addForm(PLANT);
		this.addForm(new FormString("Sandy"));
		this.addForm(new FormString("Trash"));
	}

	@Override
	public Form getDefaultForm() {
		return PLANT;
	}

}
