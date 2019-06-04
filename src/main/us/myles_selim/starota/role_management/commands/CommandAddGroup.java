package us.myles_selim.starota.role_management.commands;

import java.util.List;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IRole;
import sx.blah.discord.handle.obj.IUser;
import us.myles_selim.starota.commands.BotCommand;
import us.myles_selim.starota.role_management.GroupManager;
import us.myles_selim.starota.wrappers.StarotaServer;

public class CommandAddGroup extends BotCommand<StarotaServer> {

	public CommandAddGroup() {
		super("addGroup", "Join the given group.");
	}

	@Override
	public List<String> getAliases() {
		List<String> aliases = super.getAliases();
		aliases.add("joinGroup");
		return aliases;
	}

	@Override
	public void execute(String[] args, IMessage message, StarotaServer server, IChannel channel) {
		if (args.length != 2) {
			channel.sendMessage("**Usage**: " + server.getPrefix() + this.getName() + " [group]");
			return;
		}
		IRole targetRole = null;
		for (IRole role : server.getDiscordGuild().getRolesByName(args[1])) {
			if (GroupManager.isGroup(server, role)) {
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
