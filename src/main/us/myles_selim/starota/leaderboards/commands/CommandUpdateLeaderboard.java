package us.myles_selim.starota.leaderboards.commands;

import java.util.List;

import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.TextChannel;
import discord4j.core.object.entity.User;
import us.myles_selim.starota.commands.StarotaCommand;
import us.myles_selim.starota.leaderboards.Leaderboard;
import us.myles_selim.starota.leaderboards.LeaderboardEntry;
import us.myles_selim.starota.wrappers.StarotaServer;

public class CommandUpdateLeaderboard extends StarotaCommand {

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
	public void execute(String[] args, Message message, StarotaServer server, TextChannel channel) {
		if (args.length < 3) {
			channel.createMessage(
					"**Usage**: " + server.getPrefix() + getName() + " " + getGeneralUsage());
			return;
		}
		User user = message.getAuthor().get();
		Leaderboard board = server.getLeaderboardActive(args[1]);
		if (board == null) {
			channel.createMessage("Leaderboard \"" + args[1] + "\" not found");
			return;
		}
		LeaderboardEntry entry = board.getEntry(user.getId().asLong());
		try {
			if (entry != null) {
				long oldStat = entry.getValue();
				board.updateEntry(new LeaderboardEntry(user, Long.decode(args[2])));
				channel.createMessage((m) -> m.setContent(
						"Updated your statistic on " + board.getDisplayName() + " from " + oldStat)
						.setEmbed(board.toEmbed()));
			} else {
				board.updateEntry(new LeaderboardEntry(user, Long.decode(args[2])));
				channel.createMessage(
						(m) -> m.setContent("Updated your statistic on " + board.getDisplayName())
								.setEmbed(board.toEmbed()));
			}
		} catch (NumberFormatException e) {
			channel.createMessage("Invalid input, please try again");
		}
	}

}
