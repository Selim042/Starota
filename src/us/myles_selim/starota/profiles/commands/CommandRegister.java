package us.myles_selim.starota.profiles.commands;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IRole;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.handle.obj.Permissions;
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.commands.StarotaCommand;
import us.myles_selim.starota.enums.EnumTeam;
import us.myles_selim.starota.profiles.PlayerProfile;
import us.myles_selim.starota.wrappers.StarotaServer;

public class CommandRegister extends StarotaCommand {

	public CommandRegister() {
		super("register", "Registers the given user and assigns them a profile.");
	}

	@Override
	public String getGeneralUsage() {
		return "[poGoName] [target] [team] [level]";
	}

	@Override
	public Permissions requiredPermission() {
		return Permissions.ADMINISTRATOR;
	}

	@Override
	public IRole requiredRole(IGuild guild) {
		if (guild.getLongID() == Starota.PVILLE_SERVER)
			return guild.getRoleByID(314744756456521728l);
		return super.requiredRole(guild);
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
		channel.sendMessage("Sucessfully registered " + target.getName(), profile.toEmbed(server));

		// Role updates (pville only)
		IGuild guild = server.getDiscordGuild();
		IRole roleLost = guild.getRoleByID(ROLE_LOST);
		if (roleLost != null && target.hasRole(roleLost)) {
			target.removeRole(roleLost);
			target.addRole(guild.getRoleByID(ROLE_TRAINER));
			switch (team) {
			case INSTINCT:
				target.addRole(guild.getRoleByID(ROLE_INSTINCT));
				break;
			case MYSTIC:
				target.addRole(guild.getRoleByID(ROLE_MYSTIC));
				break;
			case VALOR:
				target.addRole(guild.getRoleByID(ROLE_VALOR));
				break;
			case NO_TEAM:
			default:
				break;
			}
		}
	}

	private static final long ROLE_LOST = 493950373460181012L;
	private static final long ROLE_TRAINER = 339514724620304386L;
	private static final long ROLE_INSTINCT = 336152173257687040L;
	private static final long ROLE_MYSTIC = 335596106102603792L;
	private static final long ROLE_VALOR = 336152455433945090L;

}
