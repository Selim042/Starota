package us.myles_selim.starota.profiles.commands;

import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.Role;
import discord4j.core.object.entity.TextChannel;
import discord4j.core.object.util.Permission;
import discord4j.core.object.util.PermissionSet;
import us.myles_selim.starota.commands.StarotaCommand;
import us.myles_selim.starota.enums.EnumTeam;
import us.myles_selim.starota.misc.utils.MiscUtils;
import us.myles_selim.starota.profiles.PlayerProfile;
import us.myles_selim.starota.wrappers.StarotaServer;

public class CommandSelfRegister extends StarotaCommand {

	public CommandSelfRegister() {
		super("sregister", "Registers your own profile and assigns you a profile.");
	}

	@Override
	public PermissionSet getCommandPermission() {
		return PermissionSet.of(Permission.SEND_MESSAGES, Permission.EMBED_LINKS,
				Permission.MANAGE_ROLES, Permission.MANAGE_MESSAGES);
	}

	@Override
	public String getGeneralUsage() {
		return "[poGoName] [level] <team>";
	}

	@Override
	public void execute(String[] args, Message message, StarotaServer server, TextChannel channel) {
		if (args.length < 3) {
			if (!hasTeamRoles(server.getDiscordGuild())) {
				channel.createMessage("**Usage**: " + server.getPrefix() + this.getName()
						+ " [poGoName] [level] [team]");
				return;
			}
			channel.createMessage(
					"**Usage**: " + server.getPrefix() + this.getName() + " [username] [level]");
			return;
		}

		Member target = message.getAuthor().get().asMember(server.getDiscordGuild().getId()).block();
		if (server.hasProfile(target)) {
			channel.createMessage("User \"" + args[1] + "\" already has a profile");
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
			for (Role role : target.getRoles().collectList().block()) {
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
			channel.createMessage("Team \"" + args[2] + "\" not found");
			return;
		}

		int level;
		try {
			level = Integer.parseInt(args[2]);
		} catch (NumberFormatException e) {
			level = -1;
		}
		if (level == -1) {
			channel.createMessage("Invalid level \"" + args[2] + "\"");
			return;
		}

		PlayerProfile profile = new PlayerProfile().setPoGoName(args[1])
				.setDiscordId(target.getId().asLong()).setLevel(level).setTeam(team);
		server.setProfile(target, profile);

		Role teamRole = MiscUtils.getTeamRole(server.getDiscordGuild(), team);
		if (teamRole != null)
			target.addRole(teamRole.getId());

		channel.createMessage((m) -> {
			m.setContent("Sucessfully registered " + target.getUsername());
			profile.toEmbed(server);
		});
	}

	private static boolean hasTeamRoles(Guild server) {
		for (Role r : server.getRoles().collectList().block()) {
			for (EnumTeam t : EnumTeam.values()) {
				if (t.getName().equalsIgnoreCase(r.getName()))
					return true;
			}
		}
		return false;
	}

}
