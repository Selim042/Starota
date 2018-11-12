package us.myles_selim.starota.lua;

import java.util.List;

import org.squiddev.cobalt.Constants;
import org.squiddev.cobalt.LuaError;
import org.squiddev.cobalt.LuaNil;
import org.squiddev.cobalt.LuaState;
import org.squiddev.cobalt.LuaTable;
import org.squiddev.cobalt.LuaValue;
import org.squiddev.cobalt.ValueFactory;
import org.squiddev.cobalt.Varargs;
import org.squiddev.cobalt.function.LuaFunction;
import org.squiddev.cobalt.function.OneArgFunction;
import org.squiddev.cobalt.function.ZeroArgFunction;
import org.squiddev.cobalt.lib.LuaLibrary;

import sx.blah.discord.handle.obj.ICategory;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IRole;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.handle.obj.Permissions;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.RequestBuffer;
import us.myles_selim.starota.Starota;

public class DiscordLib implements LuaLibrary {

	private final IGuild server;

	public DiscordLib(IGuild server) {
		this.server = server;
	}

	@Override
	public LuaValue add(LuaState state, LuaTable env) {
		env.rawset("discord", getServer(server));
		return env;
	}

	protected LuaValue getServer(IGuild server) {
		if (server == null)
			return Constants.NIL;
		LuaTable serverT = new LuaTable();
		serverT.rawset("getServerName", new ZeroArgFunction() {

			@Override
			public LuaValue call(LuaState state) throws LuaError {
				return ValueFactory.valueOf(server.getName());
			}
		});
		serverT.rawset("getUsers", new ZeroArgFunction() {

			@Override
			public LuaValue call(LuaState state) throws LuaError {
				LuaTable usersT = new LuaTable();
				List<IUser> users = server.getUsers();
				for (int i = 0; i < users.size(); i++)
					usersT.rawset(i, getUser(users.get(i)));
				return usersT;
			}
		});
		serverT.rawset("getRoles", new ZeroArgFunction() {

			@Override
			public LuaValue call(LuaState state) throws LuaError {
				LuaTable rolesT = new LuaTable();
				List<IRole> roles = server.getRoles();
				for (int i = 0; i < roles.size(); i++)
					rolesT.rawset(i, getRole(roles.get(i)));
				return rolesT;
			}
		});
		serverT.rawset("getChannels", new ZeroArgFunction() {

			@Override
			public LuaValue call(LuaState state) throws LuaError {
				LuaTable rolesT = new LuaTable();
				List<IChannel> roles = server.getChannels();
				for (int i = 0; i < roles.size(); i++)
					rolesT.rawset(i, getChannel(roles.get(i)));
				return rolesT;
			}
		});
		serverT.rawset("getChannelsByName", new OneArgFunction() {

			@Override
			public LuaValue call(LuaState state, LuaValue arg) throws LuaError {
				LuaTable table = new LuaTable();
				List<IChannel> channels = server.getChannelsByName(arg.toString());
				for (int i = 0; i < channels.size(); i++)
					table.rawset(i, getChannel(channels.get(i)));
				return table;
			}
		});
		serverT.rawset("getChannelById", new OneArgFunction() {

			@Override
			public LuaValue call(LuaState state, LuaValue arg) throws LuaError {
				long channelId;
				try {
					channelId = Long.parseLong(arg.checkString());
				} catch (NumberFormatException e) {
					return Constants.NIL;
				}
				return getChannel(server.getChannelByID(channelId));
			}
		});
		return serverT;
	}

