package us.myles_selim.starota.leaderboards.commands;

import java.util.List;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.Permissions;
import us.myles_selim.starota.commands.registry.PrimaryCommandHandler;
import us.myles_selim.starota.commands.registry.java.JavaCommand;
import us.myles_selim.starota.leaderboards.Leaderboard;
import us.myles_selim.starota.leaderboards.LeaderboardManager;

public class CommandGetLeaderboard extends JavaCommand {

	public CommandGetLeaderboard() {
		super("getLeaderboard", "Displays the specified leaderboard.");
	}

	@Override
	public List<String> getAliases() {
		List<String> aliases = super.getAliases();
		aliases.add("lb");
		aliases.add("getLb");
		return aliases;
	}

	@Override
	public String getGeneralUsage() {
		return "[leaderboard] <page>";
	}

	@Override
	public void execute(String[] args, IMessage message, IGuild guild, IChannel channel)
			throws Exception {
		if (args.length < 2) {
			channel.sendMessage("**Usage**: " + PrimaryCommandHandler.getPrefix(guild) + getName() + " "
					+ getGeneralUsage());
			return;
		}
		int page = 0;
		if (args.length > 3)
			try {
				page = Integer.parseInt(args[1]) - 1;
			} catch (NumberFormatException e) {}
		Leaderboard board;
		if (message.getAuthor().getPermissionsForGuild(guild).contains(Permissions.ADMINISTRATOR)) {
			board = LeaderboardManager.getLeaderboard(guild, args[1]);
			if (board != null && !board.isActive())
				channel.sendMessage("**NOTE**: This board is inactive");
		} else
			board = LeaderboardManager.getLeaderboardActive(guild, args[1]);
		if (board != null)
			channel.sendMessage(board.toEmbed(page));
		else
			channel.sendMessage("Leaderboard \"" + args[1] + "\" not found");
	}

}
