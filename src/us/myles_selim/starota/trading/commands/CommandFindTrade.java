package us.myles_selim.starota.trading.commands;

import java.util.List;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import us.myles_selim.starota.commands.registry.Command;
import us.myles_selim.starota.commands.registry.CommandRegistry;
import us.myles_selim.starota.trading.EnumPokemon;
import us.myles_selim.starota.trading.Tradeboard;
import us.myles_selim.starota.trading.TradeboardPost;
import us.myles_selim.starota.trading.forms.FormSet.Form;

public class CommandFindTrade extends Command {

	public CommandFindTrade() {
		super("findTrade", "Searches for a specific trade.");
	}

	@Override
	public void execute(String[] args, IMessage message, IGuild guild, IChannel channel) {
		if (args.length < 2) {
			channel.sendMessage("**Usage**: " + CommandRegistry.getPrefix(guild) + this.getName()
					+ " [pokemon] <form> <shiny>");
			return;
		}
		EnumPokemon pokemon = EnumPokemon.getPokemon(args[1]);
		Form form = null;
		boolean shiny = false;
		if (pokemon == null) {
			channel.sendMessage("**Usage**: " + CommandRegistry.getPrefix(guild) + this.getName()
					+ " [pokemon] <form> <shiny>");
			return;
		}
		List<TradeboardPost> posts = Tradeboard.findPosts(guild, pokemon);
		if (posts.isEmpty())
			channel.sendMessage("No trades currently open for \"" + pokemon + "\"");
		else
			for (TradeboardPost p : posts) {
				channel.sendMessage(Tradeboard.getPostEmbed(guild, p));
				try {
					Thread.sleep(1500);
				} catch (InterruptedException e) {}
			}
	}

}
