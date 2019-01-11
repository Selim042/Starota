package us.myles_selim.starota.pokedex;

import java.util.List;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import us.myles_selim.starota.commands.StarotaCommand;
import us.myles_selim.starota.enums.EnumPokemon;
import us.myles_selim.starota.wrappers.StarotaServer;

public class CommandPokedex extends StarotaCommand {

	public CommandPokedex() {
		super("pokedex", "Displays Pokedex information about the given Pokemon.");
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
			channel.sendMessage("Pokemon \"" + args[1] + "\" not found");
			return;
		}
		channel.sendMessage(GoHubDatabase.getEntry(pokemon).toEmbed());
	}

}
