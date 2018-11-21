package us.myles_selim.starota.lua.conversion.starota;

import org.squiddev.cobalt.Constants;
import org.squiddev.cobalt.LuaError;
import org.squiddev.cobalt.LuaState;
import org.squiddev.cobalt.LuaTable;
import org.squiddev.cobalt.LuaUserdata;
import org.squiddev.cobalt.LuaValue;
import org.squiddev.cobalt.ValueFactory;
import org.squiddev.cobalt.function.OneArgFunction;
import org.squiddev.cobalt.function.ZeroArgFunction;

import us.myles_selim.starota.lua.conversion.IConverter;
import us.myles_selim.starota.trading.EnumPokemon;
import us.myles_selim.starota.trading.forms.FormSet.Form;

public class FormConverter implements IConverter {

	@Override
	public LuaValue toLua(LuaState state, Object val) {
		if (!(val instanceof Form))
			return Constants.NIL;
		Form form = (Form) val;
		LuaTable methods = new LuaTable();
		methods.rawset("getName", new ZeroArgFunction() {

			@Override
			public LuaValue call(LuaState state) throws LuaError {
				return ValueFactory.valueOf(form.toString());
			}
		});
		methods.rawset("getType1", new OneArgFunction() {

			@Override
			public LuaValue call(LuaState state, LuaValue arg) throws LuaError {
				if (!(arg instanceof LuaUserdata)
						|| !(((LuaUserdata) arg).instance instanceof EnumPokemon))
					throw new LuaError("arg must be a pokemon object");
				return ValueFactory
						.valueOf(form.getType1((EnumPokemon) ((LuaUserdata) arg).instance).name());
			}
		});
		methods.rawset("getType2", new OneArgFunction() {

			@Override
			public LuaValue call(LuaState state, LuaValue arg) throws LuaError {
				if (!(arg instanceof LuaUserdata)
						|| !(((LuaUserdata) arg).instance instanceof EnumPokemon))
					throw new LuaError("arg must be a pokemon object");
				return ValueFactory
						.valueOf(form.getType2((EnumPokemon) ((LuaUserdata) arg).instance).name());
			}
		});
		methods.rawset("isShinyable", new OneArgFunction() {

			@Override
			public LuaValue call(LuaState state, LuaValue arg) throws LuaError {
				if (!(arg instanceof LuaUserdata)
						|| !(((LuaUserdata) arg).instance instanceof EnumPokemon))
					throw new LuaError("arg must be a pokemon object");
				return ValueFactory.valueOf(form.canBeShiny((EnumPokemon) ((LuaUserdata) arg).instance));
			}
		});
		LuaTable mt = new LuaTable();
		mt.rawset("__index", methods);
		return new LuaUserdata(form, mt);
	}

	@Override
	public Object toJava(LuaState state, LuaValue val) throws LuaError {
		if (val instanceof LuaUserdata)
			return ((LuaUserdata) val).instance;
		return null;
	}

}
