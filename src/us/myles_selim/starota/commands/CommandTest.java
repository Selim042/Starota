package us.myles_selim.starota.commands;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import us.myles_selim.starota.commands.registry.java.JavaCommand;
import us.myles_selim.starota.leaderboards.Leaderboard;
import us.myles_selim.starota.leaderboards.LeaderboardEntry;

public class CommandTest extends JavaCommand {

	public CommandTest() {
		super("test");
	}

	@Override
	public void execute(String[] args, IMessage message, IGuild guild, IChannel channel) {
		int page = 0;
		if (args.length > 1)
			try {
				page = Integer.parseInt(args[1]) - 1;
			} catch (NumberFormatException e) {}
		Leaderboard testBoard = new Leaderboard(guild, "Total XP", true);
		testBoard.updateEntry(new LeaderboardEntry(134855940938661889L, 12728524L));
		testBoard.updateEntry(new LeaderboardEntry(279266090494722049L, 1234567L));
		testBoard.updateEntry(new LeaderboardEntry(95509102796480512L, 5678L));
		testBoard.updateEntry(new LeaderboardEntry(504088307148521475L, 999999999L));
		testBoard.updateEntry(new LeaderboardEntry(292678063324659732L, 56399923L));
		testBoard.updateEntry(new LeaderboardEntry(286292930640216064L, 53L));
		channel.sendMessage(testBoard.toEmbed(page));
	}

}
