package us.myles_selim.starota.assistants.registration;

import java.util.Set;

import discord4j.core.event.domain.guild.GuildCreateEvent;
import discord4j.core.event.domain.lifecycle.ReadyEvent;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Attachment;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.PrivateChannel;
import discord4j.core.object.entity.User;
import discord4j.core.object.util.Permission;
import discord4j.core.object.util.Snowflake;
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.misc.utils.EmbedBuilder;
import us.myles_selim.starota.misc.utils.EventListener;
import us.myles_selim.starota.misc.utils.ImageHelper;
import us.myles_selim.starota.misc.utils.OcrHelper;
import us.myles_selim.starota.misc.utils.StarotaConstants;
import us.myles_selim.starota.profiles.PlayerProfile;
import us.myles_selim.starota.wrappers.StarotaServer;

public class RegistrationEventHandler implements EventListener {

	private Set<ReadyEvent.Guild> guildsWereIn;

	private boolean isInGuildAtStart(Snowflake guildId) {
		for (ReadyEvent.Guild g : guildsWereIn)
			if (g.getId().asLong() == guildId.asLong()) {
				System.out.println("was in guild at start");
				return true;
			}
		return false;
	}

	@EventSubscriber
	public void onReady(ReadyEvent event) {
		guildsWereIn = event.getGuilds();
	}

	@EventSubscriber
	public void onServerCreate(GuildCreateEvent event) {
		if (isInGuildAtStart(event.getGuild().getId()))
			return;
		if (!event.getGuild().getMembers().any((m) -> m.getId().equals(StarotaConstants.STAROTA_ID))
				.block()) {
			PrivateChannel pm = event.getGuild().getOwner().block().getPrivateChannel().block();
			pm.createMessage("This bot requires Starota to also be added to the server.  "
					+ "Follow the link below to add it.  "
					+ "Please re-add this bot after adding Starota."
					+ "\nhttps://discordbots.org/bot/489245655710040099");
			event.getGuild().leave();
			return;
		}
		Starota.submitStats();
		RegistrationBot.updateOwners();
		Guild server = event.getGuild();
		if (!server.getMembers().collectList().block().contains(RegistrationBot.CLIENT.getSelf()))
			return;
		User selimUser = RegistrationBot.CLIENT.getUserById(StarotaConstants.SELIM_USER_ID).block();
		PrivateChannel selimPm = selimUser.getPrivateChannel().block();
		selimPm.createMessage(
				RegistrationBot.BOT_NAME + " was added to the server: " + server.getName());

		User serverOwner = server.getOwner().block();
		PrivateChannel ownerPm = serverOwner.getPrivateChannel().block();
		EmbedBuilder builder = new EmbedBuilder();
		String ourName = RegistrationBot.getOurName(server);
		builder.withTitle("Thanks for using " + ourName + "!");
		builder.appendDesc("If you need any assistance with " + ourName
				+ " or it's features, feel free to join our support server at "
				+ StarotaConstants.SUPPORT_SERVER_LINK);
		ownerPm.createEmbed(builder.build());
		if (!RegistrationBot.CLIENT.getSelf().block().asMember(server.getId()).block()
				.getBasePermissions().block().contains(Permission.SEND_MESSAGES)) {
			ownerPm.createMessage(RegistrationBot.getOurName(server)
					+ " requires the `SEND_MESSAGES` permission for all command functionality.");
		}
	}

	@EventSubscriber
	public void onMessage(MessageCreateEvent event) {
		if (event.getMember().get().isBot() || event.getMessage().getAttachments().size() != 1)
			return;
		StarotaServer server = StarotaServer.getServer(event.getGuild().block());
		Member author = event.getMember().get();
		if (!server.hasProfile(author)) {
			PlayerProfile profile = OcrHelper.getProfile(server, ImageHelper.getImage(
					event.getMessage().getAttachments().toArray(new Attachment[0])[0].getUrl()));
			if (profile == null)
				return;
			profile.setDiscordId(author.getId().asLong()).toEmbed(server);
			server.setProfile(author, profile);
			event.getMessage().getChannel().block().createEmbed(profile.toEmbed(server));
		}
	}

}
