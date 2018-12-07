package us.myles_selim.starota.lua;

import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.GuildEvent;
import us.myles_selim.starota.Starota;

public class LuaEventHandler {

	// private boolean handle = true;

	@EventSubscriber
	public void onDiscordEvent(GuildEvent event) {
		if (event.getGuild() == null || event.getGuild().getLongID() == Starota.SUPPORT_SERVER)
			return;
		// if (event instanceof MessageReceivedEvent)
		// if (((MessageReceivedEvent)
		// event).getMessage().getContent().equals("toggle"))
		// handle = !handle;
		// if (!handle)
		// return;
		// DiscordEventLib.handleEvent(event);
		LuaUtils.handleEvent(event);
	}

}
