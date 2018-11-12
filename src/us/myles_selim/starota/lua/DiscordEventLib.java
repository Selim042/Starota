package us.myles_selim.starota.lua;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.squiddev.cobalt.LuaError;
import org.squiddev.cobalt.LuaState;
import org.squiddev.cobalt.LuaTable;
import org.squiddev.cobalt.LuaValue;
import org.squiddev.cobalt.ValueFactory;
import org.squiddev.cobalt.function.LuaFunction;
import org.squiddev.cobalt.lib.jse.JsePlatform;
import org.squiddev.cobalt.lib.platform.AbstractResourceManipulator;

import sx.blah.discord.handle.impl.events.guild.GuildEvent;
import sx.blah.discord.handle.impl.events.guild.channel.ChannelEvent;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageEvent;
import sx.blah.discord.handle.impl.events.guild.channel.message.reaction.ReactionEvent;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.util.RequestBuffer;

public class DiscordEventLib extends DiscordLib {

	public static final String KEY = "events";

	public DiscordEventLib(IGuild server) {
		super(server);
	}

	@Override
	public LuaValue add(LuaState state, LuaTable env) {
		super.add(state, env);
		LuaTable eventHandler = new LuaTable();
		env.rawset(KEY, eventHandler);
		// eventHandler.rawset("test", new EmptyFunction());
		return env;
	}

	protected static void handleEvent(GuildEvent event) {
		IGuild server = event.getGuild();
		LuaState state = new LuaState(new AbstractResourceManipulator() {

			private boolean hasRun = false;

			@Override
			public InputStream findResource(String filename) {
				if (hasRun)
					return null;
				hasRun = true;
				try {
					return new FileInputStream(new File(filename));
				} catch (FileNotFoundException e) {
					return null;
				}
			}
		});
		state.stdout = System.out;
		String script = "eventHandler.lua";

		LuaTable _G = JsePlatform.standardGlobals(state);
		DiscordEventLib eventLib = new DiscordEventLib(server);
		_G.load(state, eventLib);
		_G.load(state, new ServerOptionsLib(server));
		try {
			_G.get(state, "dofile").checkFunction().call(state, ValueFactory.valueOf(script));
			LuaTable eventHandler = _G.get(state, DiscordEventLib.KEY).checkTable();
			String functName = "on" + event.getClass().getSimpleName();
			LuaValue funcV = eventHandler.get(state, functName);
			System.out.println(functName);
			if (funcV == null || !funcV.isFunction())
				return;
			((LuaFunction) funcV).call(state, eventHandler, eventLib.getEvent(event));
		} catch (LuaError e) {
			if (event instanceof ChannelEvent)
				RequestBuffer.request(() -> ((ChannelEvent) event).getChannel()
						.sendMessage("lua error: " + e.getLocalizedMessage()));
			e.printStackTrace();
		}
	}

	private LuaValue getEvent(GuildEvent event) {
		LuaTable ret = new LuaTable();
		ret.rawset("server", getServer(event.getGuild()));
		if (event instanceof ChannelEvent)
			ret.rawset("channel", getChannel(((ChannelEvent) event).getChannel()));
		if (event instanceof MessageEvent) {
			ret.rawset("user", getUser(((MessageEvent) event).getAuthor()));
			ret.rawset("message",
					ValueFactory.valueOf(((MessageEvent) event).getMessage().getFormattedContent()));
		}
		if (event instanceof ReactionEvent)
			ret.rawset("reaction",
					ValueFactory.valueOf(((ReactionEvent) event).getReaction().getEmoji().getName()));
		return ret;
	}

}
