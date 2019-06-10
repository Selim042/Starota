package us.myles_selim.starota.reaction_messages;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.TextChannel;
import discord4j.core.object.entity.User;
import discord4j.core.object.reaction.ReactionEmoji;
import discord4j.core.spec.EmbedCreateSpec;
import us.myles_selim.starota.misc.utils.EmojiConstants;
import us.myles_selim.starota.misc.utils.IndexHolder;
import us.myles_selim.starota.wrappers.StarotaServer;

public class ReactionMessage {

	private ReactionMessageRegistry registry;
	private Message msg;
	private Map<ReactionEmoji, ReactionButton> buttons = new HashMap<>();

	public final ReactionMessage addButton(ReactionButton button) {
		if (buttons.containsKey(button.emoji))
			return this;
		this.buttons.put(button.getEmoji(), button);
		this.msg.addReaction(button.emoji);
		return this;
	}

	public final ReactionMessage addPageButtons(IndexHolder index, int max) {
		addButton(new ReactionButton(EmojiConstants.getLeftArrowEmoji(),
				(Message message, User user, boolean added) -> {
					if (!added)
						return false;
					if (index.value >= 0)
						index.value--;
					else
						index.value = max - 1;
					return true;
				}));
		addButton(new ReactionButton(EmojiConstants.getRightArrowEmoji(),
				(Message message, User user, boolean added) -> {
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
			this.msg.removeReaction(e, registry.getBot().getId());
		this.buttons.clear();
		return this;
	}

	public final Message createMessage(TextChannel channel) {
		registry = ReactionMessageRegistry.getRegistry(channel.getClient());
		Consumer<EmbedCreateSpec> emb = getEmbed(StarotaServer.getServer(channel.getGuild().block()));
		msg = channel.createEmbed(emb).block();
		// ReactionMessageRegistry.MESSAGES.put(msg.getId().asString(), this);
		registry.messages.put(msg.getId().asString(), this);
		StarotaServer sserver = StarotaServer.getServer(channel.getGuild().block());
		onSend(sserver, channel, msg);
		if (this instanceof PersistReactionMessage)
			// ReactionMessageRegistry.serialize(sserver, msg,
			// (PersistReactionMessage) this);
			registry.serialize(sserver, msg, (PersistReactionMessage) this);
		return msg;
	}

	public final Message editMessage(TextChannel channel, Message msg) {
		if (msg == null)
			return createMessage(channel);
		registry = ReactionMessageRegistry.getRegistry(channel.getClient());
		this.msg = msg;
		Consumer<EmbedCreateSpec> emb = getEmbed(StarotaServer.getServer(channel.getGuild().block()));
		msg.edit((m) -> m.setEmbed(emb));
		// ReactionMessageRegistry.MESSAGES.put(msg.getId().asString(), this);
		registry.messages.put(msg.getId().asString(), this);
		StarotaServer sserver = StarotaServer.getServer(channel.getGuild().block());
		// onSend(sserver, channel, msg);
		onEdit(sserver, channel, msg);
		if (this instanceof PersistReactionMessage)
			// ReactionMessageRegistry.serialize(sserver, msg,
			// (PersistReactionMessage) this);
			registry.serialize(sserver, msg, (PersistReactionMessage) this);
		return msg;
	}

	public final Message getMessage() {
		return this.msg;
	}

	public void onSend(StarotaServer server, TextChannel channel, Message msg) {}

	public void onEdit(StarotaServer server, TextChannel channel, Message msg) {}

	public void onReactionAdded(StarotaServer server, TextChannel channel, Message msg, Member member,
			ReactionEmoji react) {
		ReactionButton button = this.buttons.get(react);
		if (button != null)
			button.execute(msg, member, true);
	}

	public void onReactionRemoved(StarotaServer server, TextChannel channel, Message msg, Member member,
			ReactionEmoji react) {
		ReactionButton button = this.buttons.get(react);
		if (button != null)
			button.execute(msg, member, false);
	}

	protected Consumer<EmbedCreateSpec> getEmbed(StarotaServer server) {
		return (e) -> {};
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

		public void execute(Message message, User user, boolean added) {
			boolean toRemoveUpdate = handler.execute(message, user, added);
			if (added && toRemoveUpdate)
				message.removeReaction(emoji, user.getId());
			if (toRemoveUpdate)
				editMessage((TextChannel) message.getChannel().block(), message);
		}

	}

	public interface IButtonAction {

		public boolean execute(Message message, User user, boolean added);

	}

}
