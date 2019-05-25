package us.myles_selim.starota.assistants.points;

import java.util.LinkedList;
import java.util.List;

import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.GuildCreateEvent;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IMessage.Attachment;
import sx.blah.discord.handle.obj.IPrivateChannel;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.handle.obj.Permissions;
import sx.blah.discord.util.EmbedBuilder;
import sx.blah.discord.util.RequestBuffer;
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.misc.utils.IJournalEntry;
import us.myles_selim.starota.misc.utils.ImageHelper;
import us.myles_selim.starota.misc.utils.OcrHelper;
import us.myles_selim.starota.misc.utils.StarotaConstants;
import us.myles_selim.starota.wrappers.StarotaServer;

public class PointEventHandler {

	@EventSubscriber
	public void onServerCreate(GuildCreateEvent event) {
		if (!event.getGuild().getUsers()
				.contains(PointBot.CLIENT.getUserByID(StarotaConstants.STAROTA_ID))) {
			IPrivateChannel pm = event.getGuild().getOwner().getOrCreatePMChannel();
			pm.sendMessage("This bot has additional content including leaderboards if "
					+ Starota.BOT_NAME + " is also in your server.\n" + "For more information visit "
					+ "\nhttps://discordbots.org/bot/489245655710040099");
			return;
		}
		Starota.submitStats();
		PointBot.updateOwners();
		IGuild server = event.getGuild();
		if (!server.getUsers().contains(PointBot.CLIENT.getOurUser()))
			return;
		IUser selimUser = PointBot.CLIENT.fetchUser(StarotaConstants.SELIM_USER_ID);
		RequestBuffer.request(() -> {
			IPrivateChannel selimPm = selimUser.getOrCreatePMChannel();
			selimPm.sendMessage(PointBot.BOT_NAME + " was added to the server: " + server.getName());
		});
		RequestBuffer.request(() -> {
			IUser serverOwner = server.getOwner();
			IPrivateChannel ownerPm = serverOwner.getOrCreatePMChannel();
			EmbedBuilder builder = new EmbedBuilder();
			String ourName = PointBot.getOurName(server);
			builder.withTitle("Thanks for using " + ourName + "!");
			builder.appendDesc("If you need any assistance with " + ourName
					+ " or it's features, feel free to join our support server at "
					+ StarotaConstants.SUPPORT_SERVER_LINK);
			ownerPm.sendMessage(builder.build());
		});
		if (!PointBot.CLIENT.getOurUser().getPermissionsForGuild(server)
				.contains(Permissions.SEND_MESSAGES)) {
			IUser serverOwner = server.getOwner();
			IPrivateChannel ownerPm = serverOwner.getOrCreatePMChannel();
			ownerPm.sendMessage(PointBot.getOurName(server)
					+ " requires the `SEND_MESSAGES` permission for all command functionality.");
		}
	}

	@EventSubscriber
	public void onMessage(MessageReceivedEvent event) {
		if (event.getAuthor().isBot() || event.getMessage().getAttachments().size() != 1)
			return;
		StarotaServer server = StarotaServer.getServer(event.getGuild());
		IMessage msg = event.getMessage();
		String msgS = msg.getContent();
		if (msgS.startsWith(server.getPrefix()) || msg.getAttachments().size() <= 0)
			return;
		StringBuilder out = new StringBuilder("```\n");
		List<IJournalEntry> entries = new LinkedList<>();
		for (Attachment attach : msg.getAttachments())
			entries.addAll(OcrHelper.getJournalEntries(server, ImageHelper.getImage(attach.getUrl())));
		for (IJournalEntry entry : entries)
			out.append(entry.toString() + "\n");
		out.append("```");
		msg.reply(out.toString());
	}

}
