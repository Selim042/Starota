package us.myles_selim.starota.trading.commands;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import us.myles_selim.starota.commands.registry.Command;
import us.myles_selim.starota.commands.registry.CommandRegistry;
import us.myles_selim.starota.trading.Tradeboard;
import us.myles_selim.starota.trading.TradeboardPost;

public class CommandRemoveTrade extends Command {

	public CommandRemoveTrade() {
		super("removeTrade", "Removes a given trade posted by yourself from the tradeboard");
	}

	@Override
	public void execute(String[] args, IMessage message, IGuild guild, IChannel channel) {
		if (args.length < 2) {
			channel.sendMessage(
					"**Usage**: " + CommandRegistry.getPrefix(guild) + this.getName() + " [postId]");
			return;
		}
		int id;
		try {
			id = Integer.parseInt(args[1]);
		} catch (NumberFormatException e) {
			channel.sendMessage(
					"**Usage**: " + CommandRegistry.getPrefix(guild) + this.getName() + " [postId]");
			return;
		}
		TradeboardPost post = Tradeboard.removePost(guild, id);
		if (post == null)
			channel.sendMessage("Trade #" + String.format("%04d", id) + " not found");
		else
			channel.sendMessage("Trade #" + String.format("%04d", id) + " removed",
					Tradeboard.getPostEmbed(guild, post));
	}

}
