package us.myles_selim.starota.assistants.pokedex;

import discord4j.core.event.domain.guild.GuildCreateEvent;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.PrivateChannel;
import discord4j.core.object.entity.User;
import discord4j.core.object.util.Permission;
import discord4j.core.object.util.Snowflake;
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.misc.utils.EmbedBuilder;
import us.myles_selim.starota.misc.utils.EventListener;
import us.myles_selim.starota.misc.utils.StarotaConstants;

public class PokedexEventHandler implements EventListener {

	@EventSubscriber
	public void onServerCreate(GuildCreateEvent event) {
		Starota.submitStats();
		PokedexBot.updateOwners();
		Guild server = event.getGuild();
		Snowflake ourId = PokedexBot.getOurUser().getId();
		if (!server.getMembers().any((m) -> m.getId().equals(ourId)).block())
			return;
		User selimUser = PokedexBot.CLIENT.getUserById(StarotaConstants.SELIM_USER_ID).block();
		PrivateChannel selimPm = selimUser.getPrivateChannel().block();
		selimPm.createMessage(PokedexBot.BOT_NAME + " was added to the server: " + server.getName());

		Member serverOwner = server.getOwner().block();
		PrivateChannel ownerPm = serverOwner.getPrivateChannel().block();
		EmbedBuilder builder = new EmbedBuilder();
		String ourName = PokedexBot.getOurName(server);
		builder.withTitle("Thanks for using " + ourName + "!");
		builder.appendDesc("If you need any assistance with " + ourName
				+ " or it's features, feel free to join our support server at "
				+ StarotaConstants.SUPPORT_SERVER_LINK);
		ownerPm.createEmbed(builder.build()).block();

		if (!PokedexBot.CLIENT.getSelf().block().asMember(server.getId()).block().getBasePermissions()
				.block().contains(Permission.SEND_MESSAGES))
			ownerPm.createMessage(PokedexBot.getOurName(server)
					+ " requires the `SEND_MESSAGES` permission for all command functionality.").block();
	}

}
