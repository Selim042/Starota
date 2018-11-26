package us.myles_selim.starota.leaderboards.commands;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import us.myles_selim.starota.commands.registry.java.JavaCommand;

public class CommandUpdateLeaderboard extends JavaCommand {

	public CommandUpdateLeaderboard() {
		super("updateLeaderboard", "Updates your leaderboard statistic.");
	}

	@Override
	public void execute(String[] args, IMessage message, IGuild guild, IChannel channel) {

	}

}
