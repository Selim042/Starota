package us.myles_selim.starota.trading;

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
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.reaction_messages.PersistReactionMessage;
import us.myles_selim.starota.wrappers.StarotaServer;

public class TradeboardReactionMessage extends PersistReactionMessage {

	private static final String CONFIRM_EMOJI = "✅";
	private static final String DELETE_EMOJI = "❌";
	private static final EmbedObject DELETED_EMBED;

	static {
		EmbedBuilder builder = new EmbedBuilder();
		builder.appendDescription("Trade deleted");
		DELETED_EMBED = builder.build();
	}

	private IGuild guild;
	private int postId;

	public TradeboardReactionMessage() {}

	public TradeboardReactionMessage(IGuild guild, TradeboardPost post) {
		this.guild = guild;
		this.postId = post.getId();
	}

	@Override
	public void onSend(StarotaServer server, IChannel channel, IMessage msg) {
		this.guild = server.getDiscordGuild();

		RequestBuffer.request(() -> msg.addReaction(ReactionEmoji.of(CONFIRM_EMOJI)));
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {}
		RequestBuffer.request(() -> msg.addReaction(ReactionEmoji.of(DELETE_EMOJI)));
	}

	@Override
	public void onReactionAdded(StarotaServer server, IChannel channel, IMessage msg, IUser user,
			IReaction react) {
		String name = react.getEmoji().getName();
		if (name.equals(CONFIRM_EMOJI)) {
			// System.out.println("confirmed");
			TradeboardPost post = server.getPost(this.postId);
			IUser poster = Starota.getUser(post.getOwner());
			String nickname = user.getNicknameForGuild(server.getDiscordGuild());
			if (nickname != null)
				nickname += " (_" + poster.getName() + "#" + poster.getDiscriminator() + "_)";
			else
				nickname = poster.getName() + "#" + poster.getDiscriminator();
			poster.getOrCreatePMChannel()
					.sendMessage(nickname + " from " + server.getDiscordGuild().getName()
							+ " is interested in your trade. Please contact them for more information.",
							post.getPostEmbed(server, false));
			msg.removeReaction(user, react);
		}
		if (name.equals(DELETE_EMOJI)) {
			// System.out.println("deleted");
			TradeboardPost post = server.getPost(this.postId);
			if (user.getLongID() != post.getOwner())
				return;
			server.removePost(this.postId);
			msg.edit(DELETED_EMBED);
			msg.removeReaction(user, react);
			msg.delete();
		}
	}

	@Override
	protected EmbedObject getEmbed() {
		StarotaServer sserver = StarotaServer.getServer(this.guild);
		return sserver.getPost(this.postId).getPostEmbed(sserver);
	}

	@Override
	public void toBytes(Storage stor) {
		if (this.guild == null)
			return;
		stor.writeLong(this.guild.getLongID());
		stor.writeInt(this.postId);
	}

	@Override
	public void fromBytes(Storage stor) {
		this.guild = Starota.getGuild(stor.readLong());
		this.postId = stor.readInt();
	}

}
