package us.myles_selim.starota.commands;

import java.util.EnumSet;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.handle.obj.Permissions;
import sx.blah.discord.util.EmbedBuilder;
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.lua.LuaUtils;
import us.myles_selim.starota.misc.utils.RolePermHelper;
import us.myles_selim.starota.wrappers.StarotaServer;

public class CommandStatus extends BotCommand<StarotaServer> {

	public CommandStatus() {
		super("status", "Gets Starota status information.");
	}

	@Override
	public Permissions requiredUsePermission() {
		return Permissions.ADMINISTRATOR;
	}

	@Override
	public EnumSet<Permissions> getCommandPermissions() {
		return EnumSet.of(Permissions.SEND_MESSAGES, Permissions.EMBED_LINKS);
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
			if (RolePermHelper.canUseLua(server.getDiscordGuild()))
				builder.appendField("Lua:",
						LuaUtils.isInitialized(server) ? "Initialized" : "Uninitalized", true);
			channel.sendMessage(builder.build());
		}
	}

}
