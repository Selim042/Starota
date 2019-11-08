package us.myles_selim.starota.commands.settings.types;

import javax.annotation.Nonnull;

import us.myles_selim.ebs.Storage;
import us.myles_selim.starota.commands.settings.Setting;

public class SettingBoolean extends Setting<Boolean> {

	public SettingBoolean(String name) {
		super(name);
	}

	public SettingBoolean(String name, boolean value) {
		super(name, value);
	}

	public SettingBoolean(String name, String desc, boolean value) {
		super(name, desc, value);
	}

	public SettingBoolean(SettingBoolean setting) {
		super(setting.getName(), setting.getDescription(), new Boolean(setting.getValue()));
	}

	@Override
	public boolean setValue(String str) {
		return setValue(Boolean.valueOf(str));
	}

	@Override
	public Class<Boolean> getType() {
		return Boolean.class;
	}

	@Override
	public void toBytes(Storage stor) {
		stor.writeBoolean(getValue());
	}

	@Override
	public void fromBytes(Storage stor) {
		this.setValue(stor.readBoolean());
	}

	@Nonnull
	@Override
	protected Boolean getEmptyValue() {
		return false;
	}

}
