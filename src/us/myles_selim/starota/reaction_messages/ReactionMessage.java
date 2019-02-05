package us.myles_selim.starota.reaction_messages;

import sx.blah.discord.api.internal.json.objects.EmbedObject;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IReaction;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.EmbedBuilder;
import us.myles_selim.starota.wrappers.StarotaServer;

public class ReactionMessage {

	public final IMessage sendMessage(IChannel channel) {
		EmbedObject emb = getEmbed(StarotaServer.getServer(channel.getGuild()));
		IMessage msg = channel.sendMessage(emb);
		ReactionMessageRegistry.MESSAGES.put(msg.getStringID(), this);
		StarotaServer sserver = StarotaServer.getServer(channel.getGuild());
		onSend(sserver, channel, msg);
		if (this instanceof PersistReactionMessage)
			ReactionMessageRegistry.serialize(sserver, msg, (PersistReactionMessage) this);
		return msg;
	}

	public final IMessage editMessage(IChannel channel, IMessage msg) {
		if (msg == null)
			return sendMessage(channel);
		EmbedObject emb = getEmbed(StarotaServer.getServer(channel.getGuild()));
		msg.edit(emb);
		ReactionMessageRegistry.MESSAGES.put(msg.getStringID(), this);
		StarotaServer sserver = StarotaServer.getServer(channel.getGuild());
		// onSend(sserver, channel, msg);
		if (this instanceof PersistReactionMessage)
			ReactionMessageRegistry.serialize(sserver, msg, (PersistReactionMessage) this);
		return msg;
	}

	public void onSend(StarotaServer server, IChannel channel, IMessage msg) {}

	public void onReactionAdded(StarotaServer server, IChannel channel, IMessage msg, IUser user,
			IReaction react) {
		// channel.sendMessage("+ " + react.getEmoji());
	}

	public void onReactionRemoved(StarotaServer server, IChannel channel, IMessage msg, IUser user,
			IReaction react) {
		// channel.sendMessage("- " + react.getEmoji());
	}

	protected EmbedObject getEmbed(StarotaServer server) {
		return new EmbedBuilder().setLenient(true).build();
	}

}
