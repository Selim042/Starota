package us.myles_selim.starota.commands.selim_pm;

import com.google.gson.Gson;

import sx.blah.discord.api.internal.json.objects.EmbedObject;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IReaction;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.EmbedBuilder;
import sx.blah.discord.util.RequestBuffer;
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.commands.registry.java.JavaCommand;
import us.myles_selim.starota.misc.data_types.EmbedHolder;
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
	public void execute(String[] args, IMessage message, IGuild guild, IChannel channel)
			throws Exception {
		if (guild != null)
			return;
		// String text =
		// message.getContent().substring(message.getContent().indexOf(" ") +
		// 1);
		// int firstLineIndex = text.indexOf("\n");
		// String type = text.substring(text.indexOf("`") + 3, firstLineIndex);
		// text = text.substring(firstLineIndex, text.length() - 3);
		// String[] lines = text.split("\n");
		//
		// if (type.equalsIgnoreCase("json")) {
		// EmbedHolder embed = GSON.fromJson(text, EmbedHolder.class);
		// if (embed != null) {
		// Starota.sendOwnersMessage(embed.content, embed.embed,
		// message.getAuthor());
		// return;
		// }
		// }
		//
		// String out = "";
		// for (String l : lines)
		// out += l;
		// if (!out.isEmpty())
		// Starota.sendOwnersMessage(out, message.getAuthor());
		new ReactionMessageOwnerMessage(message.getContent(), message.getAuthor()).sendMessage(channel);
	}

	private static class ReactionMessageOwnerMessage extends ReactionMessage {

		private String content;
		private EmbedObject embed;
		private boolean emptyEmbed = true;
		private IUser author;

		public ReactionMessageOwnerMessage(String msg, IUser author) {
			int firstLineIndex = msg.indexOf("\n");
			String type = msg.substring(msg.indexOf("`") + 3, firstLineIndex);
			msg = msg.substring(firstLineIndex, msg.length() - 3);
			String[] lines = msg.split("\n");

			if (type.equalsIgnoreCase("json")) {
				EmbedHolder embed = GSON.fromJson(msg, EmbedHolder.class);
				if (embed != null) {
					this.content = embed.content;
					this.embed = embed.embed;
					emptyEmbed = false;
				}
			} else {
				String out = "";
				for (String l : lines)
					out += l;
				if (!out.isEmpty())
					this.content = out;
				this.embed = new EmbedBuilder().build();
			}
			this.author = author;
		}

		@Override
		public void onSend(StarotaServer server, IChannel channel, IMessage msg) {
			if (content == null)
				RequestBuffer.request(() -> msg.edit(getEmbed(server)));
			else
				RequestBuffer.request(() -> msg.edit(content, getEmbed(server)));
			RequestBuffer.request(() -> msg.addReaction(EmojiConstants.getBooleanEmoji(true))).get();
			RequestBuffer.request(() -> msg.addReaction(EmojiConstants.getBooleanEmoji(false))).get();
		}

		@Override
		public void onEdit(StarotaServer server, IChannel channel, IMessage msg) {
			if (content == null)
				RequestBuffer.request(() -> msg.edit(getEmbed(server)));
			else
				RequestBuffer.request(() -> msg.edit(content, getEmbed(server)));
			RequestBuffer.request(() -> msg.addReaction(EmojiConstants.getBooleanEmoji(true))).get();
			RequestBuffer.request(() -> msg.addReaction(EmojiConstants.getBooleanEmoji(false))).get();
		}

		@Override
		public void onReactionAdded(StarotaServer server, IChannel channel, IMessage msg, IUser user,
				IReaction react) {
			if (react.getEmoji().equals(EmojiConstants.getBooleanEmoji(true))) {
				if (embed != null && !emptyEmbed)
					Starota.sendOwnersMessage(content, embed, author);
				else if (emptyEmbed)
					Starota.sendOwnersMessage(content, author);
				else
					Starota.sendOwnersMessage(content, author);
				channel.sendMessage("Sent");
			} else if (react.getEmoji().equals(EmojiConstants.getBooleanEmoji(false)))
				msg.delete();
		}

		@Override
		protected EmbedObject getEmbed(StarotaServer server) {
			return embed;
		}

	}

}
