package us.myles_selim.starota.role_management.commands;

import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.MessageChannel;
import discord4j.core.object.entity.Role;
import discord4j.core.object.util.Permission;
import discord4j.core.object.util.PermissionSet;
import us.myles_selim.starota.commands.BotCommand;
import us.myles_selim.starota.commands.registry.CommandException;
import us.myles_selim.starota.misc.utils.EmbedBuilder;
import us.myles_selim.starota.role_management.GroupManager;
import us.myles_selim.starota.wrappers.StarotaServer;

public class CommandGetGroups extends BotCommand<StarotaServer> {

	public CommandGetGroups() {
		super("getGroups", "Shows a list of groups.");
	}

	@Override
	public PermissionSet getCommandPermission() {
		return PermissionSet.of(Permission.SEND_MESSAGES, Permission.EMBED_LINKS);
	}

	@Override
	public void execute(String[] args, Message message, StarotaServer server, MessageChannel channel)
			throws CommandException {
		EmbedBuilder builder = new EmbedBuilder();
		builder.withTitle("Available Roles:");
		for (Role role : GroupManager.getGroups(server))
			builder.appendDesc(role.getName() + "\n");
		channel.createEmbed(builder.build()).block();
	}

}
