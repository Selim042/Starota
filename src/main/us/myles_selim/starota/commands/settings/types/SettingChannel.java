package us.myles_selim.starota.commands.settings.types;

import sx.blah.discord.handle.obj.IChannel;
import us.myles_selim.ebs.Storage;
import us.myles_selim.starota.misc.data_types.NullChannel;
import us.myles_selim.starota.wrappers.StarotaServer;

public class SettingChannel extends ServerSetting<IChannel> {

	public SettingChannel(StarotaServer server, String name) {
		super(server, name);
	}

	public SettingChannel(StarotaServer server, String name, IChannel value) {
		super(server, name, value);
	}

	public SettingChannel(SettingChannel setting) {
		super(setting);
	}

	public SettingChannel(StarotaServer server, SettingChannel setting) {
		super(server, setting);
	}

	@Override
	protected IChannel getEmptyValue() {
		return NullChannel.NULL_CHANNEL;
	}

	@Override
	public boolean setValue(String str) {
		if (this.getServer() == null)
			throw new IllegalArgumentException("server isn't set?");
		if (str.startsWith("#")) {
			for (IChannel ch : this.getServer().getDiscordGuild()
					.getChannelsByName(str.substring(1, str.length()))) {
				return this.setValue(ch);
			}
		} else if (str.matches("<#[0-9]{18}>")) {
			try {
				return this.setValue(this.getServer().getDiscordGuild()
						.getChannelByID(Long.parseLong(str.substring(2, str.length() - 1))));
			} catch (NumberFormatException e) {
				return false;
			}
		}
		return false;
	}

	@Override
	public Class<IChannel> getType() {
		return IChannel.class;
	}

	@Override
	public void toBytes(Storage stor) {
		super.toBytes(stor);
		stor.writeLong(getValue().getLongID());
	}

	@Override
	public void fromBytes(Storage stor) {
		super.fromBytes(stor);
		long ch = stor.readLong();
		if (ch == 0)
			this.setValue(NullChannel.NULL_CHANNEL);
		else
			this.setValue(this.getServer().getDiscordGuild().getCategoryByID(stor.readLong()));
	}

}
