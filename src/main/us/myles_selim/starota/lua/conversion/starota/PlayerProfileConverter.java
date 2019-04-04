package us.myles_selim.starota.lua.conversion.starota;

import java.util.Map.Entry;

import org.squiddev.cobalt.Constants;
import org.squiddev.cobalt.LuaError;
import org.squiddev.cobalt.LuaState;
import org.squiddev.cobalt.LuaTable;
import org.squiddev.cobalt.LuaUserdata;
import org.squiddev.cobalt.LuaValue;
import org.squiddev.cobalt.ValueFactory;
import org.squiddev.cobalt.function.OneArgFunction;
import org.squiddev.cobalt.function.ZeroArgFunction;

import us.myles_selim.starota.lua.conversion.IConverter;
import us.myles_selim.starota.misc.utils.MiscUtils;
import us.myles_selim.starota.profiles.PlayerProfile;

public class PlayerProfileConverter implements IConverter {

	@Override
	public LuaValue toLua(LuaState state, Object val) {
		if (!(val instanceof PlayerProfile))
			return Constants.NIL;
		PlayerProfile prof = (PlayerProfile) val;
		LuaTable methods = new LuaTable();
		methods.rawset("getRealName", new ZeroArgFunction() {

			@Override
			public LuaValue call(LuaState state) throws LuaError {
				if (prof.getRealName() == null)
					return Constants.NIL;
				return ValueFactory.valueOf(prof.getRealName());
			}
		});
		methods.rawset("setRealName", new OneArgFunction() {

			@Override
			public LuaValue call(LuaState state, LuaValue arg) throws LuaError {
				return toLua(state, prof.setRealName(arg.toString()));
			}
		});
		methods.rawset("getPoGoName", new ZeroArgFunction() {

			@Override
			public LuaValue call(LuaState state) throws LuaError {
				if (prof.getPoGoName() == null)
					return Constants.NIL;
				return ValueFactory.valueOf(prof.getPoGoName());
			}
		});
		methods.rawset("setPoGoName", new OneArgFunction() {

			@Override
			public LuaValue call(LuaState state, LuaValue arg) throws LuaError {
				return toLua(state, prof.setPoGoName(arg.toString()));
			}
		});
		methods.rawset("getLevel", new ZeroArgFunction() {

			@Override
			public LuaValue call(LuaState state) throws LuaError {
				return ValueFactory.valueOf(prof.getLevel());
			}
		});
		methods.rawset("setLevel", new OneArgFunction() {

			@Override
			public LuaValue call(LuaState state, LuaValue arg) throws LuaError {
				if (!arg.isIntExact())
					return toLua(state, prof);
				return toLua(state, prof.setLevel(arg.toInteger()));
			}
		});
		methods.rawset("setTrainerCode", new OneArgFunction() {

			@Override
			public LuaValue call(LuaState state, LuaValue arg) throws LuaError {
				return toLua(state, prof.setTrainerCode(Long.parseLong(arg.toString())));
			}
		});
		methods.rawset("getTrainerCode", new ZeroArgFunction() {

			@Override
			public LuaValue call(LuaState state) throws LuaError {
				if (prof.getTrainerCode() == -1)
					return Constants.NIL;
				return ValueFactory.valueOf(prof.getTrainerCodeString());
			}
		});
		methods.rawset("getTeam", new ZeroArgFunction() {

			@Override
			public LuaValue call(LuaState state) throws LuaError {
				return ValueFactory.valueOf(prof.getTeam().getName());
			}
		});
		methods.rawset("getAlts", new ZeroArgFunction() {

			@Override
			public LuaValue call(LuaState state) throws LuaError {
				LuaTable ret = new LuaTable();
				for (Entry<String, Long> a : prof.getAlts().entrySet())
					ret.rawset(ValueFactory.valueOf(a.getKey()),
							ValueFactory.valueOf(MiscUtils.getTrainerCodeString(a.getValue())));
				return ret;
			}
		});
		methods.rawset("getLastUpdatedString", new ZeroArgFunction() {

			@Override
			public LuaValue call(LuaState state) throws LuaError {
				return ValueFactory.valueOf(prof.getLastUpdated().toString());
			}
		});
		methods.rawset("getLastUpdatedEpoch", new ZeroArgFunction() {

			@Override
			public LuaValue call(LuaState state) throws LuaError {
				return ValueFactory.valueOf(prof.getLastUpdated().getEpochSecond());
			}
		});
		LuaTable mt = new LuaTable();
		mt.rawset("__index", methods);
		return new LuaUserdata(prof, mt);
	}

	@Override
	public Object toJava(LuaState state, LuaValue val) throws LuaError {
		if (val instanceof LuaUserdata)
			return ((LuaUserdata) val).instance;
		return null;
	}

}
