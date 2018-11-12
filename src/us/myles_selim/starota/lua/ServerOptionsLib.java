package us.myles_selim.starota.lua;

import org.squiddev.cobalt.Constants;
import org.squiddev.cobalt.LuaError;
import org.squiddev.cobalt.LuaNil;
import org.squiddev.cobalt.LuaState;
import org.squiddev.cobalt.LuaTable;
import org.squiddev.cobalt.LuaValue;
import org.squiddev.cobalt.ValueFactory;
import org.squiddev.cobalt.function.OneArgFunction;
import org.squiddev.cobalt.function.TwoArgFunction;
import org.squiddev.cobalt.lib.LuaLibrary;

import sx.blah.discord.handle.obj.IGuild;
import us.myles_selim.ebs.EBStorage;
import us.myles_selim.starota.ServerOptions;
import us.myles_selim.starota.commands.CommandChangelogChannel;
import us.myles_selim.starota.trading.Tradeboard;

public class ServerOptionsLib implements LuaLibrary {

	private final IGuild server;

	public ServerOptionsLib(IGuild server) {
		this.server = server;
	}

	@Override
	public LuaValue add(LuaState state, LuaTable env) {
		env.rawset("options", storageToValue(ServerOptions.getOptions(server), Tradeboard.TRADE_ID_KEY,
				CommandChangelogChannel.CHANGES_CHANNEL, "changesVersion"));
		return env;
	}

	public static LuaValue storageToValue(EBStorage stor, String... lockedKeys) {
		LuaTable options = new LuaTable();
		// options.rawset("getKeys", new ZeroArgFunction() {
		//
		// @Override
		// public LuaValue call(LuaState state) throws LuaError {
		// LuaTable ret = new LuaTable();
		// int i = 0;
		// for (String k : stor.getKeys())
		// ret.rawset(i++, ValueFactory.valueOf(k));
		// return ret;
		// }
		// });
		options.rawset("hasKey", new OneArgFunction() {

			@Override
			public LuaValue call(LuaState state, LuaValue arg) throws LuaError {
				if (arg instanceof LuaNil)
					return Constants.NIL;
				return ValueFactory.valueOf(stor.containsKey(arg.toString()));
			}
		});
		options.rawset("getValue", new OneArgFunction() {

			@Override
			public LuaValue call(LuaState state, LuaValue arg) throws LuaError {
				if (arg instanceof LuaNil)
					return Constants.NIL;
				return LuaUtils.objToValue(stor.get(arg.toString()));
			}
		});
		options.rawset("setValue", new TwoArgFunction() {

			@Override
			public LuaValue call(LuaState state, LuaValue arg1, LuaValue arg2) throws LuaError {
				if (arg1 instanceof LuaNil || arg1 instanceof LuaNil
						|| arrCont(lockedKeys, arg1.toString()))
					return Constants.NIL;
				stor.set(arg1.toString(), LuaUtils.valueToObj(arg2));
				return Constants.NIL;
			}
		});
		options.rawset("clearValue", new OneArgFunction() {

			@Override
			public LuaValue call(LuaState state, LuaValue arg) throws LuaError {
				if (arg instanceof LuaNil || arrCont(lockedKeys, arg.toString()))
					return Constants.NIL;
				stor.clearKey(arg.toString());
				return Constants.NIL;
			}
		});
		return options;
	}

	private static boolean arrCont(String[] sss, String ss) {
		for (String s : sss)
			if (s != null && s.equals(ss))
				return true;
		return false;
	}

}
