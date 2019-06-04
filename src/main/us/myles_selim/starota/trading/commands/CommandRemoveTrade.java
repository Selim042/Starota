package us.myles_selim.starota.trading.commands;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import us.myles_selim.starota.commands.BotCommand;
import us.myles_selim.starota.trading.TradeboardPost;
import us.myles_selim.starota.wrappers.StarotaServer;

public class CommandRemoveTrade extends BotCommand<StarotaServer> {

	public CommandRemoveTrade() {
		super("removeTrade", "Removes a given trade posted by yourself from the tradeboard.");
	}

	@Override
	public String getGeneralUsage() {
		return "[postId]";
	}

	@Override
	public void execute(String[] args, IMessage message, StarotaServer server, IChannel channel) {
		if (args.length < 2) {
			channel.sendMessage("**Usage**: " + server.getPrefix() + this.getName() + " [postId]");
			return;
		}
		int id;
		try {
			id = Integer.parseInt(args[1]);
		} catch (NumberFormatException e) {
			channel.sendMessage("**Usage**: " + server.getPrefix() + this.getName() + " [postId]");
			return;
		}
		TradeboardPost post = server.getPost(id);
		if (post != null && post.getOwner() == message.getAuthor().getLongID()) {
			server.removePost(id);
			channel.sendMessage("Trade #" + String.format("%04d", id) + " removed",
					post.getPostEmbed(server));
		} else
			channel.sendMessage("Trade #" + String.format("%04d", id) + " not found");
	}

}
