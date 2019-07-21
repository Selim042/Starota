package us.myles_selim.starota.assistants.points;

import java.util.LinkedList;
import java.util.List;

import discord4j.core.event.domain.guild.GuildCreateEvent;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Attachment;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.PrivateChannel;
import discord4j.core.object.entity.User;
import discord4j.core.object.util.Permission;
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.misc.utils.EmbedBuilder;
import us.myles_selim.starota.misc.utils.EventListener;
import us.myles_selim.starota.misc.utils.IJournalEntry;
import us.myles_selim.starota.misc.utils.ImageHelper;
import us.myles_selim.starota.misc.utils.OcrHelper;
import us.myles_selim.starota.misc.utils.StarotaConstants;
import us.myles_selim.starota.wrappers.StarotaServer;

public class PointEventHandler implements EventListener {

	@EventSubscriber
	public void onServerCreate(GuildCreateEvent event) {
		if (!event.getGuild().getMembers().collectList().block()
				.contains(PointBot.CLIENT.getUserById(StarotaConstants.STAROTA_ID).block())) {
			PrivateChannel pm = event.getGuild().getOwner().block().getPrivateChannel().block();
			pm.createMessage("This bot has additional content including leaderboards if "
					+ Starota.BOT_NAME + " is also in your server.\n" + "For more information visit "
					+ "\nhttps://discordbots.org/bot/489245655710040099").block();
			return;
		}
		Starota.submitStats();
		PointBot.updateOwners();
		Guild server = event.getGuild();
		if (!server.getMembers().collectList().block().contains(PointBot.CLIENT.getSelf().block()))
			return;
		User selimUser = PointBot.CLIENT.getUserById(StarotaConstants.SELIM_USER_ID).block();
		PrivateChannel selimPm = selimUser.getPrivateChannel().block();
		selimPm.createMessage(PointBot.BOT_NAME + " was added to the server: " + server.getName());

		User serverOwner = server.getOwner().block();
		PrivateChannel ownerPm = serverOwner.getPrivateChannel().block();
		EmbedBuilder builder = new EmbedBuilder();
		String ourName = PointBot.getOurName(server);
		builder.withTitle("Thanks for using " + ourName + "!");
		builder.appendDesc("If you need any assistance with " + ourName
				+ " or it's features, feel free to join our support server at "
				+ StarotaConstants.SUPPORT_SERVER_LINK);
		ownerPm.createEmbed(builder.build()).block();

		if (!PointBot.CLIENT.getSelf().block().asMember(server.getId()).block().getBasePermissions()
				.block().contains(Permission.SEND_MESSAGES))
			ownerPm.createMessage(PointBot.getOurName(server)
					+ " requires the `SEND_MESSAGES` permission for all command functionality.").block();
	}

	@EventSubscriber
	public void onMessage(MessageCreateEvent event) {
		if (event.getMessage().getAuthor().get().isBot()
				|| event.getMessage().getAttachments().size() != 1)
			return;
		StarotaServer server = StarotaServer.getServer(event.getGuild().block());
		Message msg = event.getMessage();
		String msgS = msg.getContent().get();
		if (msgS.startsWith(server.getPrefix()) || msg.getAttachments().size() <= 0)
			return;
		StringBuilder out = new StringBuilder("```\n");
		List<IJournalEntry> entries = new LinkedList<>();
		for (Attachment attach : msg.getAttachments())
			entries.addAll(OcrHelper.getJournalEntries(server, ImageHelper.getImage(attach.getUrl())));
		for (IJournalEntry entry : entries)
			out.append(entry.toString() + "\n");
		out.append("```");
		msg.getChannel().block()
				.createMessage(msg.getAuthor().get().getMention() + ", " + out.toString());
	}

}
