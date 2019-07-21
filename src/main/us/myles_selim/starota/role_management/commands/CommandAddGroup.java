package us.myles_selim.starota.role_management.commands;

import java.util.List;

import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.MessageChannel;
import discord4j.core.object.entity.Role;
import us.myles_selim.starota.commands.BotCommand;
import us.myles_selim.starota.commands.registry.CommandException;
import us.myles_selim.starota.misc.utils.MiscUtils;
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
	public void execute(String[] args, Message message, StarotaServer server, MessageChannel channel)
			throws CommandException {
		if (args.length != 2) {
			channel.createMessage("**Usage**: " + server.getPrefix() + this.getName() + " [group]")
					.block();
			return;
		}
		Role targetRole = null;
		for (Role role : MiscUtils.getRolesByName(server.getDiscordGuild(), args[1], false)) {
			if (GroupManager.isGroup(server, role)) {
				targetRole = role;
				break;
			}
		}
		if (targetRole == null) {
			channel.createMessage("Group \"" + args[1] + "\" not found").block();
			return;
		}
		Member user = message.getAuthorAsMember().block();
		if (user.getRoleIds().contains(targetRole.getId())) {
			channel.createMessage("You already are part of the group \"" + targetRole.getName() + "\"")
					.block();
			return;
		} else {
			user.addRole(targetRole.getId()).block();
			channel.createMessage("You have been added to \"" + targetRole.getName() + "\"").block();
			return;
		}
	}

}
