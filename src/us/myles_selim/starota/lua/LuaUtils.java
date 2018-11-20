package us.myles_selim.starota.lua;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.squiddev.cobalt.Constants;
import org.squiddev.cobalt.LuaNil;
import org.squiddev.cobalt.LuaState;
import org.squiddev.cobalt.LuaTable;
import org.squiddev.cobalt.LuaValue;
import org.squiddev.cobalt.ValueFactory;
import org.squiddev.cobalt.compiler.LuaC;
import org.squiddev.cobalt.lib.BaseLib;
import org.squiddev.cobalt.lib.CoroutineLib;
import org.squiddev.cobalt.lib.MathLib;
import org.squiddev.cobalt.lib.StringLib;
import org.squiddev.cobalt.lib.TableLib;
import org.squiddev.cobalt.lib.platform.AbstractResourceManipulator;

import sx.blah.discord.handle.impl.events.guild.GuildEvent;
import sx.blah.discord.handle.impl.events.guild.category.CategoryEvent;
import sx.blah.discord.handle.impl.events.guild.channel.ChannelEvent;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageEvent;
import sx.blah.discord.handle.impl.events.guild.member.GuildMemberEvent;
import sx.blah.discord.handle.impl.events.guild.role.RoleEvent;
import sx.blah.discord.handle.obj.IGuild;
import us.myles_selim.ebs.EBStorage;
import us.myles_selim.starota.lua.conversion.ConversionHandler;
import us.myles_selim.starota.lua.conversion.discord.ServerConverter;
import us.myles_selim.starota.lua.conversion.starota.PlayerProfileConverter;
import us.myles_selim.starota.profiles.PlayerProfile;

public class LuaUtils {

	private static final Map<IGuild, LuaState> STATES = new HashMap<>();

	private static boolean registeredConverters = false;

	public static void registerConverters() {
		if (registeredConverters)
			return;
		registeredConverters = true;

		ConversionHandler.registerConverter(PlayerProfile.class, new PlayerProfileConverter());
		ConversionHandler.registerConverter(IGuild.class, new ServerConverter());
	}

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

	public static LuaValue getEvent(LuaState state, GuildEvent event) {
		// IGuild server = event.getGuild();
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
		return ret;
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
