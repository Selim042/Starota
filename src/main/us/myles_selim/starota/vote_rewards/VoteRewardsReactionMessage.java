package us.myles_selim.starota.vote_rewards;

import java.util.List;

import sx.blah.discord.api.internal.json.objects.EmbedObject;
import sx.blah.discord.handle.impl.obj.ReactionEmoji;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IReaction;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.EmbedBuilder;
import sx.blah.discord.util.RequestBuffer;
import us.myles_selim.starota.enums.EnumDonorPerm;
import us.myles_selim.starota.misc.data_types.Pair;
import us.myles_selim.starota.reaction_messages.ReactionMessage;
import us.myles_selim.starota.wrappers.StarotaServer;

public class VoteRewardsReactionMessage extends ReactionMessage {

	private static final String[] EMOJI = new String[] { "1⃣", "2⃣", "3⃣", "4⃣", "5⃣", "6⃣", "7⃣", "8⃣",
			"9⃣", "A⃣", "B⃣", "C⃣", "D⃣", "E⃣", "F⃣" };
	private static final String ENABLED = "✅";
	private static final String DISABLED = "❌";

	private Pair<ReactionEmoji, EnumDonorPerm>[] displayedPerms;

	@Override
	public void onReactionAdded(StarotaServer server, IChannel channel, IMessage msg, IUser user,
			IReaction react) {
		RequestBuffer.request(() -> msg.removeReaction(user, react)).get();
		if (server.getDiscordGuild().getOwnerLongID() != user.getLongID())
			return;
		EnumDonorPerm perm = null;
		for (Pair<ReactionEmoji, EnumDonorPerm> p : displayedPerms) {
			if (p != null && p.left.equals(react.getEmoji())) {
				perm = p.right;
				break;
			}
		}
		if (perm != null) {
			if (server.getVoteRewards().contains(perm))
				server.removeVoteReward(perm);
			else
				server.addVoteReward(perm);
		}
		new Thread("votePerksUpdate") {

			@Override
			public void run() {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {}
				editMessage(channel, msg);
			}
		}.start();
	}

	@Override
	public void onSend(StarotaServer server, IChannel channel, IMessage msg) {
		for (Pair<ReactionEmoji, EnumDonorPerm> e : displayedPerms)
			if (e != null)
				RequestBuffer.request(() -> msg.addReaction(e.left)).get();
	}

	@SuppressWarnings("unchecked")
	@Override
	protected EmbedObject getEmbed(StarotaServer server) {
		displayedPerms = new Pair[EnumDonorPerm.values().length];
		EmbedBuilder builder = new EmbedBuilder();
		builder.withTitle("Choose your vote perks:");
		int index = 0;
		List<EnumDonorPerm> enabledPerms = server.getVoteRewards();
		for (EnumDonorPerm p : EnumDonorPerm.values()) {
			if (index >= EMOJI.length || p.getPointsRequired() < 0)
				continue;
			ReactionEmoji emoji = ReactionEmoji.of(EMOJI[index]);
			builder.appendDesc(emoji + ": " + p + " (" + p.getPointsRequired() + " points) "
					+ ReactionEmoji.of(enabledPerms.contains(p) ? ENABLED : DISABLED) + "\n");
			displayedPerms[index] = new Pair<>(emoji, p);

			index++;
		}
		builder.appendDesc("\nUsed vote points: " + server.getUsedVotePoints() + "/"
				+ server.getEarnedVotePoints());
		return builder.build();
	}

}
