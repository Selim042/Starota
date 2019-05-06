package us.myles_selim.starota.role_management.commands;

import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.Role;
import discord4j.core.object.entity.TextChannel;
import discord4j.core.object.util.Permission;
import discord4j.core.object.util.PermissionSet;
import us.myles_selim.starota.commands.StarotaCommand;
import us.myles_selim.starota.misc.data_types.EmbedBuilder;
import us.myles_selim.starota.role_management.GroupManager;
import us.myles_selim.starota.wrappers.StarotaServer;

public class CommandGetGroups extends StarotaCommand {

	public CommandGetGroups() {
		super("getGroups", "Shows a list of groups.");
	}

	@Override
	public PermissionSet getCommandPermission() {
		return PermissionSet.of(Permission.SEND_MESSAGES, Permission.EMBED_LINKS);
	}

	@Override
	public void execute(String[] args, Message message, StarotaServer server, TextChannel channel) {
		EmbedBuilder builder = new EmbedBuilder();
		builder.setTitle("Available Roles:");
		for (Role role : GroupManager.getGroups(server))
			builder.appendDesc(role.getName() + "\n");
		channel.createEmbed(builder.build());
	}

}
