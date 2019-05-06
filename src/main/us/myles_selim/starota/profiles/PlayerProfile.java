package us.myles_selim.starota.profiles;

import java.awt.Color;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Consumer;

import discord4j.core.object.entity.Role;
import discord4j.core.object.entity.User;
import discord4j.core.object.util.Snowflake;
import discord4j.core.spec.EmbedCreateSpec;
import us.myles_selim.ebs.DataType;
import us.myles_selim.ebs.Storage;
import us.myles_selim.starota.EventFactory;
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.embed_converter.ExtraField;
import us.myles_selim.starota.enums.EnumTeam;
import us.myles_selim.starota.lua.events.GetProfileEvent;
import us.myles_selim.starota.misc.utils.MiscUtils;
import us.myles_selim.starota.misc.utils.RolePermHelper;
import us.myles_selim.starota.silph_road.SilphRoadCardUtils;
import us.myles_selim.starota.wrappers.StarotaServer;

public class PlayerProfile {

	private String realName;
	private String poGoName;
	private int level;
	private long trainerCode = -1;
	private EnumTeam team;
	private long discordId;
	private Map<String, Long> alts;
	private long lastUpdated;

	public PlayerProfile() {}

	public PlayerProfile(PlayerProfile profile) {
		this.realName = profile.realName;
		this.poGoName = profile.poGoName;
		this.level = profile.level;
		this.trainerCode = profile.trainerCode;
		this.team = profile.team;
		this.discordId = profile.discordId;
		this.alts = profile.alts;
		this.lastUpdated = System.currentTimeMillis() / 1000;
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
		if (this.trainerCode == -1)
			return null;
		return MiscUtils.getTrainerCodeString(this.trainerCode);
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

	public User getDiscordUser() {
		return Starota.getClient().getUserById(Snowflake.of(this.discordId)).block();
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

	public Instant getLastUpdated() {
		if (this.lastUpdated == 0)
			this.lastUpdated = System.currentTimeMillis() / 1000;
		return Instant.ofEpochSecond(this.lastUpdated);
	}

	public String getDonorRoleName() {
		Role donorRole = RolePermHelper.getDonorRole(Starota.getUser(this.discordId));
		if (donorRole != null)
			return donorRole.getName();
		return null;
	}

	public Consumer<EmbedCreateSpec> toEmbed(StarotaServer server) {
		return toEmbed(EventFactory.fireProfileEvent(server, this));
	}

	private Consumer<EmbedCreateSpec> toEmbed(GetProfileEvent event) {
		return (e) -> {
			e.setAuthor(Starota.getUser(this.discordId).getUsername(), null,
					Starota.getUser(this.discordId).getAvatarUrl());
			e.setTitle("Profile for " + this.poGoName + ":");
			for (ExtraField f : event.getFields()) {
				if (f == null)
					continue;
				e.addField(f.title, f.value, f.inline);
			}

			e.setColor(new Color(event.getColor()));
			e.setThumbnail(event.getThumbnail());

			e.setFooter("Profile last updated", null);
			e.setTimestamp(getLastUpdated());
		};
	}

	public String getSilphRoadCard() {
		return SilphRoadCardUtils.getCardURL(this.poGoName);
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

			stor.writeLong(value.lastUpdated);
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

			value.lastUpdated = stor.readLong();
		}

	}

}
