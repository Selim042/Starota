package us.myles_selim.starota.vote_rewards;

import java.util.List;
import java.util.function.Consumer;

import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.TextChannel;
import discord4j.core.object.reaction.ReactionEmoji;
import discord4j.core.spec.EmbedCreateSpec;
import us.myles_selim.starota.enums.EnumDonorPerm;
import us.myles_selim.starota.misc.data_types.Pair;
import us.myles_selim.starota.misc.utils.EmbedBuilder;
import us.myles_selim.starota.misc.utils.EmojiConstants;
import us.myles_selim.starota.misc.utils.MiscUtils;
import us.myles_selim.starota.reaction_messages.ReactionMessage;
import us.myles_selim.starota.wrappers.StarotaServer;

public class VoteRewardsReactionMessage extends ReactionMessage {

	private static final String[] EMOJI = new String[] { "1⃣", "2⃣", "3⃣", "4⃣", "5⃣", "6⃣", "7⃣", "8⃣",
			"9⃣", "A⃣", "B⃣", "C⃣", "D⃣", "E⃣", "F⃣" };

	private Pair<ReactionEmoji, EnumDonorPerm>[] displayedPerms;

	@Override
	public void onReactionAdded(StarotaServer server, TextChannel channel, Message msg, Member user,
			ReactionEmoji react) {
		msg.removeReaction(react, user.getId()).block();
		if (!server.getDiscordGuild().getOwnerId().equals(user.getId()))
			return;
		EnumDonorPerm perm = null;
		for (Pair<ReactionEmoji, EnumDonorPerm> p : displayedPerms) {
			if (p != null && p.left.equals(react)) {
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
	public void onSend(StarotaServer server, TextChannel channel, Message msg) {
		for (Pair<ReactionEmoji, EnumDonorPerm> e : displayedPerms)
			if (e != null)
				msg.addReaction(e.left).block();
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Consumer<? super EmbedCreateSpec> getEmbed(StarotaServer server) {
		displayedPerms = new Pair[EnumDonorPerm.values().length];
		EmbedBuilder builder = new EmbedBuilder();
		builder.withTitle("Choose your vote perks:");
		int index = 0;
		List<EnumDonorPerm> enabledPerms = server.getVoteRewards();
		for (EnumDonorPerm p : EnumDonorPerm.values()) {
			if (index >= EMOJI.length || p.getPointsRequired() < 0)
				continue;
			ReactionEmoji emoji = ReactionEmoji.unicode(EMOJI[index]);
			builder.appendDesc(MiscUtils.getEmojiDisplay(emoji) + ": " + p + " (" + p.getPointsRequired()
					+ " points) "
					+ MiscUtils.getEmojiDisplay(EmojiConstants.getBooleanEmoji(enabledPerms.contains(p)))
					+ "\n");
			displayedPerms[index] = new Pair<>(emoji, p);

			index++;
		}
		builder.appendDesc("\nUsed vote points: " + server.getUsedVotePoints() + "/"
				+ server.getEarnedVotePoints());
		return builder.build();
	}

}
