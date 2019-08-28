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
import us.myles_selim.starota.trading.PokemonInstance.ErrorPokemonInstance;
import us.myles_selim.starota.trading.forms.FormSet.Form;
import us.myles_selim.starota.wrappers.StarotaServer;

public class CommandLookingFor extends BotCommand<StarotaServer> {

	public CommandLookingFor() {
		super("lookingFor", "Looks for a trade matching your search, creates one if it doesn't exist.");
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
		if (pokemonInst instanceof ErrorPokemonInstance) {
			channel.createMessage(
					"Cannot find parameter \"" + ((ErrorPokemonInstance) pokemonInst).badArg + "\".")
					.block();
			return;
		}
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
		if (pokemonInst.getShiny() && !pokemon.isShinyable()) {
			channel.createMessage("Pokemon **" + pokemon + "** is not shinyable").block();
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

		boolean foundTrade = false;
		List<TradeboardPost> posts = server.findPosts(true, pokemon, form, shiny, gender,
				pokemonInst.isLegacy());
		for (int i = 0; i < posts.size(); i++) {
			TradeboardPost p = posts.get(i);
			if (!foundTrade)
				channel.createMessage((m) -> m
						.setContent(
								"Found the following " + posts.size() + " trades that match your search")
						.setEmbed(p.getPostEmbed(server))).block();
			else
				channel.createEmbed(p.getPostEmbed(server)).block();
			foundTrade = true;
		}

		if (!foundTrade) {
			TradeboardPost post = server.newPost(true, message.getAuthor().get().getId().asLong(),
					pokemon, form, shiny, gender, pokemonInst.isLegacy());
			channel.createMessage((m) -> m.setContent("Posted a new trade for your search")
					.setEmbed(post.getPostEmbed(server, false))).block();
		}
	}

}
