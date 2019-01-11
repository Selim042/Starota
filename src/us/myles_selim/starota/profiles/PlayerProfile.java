package us.myles_selim.starota.profiles;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import sx.blah.discord.api.internal.json.objects.EmbedObject;
import sx.blah.discord.handle.obj.IRole;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.EmbedBuilder;
import us.myles_selim.ebs.DataType;
import us.myles_selim.ebs.Storage;
import us.myles_selim.starota.EventFactory;
import us.myles_selim.starota.MiscUtils;
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.embed_converter.ExtraField;
import us.myles_selim.starota.embed_converter.annotations.EmbedAuthorIcon;
import us.myles_selim.starota.embed_converter.annotations.EmbedAuthorName;
import us.myles_selim.starota.embed_converter.annotations.EmbedColor;
import us.myles_selim.starota.embed_converter.annotations.EmbedField;
import us.myles_selim.starota.embed_converter.annotations.EmbedFooterText;
import us.myles_selim.starota.embed_converter.annotations.EmbedThumbnail;
import us.myles_selim.starota.embed_converter.annotations.EmbedTimestamp;
import us.myles_selim.starota.embed_converter.annotations.EmbedTitle;
import us.myles_selim.starota.enums.EnumTeam;
import us.myles_selim.starota.lua.events.GetProfileEvent;
import us.myles_selim.starota.wrappers.StarotaServer;

@EmbedFooterText("Profile last updated")
@EmbedTitle("Profile for %poGoName%:")
public class PlayerProfile {

	@EmbedField(value = "Real Name:", isInline = true, order = 2)
	private String realName;
	private String poGoName;
	@EmbedField(value = "Trainer Level:", isInline = true, order = 0)
	private int level;
	private long trainerCode = -1;
	@EmbedField(value = "Team:", isInline = true, order = 1)
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

	@EmbedField(value = "Trainer Code:", isInline = true, order = 3)
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

	public Instant getLastUpdated() {
		if (this.lastUpdated == 0)
			this.lastUpdated = System.currentTimeMillis() / 1000;
		return Instant.ofEpochSecond(this.lastUpdated);
	}

	public EmbedObject toEmbed(StarotaServer server) {
		return toEmbed(EventFactory.fireProfileEvent(server, this));
	}

	private EmbedObject toEmbed(GetProfileEvent event) {
		EmbedBuilder builder = new EmbedBuilder();
		builder.withAuthorIcon(this.authorIcon());
		builder.withAuthorName(this.authorText());
		builder.withTitle("Profile for " + this.poGoName + ":");
		for (ExtraField f : event.getFields()) {
			if (f == null)
				continue;
			builder.appendField(f.title, f.value, f.inline);
		}

		builder.withColor(event.getColor());
		builder.withThumbnail(event.getThumbnail());

		builder.withFooterText("Profile last updated");
		builder.withTimestamp(getLastUpdated());
		return builder.build();
	}

	@EmbedField(value = "Silph Road Card:", order = 5)
	public String getSilphRoadCard() {
		return SilphRoadUtils.getCard(this.poGoName);
	}

	// only for EmbedConverter
	@EmbedField(value = "Alternate Accounts:", order = 4)
	private String altAccounts() {
		if (this.alts == null || this.alts.isEmpty())
			return null;
		String ret = "";
		for (Entry<String, Long> e : this.alts.entrySet())
			ret += "- **" + e.getKey() + "**: " + MiscUtils.getTrainerCodeString(e.getValue());
		return ret;
	}

	// only for EmbedConverter
	@EmbedField(value = "Discord User:", order = 6)
	private String discordUser() {
		IUser user = Starota.getUser(this.discordId);
		// String nickname = user.getNicknameForGuild(server);
		// if (nickname != null)
		// return nickname + " (_" + user.getName() + "#" +
		// user.getDiscriminator() + "_)";
		// else
		return user.getName() + "#" + user.getDiscriminator();
	}

	// only for EmbedConverter
	@EmbedField(value = "Patron:", order = 7)
	public String getPatronRoleName() {
		IRole patronRole = Starota.getPatronRole(Starota.getUser(this.discordId));
		if (patronRole != null)
			return patronRole.getName();
		return null;
	}

	// only for EmbedConverter
	@EmbedColor
	private int teamColor() {
		return this.team.getColor();
	}

	// only for EmbedConverter
	@EmbedAuthorIcon
	private String authorIcon() {
		return Starota.getUser(this.discordId).getAvatarURL();
	}

	// only for EmbedConverter
	@EmbedAuthorName
	private String authorText() {
		return Starota.getUser(this.discordId).getName();
	}

	// only for EmbedConverter
	@EmbedThumbnail
	private String thumbnail() {
		if (SilphRoadUtils.hasCard(this.poGoName))
			return SilphRoadUtils.getCardAvatar(this.poGoName);
		return this.team.getIcon();
	}

	// only for EmbedConverter
	@EmbedTimestamp
	private long timestamp() {
		return this.lastUpdated * 1000;
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
