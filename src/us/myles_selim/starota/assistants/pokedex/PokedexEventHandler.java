package us.myles_selim.starota.assistants.pokedex;

import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.GuildCreateEvent;
import us.myles_selim.starota.Starota;

public class PokedexEventHandler {

	@EventSubscriber
	public void onServerJoin(GuildCreateEvent event) {
		Starota.submitStats();
	}

}
