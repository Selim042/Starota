package us.myles_selim.starota.lua.events;

import org.squiddev.cobalt.LuaState;
import org.squiddev.cobalt.LuaValue;

import sx.blah.discord.handle.obj.IGuild;

public abstract class LuaEvent {

	private final IGuild server;

	public LuaEvent(IGuild server) {
		this.server = server;
	}

	public IGuild getServer() {
		return this.server;
	}

	public abstract LuaValue toLua(LuaState state);

}
