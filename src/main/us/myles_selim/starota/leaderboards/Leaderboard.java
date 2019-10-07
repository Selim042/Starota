package us.myles_selim.starota.leaderboards;

import java.text.NumberFormat;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;

import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Member;
import discord4j.core.spec.EmbedCreateSpec;
import us.myles_selim.ebs.DataType;
import us.myles_selim.ebs.Storage;
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.misc.utils.EmbedBuilder;

public class Leaderboard extends DataType<Leaderboard> {

	public static final int PER_PAGE = 10;

	private Guild guild;
	private String displayName;
	private List<LeaderboardEntry> entries;
	private List<String> aliases;
	private boolean decending;
	private int color;
	private EnumUpdateType type;
	private boolean isEnabled = true;

	public Leaderboard() {}

	public Leaderboard(Guild server, String displayName) {
		this(server, displayName, true);
	}

	public Leaderboard(Guild server, String displayName, boolean decending) {
		this.guild = server;
		this.displayName = displayName;
		this.entries = new LinkedList<>();
		this.aliases = new LinkedList<>();
		addAlias(displayName.replaceAll(" ", "_"));
		this.decending = decending;
	}

	public String getDisplayName() {
		return this.displayName;
	}

	public List<LeaderboardEntry> getEntries() {
		return Collections.unmodifiableList(this.entries);
	}

	public Leaderboard updateEntry(LeaderboardEntry entry) {
		LeaderboardEntry eEntry = getEntry(entry.getDiscordId());
		if (eEntry == null)
			entries.add(entry);
		else
			eEntry.setValue(entry.getValue());
		this.sort();
		if (this.getParent() != null)
			this.getParent().flush();
		return this;
	}

	public LeaderboardEntry getEntry(long userId) {
		for (LeaderboardEntry e : this.entries)
			if (e.getDiscordId() == userId)
				return e;
		return null;
	}

	public List<String> getAliases() {
		return Collections.unmodifiableList(this.aliases);
	}

	public Leaderboard addAlias(String alias) {
		if (!this.aliases.contains(alias))
			this.aliases.add(alias);
		return this;
	}

	public Leaderboard removeAlias(String alias) {
		this.aliases.remove(alias);
		return this;
	}

	public Leaderboard setAliases(String[] aliases) {
		this.aliases.clear();
		for (String a : aliases)
			this.aliases.add(a);
		return this;
	}

	public boolean isDecending() {
		return this.decending;
	}

	public Leaderboard setDecending(boolean decending) {
		this.decending = decending;
		return this;
	}

	public int getColor() {
		return this.color;
	}

	public Leaderboard setColor(int color) {
		this.color = color;
		return this;
	}

	public boolean isActive() {
		return isEnabled && !(this.aliases.isEmpty() || (this.aliases.size() == 1
				&& this.aliases.get(0).equalsIgnoreCase(this.displayName.replaceAll(" ", "_"))));
	}

	protected void setType(EnumUpdateType type) {
		this.type = type;
	}

	public EnumUpdateType getType() {
		if (this.type == null)
			return EnumUpdateType.NORMAL;
		return this.type;
	}

	public boolean isEnabled() {
		return this.isEnabled;
	}

	public void setEnabled(boolean enabled) {
		this.isEnabled = enabled;
	}

	public Consumer<? super EmbedCreateSpec> toEmbed() {
		return toEmbed(0);
	}

	public Consumer<? super EmbedCreateSpec> toEmbed(int page) {
		EmbedBuilder builder = new EmbedBuilder();
		builder.withTitle(this.displayName + " Leaderboard");
		if (page < 0 || page > this.entries.size() / PER_PAGE)
			page = 0;
		boolean found = false;
		for (int i = page * PER_PAGE; i < (page + 1) * PER_PAGE && i < this.entries.size(); i++) {
			found = true;
			LeaderboardEntry entry = this.entries.get(i);
			Member user = entry.getDiscordMember(guild);
			String userDisplay = user.getDisplayName();
			if (userDisplay == null)
				userDisplay = user.getUsername() + "#" + user.getDiscriminator();
			else
				userDisplay += " (_" + user.getUsername() + "#" + user.getDiscriminator() + "_)";
			builder.appendDesc("**" + (i + 1) + "**) " + userDisplay + ": "
					+ NumberFormat.getNumberInstance(Locale.US).format(entry.getValue()) + "\n");
		}
		if (!found)
			builder.appendDesc("No tradeboard entries");
		else
			builder.appendDesc(
					"\n**Page**: " + (page + 1) + "/" + ((this.entries.size() / PER_PAGE) + 1));
		builder.withColor(this.color);
		return builder.build();
	}

