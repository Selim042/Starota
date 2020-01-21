package us.myles_selim.starota.commands;

import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.MessageChannel;
import us.myles_selim.starota.commands.registry.CommandException;
import us.myles_selim.starota.enums.EnumPokemon;
import us.myles_selim.starota.forms.Form;
import us.myles_selim.starota.forms.FormSet;
import us.myles_selim.starota.misc.utils.EmbedBuilder;
import us.myles_selim.starota.misc.utils.EmojiServerHelper;
import us.myles_selim.starota.misc.utils.MiscUtils;
import us.myles_selim.starota.wrappers.StarotaServer;

public class CommandRegionals extends BotCommand<StarotaServer> {

	public CommandRegionals() {
		super("regionals", "Lists all regional Pokemon and where they can be found.");
	}

	@Override
	public void execute(String[] args, Message message, StarotaServer server, MessageChannel channel)
			throws CommandException {
		EmbedBuilder embed = new EmbedBuilder().withTitle("Regional Pokemon:");
		StringBuilder desc = new StringBuilder();
		int gen = 0;
		for (EnumPokemon pokemon : EnumPokemon.values()) {
			if (pokemon.getData().isRegional()) {
				if (pokemon.getData().getGeneration() != gen) {
					if (desc.length() > 0) {
						embed.appendField(String.format("**Generation %d**:\n", gen), desc.toString(),
								false);
						desc = new StringBuilder();
					}
					gen = pokemon.getData().getGeneration();
				}
				FormSet formSet = pokemon.getFormSet();
				Form defaultForm = formSet.getDefaultForm();
				String emoji = defaultForm.getEmojiDisplay(pokemon) + (defaultForm.isShinyable()
						? MiscUtils.getEmojiDisplay(EmojiServerHelper.getGuildEmoji("shiny"))
						: "");
				desc.append(String.format("- %s: %s %s\n", pokemon.getData().getName(),
						pokemon.getData().getRegion(), emoji));
			}
		}
		channel.createEmbed(embed.build()).block();
	}

}
