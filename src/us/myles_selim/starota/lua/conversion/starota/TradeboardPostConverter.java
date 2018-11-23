package us.myles_selim.starota.lua.conversion.starota;

import org.squiddev.cobalt.Constants;
import org.squiddev.cobalt.LuaError;
import org.squiddev.cobalt.LuaState;
import org.squiddev.cobalt.LuaTable;
import org.squiddev.cobalt.LuaUserdata;
import org.squiddev.cobalt.LuaValue;
import org.squiddev.cobalt.ValueFactory;
import org.squiddev.cobalt.function.ZeroArgFunction;

import us.myles_selim.starota.Starota;
import us.myles_selim.starota.lua.conversion.ConversionHandler;
import us.myles_selim.starota.lua.conversion.IConverter;
import us.myles_selim.starota.trading.TradeboardPost;

public class TradeboardPostConverter implements IConverter {

	@Override
	public LuaValue toLua(LuaState state, Object val) {
		if (!(val instanceof TradeboardPost))
			return Constants.NIL;
		TradeboardPost post = (TradeboardPost) val;
		LuaTable methods = new LuaTable();
		methods.rawset("getId", new ZeroArgFunction() {

			@Override
			public LuaValue call(LuaState state) throws LuaError {
				return ValueFactory.valueOf(post.getId());
			}
		});
		methods.rawset("isLookingFor", new ZeroArgFunction() {

			@Override
			public LuaValue call(LuaState state) throws LuaError {
				return toLua(state, post.isLookingFor());
			}
		});
		methods.rawset("getOwner", new ZeroArgFunction() {

			@Override
			public LuaValue call(LuaState state) throws LuaError {
				return ConversionHandler.convertToLua(state, Starota.getUser(post.getOwner()));
			}
		});
		methods.rawset("getPokemon", new ZeroArgFunction() {

			@Override
			public LuaValue call(LuaState state) throws LuaError {
				return ConversionHandler.convertToLua(state, post.getPokemon());
			}
		});
		methods.rawset("getForm", new ZeroArgFunction() {

			@Override
			public LuaValue call(LuaState state) throws LuaError {
				return ConversionHandler.convertToLua(state, post.getForm());
			}
		});
		methods.rawset("isShiny", new ZeroArgFunction() {

			@Override
			public LuaValue call(LuaState state) throws LuaError {
				return ValueFactory.valueOf(post.isShiny());
			}
		});
		methods.rawset("getGender", new ZeroArgFunction() {

			@Override
			public LuaValue call(LuaState state) throws LuaError {
				return ValueFactory.valueOf(post.getGender().toString());
			}
		});
		methods.rawset("isLegacy", new ZeroArgFunction() {

			@Override
			public LuaValue call(LuaState state) throws LuaError {
				return ValueFactory.valueOf(post.isLegacy());
			}
		});
		methods.rawset("getPostedString", new ZeroArgFunction() {

			@Override
			public LuaValue call(LuaState state) throws LuaError {
				return ValueFactory.valueOf(post.getTimePosted().toString());
			}
		});
		methods.rawset("getPostedEpoch", new ZeroArgFunction() {

			@Override
			public LuaValue call(LuaState state) throws LuaError {
				return ValueFactory.valueOf(post.getTimePosted().getEpochSecond());
			}
		});
		LuaTable mt = new LuaTable();
		mt.rawset("__index", methods);
		return new LuaUserdata(post, mt);
	}

	@Override
	public Object toJava(LuaState state, LuaValue val) throws LuaError {
		if (val instanceof LuaUserdata)
			return ((LuaUserdata) val).instance;
		return null;
	}

}
