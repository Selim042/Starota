package us.myles_selim.starota.lua.libraries;

import java.util.List;

import org.squiddev.cobalt.Constants;
import org.squiddev.cobalt.LuaError;
import org.squiddev.cobalt.LuaNil;
import org.squiddev.cobalt.LuaState;
import org.squiddev.cobalt.LuaTable;
import org.squiddev.cobalt.LuaUserdata;
import org.squiddev.cobalt.LuaValue;
import org.squiddev.cobalt.ValueFactory;
import org.squiddev.cobalt.function.OneArgFunction;
import org.squiddev.cobalt.function.TwoArgFunction;
import org.squiddev.cobalt.function.ZeroArgFunction;
import org.squiddev.cobalt.lib.LuaLibrary;

import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IUser;
import us.myles_selim.ebs.EBStorage;
import us.myles_selim.starota.ServerOptions;
import us.myles_selim.starota.Starota.BaseModules;
import us.myles_selim.starota.commands.CommandChangelogChannel;
import us.myles_selim.starota.leaderboards.Leaderboard;
import us.myles_selim.starota.leaderboards.LeaderboardManager;
import us.myles_selim.starota.lua.LuaUtils;
import us.myles_selim.starota.lua.conversion.ConversionHandler;
import us.myles_selim.starota.modules.StarotaModule;
import us.myles_selim.starota.profiles.PlayerProfile;
import us.myles_selim.starota.profiles.ProfileManager;
import us.myles_selim.starota.trading.EnumPokemon;
import us.myles_selim.starota.trading.Tradeboard;

public class StarotaLib implements LuaLibrary {

	private final IGuild server;

	public StarotaLib(IGuild server) {
		this.server = server;
	}

	@Override
	public LuaValue add(LuaState state, LuaTable env) {
		env.rawset("options", storageToValue(ServerOptions.getOptions(server), Tradeboard.TRADE_ID_KEY,
				CommandChangelogChannel.CHANGES_CHANNEL, "changesVersion"));
		if (StarotaModule.isModuleEnabled(server, BaseModules.PROFILES)) {
			env.rawset("getProfile", new OneArgFunction() {

				@Override
				public LuaValue call(LuaState state, LuaValue arg) throws LuaError {
					if (arg == Constants.NIL || !(arg instanceof LuaUserdata)
							|| !(((LuaUserdata) arg).instance instanceof IUser))
						throw new LuaError("arg must be a user");
					PlayerProfile prof = ProfileManager.getProfile(server,
							(IUser) ((LuaUserdata) arg).instance);
					if (prof == null)
						return Constants.NIL;
					return ConversionHandler.convertToLua(state, prof);
				}
			});
		}
		env.rawset("getPokemon", new OneArgFunction() {

			@Override
			public LuaValue call(LuaState state, LuaValue arg) throws LuaError {
				if (arg.isIntExact())
					return ConversionHandler.convertToLua(state,
							EnumPokemon.getPokemon(arg.checkInteger()));
				return ConversionHandler.convertToLua(state, EnumPokemon.getPokemon(arg.toString()));
			}
		});
		if (StarotaModule.isModuleEnabled(server, BaseModules.TRADEBOARD)) {
			env.rawset("getTrade", new OneArgFunction() {

				@Override
				public LuaValue call(LuaState state, LuaValue arg) throws LuaError {
					if (arg.isIntExact())
						return ConversionHandler.convertToLua(state,
								Tradeboard.getPost(server, arg.toInteger()));
					// TODO: Finish this
					throw new LuaError("can only get posts by id, WIP");
					// return ConversionHandler.convertToLua(state,
					// EnumPokemon.getPokemon(arg.toString()));
				}
			});
		}
		if (StarotaModule.isModuleEnabled(server, BaseModules.LEADERBOARDS)) {
			env.rawset("getLeaderboard", new OneArgFunction() {

				@Override
				public LuaValue call(LuaState state, LuaValue arg) throws LuaError {
					Leaderboard board = LeaderboardManager.getLeaderboard(server, arg.toString());
					if (board == null)
						return Constants.NIL;
					return ConversionHandler.convertToLua(state, board);
				}
			});
			env.rawset("getAllLeaderboards", new ZeroArgFunction() {

				@Override
				public LuaValue call(LuaState state) throws LuaError {
					LuaTable ret = new LuaTable();
					List<Leaderboard> boards = LeaderboardManager.getLeaderboards(server);
					for (int i = 0; i < boards.size(); i++)
						ret.rawset(i, ConversionHandler.convertToLua(state, boards.get(i)));
					return ret;
				}
			});
		}
		return env;
	}

	public static LuaValue storageToValue(EBStorage stor, String... lockedKeys) {
		LuaTable options = new LuaTable();
		// options.rawset("getKeys", new ZeroArgFunction() {
		//
		// @Override
		// public LuaValue call(LuaState state) throws LuaError {
		// LuaTable ret = new LuaTable();
		// int i = 0;
		// for (String k : stor.getKeys())
		// ret.rawset(i++, ValueFactory.valueOf(k));
		// return ret;
		// }
		// });
		options.rawset("hasKey", new OneArgFunction() {

			@Override
			public LuaValue call(LuaState state, LuaValue arg) throws LuaError {
				if (arg instanceof LuaNil)
					return Constants.NIL;
				return ValueFactory.valueOf(stor.containsKey(arg.toString()));
			}
		});
		options.rawset("getValue", new OneArgFunction() {

			@Override
			public LuaValue call(LuaState state, LuaValue arg) throws LuaError {
				if (arg instanceof LuaNil)
					return Constants.NIL;
				return LuaUtils.objToValue(state, stor.get(arg.toString()));
			}
		});
		options.rawset("setValue", new TwoArgFunction() {

			@Override
			public LuaValue call(LuaState state, LuaValue arg1, LuaValue arg2) throws LuaError {
				if (arg1 instanceof LuaNil || arg1 instanceof LuaNil
						|| arrCont(lockedKeys, arg1.toString()))
					return Constants.NIL;
				stor.set(arg1.toString(), LuaUtils.valueToObj(state, arg2));
				return Constants.NIL;
			}
		});
		options.rawset("clearValue", new OneArgFunction() {

			@Override
			public LuaValue call(LuaState state, LuaValue arg) throws LuaError {
				if (arg instanceof LuaNil || arrCont(lockedKeys, arg.toString()))
					return Constants.NIL;
				stor.clearKey(arg.toString());
				return Constants.NIL;
			}
		});
		return options;
	}

	private static boolean arrCont(String[] sss, String ss) {
		for (String s : sss)
			if (s != null && s.equals(ss))
				return true;
		return false;
	}

}
