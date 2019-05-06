package us.myles_selim.starota.trading.commands;

import java.util.List;

import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.TextChannel;
import discord4j.core.object.util.Permission;
import discord4j.core.object.util.PermissionSet;
import us.myles_selim.starota.commands.StarotaCommand;
import us.myles_selim.starota.enums.EnumGender;
import us.myles_selim.starota.enums.EnumPokemon;
import us.myles_selim.starota.trading.PokemonInstance;
import us.myles_selim.starota.trading.TradeboardPost;
import us.myles_selim.starota.trading.forms.FormSet.Form;
import us.myles_selim.starota.wrappers.StarotaServer;

public class CommandFindTrade extends StarotaCommand {

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
	public void execute(String[] args, Message message, StarotaServer server, TextChannel channel) {
		if (args.length < 2) {
			channel.createMessage("**Usage**: " + server.getPrefix() + this.getName()
					+ " [pokemon] <form> <shiny> <gender>");
			return;
		}
		PokemonInstance pokemonInst = PokemonInstance.getInstance(args);
		EnumPokemon pokemon = pokemonInst.getPokemon();
		Form form = pokemonInst.getForm();
		boolean shiny = pokemonInst.getShiny();
		EnumGender gender = pokemonInst.getGender();
		if (pokemon == null) {
			channel.createMessage("Pokemon \"" + args[1] + "\" not found");
			return;
		}
		if (!pokemon.isAvailable()) {
			channel.createMessage("Pokemon **" + pokemon + "** is not available");
			return;
		}
		if (!pokemon.isTradable()) {
			channel.createMessage("Pokemon **" + pokemon + "** is not tradable");
			return;
		}
		if (shiny && ((form == null && !pokemon.isShinyable())
				|| (form != null && !form.canBeShiny(pokemon)))) {
			channel.createMessage("Pokemon **" + pokemon + "** cannot be shiny"
					+ (form == null ? "" : " in form \"" + form + "\""));
			return;
		}
		if (pokemon.getGenderPossible() != gender && pokemon.getGenderPossible() != EnumGender.EITHER) {
			channel.createMessage("Pokemon **" + pokemon + "** cannot be " + gender);
			return;
		}

		List<TradeboardPost> posts = server.findPosts(pokemon, form, shiny, gender,
				pokemonInst.isLegacy());
		if (posts.isEmpty())
			channel.createMessage("No trades currently open matching your search");
		else {
			channel.createMessage("Found " + posts.size() + " results for your search");
			for (TradeboardPost p : posts)
				channel.createEmbed(p.getPostEmbed(server));
		}
	}

}
