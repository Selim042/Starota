package us.myles_selim.starota.leaderboards.commands;

import java.util.List;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;
import us.myles_selim.starota.commands.registry.PrimaryCommandHandler;
import us.myles_selim.starota.commands.registry.java.JavaCommand;
import us.myles_selim.starota.leaderboards.Leaderboard;
import us.myles_selim.starota.leaderboards.LeaderboardEntry;
import us.myles_selim.starota.leaderboards.LeaderboardManager;

public class CommandUpdateLeaderboard extends JavaCommand {

	public CommandUpdateLeaderboard() {
		super("updateLeaderboard", "Updates your leaderboard statistic.");
	}

	@Override
	public List<String> getAliases() {
		List<String> aliases = super.getAliases();
		aliases.add("ulb");
		return aliases;
	}

	@Override
	public String getGeneralUsage() {
		return "[tradeboard] [value]";
	}

	@Override
	public void execute(String[] args, IMessage message, IGuild guild, IChannel channel) {
		if (args.length < 3) {
			channel.sendMessage("**Usage**: " + PrimaryCommandHandler.getPrefix(guild) + getName() + " "
					+ getGeneralUsage());
			return;
		}
		IUser user = message.getAuthor();
		Leaderboard board = LeaderboardManager.getLeaderboardActive(guild, args[1]);
		if (board == null) {
			channel.sendMessage("Leaderboard \"" + args[1] + "\" not found");
			return;
		}
		LeaderboardEntry entry = board.getEntry(user.getLongID());
		if (entry != null) {
			long oldStat = entry.getValue();
			board.updateEntry(new LeaderboardEntry(user, Long.decode(args[2])));
			channel.sendMessage(
					"Updated your statistic on " + board.getDisplayName() + " from " + oldStat,
					board.toEmbed());
		} else {
			board.updateEntry(new LeaderboardEntry(user, Long.decode(args[2])));
			channel.sendMessage("Updated your statistic on " + board.getDisplayName(), board.toEmbed());
		}
	}

}
