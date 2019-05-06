package us.myles_selim.starota.trading;

import java.util.function.Consumer;

import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.TextChannel;
import discord4j.core.object.entity.User;
import discord4j.core.object.reaction.ReactionEmoji;
import discord4j.core.spec.EmbedCreateSpec;
import us.myles_selim.ebs.Storage;
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.reaction_messages.PersistReactionMessage;
import us.myles_selim.starota.wrappers.StarotaServer;

public class TradeboardReactionMessage extends PersistReactionMessage {

	public static final String CONFIRM_EMOJI = "✅";
	public static final String DELETE_EMOJI = "❌";

	private static final Consumer<EmbedCreateSpec> DELETED_EMBED = (e) -> e
			.setDescription("Trade deleted");

	private Guild guild;
	private int postId;

	public TradeboardReactionMessage() {}

	public TradeboardReactionMessage(Guild guild, TradeboardPost post) {
		this.guild = guild;
		this.postId = post.getId();
	}

	@Override
	public void onSend(StarotaServer server, TextChannel channel, Message msg) {
		this.guild = server.getDiscordGuild();

		msg.addReaction(ReactionEmoji.unicode(CONFIRM_EMOJI));
		msg.addReaction(ReactionEmoji.unicode(DELETE_EMOJI));
	}

	@Override
	public void onReactionAdded(StarotaServer server, TextChannel channel, Message msg, Member member,
			ReactionEmoji react) {
		String name = react.toString();
		if (name.equals(CONFIRM_EMOJI)) {
			// System.out.println("confirmed");
			TradeboardPost post = server.getPost(this.postId);
			User poster = Starota.getUser(post.getOwner());
			poster.getPrivateChannel().block().createMessage((m) -> m
					.setContent(member.getDisplayName() + " from " + server.getDiscordGuild().getName()
							+ " is interested in your trade. Please contact them for more information.")
					.setEmbed(post.getPostEmbed(server, false)));
			msg.removeReaction(react, member.getId());
		}
		if (name.equals(DELETE_EMOJI)) {
			// System.out.println("deleted");
			TradeboardPost post = server.getPost(this.postId);
			if (member.getId().asLong() != post.getOwner())
				return;
			server.removePost(this.postId);
			msg.edit((m) -> m.setEmbed(DELETED_EMBED));
			msg.removeReaction(react, member.getId());
			msg.delete();
		}
	}

	@Override
	protected Consumer<EmbedCreateSpec> getEmbed(StarotaServer server) {
		StarotaServer sserver = StarotaServer.getServer(this.guild);
		return sserver.getPost(this.postId).getPostEmbed(sserver);
	}

	@Override
	public void toBytes(Storage stor) {
		if (this.guild == null)
			return;
		stor.writeLong(this.guild.getId().asLong());
		stor.writeInt(this.postId);
	}

	@Override
	public void fromBytes(Storage stor) {
		this.guild = Starota.getGuild(stor.readLong());
		this.postId = stor.readInt();
	}

}
