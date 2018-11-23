package us.myles_selim.starota.role_management.commands;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IRole;
import sx.blah.discord.handle.obj.Permissions;
import us.myles_selim.starota.commands.registry.PrimaryCommandHandler;
import us.myles_selim.starota.commands.registry.java.JavaCommand;
import us.myles_selim.starota.role_management.GroupManager;

public class CommandSetAsGroup extends JavaCommand {

	public CommandSetAsGroup() {
		super("setAsGroup", "Sets if the target role is a group.");
	}

	@Override
	public Permissions requiredPermission() {
		return Permissions.ADMINISTRATOR;
	}

	@Override
	public void execute(String[] args, IMessage message, IGuild guild, IChannel channel) {
		if (args.length < 3) {
			channel.sendMessage("**Usage**: " + PrimaryCommandHandler.getPrefix(guild) + this.getName()
					+ " [group] [isGroup]");
			return;
		}
		IRole targetRole = null;
		for (IRole role : guild.getRolesByName(args[1])) {
			targetRole = role;
			break;
		}
		if (targetRole == null) {
			channel.sendMessage("Role \"" + args[1] + "\" not found");
			return;
		}
		boolean isGroup = args[2].equalsIgnoreCase("true");
		GroupManager.setAsGroup(guild, targetRole, isGroup);
		if (isGroup)
			channel.sendMessage("Role \"" + targetRole.getName() + "\" is now an assignable group.");
		else
			channel.sendMessage(
					"Role \"" + targetRole.getName() + "\" is no longer an assignable group.");
	}

}
