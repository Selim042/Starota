package us.myles_selim.starota.trading.forms;

public class FormSetSquirtleSquad extends FormSet {

	private static final FormString NO_GLASSES = new FormString("No Glasses");
	public static final FormSetSquirtleSquad FORM_SET = new FormSetSquirtleSquad();

	private FormSetSquirtleSquad() {
		if (NO_GLASSES == null)
			System.out.println("NO_GLASSES IS NULL");
		this.addForm(NO_GLASSES);
		this.addForm(new FormString("Glasses"));
	}

	@Override
	public Form getDefaultForm() {
		return NO_GLASSES;
	}

}
