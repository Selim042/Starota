package us.myles_selim.starota.profiles.commands;

import java.util.EnumSet;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IPrivateChannel;
import sx.blah.discord.handle.obj.IRole;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.handle.obj.Permissions;
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.commands.BotCommand;
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
	public EnumSet<Permissions> getCommandPermissions() {
		return EnumSet.of(Permissions.SEND_MESSAGES, Permissions.EMBED_LINKS, Permissions.MANAGE_ROLES,
				Permissions.MANAGE_MESSAGES);
	}

	@Override
	public String getGeneralUsage() {
		return "[poGoName] [target] [team] [level]";
	}

	@Override
	public Permissions requiredUsePermission() {
		return Permissions.ADMINISTRATOR;
	}

	@Override
	public void execute(String[] args, IMessage message, StarotaServer server, IChannel channel) {
		if (args.length != 5) {
			channel.sendMessage("**Usage**: " + server.getPrefix() + this.getName()
					+ " [poGoName] [target] [team] [level]");
			return;
		}

		IUser target = Starota.findUser(server.getDiscordGuild().getLongID(), args[2]);
		if (target == null) {
			channel.sendMessage("User \"" + args[2] + "\" not found");
			return;
		}
		if (server.hasProfile(target)) {
			channel.sendMessage("User \"" + args[2] + "\" already has a profile");
			return;
		}

		EnumTeam team;
		try {
			team = EnumTeam.valueOf(args[3].toUpperCase());
		} catch (IllegalArgumentException e) {
			team = null;
		}
		if (team == null) {
			channel.sendMessage("Team \"" + args[3] + "\" not found");
			return;
		}

		int level;
		try {
			level = Integer.parseInt(args[4]);
		} catch (NumberFormatException e) {
			level = -1;
		}
		if (level == -1) {
			channel.sendMessage("Invalid level \"" + args[4] + "\"");
			return;
		}

		PlayerProfile profile = new PlayerProfile().setPoGoName(args[1]).setDiscordId(target.getLongID())
				.setLevel(level).setTeam(team);
		server.setProfile(target, profile);

		IRole teamRole = MiscUtils.getTeamRole(server.getDiscordGuild(), team);
		if (teamRole != null)
			target.addRole(teamRole);

		channel.sendMessage("Sucessfully registered " + target.getName(), profile.toEmbed(server));
		IPrivateChannel targetPm = target.getOrCreatePMChannel();
		targetPm.sendMessage(String.format(REGISTERED_PM, server.getDiscordGuild().getName()));

		// Role updates (pville only)
		IGuild guild = server.getDiscordGuild();
		IRole roleLost = guild.getRoleByID(ROLE_LOST);
		if (roleLost != null && target.hasRole(roleLost)) {
			target.removeRole(roleLost);
			target.addRole(guild.getRoleByID(ROLE_TRAINER));
			// switch (team) {
			// case INSTINCT:
			// target.addRole(guild.getRoleByID(ROLE_INSTINCT));
			// break;
			// case MYSTIC:
			// target.addRole(guild.getRoleByID(ROLE_MYSTIC));
			// break;
			// case VALOR:
			// target.addRole(guild.getRoleByID(ROLE_VALOR));
			// break;
			// case NO_TEAM:
			// default:
			// break;
			// }
		}
	}

	private static final long ROLE_LOST = 493950373460181012L;
	private static final long ROLE_TRAINER = 339514724620304386L;
	// private static final long ROLE_INSTINCT = 336152173257687040L;
	// private static final long ROLE_MYSTIC = 335596106102603792L;
	// private static final long ROLE_VALOR = 336152455433945090L;

}
