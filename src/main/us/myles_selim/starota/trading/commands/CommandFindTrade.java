package us.myles_selim.starota.trading.commands;

import java.util.List;

import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.MessageChannel;
import discord4j.core.object.util.Permission;
import discord4j.core.object.util.PermissionSet;
import us.myles_selim.starota.commands.BotCommand;
import us.myles_selim.starota.commands.registry.CommandException;
import us.myles_selim.starota.enums.EnumGender;
import us.myles_selim.starota.enums.EnumPokemon;
import us.myles_selim.starota.trading.PokemonInstance;
import us.myles_selim.starota.trading.TradeboardPost;
import us.myles_selim.starota.trading.forms.FormSet.Form;
import us.myles_selim.starota.wrappers.StarotaServer;

public class CommandFindTrade extends BotCommand<StarotaServer> {

	public CommandFindTrade() {
		super("findTrade", "Searches for a specific trade.");
	}

	@Override
	public PermissionSet getCommandPermission() {
		return PermissionSet.of(Permission.SEND_MESSAGES, Permission.EMBED_LINKS);
	}

	@Override
	public String getGeneralUsage() {
		return "[pokemon] <form> <shiny> <gender>";
	}

	@Override
	public void execute(String[] args, Message message, StarotaServer server, MessageChannel channel)
			throws CommandException {
		if (args.length < 2) {
			channel.createMessage("**Usage**: " + server.getPrefix() + this.getName()
					+ " [pokemon] <form> <shiny> <gender>").block();
			return;
		}
		PokemonInstance pokemonInst = PokemonInstance.getInstance(args);
		EnumPokemon pokemon = pokemonInst.getPokemon();
		Form form = pokemonInst.getForm();
		boolean shiny = pokemonInst.getShiny();
		EnumGender gender = pokemonInst.getGender();
		if (pokemon == null) {
			channel.createMessage("Pokemon \"" + args[1] + "\" not found").block();
			return;
		}
		if (!pokemon.isAvailable()) {
			channel.createMessage("Pokemon **" + pokemon + "** is not available").block();
			return;
		}
		if (!pokemon.isTradable()) {
			channel.createMessage("Pokemon **" + pokemon + "** is not tradable").block();
			return;
		}
		if (shiny && ((form == null && !pokemon.isShinyable())
				|| (form != null && !form.canBeShiny(pokemon)))) {
			channel.createMessage("Pokemon **" + pokemon + "** cannot be shiny"
					+ (form == null ? "" : " in form \"" + form + "\"")).block();
			return;
		}
		if (pokemon.getGenderPossible() != gender && pokemon.getGenderPossible() != EnumGender.EITHER) {
			channel.createMessage("Pokemon **" + pokemon + "** cannot be " + gender).block();
			return;
		}

		List<TradeboardPost> posts = server.findPosts(pokemon, form, shiny, gender,
				pokemonInst.isLegacy());
		if (posts.isEmpty())
			channel.createMessage("No trades currently open matching your search").block();
		else {
			channel.createMessage("Found " + posts.size() + " results for your search").block();
			for (TradeboardPost p : posts)
				channel.createEmbed(p.getPostEmbed(server)).block();
		}
	}

}
