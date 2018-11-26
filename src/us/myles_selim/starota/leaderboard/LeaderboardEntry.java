package us.myles_selim.starota.leaderboard;

import sx.blah.discord.handle.obj.IUser;
import us.myles_selim.starota.Starota;

public class LeaderboardEntry implements Comparable<LeaderboardEntry> {

	private final long discordId;
	private long value;

	public LeaderboardEntry(long discordId, long value) {
		this.discordId = discordId;
		this.value = value;
	}

	public long getDiscordId() {
		return this.discordId;
	}

	public IUser getDiscordUser() {
		return Starota.getUser(this.discordId);
	}

	public long getValue() {
		return this.value;
	}

	public void setValue(long value) {
		this.value = value;
	}

	@Override
	public int compareTo(LeaderboardEntry o) {
		return Long.compare(this.value, o.value);
	}

}
