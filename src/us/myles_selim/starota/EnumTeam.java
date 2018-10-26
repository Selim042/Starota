package us.myles_selim.starota;

public enum EnumTeam {
	NO_TEAM(
			"No team",
			0x777777),
	INSTINCT(
			"Instinct",
			0xF1C40F),
	MYSTIC(
			"Mystic",
			0x277ECD),
	VALOR(
			"Valor",
			0x992D22);

	private String name;
	private int color;

	EnumTeam(String name, int color) {
		this.name = name;
		this.color = color;
	}

	public String getName() {
		return this.name;
	}

	public int getColor() {
		return this.color;
	}

	public static EnumTeam valueOf(int ordinal) {
		if (ordinal < 0 || ordinal >= values().length)
			return null;
		return values()[ordinal];
	}

}
