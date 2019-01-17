package us.myles_selim.starota.raids;

import java.util.List;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import us.myles_selim.starota.commands.StarotaCommand;
import us.myles_selim.starota.wrappers.StarotaServer;

public class CommandRaid extends StarotaCommand {

	public CommandRaid() {
		super("raid1", "Makes a new raid post.");
	}

	@Override
	public List<String> getAliases() {
		List<String> aliases = super.getAliases();
		for (int i = 2; i <= 5; i++)
			aliases.add("raid" + i);
		return aliases;
	}

	@Override
	public String getGeneralUsage() {
		return "[time] [location]";
	}

	@Override
	public void execute(String[] args, IMessage message, StarotaServer server, IChannel channel)
			throws Exception {
		if (args.length < 3) {
			channel.sendMessage("**Usage**: " + server.getPrefix() + args[0] + " " + getGeneralUsage());
			return;
		}
		String location = "";
		for (int i = 2; i < args.length; i++)
			location += args[i] + " ";
		int tier;
		switch (args[0].toLowerCase()) {
		case "raid1":
			tier = 1;
			break;
		case "raid2":
			tier = 2;
			break;
		case "raid3":
			tier = 3;
			break;
		case "raid4":
			tier = 4;
			break;
		case "raid5":
			tier = 5;
			break;
		default:
			channel.sendMessage("Failed to get raid tier");
			return;
		}
		new RaidReactionMessage(tier, args[1], location).sendMessage(channel);
	}

}