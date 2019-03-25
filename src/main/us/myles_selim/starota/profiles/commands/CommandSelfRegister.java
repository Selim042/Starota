package us.myles_selim.starota.profiles.commands;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IRole;
import sx.blah.discord.handle.obj.IUser;
import us.myles_selim.starota.commands.StarotaCommand;
import us.myles_selim.starota.enums.EnumTeam;
import us.myles_selim.starota.profiles.PlayerProfile;
import us.myles_selim.starota.wrappers.StarotaServer;

public class CommandSelfRegister extends StarotaCommand {

	public CommandSelfRegister() {
		super("sregister", "Registers your own profile and assigns you a profile.");
	}

	@Override
	public String getGeneralUsage() {
		return "[poGoName] [level] <team>";
	}

	@Override
	public void execute(String[] args, IMessage message, StarotaServer server, IChannel channel) {
		if (args.length < 3) {
			if (!hasTeamRoles(server.getDiscordGuild())) {
				channel.sendMessage("**Usage**: " + server.getPrefix() + this.getName()
						+ " [poGoName] [level] [team]");
				return;
			}
			channel.sendMessage(
					"**Usage**: " + server.getPrefix() + this.getName() + " [username] [level]");
			return;
		}

		IUser target = message.getAuthor();
		if (server.hasProfile(target)) {
			channel.sendMessage("User \"" + args[1] + "\" already has a profile");
			return;
		}

		EnumTeam team = null;
		if (args.length > 3) {
			try {
				team = EnumTeam.valueOf(args[3].toUpperCase());
			} catch (IllegalArgumentException e) {
				team = null;
			}
		}
		try {
			for (IRole role : target.getRolesForGuild(server.getDiscordGuild())) {
				String name = role.getName().replaceAll(" ", "_");
				for (EnumTeam t : EnumTeam.values()) {
					if (t.name().equalsIgnoreCase(name)) {
						team = t;
						break;
					}
				}
				if (team != null)
					break;
			}
		} catch (IllegalArgumentException e) {
			team = null;
		}
		if (team == null) {
			channel.sendMessage("Team \"" + args[2] + "\" not found");
			return;
		}

		int level;
		try {
			level = Integer.parseInt(args[2]);
		} catch (NumberFormatException e) {
			level = -1;
		}
		if (level == -1) {
			channel.sendMessage("Invalid level \"" + args[2] + "\"");
			return;
		}

		PlayerProfile profile = new PlayerProfile().setPoGoName(args[1]).setDiscordId(target.getLongID())
				.setLevel(level).setTeam(team);
		server.setProfile(target, profile);
		channel.sendMessage("Sucessfully registered " + target.getName(), profile.toEmbed(server));
	}

	private static boolean hasTeamRoles(IGuild server) {
		for (IRole r : server.getRoles()) {
			for (EnumTeam t : EnumTeam.values()) {
				if (t.getName().equalsIgnoreCase(r.getName()))
					return true;
			}
		}
		return false;
	}

}
