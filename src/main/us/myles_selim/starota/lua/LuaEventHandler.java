package us.myles_selim.starota.lua;

import discord4j.core.event.domain.guild.GuildEvent;
import us.myles_selim.starota.misc.utils.EventListener;

public class LuaEventHandler implements EventListener<GuildEvent> {

	@Override
	public void execute(GuildEvent event) {
		LuaUtils.handleEvent(event);
	}

	@Override
	public Class<GuildEvent> getEventType() {
		return GuildEvent.class;
	}

}
