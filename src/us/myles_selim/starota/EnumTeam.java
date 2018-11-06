package us.myles_selim.starota;

public enum EnumTeam {
	// TODO: Replace NO_TEAM URL with a representative image
	NO_TEAM(
			"No team",
			0x777777,
			"http://assets.myles-selim.us/starota/teams/instinct.png"),
	INSTINCT(
			"Instinct",
			0xF1C40F,
			"http://assets.myles-selim.us/starota/teams/instinct.png"),
	MYSTIC(
			"Mystic",
			0x277ECD,
			"http://assets.myles-selim.us/starota/teams/mystic.png"),
	VALOR(
			"Valor",
			0x992D22,
			"http://assets.myles-selim.us/starota/teams/valor.png");

	private String name;
	private int color;
	private String icon;

	EnumTeam(String name, int color, String icon) {
		this.name = name;
		this.color = color;
		this.icon = icon;
	}

	public String getName() {
		return this.name;
	}

	public int getColor() {
		return this.color;
	}

	public String getIcon() {
		return this.icon;
	}

	public static EnumTeam valueOf(int ordinal) {
		if (ordinal < 0 || ordinal >= values().length)
			return null;
		return values()[ordinal];
	}

}
