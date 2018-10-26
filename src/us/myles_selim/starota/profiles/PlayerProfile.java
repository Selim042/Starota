package us.myles_selim.starota.profiles;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import sx.blah.discord.handle.obj.IUser;
import us.myles_selim.ebs.DataType;
import us.myles_selim.ebs.Storage;
import us.myles_selim.starota.EnumTeam;
import us.myles_selim.starota.Starota;

public class PlayerProfile {

	private String realName;
	private String poGoName;
	private int level;
	private long trainerCode = -1;
	private EnumTeam team;
	private long discordId;
	private Map<String, Long> alts;

	public PlayerProfile() {}

	public PlayerProfile(PlayerProfile profile) {
		this.realName = profile.realName;
		this.poGoName = profile.poGoName;
		this.level = profile.level;
		this.trainerCode = profile.trainerCode;
		this.team = profile.team;
		this.discordId = profile.discordId;
		this.alts = profile.alts;
	}

	public String getRealName() {
		return this.realName;
	}

	public PlayerProfile setRealName(String name) {
		this.realName = name;
		return this;
	}

	public String getPoGoName() {
		return this.poGoName;
	}

	public PlayerProfile setPoGoName(String name) {
		this.poGoName = name;
		return this;
	}

	public int getLevel() {
		return this.level;
	}

	public PlayerProfile setLevel(int level) {
		if (level >= 0 && level <= 40)
			this.level = level;
		return this;
	}

	public long getTrainerCode() {
		return this.trainerCode;
	}

	public PlayerProfile setTrainerCode(long code) {
		this.trainerCode = code;
		return this;
	}

	public String getTrainerCodeString() {
		return ProfileManager.getTrainerCodeString(this.trainerCode);
	}

	public EnumTeam getTeam() {
		if (this.team == null)
			return EnumTeam.NO_TEAM;
		return this.team;
	}

	public PlayerProfile setTeam(EnumTeam team) {
		this.team = team;
		return this;
	}

	public long getDiscordId() {
		return this.discordId;
	}

	public IUser getDiscordUser() {
		return Starota.getClient().getUserByID(this.discordId);
	}

	public PlayerProfile setDiscordId(long id) {
		this.discordId = id;
		return this;
	}

	public Map<String, Long> getAlts() {
		if (this.alts == null)
			this.alts = new HashMap<>();
		return this.alts;
	}

	public static class DataTypePlayerProfile extends DataType<PlayerProfile> {

		private PlayerProfile value;

		public DataTypePlayerProfile() {}

		@Override
		public PlayerProfile getValue() {
			return value;
		}

		@Override
		public void setValue(PlayerProfile value) {
			this.value = value;
		}

		@Override
		protected void setValueObject(Object value) {
			setValue((PlayerProfile) value);
		}

		@Override
		public Class<?>[] accepts() {
			return new Class[] { PlayerProfile.class };
		}

		@Override
		public void toBytes(Storage stor) {
			if (value == null)
				return;
			stor.writeInt(value.level);
			stor.writeLong(value.trainerCode);
			stor.writeLong(value.discordId);
			if (value.team != null)
				stor.writeInt(value.team.ordinal());
			else
				stor.writeInt(-1);
			stor.writeString(value.realName);
			stor.writeString(value.poGoName);

			if (value.alts == null)
				stor.writeInt(0);
			else {
				stor.writeInt(value.alts.size());
				for (Entry<String, Long> ap : value.alts.entrySet()) {
					stor.writeString(ap.getKey());
					stor.writeLong(ap.getValue());
				}
			}
		}

		@Override
		public void fromBytes(Storage stor) {
			if (value == null)
				value = new PlayerProfile();
			value.level = stor.readInt();
			value.trainerCode = stor.readLong();
			value.discordId = stor.readLong();
			value.team = EnumTeam.valueOf(stor.readInt());
			value.realName = stor.readString();
			value.poGoName = stor.readString();

			int alts = stor.readInt();
			if (value.alts == null)
				value.alts = new HashMap<>();
			for (int i = 0; i < alts; i++)
				value.alts.put(stor.readString(), stor.readLong());
		}

	}

}
