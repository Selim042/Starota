package us.myles_selim.starota.reaction_messages;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Consumer;

import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.MessageChannel;
import discord4j.core.object.entity.TextChannel;
import discord4j.core.object.entity.User;
import discord4j.core.object.reaction.ReactionEmoji;
import discord4j.core.spec.EmbedCreateSpec;
import us.myles_selim.starota.misc.utils.EmbedBuilder;
import us.myles_selim.starota.misc.utils.EmojiConstants;
import us.myles_selim.starota.misc.utils.IndexHolder;
import us.myles_selim.starota.misc.utils.MiscUtils;
import us.myles_selim.starota.wrappers.StarotaServer;

/**
 * Only usable on servers. Bots cannot edit messages in a private channel.
 */
public class ReactionMessage {

	private ReactionMessageRegistry registry;
	private Message msg;
	private Map<ReactionEmoji, ReactionButton> buttons = new HashMap<>();

	public final ReactionMessage addButton(ReactionButton button) {
		if (buttons.keySet().stream().anyMatch((r) -> {
			if (!r.getClass().equals(button.emoji.getClass()))
				return false;
			if (r instanceof ReactionEmoji.Custom)
				return ((ReactionEmoji.Custom) r).getId()
						.equals(((ReactionEmoji.Custom) button.emoji).getId());
			return ((ReactionEmoji.Unicode) r).getRaw()
					.equals(((ReactionEmoji.Unicode) button.emoji).getRaw());
		}))
			return this;
		this.buttons.put(button.getEmoji(), button);
		this.msg.addReaction(button.emoji).block();
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
					if (index.value < max - 1)
						index.value++;
					else
						index.value = 0;
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

	public boolean isButton(ReactionEmoji emoji) {
		for (ReactionEmoji e : buttons.keySet())
			if (MiscUtils.getEmojiName(e).equals(MiscUtils.getEmojiName(emoji)))
				return true;
		return buttons.containsKey(emoji);
	}

	public ReactionButton getButton(ReactionEmoji emoji) {
		for (Entry<ReactionEmoji, ReactionButton> e : buttons.entrySet())
			if (MiscUtils.getEmojiName(e.getKey()).equals(MiscUtils.getEmojiName(emoji)))
				return e.getValue();
		return null;
	}

	public boolean removeButton(ReactionEmoji emoji) {
		if (buttons.containsKey(emoji)) {
			buttons.remove(emoji);
			return true;
		}
		return false;
	}

	@SuppressWarnings("deprecation")
	public final Message createMessage(MessageChannel channel) {
		Guild guild = channel instanceof TextChannel ? ((TextChannel) channel).getGuild().block() : null;
		registry = ReactionMessageRegistry.getRegistry(channel.getClient());
		Consumer<? super EmbedCreateSpec> emb = getEmbed(StarotaServer.getServer(guild));
		msg = channel.createEmbed(emb).block();
		registry.messages.put(msg.getId().asString(), this);
		StarotaServer sserver = StarotaServer.getServer(guild);
		if (this instanceof IHelpReactionMessage)
			((IHelpReactionMessage) this).addHelpButton(this, sserver);
		onSend(sserver, channel, msg);
		if (this instanceof PersistReactionMessage)
			registry.serialize(sserver, msg, (PersistReactionMessage) this);
		return msg;
	}

	public final Message editMessage(MessageChannel channel, Message msg) {
		if (msg == null)
			return createMessage(channel);
		Guild guild = channel instanceof TextChannel ? ((TextChannel) channel).getGuild().block() : null;
		registry = ReactionMessageRegistry.getRegistry(channel.getClient());
		this.msg = msg;
		Consumer<? super EmbedCreateSpec> emb = getEmbed(StarotaServer.getServer(guild));
		msg.edit((ed) -> ed.setEmbed(emb)).block();
		registry.messages.put(msg.getId().asString(), this);
		StarotaServer sserver = StarotaServer.getServer(guild);
		// onSend(sserver, channel, msg);
		onEdit(sserver, channel, msg);
		if (this instanceof PersistReactionMessage)
			registry.serialize(sserver, msg, (PersistReactionMessage) this);
		return msg;
	}

	public final Message getMessage() {
		return this.msg;
	}

	public void onSend(StarotaServer server, MessageChannel channel, Message msg) { /* */ }

	public void onEdit(StarotaServer server, MessageChannel channel, Message msg) { /* */ }

	public void onReactionAdded(StarotaServer server, MessageChannel channel, Message msg, User user,
			ReactionEmoji react) {
		ReactionButton button = getButton(react);
		if (button != null)
			button.execute(msg, user, true);
	}

	public void onReactionRemoved(StarotaServer server, MessageChannel channel, Message msg, User user,
			ReactionEmoji react) {
		ReactionButton button = getButton(react);
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

		public void execute(Message message, User user, boolean added) {
			boolean toRemoveUpdate = handler.execute(message, user, added);
			if (added && toRemoveUpdate)
				message.removeReaction(emoji, user.getId()).block();
			if (toRemoveUpdate)
				editMessage((TextChannel) message.getChannel().block(), message);
		}

	}

	public interface IButtonAction {

		/**
		 * @param message
		 *            The message that was reacted to
		 * @param user
		 *            The member that reacted
		 * @param added
		 *            Whether the reaction was added or removed
		 * @return Whether the reaction should be removed after handling
		 */
		public boolean execute(Message message, User user, boolean added);

	}

}
