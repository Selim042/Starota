package us.myles_selim.starota.reaction_messages;

import java.util.HashMap;
import java.util.Map;

import sx.blah.discord.api.internal.json.objects.EmbedObject;
import sx.blah.discord.handle.impl.obj.ReactionEmoji;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IPrivateChannel;
import sx.blah.discord.handle.obj.IReaction;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.EmbedBuilder;
import sx.blah.discord.util.RequestBuffer;
import us.myles_selim.starota.misc.utils.EmojiConstants;
import us.myles_selim.starota.misc.utils.IndexHolder;
import us.myles_selim.starota.wrappers.StarotaServer;

public class ReactionMessage {

	private ReactionMessageRegistry registry;
	private IMessage msg;
	private Map<ReactionEmoji, ReactionButton> buttons = new HashMap<>();

	public final ReactionMessage addButton(ReactionButton button) {
		if (buttons.containsKey(button.emoji))
			return this;
		this.buttons.put(button.getEmoji(), button);
		RequestBuffer.request(() -> this.msg.addReaction(button.emoji)).get();
		return this;
	}

	public final ReactionMessage addPageButtons(IndexHolder index, int max) {
		addButton(new ReactionButton(EmojiConstants.getLeftArrowEmoji(),
				(IMessage message, IUser user, boolean added) -> {
					if (!added)
						return false;
					if (index.value >= 0)
						index.value--;
					else
						index.value = max - 1;
					return true;
				}));
		addButton(new ReactionButton(EmojiConstants.getRightArrowEmoji(),
				(IMessage message, IUser user, boolean added) -> {
					if (!added)
						return false;
					if (index.value >= max)
						index.value = 0;
					else
						index.value++;
					return true;
				}));
		return this;
	}

	public final ReactionMessage clearButtons() {
		for (ReactionEmoji e : this.buttons.keySet())
			RequestBuffer.request(() -> this.msg.removeReaction(registry.getBot(), e));
		this.buttons.clear();
		return this;
	}

	public final IMessage sendMessage(IChannel channel) {
		registry = ReactionMessageRegistry.getRegistry(channel.getShard());
		EmbedObject emb = getEmbed(
				channel instanceof IPrivateChannel ? null : StarotaServer.getServer(channel.getGuild()));
		msg = channel.sendMessage(emb);
		// ReactionMessageRegistry.MESSAGES.put(msg.getStringID(), this);
		registry.messages.put(msg.getStringID(), this);
		StarotaServer sserver = StarotaServer
				.getServer(channel instanceof IPrivateChannel ? null : channel.getGuild());
		onSend(sserver, channel, msg);
		if (this instanceof PersistReactionMessage)
			// ReactionMessageRegistry.serialize(sserver, msg,
			// (PersistReactionMessage) this);
			registry.serialize(sserver, msg, (PersistReactionMessage) this);
		return msg;
	}

	public final IMessage editMessage(IChannel channel, IMessage msg) {
		if (msg == null)
			return sendMessage(channel);
		registry = ReactionMessageRegistry.getRegistry(channel.getShard());
		this.msg = msg;
		EmbedObject emb = getEmbed(
				channel instanceof IPrivateChannel ? null : StarotaServer.getServer(channel.getGuild()));
		msg.edit(emb);
		// ReactionMessageRegistry.MESSAGES.put(msg.getStringID(), this);
		registry.messages.put(msg.getStringID(), this);
		StarotaServer sserver = StarotaServer
				.getServer(channel instanceof IPrivateChannel ? null : channel.getGuild());
		// onSend(sserver, channel, msg);
		onEdit(sserver, channel, msg);
		if (this instanceof PersistReactionMessage)
			// ReactionMessageRegistry.serialize(sserver, msg,
			// (PersistReactionMessage) this);
			registry.serialize(sserver, msg, (PersistReactionMessage) this);
		return msg;
	}

	public final IMessage getMessage() {
		return this.msg;
	}

	public void onSend(StarotaServer server, IChannel channel, IMessage msg) {}

	public void onEdit(StarotaServer server, IChannel channel, IMessage msg) {}

	public void onReactionAdded(StarotaServer server, IChannel channel, IMessage msg, IUser user,
			IReaction react) {
		ReactionButton button = this.buttons.get(react.getEmoji());
		if (button != null)
			button.execute(msg, user, true);
	}

	public void onReactionRemoved(StarotaServer server, IChannel channel, IMessage msg, IUser user,
			IReaction react) {
		ReactionButton button = this.buttons.get(react.getEmoji());
		if (button != null)
			button.execute(msg, user, false);
	}

	protected EmbedObject getEmbed(StarotaServer server) {
		return new EmbedBuilder().setLenient(true).build();
	}

	public class ReactionButton {

		private ReactionEmoji emoji;
		private IButtonAction handler;

		public ReactionButton(ReactionEmoji emoji, IButtonAction handler) {
			this.emoji = emoji;
			this.handler = handler;
		}

		public ReactionEmoji getEmoji() {
			return this.emoji;
		}

		public void execute(IMessage message, IUser user, boolean added) {
			boolean toRemoveUpdate = handler.execute(message, user, added);
			if (added && toRemoveUpdate)
				RequestBuffer.request(() -> message.removeReaction(user, emoji));
			if (toRemoveUpdate)
				RequestBuffer
						.request(() -> editMessage(message.getChannel(), message));
		}

	}

	public interface IButtonAction {

		public boolean execute(IMessage message, IUser user, boolean added);

	}

}
