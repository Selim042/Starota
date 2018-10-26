package us.myles_selim.starota.role_management.commands;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IRole;
import sx.blah.discord.handle.obj.IUser;
import us.myles_selim.starota.commands.registry.Command;
import us.myles_selim.starota.commands.registry.CommandRegistry;
import us.myles_selim.starota.role_management.EnumGroup;

public class CommandAddGroup extends Command {

	public CommandAddGroup() {
		super("addGroup", "Join the given group.");
	}

	@Override
	public void execute(String[] args, IMessage message, IGuild guild, IChannel channel) {
		if (args.length != 2) {
			channel.sendMessage(
					"**Usage**: " + CommandRegistry.getPrefix(guild) + this.getName() + " [group]");
			return;
		}
		IRole targetRole = null;
		for (IRole role : guild.getRolesByName(args[1])) {
			if (EnumGroup.isIncluded(role)) {
				targetRole = role;
				break;
			}
		}
		if (targetRole == null) {
			channel.sendMessage("Group \"" + args[1] + "\" not found");
			return;
		}
		IUser user = message.getAuthor();
		if (user.hasRole(targetRole)) {
			channel.sendMessage("You already are part of the group \"" + targetRole.getName() + "\"");
			return;
		} else {
			user.addRole(targetRole);
			channel.sendMessage("You have been added to \"" + targetRole.getName() + "\"");
			return;
		}
	}

}
