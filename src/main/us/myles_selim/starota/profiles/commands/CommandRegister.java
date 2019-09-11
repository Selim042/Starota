package us.myles_selim.starota.profiles.commands;

import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.MessageChannel;
import discord4j.core.object.entity.PrivateChannel;
import discord4j.core.object.entity.Role;
import discord4j.core.object.util.Permission;
import discord4j.core.object.util.PermissionSet;
import discord4j.core.object.util.Snowflake;
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.commands.BotCommand;
import us.myles_selim.starota.commands.registry.CommandException;
import us.myles_selim.starota.enums.EnumTeam;
import us.myles_selim.starota.misc.utils.MiscUtils;
import us.myles_selim.starota.profiles.PlayerProfile;
import us.myles_selim.starota.wrappers.StarotaServer;

public class CommandRegister extends BotCommand<StarotaServer> {

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
	public void execute(String[] args, Message message, StarotaServer server, MessageChannel channel)
			throws CommandException {
		if (args.length != 5) {
			channel.createMessage("**Usage**: " + server.getPrefix() + this.getName()
					+ " [poGoName] [target] [team] [level]").block();
			return;
		}

		Member target = Starota.findMember(server.getDiscordGuild(), args[2]);
		if (target == null) {
			channel.createMessage("User \"" + args[2] + "\" not found").block();
			return;
		}
		if (server.hasProfile(target)) {
			channel.createMessage("User \"" + args[2] + "\" already has a profile").block();
			return;
		}

		EnumTeam team = EnumTeam.getTeam(new String[] { args[3], args[4] });
		if (team == null) {
			channel.createMessage("Team \"" + args[3] + "\" not found").block();
			return;
		}

		int level;
		try {
			level = Integer.parseInt(args[4]);
		} catch (NumberFormatException e) {
			try {
				level = Integer.parseInt(args[3]);
			} catch (NumberFormatException e2) {
				level = -1;
			}
		}
		if (level == -1) {
			channel.createMessage("Invalid level \"" + args[4] + "\"").block();
			return;
		}

		PlayerProfile profile = new PlayerProfile().setPoGoName(args[1])
				.setDiscordId(target.getId().asLong()).setLevel(level).setTeam(team);
		server.setProfile(target, profile);

		Role teamRole = MiscUtils.getTeamRole(server.getDiscordGuild(), team);
		if (teamRole != null)
			target.addRole(teamRole.getId()).block();

		channel.createMessage((m) -> m.setContent("Successfully registered " + target.getUsername())
				.setEmbed(profile.toEmbed(server))).block();
		PrivateChannel targetPm = target.getPrivateChannel().block();
		targetPm.createMessage(String.format(REGISTERED_PM, server.getDiscordGuild().getName())).block();

		// Role updates (pville only)
		Guild guild = server.getDiscordGuild();
		Role roleLost = guild.getRoleById(ROLE_LOST).block();
		if (roleLost != null && target.getRoleIds().contains(ROLE_LOST)) {
			target.removeRole(ROLE_LOST).block();
			target.addRole(ROLE_TRAINER).block();
		}
	}

	private static final Snowflake ROLE_LOST = Snowflake.of(493950373460181012L);
	private static final Snowflake ROLE_TRAINER = Snowflake.of(339514724620304386L);
	// private static final long ROLE_INSTINCT = 336152173257687040L;
	// private static final long ROLE_MYSTIC = 335596106102603792L;
	// private static final long ROLE_VALOR = 336152455433945090L;

}
