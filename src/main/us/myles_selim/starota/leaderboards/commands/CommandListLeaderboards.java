package us.myles_selim.starota.leaderboards.commands;

import java.util.List;

import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.MessageChannel;
import discord4j.core.object.util.Permission;
import discord4j.core.object.util.PermissionSet;
import us.myles_selim.starota.commands.BotCommand;
import us.myles_selim.starota.commands.registry.CommandException;
import us.myles_selim.starota.leaderboards.Leaderboard;
import us.myles_selim.starota.misc.utils.EmbedBuilder;
import us.myles_selim.starota.wrappers.StarotaServer;

public class CommandListLeaderboards extends BotCommand<StarotaServer> {

	public CommandListLeaderboards() {
		super("listLeaderboards", "Displays all leaderboards.");
	}

	@Override
	public PermissionSet getCommandPermission() {
		return PermissionSet.of(Permission.SEND_MESSAGES, Permission.EMBED_LINKS);
	}

	@Override
	public List<String> getAliases() {
		List<String> aliases = super.getAliases();
		aliases.add("listLb");
		return aliases;
	}

	@Override
	public void execute(String[] args, Message message, StarotaServer server, MessageChannel channel)
			throws CommandException {
		Member author = message.getAuthorAsMember().block();
		EmbedBuilder builder = new EmbedBuilder();
		List<Leaderboard> boards = server.getLeaderboards();
		builder.withTitle("Leaderboards on " + server.getDiscordGuild().getName() + ":");
		boolean isAdmin = author.getBasePermissions().block().contains(Permission.ADMINISTRATOR);
		for (Leaderboard b : boards)
			builder.appendDesc(
					b.getDisplayName() + (isAdmin && !b.isActive() ? " _inactive_" : "") + "\n");
		channel.createEmbed(builder.build()).block();
	}

}
