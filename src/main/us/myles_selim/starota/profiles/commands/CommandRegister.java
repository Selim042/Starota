package us.myles_selim.starota.profiles.commands;

import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.PrivateChannel;
import discord4j.core.object.entity.Role;
import discord4j.core.object.entity.TextChannel;
import discord4j.core.object.util.Permission;
import discord4j.core.object.util.PermissionSet;
import discord4j.core.object.util.Snowflake;
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.commands.StarotaCommand;
import us.myles_selim.starota.enums.EnumTeam;
import us.myles_selim.starota.misc.utils.MiscUtils;
import us.myles_selim.starota.profiles.PlayerProfile;
import us.myles_selim.starota.wrappers.StarotaServer;

public class CommandRegister extends StarotaCommand {

	public static final String REGISTERED_PM = "You have been registered for a profile on `%s`.\n"
			+ "For more profile information, go into that server and type the command `.profileHelp`.";

	public CommandRegister() {
		super("register", "Registers the given user and assigns them a profile.");
	}

	@Override
	public PermissionSet getCommandPermission() {
		return PermissionSet.of(Permission.SEND_MESSAGES, Permission.EMBED_LINKS,
				Permission.MANAGE_ROLES, Permission.MANAGE_MESSAGES);
	}

	@Override
	public String getGeneralUsage() {
		return "[poGoName] [target] [team] [level]";
	}

	@Override
	public Permission requiredUsePermission() {
		return Permission.ADMINISTRATOR;
	}

	@Override
	public void execute(String[] args, Message message, StarotaServer server, TextChannel channel) {
		if (args.length != 5) {
			channel.createMessage("**Usage**: " + server.getPrefix() + this.getName()
					+ " [poGoName] [target] [team] [level]");
			return;
		}

		Member target = Starota.findUser(server.getDiscordGuild().getId().asLong(), args[2])
				.asMember(server.getDiscordGuild().getId()).block();
		if (target == null) {
			channel.createMessage("User \"" + args[2] + "\" not found");
			return;
		}
		if (server.hasProfile(target)) {
			channel.createMessage("User \"" + args[2] + "\" already has a profile");
			return;
		}

		EnumTeam team;
		try {
			team = EnumTeam.valueOf(args[3].toUpperCase());
		} catch (IllegalArgumentException e) {
			team = null;
		}
		if (team == null) {
			channel.createMessage("Team \"" + args[3] + "\" not found");
			return;
		}

		int level;
		try {
			level = Integer.parseInt(args[4]);
		} catch (NumberFormatException e) {
			level = -1;
		}
		if (level == -1) {
			channel.createMessage("Invalid level \"" + args[4] + "\"");
			return;
		}

		PlayerProfile profile = new PlayerProfile().setPoGoName(args[1])
				.setDiscordId(target.getId().asLong()).setLevel(level).setTeam(team);
		server.setProfile(target, profile);

		Role teamRole = MiscUtils.getTeamRole(server.getDiscordGuild(), team);
		if (teamRole != null)
			target.addRole(teamRole.getId());

		channel.createMessage((m) -> m.setContent("Sucessfully registered " + target.getUsername())
				.setEmbed(profile.toEmbed(server)));
		PrivateChannel targetPm = target.getPrivateChannel().block();
		targetPm.createMessage(String.format(REGISTERED_PM, server.getDiscordGuild().getName()));

		// Role updates (pville only)
		Guild guild = server.getDiscordGuild();
		if (target.getRoles().any((r) -> r.getId().equals(ROLE_LOST)).block()) {
			target.removeRole(ROLE_LOST);
			target.addRole(ROLE_TRAINER);
		}
	}

	private static final Snowflake ROLE_LOST = Snowflake.of(493950373460181012L);
	private static final Snowflake ROLE_TRAINER = Snowflake.of(339514724620304386L);
	// private static final long ROLE_INSTINCT = 336152173257687040L;
	// private static final long ROLE_MYSTIC = 335596106102603792L;
	// private static final long ROLE_VALOR = 336152455433945090L;

}
