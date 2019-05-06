package us.myles_selim.starota.leaderboards.commands;

import java.util.List;

import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.TextChannel;
import discord4j.core.object.util.Permission;
import discord4j.core.object.util.PermissionSet;
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
	public void execute(String[] args, Message message, StarotaServer server, TextChannel channel) {
		if (args.length == 2 && args[1].equalsIgnoreCase("options")) {
			channel.createMessage(OPTIONS);
			return;
		}
		if (args.length == 3 && args[2].equalsIgnoreCase("view")) {
			Leaderboard board = server.getLeaderboard(args[1]);
			if (board == null)
				channel.createMessage("Leaderboard \"" + args[1] + "\" not found");
			else
				channel.createEmbed(board.toEmbedOptions());
			return;
		}
		if (args.length < 4) {
			channel.createMessage(
					"**Usage**: " + server.getPrefix() + getName() + " " + getGeneralUsage());
			return;
		}
		Leaderboard board = server.getLeaderboard(args[1]);
		if (board == null) {
			channel.createMessage("Leaderboard \"" + args[1] + "\" not found");
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
			channel.createMessage("Unknown option \"" + args[2] + "\", valid options are:\n" + OPTIONS);
			return;
		}
		channel.createMessage((m) -> m.setContent("Updated options for " + board.getDisplayName())
				.setEmbed(board.toEmbedOptions()));
		server.setLeaderboard(board.getDisplayName(), board);
	}

}
