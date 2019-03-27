package us.myles_selim.starota.trading.commands;

import java.util.List;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.RequestBuffer;
import us.myles_selim.starota.commands.StarotaCommand;
import us.myles_selim.starota.enums.EnumGender;
import us.myles_selim.starota.enums.EnumPokemon;
import us.myles_selim.starota.trading.PokemonInstance;
import us.myles_selim.starota.trading.TradeboardPost;
import us.myles_selim.starota.trading.forms.FormSet.Form;
import us.myles_selim.starota.wrappers.StarotaServer;

public class CommandLookingFor extends StarotaCommand {

	public CommandLookingFor() {
		super("lookingFor", "Looks for a trade matching your search, creates one if it doesn't exist.");
	}

	@Override
	public String getGeneralUsage() {
		return "[pokemon] <form> <shiny> <gender>";
	}

	@Override
	public void execute(String[] args, IMessage message, StarotaServer server, IChannel channel) {
		if (args.length < 2) {
			channel.sendMessage("**Usage**: " + server.getPrefix() + this.getName()
					+ " [pokemon] <form> <shiny> <gender>");
			return;
		}
		PokemonInstance pokemonInst = PokemonInstance.getInstance(args);
		EnumPokemon pokemon = pokemonInst.getPokemon();
		Form form = pokemonInst.getForm();
		boolean shiny = pokemonInst.getShiny();
		EnumGender gender = pokemonInst.getGender();
		if (pokemon == null) {
			channel.sendMessage("Pokemon \"" + args[1] + "\" not found");
			return;
		}
		if (!pokemon.isShinyable()) {
			channel.sendMessage("Pokemon **" + pokemon + "** is not available");
			return;
		}
		if (!pokemon.isTradable()) {
			channel.sendMessage("Pokemon **" + pokemon + "** is not tradable");
			return;
		}
		if (shiny && ((form == null && !pokemon.isShinyable())
				|| (form != null && !form.canBeShiny(pokemon)))) {
			channel.sendMessage("Pokemon **" + pokemon + "** cannot be shiny"
					+ (form == null ? "" : " in form \"" + form + "\""));
			return;
		}
		if (pokemon.getGenderPossible() != gender && pokemon.getGenderPossible() != EnumGender.EITHER) {
			channel.sendMessage("Pokemon **" + pokemon + "** cannot be " + gender);
			return;
		}

		boolean foundTrade = false;
		List<TradeboardPost> posts = server.findPosts(true, pokemon, form, shiny, gender,
				pokemonInst.isLegacy());
		for (int i = 0; i < posts.size(); i++) {
			TradeboardPost p = posts.get(i);
			if (!foundTrade)
				RequestBuffer.request(() -> channel.sendMessage(
						"Found the following " + posts.size() + " trades that match your search",
						p.getPostEmbed(server)));
			else
				RequestBuffer.request(() -> channel.sendMessage(p.getPostEmbed(server)));
			foundTrade = true;
		}

		if (!foundTrade) {
			TradeboardPost post = server.newPost(true, message.getAuthor().getLongID(), pokemon, form,
					shiny, gender, pokemonInst.isLegacy());
			channel.sendMessage("Posted a new trade for your search", post.getPostEmbed(server));
		}
	}

}
