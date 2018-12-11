package us.myles_selim.starota.profiles.commands;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.commands.registry.PrimaryCommandHandler;
import us.myles_selim.starota.commands.registry.java.JavaCommand;
import us.myles_selim.starota.profiles.PlayerProfile;
import us.myles_selim.starota.profiles.ProfileManager;

public class CommandProfile extends JavaCommand {

	public CommandProfile() {
		super("profile", "Views a user's profile.");
	}

	@Override
	public String getGeneralUsage() {
		return "<target>";
	}

	@Override
	public void execute(String[] args, IMessage message, IGuild guild, IChannel channel) {
		IUser target;
		if (args.length == 1)
			target = message.getAuthor();
		else {
			if (args.length != 2) {
				channel.sendMessage("**Usage**: " + PrimaryCommandHandler.getPrefix(guild)
						+ this.getName() + " <target>");
				return;
			}
			target = Starota.findUser(guild.getLongID(), args[1]);
			if (target == null) {
				PlayerProfile profile = ProfileManager.getProfile(guild, args[1]);
				if (profile == null)
					channel.sendMessage("User \"" + args[1] + "\" not found");
				else {
					channel.sendMessage(profile.toEmbed(guild));
					return;
				}
				return;
			}
		}
		if (ProfileManager.hasProfile(guild, target)) {
			channel.sendMessage(ProfileManager.getProfile(guild, target).toEmbed(guild));
			return;
		}
		channel.sendMessage("User " + target.getName() + " does not have a profile");
		return;
	}

}
