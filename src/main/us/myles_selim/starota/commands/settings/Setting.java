package us.myles_selim.starota.commands.settings;

import java.lang.reflect.InvocationTargetException;

import javax.annotation.Nonnull;

import us.myles_selim.ebs.Storage;

public abstract class Setting<V> {

	private final String name;
	private final String desc;
	private V value;

	public Setting(String name) {
		this(name, (V) null);
	}

	public Setting(String name, String desc) {
		this(name, desc, null);
	}

	public Setting(String name, V value) {
		this.name = name;
		this.desc = null;
		if (!this.setValue(value))
			throw new IllegalArgumentException("value " + value + "not accepted");
	}

	public Setting(String name, String desc, V value) {
		this.name = name;
		this.desc = desc;
		if (!this.setValue(value))
			throw new IllegalArgumentException("value " + value + "not accepted");
	}

	public Setting(Setting<V> setting) {
		this.name = setting.name;
		this.desc = setting.desc;
		this.value = setting.value;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return this.desc;
	}

	@Nonnull
	public V getValue() {
		return value;
	}

	@SuppressWarnings("unchecked")
	public boolean setValue(Object newVal) {
		if (newVal == null) {
			this.value = getEmptyValue();
			return true;
		}
		if (!getType().isInstance(newVal))
			return false;
		this.value = (V) newVal;
		return true;
	}

	@Nonnull
	protected abstract V getEmptyValue();

	public abstract boolean setValue(String str);

	public abstract Class<V> getType();

	public abstract void toBytes(Storage stor);

	public abstract void fromBytes(Storage stor);

	@SuppressWarnings("unchecked")
	@Override
	public Setting<V> clone() {
		Class<?> clazz = this.getClass();
		try {
			return (Setting<V>) clazz.getConstructor(clazz).newInstance(this);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException | SecurityException e) {}
		throw new IllegalArgumentException(
				"Setting classes must have a public copy constructor, missing in " + clazz.getName());
	}

}
