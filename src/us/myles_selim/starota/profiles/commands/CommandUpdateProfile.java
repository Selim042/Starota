package us.myles_selim.starota.profiles.commands;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import us.myles_selim.starota.commands.registry.PrimaryCommandHandler;
import us.myles_selim.starota.commands.registry.java.JavaCommand;
import us.myles_selim.starota.embed_converter.EmbedConverter;
import us.myles_selim.starota.profiles.PlayerProfile;
import us.myles_selim.starota.profiles.ProfileManager;

public class CommandUpdateProfile extends JavaCommand {

	public CommandUpdateProfile() {
		super("updateProfile", "Update parts of your profile.");
	}

	@Override
	public String getGeneralUsage() {
		return "[level/trainerCode/realName/alt] [value]";
	}

	@Override
	public void execute(String[] args, IMessage message, IGuild guild, IChannel channel) {
		if (!ProfileManager.hasProfile(guild, message.getAuthor())) {
			channel.sendMessage("You do not yet have a profile.  Please contact an admin of \""
					+ guild.getName() + "\".");
			return;
		}
		if (args.length < 3) {
			channel.sendMessage("**Usage**: " + PrimaryCommandHandler.getPrefix(guild) + this.getName()
					+ " [level/trainerCode/realName/alt] [value]");
			return;
		}
		PlayerProfile profile = ProfileManager.getProfile(guild, message.getAuthor());
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
			if (args[3].length() != 12) {
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
		if (executed) {
			ProfileManager.setProfile(guild, message.getAuthor(), profile);
			channel.sendMessage("Updated \"" + args[1] + "\" to \"" + args[2] + "\"",
					EmbedConverter.toEmbed(profile));
		} else
			channel.sendMessage("Invalid option " + args[1]);
	}

}
