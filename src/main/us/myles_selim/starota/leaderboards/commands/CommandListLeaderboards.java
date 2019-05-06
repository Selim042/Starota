package us.myles_selim.starota.leaderboards.commands;

import java.util.List;

import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.TextChannel;
import discord4j.core.object.entity.User;
import discord4j.core.object.util.Permission;
import discord4j.core.object.util.PermissionSet;
import us.myles_selim.starota.commands.StarotaCommand;
import us.myles_selim.starota.leaderboards.Leaderboard;
import us.myles_selim.starota.misc.data_types.EmbedBuilder;
import us.myles_selim.starota.wrappers.StarotaServer;

public class CommandListLeaderboards extends StarotaCommand {

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
	public void execute(String[] args, Message message, StarotaServer server, TextChannel channel)
			throws Exception {
		User author = message.getAuthor().get();
		EmbedBuilder builder = new EmbedBuilder();
		List<Leaderboard> boards = server.getLeaderboards();
		builder.setTitle("Leaderboards on " + server.getDiscordGuild().getName() + ":");
		boolean isAdmin = author.asMember(server.getDiscordGuild().getId()).block().getBasePermissions()
				.block().contains(Permission.ADMINISTRATOR);
		for (Leaderboard b : boards)
			builder.appendDesc(
					b.getDisplayName() + (isAdmin && !b.isActive() ? " _inactive_" : "") + "\n");
		channel.createEmbed(builder.build());
	}

}
