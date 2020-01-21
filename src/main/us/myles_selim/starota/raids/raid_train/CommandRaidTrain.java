package us.myles_selim.starota.raids.raid_train;

import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.MessageChannel;
import us.myles_selim.starota.commands.BotCommand;
import us.myles_selim.starota.commands.registry.CommandException;
import us.myles_selim.starota.wrappers.StarotaServer;

public class CommandRaidTrain extends BotCommand<StarotaServer> {

	public CommandRaidTrain() {
		super("raidTrain", "Starts a raid train.  Use ',' between raid locations.");
	}

	@Override
	public String getGeneralUsage() {
		return "[time] [locations]";
	}

	@Override
	public void execute(String[] args, Message message, StarotaServer server, MessageChannel channel)
			throws CommandException {
		StringBuilder locsS = new StringBuilder();
		for (int i = 2; i < args.length; i++)
			locsS.append(args[i] + " ");
		String[] locsRaw = locsS.toString().split(",");
		String[] locs = new String[locsRaw.length];
		for (int i = 0; i < locsRaw.length; i++)
			locs[i] = locsRaw[i].trim();
		new RaidTrainReactionMessage(args[1], locs).createMessage(channel);
	}

}
