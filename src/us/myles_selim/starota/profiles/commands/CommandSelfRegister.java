package us.myles_selim.starota.profiles.commands;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IRole;
import sx.blah.discord.handle.obj.IUser;
import us.myles_selim.starota.EnumTeam;
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.commands.registry.Command;
import us.myles_selim.starota.commands.registry.CommandRegistry;
import us.myles_selim.starota.profiles.PlayerProfile;
import us.myles_selim.starota.profiles.ProfileManager;

public class CommandSelfRegister extends Command {

	public CommandSelfRegister() {
		super("sregister", "Registers your own profile and assigns you a profile.");
	}

	@Override
	public IRole requiredRole(IGuild guild) {
		if (guild.getLongID() == Starota.PVILLE_SERVER)
			return guild.getRoleByID(335777489534320640L); // Backer
		return super.requiredRole(guild);
	}

	@Override
	public void execute(String[] args, IMessage message, IGuild guild, IChannel channel) {
		if (args.length < 3) {
			if (!hasTeamRoles(guild)) {
				channel.sendMessage("**Usage**: " + CommandRegistry.getPrefix(guild) + this.getName()
						+ " [username] [level] [team]");
				return;
			}
			channel.sendMessage("**Usage**: " + CommandRegistry.getPrefix(guild) + this.getName()
					+ " [username] [level]");
			return;
		}

		IUser target = message.getAuthor();
		if (ProfileManager.hasProfile(guild, target)) {
			channel.sendMessage("User \"" + args[1] + "\" already has a profile");
			return;
		}

		EnumTeam team = null;
		try {
			team = EnumTeam.valueOf(args[3].toUpperCase());
		} catch (IllegalArgumentException e) {
			team = null;
		}
		try {
			for (IRole role : target.getRolesForGuild(guild)) {
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
		ProfileManager.setProfile(guild, target, profile);
		channel.sendMessage("Sucessfully registered " + target.getName(),
				ProfileManager.getProfileEmbed(guild, profile));
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
