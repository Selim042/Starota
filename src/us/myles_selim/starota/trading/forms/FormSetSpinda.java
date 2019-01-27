package us.myles_selim.starota.trading.forms;

public class FormSetSpinda extends FormSet {

	private static final FormSpinda ONE = new FormSpinda(1);
	public static final FormSetSpinda FORM_SET = new FormSetSpinda();

	private FormSetSpinda() {
		this.addForm(ONE);
		for (int i = 2; i < 9; i++)
			this.addForm(new FormSpinda(i));
	}

	@Override
	public Form getDefaultForm() {
		return ONE;
	}

	private static class FormSpinda extends Form {

		private final int num;

		public FormSpinda(int num) {
			super(-1);
			this.num = num;
		}

		@Override
		public String toString() {
			return Integer.toString(this.num);
		}

	}

}
