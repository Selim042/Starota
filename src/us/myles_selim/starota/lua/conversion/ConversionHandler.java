package us.myles_selim.starota.lua.conversion;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.squiddev.cobalt.Constants;
import org.squiddev.cobalt.LuaBoolean;
import org.squiddev.cobalt.LuaError;
import org.squiddev.cobalt.LuaNumber;
import org.squiddev.cobalt.LuaState;
import org.squiddev.cobalt.LuaString;
import org.squiddev.cobalt.LuaTable;
import org.squiddev.cobalt.LuaUserdata;
import org.squiddev.cobalt.LuaValue;
import org.squiddev.cobalt.ValueFactory;
import org.squiddev.cobalt.function.OneArgFunction;
import org.squiddev.cobalt.lib.platform.AbstractResourceManipulator;

import us.myles_selim.starota.Starota;
import us.myles_selim.starota.lua.conversion.PrimitiveConverters.BooleanConverter;
import us.myles_selim.starota.lua.conversion.PrimitiveConverters.DoubleConverter;
import us.myles_selim.starota.lua.conversion.PrimitiveConverters.IntegerConverter;
import us.myles_selim.starota.lua.conversion.PrimitiveConverters.StringConverter;
import us.myles_selim.starota.lua.conversion.starota.PlayerProfileConverter;
import us.myles_selim.starota.profiles.PlayerProfile;

public class ConversionHandler {

	private static final Map<Class<?>, IConverter> CONVERTERS = new HashMap<>();

	static {
		registerConverter(Boolean.class, new BooleanConverter());
		registerConverter(boolean.class, new BooleanConverter());
		registerConverter(Integer.class, new IntegerConverter());
		registerConverter(int.class, new IntegerConverter());
		registerConverter(Double.class, new DoubleConverter());
		registerConverter(double.class, new DoubleConverter());
		registerConverter(String.class, new StringConverter());
	}

	public static void registerConverter(Class<?> clazz, IConverter conv) {
		if (!CONVERTERS.containsKey(clazz))
			CONVERTERS.put(clazz, conv);
	}

	public static void registerAutoConverter(Class<?> clazz) {
		registerConverter(clazz, AutoConverter.INSTANCE);
	}

	public static boolean hasConverter(Class<?> clazz) {
		if (clazz == null || clazz.equals(void.class) || clazz.equals(Void.class))
			return true;
		return CONVERTERS.containsKey(clazz);
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
			if (shouldOverrideMetatable(val)) {
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
			}
			return val;
		} catch (LuaError e) {
			Starota.submitError(e);
		}
		return null;
	}

	private static boolean shouldOverrideMetatable(LuaValue value) {
		return value instanceof LuaTable || value instanceof LuaUserdata;
	}

	public static Object convertToJava(LuaState state, LuaValue val) {
		if (val == null || val == Constants.NIL)
			return null;
		try {
			Class<?> clazz;
			if (val instanceof LuaBoolean)
				clazz = Boolean.class;
			else if (val instanceof LuaNumber) {
				if (val.isIntExact())
					clazz = Integer.class;
				else
					clazz = Double.class;
			} else if (val instanceof LuaString)
				clazz = String.class;
			else
				clazz = null;
			if (clazz == null) {
				LuaTable mt = val.getMetatable(state);
				String javaType = mt.rawget("javaType").toString();
				if (javaType.equals("nil"))
					clazz = null;
				else if (clazz == null)
					clazz = Class.forName(javaType);
			}
			if (!CONVERTERS.containsKey(clazz))
				return null;
			IConverter conv = CONVERTERS.get(clazz);
			return conv.toJava(state, val);
		} catch (ClassNotFoundException | LuaError e) {
			Starota.submitError(e);
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
