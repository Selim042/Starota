package us.myles_selim.starota.lua;

import org.squiddev.cobalt.Constants;
import org.squiddev.cobalt.LuaNil;
import org.squiddev.cobalt.LuaValue;
import org.squiddev.cobalt.ValueFactory;

import us.myles_selim.ebs.EBStorage;

public class LuaUtils {

	public static LuaValue objToValue(Object obj) {
		if (obj == null)
			return Constants.NIL;
		if (obj instanceof Boolean)
			return ValueFactory.valueOf((Boolean) obj);
		if (obj instanceof Integer)
			return ValueFactory.valueOf((Integer) obj);
		if (obj instanceof Double)
			return ValueFactory.valueOf((Double) obj);
		if (obj instanceof String)
			return ValueFactory.valueOf((String) obj);
		if (obj instanceof byte[])
			return ValueFactory.valueOf((byte[]) obj);
		if (obj instanceof EBStorage)
			return StarotaLib.storageToValue((EBStorage) obj);
		return Constants.NIL;
	}

	public static Object valueToObj(LuaValue val) {
		if (val == null || val instanceof LuaNil)
			return null;
		if (val.isBoolean())
			return val.toBoolean();
		if (val.isString())
			return val.toString();
		if (val.isInteger())
			return val.toInteger();
		if (val.isNumber())
			return val.toDouble();
		return null;
	}

}
