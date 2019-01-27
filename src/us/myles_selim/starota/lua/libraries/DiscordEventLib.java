package us.myles_selim.starota.lua.libraries;

import org.squiddev.cobalt.LuaState;
import org.squiddev.cobalt.LuaTable;
import org.squiddev.cobalt.LuaValue;

import us.myles_selim.starota.lua.ScriptManager;
import us.myles_selim.starota.wrappers.StarotaServer;

public class DiscordEventLib extends DiscordLib {

	public static final String KEY = "events";

	public DiscordEventLib(StarotaServer server) {
		super(server);
	}

	@Override
	public LuaValue add(LuaState state, LuaTable env) {
		super.add(state, env);
		LuaTable eventHandler = new LuaTable();
		env.rawset(KEY, eventHandler);
		ScriptManager.executeEventScript(state, getServer());
		return env;
	}

}
