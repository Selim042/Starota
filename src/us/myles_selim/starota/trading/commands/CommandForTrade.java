package us.myles_selim.starota.trading.commands;

import java.util.List;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.RequestBuffer;
import us.myles_selim.starota.commands.registry.Command;
import us.myles_selim.starota.commands.registry.CommandRegistry;
import us.myles_selim.starota.trading.EnumGender;
import us.myles_selim.starota.trading.EnumPokemon;
import us.myles_selim.starota.trading.FormManager;
import us.myles_selim.starota.trading.PokemonInstance;
import us.myles_selim.starota.trading.Tradeboard;
import us.myles_selim.starota.trading.TradeboardPost;
import us.myles_selim.starota.trading.forms.FormSet;
import us.myles_selim.starota.trading.forms.FormSet.Form;

public class CommandForTrade extends Command {

	public CommandForTrade() {
		super("forTrade", "Posts the given Pokemon up for trade.");
	}

	@Override
	public String getGeneralUsage() {
		return "[pokemon] <form> <shiny> <gender>";
	}

	@Override
	public void execute(String[] args, IMessage message, IGuild guild, IChannel channel) {
		if (args.length < 2) {
			channel.sendMessage("**Usage**: " + CommandRegistry.getPrefix(guild) + this.getName()
					+ " [pokemon] <form> <shiny> <gender>");
			return;
		}
		PokemonInstance pokemonInst = PokemonInstance.getInstance(args);
		EnumPokemon pokemon = pokemonInst.getPokemon();
		Form form = pokemonInst.getForm();
		boolean shiny = pokemonInst.getShiny();
		if (pokemon == null) {
			channel.sendMessage("Pokemon \"" + args[1] + "\" not found");
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
			switch (args[2].toLowerCase()) {
			case "shiny":
			case "s":
			case "true":
				arg2Shiny = true;
				shiny = true;
			}
			if (!arg2Shiny && args.length >= 4) {
				switch (args[3].toLowerCase()) {
				case "shiny":
				case "s":
				case "true":
					shiny = true;
				}
			}
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

		boolean foundTrade = false;
		List<TradeboardPost> posts = Tradeboard.findPosts(false, guild, pokemon, form, shiny);
		for (int i = 0; i < posts.size(); i++) {
			TradeboardPost p = posts.get(i);
			if (!foundTrade)
				RequestBuffer.request(() -> {
					channel.sendMessage(
							"Found the following " + posts.size() + " trades that match your search",
							Tradeboard.getPostEmbed(guild, p));
				});
			else
				RequestBuffer.request(() -> {
					channel.sendMessage(Tradeboard.getPostEmbed(guild, p));
				});
			foundTrade = true;
		}

		if (!foundTrade) {
			TradeboardPost post = Tradeboard.newPost(guild, false, message.getAuthor().getLongID(),
					pokemon, form, shiny, EnumGender.EITHER);
			channel.sendMessage("Posted a new trade for your search",
					Tradeboard.getPostEmbed(guild, post));
		}
		// TradeboardPost post = Tradeboard.newPost(guild, false,
		// message.getAuthor().getLongID(), pokemon,
		// form, shiny, EnumGenderPossible.EITHER);
		// channel.sendMessage(Tradeboard.getPostEmbed(guild, post));
	}

}
