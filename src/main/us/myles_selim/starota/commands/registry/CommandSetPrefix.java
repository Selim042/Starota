package us.myles_selim.starota.commands.registry;

import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.MessageChannel;
import discord4j.core.object.util.Permission;
import us.myles_selim.starota.commands.registry.java.JavaCommand;

public class CommandSetPrefix extends JavaCommand {

	public CommandSetPrefix() {
		super("setPrefix", "Sets the command prefix.");
	}

	@Override
	public Permission requiredUsePermission() {
		return Permission.ADMINISTRATOR;
	}

	@Override
	public void execute(String[] args, Message message, Guild guild, MessageChannel channel) {
		if (args.length < 2) {
			channel.createMessage("**Usage**: " + PrimaryCommandHandler.getPrefix(guild) + this.getName()
					+ " <prefix>");
			return;
		}
		String newPrefix = args[1];
		if (newPrefix.length() > 2) {
			channel.createMessage("Prefix \"" + newPrefix + "\" cannot be longer than 2 characters");
			return;
		}
		String oldPrefix = PrimaryCommandHandler.getPrefix(guild);
		PrimaryCommandHandler.setPrefix(guild, newPrefix);
		channel.createMessage(
				"Changed command prefix from \"" + oldPrefix + "\" to \"" + newPrefix + "\"");
	}

}
