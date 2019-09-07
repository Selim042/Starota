package us.myles_selim.starota.commands.settings.types;

import java.lang.reflect.InvocationTargetException;

import us.myles_selim.ebs.Storage;
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.commands.settings.Setting;
import us.myles_selim.starota.misc.data_types.BotServer;
import us.myles_selim.starota.wrappers.StarotaServer;

public abstract class ServerSetting<V> extends Setting<V> {

	private BotServer server;

	public ServerSetting(BotServer server, String name) {
		super(name);
		this.server = server;
	}

	public ServerSetting(BotServer server, String name, String desc) {
		super(name, desc);
		this.server = server;
	}

	public ServerSetting(BotServer server, String name, V value) {
		super(name, value);
		this.server = server;
	}

	public ServerSetting(BotServer server, String name, String desc, V value) {
		super(name, desc, value);
		this.server = server;
	}

	public ServerSetting(ServerSetting<V> setting) {
		super(setting);
		this.server = setting.server;
	}

	public ServerSetting(BotServer server, ServerSetting<V> setting) {
		super(setting);
		this.server = server;
	}

	public BotServer getServer() {
		return this.server;
	}

	@Override
	public void toBytes(Storage stor) {
		if (getServer() == null)
			stor.writeLong(-1);
		else
			stor.writeLong(getServer().getDiscordGuild().getId().asLong());
	}

	@Override
	public void fromBytes(Storage stor) {
		// TODO: this probably shouldn't be hard coded to Starota, will fail
		// with other bots
		long id = stor.readLong();
		if (id != -1)
			this.server = StarotaServer.getServer(Starota.getGuild(id));
	}

	@SuppressWarnings("unchecked")
	public ServerSetting<V> clone(BotServer server) {
		Class<?> clazz = this.getClass();
		try {
			return (ServerSetting<V>) clazz.getConstructor(BotServer.class, clazz).newInstance(server,
					this);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException | SecurityException e) {
			throw new IllegalArgumentException(
					"ServerSetting classes must have a public copy constructor with BotServer "
							+ "as first param, missing in " + clazz.getName());
		}
	}

}
