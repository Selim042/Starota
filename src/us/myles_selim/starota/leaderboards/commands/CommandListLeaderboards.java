package us.myles_selim.starota.leaderboards.commands;

import java.util.List;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.handle.obj.Permissions;
import sx.blah.discord.util.EmbedBuilder;
import us.myles_selim.starota.commands.registry.java.JavaCommand;
import us.myles_selim.starota.leaderboards.Leaderboard;
import us.myles_selim.starota.leaderboards.LeaderboardManager;

public class CommandListLeaderboards extends JavaCommand {

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
	public void execute(String[] args, IMessage message, IGuild guild, IChannel channel)
			throws Exception {
		IUser author = message.getAuthor();
		EmbedBuilder builder = new EmbedBuilder();
		List<Leaderboard> boards = LeaderboardManager.getLeaderboards(guild);
		builder.withTitle("Leaderboards on " + guild.getName() + ":");
		boolean isAdmin = author.getPermissionsForGuild(guild).contains(Permissions.ADMINISTRATOR);
		for (Leaderboard b : boards)
			builder.appendDesc(
					b.getDisplayName() + (isAdmin && !b.isActive() ? " _inactive_" : "") + "\n");
		channel.sendMessage(builder.build());
	}

}
