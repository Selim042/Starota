package us.myles_selim.starota.pokedex;

import java.util.EnumSet;
import java.util.List;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.Permissions;
import sx.blah.discord.util.EmbedBuilder;
import us.myles_selim.starota.commands.StarotaCommand;
import us.myles_selim.starota.enums.EnumPokemon;
import us.myles_selim.starota.misc.utils.MiscUtils;
import us.myles_selim.starota.wrappers.StarotaServer;

public class CommandPokedex extends StarotaCommand {

	public CommandPokedex() {
		super("pokedex", "Displays Pokedex information about the given Pokemon.");
	}

	@Override
	public EnumSet<Permissions> getCommandPermissions() {
		return EnumSet.of(Permissions.SEND_MESSAGES, Permissions.EMBED_LINKS);
	}

	@Override
	public List<String> getAliases() {
		List<String> aliases = super.getAliases();
		aliases.add("dex");
		return aliases;
	}

	@Override
	public String getGeneralUsage() {
		return "<pokemon>";
	}

	@Override
	public void execute(String[] args, IMessage message, StarotaServer server, IChannel channel)
			throws Exception {
		if (args.length < 2) {
			channel.sendMessage(
					"**Usage**: " + server.getPrefix() + getName() + " " + getGeneralUsage());
			return;
		}
		EnumPokemon pokemon;
		try {
			pokemon = EnumPokemon.getPokemon(Integer.parseInt(args[1]));
		} catch (NumberFormatException e) {
			pokemon = EnumPokemon.getPokemon(args[1]);
		}
		if (pokemon == null) {
			EmbedBuilder builder = new EmbedBuilder();
			builder.withTitle("Did you mean...?");
			for (EnumPokemon poke : MiscUtils.getSuggestedPokemon(args[1], 6)) {
				if (poke != null)
					builder.appendDesc("- " + poke + "\n");
			}
			channel.sendMessage("Pokemon \"" + args[1] + "\" not found", builder.build());
			return;
		}
		IMessage oldMessage = null;
		if (!GoHubDatabase.isEntryLoaded(pokemon))
			oldMessage = channel.sendMessage(GoHubDatabase.LOADING_EMBED);
		PokedexEntry entry = GoHubDatabase.getEntry(pokemon);
		PokedexReactionMessage pokedexMessage = new PokedexReactionMessage(entry);
		pokedexMessage.editMessage(channel, oldMessage);
	}

}
