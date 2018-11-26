package us.myles_selim.starota.leaderboard;

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

public class Leaderboard {

	public static final int PER_PAGE = 10;

	private final IGuild server;
	private final String displayName;
	private final List<LeaderboardEntry> entries;
	private final List<String> aliases;
	private final boolean decending;

	@SuppressWarnings("unchecked")
	protected Leaderboard(IGuild server, EBStorage ebs) {
		this.server = server;
		this.displayName = ebs.get("displayName", String.class);
		this.entries = new LinkedList<>(ebs.get("entries", EBList.class).values());
		this.aliases = new LinkedList<>(ebs.get("aliases", EBList.class).values());
		this.decending = ebs.get("decending", boolean.class);
	}

	public Leaderboard(IGuild server, String displayName, boolean decending) {
		this.server = server;
		this.displayName = displayName;
		this.entries = new LinkedList<>();
		this.aliases = Collections.emptyList();
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

	public EmbedObject toEmbed() {
		return toEmbed(0);
	}

	public EmbedObject toEmbed(int page) {
		EmbedBuilder builder = new EmbedBuilder();
		builder.withTitle(this.displayName + " Leaderboard");
		if (page < 0 || page > this.entries.size() / PER_PAGE)
			page = 0;
		for (int i = page * PER_PAGE; i < (page + 1) * PER_PAGE && i < this.entries.size(); i++) {
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
		builder.appendDesc("\n**Page**: " + (page + 1) + "/" + ((this.entries.size() / PER_PAGE) + 1));
		return builder.build();
	}

	private void sort() {
		if (this.decending)
			this.entries.sort(Collections.reverseOrder());
		else
			this.entries.sort(null);
	}

}
