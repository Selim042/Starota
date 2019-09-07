package us.myles_selim.starota.leaderboards.commands;

import java.util.List;

import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.MessageChannel;
import discord4j.core.object.util.Permission;
import discord4j.core.object.util.PermissionSet;
import us.myles_selim.starota.commands.BotCommand;
import us.myles_selim.starota.commands.registry.CommandException;
import us.myles_selim.starota.leaderboards.Leaderboard;
import us.myles_selim.starota.wrappers.StarotaServer;

public class CommandGetLeaderboard extends BotCommand<StarotaServer> {

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
	public void execute(String[] args, Message message, StarotaServer server, MessageChannel channel)
			throws CommandException {
		if (args.length < 2) {
			channel.createMessage(
					"**Usage**: " + server.getPrefix() + getName() + " " + getGeneralUsage()).block();
			return;
		}
		int page = 0;
		if (args.length > 3)
			try {
				page = Integer.parseInt(args[1]) - 1;
			} catch (NumberFormatException e) { /* */ }
		Leaderboard board;
		if (message.getAuthorAsMember().block().getBasePermissions().block()
				.contains(Permission.ADMINISTRATOR)) {
			board = server.getLeaderboard(args[1]);
			if (board != null && !board.isActive())
				channel.createMessage("**NOTE**: This board is inactive").block();
		} else
			board = server.getLeaderboardActive(args[1]);
		if (board != null)
			channel.createEmbed(board.toEmbed(page)).block();
		else
			channel.createMessage("Leaderboard \"" + args[1] + "\" not found").block();
	}

}
