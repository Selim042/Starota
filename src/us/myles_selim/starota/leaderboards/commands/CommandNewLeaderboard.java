package us.myles_selim.starota.leaderboards.commands;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.Permissions;
import us.myles_selim.starota.commands.registry.PrimaryCommandHandler;
import us.myles_selim.starota.commands.registry.java.JavaCommand;
import us.myles_selim.starota.leaderboards.Leaderboard;
import us.myles_selim.starota.leaderboards.LeaderboardManager;

public class CommandNewLeaderboard extends JavaCommand {

	public CommandNewLeaderboard() {
		super("newLeaderboard", "Creates a new inactive leaderboard.");
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
	public void execute(String[] args, IMessage message, IGuild guild, IChannel channel) {
		if (args.length < 2) {
			channel.sendMessage("**Usage**: " + PrimaryCommandHandler.getPrefix(guild) + getName() + " "
					+ getGeneralUsage());
			return;
		}
		Leaderboard board = LeaderboardManager.newLeaderboard(guild, args[1]);
		if (board != null)
			channel.sendMessage("Created a new leaderboard, \"" + args[1]
					+ "\" with a default alias of \"" + args[1].replaceAll(" ", "_")
					+ "\".  It will be inactive until you add aliases with `"
					+ PrimaryCommandHandler.getPrefix(guild) + "editLeaderboard`.");
		else
			channel.sendMessage("Leaderboard \"" + args[1] + "\" already exists.");
	}

}