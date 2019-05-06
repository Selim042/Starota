package us.myles_selim.starota.lua.libraries;

import org.squiddev.cobalt.LuaState;
import org.squiddev.cobalt.LuaTable;
import org.squiddev.cobalt.LuaValue;
import org.squiddev.cobalt.lib.LuaLibrary;

import us.myles_selim.starota.lua.conversion.ConversionHandler;
import us.myles_selim.starota.wrappers.StarotaServer;

public class DiscordLib implements LuaLibrary {

	private final StarotaServer server;

	public DiscordLib(StarotaServer server) {
		this.server = server;
	}

	public StarotaServer getServer() {
		return server;
	}

	@Override
	public LuaValue add(LuaState state, LuaTable env) {
		// env.rawset("_DISCORD4J", ValueFactory.valueOf(Discord4J.VERSION));
		env.rawset("discord", ConversionHandler.convertToLua(state, getServer()));
		return env;
	}

}
