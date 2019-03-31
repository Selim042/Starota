package us.myles_selim.starota.leek_duck.events;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import us.myles_selim.starota.commands.StarotaCommand;
import us.myles_selim.starota.leek_duck.LeekDuckData;
import us.myles_selim.starota.wrappers.StarotaServer;

public class CommandEvents extends StarotaCommand {

	public CommandEvents() {
		super("events", "Displays upcoming events.");
	}

	@Override
	public void execute(String[] args, IMessage message, StarotaServer server, IChannel channel)
			throws Exception {
		if (!LeekDuckData.areEventsLoaded())
			new EventReactionMessage().editMessage(channel,
					channel.sendMessage(LeekDuckData.LOADING_EMBED));
		else
			new EventReactionMessage().sendMessage(channel);
	}

}
