package us.myles_selim.starota.leaderboards;

import java.text.NumberFormat;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;

import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.User;
import discord4j.core.spec.EmbedCreateSpec;
import us.myles_selim.ebs.DataType;
import us.myles_selim.ebs.EBList;
import us.myles_selim.ebs.EBStorage;
import us.myles_selim.ebs.Storage;
import us.myles_selim.starota.misc.data_types.EmbedBuilder;

public class Leaderboard extends DataType<Leaderboard> {

	public static final int PER_PAGE = 10;

	private Guild server;
	private String displayName;
	private List<LeaderboardEntry> entries;
	private List<String> aliases;
	private boolean decending;
	private int color;

	public Leaderboard() {}

	@SuppressWarnings("unchecked")
	public Leaderboard(Guild server, EBStorage ebs) {
		this.server = server;
		this.displayName = ebs.get("displayName", String.class);
		this.entries = new LinkedList<>(ebs.get("entries", EBList.class).values());
		this.aliases = new LinkedList<>(ebs.get("aliases", EBList.class).values());
		this.decending = ebs.get("decending", Boolean.class);
		this.color = ebs.get("color", Integer.class);
	}

	public Leaderboard(Guild server, String displayName) {
		this(server, displayName, true);
	}

	public Leaderboard(Guild server, String displayName, boolean decending) {
		this.server = server;
		this.displayName = displayName;
		this.entries = new LinkedList<>();
		this.aliases = new LinkedList<>();
		this.decending = decending;
	}

	// public EBStorage toStorage() {
	// EBStorage stor = new EBStorage().registerPrimitives();
	// stor.set("displayName", this.displayName);
	// EBList<LeaderboardEntry> entries = new EBList<>(new
	// DataTypeLeaderboardEntry());
	// for (LeaderboardEntry te : this.entries)
	// entries.addWrapped(te);
	// stor.set("entries", entries);
	// EBList<String> aliases = new EBList<>(new DataTypeString());
	// for (String a : this.aliases)
	// aliases.addWrapped(a);
	// stor.set("aliases", aliases);
	// stor.set("decending", this.decending);
	// stor.set("color", this.color);
	// return stor;
	// }

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
		return !(this.aliases.isEmpty() || (this.aliases.size() == 1
				&& this.aliases.get(0).equalsIgnoreCase(this.displayName.replaceAll(" ", "_"))));
	}

	public Consumer<EmbedCreateSpec> toEmbed() {
		return toEmbed(0);
	}

	public Consumer<EmbedCreateSpec> toEmbed(int page) {
		EmbedBuilder builder = new EmbedBuilder();
		builder.withTitle(this.displayName + " Leaderboard");
		if (page < 0 || page > this.entries.size() / PER_PAGE)
			page = 0;
		boolean found = false;
		for (int i = page * PER_PAGE; i < (page + 1) * PER_PAGE && i < this.entries.size(); i++) {
			found = true;
			LeaderboardEntry entry = this.entries.get(i);
			User user = entry.getDiscordUser();
			String userDisplay = user.asMember(server.getId()).block().getDisplayName();
			if (userDisplay == null)
				userDisplay = user.getUsername() + "#" + user.getDiscriminator();
			else
				userDisplay += "(__" + user.getUsername() + "#" + user.getDiscriminator() + "__)";
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

	public Consumer<EmbedCreateSpec> toEmbedOptions() {
		EmbedBuilder builder = new EmbedBuilder();
		builder.withTitle("Options for: " + this.displayName);
		builder.appendField("Decending:", Boolean.toString(this.decending), true);
		builder.appendField("Active:", Boolean.toString(isActive()), true);
		String aliases = "";
		for (String a : this.aliases)
			aliases += " - " + a + "\n";
		builder.appendField("Aliases:", aliases, false);
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
	public void setValue(Leaderboard value) {
		this.aliases = new LinkedList<>(value.aliases);
		this.color = value.color;
		this.decending = value.decending;
		this.displayName = value.displayName;
		this.entries = new LinkedList<>(value.entries);
		this.server = value.server;
	}

	@Override
	protected void setValueObject(Object value) {
		if (value instanceof Leaderboard)
			setValue((Leaderboard) value);
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
	}

}
