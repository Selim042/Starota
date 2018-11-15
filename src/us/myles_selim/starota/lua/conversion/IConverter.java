package us.myles_selim.starota.lua.conversion;

import org.squiddev.cobalt.LuaError;
import org.squiddev.cobalt.LuaState;
import org.squiddev.cobalt.LuaValue;

public interface IConverter {

	public LuaValue toLua(LuaState state, Object val) throws LuaError;

	public Object toJava(LuaState state, LuaValue val) throws LuaError;

}
