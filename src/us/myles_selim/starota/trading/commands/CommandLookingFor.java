package us.myles_selim.starota.trading.commands;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import us.myles_selim.starota.commands.registry.Command;
import us.myles_selim.starota.commands.registry.CommandRegistry;
import us.myles_selim.starota.trading.EnumPokemon;
import us.myles_selim.starota.trading.FormManager;
import us.myles_selim.starota.trading.Tradeboard;
import us.myles_selim.starota.trading.TradeboardPost;
import us.myles_selim.starota.trading.forms.FormSet;
import us.myles_selim.starota.trading.forms.FormSet.Form;

public class CommandLookingFor extends Command {

	public CommandLookingFor() {
		super("lookingFor");
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

		if (!FormManager.isAvailable(pokemon)) {
			channel.sendMessage("Pokemon **" + pokemon + "** is not available");
			return;
		}

		if (!pokemon.isTradable()) {
			channel.sendMessage("Pokemon **" + pokemon + "** is not tradable");
			return;
		}

		if (args.length >= 3) {
			boolean arg2Shiny = false;
			if (args[2].equalsIgnoreCase("shiny") || args[2].equalsIgnoreCase("s")) {
				arg2Shiny = true;
				shiny = true;
			} else if (args.length >= 4
					&& (args[3].equalsIgnoreCase("shiny") || args[2].equalsIgnoreCase("s")))
				shiny = true;
			if (!arg2Shiny) {
				FormSet forms = pokemon.getFormSet();
				if (forms != null)
					form = forms.getForm(args[2]);
				if (form == null) {
					channel.sendMessage(
							"Pokemon **" + pokemon + "** cannot have form \"" + args[2] + "\"");
					return;
				}
			}
		}
		if (pokemon.getFormSet() != null && form == null)
			form = pokemon.getFormSet().getDefaultForm();

		if (shiny && ((form == null && !FormManager.isShinyable(pokemon))
				|| (form != null && !form.canBeShiny(pokemon)))) {
			channel.sendMessage("Pokemon **" + pokemon + "** cannot be shiny"
					+ (form == null ? "" : " in form \"" + form + "\""));
			return;
		}

		TradeboardPost post = Tradeboard.newPost(guild, true, message.getAuthor().getLongID(), pokemon,
				form, shiny);
		channel.sendMessage(Tradeboard.getPostEmbed(guild, post));
	}

}
