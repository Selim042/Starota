package us.myles_selim.starota.commands;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.handle.obj.Permissions;
import sx.blah.discord.util.EmbedBuilder;
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.lua.LuaUtils;
import us.myles_selim.starota.wrappers.StarotaServer;

public class CommandStatus extends StarotaCommand {

	public CommandStatus() {
		super("status");
	}

	@Override
	public Permissions requiredPermission() {
		return Permissions.ADMINISTRATOR;
	}

	@Override
	public void execute(String[] args, IMessage message, StarotaServer server, IChannel channel) {
		IUser sender = message.getAuthor();
		if (sender.getPermissionsForGuild(server.getDiscordGuild())
				.contains(Permissions.ADMINISTRATOR)) {
			EmbedBuilder builder = new EmbedBuilder();
			builder.withTitle(Starota.getClient().getOurUser().getDisplayName(server.getDiscordGuild())
					+ " Status");
			builder.appendField("Discord:", Starota.getClient().isReady() ? "Online" : "Offline", true);
			if (Starota.canUseLua(server.getDiscordGuild()))
				builder.appendField("Lua:",
						LuaUtils.isInitialized(server) ? "Initialized" : "Uninitalized", true);
			channel.sendMessage(builder.build());
		}
	}

}
