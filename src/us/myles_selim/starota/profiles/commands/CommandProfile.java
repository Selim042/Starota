package us.myles_selim.starota.profiles.commands;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.commands.StarotaCommand;
import us.myles_selim.starota.profiles.PlayerProfile;
import us.myles_selim.starota.wrappers.StarotaServer;

public class CommandProfile extends StarotaCommand {

	public CommandProfile() {
		super("profile", "Views a user's profile.");
	}

	@Override
	public String getGeneralUsage() {
		return "<target>";
	}

	@Override
	public void execute(String[] args, IMessage message, StarotaServer server, IChannel channel) {
		IUser target;
		if (args.length == 1)
			target = message.getAuthor();
		else {
			if (args.length != 2) {
				channel.sendMessage("**Usage**: " + server.getPrefix() + this.getName() + " <target>");
				return;
			}
			target = Starota.findUser(server.getDiscordGuild().getLongID(), args[1]);
			if (target == null) {
				PlayerProfile profile = server.getProfile(args[1]);
				if (profile == null)
					channel.sendMessage("User \"" + args[1] + "\" not found");
				else {
					channel.sendMessage(profile.toEmbed(server));
					return;
				}
				return;
			}
		}
		if (server.hasProfile(target)) {
			channel.sendMessage(server.getProfile(target).toEmbed(server));
			return;
		}
		channel.sendMessage("User " + target.getName() + " does not have a profile");
		return;
	}

}
