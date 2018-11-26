package us.myles_selim.starota.leaderboards.commands;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.Permissions;
import us.myles_selim.starota.commands.registry.java.JavaCommand;

public class CommandEditLeaderboard extends JavaCommand {

	public CommandEditLeaderboard() {
		super("editLeaderboard",
				"Edits the properties of an existing leaderboard or creates a new one.");
	}

	@Override
	public Permissions requiredPermission() {
		return Permissions.ADMINISTRATOR;
	}

	@Override
	public void execute(String[] args, IMessage message, IGuild guild, IChannel channel) {

	}

}
