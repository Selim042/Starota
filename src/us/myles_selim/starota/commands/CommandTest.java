package us.myles_selim.starota.commands;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import us.myles_selim.starota.commands.registry.java.JavaCommand;
import us.myles_selim.starota.leaderboard.Leaderboard;
import us.myles_selim.starota.leaderboard.LeaderboardEntry;

public class CommandTest extends JavaCommand {

	public CommandTest() {
		super("test");
	}

	@Override
	public void execute(String[] args, IMessage message, IGuild guild, IChannel channel) {
		// EmbedBuilder builder = new EmbedBuilder();
		// builder.withTitle("Total XP Leaderboard");
		// builder.appendDesc("**1**) Selim: 12,728,524\n");
		// builder.appendDesc("**2**) JimmyBanana42: 2,066,845\n");
		// builder.appendDesc("\n**Page**: 1/1");
		// channel.sendMessage(builder.build());
		Leaderboard testBoard = new Leaderboard(guild, "Total XP", true);
		testBoard.updateEntry(new LeaderboardEntry(134855940938661889L, 12728524L));
		testBoard.updateEntry(new LeaderboardEntry(279266090494722049L, 1234567L));
		channel.sendMessage(testBoard.toEmbed());
	}

}
