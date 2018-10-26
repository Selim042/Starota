package us.myles_selim.starota.trading.forms;

public class FormSetUnown extends FormSet {

	private static final FormUnown F = new FormUnown('F');
	public static final FormSetUnown FORM_SET = new FormSetUnown();

	private FormSetUnown() {
		for (char c = 'A'; c <= 'Z'; c++)
			if (c == 'F')
				this.addForm(F);
			else
				this.addForm(new FormUnown(c));
		this.addForm(new FormUnown('!'));
		this.addForm(new FormUnown('?'));
	}

	@Override
	public Form getDefaultForm() {
		return F;
	}

	private static class FormUnown extends Form {

		private final char letter;

		public FormUnown(char letter) {
			if (Character.isAlphabetic(letter) || letter == '!' || letter == '?')
				this.letter = letter;
			else
				throw new IllegalArgumentException("letter must be alphabetic or '?' or '!'");
		}

		@Override
		public String toString() {
			return Character.toString(this.letter);
		}

	}

}
