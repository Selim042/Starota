package us.myles_selim.starota.leaderboards.commands;

import java.util.List;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.Permissions;
import us.myles_selim.starota.RolePermHelper;
import us.myles_selim.starota.commands.StarotaCommand;
import us.myles_selim.starota.leaderboards.Leaderboard;
import us.myles_selim.starota.wrappers.StarotaServer;

public class CommandNewLeaderboard extends StarotaCommand {

	public CommandNewLeaderboard() {
		super("newLeaderboard", "Creates a new inactive leaderboard.");
	}

	@Override
	public List<String> getAliases() {
		List<String> aliases = super.getAliases();
		aliases.add("newLb");
		return aliases;
	}

	@Override
	public Permissions requiredPermission() {
		return Permissions.ADMINISTRATOR;
	}

	@Override
	public String getGeneralUsage() {
		return "[leaderboard]";
	}

	@Override
	public void execute(String[] args, IMessage message, StarotaServer server, IChannel channel) {
		if (args.length < 2) {
			channel.sendMessage(
					"**Usage**: " + server.getPrefix() + getName() + " " + getGeneralUsage());
			return;
		}
		int maxBoards = RolePermHelper.getMaxLeaderboards(server.getDiscordGuild());
		if (server.getLeaderboardCount() > maxBoards) {
			channel.sendMessage("You have reached your leaderboard limit of " + maxBoards
					+ ".  To upgrade your maximum please visit https://patreon.com/Selim_042.");
			return;
		}
		Leaderboard board = server.newLeaderboard(args[1]);
		if (board != null)
			channel.sendMessage("Created a new leaderboard, \"" + args[1]
					+ "\" with a default alias of \"" + args[1].replaceAll(" ", "_")
					+ "\".  It will be inactive until you add aliases with `" + server.getPrefix()
					+ "editLeaderboard`.");
		else
			channel.sendMessage("Leaderboard \"" + args[1] + "\" already exists.");
	}

}