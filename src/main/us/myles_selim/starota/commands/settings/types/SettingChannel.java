package us.myles_selim.starota.commands.settings.types;

import sx.blah.discord.handle.obj.IChannel;
import us.myles_selim.ebs.Storage;
import us.myles_selim.starota.misc.data_types.BotServer;
import us.myles_selim.starota.misc.data_types.NullChannel;

public class SettingChannel extends ServerSetting<IChannel> {

	public SettingChannel(BotServer server, String name) {
		super(server, name);
	}

	public SettingChannel(BotServer server, String name, String desc) {
		super(server, name, desc);
	}

	public SettingChannel(BotServer server, String name, IChannel value) {
		super(server, name, value);
	}

	public SettingChannel(BotServer server, String name, String desc, IChannel value) {
		super(server, name, value);
	}

	public SettingChannel(SettingChannel setting) {
		super(setting);
	}

	public SettingChannel(BotServer server, SettingChannel setting) {
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
			for (IChannel ch : this.getServer().getChannelsByName(str.substring(1, str.length()))) {
				return this.setValue(ch);
			}
		} else if (str.matches("<#[0-9]{18}>")) {
			try {
				return this.setValue(this.getServer()
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
		if (getValue().equals(NullChannel.NULL_CHANNEL))
			stor.writeLong(-1);
		else
			stor.writeLong(getValue().getLongID());
	}

	@Override
	public void fromBytes(Storage stor) {
		super.fromBytes(stor);
		long ch = stor.readLong();
		if (ch == -1)
			this.setValue(NullChannel.NULL_CHANNEL);
		else
			this.setValue(this.getServer().getChannelByID(ch));
	}

}
