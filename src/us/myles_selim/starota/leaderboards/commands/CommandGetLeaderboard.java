package us.myles_selim.starota.leaderboards.commands;

import java.util.List;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.Permissions;
import us.myles_selim.starota.commands.StarotaCommand;
import us.myles_selim.starota.leaderboards.Leaderboard;
import us.myles_selim.starota.wrappers.StarotaServer;

public class CommandGetLeaderboard extends StarotaCommand {

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
	public void execute(String[] args, IMessage message, StarotaServer server, IChannel channel)
			throws Exception {
		if (args.length < 2) {
			channel.sendMessage(
					"**Usage**: " + server.getPrefix() + getName() + " " + getGeneralUsage());
			return;
		}
		int page = 0;
		if (args.length > 3)
			try {
				page = Integer.parseInt(args[1]) - 1;
			} catch (NumberFormatException e) {}
		Leaderboard board;
		if (message.getAuthor().getPermissionsForGuild(server.getDiscordGuild())
				.contains(Permissions.ADMINISTRATOR)) {
			board = server.getLeaderboard(args[1]);
			if (board != null && !board.isActive())
				channel.sendMessage("**NOTE**: This board is inactive");
		} else
			board = server.getLeaderboardActive(args[1]);
		if (board != null)
			channel.sendMessage(board.toEmbed(page));
		else
			channel.sendMessage("Leaderboard \"" + args[1] + "\" not found");
	}

}
