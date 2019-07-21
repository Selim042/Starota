package us.myles_selim.starota.commands;

import java.util.List;

import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.MessageChannel;
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.commands.registry.CommandException;
import us.myles_selim.starota.commands.registry.java.JavaCommand;

public class CommandChangelog extends JavaCommand {

	public CommandChangelog() {
		super("changelog");
	}

	@Override
	public List<String> getAliases() {
		List<String> aliases = super.getAliases();
		aliases.add("changes");
		aliases.add("whatsnew");
		return aliases;
	}

	@Override
	public void execute(String[] args, Message message, Guild guild, MessageChannel channel)
			throws CommandException {
		channel.createMessage("```" + Starota.CHANGELOG + "```").block();
	}

}
