package us.myles_selim.starota.lua.conversion.discord;

import org.squiddev.cobalt.Constants;
import org.squiddev.cobalt.LuaError;
import org.squiddev.cobalt.LuaState;
import org.squiddev.cobalt.LuaTable;
import org.squiddev.cobalt.LuaUserdata;
import org.squiddev.cobalt.LuaValue;
import org.squiddev.cobalt.ValueFactory;
import org.squiddev.cobalt.function.OneArgFunction;
import org.squiddev.cobalt.function.ZeroArgFunction;

import sx.blah.discord.handle.obj.IMessage;
import us.myles_selim.starota.lua.conversion.ConversionHandler;
import us.myles_selim.starota.lua.conversion.IConverter;

public class MessageConverter implements IConverter {

	@Override
	public LuaValue toLua(LuaState state, Object val) throws LuaError {
		if (!(val instanceof IMessage))
			return Constants.NIL;
		IMessage message = (IMessage) val;
		LuaTable methods = new LuaTable();
		methods.rawset("getContent", new ZeroArgFunction() {

			@Override
			public LuaValue call(LuaState state) throws LuaError {
				return ValueFactory.valueOf(message.getContent());
			}
		});
		methods.rawset("getChannel", new ZeroArgFunction() {

			@Override
			public LuaValue call(LuaState state) throws LuaError {
				return ConversionHandler.convertToLua(state, message.getChannel());
			}
		});
		methods.rawset("getAuthor", new ZeroArgFunction() {

			@Override
			public LuaValue call(LuaState state) throws LuaError {
				return ConversionHandler.convertToLua(state, message.getAuthor());
			}
		});
		methods.rawset("reply", new OneArgFunction() {

			@Override
			public LuaValue call(LuaState state, LuaValue arg) throws LuaError {
				if (arg == Constants.NIL || arg.toString().isEmpty())
					return Constants.NIL;
				return ConversionHandler.convertToLua(state, message.reply(arg.toString()));
			}
		});
		methods.rawset("edit", new OneArgFunction() {

			@Override
			public LuaValue call(LuaState state, LuaValue arg) throws LuaError {
				if (arg == Constants.NIL || arg.toString().isEmpty())
					return Constants.NIL;
				return ConversionHandler.convertToLua(state, message.edit(arg.toString()));
			}
		});
		methods.rawset("delete", new ZeroArgFunction() {

			@Override
			public LuaValue call(LuaState state) throws LuaError {
				message.delete();
				return Constants.TRUE;
			}
		});
		methods.rawset("getFormattedContent", new ZeroArgFunction() {

			@Override
			public LuaValue call(LuaState state) throws LuaError {
				return ValueFactory.valueOf(message.getFormattedContent());
			}
		});
		// TODO: Add reaction support
		// methods.rawset("getReactions", new ZeroArgFunction() {
		//
		// @Override
		// public LuaValue call(LuaState state) throws LuaError {
		// LuaTable ret = new LuaTable();
		// List<IReaction> reactions = message.getReactions();
		// for (int i = 0; i < reactions.size(); i++)
		// ret.rawset(i, ConversionHandler.convertToLua(state,
		// reactions.get(i)));
		// return ret;
		// }
		// });
		// methods.rawset("addReaction", new OneArgFunction() {
		//
		// @Override
		// public LuaValue call(LuaState state, LuaValue arg) throws LuaError {
		// return null;
		// }
		// });
		LuaTable mt = new LuaTable();
		mt.rawset("__index", methods);
		return new LuaUserdata(message, mt);
	}

	@Override
	public Object toJava(LuaState state, LuaValue val) throws LuaError {
		if (val instanceof LuaUserdata)
			return ((LuaUserdata) val).instance;
		return null;
	}

}
