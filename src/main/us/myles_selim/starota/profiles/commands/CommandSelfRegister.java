package us.myles_selim.starota.profiles.commands;

import java.util.EnumSet;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IRole;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.handle.obj.Permissions;
import us.myles_selim.starota.commands.BotCommand;
import us.myles_selim.starota.enums.EnumTeam;
import us.myles_selim.starota.misc.utils.MiscUtils;
import us.myles_selim.starota.profiles.PlayerProfile;
import us.myles_selim.starota.wrappers.StarotaServer;

public class CommandSelfRegister extends BotCommand<StarotaServer> {

	public CommandSelfRegister() {
		super("sregister", "Registers your own profile and assigns you a profile.");
	}

	@Override
	public EnumSet<Permissions> getCommandPermissions() {
		return EnumSet.of(Permissions.SEND_MESSAGES, Permissions.EMBED_LINKS, Permissions.MANAGE_ROLES,
				Permissions.MANAGE_MESSAGES);
	}

	@Override
	public String getGeneralUsage() {
		return "[poGoName] [level] <team>";
	}

	@Override
	public void execute(String[] args, IMessage message, StarotaServer server, IChannel channel) {
		if (args.length < 3) {
			if (!hasTeamRoles(message.getAuthor(), server)) {
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
			for (IRole role : target.getRolesForGuild(server)) {
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
			if (args.length > 3)
				channel.sendMessage("Team \"" + args[2] + "\" not found");
			else
				this.sendUsage(server.getPrefix(), channel);
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

		IRole teamRole = MiscUtils.getTeamRole(server, team);
		if (teamRole != null)
			target.addRole(teamRole);

		channel.sendMessage("Sucessfully registered " + target.getName(), profile.toEmbed(server));
	}

	private static boolean hasTeamRoles(IUser user, IGuild guild) {
		for (IRole r : user.getRolesForGuild(guild)) {
			for (EnumTeam t : EnumTeam.values()) {
				if (t.getName().equalsIgnoreCase(r.getName()))
					return true;
			}
		}
		return false;
	}

}
