package us.myles_selim.starota.lua.conversion;

import org.squiddev.cobalt.LuaError;
import org.squiddev.cobalt.LuaState;
import org.squiddev.cobalt.LuaValue;
import org.squiddev.cobalt.ValueFactory;

public class PrimitiveConverters {

	public static class BooleanConverter implements IConverter {

		@Override
		public LuaValue toLua(LuaState state, Object val) throws LuaError {
			return ValueFactory.valueOf((boolean) val);
		}

		@Override
		public Object toJava(LuaState state, LuaValue val) throws LuaError {
			return val.checkBoolean();
		}
	}

	public static class IntegerConverter implements IConverter {

		@Override
		public LuaValue toLua(LuaState state, Object val) throws LuaError {
			return ValueFactory.valueOf((int) val);
		}

		@Override
		public Object toJava(LuaState state, LuaValue val) throws LuaError {
			return val.checkInteger();
		}
	}

	public static class DoubleConverter implements IConverter {

		@Override
		public LuaValue toLua(LuaState state, Object val) throws LuaError {
			return ValueFactory.valueOf((double) val);
		}

		@Override
		public Object toJava(LuaState state, LuaValue val) throws LuaError {
			return val.checkDouble();
		}
	}

	public static class StringConverter implements IConverter {

		@Override
		public LuaValue toLua(LuaState state, Object val) throws LuaError {
			return ValueFactory.valueOf((String) val);
		}

		@Override
		public Object toJava(LuaState state, LuaValue val) throws LuaError {
			return val.checkString();
		}
	}

}
