package us.myles_selim.starota.lua;

import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.GuildEvent;

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
		DiscordEventLib.handleEvent(event);
	}

}
