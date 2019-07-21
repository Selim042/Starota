package us.myles_selim.starota.leaderboards.commands;

import java.util.List;

import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.MessageChannel;
import discord4j.core.object.util.Permission;
import discord4j.core.object.util.PermissionSet;
import us.myles_selim.starota.commands.BotCommand;
import us.myles_selim.starota.commands.registry.CommandException;
import us.myles_selim.starota.leaderboards.Leaderboard;
import us.myles_selim.starota.misc.utils.RolePermHelper;
import us.myles_selim.starota.wrappers.StarotaServer;

public class CommandEditLeaderboard extends BotCommand<StarotaServer> {

	private static final String OPTIONS = "**Options**:\n"
			+ " - addAlias\n - removeAlias\n - aliases (comma delimited)\n - color\n - enabled";

	public CommandEditLeaderboard() {
		super("editLeaderboard",
				"Edits the properties of an existing leaderboard or creates a new one.");
	}

	@Override
	public List<String> getAliases() {
		List<String> aliases = super.getAliases();
		aliases.add("editLb");
		return aliases;
	}

	@Override
	public Permission requiredUsePermission() {
		return Permission.ADMINISTRATOR;
	}

	@Override
	public PermissionSet getCommandPermission() {
		return PermissionSet.of(Permission.SEND_MESSAGES, Permission.EMBED_LINKS);
	}

	@Override
	public String getGeneralUsage() {
		return "[leaderboard] [optionName] [optionValue]";
	}

	@Override
	public void execute(String[] args, Message message, StarotaServer server, MessageChannel channel)
			throws CommandException {
		if (args.length == 2 && args[1].equalsIgnoreCase("options")) {
			channel.createMessage(OPTIONS).block();
			return;
		}
		if (args.length == 3 && args[2].equalsIgnoreCase("view")) {
			Leaderboard board = server.getLeaderboard(args[1]);
			if (board == null)
				channel.createMessage("Leaderboard \"" + args[1] + "\" not found").block();
			else
				channel.createEmbed(board.toEmbedOptions()).block();
			return;
		}
		if (args.length < 4) {
			channel.createMessage(
					"**Usage**: " + server.getPrefix() + getName() + " " + getGeneralUsage()).block();
			return;
		}
		Leaderboard board = server.getLeaderboard(args[1]);
		if (board == null) {
			channel.createMessage("Leaderboard \"" + args[1] + "\" not found").block();
			return;
		}
		boolean updated;
		switch (args[2].toLowerCase()) {
		case "addalias":
			board.addAlias(args[3]);
			updated = true;
			break;
		case "removealias":
			board.removeAlias(args[3]);
			updated = true;
			break;
		case "aliases":
			String[] aliases = args[3].split(",");
			board.setAliases(aliases);
			updated = true;
			break;
		case "decending":
			boolean decending;
			switch (args[3].toLowerCase()) {
			case "true":
				decending = true;
				updated = true;
				break;
			case "false":
				decending = false;
				updated = true;
				break;
			default:
				decending = false;
				updated = false;
				break;
			}
			if (updated) {
				board.setDecending(decending);
				updated = true;
			}
			break;
		case "color":
			int color;
			try {
				color = Integer.decode(args[3]);
			} catch (NumberFormatException e) {
				color = -1;
			}
			if (color != -1) {
				board.setColor(color);
				updated = true;
			} else
				updated = false;
			break;
		case "enabled":
			boolean enabled;
			switch (args[3].toLowerCase()) {
			case "true":
				int maxBoards = RolePermHelper.getMaxLeaderboards(server.getDiscordGuild());
				if (server.getLeaderboardsActive().size() >= maxBoards) {
					channel.createMessage(String.format(
							"This server has already reached its limit of %d active leaderboards",
							maxBoards)).block();
					return;
				}
				enabled = true;
				updated = true;
				break;
			case "false":
				enabled = false;
				updated = true;
				break;
			default:
				enabled = false;
				updated = false;
				break;
			}
			if (updated) {
				board.setEnabled(enabled);
				updated = true;
			}
			break;
		default:
			channel.createMessage("Unknown option \"" + args[2] + "\", valid options are:\n" + OPTIONS)
					.block();
			return;
		}
		if (!updated)
			channel.createMessage("Invalid value \"" + args[3] + "\"").block();
		else {
			channel.createMessage((m) -> m.setContent("Updated options for " + board.getDisplayName())
					.setEmbed(board.toEmbedOptions())).block();
			server.setLeaderboard(board.getDisplayName(), board);
		}
	}

}
