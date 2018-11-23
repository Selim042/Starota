package us.myles_selim.starota.lua.conversion.discord;

import java.awt.Color;

import org.squiddev.cobalt.Constants;
import org.squiddev.cobalt.LuaError;
import org.squiddev.cobalt.LuaState;
import org.squiddev.cobalt.LuaTable;
import org.squiddev.cobalt.LuaUserdata;
import org.squiddev.cobalt.LuaValue;
import org.squiddev.cobalt.ValueFactory;
import org.squiddev.cobalt.function.OneArgFunction;
import org.squiddev.cobalt.function.ZeroArgFunction;

import sx.blah.discord.handle.obj.IRole;
import us.myles_selim.starota.lua.conversion.IConverter;

public class RoleConverter implements IConverter {

	@Override
	public LuaValue toLua(LuaState state, Object val) throws LuaError {
		if (!(val instanceof IRole))
			return Constants.NIL;
		IRole role = (IRole) val;
		LuaTable methods = new LuaTable();
		methods.rawset("getName", new ZeroArgFunction() {

			@Override
			public LuaValue call(LuaState state) throws LuaError {
				return ValueFactory.valueOf(role.getName());
			}
		});
		methods.rawset("getColor", new ZeroArgFunction() {

			@Override
			public LuaValue call(LuaState state) throws LuaError {
				return ValueFactory.valueOf(role.getColor().getRGB());
			}
		});
		methods.rawset("changeColor", new OneArgFunction() {

			@Override
			public LuaValue call(LuaState state, LuaValue arg) throws LuaError {
				if (arg == Constants.NIL || !arg.isIntExact())
					throw new LuaError("arg must be an int");
				role.changeColor(new Color(arg.toInteger()));
				return Constants.NIL;
			}
		});
		methods.rawset("changeName", new OneArgFunction() {

			@Override
			public LuaValue call(LuaState state, LuaValue arg) throws LuaError {
				if (arg == Constants.NIL || arg.toString().isEmpty())
					return Constants.FALSE;
				role.changeName(arg.toString());
				return Constants.TRUE;
			}
		});
		LuaTable mt = new LuaTable();
		mt.rawset("__index", methods);
		return new LuaUserdata(role, mt);
	}

	@Override
	public Object toJava(LuaState state, LuaValue val) throws LuaError {
		if (val instanceof LuaUserdata)
			return ((LuaUserdata) val).instance;
		return null;
	}

}
