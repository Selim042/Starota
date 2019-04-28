package us.myles_selim.starota.commands.settings.types;

import javax.annotation.Nonnull;

import us.myles_selim.ebs.Storage;
import us.myles_selim.starota.commands.settings.Setting;

public class SettingString extends Setting<String> {

	public SettingString(String name) {
		super(name);
	}

	public SettingString(String name, String value) {
		super(name, value);
	}

	public SettingString(SettingString setting) {
		super(setting.getName(), new String(setting.getValue()));
	}

	@Override
	public boolean setValue(String str) {
		return setValue((Object) str);
	}

	@Override
	public Class<String> getType() {
		return String.class;
	}

	@Override
	public void toBytes(Storage stor) {
		stor.writeString(this.getValue());
	}

	@Override
	public void fromBytes(Storage stor) {
		this.setValue(stor.readString());
	}

	@Nonnull
	@Override
	protected String getEmptyValue() {
		return "null";
	}

}
