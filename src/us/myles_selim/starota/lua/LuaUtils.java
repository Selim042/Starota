package us.myles_selim.starota.lua;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.squiddev.cobalt.Constants;
import org.squiddev.cobalt.LuaError;
import org.squiddev.cobalt.LuaNil;
import org.squiddev.cobalt.LuaState;
import org.squiddev.cobalt.LuaTable;
import org.squiddev.cobalt.LuaUserdata;
import org.squiddev.cobalt.LuaValue;
import org.squiddev.cobalt.ValueFactory;
import org.squiddev.cobalt.compiler.LuaC;
import org.squiddev.cobalt.function.LuaFunction;
import org.squiddev.cobalt.function.VarArgFunction;
import org.squiddev.cobalt.lib.BaseLib;
import org.squiddev.cobalt.lib.CoroutineLib;
import org.squiddev.cobalt.lib.MathLib;
import org.squiddev.cobalt.lib.StringLib;
import org.squiddev.cobalt.lib.TableLib;
import org.squiddev.cobalt.lib.platform.AbstractResourceManipulator;

import sx.blah.discord.handle.impl.events.guild.GuildEvent;
import sx.blah.discord.handle.impl.events.guild.category.CategoryEvent;
import sx.blah.discord.handle.impl.events.guild.channel.ChannelEvent;
import sx.blah.discord.handle.impl.events.guild.channel.TypingEvent;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageEvent;
import sx.blah.discord.handle.impl.events.guild.member.GuildMemberEvent;
import sx.blah.discord.handle.impl.events.guild.role.RoleEvent;
import sx.blah.discord.handle.obj.ICategory;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IRole;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.RequestBuffer;
import us.myles_selim.ebs.EBStorage;
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.Starota.BaseModules;
import us.myles_selim.starota.leaderboards.Leaderboard;
import us.myles_selim.starota.lua.conversion.ConversionHandler;
import us.myles_selim.starota.lua.conversion.discord.CategoryConverter;
import us.myles_selim.starota.lua.conversion.discord.ChannelConverter;
import us.myles_selim.starota.lua.conversion.discord.MessageConverter;
import us.myles_selim.starota.lua.conversion.discord.RoleConverter;
import us.myles_selim.starota.lua.conversion.discord.ServerConverter;
import us.myles_selim.starota.lua.conversion.discord.UserConverter;
import us.myles_selim.starota.lua.conversion.starota.EnumPokemonConverter;
import us.myles_selim.starota.lua.conversion.starota.LeaderboardConverter;
import us.myles_selim.starota.lua.conversion.starota.PlayerProfileConverter;
import us.myles_selim.starota.lua.conversion.starota.TradeboardPostConverter;
import us.myles_selim.starota.lua.events.LuaEvent;
import us.myles_selim.starota.lua.libraries.DiscordEventLib;
import us.myles_selim.starota.lua.libraries.DiscordLib;
import us.myles_selim.starota.lua.libraries.StarotaLib;
import us.myles_selim.starota.modules.StarotaModule;
import us.myles_selim.starota.profiles.PlayerProfile;
import us.myles_selim.starota.trading.EnumPokemon;
import us.myles_selim.starota.trading.TradeboardPost;
import us.myles_selim.starota.wrappers.StarotaServer;

public class LuaUtils {

	private static final Map<Long, LuaState> STATES = new HashMap<>();

	private static boolean registeredConverters = false;

	public static void registerConverters() {
		if (registeredConverters)
			return;
		registeredConverters = true;

		ConversionHandler.registerConverter(PlayerProfile.class, new PlayerProfileConverter());
		ConversionHandler.registerConverter(TradeboardPost.class, new TradeboardPostConverter());
		ConversionHandler.registerConverter(EnumPokemon.class, new EnumPokemonConverter());
		ConversionHandler.registerConverter(Leaderboard.class, new LeaderboardConverter());

		ConversionHandler.registerConverter(ICategory.class, new CategoryConverter());
		ConversionHandler.registerConverter(IChannel.class, new ChannelConverter());
		ConversionHandler.registerConverter(IMessage.class, new MessageConverter());
		ConversionHandler.registerConverter(IRole.class, new RoleConverter());
		ConversionHandler.registerConverter(IGuild.class, new ServerConverter());
		ConversionHandler.registerConverter(IUser.class, new UserConverter());
	}

