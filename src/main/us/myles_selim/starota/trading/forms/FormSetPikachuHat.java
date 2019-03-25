package us.myles_selim.starota.trading.forms;

public class FormSetPikachuHat extends FormSetPichuHat {

	protected static final FormString NO_HAT = new FormString(-1, "No Hat");
	public static final FormSetPikachuHat FORM_SET = new FormSetPikachuHat();

	protected FormSetPikachuHat() {
		this.addForm(new FormString(-1, "H.F. Hat"));
		this.addForm(new FormString(-1, "Flower"));
	}

	@Override
	public Form getDefaultForm() {
		return NO_HAT;
	}

}
