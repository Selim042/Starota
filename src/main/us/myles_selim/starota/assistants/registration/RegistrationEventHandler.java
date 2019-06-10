package us.myles_selim.starota.assistants.registration;

import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.GuildCreateEvent;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IPrivateChannel;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.handle.obj.Permissions;
import sx.blah.discord.util.EmbedBuilder;
import sx.blah.discord.util.RequestBuffer;
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.misc.utils.ImageHelper;
import us.myles_selim.starota.misc.utils.OcrHelper;
import us.myles_selim.starota.misc.utils.StarotaConstants;
import us.myles_selim.starota.profiles.PlayerProfile;
import us.myles_selim.starota.wrappers.StarotaServer;

public class RegistrationEventHandler {

	@EventSubscriber
	public void onServerCreate(GuildCreateEvent event) {
		if (!event.getGuild().getUsers().contains(
				RegistrationBot.CLIENT.getUserByID(StarotaConstants.STAROTA_ID))) {
			IPrivateChannel pm = event.getGuild().getOwner().getOrCreatePMChannel();
			pm.sendMessage("This bot requires Starota to also be added to the server.  "
					+ "Follow the link below to add it.  "
					+ "Please re-add this bot after adding Starota."
					+ "\nhttps://discordbots.org/bot/489245655710040099");
			event.getGuild().leave();
			return;
		}
		Starota.submitStats();
		RegistrationBot.updateOwners();
		IGuild server = event.getGuild();
		if (!server.getUsers().contains(RegistrationBot.CLIENT.getOurUser()))
			return;
		IUser selimUser = RegistrationBot.CLIENT.fetchUser(StarotaConstants.SELIM_USER_ID);
		RequestBuffer.request(() -> {
			IPrivateChannel selimPm = selimUser.getOrCreatePMChannel();
			selimPm.sendMessage(
					RegistrationBot.BOT_NAME + " was added to the server: " + server.getName());
		});
		RequestBuffer.request(() -> {
			IUser serverOwner = server.getOwner();
			IPrivateChannel ownerPm = serverOwner.getOrCreatePMChannel();
			EmbedBuilder builder = new EmbedBuilder();
			String ourName = RegistrationBot.getOurName(server);
			builder.withTitle("Thanks for using " + ourName + "!");
			builder.appendDesc("If you need any assistance with " + ourName
					+ " or it's features, feel free to join our support server at "
					+ StarotaConstants.SUPPORT_SERVER_LINK);
			ownerPm.sendMessage(builder.build());
		});
		if (!RegistrationBot.CLIENT.getOurUser().getPermissionsForGuild(server)
				.contains(Permissions.SEND_MESSAGES)) {
			IUser serverOwner = server.getOwner();
			IPrivateChannel ownerPm = serverOwner.getOrCreatePMChannel();
			ownerPm.sendMessage(RegistrationBot.getOurName(server)
					+ " requires the `SEND_MESSAGES` permission for all command functionality.");
		}
	}

	@EventSubscriber
	public void onMessage(MessageReceivedEvent event) {
		if (event.getAuthor().isBot() || event.getMessage().getAttachments().size() != 1)
			return;
		StarotaServer server = StarotaServer.getServer(event.getGuild());
		IUser author = event.getAuthor();
		if (!server.hasProfile(author)) {
			PlayerProfile profile = OcrHelper.getProfile(server,
					ImageHelper.getImage(event.getMessage().getAttachments().get(0).getUrl()));
			if (profile == null) {
				event.getChannel().sendMessage("profile == null");
				return;
			}
			profile.setDiscordId(author.getLongID()).toEmbed(server);
			server.setProfile(author, profile);
			event.getChannel().sendMessage(profile.toEmbed(server));
		}
	}

}
