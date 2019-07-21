package us.myles_selim.starota.role_management.commands;

import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.MessageChannel;
import discord4j.core.object.entity.Role;
import discord4j.core.object.util.Permission;
import us.myles_selim.starota.commands.BotCommand;
import us.myles_selim.starota.commands.registry.CommandException;
import us.myles_selim.starota.misc.utils.MiscUtils;
import us.myles_selim.starota.role_management.GroupManager;
import us.myles_selim.starota.wrappers.StarotaServer;

public class CommandSetAsGroup extends BotCommand<StarotaServer> {

	public CommandSetAsGroup() {
		super("setAsGroup", "Sets the target role to a group.");
	}

	@Override
	public Permission requiredUsePermission() {
		return Permission.ADMINISTRATOR;
	}

	@Override
	public void execute(String[] args, Message message, StarotaServer server, MessageChannel channel)
			throws CommandException {
		if (args.length < 3) {
			channel.createMessage(
					"**Usage**: " + server.getPrefix() + this.getName() + " [group] [isGroup]").block();
			return;
		}
		Role targetRole = null;
		for (Role role : MiscUtils.getRolesByName(server.getDiscordGuild(), args[1], false)) {
			targetRole = role;
			break;
		}
		if (targetRole == null) {
			channel.createMessage("Role \"" + args[1] + "\" not found").block();
			return;
		}
		boolean isGroup = args[2].equalsIgnoreCase("true");
		GroupManager.setAsGroup(server, targetRole, isGroup);
		if (isGroup)
			channel.createMessage("Role \"" + targetRole.getName() + "\" is now an assignable group.")
					.block();
		else
			channel.createMessage(
					"Role \"" + targetRole.getName() + "\" is no longer an assignable group.").block();
	}

}
