package us.myles_selim.starota.assistants.pokedex;

import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.GuildCreateEvent;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IPrivateChannel;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.handle.obj.Permissions;
import sx.blah.discord.util.EmbedBuilder;
import sx.blah.discord.util.RequestBuffer;
import us.myles_selim.starota.Starota;

public class PokedexEventHandler {

	@EventSubscriber
	public void onServerJoin(GuildCreateEvent event) {
		Starota.submitStats();
	}

	@EventSubscriber
	public void onServerCreate(GuildCreateEvent event) {
		Starota.submitStats();
		IGuild server = event.getGuild();
		if (!server.getUsers().contains(PokedexBot.POKEDEX_CLIENT.getOurUser()))
			return;
		IUser selimUser = PokedexBot.POKEDEX_CLIENT.fetchUser(Starota.SELIM_USER_ID);
		RequestBuffer.request(() -> {
			IPrivateChannel selimPm = selimUser.getOrCreatePMChannel();
			selimPm.sendMessage(PokedexBot.BOT_NAME + " was added to the server: " + server.getName());
		});
		RequestBuffer.request(() -> {
			IUser serverOwner = server.getOwner();
			IPrivateChannel ownerPm = serverOwner.getOrCreatePMChannel();
			EmbedBuilder builder = new EmbedBuilder();
			String ourName = PokedexBot.getOurName(server);
			builder.withTitle("Thanks for using " + ourName + "!");
			builder.appendDesc("If you need any assistance with " + ourName
					+ " or it's features, feel free to join our support server at "
					+ Starota.SUPPORT_SERVER_LINK);
			ownerPm.sendMessage(builder.build());
		});
		if (!PokedexBot.POKEDEX_CLIENT.getOurUser().getPermissionsForGuild(server)
				.contains(Permissions.SEND_MESSAGES)) {
			IUser serverOwner = server.getOwner();
			IPrivateChannel ownerPm = serverOwner.getOrCreatePMChannel();
			ownerPm.sendMessage(PokedexBot.getOurName(server)
					+ " requires the `SEND_MESSAGES` permission for all command functionality.");
		}
	}

}
