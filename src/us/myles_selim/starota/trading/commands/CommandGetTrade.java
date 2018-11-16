package us.myles_selim.starota.trading.commands;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import us.myles_selim.starota.commands.registry.PrimaryCommandHandler;
import us.myles_selim.starota.commands.registry.java.JavaCommand;
import us.myles_selim.starota.trading.Tradeboard;
import us.myles_selim.starota.trading.TradeboardPost;

public class CommandGetTrade extends JavaCommand {

	public CommandGetTrade() {
		super("getTrade", "Gets a specific trade by ID.");
	}

	@Override
	public String getGeneralUsage() {
		return "[tradeId]";
	}

	@Override
	public void execute(String[] args, IMessage message, IGuild guild, IChannel channel) {
		if (args.length < 2) {
			channel.sendMessage(
					"**Usage**: " + PrimaryCommandHandler.getPrefix(guild) + this.getName() + " [tradeId]");
			return;
		}
		int tradeId = -1;
		try {
			tradeId = Integer.parseInt(args[1]);
		} catch (NumberFormatException e) {}
		TradeboardPost post = Tradeboard.getPost(guild, tradeId);
		if (post == null) {
			channel.sendMessage("Trade \"" + args[1] + "\" not found.");
			return;
		}
		channel.sendMessage(Tradeboard.getPostEmbed(guild, post));
	}

}
