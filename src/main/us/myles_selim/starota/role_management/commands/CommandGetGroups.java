package us.myles_selim.starota.role_management.commands;

import java.util.EnumSet;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IRole;
import sx.blah.discord.handle.obj.Permissions;
import sx.blah.discord.util.EmbedBuilder;
import us.myles_selim.starota.commands.StarotaCommand;
import us.myles_selim.starota.role_management.GroupManager;
import us.myles_selim.starota.wrappers.StarotaServer;

public class CommandGetGroups extends StarotaCommand {

	public CommandGetGroups() {
		super("getGroups", "Shows a list of groups.");
	}

	@Override
	public EnumSet<Permissions> getCommandPermissions() {
		return EnumSet.of(Permissions.SEND_MESSAGES, Permissions.EMBED_LINKS);
	}

	@Override
	public void execute(String[] args, IMessage message, StarotaServer server, IChannel channel) {
		EmbedBuilder builder = new EmbedBuilder();
		builder.withTitle("Available Roles:");
		for (IRole role : GroupManager.getGroups(server))
			builder.appendDesc(role.getName() + "\n");
		channel.sendMessage(builder.build());
	}

}
