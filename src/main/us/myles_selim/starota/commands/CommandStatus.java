package us.myles_selim.starota.commands;

import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.TextChannel;
import discord4j.core.object.entity.User;
import discord4j.core.object.util.Permission;
import discord4j.core.object.util.PermissionSet;
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.lua.LuaUtils;
import us.myles_selim.starota.misc.utils.RolePermHelper;
import us.myles_selim.starota.wrappers.StarotaServer;

public class CommandStatus extends StarotaCommand {

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
	public void execute(String[] args, Message message, StarotaServer server, TextChannel channel) {
		User sender = message.getAuthor().get();
		if (sender.asMember(server.getDiscordGuild().getId()).block().getBasePermissions().block()
				.contains(Permission.ADMINISTRATOR)) {
			channel.createEmbed((e) -> {
				e.setTitle(
						Starota.getClient().getSelf().block().asMember(server.getDiscordGuild().getId())
								.block().getDisplayName() + " Status");
				e.addField("Discord:", Starota.getClient().isConnected() ? "Online" : "Offline", true);
				if (RolePermHelper.canUseLua(server.getDiscordGuild()))
					e.addField("Lua:", LuaUtils.isInitialized(server) ? "Initialized" : "Uninitalized",
							true);
			});
		}
	}

}
