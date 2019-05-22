package us.myles_selim.starota.leaderboards.commands;

import java.util.EnumSet;
import java.util.List;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.Permissions;
import us.myles_selim.starota.commands.StarotaCommand;
import us.myles_selim.starota.leaderboards.Leaderboard;
import us.myles_selim.starota.misc.utils.RolePermHelper;
import us.myles_selim.starota.wrappers.StarotaServer;

public class CommandEditLeaderboard extends StarotaCommand {

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
	public Permissions requiredUsePermission() {
		return Permissions.ADMINISTRATOR;
	}

	@Override
	public EnumSet<Permissions> getCommandPermissions() {
		return EnumSet.of(Permissions.SEND_MESSAGES, Permissions.EMBED_LINKS);
	}

	@Override
	public String getGeneralUsage() {
		return "[leaderboard] [optionName] [optionValue]";
	}

	@Override
	public void execute(String[] args, IMessage message, StarotaServer server, IChannel channel) {
		if (args.length == 2 && args[1].equalsIgnoreCase("options")) {
			channel.sendMessage(OPTIONS);
			return;
		}
		if (args.length == 3 && args[2].equalsIgnoreCase("view")) {
			Leaderboard board = server.getLeaderboard(args[1]);
			if (board == null)
				channel.sendMessage("Leaderboard \"" + args[1] + "\" not found");
			else
				channel.sendMessage(board.toEmbedOptions());
			return;
		}
		if (args.length < 4) {
			channel.sendMessage(
					"**Usage**: " + server.getPrefix() + getName() + " " + getGeneralUsage());
			return;
		}
		Leaderboard board = server.getLeaderboard(args[1]);
		if (board == null) {
			channel.sendMessage("Leaderboard \"" + args[1] + "\" not found");
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
					channel.sendMessage(String.format(
							"This server has already reached its limit of %d active leaderboards",
							maxBoards));
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
			channel.sendMessage("Unknown option \"" + args[2] + "\", valid options are:\n" + OPTIONS);
			return;
		}
		if (!updated)
			channel.sendMessage("Invalid value \"" + args[3] + "\"");
		else {
			channel.sendMessage("Updated options for " + board.getDisplayName(), board.toEmbedOptions());
			server.setLeaderboard(board.getDisplayName(), board);
		}
	}

}
