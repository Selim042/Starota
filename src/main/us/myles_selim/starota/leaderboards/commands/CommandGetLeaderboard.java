package us.myles_selim.starota.leaderboards.commands;

import java.util.List;

import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.TextChannel;
import discord4j.core.object.util.Permission;
import discord4j.core.object.util.PermissionSet;
import us.myles_selim.starota.commands.StarotaCommand;
import us.myles_selim.starota.leaderboards.Leaderboard;
import us.myles_selim.starota.wrappers.StarotaServer;

public class CommandGetLeaderboard extends StarotaCommand {

	public CommandGetLeaderboard() {
		super("getLeaderboard", "Displays the specified leaderboard.");
	}

	@Override
	public PermissionSet getCommandPermission() {
		return PermissionSet.of(Permission.SEND_MESSAGES, Permission.EMBED_LINKS);
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
	public void execute(String[] args, Message message, StarotaServer server, TextChannel channel)
			throws Exception {
		if (args.length < 2) {
			channel.createMessage(
					"**Usage**: " + server.getPrefix() + getName() + " " + getGeneralUsage());
			return;
		}
		int page = 0;
		if (args.length > 3)
			try {
				page = Integer.parseInt(args[1]) - 1;
			} catch (NumberFormatException e) {}
		Leaderboard board;
		if (message.getAuthor().get().asMember(server.getDiscordGuild().getId()).block()
				.getBasePermissions().block().contains(Permission.ADMINISTRATOR)) {
			board = server.getLeaderboard(args[1]);
			if (board != null && !board.isActive())
				channel.createMessage("**NOTE**: This board is inactive");
		} else
			board = server.getLeaderboardActive(args[1]);
		if (board != null)
			channel.createEmbed(board.toEmbed(page));
		else
			channel.createMessage("Leaderboard \"" + args[1] + "\" not found");
	}

}
