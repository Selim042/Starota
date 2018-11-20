package us.myles_selim.starota.lua.conversion.discord;

import java.util.List;

import org.squiddev.cobalt.Constants;
import org.squiddev.cobalt.LuaError;
import org.squiddev.cobalt.LuaState;
import org.squiddev.cobalt.LuaTable;
import org.squiddev.cobalt.LuaUserdata;
import org.squiddev.cobalt.LuaValue;
import org.squiddev.cobalt.ValueFactory;
import org.squiddev.cobalt.function.OneArgFunction;
import org.squiddev.cobalt.function.ZeroArgFunction;

import sx.blah.discord.handle.obj.ICategory;
import sx.blah.discord.handle.obj.IChannel;
import us.myles_selim.starota.lua.conversion.ConversionHandler;
import us.myles_selim.starota.lua.conversion.IConverter;

public class CategoryConverter implements IConverter {

	@Override
	public LuaValue toLua(LuaState state, Object val) throws LuaError {
		if (!(val instanceof ICategory))
			return Constants.NIL;
		ICategory category = (ICategory) val;
		LuaTable methods = new LuaTable();
		methods.rawset("getChannels", new ZeroArgFunction() {

			@Override
			public LuaValue call(LuaState state) throws LuaError {
				LuaTable ret = new LuaTable();
				List<IChannel> channels = category.getChannels();
				for (int i = 0; i < channels.size(); i++)
					ret.rawset(i, ConversionHandler.convertToLua(state, channels.get(i)));
				return ret;
			}
		});
		methods.rawset("createChannel", new OneArgFunction() {

			@Override
			public LuaValue call(LuaState state, LuaValue arg) throws LuaError {
				if (arg == Constants.NIL || arg.toString().isEmpty())
					return Constants.FALSE;
				return ConversionHandler.convertToLua(state, category.createChannel(arg.toString()));
			}
		});
		methods.rawset("getName", new ZeroArgFunction() {

			@Override
			public LuaValue call(LuaState state) throws LuaError {
				return ValueFactory.valueOf(category.getName());
			}
		});
		methods.rawset("changeName", new OneArgFunction() {

			@Override
			public LuaValue call(LuaState state, LuaValue arg) throws LuaError {
				if (arg == Constants.NIL || arg.toString().isEmpty())
					return Constants.FALSE;
				category.changeName(arg.toString());
				return Constants.TRUE;
			}
		});
		LuaTable mt = new LuaTable();
		mt.rawset("__index", methods);
		return new LuaUserdata(category, mt);
	}

	@Override
	public Object toJava(LuaState state, LuaValue val) throws LuaError {
		if (val instanceof LuaUserdata)
			return ((LuaUserdata) val).instance;
		return null;
	}

}
