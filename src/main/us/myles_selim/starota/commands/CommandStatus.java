package us.myles_selim.starota.commands;

import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.MessageChannel;
import discord4j.core.object.util.Permission;
import discord4j.core.object.util.PermissionSet;
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.commands.registry.CommandException;
import us.myles_selim.starota.misc.utils.EmbedBuilder;
import us.myles_selim.starota.wrappers.StarotaServer;

public class CommandStatus extends BotCommand<StarotaServer> {

	public CommandStatus() {
		super("status", "Gets Starota status information.");
	}

	@Override
	public Permission requiredUsePermission() {
		return Permission.ADMINISTRATOR;
	}

	@Override
	public PermissionSet getCommandPermission() {
		return PermissionSet.of(Permission.SEND_MESSAGES, Permission.EMBED_LINKS);
	}

	@Override
	public void execute(String[] args, Message message, StarotaServer server, MessageChannel channel)
			throws CommandException {
		Member sender = message.getAuthorAsMember().block();
		if (sender.getBasePermissions().block().contains(Permission.ADMINISTRATOR)) {
			EmbedBuilder builder = new EmbedBuilder();
			builder.withTitle(
					Starota.getOurUserAsMember(server.getDiscordGuild()).getDisplayName() + " Status");
			builder.appendField("Discord:", Starota.getClient().isConnected() ? "Online" : "Offline",
					true);
			// if (RolePermHelper.canUseLua(server.getDiscordGuild()))
			// builder.appendField("Lua:",
			// LuaUtils.isInitialized(server) ? "Initialized" : "Uninitalized",
			// true);
			channel.createEmbed(builder.build()).block();
		}
	}

}
