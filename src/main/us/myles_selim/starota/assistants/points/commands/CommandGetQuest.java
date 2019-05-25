package us.myles_selim.starota.assistants.points.commands;

import java.util.List;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import us.myles_selim.starota.commands.StarotaCommand;
import us.myles_selim.starota.wrappers.StarotaServer;

public class CommandGetQuest extends StarotaCommand {

	public CommandGetQuest() {
		super("getQuest", "Gets the current quest.");
	}

	@Override
	public List<String> getAliases() {
		List<String> aliases = super.getAliases();
		aliases.add("quest");
		return aliases;
	}

	@Override
	public void execute(String[] args, IMessage message, StarotaServer server, IChannel channel)
			throws Exception {
		super.execute(args, message, server, channel);
	}

}
