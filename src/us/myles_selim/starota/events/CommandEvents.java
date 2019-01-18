package us.myles_selim.starota.events;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import us.myles_selim.starota.commands.StarotaCommand;
import us.myles_selim.starota.wrappers.StarotaServer;

public class CommandEvents extends StarotaCommand {

	public CommandEvents() {
		super("events", "Displays upcoming events.");
	}

	@Override
	public void execute(String[] args, IMessage message, StarotaServer server, IChannel channel)
			throws Exception {
		new EventReactionMessage().sendMessage(channel);
	}

}
