package us.myles_selim.starota.leaderboards.commands;

import java.util.List;

import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.MessageChannel;
import us.myles_selim.starota.commands.BotCommand;
import us.myles_selim.starota.commands.registry.CommandException;
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
	public void execute(String[] args, Message message, StarotaServer server, MessageChannel channel)
			throws CommandException {
		if (args.length < 2) {
			channel.createMessage(
					"**Usage**: " + server.getPrefix() + getName() + " " + getGeneralUsage()).block();
			return;
		}
		Member user = message.getAuthorAsMember().block();
		Leaderboard board = server.getLeaderboardActive(args[1]);
		if (board == null) {
			channel.createMessage("Leaderboard \"" + args[1] + "\" not found").block();
			return;
		}
		LeaderboardEntry entry = board.getEntry(user.getId().asLong());
		switch (board.getType()) {
		case INTEGRATION:
			channel.createMessage(
					"This leaderboard is managed by an integration and cannot be updated manually")
					.block();
			break;
		case NORMAL:
			if (args.length < 3) {
				channel.createMessage(
						"**Usage**: " + server.getPrefix() + getName() + " " + getGeneralUsage())
						.block();
				return;
			}
			handleNormalBoard(board, entry, user, channel, args);
			break;
		case OCR:
			if (message.getAttachments().isEmpty()) {
				channel.createMessage("This leaderboard is updated via OCR (image processing).  "
						+ "Please upload your respective photo with this message to update").block();
				return;
			}
			break;
		default:
			channel.createMessage("Unrecognized leaderboard type, please report to Selim#0042 urgently")
					.block();
			break;
		}
	}

	private void handleNormalBoard(Leaderboard board, LeaderboardEntry entry, Member user,
			MessageChannel channel, String[] args) {
		try {
			if (entry != null) {
				long oldStat = entry.getValue();
				board.updateEntry(new LeaderboardEntry(user, Long.decode(args[2])));
				channel.createMessage((m) -> m.setContent(
						"Updated your statistic on " + board.getDisplayName() + " from " + oldStat)
						.setEmbed(board.toEmbed())).block();
			} else {
				board.updateEntry(new LeaderboardEntry(user, Long.decode(args[2])));
				channel.createMessage(
						(m) -> m.setContent("Updated your statistic on " + board.getDisplayName())
								.setEmbed(board.toEmbed()))
						.block();
			}
		} catch (NumberFormatException e) {
			channel.createMessage("Invalid input, please try again").block();
		}
	}

}
