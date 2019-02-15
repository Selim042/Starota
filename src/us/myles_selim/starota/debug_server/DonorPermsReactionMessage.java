package us.myles_selim.starota.debug_server;

import java.util.List;

import sx.blah.discord.api.internal.json.objects.EmbedObject;
import sx.blah.discord.handle.impl.obj.ReactionEmoji;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IReaction;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.EmbedBuilder;
import sx.blah.discord.util.RequestBuffer;
import us.myles_selim.ebs.Storage;
import us.myles_selim.starota.EmojiServerHelper;
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.enums.EnumDonorPerm;
import us.myles_selim.starota.reaction_messages.PersistReactionMessage;
import us.myles_selim.starota.wrappers.StarotaServer;

public class DonorPermsReactionMessage extends PersistReactionMessage {

	public static final String LEFT_EMOJI = "⬅";
	public static final String RIGHT_EMOJI = "➡";
	public static final String REFRESH_EMOJI = "🔄";
	public static final int SERVERS_PER_PAGE = 20;

	private int index = 0;

	@Override
	public void onSend(StarotaServer server, IChannel channel, IMessage msg) {
		RequestBuffer.request(() -> msg.addReaction(ReactionEmoji.of(LEFT_EMOJI))).get();
		RequestBuffer.request(() -> msg.addReaction(ReactionEmoji.of(RIGHT_EMOJI))).get();
		RequestBuffer.request(() -> msg.addReaction(ReactionEmoji.of(REFRESH_EMOJI))).get();
	}

	@Override
	public void onReactionAdded(StarotaServer server, IChannel channel, IMessage msg, IUser user,
			IReaction react) {
		int numPages = (Starota.getClient().getGuilds().size() - EmojiServerHelper.getNumberServers()
				- 1) / SERVERS_PER_PAGE;
		if (react.getEmoji().getName().equals(LEFT_EMOJI)) {
			if (index <= 0)
				index = numPages;
			else
				index--;
		} else if (react.getEmoji().getName().equals(RIGHT_EMOJI)) {
			if (index >= numPages)
				index = 0;
			else
				index++;
		}
		RequestBuffer.request(() -> this.editMessage(channel, msg));
		RequestBuffer.request(() -> msg.removeReaction(user, react));
	}

	@Override
	protected EmbedObject getEmbed(StarotaServer server) {
		if (!Starota.FULLY_STARTED)
			return DebugServer.getNotReadyEmbed();
		EmbedBuilder builder = new EmbedBuilder();
		List<IGuild> guilds = Starota.getClient().getGuilds();
		int numGuilds = Starota.getClient().getGuilds().size() - EmojiServerHelper.getNumberServers()
				- 1;
		builder.withTitle(
				"Donor Perms: (" + (index + 1) + "/" + (numGuilds / SERVERS_PER_PAGE + 1) + ")");
		int displayed = 0;
		for (int i = 0; i < guilds.size() && displayed < SERVERS_PER_PAGE
				&& (SERVERS_PER_PAGE * index) + i < guilds.size(); i++) {
			IGuild g = guilds.get(i);
			if (EmojiServerHelper.isEmojiServer(g) || g.equals(DebugServer.DEBUG_SERVER))
				continue;
			List<EnumDonorPerm> perms = Starota.getDonorPerms(g);
			if (perms == null || perms.isEmpty()) {
				builder.appendField(g.getName(), " - No donor permissions", true);
				continue;
			}
			String text = "";
			for (EnumDonorPerm p : perms)
				text += " - " + p + "\n";
			builder.appendField(g.getName(), text, true);
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
