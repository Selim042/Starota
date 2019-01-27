package us.myles_selim.starota.lua.libraries;

import org.squiddev.cobalt.ErrorFactory;
import org.squiddev.cobalt.LuaError;
import org.squiddev.cobalt.LuaState;
import org.squiddev.cobalt.LuaTable;
import org.squiddev.cobalt.LuaValue;
import org.squiddev.cobalt.ValueFactory;
import org.squiddev.cobalt.function.LuaFunction;
import org.squiddev.cobalt.function.OneArgFunction;
import org.squiddev.cobalt.lib.LuaLibrary;

import sx.blah.discord.Discord4J;
import sx.blah.discord.util.RequestBuffer;
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
		env.rawset("_DISCORD4J", ValueFactory.valueOf(Discord4J.VERSION));
		env.rawset("discord", ConversionHandler.convertToLua(state, getServer()));
		env.rawset("requestBuffer", new OneArgFunction() {

			@Override
			public LuaValue call(LuaState state, LuaValue arg) throws LuaError {
				if (!arg.isFunction())
					throw ErrorFactory.argError(arg, "function");
				return ConversionHandler.convertToLua(state,
						RequestBuffer.request(() -> wrappedFunctionCall(state, (LuaFunction) arg)));
			}
		});
		return env;
	}

	private static void wrappedFunctionCall(LuaState state, LuaFunction func) {
		try {
			func.call(state);
		} catch (LuaError e) {
			throw new RuntimeException(e);
		}
	}

}
