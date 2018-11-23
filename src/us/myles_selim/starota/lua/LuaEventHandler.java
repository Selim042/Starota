package us.myles_selim.starota.lua;

import org.squiddev.cobalt.LuaError;
import org.squiddev.cobalt.LuaState;
import org.squiddev.cobalt.LuaTable;
import org.squiddev.cobalt.LuaUserdata;
import org.squiddev.cobalt.LuaValue;
import org.squiddev.cobalt.function.LuaFunction;

import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.GuildEvent;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.util.RequestBuffer;
import us.myles_selim.starota.lua.libraries.DiscordEventLib;

public class LuaEventHandler {

	// private boolean handle = true;

	@EventSubscriber
	public void onDiscordEvent(GuildEvent event) {
		// if (event instanceof MessageReceivedEvent)
		// if (((MessageReceivedEvent)
		// event).getMessage().getContent().equals("toggle"))
		// handle = !handle;
		// if (!handle)
		// return;
		// DiscordEventLib.handleEvent(event);
		handleEvent(event);
	}

	private static void handleEvent(GuildEvent event) {
		IGuild server = event.getGuild();
		LuaState state = LuaUtils.getState(server);
		LuaTable _G = state.getMainThread().getfenv();
		String functName = "on" + event.getClass().getSimpleName();
		IChannel errorChannel;
		LuaValue eventL = LuaUtils.getEvent(state, event);
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
			System.out.println("event fired: " + functName + " on server: " + server.getName());
			if (errorChannel != null)
				RequestBuffer
						.request(() -> errorChannel.sendMessage("There was an error when processing a "
								+ functName + " event: " + e.getLocalizedMessage()));
			e.printStackTrace();
		}
	}

}
