package us.myles_selim.starota.profiles.commands;

import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.MessageChannel;
import discord4j.core.object.util.Permission;
import discord4j.core.object.util.PermissionSet;
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.commands.BotCommand;
import us.myles_selim.starota.commands.registry.CommandException;
import us.myles_selim.starota.profiles.PlayerProfile;
import us.myles_selim.starota.wrappers.StarotaServer;

public class CommandProfile extends BotCommand<StarotaServer> {

	public CommandProfile() {
		super("profile", "Views a user's profile.");
	}

	@Override
	public PermissionSet getCommandPermission() {
		return PermissionSet.of(Permission.SEND_MESSAGES, Permission.EMBED_LINKS);
	}

	@Override
	public String getGeneralUsage() {
		return "<target>";
	}

	@Override
	public void execute(String[] args, Message message, StarotaServer server, MessageChannel channel)
			throws CommandException {
		Member target;
		if (args.length == 1)
			target = message.getAuthorAsMember().block();
		else {
			if (args.length != 2) {
				channel.createMessage("**Usage**: " + server.getPrefix() + this.getName() + " <target>");
				return;
			}
			target = Starota.findMember(server.getDiscordGuild(), args[1]);
			if (target == null) {
				PlayerProfile profile = server.getProfile(args[1]);
				if (profile == null)
					channel.createMessage("User \"" + args[1] + "\" not found").block();
				else {
					channel.createEmbed(profile.toEmbed(server)).block();
					return;
				}
				return;
			}
		}
		if (server.hasProfile(target)) {
			channel.createEmbed(server.getProfile(target).toEmbed(server)).block();
			return;
		}
		channel.createMessage("User " + target.getUsername() + " does not have a profile").block();
	}

}
