package us.myles_selim.starota.profiles.commands;

import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.MessageChannel;
import discord4j.core.object.util.Permission;
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.commands.BotCommand;
import us.myles_selim.starota.commands.registry.CommandException;
import us.myles_selim.starota.enums.EnumTeam;
import us.myles_selim.starota.profiles.PlayerProfile;
import us.myles_selim.starota.wrappers.StarotaServer;

public class CommandUpdateProfile extends BotCommand<StarotaServer> {

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
	public void execute(String[] args, Message message, StarotaServer server, MessageChannel channel)
			throws CommandException {
		boolean isAdmin = message.getAuthorAsMember().block().getBasePermissions().block()
				.contains(Permission.ADMINISTRATOR);
		if (args.length > 3 && !isAdmin) {
			channel.createMessage("Only admins can change other user's profile information.").block();
			return;
		}
		Member target = message.getAuthorAsMember().block();
		if (args.length > 3 && isAdmin) {
			target = Starota.findMember(server.getDiscordGuild(), args[3]);
			if (target == null) {
				channel.createMessage("User " + args[3] + " not found.").block();
				return;
			}
		}
		if (!server.hasProfile(target)) {
			if (target.equals(message.getAuthor().orElse(null)))
				channel.createMessage("You do not yet have a profile.  Please contact an admin of \""
						+ server.getDiscordGuild().getName() + "\".").block();
			else
				channel.createMessage(target.getMention()
						+ " does not yet have a profile.  Please contact an admin of \""
						+ server.getDiscordGuild().getName() + "\".").block();
			return;
		}
		if (args.length < 3) {
			if (isAdmin)
				channel.createMessage("**Usage**: " + server.getPrefix() + this.getName()
						+ " [level/trainerCode/realName/alt] [value] <target>").block();
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
				channel.createMessage("Level \"" + args[2] + "\" is an invalid level").block();
				return;
			}
			profile.setLevel(level);
			executed = true;
			break;
		case "trainercode":
		case "trainer_code":
		case "tc":
			if (args[2].length() != 12) {
				channel.createMessage("Code \"" + args[2] + "\" is an invalid trainer code").block();
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
		case "alts":
			profile.getAlts().clear();
			if (args[2].equalsIgnoreCase("null")) {
				executed = true;
				break;
			}
			String[] altParts = args[2].split(":");
			for (int i = 0; i < altParts.length; i += 2) {
				String altName = altParts[i];
				String altCode = altParts[i + 1];
				if (altCode.length() != 12)
					continue;
				try {
					profile.getAlts().put(altName, Long.valueOf(altCode));
				} catch (NumberFormatException e) { /* */ }
			}
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
					channel.createMessage("Team \"" + args[3] + "\" not found").block();
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
			channel.createMessage(
					(m) -> m.setContent("Updated \"" + args[1] + "\" to \"" + args[2] + "\"")
							.setEmbed(profile.toEmbed(server)))
					.block();
		} else
			channel.createMessage("Invalid option \"" + args[1] + "\".").block();
	}

}
