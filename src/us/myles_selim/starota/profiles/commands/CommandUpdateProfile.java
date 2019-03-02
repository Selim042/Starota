package us.myles_selim.starota.profiles.commands;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.handle.obj.Permissions;
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.commands.StarotaCommand;
import us.myles_selim.starota.enums.EnumTeam;
import us.myles_selim.starota.profiles.PlayerProfile;
import us.myles_selim.starota.wrappers.StarotaServer;

public class CommandUpdateProfile extends StarotaCommand {

	public CommandUpdateProfile() {
		super("updateProfile", "Update parts of your profile.");
	}

	@Override
	public String getGeneralUsage() {
		return "[level/trainerCode/realName/alt] [value]";
	}

	@Override
	public String getAdminUsage() {
		return "[level/trainerCode/realName/alt/username/team] [value] <target>";
	}

	@Override
	public void execute(String[] args, IMessage message, StarotaServer server, IChannel channel) {
		boolean isAdmin = channel.getModifiedPermissions(message.getAuthor())
				.contains(Permissions.ADMINISTRATOR);
		IUser target = message.getAuthor();
		if (args.length > 3)
			target = Starota.findUser(args[3]);
		if (target == null && isAdmin) {
			channel.sendMessage("User " + args[3] + " not found.");
			return;
		}
		if (!isAdmin && !target.equals(message.getAuthor())) {
			channel.sendMessage("Only admins can change other user's profile information.");
			return;
		}
		if (!server.hasProfile(target)) {
			if (target.equals(message.getAuthor()))
				channel.sendMessage("You do not yet have a profile.  Please contact an admin of \""
						+ server.getDiscordGuild().getName() + "\".");
			else
				channel.sendMessage(
						target + " does not yet have a profile.  Please contact an admin of \""
								+ server.getDiscordGuild().getName() + "\".");
			return;
		}
		if (args.length < 3) {
			if (isAdmin)
				channel.sendMessage("**Usage**: " + server.getPrefix() + this.getName()
						+ " [level/trainerCode/realName/alt] [value] <target>");
			else
				this.sendUsage(server.getPrefix(), channel);
			return;
		}
		PlayerProfile profile = server.getProfile(target);
		boolean executed = false;
		switch (args[1].toLowerCase()) {
		case "level":
		case "l":
			int level;
			try {
				level = Integer.parseInt(args[2]);
			} catch (NumberFormatException e) {
				level = -1;
			}
			if (level < 0 || level > 40) {
				channel.sendMessage("Level \"" + args[2] + "\" is an invalid level");
				return;
			}
			profile.setLevel(level);
			executed = true;
			break;
		case "trainercode":
		case "trainer_code":
		case "tc":
			if (args[2].length() != 12) {
				channel.sendMessage("Code \"" + args[2] + "\" is an invalid trainer code");
				return;
			}
			long trainerCode;
			try {
				trainerCode = Long.parseLong(args[2]);
			} catch (NumberFormatException e) {
				trainerCode = -1;
			}
			profile.setTrainerCode(trainerCode);
			executed = true;
			break;
		case "realname":
		case "real_name":
		case "rn":
			profile.setRealName(args[2]);
			executed = true;
			break;
		case "alt":
			if (args[2].length() != 12) {
				channel.sendMessage("Code \"" + args[3] + "\" is an invalid trainer code");
				return;
			}
			long altTrainerCode;
			try {
				altTrainerCode = Long.parseLong(args[3]);
			} catch (NumberFormatException e) {
				altTrainerCode = -1;
			}
			profile.getAlts().put(args[2], altTrainerCode);
			executed = true;
			break;
		}
		if (!executed && isAdmin) {
			switch (args[1].toLowerCase()) {
			case "team":
			case "t":
				EnumTeam team;
				try {
					team = EnumTeam.valueOf(args[2].toUpperCase());
				} catch (IllegalArgumentException e) {
					team = null;
				}
				if (team == null) {
					channel.sendMessage("Team \"" + args[3] + "\" not found");
					return;
				}
				profile.setTeam(team);
				executed = true;
				break;
			case "pogoname":
			case "username":
				profile.setPoGoName(args[2]);
				executed = true;
				break;
			}
		}
		if (executed) {
			server.setProfile(target, profile);
			channel.sendMessage("Updated \"" + args[1] + "\" to \"" + args[2] + "\"",
					profile.toEmbed(server));
		} else
			channel.sendMessage("Invalid option \"" + args[1] + "\".");
	}

}
