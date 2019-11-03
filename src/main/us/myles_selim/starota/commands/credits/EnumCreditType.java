package us.myles_selim.starota.commands.credits;

public enum EnumCreditType {

	OTHER(null),
	CONTRIBUTOR("Contributors"),
	EDITOR("Article Editors"),
	BETA_TESTER("Beta Tester");

	private String display;

	private EnumCreditType(String display) {
		this.display = display;
	}

	public String getDisplay() {
		return display;
	}

}
