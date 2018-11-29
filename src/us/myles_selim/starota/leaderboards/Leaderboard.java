package us.myles_selim.starota.leaderboards;

import java.text.NumberFormat;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import sx.blah.discord.api.internal.json.objects.EmbedObject;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.EmbedBuilder;
import us.myles_selim.ebs.EBList;
import us.myles_selim.ebs.EBStorage;
import us.myles_selim.ebs.data_types.DataTypeString;
import us.myles_selim.starota.leaderboards.LeaderboardEntry.DataTypeLeaderboardEntry;

public class Leaderboard {

	public static final int PER_PAGE = 10;

	private final IGuild server;
	private final String displayName;
	private final List<LeaderboardEntry> entries;
	private final List<String> aliases;
	private boolean decending;
	private int color;

	@SuppressWarnings("unchecked")
	protected Leaderboard(IGuild server, EBStorage ebs) {
		this.server = server;
		this.displayName = ebs.get("displayName", String.class);
		this.entries = new LinkedList<>(ebs.get("entries", EBList.class).values());
		this.aliases = new LinkedList<>(ebs.get("aliases", EBList.class).values());
		this.decending = ebs.get("decending", Boolean.class);
		this.color = ebs.get("color", Integer.class);
	}

	protected EBStorage toStorage() {
		EBStorage stor = new EBStorage().registerPrimitives();
		stor.set("displayName", this.displayName);
		EBList<LeaderboardEntry> entries = new EBList<>(new DataTypeLeaderboardEntry());
		for (LeaderboardEntry te : this.entries)
			entries.addWrapped(te);
		stor.set("entries", entries);
		EBList<String> aliases = new EBList<>(new DataTypeString());
		for (String a : this.aliases)
			aliases.addWrapped(a);
		stor.set("aliases", aliases);
		stor.set("decending", this.decending);
		stor.set("color", this.color);
		return stor;
	}

	public Leaderboard(IGuild server, String displayName) {
		this(server, displayName, true);
	}

	public Leaderboard(IGuild server, String displayName, boolean decending) {
		this.server = server;
		this.displayName = displayName;
		this.entries = new LinkedList<>();
		this.aliases = new LinkedList<>();
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
		LeaderboardManager.flush(this.server);
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

	public EmbedObject toEmbed() {
		return toEmbed(0);
	}

	public EmbedObject toEmbed(int page) {
		EmbedBuilder builder = new EmbedBuilder();
		builder.withTitle(this.displayName + " Leaderboard");
		if (page < 0 || page > this.entries.size() / PER_PAGE)
			page = 0;
		boolean found = false;
		for (int i = page * PER_PAGE; i < (page + 1) * PER_PAGE && i < this.entries.size(); i++) {
			found = true;
			LeaderboardEntry entry = this.entries.get(i);
			IUser user = entry.getDiscordUser();
			String userDisplay = user.getNicknameForGuild(server);
			if (userDisplay == null)
				userDisplay = user.getName() + "#" + user.getDiscriminator();
			else
				userDisplay += "(__" + user.getName() + "#" + user.getDiscriminator() + "__)";
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

	public EmbedObject toEmbedOptions() {
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

}
