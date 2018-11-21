package us.myles_selim.starota.lua.conversion;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.squiddev.cobalt.Constants;
import org.squiddev.cobalt.LuaError;
import org.squiddev.cobalt.LuaState;
import org.squiddev.cobalt.LuaTable;
import org.squiddev.cobalt.LuaValue;
import org.squiddev.cobalt.ValueFactory;
import org.squiddev.cobalt.function.OneArgFunction;
import org.squiddev.cobalt.lib.platform.AbstractResourceManipulator;

import us.myles_selim.starota.lua.conversion.starota.PlayerProfileConverter;
import us.myles_selim.starota.profiles.PlayerProfile;

public class ConversionHandler {

	private static final Map<Class<?>, IConverter> CONVERTERS = new HashMap<>();

	public static void registerConverter(Class<?> clazz, IConverter conv) {
		if (!CONVERTERS.containsKey(clazz))
			CONVERTERS.put(clazz, conv);
	}

	public static LuaValue convertToLua(LuaState state, Object obj) {
		if (obj == null)
			return Constants.NIL;
		Class<?> clazz = obj.getClass();
		IConverter conv = CONVERTERS.get(clazz);
		if (conv == null)
			for (Class<?> c : CONVERTERS.keySet())
				if (c.isInstance(obj)) {
					conv = CONVERTERS.get(c);
					break;
				}
		if (conv == null)
			throw new IllegalArgumentException(
					"converter not registered for " + obj + " (" + clazz + ")");
		try {
			LuaValue val = conv.toLua(state, obj);
			LuaTable mt = val.getMetatable(state);
			if (mt == null)
				mt = new LuaTable();
			mt.set(state, "javaType", ValueFactory.valueOf(clazz.getName()));
			mt.set(state, "__metatable", new LuaTable());
			mt.set(state, "__tostring", new OneArgFunction() {

				@Override
				public LuaValue call(LuaState state, LuaValue arg) throws LuaError {
					return ValueFactory.valueOf(obj.toString());
				}
			});
			val.setMetatable(state, mt);
			return val;
		} catch (LuaError e) {}
		return null;
	}

	public static Object convertToJava(LuaState state, LuaValue val) {
		if (val == null || val == Constants.NIL)
			return null;
		try {
			LuaTable mt = val.getMetatable(state);
			if (mt == null)
				return null;
			Class<?> clazz = Class.forName(mt.rawget("javaType").toString());
			if (!CONVERTERS.containsKey(clazz))
				return null;
			IConverter conv = CONVERTERS.get(clazz);
			return conv.toJava(state, val);
		} catch (ClassNotFoundException | LuaError e) {
			return null;
		}
	}

	public static void main(String... args) {
		registerConverter(PlayerProfile.class, new PlayerProfileConverter());
		LuaState state = new LuaState(new AbstractResourceManipulator() {

			@Override
			public InputStream findResource(String filename) {
				return null;
			}
		});
		PlayerProfile prof = new PlayerProfile().setLevel(38).setPoGoName("042Selim");
		LuaValue profL = convertToLua(state, prof);
		System.out.println(profL);
		try {
			System.out.println(profL.get(state, "getPoGoName").checkFunction().call(state));
		} catch (LuaError e) {}
		System.out.println(convertToJava(state, profL));
	}

}
