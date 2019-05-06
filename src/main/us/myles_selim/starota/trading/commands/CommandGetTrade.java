package us.myles_selim.starota.trading.commands;

import java.util.EnumSet;

import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.TextChannel;
import discord4j.core.object.util.PermissionSet;
import discord4j.core.object.util.Permission;
import us.myles_selim.starota.commands.StarotaCommand;
import us.myles_selim.starota.trading.TradeboardPost;
import us.myles_selim.starota.trading.TradeboardReactionMessage;
import us.myles_selim.starota.wrappers.StarotaServer;

public class CommandGetTrade extends StarotaCommand {

	public CommandGetTrade() {
		super("getTrade", "Gets a specific trade by ID.");
	}

	@Override
	public PermissionSet getCommandPermission() {
		return PermissionSet.of(Permission.SEND_MESSAGES, Permission.EMBED_LINKS,
				Permission.USE_EXTERNAL_EMOJIS, Permission.ADD_REACTIONS, Permission.MANAGE_MESSAGES);
	}

	@Override
	public String getGeneralUsage() {
		return "[tradeId]";
	}

	@Override
	public void execute(String[] args, Message message, StarotaServer server, TextChannel channel) {
		if (args.length < 2) {
			channel.createMessage("**Usage**: " + server.getPrefix() + this.getName() + " [tradeId]");
			return;
		}
		int tradeId = -1;
		try {
			tradeId = Integer.parseInt(args[1]);
		} catch (NumberFormatException e) {}
		TradeboardPost post = server.getPost(tradeId);
		if (post == null) {
			channel.createMessage("Trade \"" + args[1] + "\" not found.");
			return;
		}
		// channel.createMessage(post.getPostEmbed(server));
		new TradeboardReactionMessage(server.getDiscordGuild(), post).createMessage(channel);
	}

}
