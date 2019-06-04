package us.myles_selim.starota.trading.commands;

import java.util.EnumSet;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.Permissions;
import us.myles_selim.starota.commands.BotCommand;
import us.myles_selim.starota.trading.TradeboardPost;
import us.myles_selim.starota.trading.TradeboardReactionMessage;
import us.myles_selim.starota.wrappers.StarotaServer;

public class CommandGetTrade extends BotCommand<StarotaServer> {

	public CommandGetTrade() {
		super("getTrade", "Gets a specific trade by ID.");
	}

	@Override
	public EnumSet<Permissions> getCommandPermissions() {
		return EnumSet.of(Permissions.SEND_MESSAGES, Permissions.EMBED_LINKS,
				Permissions.USE_EXTERNAL_EMOJIS, Permissions.ADD_REACTIONS, Permissions.MANAGE_MESSAGES);
	}

	@Override
	public String getGeneralUsage() {
		return "[tradeId]";
	}

	@Override
	public void execute(String[] args, IMessage message, StarotaServer server, IChannel channel) {
		if (args.length < 2) {
			channel.sendMessage("**Usage**: " + server.getPrefix() + this.getName() + " [tradeId]");
			return;
		}
		int tradeId = -1;
		try {
			tradeId = Integer.parseInt(args[1]);
		} catch (NumberFormatException e) {}
		TradeboardPost post = server.getPost(tradeId);
		if (post == null) {
			channel.sendMessage("Trade \"" + args[1] + "\" not found.");
			return;
		}
		// channel.sendMessage(post.getPostEmbed(server));
		new TradeboardReactionMessage(server.getDiscordGuild(), post).sendMessage(channel);
	}

}
