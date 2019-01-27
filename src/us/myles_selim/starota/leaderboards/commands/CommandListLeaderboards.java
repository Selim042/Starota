package us.myles_selim.starota.leaderboards.commands;

import java.util.List;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.handle.obj.Permissions;
import sx.blah.discord.util.EmbedBuilder;
import us.myles_selim.starota.commands.StarotaCommand;
import us.myles_selim.starota.leaderboards.Leaderboard;
import us.myles_selim.starota.wrappers.StarotaServer;

public class CommandListLeaderboards extends StarotaCommand {

	public CommandListLeaderboards() {
		super("listLeaderboards", "Displays all leaderboards.");
	}

	@Override
	public List<String> getAliases() {
		List<String> aliases = super.getAliases();
		aliases.add("listLb");
		return aliases;
	}

	@Override
	public void execute(String[] args, IMessage message, StarotaServer server, IChannel channel)
			throws Exception {
		IUser author = message.getAuthor();
		EmbedBuilder builder = new EmbedBuilder();
		List<Leaderboard> boards = server.getLeaderboards();
		builder.withTitle("Leaderboards on " + server.getDiscordGuild().getName() + ":");
		boolean isAdmin = author.getPermissionsForGuild(server.getDiscordGuild())
				.contains(Permissions.ADMINISTRATOR);
		for (Leaderboard b : boards)
			builder.appendDesc(
					b.getDisplayName() + (isAdmin && !b.isActive() ? " _inactive_" : "") + "\n");
		channel.sendMessage(builder.build());
	}

}
