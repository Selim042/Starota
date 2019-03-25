package us.myles_selim.starota.lua.events;

import org.squiddev.cobalt.LuaState;
import org.squiddev.cobalt.LuaValue;

import us.myles_selim.starota.wrappers.StarotaServer;

public abstract class LuaEvent {

	private final StarotaServer server;

	public LuaEvent(StarotaServer server) {
		this.server = server;
	}

	public StarotaServer getServer() {
		return this.server;
	}

	public abstract LuaValue toLua(LuaState state);

}
