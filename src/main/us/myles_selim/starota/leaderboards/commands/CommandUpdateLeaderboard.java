package us.myles_selim.starota.leaderboards.commands;

import java.util.List;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;
import us.myles_selim.starota.commands.BotCommand;
import us.myles_selim.starota.leaderboards.Leaderboard;
import us.myles_selim.starota.leaderboards.LeaderboardEntry;
import us.myles_selim.starota.wrappers.StarotaServer;

public class CommandUpdateLeaderboard extends BotCommand<StarotaServer> {

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
	public void execute(String[] args, IMessage message, StarotaServer server, IChannel channel) {
		if (args.length < 2) {
			channel.sendMessage(
					"**Usage**: " + server.getPrefix() + getName() + " " + getGeneralUsage());
			return;
		}
		IUser user = message.getAuthor();
		Leaderboard board = server.getLeaderboardActive(args[1]);
		if (board == null) {
			channel.sendMessage("Leaderboard \"" + args[1] + "\" not found");
			return;
		}
		LeaderboardEntry entry = board.getEntry(user.getLongID());
		switch (board.getType()) {
		case INTEGRATION:
			channel.sendMessage(
					"This leaderboard is managed by an integration and cannot be updated manually");
			break;
		case NORMAL:
			if (args.length < 3) {
				channel.sendMessage(
						"**Usage**: " + server.getPrefix() + getName() + " " + getGeneralUsage());
				return;
			}
			handleNormalBoard(board, entry, user, channel, args);
			break;
		case OCR:
			if (message.getAttachments().isEmpty()) {
				channel.sendMessage("This leaderboard is updated via OCR (image processing).  "
						+ "Please upload your respective photo with this message to update");
				return;
			}
			break;
		default:
			channel.sendMessage("Unrecognized leaderboard type, please report to Selim#0042 urgently");
			break;
		}
	}

	private void handleNormalBoard(Leaderboard board, LeaderboardEntry entry, IUser user,
			IChannel channel, String[] args) {
		try {
			if (entry != null) {
				long oldStat = entry.getValue();
				board.updateEntry(new LeaderboardEntry(user, Long.decode(args[2])));
				channel.sendMessage(
						"Updated your statistic on " + board.getDisplayName() + " from " + oldStat,
						board.toEmbed());
			} else {
				board.updateEntry(new LeaderboardEntry(user, Long.decode(args[2])));
				channel.sendMessage("Updated your statistic on " + board.getDisplayName(),
						board.toEmbed());
			}
		} catch (NumberFormatException e) {
			channel.sendMessage("Invalid input, please try again");
		}
	}

}
