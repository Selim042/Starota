package us.myles_selim.starota.leaderboards;

import sx.blah.discord.handle.obj.IUser;
import us.myles_selim.ebs.DataType;
import us.myles_selim.ebs.Storage;
import us.myles_selim.starota.Starota;

public class LeaderboardEntry implements Comparable<LeaderboardEntry> {

	private final long discordId;
	private long value;

	public LeaderboardEntry(IUser user, long value) {
		this(user.getLongID(), value);
	}

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

	protected static class DataTypeLeaderboardEntry extends DataType<LeaderboardEntry> {

		private LeaderboardEntry value;

		public DataTypeLeaderboardEntry() {}

		@Override
		public LeaderboardEntry getValue() {
			return this.value;
		}

		@Override
		public void setValue(LeaderboardEntry value) {
			this.value = value;
		}

		@Override
		protected void setValueObject(Object value) {
			if (value instanceof LeaderboardEntry)
				this.value = (LeaderboardEntry) value;
		}

		@Override
		public Class<?>[] accepts() {
			return new Class[] { LeaderboardEntry.class };
		}

		@Override
		public void toBytes(Storage stor) {
			if (this.value == null) {
				stor.writeLong(-1);
				return;
			}
			stor.writeLong(this.value.discordId);
			stor.writeLong(this.value.value);
		}

		@Override
		public void fromBytes(Storage stor) {
			long l = stor.readLong();
			if (l != -1)
				this.value = new LeaderboardEntry(l, stor.readLong());
		}

	}

}
