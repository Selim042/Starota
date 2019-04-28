package us.myles_selim.starota.misc.data_types;

import sx.blah.discord.handle.obj.IChannel;
import us.myles_selim.ebs.DataType;
import us.myles_selim.ebs.Storage;
import us.myles_selim.starota.Starota;

public class ChannelDataType extends DataType<IChannel> {

	private IChannel value;

	public ChannelDataType() {
		value = NullChannel.NULL_CHANNEL;
	}

	public ChannelDataType(IChannel value) {
		this.value = value;
	}

	@Override
	public IChannel getValue() {
		return this.value;
	}

	@Override
	public void setValue(IChannel value) {
		this.value = value;
	}

	@Override
	protected void setValueObject(Object value) {
		if (this.acceptsValue(value))
			this.value = (IChannel) value;
	}

	@Override
	public Class<?>[] accepts() {
		return new Class[] { IChannel.class };
	}

	@Override
	public void toBytes(Storage stor) {
		if (this.value.getLongID() == -1) {
			stor.writeLong(-1);
			return;
		}
		stor.writeLong(this.value.getGuild().getLongID());
		stor.writeLong(this.value.getLongID());
	}

	@Override
	public void fromBytes(Storage stor) {
		long sId = stor.readLong();
		if (sId == -1) {
			this.value = NullChannel.NULL_CHANNEL;
			return;
		}
		this.value = Starota.getGuild(sId).getChannelByID(stor.readLong());
	}

}
