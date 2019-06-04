package us.myles_selim.starota.assistants.points.commands;

import java.util.List;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import us.myles_selim.starota.assistants.points.PointServer;
import us.myles_selim.starota.commands.BotCommand;

public class CommandGetQuest extends BotCommand<PointServer> {

	public CommandGetQuest() {
		super("getQuest", "Gets the current quests.");
	}

	@Override
	public List<String> getAliases() {
		List<String> aliases = super.getAliases();
		aliases.add("quest");
		return aliases;
	}

	@Override
	public void execute(String[] args, IMessage message, PointServer server, IChannel channel)
			throws Exception {
		super.execute(args, message, server, channel);
	}

}
