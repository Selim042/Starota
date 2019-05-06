package us.myles_selim.starota.leaderboards.commands;

import java.util.List;

import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.TextChannel;
import discord4j.core.object.util.Permission;
import us.myles_selim.starota.commands.StarotaCommand;
import us.myles_selim.starota.leaderboards.Leaderboard;
import us.myles_selim.starota.misc.utils.RolePermHelper;
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
	public Permission requiredUsePermission() {
		return Permission.ADMINISTRATOR;
	}

	@Override
	public String getGeneralUsage() {
		return "[leaderboard]";
	}

	@Override
	public void execute(String[] args, Message message, StarotaServer server, TextChannel channel) {
		if (args.length < 2) {
			channel.createMessage(
					"**Usage**: " + server.getPrefix() + getName() + " " + getGeneralUsage());
			return;
		}
		int maxBoards = RolePermHelper.getMaxLeaderboards(server.getDiscordGuild());
		if (server.getLeaderboardCount() > maxBoards) {
			channel.createMessage("You have reached your leaderboard limit of " + maxBoards
					+ ".  To upgrade your maximum please visit https://patreon.com/Selim_042.");
			return;
		}
		Leaderboard board = server.newLeaderboard(args[1]);
		if (board != null)
			channel.createMessage("Created a new leaderboard, \"" + args[1]
					+ "\" with a default alias of \"" + args[1].replaceAll(" ", "_")
					+ "\".  It will be inactive until you add aliases with `" + server.getPrefix()
					+ "editLeaderboard`.");
		else
			channel.createMessage("Leaderboard \"" + args[1] + "\" already exists.");
	}

}
