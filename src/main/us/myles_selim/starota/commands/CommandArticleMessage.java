package us.myles_selim.starota.commands;

import java.util.function.Consumer;

import com.google.gson.Gson;

import discord4j.core.object.entity.Channel;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.Role;
import discord4j.core.object.entity.TextChannel;
import discord4j.core.object.entity.User;
import discord4j.core.object.reaction.ReactionEmoji;
import discord4j.core.spec.EmbedCreateSpec;
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.commands.registry.java.JavaCommand;
import us.myles_selim.starota.misc.data_types.EmbedHolder;
import us.myles_selim.starota.misc.utils.EmojiConstants;
import us.myles_selim.starota.misc.utils.StarotaConstants;
import us.myles_selim.starota.reaction_messages.ReactionMessage;
import us.myles_selim.starota.wrappers.StarotaServer;

public class CommandArticleMessage extends JavaCommand {

	private static final Gson GSON = new Gson();

	public CommandArticleMessage() {
		super("article", "Posts an article to news channels.");
	}

	@Override
	public Role requiredRole(Guild guild) {
		return Starota.getGuild(StarotaConstants.SUPPORT_SERVER.asLong())
				.getRoleById(StarotaConstants.EDITOR_ROLE_ID).block();
	}

	@Override
	public Channel requiredChannel(Guild guild) {
		return Starota.getGuild(StarotaConstants.SUPPORT_SERVER.asLong())
				.getChannelById(StarotaConstants.EDITOR_CHANNEL_ID).block();
	}

	@Override
	public String getGeneralUsage() {
		return "<message> (in ``` followed by message type, json for embed json, anything else for normal message)";
	}

	@Override
	public void execute(String[] args, Message message, Guild guild, TextChannel channel)
			throws Exception {
		new ReactionMessageArticleMessage(message.getContent().get(), message.getAuthor().get())
				.createMessage(channel);
	}

	private static class ReactionMessageArticleMessage extends ReactionMessage {

		private String content;
		private Consumer<EmbedCreateSpec> embed;
		private boolean emptyEmbed = true;
		private User author;

		public ReactionMessageArticleMessage(String msg, User author) {
			int firstLineIndex = msg.indexOf("\n");
			String type = msg.substring(msg.indexOf("`") + 3, firstLineIndex);
			msg = msg.substring(firstLineIndex, msg.length() - 3);
			String[] lines = msg.split("\n");

			if (type.equalsIgnoreCase("json")) {
				EmbedHolder embed = GSON.fromJson(msg, EmbedHolder.class);
				if (embed != null) {
					this.content = embed.content;
					this.embed = embed.getConsumerEmbed();
					emptyEmbed = false;
				}
			} else {
				String out = "";
				for (String l : lines)
					out += l;
				if (!out.isEmpty())
					this.content = out;
				this.embed = (e) -> {};
			}
			this.author = author;
		}

		@Override
		public void onSend(StarotaServer server, TextChannel channel, Message msg) {
			if (content == null)
				msg.edit((m) -> m.setEmbed(getEmbed(server)));
			else
				msg.edit((m) -> m.setContent(content).setEmbed(getEmbed(server)));
			msg.addReaction(EmojiConstants.getBooleanEmoji(true));
			msg.addReaction(EmojiConstants.getBooleanEmoji(false));
		}

		@Override
		public void onEdit(StarotaServer server, TextChannel channel, Message msg) {
			if (content == null)
				msg.edit((m) -> m.setEmbed(getEmbed(server)));
			else
				msg.edit((m) -> m.setContent(content).setEmbed(getEmbed(server)));
			msg.addReaction(EmojiConstants.getBooleanEmoji(true));
			msg.addReaction(EmojiConstants.getBooleanEmoji(false));
		}

		@Override
		public void onReactionAdded(StarotaServer server, TextChannel channel, Message msg,
				Member member, ReactionEmoji react) {
			if (react.equals(EmojiConstants.getBooleanEmoji(true))) {
				if (embed != null && !emptyEmbed)
					Starota.sendOwnersMessage(content, embed, author);
				else if (emptyEmbed)
					Starota.sendOwnersMessage(content, author);
				else
					Starota.sendOwnersMessage(content, author);
				channel.createMessage("Sent");
			} else if (react.equals(EmojiConstants.getBooleanEmoji(false)))
				msg.delete();
		}

		@Override
		protected Consumer<EmbedCreateSpec> getEmbed(StarotaServer server) {
			return embed;
		}

	}

}
