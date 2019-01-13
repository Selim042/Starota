package us.myles_selim.starota.trading;

import sx.blah.discord.api.internal.json.objects.EmbedObject;
import sx.blah.discord.handle.impl.obj.ReactionEmoji;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IReaction;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.RequestBuffer;
import us.myles_selim.ebs.Storage;
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.reaction_messages.PersistReactionMessage;
import us.myles_selim.starota.wrappers.StarotaServer;

public class TradeboardReactionMessage extends PersistReactionMessage {

	private static final String CONFIRM_EMOJI = "✅";
	private static final String DELETE_EMOJI = "❌";

	private StarotaServer server;
	private TradeboardPost post;

	public TradeboardReactionMessage() {}

	public TradeboardReactionMessage(StarotaServer server, TradeboardPost post) {
		this.server = server;
		this.post = post;
	}

	@Override
	public void onSend(StarotaServer server, IChannel channel, IMessage msg) {
		this.server = server;

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
			msg.removeReaction(user, react);
		}
	}

	@Override
	public void onReactionRemoved(StarotaServer server, IChannel channel, IMessage msg, IUser user,
			IReaction react) {

	}

	@Override
	protected EmbedObject getEmbed() {
		return post.getPostEmbed(server);
	}

	@Override
	public void toBytes(Storage stor) {
		stor.writeLong(this.server.getDiscordGuild().getLongID());
		this.post.toBytes(stor);
	}

	@Override
	public void fromBytes(Storage stor) {
		this.server = StarotaServer.getServer(Starota.getGuild(stor.readLong()));
		this.post = new TradeboardPost();
		this.post.fromBytes(stor);
	}

}
