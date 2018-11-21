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
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.RequestBuffer;
import us.myles_selim.starota.lua.conversion.ConversionHandler;
import us.myles_selim.starota.lua.conversion.IConverter;

public class ChannelConverter implements IConverter {

	@Override
	public LuaValue toLua(LuaState state, Object val) throws LuaError {
		if (!(val instanceof IChannel))
			return Constants.NIL;
		IChannel channel = (IChannel) val;
		LuaTable methods = new LuaTable();
		methods.rawset("getName", new ZeroArgFunction() {

			@Override
			public LuaValue call(LuaState state) throws LuaError {
				return ValueFactory.valueOf(channel.getName());
			}
		});
		methods.rawset("getMessageById", new OneArgFunction() {

			@Override
			public LuaValue call(LuaState state, LuaValue arg) throws LuaError {
				if (arg == Constants.NIL || !arg.isString())
					throw new LuaError("arg must be a string representation of a long");
				try {
					long id = Long.parseLong(arg.checkString());
					return ConversionHandler.convertToLua(state, channel.fetchMessage(id));
				} catch (NumberFormatException e) {
					throw new LuaError("arg must be a string representation of a long");
				}
			}
		});
		methods.rawset("getTopic", new ZeroArgFunction() {

			@Override
			public LuaValue call(LuaState state) throws LuaError {
				String topic = channel.getTopic();
				if (topic == null)
					return Constants.NIL;
				return ValueFactory.valueOf(topic);
			}
		});
		methods.rawset("getMessageById", new OneArgFunction() {

			@Override
			public LuaValue call(LuaState state, LuaValue arg) throws LuaError {
				if (arg == Constants.NIL || arg.toString().isEmpty())
					return Constants.NIL;
				return ConversionHandler.convertToLua(state, channel.sendMessage(arg.toString()));
			}
		});
		methods.rawset("changeName", new OneArgFunction() {

			@Override
			public LuaValue call(LuaState state, LuaValue arg) throws LuaError {
				if (arg == Constants.NIL || arg.toString().isEmpty())
					return Constants.FALSE;
				channel.changeName(arg.toString());
				return Constants.TRUE;
			}
		});
		methods.rawset("changeTopic", new OneArgFunction() {

			@Override
			public LuaValue call(LuaState state, LuaValue arg) throws LuaError {
				if (arg == Constants.NIL || arg.toString().isEmpty())
					return Constants.FALSE;
				channel.changeTopic(arg.toString());
				return Constants.TRUE;
			}
		});
		methods.rawset("getUsersHere", new ZeroArgFunction() {

			@Override
			public LuaValue call(LuaState state) throws LuaError {
				LuaTable ret = new LuaTable();
				List<IUser> users = channel.getUsersHere();
				for (int i = 0; i < users.size(); i++)
					ret.rawset(i, ConversionHandler.convertToLua(state, users.get(i)));
				return ret;
			}
		});
		methods.rawset("getCategory", new ZeroArgFunction() {

			@Override
			public LuaValue call(LuaState state) throws LuaError {
				ICategory cat = channel.getCategory();
				if (cat == null)
					return Constants.NIL;
				return ConversionHandler.convertToLua(state, cat);
			}
		});
		methods.rawset("delete", new ZeroArgFunction() {

			@Override
			public LuaValue call(LuaState state) throws LuaError {
				channel.delete();
				return Constants.TRUE;
			}
		});
		methods.rawset("sendMessage", new OneArgFunction() {

			@Override
			public LuaValue call(LuaState state, LuaValue arg) throws LuaError {
				if (arg == Constants.NIL || arg.toString().isEmpty())
					return Constants.NIL;
				return RequestBuffer.request(() -> {
					return ConversionHandler.convertToLua(state, channel.sendMessage(arg.toString()));
				}).get();
			}
		});
		LuaTable mt = new LuaTable();
		mt.rawset("__index", methods);
		return new LuaUserdata(channel, mt);
	}

	@Override
	public Object toJava(LuaState state, LuaValue val) throws LuaError {
		if (val instanceof LuaUserdata)
			return ((LuaUserdata) val).instance;
		return null;
	}

}
