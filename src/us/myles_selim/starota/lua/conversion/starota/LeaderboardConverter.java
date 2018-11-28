package us.myles_selim.starota.lua.conversion.starota;

import java.util.List;

import org.squiddev.cobalt.Constants;
import org.squiddev.cobalt.LuaError;
import org.squiddev.cobalt.LuaState;
import org.squiddev.cobalt.LuaTable;
import org.squiddev.cobalt.LuaUserdata;
import org.squiddev.cobalt.LuaValue;
import org.squiddev.cobalt.ValueFactory;
import org.squiddev.cobalt.function.ZeroArgFunction;

import us.myles_selim.starota.leaderboards.Leaderboard;
import us.myles_selim.starota.leaderboards.LeaderboardEntry;
import us.myles_selim.starota.lua.conversion.ConversionHandler;
import us.myles_selim.starota.lua.conversion.IConverter;

public class LeaderboardConverter implements IConverter {

	@Override
	public LuaValue toLua(LuaState state, Object val) {
		if (!(val instanceof Leaderboard))
			return Constants.NIL;
		Leaderboard board = (Leaderboard) val;
		LuaTable methods = new LuaTable();
		methods.rawset("getDisplayName", new ZeroArgFunction() {

			@Override
			public LuaValue call(LuaState state) throws LuaError {
				return ValueFactory.valueOf(board.getDisplayName());
			}
		});
		methods.rawset("getColor", new ZeroArgFunction() {

			@Override
			public LuaValue call(LuaState state) throws LuaError {
				return ValueFactory.valueOf(board.getColor());
			}
		});
		methods.rawset("getEntries", new ZeroArgFunction() {

			@Override
			public LuaValue call(LuaState state) throws LuaError {
				LuaTable ret = new LuaTable();
				List<LeaderboardEntry> entries = board.getEntries();
				for (int i = 0; i < entries.size(); i++) {
					LuaTable tbl = new LuaTable();
					LeaderboardEntry entry = entries.get(i);
					tbl.rawset("user", ConversionHandler.convertToLua(state, entry.getDiscordUser()));
					tbl.rawset("value", ValueFactory.valueOf(entry.getValue()));
					ret.rawset(i, tbl);
				}
				return ret;
			}
		});
		methods.rawset("isDecending", new ZeroArgFunction() {

			@Override
			public LuaValue call(LuaState state) throws LuaError {
				return ValueFactory.valueOf(board.isDecending());
			}
		});
		methods.rawset("isActive", new ZeroArgFunction() {

			@Override
			public LuaValue call(LuaState state) throws LuaError {
				return ValueFactory.valueOf(board.isActive());
			}
		});
		LuaTable mt = new LuaTable();
		mt.rawset("__index", methods);
		return new LuaUserdata(board, mt);
	}

	@Override
	public Object toJava(LuaState state, LuaValue val) throws LuaError {
		if (val instanceof LuaUserdata)
			return ((LuaUserdata) val).instance;
		return null;
	}

}
