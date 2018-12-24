package us.myles_selim.starota.leaderboards.commands;

import java.util.List;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.Permissions;
import us.myles_selim.starota.commands.StarotaCommand;
import us.myles_selim.starota.leaderboards.Leaderboard;
import us.myles_selim.starota.wrappers.StarotaServer;

public class CommandEditLeaderboard extends StarotaCommand {

	private static final String OPTIONS = "**Options**:\n"
			+ " - addAlias\n - removeAlias\n - aliases (comma delimited)\n - color";

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
	public Permissions requiredPermission() {
		return Permissions.ADMINISTRATOR;
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
		switch (args[2].toLowerCase()) {
		case "addalias":
			board.addAlias(args[3]);
			break;
		case "removealias":
			board.removeAlias(args[3]);
			break;
		case "aliases":
			String[] aliases = args[3].split(",");
			board.setAliases(aliases);
			break;
		case "decending":
			board.setDecending(Boolean.parseBoolean(args[3]));
			break;
		case "color":
			int color;
			try {
				color = Integer.decode(args[3]);
			} catch (NumberFormatException e) {
				color = -1;
			}
			if (color != -1)
				board.setColor(color);
			break;
		default:
			channel.sendMessage("Unknown option \"" + args[2] + "\", valid options are:\n" + OPTIONS);
			return;
		}
		channel.sendMessage("Updated options for " + board.getDisplayName(), board.toEmbedOptions());
	}

}
