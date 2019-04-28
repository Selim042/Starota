package us.myles_selim.starota.commands.settings.types;

import javax.annotation.Nonnull;

import us.myles_selim.ebs.Storage;
import us.myles_selim.starota.commands.settings.Setting;

public class SettingInteger extends Setting<Integer> {

	public SettingInteger(String name) {
		super(name);
	}

	public SettingInteger(String name, int value) {
		super(name, value);
	}

	public SettingInteger(SettingInteger setting) {
		super(setting.getName(), new Integer(setting.getValue()));
	}

	@Override
	public boolean setValue(String str) {
		return setValue(Integer.valueOf(str));
	}

	@Override
	public Class<Integer> getType() {
		return Integer.class;
	}

	@Override
	public void toBytes(Storage stor) {
		stor.writeInt(getValue());
	}

	@Override
	public void fromBytes(Storage stor) {
		this.setValue(stor.readInt());
	}

	@Nonnull
	@Override
	protected Integer getEmptyValue() {
		return -1;
	}

}
