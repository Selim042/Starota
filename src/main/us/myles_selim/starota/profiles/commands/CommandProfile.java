package us.myles_selim.starota.profiles.commands;

import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.TextChannel;
import discord4j.core.object.entity.User;
import discord4j.core.object.util.Permission;
import discord4j.core.object.util.PermissionSet;
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.commands.StarotaCommand;
import us.myles_selim.starota.profiles.PlayerProfile;
import us.myles_selim.starota.wrappers.StarotaServer;

public class CommandProfile extends StarotaCommand {

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
	public void execute(String[] args, Message message, StarotaServer server, TextChannel channel) {
		User target;
		if (args.length == 1)
			target = message.getAuthor().get();
		else {
			if (args.length != 2) {
				channel.createMessage("**Usage**: " + server.getPrefix() + this.getName() + " <target>");
				return;
			}
			target = Starota.findUser(server.getDiscordGuild().getId().asLong(), args[1]);
			if (target == null) {
				PlayerProfile profile = server.getProfile(args[1]);
				if (profile == null)
					channel.createMessage("User \"" + args[1] + "\" not found");
				else {
					channel.createEmbed(profile.toEmbed(server));
					return;
				}
				return;
			}
		}
		if (server.hasProfile(target)) {
			channel.createEmbed(server.getProfile(target).toEmbed(server));
			return;
		}
		channel.createMessage("User " + target.getUsername() + " does not have a profile");
	}

}
