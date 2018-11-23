package us.myles_selim.starota.lua.libraries;

import org.squiddev.cobalt.LuaState;
import org.squiddev.cobalt.LuaTable;
import org.squiddev.cobalt.LuaValue;

import sx.blah.discord.handle.obj.IGuild;
import us.myles_selim.starota.lua.ScriptManager;

public class DiscordEventLib extends DiscordLib {

	public static final String KEY = "events";

	public DiscordEventLib(IGuild server) {
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
