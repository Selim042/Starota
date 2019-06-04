package us.myles_selim.starota.trading.commands;

import java.util.EnumSet;
import java.util.List;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.Permissions;
import sx.blah.discord.util.RequestBuffer;
import us.myles_selim.starota.commands.BotCommand;
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
	public EnumSet<Permissions> getCommandPermissions() {
		return EnumSet.of(Permissions.SEND_MESSAGES, Permissions.EMBED_LINKS);
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
		if (!pokemon.isAvailable()) {
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

		List<TradeboardPost> posts = server.findPosts(pokemon, form, shiny, gender,
				pokemonInst.isLegacy());
		if (posts.isEmpty())
			channel.sendMessage("No trades currently open matching your search");
		else {
			channel.sendMessage("Found " + posts.size() + " results for your search");
			for (TradeboardPost p : posts)
				RequestBuffer.request(() -> channel.sendMessage(p.getPostEmbed(server)));
		}
	}

}
