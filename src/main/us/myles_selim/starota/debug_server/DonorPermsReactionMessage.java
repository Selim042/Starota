package us.myles_selim.starota.debug_server;

import java.util.List;
import java.util.function.Consumer;

import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.TextChannel;
import discord4j.core.object.reaction.ReactionEmoji;
import discord4j.core.spec.EmbedCreateSpec;
import us.myles_selim.ebs.Storage;
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.enums.EnumDonorPerm;
import us.myles_selim.starota.misc.utils.EmbedBuilder;
import us.myles_selim.starota.misc.utils.EmojiServerHelper;
import us.myles_selim.starota.misc.utils.RolePermHelper;
import us.myles_selim.starota.reaction_messages.PersistReactionMessage;
import us.myles_selim.starota.wrappers.StarotaServer;

public class DonorPermsReactionMessage extends PersistReactionMessage {

	public static final String LEFT_EMOJI = "⬅";
	public static final String RIGHT_EMOJI = "➡";
	public static final String REFRESH_EMOJI = "🔄";
	public static final int SERVERS_PER_PAGE = 20;

	private int index = 0;

	@Override
	public void onSend(StarotaServer server, TextChannel channel, Message msg) {
		msg.addReaction(ReactionEmoji.unicode(LEFT_EMOJI)).block();
		msg.addReaction(ReactionEmoji.unicode(RIGHT_EMOJI)).block();
		msg.addReaction(ReactionEmoji.unicode(REFRESH_EMOJI)).block();
	}

	@Override
	public void onReactionAdded(StarotaServer server, TextChannel channel, Message msg, Member user,
			discord4j.core.object.reaction.ReactionEmoji react) {
		int numPages = (Starota.getClient().getGuilds().collectList().block().size()
				- EmojiServerHelper.getNumberServers() - 1) / SERVERS_PER_PAGE;
		if (react.asUnicodeEmoji().get().getRaw().equals(LEFT_EMOJI)) {
			if (index <= 0)
				index = numPages;
			else
				index--;
		} else if (react.asUnicodeEmoji().get().getRaw().equals(RIGHT_EMOJI)) {
			if (index >= numPages)
				index = 0;
			else
				index++;
		}
		this.editMessage(channel, msg);
		msg.removeReaction(react, user.getId()).block();
	}

	@Override
	protected Consumer<? super EmbedCreateSpec> getEmbed(StarotaServer server) {
		if (!Starota.FULLY_STARTED)
			return DebugServer.getNotReadyEmbed();
		EmbedBuilder builder = new EmbedBuilder();
		List<Guild> guilds = Starota.getClient().getGuilds().collectList().block();
		int numGuilds = guilds.size() - EmojiServerHelper.getNumberServers() - 1;
		builder.withTitle(
				"Donor Perms: (" + (index + 1) + "/" + (numGuilds / SERVERS_PER_PAGE + 1) + ")");
		int displayed = 0;
		for (int i = 0; i < guilds.size() && displayed < SERVERS_PER_PAGE
				&& (SERVERS_PER_PAGE * index) + i < guilds.size(); i++) {
			Guild g = guilds.get((SERVERS_PER_PAGE * index) + i);
			if (EmojiServerHelper.isEmojiServer(g) || g.getId().asLong() == DebugServer.DEBUG_SERVER_ID)
				continue;
			List<EnumDonorPerm> perms = RolePermHelper.getDonorPerms(g);
			boolean hasAll = true;
			if (perms == null)
				hasAll = false;
			else {
				for (EnumDonorPerm p : EnumDonorPerm.values()) {
					if (!perms.contains(p)) {
						hasAll = false;
						break;
					}
				}
			}
			if (hasAll) {
				builder.appendField(g.getName(), " - All donor permissions", true);
				continue;
			} else if (perms == null || perms.isEmpty()) {
				builder.appendField(g.getName(), " - No donor permissions", true);
				continue;
			} else {
				String text = "";
				for (EnumDonorPerm p : perms)
					text += " - " + p + "\n";
				builder.appendField(g.getName(), text, true);
			}
			displayed++;
		}
		builder.withFooterText("Last updated");
		builder.withTimestamp(System.currentTimeMillis());
		return builder.build();
	}

	@Override
	public void toBytes(Storage stor) {
		stor.writeInt(index);
	}

	@Override
	public void fromBytes(Storage stor) {
		index = stor.readInt();
	}

}
