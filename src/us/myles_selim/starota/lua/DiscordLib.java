package us.myles_selim.starota.lua;

import java.util.List;

import org.squiddev.cobalt.Constants;
import org.squiddev.cobalt.LuaError;
import org.squiddev.cobalt.LuaNil;
import org.squiddev.cobalt.LuaState;
import org.squiddev.cobalt.LuaTable;
import org.squiddev.cobalt.LuaValue;
import org.squiddev.cobalt.ValueFactory;
import org.squiddev.cobalt.function.OneArgFunction;
import org.squiddev.cobalt.function.ZeroArgFunction;
import org.squiddev.cobalt.lib.LuaLibrary;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IRole;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.RequestBuffer;

public class DiscordLib implements LuaLibrary {

	private final IGuild server;

	public DiscordLib(IGuild server) {
		this.server = server;
	}

	@Override
	public LuaValue add(LuaState state, LuaTable env) {
		env.rawset("discord", getServer());
		return env;
	}

	private LuaValue getServer() {
		if (server == null)
			return Constants.NIL;
		LuaTable serverT = new LuaTable();
		serverT.rawset("getServerName", new FunctionGetServerName());
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
				return getChannel(server.getChannelByID(arg.checkLong()));
			}
		});
		return serverT;
	}

	private LuaValue getUser(IUser user) {
		if (user == null)
			return Constants.NIL;
		LuaTable userT = new LuaTable();
		userT.rawset("getName", new ZeroArgFunction() {

			@Override
			public LuaValue call(LuaState state) throws LuaError {
				return ValueFactory.valueOf(user.getName());
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
		return userT;
	}

	private LuaValue getRole(IRole role) {
		if (role == null)
			return Constants.NIL;
		LuaTable roleT = new LuaTable();
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

	private LuaValue getChannel(IChannel channel) {
		if (channel == null)
			return Constants.NIL;
		LuaTable channelT = new LuaTable();
		channelT.rawset("getName", new ZeroArgFunction() {

			@Override
			public LuaValue call(LuaState state) throws LuaError {
				return ValueFactory.valueOf(channel.getName());
			}
		});
		// channelT.rawset("sendMessage", new OneArgFunction() {
		//
		// private long chId = channel.getLongID();
		//
		// @Override
		// public LuaValue call(LuaState state, LuaValue arg) throws LuaError {
		// if (arg instanceof LuaNil)
		// return ValueFactory.valueOf(false);
		// IChannel ch = server.getChannelByID(chId);
		// ch.sendMessage("s");
		// RequestBuffer.request(() -> ch.sendMessage(arg.toString()));
		// return ValueFactory.valueOf(true);
		// }
		// });
		channelT.rawset("sendMessage", new FunctionChannelSendMessage(channel));
		return channelT;
	}

	private class FunctionChannelSendMessage extends OneArgFunction {

		private final IChannel channel;

		public FunctionChannelSendMessage(IChannel channel) {
			this.channel = channel;
		}

		@Override
		public LuaValue call(LuaState state, LuaValue arg) throws LuaError {
			if (arg instanceof LuaNil)
				return ValueFactory.valueOf(false);
			channel.sendMessage("s");
			RequestBuffer.request(() -> channel.sendMessage(arg.toString()));
			return ValueFactory.valueOf(true);
		}
	}

	// private LuaTable getChannel(IChannel channel) {
	//
	// }

	private class FunctionGetServerName extends ZeroArgFunction {

		@Override
		public LuaValue call(LuaState state) throws LuaError {
			return ValueFactory.valueOf(server.getName());
		}

	}

	// private LuaTable getUser(IUser user) {
	//
	// }

}
