package us.myles_selim.starota.trading;

import java.util.function.Consumer;

import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.MessageChannel;
import discord4j.core.object.entity.User;
import discord4j.core.object.reaction.ReactionEmoji;
import discord4j.core.object.util.Snowflake;
import discord4j.core.spec.EmbedCreateSpec;
import us.myles_selim.ebs.Storage;
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.misc.utils.EmbedBuilder;
import us.myles_selim.starota.reaction_messages.PersistReactionMessage;
import us.myles_selim.starota.wrappers.StarotaServer;

public class TradeboardReactionMessage extends PersistReactionMessage {

	public static final String CONFIRM_EMOJI = "✅";
	public static final String DELETE_EMOJI = "❌";

	private static final Consumer<? super EmbedCreateSpec> DELETED_EMBED;

	static {
		EmbedBuilder builder = new EmbedBuilder();
		builder.appendDescription("Trade deleted");
		DELETED_EMBED = builder.build();
	}

	private Guild guild;
	private int postId;

	public TradeboardReactionMessage() {}

	public TradeboardReactionMessage(Guild guild, TradeboardPost post) {
		this.guild = guild;
		this.postId = post.getId();
	}

	@Override
	public void onSend(StarotaServer server, MessageChannel channel, Message msg) {
		this.guild = server.getDiscordGuild();

		msg.addReaction(ReactionEmoji.unicode(CONFIRM_EMOJI)).block();
		msg.addReaction(ReactionEmoji.unicode(DELETE_EMOJI)).block();
	}

	@Override
	public void onReactionAdded(StarotaServer server, MessageChannel channel, Message msg, User user,
			ReactionEmoji react) {
		if (!react.asUnicodeEmoji().isPresent())
			return;
		String name = react.asUnicodeEmoji().get().getRaw();
		if (name.equals(CONFIRM_EMOJI)) {
			// System.out.println("confirmed");
			TradeboardPost post = server.getPost(this.postId);
			Member poster = server.getDiscordGuild().getMemberById(Snowflake.of(post.getOwner()))
					.block();
			String nickname = user.asMember(server.getDiscordGuild().getId()).block().getDisplayName();
			if (nickname != null)
				nickname += " (_" + poster.getUsername() + "#" + poster.getDiscriminator() + "_)";
			else
				nickname = poster.getUsername() + "#" + poster.getDiscriminator();
			final String finalNick = nickname;
			poster.getPrivateChannel().block().createMessage((e) -> e
					.setContent(finalNick + " from " + server.getDiscordGuild().getName()
							+ " is interested in your trade. Please contact them for more information.")
					.setEmbed(post.getPostEmbed(server, false))).block();
			msg.removeReaction(react, user.getId()).block();
		}
		if (name.equals(DELETE_EMOJI)) {
			// System.out.println("deleted");
			TradeboardPost post = server.getPost(this.postId);
			if (user.getId().asLong() != post.getOwner())
				return;
			server.removePost(this.postId);
			msg.edit((m) -> m.setEmbed(DELETED_EMBED));
			msg.removeReaction(react, user.getId());
			msg.delete();
		}
	}

	@Override
	protected Consumer<? super EmbedCreateSpec> getEmbed(StarotaServer server) {
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
