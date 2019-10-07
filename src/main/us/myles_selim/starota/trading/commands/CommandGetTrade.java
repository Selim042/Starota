package us.myles_selim.starota.trading.commands;

import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.MessageChannel;
import discord4j.core.object.entity.TextChannel;
import discord4j.core.object.util.Permission;
import discord4j.core.object.util.PermissionSet;
import us.myles_selim.starota.commands.BotCommand;
import us.myles_selim.starota.commands.registry.CommandException;
import us.myles_selim.starota.trading.TradeboardPost;
import us.myles_selim.starota.trading.TradeboardReactionMessage;
import us.myles_selim.starota.wrappers.StarotaServer;

public class CommandGetTrade extends BotCommand<StarotaServer> {

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
	public void execute(String[] args, Message message, StarotaServer server, MessageChannel channel)
			throws CommandException {
		if (args.length < 2) {
			channel.createMessage("**Usage**: " + server.getPrefix() + this.getName() + " [tradeId]")
					.block();
			return;
		}
		int tradeId;
		try {
			tradeId = Integer.parseInt(args[1]);
		} catch (NumberFormatException e) {
			tradeId = -1;
		}
		TradeboardPost post = server.getPost(tradeId);
		if (post == null) {
			channel.createMessage("Trade \"" + args[1] + "\" not found.").block();
			return;
		}
		// channel.createMessage(post.getPostEmbed(server));
		new TradeboardReactionMessage(server.getDiscordGuild(), post)
				.createMessage((TextChannel) channel);
	}

}
