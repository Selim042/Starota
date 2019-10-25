package us.myles_selim.starota.commands.settings.types;

import discord4j.core.object.entity.GuildChannel;
import discord4j.core.object.util.Snowflake;
import us.myles_selim.ebs.Storage;
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.misc.utils.MiscUtils;
import us.myles_selim.starota.wrappers.BotServer;

public class SettingChannelStarota extends ServerSetting<Snowflake> {

	public SettingChannelStarota(Snowflake server, String name) {
		super(server, name);
	}

	public SettingChannelStarota(Snowflake server, String name, String desc) {
		super(server, name, desc);
	}

	public SettingChannelStarota(Snowflake server, String name, Snowflake value) {
		super(server, name, value);
	}

	public SettingChannelStarota(Snowflake server, String name, String desc, Snowflake value) {
		super(server, name, value);
	}

	public SettingChannelStarota(SettingChannelStarota setting) {
		super(setting);
	}

	public SettingChannelStarota(Snowflake server, SettingChannelStarota setting) {
		super(server, setting);
	}

	@Override
	protected Snowflake getEmptyValue() {
		return null;
	}

	@Override
	public boolean setValue(Snowflake server, String str) {
		if (this.getServer() == null || str == null)
			return this.setValue(server, (Object) null);
		if (str.startsWith("#")) {
			for (GuildChannel ch : MiscUtils.getChannelsByName(Starota.getGuild(server),
					str.substring(1, str.length()))) {
				return this.setValue(server, ch);
			}
		} else if (str.matches("<#[0-9]{18}>")) {
			try {
				return this.setValue(server, Snowflake.of(str.substring(2, str.length() - 1)));
			} catch (NumberFormatException e) {
				return false;
			}
		}
		return false;
	}

	@Override
	public String getValueString(BotServer server) {
		Snowflake ch = getValue();
		if (ch == null)
			return "null";
		return server.getDiscordGuild().getChannelById(ch).block().getMention();
	}

	@Override
	public Class<Snowflake> getType() {
		return Snowflake.class;
	}

	@Override
	public void toBytes(Storage stor) {
		super.toBytes(stor);
		if (getValue() == null)
			stor.writeLong(-1);
		else
			stor.writeLong(getValue().asLong());
	}

	@Override
	public void fromBytes(Storage stor) {
		super.fromBytes(stor);
		long ch = stor.readLong();
		if (ch == -1)
			this.setValue((Snowflake) null, (String) null);
		else
			this.setValue(getServer(), Snowflake.of(ch));
	}

}
