package us.myles_selim.starota.lua.conversion.starota;

import java.util.List;

import org.squiddev.cobalt.Constants;
import org.squiddev.cobalt.LuaError;
import org.squiddev.cobalt.LuaState;
import org.squiddev.cobalt.LuaTable;
import org.squiddev.cobalt.LuaUserdata;
import org.squiddev.cobalt.LuaValue;
import org.squiddev.cobalt.ValueFactory;
import org.squiddev.cobalt.function.ZeroArgFunction;

import us.myles_selim.starota.lua.conversion.ConversionHandler;
import us.myles_selim.starota.lua.conversion.IConverter;
import us.myles_selim.starota.trading.EnumPokemon;
import us.myles_selim.starota.trading.EnumPokemonType;
import us.myles_selim.starota.trading.FormManager;
import us.myles_selim.starota.trading.forms.FormSet;
import us.myles_selim.starota.trading.forms.FormSet.Form;

public class EnumPokemonConverter implements IConverter {

	@Override
	public LuaValue toLua(LuaState state, Object val) {
		if (!(val instanceof EnumPokemon))
			return Constants.NIL;
		EnumPokemon pokemon = (EnumPokemon) val;
		LuaTable methods = new LuaTable();
		methods.rawset("getName", new ZeroArgFunction() {

			@Override
			public LuaValue call(LuaState state) throws LuaError {
				return ValueFactory.valueOf(pokemon.getName());
			}
		});
		methods.rawset("getType1", new ZeroArgFunction() {

			@Override
			public LuaValue call(LuaState state) throws LuaError {
				return ValueFactory.valueOf(pokemon.getType1().name());
			}
		});
		methods.rawset("getType2", new ZeroArgFunction() {

			@Override
			public LuaValue call(LuaState state) throws LuaError {
				EnumPokemonType type = pokemon.getType2();
				if (type == null)
					return Constants.NIL;
				return ValueFactory.valueOf(type.name());
			}
		});
		methods.rawset("isTradeable", new ZeroArgFunction() {

			@Override
			public LuaValue call(LuaState state) throws LuaError {
				return ValueFactory.valueOf(pokemon.isTradable());
			}
		});
		methods.rawset("getGenderPossible", new ZeroArgFunction() {

			@Override
			public LuaValue call(LuaState state) throws LuaError {
				return ValueFactory.valueOf(pokemon.getGenderPossible().toString());
			}
		});
		methods.rawset("getDefaultForm", new ZeroArgFunction() {

			@Override
			public LuaValue call(LuaState state) throws LuaError {
				Form defForm = pokemon.getDefaultForm();
				if (defForm == null)
					return Constants.NIL;
				return ConversionHandler.convertToLua(state, defForm);
			}
		});
		methods.rawset("getForms", new ZeroArgFunction() {

			@Override
			public LuaValue call(LuaState state) throws LuaError {
				FormSet set = pokemon.getFormSet();
				if (set == null)
					return Constants.NIL;
				LuaTable ret = new LuaTable();
				List<Form> forms = set.getForms();
				for (int i = 1; i <= forms.size(); i++)
					ret.rawset(i, ValueFactory.valueOf(forms.get(i - 1).toString()));
				return ret;
			}
		});
		methods.rawset("isAvailable", new ZeroArgFunction() {

			@Override
			public LuaValue call(LuaState state) throws LuaError {
				return ValueFactory.valueOf(FormManager.isAvailable(pokemon));
			}
		});
		methods.rawset("isShinyable", new ZeroArgFunction() {

			@Override
			public LuaValue call(LuaState state) throws LuaError {
				return ValueFactory.valueOf(FormManager.isShinyable(pokemon));
			}
		});
		LuaTable mt = new LuaTable();
		mt.rawset("__index", methods);
		return new LuaUserdata(pokemon, mt);
	}

	@Override
	public Object toJava(LuaState state, LuaValue val) throws LuaError {
		if (val instanceof LuaUserdata)
			return ((LuaUserdata) val).instance;
		return null;
	}

}
