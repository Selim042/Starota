package us.myles_selim.starota.lua.conversion;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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

		Map<String, List<Method>> mm = new HashMap<>();
		for (Method m : clazz.getMethods()) {
			if (hasNecessaryConverters(m) && !isHiddenMethod(m)) {
				if (!mm.containsKey(m.getName()))
					mm.put(m.getName(), new ArrayList<>());
				mm.get(m.getName()).add(m);
			}
		}

		for (Entry<String, List<Method>> e : mm.entrySet())
			methods.rawset(e.getKey(), getJavaFunction(state, val, e.getValue()));
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
	}

	private LuaValue getJavaFunction(LuaState state, Object obj, List<Method> ml) {
		LuaUserdata data = new LuaUserdata(null);
		JavaFunction func = new JavaFunction(obj, ml);
		LuaTable mt = new LuaTable();
		LuaTable helpMethod = new LuaTable();
		helpMethod.rawset("help", new ZeroArgFunction() {

			@Override
			public LuaValue call(LuaState state) throws LuaError {
				LuaTable help = new LuaTable();
				int index = 1;
				LuaTable op = new LuaTable();
				for (Method m : ml) {
					LuaTable p = new LuaTable();
					Class<?>[] types = m.getParameterTypes();
					for (int i = 0; i < types.length; i++)
						p.rawset(i + 1, ConversionHandler.convertToLua(state, types[i].getName()));
					op.rawset(index++, p);
				}
				help.rawset("params", op);
				help.rawset("returnType", ValueFactory.valueOf(ml.get(0).getReturnType().getName()));
				return help;
			}
		});
		mt.rawset("__index", helpMethod);
		data.setMetatable(state, mt);
		mt.rawset("__call", func);
		return data;
	}

	private class JavaFunction extends VarArgFunction {

		private final Object inst;
		private final List<Method> methods;

		public JavaFunction(Object inst, List<Method> methods) {
			this.inst = inst;
			this.methods = methods;
		}

		@Override
		public Varargs invoke(LuaState state, Varargs args) throws LuaError {
			args = args.subargs(2);
			int luaArgCount = args.count();
			if (luaArgCount >= 1 && (args.first().isNil())
					|| (args instanceof LuaUserdata && ((LuaUserdata) args).instance == null))
				luaArgCount--;
			Object[] params;
			if (luaArgCount != 0) {
				params = new Object[args.count()];
				for (int i = 1; i <= args.count(); i++)
					params[i - 1] = ConversionHandler.convertToJava(state, args.arg(i));
			} else
				params = new Object[0];
			Method m = findMethod(params);
			if (m == null)
				throw new LuaError("invalid number of arguments, " + luaArgCount);
			try {
				return ConversionHandler.convertToLua(state, m.invoke(inst, params));
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
				return Constants.NIL;
			}
		}

		private Method findMethod(Object[] params) {
			for (Method m : methods) {
				Class<?>[] types = m.getParameterTypes();
				if (params.length != types.length)
					continue;
				if (params.length == 0)
					return m;
				boolean found = true;
				for (int i = 0; i < types.length && i < params.length; i++)
					if (!types[i].isInstance(params[i])) {
						found = false;
						break;
					}
				if (found)
					return m;
			}
			return null;
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
