package us.myles_selim.starota.pokedex;

import java.util.List;

import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.TextChannel;
import discord4j.core.object.util.Permission;
import discord4j.core.object.util.PermissionSet;
import us.myles_selim.starota.commands.StarotaCommand;
import us.myles_selim.starota.enums.EnumPokemon;
import us.myles_selim.starota.misc.data_types.EmbedBuilder;
import us.myles_selim.starota.misc.utils.MiscUtils;
import us.myles_selim.starota.wrappers.StarotaServer;

public class CommandPokedex extends StarotaCommand {

	public CommandPokedex() {
		super("pokedex", "Displays Pokedex information about the given Pokemon.");
	}

	@Override
	public PermissionSet getCommandPermission() {
		return PermissionSet.of(Permission.SEND_MESSAGES, Permission.EMBED_LINKS,
				Permission.USE_EXTERNAL_EMOJIS, Permission.ADD_REACTIONS, Permission.MANAGE_MESSAGES);
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
	public void execute(String[] args, Message message, StarotaServer server, TextChannel channel)
			throws Exception {
		if (args.length < 2) {
			channel.createMessage(
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
			builder.setTitle("Did you mean...?");
			for (EnumPokemon poke : MiscUtils.getSuggestedPokemon(args[1], 6)) {
				if (poke != null)
					builder.appendDesc("- " + poke + "\n");
			}
			channel.createMessage((m) -> m.setContent("Pokemon \"" + args[1] + "\" not found")
					.setEmbed(builder.build()));
			return;
		}
		Message oldMessage = null;
		if (!GoHubDatabase.isEntryLoaded(pokemon))
			oldMessage = channel.createEmbed(GoHubDatabase.LOADING_EMBED).block();
		PokedexEntry entry = GoHubDatabase.getEntry(pokemon);
		PokedexReactionMessage pokedexMessage = new PokedexReactionMessage(entry);
		pokedexMessage.editMessage(channel, oldMessage);
	}

}
