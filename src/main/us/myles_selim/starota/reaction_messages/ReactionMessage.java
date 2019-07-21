package us.myles_selim.starota.reaction_messages;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.TextChannel;
import discord4j.core.object.reaction.ReactionEmoji;
import discord4j.core.spec.EmbedCreateSpec;
import us.myles_selim.starota.misc.utils.EmbedBuilder;
import us.myles_selim.starota.misc.utils.EmojiConstants;
import us.myles_selim.starota.misc.utils.IndexHolder;
import us.myles_selim.starota.wrappers.StarotaServer;

/**
 * Only usable on servers. Bots cannot edit messages in a private channel.
 */
public class ReactionMessage {

	private ReactionMessageRegistry registry;
	private Message msg;
	private Map<ReactionEmoji, ReactionButton> buttons = new HashMap<>();

	public final ReactionMessage addButton(ReactionButton button) {
		if (buttons.containsKey(button.emoji))
			return this;
		this.buttons.put(button.getEmoji(), button);
		this.msg.addReaction(button.emoji).block();
		return this;
	}

	public final ReactionMessage addPageButtons(IndexHolder index, int max) {
		addButton(new ReactionButton(EmojiConstants.getLeftArrowEmoji(),
				(Message message, Member user, boolean added) -> {
					if (!added)
						return false;
					if (index.value >= 0)
						index.value--;
					else
						index.value = max - 1;
					return true;
				}));
		addButton(new ReactionButton(EmojiConstants.getRightArrowEmoji(),
				(Message message, Member user, boolean added) -> {
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
		Consumer<? super EmbedCreateSpec> emb = getEmbed(
				StarotaServer.getServer(channel.getGuild().block()));
		msg = channel.createEmbed(emb).block();
		registry.messages.put(msg.getId().asString(), this);
		StarotaServer sserver = StarotaServer.getServer(channel.getGuild().block());
		onSend(sserver, channel, msg);
		if (this instanceof PersistReactionMessage)
			registry.serialize(sserver, msg, (PersistReactionMessage) this);
		return msg;
	}

	public final Message editMessage(TextChannel channel, Message msg) {
		if (msg == null)
			return createMessage(channel);
		registry = ReactionMessageRegistry.getRegistry(channel.getClient());
		this.msg = msg;
		Consumer<? super EmbedCreateSpec> emb = getEmbed(
				StarotaServer.getServer(channel.getGuild().block()));
		msg.edit((ed) -> ed.setEmbed(emb)).block();
		registry.messages.put(msg.getId().asString(), this);
		StarotaServer sserver = StarotaServer.getServer(channel.getGuild().block());
		// onSend(sserver, channel, msg);
		onEdit(sserver, channel, msg);
		if (this instanceof PersistReactionMessage)
			registry.serialize(sserver, msg, (PersistReactionMessage) this);
		return msg;
	}

	public final Message getMessage() {
		return this.msg;
	}

	public void onSend(StarotaServer server, TextChannel channel, Message msg) {}

	public void onEdit(StarotaServer server, TextChannel channel, Message msg) {}

	public void onReactionAdded(StarotaServer server, TextChannel channel, Message msg, Member user,
			ReactionEmoji react) {
		ReactionButton button = this.buttons.get(react);
		if (button != null)
			button.execute(msg, user, true);
	}

	public void onReactionRemoved(StarotaServer server, TextChannel channel, Message msg, Member user,
			ReactionEmoji react) {
		ReactionButton button = this.buttons.get(react);
		if (button != null)
			button.execute(msg, user, false);
	}

	protected Consumer<? super EmbedCreateSpec> getEmbed(StarotaServer server) {
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

		public void execute(Message message, Member user, boolean added) {
			boolean toRemoveUpdate = handler.execute(message, user, added);
			if (added && toRemoveUpdate)
				message.removeReaction(emoji, user.getId()).block();
			if (toRemoveUpdate)
				editMessage((TextChannel) message.getChannel().block(), message);
		}

	}

	public interface IButtonAction {

		public boolean execute(Message message, Member user, boolean added);

	}

}
