package us.myles_selim.starota.assistants;

import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.MessageChannel;
import us.myles_selim.starota.commands.registry.CommandException;
import us.myles_selim.starota.commands.registry.java.JavaCommand;
import us.myles_selim.starota.misc.utils.EmbedBuilder;

public class CommandBots extends JavaCommand {

	public CommandBots() {
		super("bots", "Displays information on other bots by Selim.");
	}

	@Override
	public void execute(String[] args, Message message, Guild guild, MessageChannel channel)
			throws CommandException {
		EmbedBuilder builder = new EmbedBuilder();
		builder.withTitle("Other bots:");

		builder.appendDesc(
				"\n**Starota**: Provides misc Pokemon Go utilities including, but not limited to:\n");
		builder.appendDesc(
				" - Player profiles\n - Role management\n - Custom scripting\n - Tradeboards\n - Leaderboards\n");
		builder.appendDesc("[DiscordBots.org](https://discordbots.org/bot/489245655710040099)\n");

		builder.appendDesc("\n**Pokedex**: Just the Pokedex command from Starota.\n");
		builder.appendDesc("[DiscordBots.org](https://discordbots.org/bot/541079916234407967)\n");

		builder.appendDesc("\n**Registration Bot**: Registers Starota players profiles using OCR.\n");
		builder.appendDesc("[DiscordBots.org](https://discordbots.org/bot/578326030603780126)\n");

		builder.appendDesc("\n**Point Bot (WIP)**: Quest-based bot for some friendly competition.\n");
		channel.createEmbed(builder.build()).block();
	}

}
