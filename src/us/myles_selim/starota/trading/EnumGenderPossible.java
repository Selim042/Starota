package us.myles_selim.starota.trading;

public enum EnumGenderPossible {
	UNKNOWN,
	EITHER(
			"male/female"),
	MALE,
	FEMALE;

	private String display;

	EnumGenderPossible() {
		this(null);
	}

	EnumGenderPossible(String display) {
		this.display = display;
	}

	@Override
	public String toString() {
		return this.display == null ? this.name() : this.display;
	}
}
