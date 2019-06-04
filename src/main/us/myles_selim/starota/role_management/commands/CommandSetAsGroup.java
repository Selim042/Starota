package us.myles_selim.starota.role_management.commands;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IRole;
import sx.blah.discord.handle.obj.Permissions;
import us.myles_selim.starota.commands.BotCommand;
import us.myles_selim.starota.role_management.GroupManager;
import us.myles_selim.starota.wrappers.StarotaServer;

public class CommandSetAsGroup extends BotCommand<StarotaServer> {

	public CommandSetAsGroup() {
		super("setAsGroup", "Sets the target role to a group.");
	}

	@Override
	public Permissions requiredUsePermission() {
		return Permissions.ADMINISTRATOR;
	}

	@Override
	public void execute(String[] args, IMessage message, StarotaServer server, IChannel channel) {
		if (args.length < 3) {
			channel.sendMessage(
					"**Usage**: " + server.getPrefix() + this.getName() + " [group] [isGroup]");
			return;
		}
		IRole targetRole = null;
		for (IRole role : server.getDiscordGuild().getRolesByName(args[1])) {
			targetRole = role;
			break;
		}
		if (targetRole == null) {
			channel.sendMessage("Role \"" + args[1] + "\" not found");
			return;
		}
		boolean isGroup = args[2].equalsIgnoreCase("true");
		GroupManager.setAsGroup(server, targetRole, isGroup);
		if (isGroup)
			channel.sendMessage("Role \"" + targetRole.getName() + "\" is now an assignable group.");
		else
			channel.sendMessage(
					"Role \"" + targetRole.getName() + "\" is no longer an assignable group.");
	}

}
