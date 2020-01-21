package us.myles_selim.starota.pvp.pokebattler.commands;

import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.MessageChannel;
import us.myles_selim.starota.commands.BotCommand;
import us.myles_selim.starota.commands.registry.CommandException;
import us.myles_selim.starota.enums.EnumPokemon;
import us.myles_selim.starota.forms.Form;
import us.myles_selim.starota.misc.utils.EmbedBuilder;
import us.myles_selim.starota.pokedex.GoHubDatabase;
import us.myles_selim.starota.pokedex.PokedexEntry;
import us.myles_selim.starota.pokedex.PokedexEntry.DexMove;
import us.myles_selim.starota.pvp.pokebattler.PokebattlerAPI;
import us.myles_selim.starota.pvp.pokebattler.PokebattlerAttacker;
import us.myles_selim.starota.pvp.pokebattler.PokebattlerAttacker.PokeBattlerAttackerMoveset;
import us.myles_selim.starota.pvp.pokebattler.PokebattlerLeague;
import us.myles_selim.starota.wrappers.StarotaServer;

public class CommandCupCounters extends BotCommand<StarotaServer> {

	private static final int DISPLAY_COUNT = 15;

	public CommandCupCounters() {
		super("cupCounters",
				"Displays the top " + DISPLAY_COUNT + " counters for the latest Silph Arena Cup.");
	}

	@Override
	public void execute(String[] args, Message message, StarotaServer server, MessageChannel channel)
			throws CommandException {
		EmbedBuilder embed = new EmbedBuilder()
				.withAuthorIcon("https://www.pokebattler.com/favicon-32x32.png")
				.withAuthorName("Pokebattler").withAuthorUrl("https://www.pokebattler.com/");
		int i = 1;
		PokebattlerLeague[] leagues = PokebattlerAPI.getLeagues();
		PokebattlerLeague targetLeague = leagues[leagues.length - 1];
		embed.withTitle(targetLeague.getTitle() + " Top " + DISPLAY_COUNT + " Counters:")
				.withThumbnail("https://silph.gg/img/badges/" + targetLeague.getSilphType() + ".png");
		for (PokebattlerAttacker attacker : targetLeague.getCounters()) {
			if (i > DISPLAY_COUNT)
				break;
			PokeBattlerAttackerMoveset moves = attacker.getMovesets()[0];
			String pokemonId = attacker.getPokemonId();
			EnumPokemon pokemon = EnumPokemon.getPokemon(pokemonId);
			boolean purified = false;
			Form form = pokemon.getFormSet().getDefaultForm();
			if (pokemonId.endsWith("_PURIFIED_FORM"))
				purified = true;
			else if (pokemonId.endsWith("_FORM"))
				form = pokemon.getFormSet().getForm(
						pokemonId.substring(pokemonId.indexOf("_") + 1, pokemonId.indexOf("_FORM")));
			PokedexEntry entry = GoHubDatabase.getEntry(pokemon, form.getGoHubFormName());
			if (entry == null)
				embed.appendDesc(String.format("#%d ERROR\n", i++));
			else {
				String pokemonName = pokemon.getData().getName();
				if (purified)
					pokemonName += " (Purified)";
				if (!pokemon.getFormSet().isDefaultForm(form))
					pokemonName += " (" + form.getName() + ")";
				DexMove fastMove = entry.getMove(moves.getMove1());
				DexMove chargedMove = entry.getMove(moves.getMove2());
				if (fastMove == null || chargedMove == null)
					embed.appendDesc(String.format("#%d **%s**: ERROR\n", i++, pokemonName));
				else
					embed.appendDesc(String.format("#%d **%s**: %s/%s\n", i++, pokemonName,
							fastMove.toString(entry), chargedMove.toString(entry)));
			}
		}
		embed.appendDesc(
				"\nFor more counters, to modify the search, or check data on other leagues, visit "
						+ "[PokeBattler](https://www.pokebattler.com/pvp/rankings/attackers/leagues/"
						+ targetLeague.getCombatLeagueType()
						+ "/strategies/PVP/PVP?sort=WIN&shieldStrategy=SHIELD_RANDOM&defenderShieldStrategy=SHIELD_RANDOM&meta=SINGLE_MOVE).");

		embed.withFooterText(DexMove.STAB_MARKER.replaceAll("\\\\", "") + " denotes a STAB move, "
				+ DexMove.LEGACY_MARKER.replaceAll("\\\\", "") + " for legacy moves, and "
				+ DexMove.EXCLUSIVE_MARKER.replaceAll("\\\\", "") + " for exclusive moves");
		channel.createEmbed(embed.build()).block();
	}

}
