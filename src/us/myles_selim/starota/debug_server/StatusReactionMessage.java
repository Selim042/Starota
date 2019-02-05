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
import us.myles_selim.starota.DebugServer;
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.reaction_messages.PersistReactionMessage;
import us.myles_selim.starota.wrappers.StarotaServer;

public class StatusReactionMessage extends PersistReactionMessage {

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
		int numPages = Starota.getClient().getGuilds().size() / SERVERS_PER_PAGE;
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
		System.out.println(index);
		builder.withTitle(
				"Status: (" + (index + 1) + "/" + (guilds.size() / SERVERS_PER_PAGE + 1) + ")");
		for (int i = 0; i < guilds.size() && i < SERVERS_PER_PAGE
				&& (SERVERS_PER_PAGE * index) + i < guilds.size(); i++) {
			IGuild g = guilds.get(i);
			// if (EmojiServerHelper.isEmojiServer(g) ||
			// g.equals(DebugServer.DEBUG_SERVER))
			// continue;
			StarotaServer s = StarotaServer.getServer(g);
			String text = "";
			text += "Owner: " + g.getOwner().getName() + "#" + g.getOwner().getDiscriminator() + "\n";
			text += "Users: " + g.getUsers().size() + "\n";
			int tradeId = s.hasKey(StarotaServer.TRADE_ID_KEY)
					? (int) s.getValue(StarotaServer.TRADE_ID_KEY)
					: 0;
			text += "Trades: " + tradeId + "/9,999\n";
			text += "Leaderboards: " + s.getLeaderboardCount() + "/" + Starota.getMaxLeaderboards(g)
					+ "\n";
			text += "Voter Ratio: " + s.getVoterPercent() + "\n";
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
