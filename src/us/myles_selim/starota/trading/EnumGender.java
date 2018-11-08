package us.myles_selim.starota.trading;

public enum EnumGender {
	UNKNOWN("Unknown"),
	EITHER("Male/female"),
	MALE("Male"),
	FEMALE("Female");

	private String display;

	EnumGender() {
		this(null);
	}

	EnumGender(String display) {
		this.display = display;
	}

	@Override
	public String toString() {
		return this.display == null ? this.name() : this.display;
	}
}