	protected LuaValue getUser(IUser user) {
		if (user == null)
			return Constants.NIL;
		LuaTable userT = new LuaTable();
		userT.rawset("getName", new ZeroArgFunction() {

			@Override
			public LuaValue call(LuaState state) throws LuaError {
				return ValueFactory.valueOf(user.getName());
			}
		});
		userT.rawset("getDiscriminator", new ZeroArgFunction() {

			@Override
			public LuaValue call(LuaState state) throws LuaError {
				return ValueFactory.valueOf(user.getDiscriminator());
			}
		});
		userT.rawset("getDisplayName", new ZeroArgFunction() {

			@Override
			public LuaValue call(LuaState state) throws LuaError {
				return ValueFactory.valueOf(user.getDisplayName(server));
			}
		});
		userT.rawset("getRoles", new ZeroArgFunction() {

			@Override
			public LuaValue call(LuaState state) throws LuaError {
				LuaTable rolesT = new LuaTable();
				List<IRole> roles = user.getRolesForGuild(server);
				for (int i = 0; i < roles.size(); i++)
					rolesT.rawset(i, getRole(roles.get(i)));
				return rolesT;
			}
		});
		userT.rawset("addRole", new OneArgFunction() {

			@Override
			public LuaValue call(LuaState state, LuaValue arg) throws LuaError {
				if (arg instanceof LuaNil)
					return Constants.FALSE;
				LuaTable tbl = (LuaTable) arg;
				LuaFunction func = (LuaFunction) tbl.rawget("getId");
				IRole role = server.getRoleByID(Long.parseLong(func.call(state).checkString()));
				if (Starota.getOurUser().getPermissionsForGuild(server)
						.contains(Permissions.MANAGE_ROLES))
					return RequestBuffer.request(() -> {
						try {
							user.addRole(role);
						} catch (DiscordException e) {
							return Constants.FALSE;
						}
						return Constants.TRUE;
					}).get();
				else
					return Constants.FALSE;
			}
		});
		userT.rawset("removeRole", new OneArgFunction() {

			@Override
			public LuaValue call(LuaState state, LuaValue arg) throws LuaError {
				if (arg instanceof LuaNil)
					return Constants.FALSE;
				LuaTable tbl = (LuaTable) arg;
				LuaFunction func = (LuaFunction) tbl.rawget("getId");
				IRole role = server.getRoleByID(Long.parseLong(func.call(state).checkString()));
				if (Starota.getOurUser().getPermissionsForGuild(server)
						.contains(Permissions.MANAGE_ROLES))
					return RequestBuffer.request(() -> {
						try {
							user.removeRole(role);
						} catch (DiscordException e) {
							return Constants.FALSE;
						}
						return Constants.TRUE;
					}).get();
				else
					return Constants.FALSE;
			}
		});
		return userT;
	}

	protected LuaValue getRole(IRole role) {
		if (role == null)
			return Constants.NIL;
		LuaTable roleT = new LuaTable();
		roleT.rawset("getId", new ZeroArgFunction() {

			@Override
			public LuaValue call(LuaState state) throws LuaError {
				return ValueFactory.valueOf(role.getStringID());
			}
		});
		roleT.rawset("getName", new ZeroArgFunction() {

			@Override
			public LuaValue call(LuaState state) throws LuaError {
				return ValueFactory.valueOf(role.getName());
			}
		});
		roleT.rawset("getColor", new ZeroArgFunction() {

			@Override
			public LuaValue call(LuaState state) throws LuaError {
				return ValueFactory.valueOf(role.getColor().getRGB());
			}
		});
		return roleT;
	}

	protected LuaValue getChannel(IChannel channel) {
		if (channel == null)
			return Constants.NIL;
		LuaTable channelT = new LuaTable();
		channelT.rawset("getName", new ZeroArgFunction() {

			@Override
			public LuaValue call(LuaState state) throws LuaError {
				return ValueFactory.valueOf(channel.getName());
			}
		});
		channelT.rawset("sendMessage", new OneArgFunction() {

			@Override
			public LuaValue call(LuaState state, LuaValue arg) throws LuaError {
				if (arg instanceof LuaNil)
					return Constants.FALSE;
				if (channel.getModifiedPermissions(Starota.getOurUser())
						.contains(Permissions.SEND_MESSAGES))
					return RequestBuffer.request(() -> {
						try {
							channel.sendMessage(arg.toString());
						} catch (DiscordException e) {
							return Constants.FALSE;
						}
						return Constants.TRUE;
					}).get();
				else
					return Constants.FALSE;
			}
		});
		channelT.rawset("getCategory", new ZeroArgFunction() {

			@Override
			public LuaValue call(LuaState state) throws LuaError {
				ICategory cat = channel.getCategory();
				if (cat == null)
					return Constants.NIL;
				return ValueFactory.valueOf(cat.getName());
			}
		});
		channelT.rawset("getUsersHere", new ZeroArgFunction() {

			@Override
			public LuaValue call(LuaState state) throws LuaError {
				LuaTable tbl = new LuaTable();
				List<IUser> users = channel.getUsersHere();
				for (int i = 0; i < users.size(); i++)
					tbl.rawset(i, getUser(users.get(i)));
				return tbl;
			}
		});
		return channelT;
	}

}
