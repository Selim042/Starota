package us.myles_selim.starota.trading.commands;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.EmbedBuilder;
import us.myles_selim.starota.commands.StarotaCommand;
import us.myles_selim.starota.trading.EnumPokemon;
import us.myles_selim.starota.trading.FormManager;
import us.myles_selim.starota.trading.forms.FormSet.Form;
import us.myles_selim.starota.wrappers.StarotaServer;

public class CommandGetForms extends StarotaCommand {

	public CommandGetForms() {
		super("getForms");
	}

	@Override
	public void execute(String[] args, IMessage message, StarotaServer server, IChannel channel) {
		if (args.length != 2) {
			channel.sendMessage("**Usage**: " + server.getPrefix() + this.getName() + " [Pokemon]");
			return;
		}
		EnumPokemon pokemon = EnumPokemon.getPokemon(args[1]);
		if (pokemon == null) {
			channel.sendMessage("Pokemon \"" + args[1] + "\" cannot be found");
			return;
		}
		if (!FormManager.isAvailable(pokemon)) {
			channel.sendMessage("Pokemon \"" + pokemon + "\" is not yet available");
			return;
		}
		EmbedBuilder builder = new EmbedBuilder();
		int numForms = 0;
		String text = "";
		boolean shinyable = FormManager.isShinyable(pokemon.getId());
		String shinyText = "shiny";
		if (pokemon.getFormSet() == null) {
			switch (pokemon.getGenderPossible()) {
			case EITHER:
				text += "♂/♀\n";
				numForms += 2;
				if (shinyable) {
					text += "♂/♀, " + shinyText + "\n";
					numForms += 2;
				}
				break;
			case FEMALE:
				text += "♀\n";
				numForms++;
				if (shinyable) {
					text += "♀, " + shinyText + "\n";
					numForms++;
				}
				break;
			case MALE:
				text += "♂\n";
				numForms++;
				if (shinyable) {
					text += "♂, " + shinyText + "\n";
					numForms++;
				}
				break;
			case UNKNOWN:
				text += "Genderless\n";
				numForms++;
				if (shinyable) {
					text += "Genderless, " + shinyText + "\n";
					numForms++;
				}
				break;
			}
		} else {
			for (Form f : pokemon.getFormSet().getForms()) {
				switch (pokemon.getGenderPossible()) {
				case EITHER:
					text += "__" + f + "__" + ": ♂/♀\n";
					numForms += 2;
					if (f.canBeShiny(pokemon)) {
						text += "__" + f + "__" + ": ♂/♀, " + shinyText + "\n";
						numForms += 2;
					}
					break;
				case FEMALE:
					text += "__" + f + "__" + ": ♀\n";
					numForms++;
					if (f.canBeShiny(pokemon)) {
						text += "__" + f + "__" + ": ♀, " + shinyText + "\n";
						numForms++;
					}
					break;
				case MALE:
					text += "__" + f + "__" + ": ♂\n";
					numForms++;
					if (f.canBeShiny(pokemon)) {
						text += "__" + f + "__" + ": ♂, " + shinyText + "\n";
						numForms++;
					}
					break;
				case UNKNOWN:
					text += "__" + f + "__" + ": Genderless\n";
					numForms++;
					if (f.canBeShiny(pokemon)) {
						text += "__" + f + "__" + ": Genderless, " + shinyText + "\n";
						numForms++;
					}
					break;
				}
			}
		}
		builder.appendDesc("**" + pokemon.getName() + "** has " + numForms + " available form"
				+ (numForms == 1 ? "" : "s") + ":\n" + text);
		channel.sendMessage(builder.build());
	}

}
