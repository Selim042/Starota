package us.myles_selim.starota.misc.data_types;

import discord4j.core.object.entity.TextChannel;
import discord4j.core.object.util.Snowflake;
import us.myles_selim.ebs.DataType;
import us.myles_selim.ebs.Storage;
import us.myles_selim.starota.Starota;

public class ChannelDataType extends DataType<TextChannel> {

	private TextChannel value;

	public ChannelDataType() {
		value = null;
	}

	public ChannelDataType(TextChannel value) {
		this.value = value;
	}

	@Override
	public TextChannel getValue() {
		return this.value;
	}

	@Override
	public void setValue(TextChannel value) {
		this.value = value;
	}

	@Override
	protected void setValueObject(Object value) {
		if (this.acceptsValue(value))
			this.value = (TextChannel) value;
	}

	@Override
	public Class<?>[] accepts() {
		return new Class[] { TextChannel.class };
	}

	@Override
	public void toBytes(Storage stor) {
		if (this.value == null) {
			stor.writeLong(-1);
			return;
		}
		stor.writeLong(this.value.getGuild().block().getId().asLong());
		stor.writeLong(this.value.getId().asLong());
	}

	@Override
	public void fromBytes(Storage stor) {
		long sId = stor.readLong();
		if (sId == -1) {
			this.value = null;
			return;
		}
		this.value = (TextChannel) Starota.getGuild(sId).getChannelById(Snowflake.of(stor.readLong()))
				.block();
	}

}
