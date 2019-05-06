package us.myles_selim.starota.trading.commands;

import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.TextChannel;
import us.myles_selim.starota.commands.StarotaCommand;
import us.myles_selim.starota.trading.TradeboardPost;
import us.myles_selim.starota.wrappers.StarotaServer;

public class CommandRemoveTrade extends StarotaCommand {

	public CommandRemoveTrade() {
		super("removeTrade", "Removes a given trade posted by yourself from the tradeboard.");
	}

	@Override
	public String getGeneralUsage() {
		return "[postId]";
	}

	@Override
	public void execute(String[] args, Message message, StarotaServer server, TextChannel channel) {
		if (args.length < 2) {
			channel.createMessage("**Usage**: " + server.getPrefix() + this.getName() + " [postId]");
			return;
		}
		int id;
		try {
			id = Integer.parseInt(args[1]);
		} catch (NumberFormatException e) {
			channel.createMessage("**Usage**: " + server.getPrefix() + this.getName() + " [postId]");
			return;
		}
		TradeboardPost post = server.getPost(id);
		if (post != null && post.getOwner() == message.getAuthor().get().getId().asLong()) {
			server.removePost(id);
			channel.createMessage((m) -> m.setContent("Trade #" + String.format("%04d", id) + " removed")
					.setEmbed(post.getPostEmbed(server)));
		} else
			channel.createMessage("Trade #" + String.format("%04d", id) + " not found");
	}

}
