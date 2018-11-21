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

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IRole;
import sx.blah.discord.handle.obj.IUser;
import us.myles_selim.starota.lua.conversion.ConversionHandler;
import us.myles_selim.starota.lua.conversion.IConverter;

public class ServerConverter implements IConverter {

	@Override
	public LuaValue toLua(LuaState state, Object val) {
		if (!(val instanceof IGuild))
			return Constants.NIL;
		IGuild server = (IGuild) val;
		LuaTable methods = new LuaTable();
		methods.rawset("getServerName", new ZeroArgFunction() {

			@Override
			public LuaValue call(LuaState state) throws LuaError {
				return ValueFactory.valueOf(server.getName());
			}
		});
		methods.rawset("getUsers", new ZeroArgFunction() {

			@Override
			public LuaValue call(LuaState state) throws LuaError {
				LuaTable usersT = new LuaTable();
				List<IUser> users = server.getUsers();
				for (int i = 1; i <= users.size(); i++)
					usersT.rawset(i, ConversionHandler.convertToLua(state, users.get(i - 1)));
				return usersT;
			}
		});
		methods.rawset("getRoles", new ZeroArgFunction() {

			@Override
			public LuaValue call(LuaState state) throws LuaError {
				LuaTable rolesT = new LuaTable();
				List<IRole> roles = server.getRoles();
				for (int i = 1; i <= roles.size(); i++)
					rolesT.rawset(i, ConversionHandler.convertToLua(state, roles.get(i - 1)));
				return rolesT;
			}
		});
		methods.rawset("getChannels", new ZeroArgFunction() {

			@Override
			public LuaValue call(LuaState state) throws LuaError {
				LuaTable rolesT = new LuaTable();
				List<IChannel> roles = server.getChannels();
				for (int i = 1; i <= roles.size(); i++)
					rolesT.rawset(i, ConversionHandler.convertToLua(state, roles.get(i - 1)));
				return rolesT;
			}
		});
		methods.rawset("getChannelsByName", new OneArgFunction() {

			@Override
			public LuaValue call(LuaState state, LuaValue arg) throws LuaError {
				LuaTable table = new LuaTable();
				List<IChannel> channels = server.getChannelsByName(arg.toString());
				for (int i = 1; i <= channels.size(); i++)
					table.rawset(i, ConversionHandler.convertToLua(state, channels.get(i - 1)));
				return table;
			}
		});
		methods.rawset("getChannelById", new OneArgFunction() {

			@Override
			public LuaValue call(LuaState state, LuaValue arg) throws LuaError {
				long channelId;
				try {
					channelId = Long.parseLong(arg.checkString());
				} catch (NumberFormatException e) {
					return Constants.NIL;
				}
				return ConversionHandler.convertToLua(state, server.getChannelByID(channelId));
			}
		});
		LuaTable mt = new LuaTable();
		mt.rawset("__index", methods);
		return new LuaUserdata(server, mt);
	}

	@Override
	public Object toJava(LuaState state, LuaValue val) throws LuaError {
		if (val instanceof LuaUserdata)
			return ((LuaUserdata) val).instance;
		return null;
	}

}
