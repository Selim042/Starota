package us.myles_selim.starota.role_management.commands;

import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.Role;
import discord4j.core.object.entity.TextChannel;
import discord4j.core.object.util.Permission;
import us.myles_selim.starota.commands.StarotaCommand;
import us.myles_selim.starota.misc.utils.MiscUtils;
import us.myles_selim.starota.role_management.GroupManager;
import us.myles_selim.starota.wrappers.StarotaServer;

public class CommandSetAsGroup extends StarotaCommand {

	public CommandSetAsGroup() {
		super("setAsGroup", "Sets the target role to a group.");
	}

	@Override
	public Permission requiredUsePermission() {
		return Permission.ADMINISTRATOR;
	}

	@Override
	public void execute(String[] args, Message message, StarotaServer server, TextChannel channel) {
		if (args.length < 3) {
			channel.createMessage(
					"**Usage**: " + server.getPrefix() + this.getName() + " [group] [isGroup]");
			return;
		}
		Role targetRole = null;
		for (Role role : MiscUtils.getRoleByName(server.getDiscordGuild(), args[1])) {
			targetRole = role;
			break;
		}
		if (targetRole == null) {
			channel.createMessage("Role \"" + args[1] + "\" not found");
			return;
		}
		boolean isGroup = args[2].equalsIgnoreCase("true");
		GroupManager.setAsGroup(server, targetRole, isGroup);
		if (isGroup)
			channel.createMessage("Role \"" + targetRole.getName() + "\" is now an assignable group.");
		else
			channel.createMessage(
					"Role \"" + targetRole.getName() + "\" is no longer an assignable group.");
	}

}
