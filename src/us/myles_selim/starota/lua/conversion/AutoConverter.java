package us.myles_selim.starota.lua.conversion;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.squiddev.cobalt.Constants;
import org.squiddev.cobalt.LuaError;
import org.squiddev.cobalt.LuaState;
import org.squiddev.cobalt.LuaTable;
import org.squiddev.cobalt.LuaUserdata;
import org.squiddev.cobalt.LuaValue;
import org.squiddev.cobalt.ValueFactory;
import org.squiddev.cobalt.Varargs;
import org.squiddev.cobalt.function.LuaFunction;
import org.squiddev.cobalt.function.VarArgFunction;
import org.squiddev.cobalt.function.ZeroArgFunction;
import org.squiddev.cobalt.lib.jse.JsePlatform;
import org.squiddev.cobalt.lib.platform.AbstractResourceManipulator;

import us.myles_selim.starota.EnumTeam;
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.profiles.PlayerProfile;

public class AutoConverter implements IConverter {

	public static final AutoConverter INSTANCE = new AutoConverter();

	// private static final Method[] OBJECT_METHODS = Object.class.getMethods();

	@Override
	public LuaValue toLua(LuaState state, Object val) throws LuaError {
		LuaTable methods = new LuaTable();
		Class<?> clazz = val.getClass();

		for (Method m : clazz.getMethods())
			if (
			// m.isAccessible() &&
			hasNecessaryConverters(m) && !isHiddenMethod(m))
				methods.rawset(m.getName(), getJavaFunction(state, val, m));

		LuaTable mt = new LuaTable();
		mt.rawset("__index", methods);
		return new LuaUserdata(val, mt);

	}

	@Override
	public Object toJava(LuaState state, LuaValue val) throws LuaError {
		if (val instanceof LuaUserdata)
			return ((LuaUserdata) val).instance;
		return null;
	}

	private static boolean hasNecessaryConverters(Method m) {
		boolean hasConv = true;
		if (!ConversionHandler.hasConverter(m.getReturnType()))
			return false;
		for (Class<?> p : m.getParameterTypes()) {
			if (!ConversionHandler.hasConverter(p)) {
				hasConv = false;
				break;
			}
		}
		return hasConv;
	}

	private static boolean isHiddenMethod(Method m) {
		return m.getDeclaringClass().equals(Object.class);
		// for (Method m1 : OBJECT_METHODS)
		// if (m1.equals(m))
		// return true;
		// return false;
	}

	private LuaValue getJavaFunction(LuaState state, Object obj, Method m) {
		LuaUserdata data = new LuaUserdata(null);
		JavaFunction func = new JavaFunction(obj, m);
		LuaTable mt = new LuaTable();
		LuaTable helpMethod = new LuaTable();
		helpMethod.rawset("help", new ZeroArgFunction() {

			@Override
			public LuaValue call(LuaState state) throws LuaError {
				String help = "returnType: " + m.getReturnType();
				return ValueFactory.valueOf(help);
			}
		});
		mt.rawset("__index", helpMethod);
		data.setMetatable(state, mt);
		mt.rawset("__call", func);
		return data;
	}

	private class JavaFunction extends VarArgFunction {

		private final Object obj;
		private final Method m;

		public JavaFunction(Object obj, Method m) {
			this.obj = obj;
			this.m = m;
		}

		@Override
		public Varargs invoke(LuaState state, Varargs args) throws LuaError {
			Object[] params = new Object[args.count()];
			if (args.count() != 0) {
				for (int i = 1; i <= args.count(); i++) {
					Class<?> type = m.getParameterTypes()[i - 1];
					Object obj = ConversionHandler.convertToJava(state, args.arg(i));
					if (!type.isInstance(obj))
						throw new LuaError("arg" + i + " must be a " + type.getName() + ", got "
								+ (obj == null ? "nil" : obj.getClass().getName()));
					params[i - 1] = ConversionHandler.convertToJava(state, args.arg(i));
				}
			}
			try {
				return ConversionHandler.convertToLua(state, m.invoke(obj, params));
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
				return Constants.NIL;
			}
		}
	}

	public static void main(String... args) {
		Starota.IS_DEV = true;
		LuaState state = new LuaState(new AbstractResourceManipulator() {

			@Override
			public InputStream findResource(String filename) {
				return null;
			}
		});
		LuaTable globals = JsePlatform.standardGlobals(state);
		try {
			LuaFunction func = (LuaFunction) globals.get(state, "loadstring").checkFunction().call(state,
					ValueFactory.valueOf(
							"print('pairs:') for k,v in pairs(getmetatable(test)['__index']) do "
									+ "print('  '..k..': '..tostring(v)) end"));
			Object test = new PlayerProfile().setLevel(38).setPoGoName("042Selim").setRealName("Myles");
			ConversionHandler.registerAutoConverter(test.getClass());
			ConversionHandler.registerAutoConverter(EnumTeam.class);
			LuaValue v = ConversionHandler.convertToLua(state, test);
			globals.set(state, "test", v);
			System.out.println(v);
			System.out.println(ConversionHandler.convertToJava(state, v));
			if (func != null)
				func.call(state, v);
			else
				System.out.println("func is null");
		} catch (LuaError e) {
			e.printStackTrace();
		}
	}

}
