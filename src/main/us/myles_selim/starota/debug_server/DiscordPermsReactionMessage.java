package us.myles_selim.starota.debug_server;

import java.util.EnumSet;
import java.util.List;
import java.util.function.Consumer;

import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.TextChannel;
import discord4j.core.object.entity.User;
import discord4j.core.object.reaction.ReactionEmoji;
import discord4j.core.object.util.Permission;
import discord4j.core.spec.EmbedCreateSpec;
import us.myles_selim.ebs.Storage;
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.misc.data_types.EmbedBuilder;
import us.myles_selim.starota.misc.utils.EmojiServerHelper;
import us.myles_selim.starota.reaction_messages.PersistReactionMessage;
import us.myles_selim.starota.wrappers.StarotaServer;

public class DiscordPermsReactionMessage extends PersistReactionMessage {

	public static final String LEFT_EMOJI = "⬅";
	public static final String RIGHT_EMOJI = "➡";
	public static final String REFRESH_EMOJI = "🔄";
	public static final int SERVERS_PER_PAGE = 20;

	private int index = 0;

	@Override
	public void onSend(StarotaServer server, TextChannel channel, Message msg) {
		msg.addReaction(ReactionEmoji.unicode(LEFT_EMOJI));
		msg.addReaction(ReactionEmoji.unicode(RIGHT_EMOJI));
		msg.addReaction(ReactionEmoji.unicode(REFRESH_EMOJI));
	}

	@Override
	public void onReactionAdded(StarotaServer server, TextChannel channel, Message msg, Member member,
			ReactionEmoji react) {
		int numPages = (Starota.getClient().getGuilds().collectList().block().size()
				- EmojiServerHelper.getNumberServers() - 1) / SERVERS_PER_PAGE;
		if (react.toString().equals(LEFT_EMOJI)) {
			if (index <= 0)
				index = numPages;
			else
				index--;
		} else if (react.toString().equals(RIGHT_EMOJI)) {
			if (index >= numPages)
				index = 0;
			else
				index++;
		}
		this.editMessage(channel, msg);
		msg.removeReaction(react, member.getId());
	}

	@Override
	protected Consumer<EmbedCreateSpec> getEmbed(StarotaServer server) {
		if (!Starota.FULLY_STARTED)
			return DebugServer.NOT_READY_EMBED;
		EmbedBuilder builder = new EmbedBuilder();
		List<Guild> guilds = Starota.getClient().getGuilds().collectList().block();
		int numGuilds = guilds.size() - EmojiServerHelper.getNumberServers() - 1;
		User ourUser = Starota.getSelf();
		builder.withTitle("Missing Discord Perms: (" + (index + 1) + "/"
				+ (numGuilds / SERVERS_PER_PAGE + 1) + ")");
		int displayed = 0;
		for (int i = 0; i < guilds.size() && displayed < SERVERS_PER_PAGE
				&& (SERVERS_PER_PAGE * index) + i < guilds.size(); i++) {
			Guild g = guilds.get((SERVERS_PER_PAGE * index) + i);
			if (EmojiServerHelper.isEmojiServer(g) || g.getId().asLong() == DebugServer.DEBUG_SERVER_ID)
				continue;
			String text = "";
			EnumSet<Permission> invertPerms = DebugServer.getUsedPermission();
			invertPerms.removeAll(ourUser.asMember(g.getId()).block().getBasePermissions().block());
			for (Permission p : invertPerms)
				text += " - " + p + "\n";
			builder.appendField(g.getName(), text.isEmpty() ? "All permissions given" : text, true);
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
