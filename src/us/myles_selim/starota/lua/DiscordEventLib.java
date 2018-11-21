package us.myles_selim.starota.lua;

import org.squiddev.cobalt.LuaError;
import org.squiddev.cobalt.LuaState;
import org.squiddev.cobalt.LuaTable;
import org.squiddev.cobalt.LuaValue;
import org.squiddev.cobalt.function.LuaFunction;

import sx.blah.discord.handle.impl.events.guild.GuildEvent;
import sx.blah.discord.handle.obj.IGuild;
import us.myles_selim.starota.lua.libraries.DiscordLib;

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
		// eventHandler.rawset("test", new EmptyFunction());
		return env;
	}

	protected static void handleEvent(GuildEvent event) {
		IGuild server = event.getGuild();
		LuaState state = LuaUtils.getState(server);
		LuaTable _G = state.getMainThread().getfenv();
		try {
			LuaTable eventLib = _G.rawget(DiscordEventLib.KEY).checkTable();
			String functName = "on" + event.getClass().getSimpleName();
			LuaValue funcV = eventLib.get(state, functName);
			for (LuaValue v : eventLib.keys()) {
				System.out.println(v);
			}
			System.out.println(functName);
			if (funcV == null || !funcV.isFunction())
				return;
			((LuaFunction) funcV).call(state, eventLib, LuaUtils.getEvent(state, event));
		} catch (LuaError e) {}
	}

}
