package us.myles_selim.starota.pokedex;

import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.MessageChannel;
import us.myles_selim.starota.commands.BotCommand;
import us.myles_selim.starota.commands.registry.CommandException;
import us.myles_selim.starota.enums.EnumPokemon;
import us.myles_selim.starota.enums.PokemonData;
import us.myles_selim.starota.misc.utils.EmbedBuilder;
import us.myles_selim.starota.wrappers.StarotaServer;

public class CommandCPTable extends BotCommand<StarotaServer> {

	public CommandCPTable() {
		super("cptable", "Shows CPs for various levels for given IVs, or 15/15/15 by default.");
	}

	@Override
	public String getGeneralUsage() {
		return "[pokemon] <ivs>";
	}

	@Override
	public void execute(String[] args, Message message, StarotaServer server, MessageChannel channel)
			throws CommandException {
		if (args.length < 2) {
			this.sendUsage(server.getPrefix(), channel);
			return;
		}
		EnumPokemon pokemon = EnumPokemon.getPokemon(args[1]);
		if (pokemon == null) {
			channel.createMessage("Pokemon \"" + args[1] + "\" not found.").block();
			return;
		}
		int atk = 15;
		int def = 15;
		int sta = 15;
		if (args.length > 2) {
			if (!args[2].matches("(1[0-5]|[0-9])\\/(1[0-5]|[0-9])\\/(1[0-5]|[0-9])")) {
				channel.createMessage("Invalid IVs.  Try something between 0/0/0 and 15/15/15.").block();
				return;
			}
			String[] parts = args[2].split("\\/");
			atk = Integer.parseInt(parts[0]);
			def = Integer.parseInt(parts[1]);
			sta = Integer.parseInt(parts[2]);
		}

		if (args.length > 1)
			pokemon = EnumPokemon.getPokemon(args[1]);
		PokedexEntry entry = GoHubDatabase.getEntry(pokemon);
		PokemonData pokemonData = pokemon.getData();
		EmbedBuilder builder = new EmbedBuilder().withThumbnail(pokemonData.getDefaultForm().getImage());
		builder.withTitle(String.format("%s %d/%d/%d CPs:", pokemonData.getName(), atk, def, sta));
		builder.withColor(pokemonData.getType1().getColor());
		builder.appendDesc("**Important Levels**:\n");
		builder.appendDesc("Field Research: 15\n");
		builder.appendDesc("Egg Hatch: 20\n");
		builder.appendDesc("Raid Boss: 20\n");
		builder.appendDesc("Raid Boss (Weather Boost): 25\n");
		builder.appendDesc("\n```\nLevel: CP:   | Level: CP:\n");
		builder.appendDesc("-------------+-------------\n");
		for (int i = 1; i <= 20; i++)
			builder.appendDesc(String.format("%-,6d %-,5d | %-,6d %-,5d\n", i,
					entry.getCP(i, atk, def, sta), i + 20, entry.getCP(i + 20, atk, def, sta)));
		builder.appendDesc(
				"\n```\n**Note**: This command currently only supports \"default\" forms of Pokemon, "
						+ "no alternative forms, Alolan forms, etc.");
		channel.createEmbed(builder.build()).block();
	}

}
