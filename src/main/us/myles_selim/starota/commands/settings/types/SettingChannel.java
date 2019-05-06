package us.myles_selim.starota.commands.settings.types;

import discord4j.core.object.entity.Channel;
import discord4j.core.object.entity.TextChannel;
import discord4j.core.object.util.Snowflake;
import us.myles_selim.ebs.Storage;
import us.myles_selim.starota.misc.utils.MiscUtils;
import us.myles_selim.starota.wrappers.StarotaServer;

public class SettingChannel extends ServerSetting<TextChannel> {

	public SettingChannel(StarotaServer server, String name) {
		super(server, name);
	}

	public SettingChannel(StarotaServer server, String name, String desc) {
		super(server, name, desc);
	}

	public SettingChannel(StarotaServer server, String name, TextChannel value) {
		super(server, name, value);
	}

	public SettingChannel(StarotaServer server, String name, String desc, TextChannel value) {
		super(server, name, value);
	}

	public SettingChannel(SettingChannel setting) {
		super(setting);
	}

	public SettingChannel(StarotaServer server, SettingChannel setting) {
		super(server, setting);
	}

	@Override
	protected TextChannel getEmptyValue() {
		return null;
	}

	@Override
	public boolean setValue(String str) {
		if (this.getServer() == null)
			throw new IllegalArgumentException("server isn't set?");
		if (str.startsWith("#")) {
			for (Channel ch : MiscUtils.getChannelByName(this.getServer().getDiscordGuild(),
					str.substring(1, str.length()))) {
				return this.setValue(ch);
			}
		} else if (str.matches("<#[0-9]{18}>")) {
			try {
				return this.setValue(this.getServer().getDiscordGuild()
						.getChannelById(Snowflake.of(str.substring(2, str.length() - 1))));
			} catch (NumberFormatException e) {
				return false;
			}
		}
		return false;
	}

	@Override
	public Class<TextChannel> getType() {
		return TextChannel.class;
	}

	@Override
	public void toBytes(Storage stor) {
		super.toBytes(stor);
		if (getValue() == null)
			stor.writeLong(-1);
		else
			stor.writeLong(getValue().getId().asLong());
	}

	@Override
	public void fromBytes(Storage stor) {
		super.fromBytes(stor);
		long ch = stor.readLong();
		if (ch == -1)
			this.setValue(null);
		else
			this.setValue(this.getServer().getDiscordGuild().getChannelById(Snowflake.of(ch)));
	}

}
