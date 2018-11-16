package us.myles_selim.starota.lua;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.squiddev.cobalt.Constants;
import org.squiddev.cobalt.LuaError;
import org.squiddev.cobalt.LuaNil;
import org.squiddev.cobalt.LuaState;
import org.squiddev.cobalt.LuaTable;
import org.squiddev.cobalt.LuaValue;
import org.squiddev.cobalt.ValueFactory;
import org.squiddev.cobalt.compiler.LuaC;
import org.squiddev.cobalt.function.LuaFunction;
import org.squiddev.cobalt.function.OneArgFunction;
import org.squiddev.cobalt.function.ZeroArgFunction;
import org.squiddev.cobalt.lib.BaseLib;
import org.squiddev.cobalt.lib.CoroutineLib;
import org.squiddev.cobalt.lib.MathLib;
import org.squiddev.cobalt.lib.StringLib;
import org.squiddev.cobalt.lib.TableLib;
import org.squiddev.cobalt.lib.platform.AbstractResourceManipulator;

import sx.blah.discord.handle.impl.events.guild.GuildEvent;
import sx.blah.discord.handle.impl.events.guild.channel.ChannelEvent;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageEvent;
import sx.blah.discord.handle.impl.events.guild.channel.message.reaction.ReactionEvent;
import sx.blah.discord.handle.obj.ICategory;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IRole;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.handle.obj.Permissions;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.RequestBuffer;
import us.myles_selim.ebs.EBStorage;
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.lua.conversion.ConversionHandler;

public class LuaUtils {

	private static final Map<IGuild, LuaState> STATES = new HashMap<>();

	public static LuaState getState(IGuild server) {
		if (STATES.containsKey(server))
			return STATES.get(server);
		LuaState state = new LuaState(new AbstractResourceManipulator() {

			// private boolean hasRun = false;

			@Override
			public InputStream findResource(String filename) {
				// if (hasRun)
				return null;
				// hasRun = true;
				// try {
				// return new FileInputStream(new File(filename));
				// } catch (FileNotFoundException e) {
				// return null;
				// }
			}
		});
		LuaTable _G = new LuaTable();
		state.setupThread(_G);
		_G.load(state, new BaseLib());
		// _G.load(state, new PackageLib());
		_G.load(state, new TableLib());
		_G.load(state, new StringLib());
		_G.load(state, new CoroutineLib());
		_G.load(state, new MathLib());
		// _G.load(state, new JseIoLib());
		// _G.load(state, new OsLib());

		_G.load(state, new DiscordLib(server));
		_G.load(state, new StarotaLib(server));
		_G.load(state, new DiscordEventLib(server));
		_G.rawset("dofile", Constants.NIL);
		_G.rawset("loadfile", Constants.NIL);
		LuaC.install(state);
		STATES.put(server, state);
		return state;
	}

	public static LuaValue objToValue(LuaState state, Object obj) {
		if (obj == null)
			return Constants.NIL;
		if (obj instanceof Boolean)
			return ValueFactory.valueOf((Boolean) obj);
		if (obj instanceof Integer)
			return ValueFactory.valueOf((Integer) obj);
		if (obj instanceof Double)
			return ValueFactory.valueOf((Double) obj);
		if (obj instanceof String)
			return ValueFactory.valueOf((String) obj);
		if (obj instanceof byte[])
			return ValueFactory.valueOf((byte[]) obj);
		if (obj instanceof EBStorage)
			return StarotaLib.storageToValue((EBStorage) obj);
		return ConversionHandler.convertToLua(state, obj);
	}

	public static Object valueToObj(LuaState state, LuaValue val) {
		if (val == null || val instanceof LuaNil)
			return null;
		if (val.isBoolean())
			return val.toBoolean();
		if (val.isString())
			return val.toString();
		if (val.isInteger())
			return val.toInteger();
		if (val.isNumber())
			return val.toDouble();
		return ConversionHandler.convertToJava(state, val);
	}

	public static LuaValue getEvent(GuildEvent event) {
		IGuild server = event.getGuild();
		LuaTable ret = new LuaTable();
		ret.rawset("server", getServer(event.getGuild()));
		if (event instanceof ChannelEvent)
			ret.rawset("channel", getChannel(server, ((ChannelEvent) event).getChannel()));
		if (event instanceof MessageEvent) {
			ret.rawset("user", getUser(server, ((MessageEvent) event).getAuthor()));
			ret.rawset("message",
					ValueFactory.valueOf(((MessageEvent) event).getMessage().getFormattedContent()));
		}
		if (event instanceof ReactionEvent)
			ret.rawset("reaction",
					ValueFactory.valueOf(((ReactionEvent) event).getReaction().getEmoji().getName()));
		return ret;
	}

	protected static LuaValue getServer(IGuild server) {
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
					usersT.rawset(i, getUser(server, users.get(i)));
				return usersT;
			}
		});
		serverT.rawset("getRoles", new ZeroArgFunction() {

			@Override
			public LuaValue call(LuaState state) throws LuaError {
				LuaTable rolesT = new LuaTable();
				List<IRole> roles = server.getRoles();
				for (int i = 0; i < roles.size(); i++)
					rolesT.rawset(i, getRole(server, roles.get(i)));
				return rolesT;
			}
		});
		serverT.rawset("getChannels", new ZeroArgFunction() {

			@Override
			public LuaValue call(LuaState state) throws LuaError {
				LuaTable rolesT = new LuaTable();
				List<IChannel> roles = server.getChannels();
				for (int i = 0; i < roles.size(); i++)
					rolesT.rawset(i, getChannel(server, roles.get(i)));
				return rolesT;
			}
		});
		serverT.rawset("getChannelsByName", new OneArgFunction() {

			@Override
			public LuaValue call(LuaState state, LuaValue arg) throws LuaError {
				LuaTable table = new LuaTable();
				List<IChannel> channels = server.getChannelsByName(arg.toString());
				for (int i = 0; i < channels.size(); i++)
					table.rawset(i, getChannel(server, channels.get(i)));
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
				return getChannel(server, server.getChannelByID(channelId));
			}
		});
		return serverT;
	}

	protected static LuaValue getUser(IGuild server, IUser user) {
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
					rolesT.rawset(i, getRole(server, roles.get(i)));
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

	protected static LuaValue getRole(IGuild server, IRole role) {
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

	protected static LuaValue getChannel(IGuild server, IChannel channel) {
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
					tbl.rawset(i, getUser(server, users.get(i)));
				return tbl;
			}
		});
		return channelT;
	}

}
