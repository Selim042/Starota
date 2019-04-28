package us.myles_selim.starota.commands.settings.types;

import us.myles_selim.ebs.DataType;
import us.myles_selim.ebs.Storage;
import us.myles_selim.starota.commands.settings.Setting;

public abstract class SettingDataType<V> extends Setting<V> {

	private final DataType<V> type;

	public SettingDataType(String name, DataType<V> type) {
		super(name);
		this.type = type;
	}

	@Override
	public V getValue() {
		return this.type.getValue();
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean setValue(Object newVal) {
		if (!type.acceptsValue(newVal))
			return false;
		type.setValue((V) newVal);
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Class<V> getType() {
		for (Class<?> c : type.accepts())
			if (c.equals(type.getValue().getClass()))
				return (Class<V>) c;
		throw new IllegalArgumentException("type doesn't have a compatible acceptable class");
	}

	@Override
	public void toBytes(Storage stor) {
		this.type.toBytes(stor);
	}

	@Override
	public void fromBytes(Storage stor) {
		this.type.fromBytes(stor);
	}

}
