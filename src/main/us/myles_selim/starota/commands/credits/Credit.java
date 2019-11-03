package us.myles_selim.starota.commands.credits;

public class Credit implements Creditable {

	private EnumCreditType type;
	private String title;
	private String name;
	private String link;

	private Credit(Builder builder) {
		this.type = builder.type;
		if (builder.title != null && builder.type != EnumCreditType.OTHER)
			throw new IllegalArgumentException(type + " credits don't have titles");
		this.title = builder.title;
		this.name = builder.name;
		this.link = builder.link;
	}

	@Override
	public EnumCreditType getType() {
		return type;
	}

	public String getTitle() {
		return title;
	}

	public String getName() {
		return name;
	}

	public String getLink() {
		return link;
	}

	public static class Builder {

		private EnumCreditType type;
		private String title;
		private String name;
		private String link;

		public Builder(EnumCreditType type) {
			this.type = type;
		}

		public Builder withTitle(String title) {
			this.title = title;
			return this;
		}

		public Builder withName(String name) {
			this.name = name;
			return this;
		}

		public Builder withLink(String link) {
			this.link = link;
			return this;
		}

		public Credit build() {
			return new Credit(this);
		}

	}

}