	public static LuaState getState(StarotaServer server) {
		if (!StarotaModule.isModuleEnabled(server, BaseModules.LUA))
			return null;
		if (STATES.containsKey(server.getDiscordGuild().getLongID()))
			return STATES.get(server.getDiscordGuild().getLongID());
		LuaState state = new LuaState(new AbstractResourceManipulator() {

			@Override
			public InputStream findResource(String filename) {
				return null;
			}
		});
		// state.stdout = null;
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
		if (!Starota.IS_DEV)
			_G.rawset("print", new VarArgFunction() {});
		LuaC.install(state);
		STATES.put(server.getDiscordGuild().getLongID(), state);
		return state;
	}

	public static boolean isInitialized(StarotaServer server) {
		return STATES.containsKey(server.getDiscordGuild().getLongID());
	}

	public static void clearEventHandlers(StarotaServer server) {
		if (STATES.containsKey(server.getDiscordGuild().getLongID()))
			clearEventHandlers(getState(server));
	}

	public static void clearEventHandlers(LuaState state) {
		LuaTable env = state.getMainThread().getfenv();
		env.rawset("events", new LuaTable());
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

	public static LuaValue getEvent(LuaState state, Object event) {
		if (event instanceof GuildEvent)
			return getEvent(state, (GuildEvent) event);
		if (event instanceof LuaEvent)
			return ((LuaEvent) event).toLua(state);
		throw new RuntimeException(
				"event must be a LuaEvent or GuildEvent, got " + event.getClass().getName());
	}

	private static LuaValue getEvent(LuaState state, GuildEvent event) {
		LuaTable ret = new LuaTable();
		ret.rawset("server", ConversionHandler.convertToLua(state, event.getGuild()));
		if (event instanceof ChannelEvent)
			ret.rawset("channel",
					ConversionHandler.convertToLua(state, ((ChannelEvent) event).getChannel()));
		if (event instanceof CategoryEvent)
			ret.rawset("category",
					ConversionHandler.convertToLua(state, ((CategoryEvent) event).getCategory()));
		if (event instanceof MessageEvent) {
			ret.rawset("user",
					ConversionHandler.convertToLua(state, ((MessageEvent) event).getAuthor()));
			ret.rawset("message",
					ConversionHandler.convertToLua(state, ((MessageEvent) event).getMessage()));
			ret.rawset("category", ConversionHandler.convertToLua(state,
					((MessageEvent) event).getChannel().getCategory()));
		}
		// TODO: Add reaction support
		// if (event instanceof ReactionEvent)
		// ret.rawset("reaction",
		// ValueFactory.valueOf(((ReactionEvent)
		// event).getReaction().getEmoji().getName()));
		if (event instanceof GuildMemberEvent)
			ret.rawset("user",
					ConversionHandler.convertToLua(state, ((GuildMemberEvent) event).getUser()));
		if (event instanceof RoleEvent)
			ret.rawset("role", ConversionHandler.convertToLua(state, ((RoleEvent) event).getRole()));

		if (event instanceof TypingEvent)
			ret.rawset("user", ConversionHandler.convertToLua(state, ((TypingEvent) event).getUser()));
		return ret;
	}

	public static void handleEvent(Object event) {
		StarotaServer server = null;
		LuaValue eventL = null;
		LuaState state = null;
		if (event instanceof GuildEvent) {
			server = StarotaServer.getServer(((GuildEvent) event).getGuild());
			state = getState(server);
			eventL = getEvent(state, (GuildEvent) event);
		} else if (event instanceof LuaEvent) {
			server = ((LuaEvent) event).getServer();
			state = getState(server);
			eventL = getEvent(state, (LuaEvent) event);
		}
		if (state == null || !StarotaModule.isModuleEnabled(server, BaseModules.LUA))
			return;
		handleEvent(server, event, state, eventL);
	}

	private static void handleEvent(StarotaServer server, Object event, LuaState state,
			LuaValue eventL) {
		LuaTable _G = state.getMainThread().getfenv();
		String functName = "on" + event.getClass().getSimpleName();
		IChannel errorChannel;
		if (eventL.isTable() && ((LuaTable) eventL).rawget("channel") instanceof LuaUserdata)
			errorChannel = (IChannel) ((LuaUserdata) ((LuaTable) eventL).rawget("channel")).instance;
		else
			errorChannel = null;
		try {
			LuaTable eventLib = _G.rawget(DiscordEventLib.KEY).checkTable();
			LuaValue funcV = eventLib.get(state, functName);
			// for (LuaValue v : eventLib.keys()) {
			// System.out.println("event handler: " + v);
			// }
			// System.out.println("event fired: " + functName);
			if (funcV == null || !funcV.isFunction())
				return;
			((LuaFunction) funcV).call(state, eventLib, eventL);
		} catch (LuaError e) {
			System.out.println(
					"event fired: " + functName + " on server: " + server.getDiscordGuild().getName());
			if (errorChannel != null)
				RequestBuffer
						.request(() -> errorChannel.sendMessage("There was an error when processing a "
								+ functName + " event: " + e.getLocalizedMessage()));
			e.printStackTrace();
		}
	}

	// protected static LuaValue getRole(IGuild server, IRole role) {
	// if (role == null)
	// return Constants.NIL;
	// LuaTable roleT = new LuaTable();
	// roleT.rawset("getId", new ZeroArgFunction() {
	//
	// @Override
	// public LuaValue call(LuaState state) throws LuaError {
	// return ValueFactory.valueOf(role.getStringID());
	// }
	// });
	// roleT.rawset("getName", new ZeroArgFunction() {
	//
	// @Override
	// public LuaValue call(LuaState state) throws LuaError {
	// return ValueFactory.valueOf(role.getName());
	// }
	// });
	// roleT.rawset("getColor", new ZeroArgFunction() {
	//
	// @Override
	// public LuaValue call(LuaState state) throws LuaError {
	// return ValueFactory.valueOf(role.getColor().getRGB());
	// }
	// });
	// return roleT;
	// }
	//
	// protected static LuaValue getChannel(IGuild server, IChannel channel) {
	// if (channel == null)
	// return Constants.NIL;
	// LuaTable channelT = new LuaTable();
	// channelT.rawset("getName", new ZeroArgFunction() {
	//
	// @Override
	// public LuaValue call(LuaState state) throws LuaError {
	// return ValueFactory.valueOf(channel.getName());
	// }
	// });
	// channelT.rawset("sendMessage", new OneArgFunction() {
	//
	// @Override
	// public LuaValue call(LuaState state, LuaValue arg) throws LuaError {
	// if (arg instanceof LuaNil)
	// return Constants.FALSE;
	// if (channel.getModifiedPermissions(Starota.getOurUser())
	// .contains(Permissions.SEND_MESSAGES))
	// return RequestBuffer.request(() -> {
	// try {
	// channel.sendMessage(arg.toString());
	// } catch (DiscordException e) {
	// return Constants.FALSE;
	// }
	// return Constants.TRUE;
	// }).get();
	// else
	// return Constants.FALSE;
	// }
	// });
	// channelT.rawset("getCategory", new ZeroArgFunction() {
	//
	// @Override
	// public LuaValue call(LuaState state) throws LuaError {
	// ICategory cat = channel.getCategory();
	// if (cat == null)
	// return Constants.NIL;
	// return ValueFactory.valueOf(cat.getName());
	// }
	// });
	// channelT.rawset("getUsersHere", new ZeroArgFunction() {
	//
	// @Override
	// public LuaValue call(LuaState state) throws LuaError {
	// LuaTable tbl = new LuaTable();
	// List<IUser> users = channel.getUsersHere();
	// for (int i = 0; i < users.size(); i++)
	// tbl.rawset(i, getUser(server, users.get(i)));
	// return tbl;
	// }
	// });
	// return channelT;
	// }

}
