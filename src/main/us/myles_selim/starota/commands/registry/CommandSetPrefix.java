package us.myles_selim.starota.commands.registry;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.Permissions;
import us.myles_selim.starota.commands.registry.java.JavaCommand;

public class CommandSetPrefix extends JavaCommand {

	public CommandSetPrefix() {
		super("setPrefix", "Sets the command prefix.");
	}

	@Override
	public Permissions requiredUsePermission() {
		return Permissions.ADMINISTRATOR;
	}

	@Override
	public void execute(String[] args, IMessage message, IGuild guild, IChannel channel) {
		if (args.length < 2) {
			channel.sendMessage("**Usage**: " + PrimaryCommandHandler.getPrefix(guild) + this.getName()
					+ " <prefix>");
			return;
		}
		String newPrefix = args[1];
		if (newPrefix.length() > 2) {
			channel.sendMessage("Prefix \"" + newPrefix + "\" cannot be longer than 2 characters");
			return;
		}
		String oldPrefix = PrimaryCommandHandler.getPrefix(guild);
		PrimaryCommandHandler.setPrefix(guild, newPrefix);
		channel.sendMessage(
				"Changed command prefix from \"" + oldPrefix + "\" to \"" + newPrefix + "\"");
	}

}
