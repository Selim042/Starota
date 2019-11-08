package us.myles_selim.starota.commands.selim_pm;

import java.util.function.Consumer;

import com.google.gson.Gson;

import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.MessageChannel;
import discord4j.core.object.entity.User;
import discord4j.core.object.reaction.ReactionEmoji;
import discord4j.core.spec.EmbedCreateSpec;
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.commands.registry.CommandException;
import us.myles_selim.starota.commands.registry.java.JavaCommand;
import us.myles_selim.starota.misc.data_types.EmbedHolder;
import us.myles_selim.starota.misc.utils.EmbedBuilder;
import us.myles_selim.starota.misc.utils.EmojiConstants;
import us.myles_selim.starota.reaction_messages.ReactionMessage;
import us.myles_selim.starota.wrappers.StarotaServer;

public class CommandOwnerMessage extends JavaCommand {

	private static final Gson GSON = new Gson();

	public CommandOwnerMessage() {
		super("oMsg", "Sends a message to all Starota server owners.");
	}

	@Override
	public String getGeneralUsage() {
		return "<message> (in ``` followed by message type, json for embed json, anything else for normal message)";
	}

	@Override
	public void execute(String[] args, Message message, Guild guild, MessageChannel channel)
			throws CommandException {
		if (guild != null)
			return;
		new ReactionMessageOwnerMessage(message.getContent().get(), message.getAuthor().get())
				.createMessage((MessageChannel) channel);
	}

	private static class ReactionMessageOwnerMessage extends ReactionMessage {

		private String content;
		private Consumer<? super EmbedCreateSpec> embed;
		private boolean emptyEmbed = true;
		private User author;

		public ReactionMessageOwnerMessage(String msg, User author) {
			int firstLineIndex = msg.indexOf("\n");
			String type = msg.substring(msg.indexOf("`") + 3, firstLineIndex);
			msg = msg.substring(firstLineIndex, msg.length() - 3);
			String[] lines = msg.split("\n");

			if (type.equalsIgnoreCase("json")) {
				EmbedHolder embed = GSON.fromJson(msg, EmbedHolder.class);
				if (embed != null) {
					this.content = embed.content;
					this.embed = EmbedBuilder.fromBean(embed.embed).build();
					emptyEmbed = false;
				}
			} else {
				String out = "";
				for (String l : lines)
					out += l + "\n";
				if (!out.isEmpty())
					this.content = out;
				this.embed = new EmbedBuilder().build();
			}
			this.author = author;
		}

		@Override
		public void onSend(StarotaServer server, MessageChannel channel, Message msg) {
			if (content == null)
				msg.edit((m) -> m.setEmbed(getEmbed(server))).block();
			else
				msg.edit((m) -> m.setContent(content).setEmbed(getEmbed(server))).block();
			msg.addReaction(EmojiConstants.getBooleanEmoji(true)).block();
			msg.addReaction(EmojiConstants.getBooleanEmoji(false)).block();
		}

		@Override
		public void onEdit(StarotaServer server, MessageChannel channel, Message msg) {
			if (content == null)
				msg.edit((m) -> m.setEmbed(getEmbed(server))).block();
			else
				msg.edit((m) -> m.setContent(content).setEmbed(getEmbed(server))).block();
			msg.addReaction(EmojiConstants.getBooleanEmoji(true)).block();
			msg.addReaction(EmojiConstants.getBooleanEmoji(false)).block();
		}

		@Override
		public void onReactionAdded(StarotaServer server, MessageChannel channel, Message msg, User user,
				ReactionEmoji react) {
			if (react.asUnicodeEmoji().get().equals(EmojiConstants.getBooleanEmoji(true))) {
				if (embed != null && !emptyEmbed)
					Starota.sendOwnersMessage(content, embed, author);
				else if (emptyEmbed)
					Starota.sendOwnersMessage(content, author);
				else
					Starota.sendOwnersMessage(content, author);
				channel.createMessage("Sent").block();
			} else if (react.asUnicodeEmoji().get().equals(EmojiConstants.getBooleanEmoji(false)))
				msg.delete().block();
		}

		@Override
		protected Consumer<? super EmbedCreateSpec> getEmbed(StarotaServer server) {
			return embed;
		}

	}

}
