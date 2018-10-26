package us.myles_selim.starota.commands;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.Permissions;
import us.myles_selim.starota.ServerOptions;
import us.myles_selim.starota.commands.registry.Command;
import us.myles_selim.starota.commands.registry.CommandRegistry;

public class CommandSetPrefix extends Command {

	public CommandSetPrefix() {
		super("setPrefix", "Sets the command prefix.");
	}

	@Override
	public Permissions requiredPermission() {
		return Permissions.ADMINISTRATOR;
	}

	@Override
	public void execute(String[] args, IMessage message, IGuild guild, IChannel channel) {
		if (args.length < 2) {
			channel.sendMessage(
					"**Usage**: " + CommandRegistry.getPrefix(guild) + this.getName() + " <prefix>");
			return;
		}
		String newPrefix = args[1];
		if (newPrefix.length() > 2) {
			channel.sendMessage("Prefix \"" + newPrefix + "\" cannot be longer than 2 characters");
			return;
		}
		String oldPrefix = CommandRegistry.getPrefix(guild);
		ServerOptions.setValue(guild, CommandRegistry.PREFIX_KEY, newPrefix);
		channel.sendMessage(
				"Changed command prefix from \"" + oldPrefix + "\" to \"" + newPrefix + "\"");
	}

}
