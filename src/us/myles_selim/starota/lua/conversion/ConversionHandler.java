package us.myles_selim.starota.lua.conversion;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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

import us.myles_selim.starota.profiles.PlayerProfile;

public class ConversionHandler {

	private static final Map<Class<?>, IConverter> CONVERTERS = new HashMap<>();

	public static void registerConverter(Class<?> clazz, IConverter conv) {
		if (!CONVERTERS.containsKey(clazz))
			CONVERTERS.put(clazz, conv);
	}

	public static LuaValue convertToLua(LuaState state, Object obj) {
		Class<?> clazz = obj.getClass();
		if (!CONVERTERS.containsKey(clazz))
			return null;
		try {
			IConverter conv = CONVERTERS.get(clazz);
			LuaValue val = conv.toLua(state, obj);
			LuaTable mt = val.getMetatable(state);
			if (mt == null)
				mt = new LuaTable();
			mt.set(state, "javaType", ValueFactory.valueOf(clazz.getName()));
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
		registerConverter(PlayerProfile.class, new IConverter() {

			@Override
			public LuaValue toLua(LuaState state, Object val) {
				LuaTable ret = new LuaTable();
				ret.rawset("getDiscordUser", ValueFactory.valueOf("12345"));
				return ret;
			}

			@Override
			public Object toJava(LuaState state, LuaValue val) throws LuaError {
				LuaTable tbl = val.checkTable();
				PlayerProfile ret = new PlayerProfile();
				ret.setDiscordId(Long.parseLong(tbl.get(state, "getDiscordUser").toString()));
				return new PlayerProfile();
			}
		});
		LuaState state = new LuaState(new AbstractResourceManipulator() {

			private boolean hasRun = false;

			@Override
			public InputStream findResource(String filename) {
				if (hasRun)
					return null;
				hasRun = true;
				try {
					return new FileInputStream(new File(filename));
				} catch (FileNotFoundException e) {
					return null;
				}
			}
		});
		PlayerProfile prof = new PlayerProfile();
		LuaValue profL = convertToLua(state, prof);
		LuaTable mt = profL.getMetatable(state);
		try {
			System.out.println(mt.get(state, "javaType"));
		} catch (LuaError e) {}
		System.out.println(profL);
		System.out.println(convertToJava(state, profL));
	}

}
