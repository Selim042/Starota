package us.myles_selim.starota.commands.settings.types;

import java.lang.reflect.InvocationTargetException;

import discord4j.core.object.util.Snowflake;
import us.myles_selim.ebs.Storage;
import us.myles_selim.starota.commands.settings.Setting;
import us.myles_selim.starota.wrappers.BotServer;

public abstract class ServerSetting<V> extends Setting<V> {

	private Snowflake server;

	public ServerSetting(Snowflake server, String name) {
		super(name);
		this.server = server;
		this.setValue(server, (V) null);
	}

	public ServerSetting(Snowflake server, String name, String desc) {
		super(name, desc);
		this.server = server;
		this.setValue(server, (V) null);
	}

	public ServerSetting(Snowflake server, String name, V value) {
		super(name, value);
		this.server = server;
		this.setValue(server, value);
	}

	public ServerSetting(Snowflake server, String name, String desc, V value) {
		super(name, desc, value);
		this.server = server;
		this.setValue(server, value);
	}

	public ServerSetting(ServerSetting<V> setting) {
		super(setting);
		this.server = setting.server;
		this.setValue(server, setting.getValue());
	}

	public ServerSetting(Snowflake server, ServerSetting<V> setting) {
		super(setting);
		this.server = server;
		this.setValue(server, setting.getValue());
	}

	public Snowflake getServer() {
		return this.server;
	}

	@Deprecated
	@Override
	public final boolean setValue(String value) {
		throw new UnsupportedOperationException(
				"call ServerSetting#setValue(Snowflake server, String value) instead");
	}

	public abstract boolean setValue(Snowflake server, String value);

	@Deprecated
	@Override
	public final boolean setValue(Object newVal) {
		throw new UnsupportedOperationException(
				"call ServerSetting#setValue(Snowflake server, Object newValue) instead");
	}

	public boolean setValue(Snowflake server, Object newVal) {
		return setValueInternal(newVal);
	}

	@Deprecated
	@Override
	public final String getValueString() {
		throw new UnsupportedOperationException(
				"call ServerSetting#getValueString(BotServer server) instead");
	}

	public abstract String getValueString(BotServer server);

	@Override
	public void toBytes(Storage stor) {
		if (getServer() == null)
			stor.writeLong(-1);
		else
			stor.writeLong(server.asLong());
	}

	@Override
	public void fromBytes(Storage stor) {
		long id = stor.readLong();
		if (id != -1)
			this.server = Snowflake.of(id);
	}

	@SuppressWarnings("unchecked")
	public ServerSetting<V> clone(Snowflake server) {
		Class<?> clazz = this.getClass();
		try {
			return (ServerSetting<V>) clazz.getConstructor(Snowflake.class, clazz).newInstance(server,
					this);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException | SecurityException e) {
			throw new IllegalArgumentException(
					"ServerSetting classes must have a public copy constructor with Snowflake "
							+ "as first param, missing in " + clazz.getName(),
					e);
		}
	}

}
