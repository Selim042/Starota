package us.myles_selim.starota.trading.commands;

import java.util.LinkedList;
import java.util.List;

import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.TextChannel;
import discord4j.core.object.util.Permission;
import discord4j.core.object.util.PermissionSet;
import us.myles_selim.starota.commands.StarotaCommand;
import us.myles_selim.starota.enums.EnumPokemon;
import us.myles_selim.starota.misc.data_types.EmbedBuilder;
import us.myles_selim.starota.trading.forms.FormSet.Form;
import us.myles_selim.starota.wrappers.StarotaServer;

public class CommandGetShinies extends StarotaCommand {

	public CommandGetShinies() {
		super("getShinies");
	}

	@Override
	public PermissionSet getCommandPermission() {
		return PermissionSet.of(Permission.SEND_MESSAGES, Permission.EMBED_LINKS);
	}

	@Override
	public void execute(String[] args, Message message, StarotaServer server, TextChannel channel) {
		if (args.length != 1) {
			channel.createMessage("**Usage**: " + server.getPrefix() + this.getName());
			return;
		}
		int numForms = 0;
		List<String> lines = new LinkedList<>();
		for (EnumPokemon pokemon : EnumPokemon.values()) {
			boolean shinyable = pokemon.isShinyable();
			if (!shinyable)
				continue;
			String shinyText = "shiny";
			if (pokemon.getFormSet() == null) {
				switch (pokemon.getGenderPossible()) {
				case EITHER:
					lines.add(pokemon + ": ♂/♀, " + shinyText + "\n");
					numForms += 2;
					break;
				case FEMALE:
					lines.add(pokemon + ": ♀, " + shinyText + "\n");
					numForms++;
					break;
				case MALE:
					lines.add(pokemon + ": ♂, " + shinyText + "\n");
					numForms++;
					break;
				case UNKNOWN:
					lines.add(pokemon + ": Genderless, " + shinyText + "\n");
					numForms++;
					break;
				}
			} else {
				for (Form f : pokemon.getFormSet().getForms()) {
					switch (pokemon.getGenderPossible()) {
					case EITHER:
						if (!f.canBeShiny(pokemon))
							continue;
						lines.add(pokemon + ": __" + f + "__: ♂/♀, " + shinyText + "\n");
						numForms += 2;
						break;
					case FEMALE:
						if (!f.canBeShiny(pokemon))
							continue;
						lines.add(pokemon + ": __" + f + "__: ♀, " + shinyText + "\n");
						numForms++;
						break;
					case MALE:
						if (!f.canBeShiny(pokemon))
							continue;
						lines.add(pokemon + ": __" + f + "__: ♂, " + shinyText + "\n");
						numForms++;
						break;
					case UNKNOWN:
						if (!f.canBeShiny(pokemon))
							continue;
						lines.add(pokemon + ": __" + f + "__: Genderless, " + shinyText + "\n");
						numForms++;
						break;
					}
				}
			}
		}
		List<String> groups = new LinkedList<>();
		String line = "There are " + numForms + " available shiny form" + (numForms == 1 ? "" : "s")
				+ ":\n";
		for (String l : lines) {
			if (line.length() + l.length() > 2048) {
				groups.add(line);
				line = l;
			} else
				line += l;
		}
		groups.add(line);
		for (String g : groups) {
			EmbedBuilder builder = new EmbedBuilder();
			builder.appendDesc(g);
			channel.createEmbed(builder.build());
			try {
				Thread.sleep(2500);
			} catch (InterruptedException e) {}
		}
	}

}
