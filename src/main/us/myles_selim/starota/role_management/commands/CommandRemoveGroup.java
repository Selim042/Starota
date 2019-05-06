package us.myles_selim.starota.role_management.commands;

import java.util.List;

import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.Role;
import discord4j.core.object.entity.TextChannel;
import us.myles_selim.starota.commands.StarotaCommand;
import us.myles_selim.starota.misc.utils.MiscUtils;
import us.myles_selim.starota.role_management.GroupManager;
import us.myles_selim.starota.wrappers.StarotaServer;

public class CommandRemoveGroup extends StarotaCommand {

	public CommandRemoveGroup() {
		super("removeGroup", "Leave the given group.");
	}

	@Override
	public List<String> getAliases() {
		List<String> aliases = super.getAliases();
		aliases.add("leaveGroup");
		return aliases;
	}

	@Override
	public void execute(String[] args, Message message, StarotaServer server, TextChannel channel) {
		if (args.length != 2) {
			channel.createMessage("**Usage**: " + server.getPrefix() + this.getName() + " [group]");
			return;
		}
		Role targetRole = null;
		for (Role role : MiscUtils.getRoleByName(server.getDiscordGuild(), args[1])) {
			if (GroupManager.isGroup(server, role)) {
				targetRole = role;
				break;
			}
		}
		if (targetRole == null) {
			channel.createMessage("Group \"" + args[1] + "\" not found");
			return;
		}
		Member user = message.getAuthor().get().asMember(server.getDiscordGuild().getId()).block();
		final Role fTargetRole = targetRole;
		if (!user.getRoles().any((r) -> r.equals(fTargetRole)).block()) {
			channel.createMessage("You already are part of the group \"" + targetRole.getName() + "\"");
			return;
		} else {
			user.removeRole(targetRole.getId());
			channel.createMessage("You have been removed from \"" + targetRole.getName() + "\"");
			return;
		}
	}

}