	public Consumer<? super EmbedCreateSpec> toEmbedOptions() {
		EmbedBuilder builder = new EmbedBuilder();
		builder.withTitle("Options for: " + this.displayName);
		builder.appendField("Decending:", Boolean.toString(this.decending), true);
		builder.appendField("Active:", Boolean.toString(isActive()), true);
		builder.appendField("Enabled:", Boolean.toString(isEnabled()), true);
		String aliases = "";
		for (String a : this.aliases)
			aliases += " - " + a + "\n";
		builder.appendField("Aliases:", aliases, false);
		builder.appendField("Type:", getType().getDisplayName(), false);
		builder.withColor(this.color);
		return builder.build();
	}

	private void sort() {
		if (this.decending)
			this.entries.sort(Collections.reverseOrder());
		else
			this.entries.sort(null);
	}

	@Override
	public Leaderboard getValue() {
		return this;
	}

	@Override
	protected void setValueInternal(Leaderboard value) {
		this.aliases = new LinkedList<>(value.aliases);
		this.color = value.color;
		this.decending = value.decending;
		this.displayName = value.displayName;
		this.entries = new LinkedList<>(value.entries);
		this.guild = value.guild;
		this.type = value.type;
		this.isEnabled = value.isEnabled;
	}

	@Override
	protected void setValueObject(Object value) {
		if (value instanceof Leaderboard && !(value instanceof DefaultLeaderboard))
			setValue((Leaderboard) value);
	}

	@Override
	public boolean acceptsValue(Object value) {
		return super.acceptsValue(value) && !(value instanceof DefaultLeaderboard);
	}

	@Override
	public Class<?>[] accepts() {
		return new Class[] { Leaderboard.class };
	}

	@Override
	public void toBytes(Storage stor) {
		stor.writeString(this.displayName);
		if (this.entries == null || this.entries.isEmpty())
			stor.writeInt(0);
		else {
			stor.writeInt(this.entries.size());
			for (LeaderboardEntry e : this.entries) {
				stor.writeLong(e.getDiscordId());
				stor.writeLong(e.getValue());
			}
		}
		if (this.aliases == null || this.aliases.isEmpty())
			stor.writeInt(0);
		else {
			stor.writeInt(this.aliases.size());
			for (String a : this.aliases)
				stor.writeString(a);
		}
		stor.writeBoolean(this.decending);
		stor.writeInt(this.color);
		if (this.type == null)
			stor.writeInt(0);
		else
			stor.writeInt(this.type.ordinal());
		stor.writeByte((byte) (isEnabled ? 1 : 2));
		if (this.guild == null)
			stor.writeInt(0);
		else
			stor.writeLong(this.guild.getId().asLong());
	}

	@Override
	public void fromBytes(Storage stor) {
		this.displayName = stor.readString();
		this.entries = new LinkedList<>();
		int entries = stor.readInt();
		for (int i = 0; i < entries; i++)
			this.entries.add(new LeaderboardEntry(stor.readLong(), stor.readLong()));
		this.aliases = new LinkedList<>();
		int aliases = stor.readInt();
		for (int i = 0; i < aliases; i++)
			this.aliases.add(stor.readString());
		this.decending = stor.readBoolean();
		this.color = stor.readInt();
		int type = stor.readInt();
		if (type <= 0 || type >= EnumUpdateType.values().length)
			this.type = EnumUpdateType.NORMAL;
		else
			this.type = EnumUpdateType.values()[type];
		byte enabledByte = stor.readByte();
		this.isEnabled = enabledByte == 0 || enabledByte == 1 ? true : false;
		long guildId = stor.readLong();
		if (guildId != 0)
			this.guild = Starota.getGuild(guildId);
	}

	public enum EnumUpdateType {
		NORMAL("Normal"),
		INTEGRATION("Intergration"),
		OCR("OCR");

		private final String displayName;

		EnumUpdateType(String displayName) {
			this.displayName = displayName;
		}

		public String getDisplayName() {
			return this.displayName;
		}
	}

}
