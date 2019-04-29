package us.myles_selim.starota.leek_duck.events;

import java.util.EnumSet;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.Permissions;
import us.myles_selim.starota.commands.StarotaCommand;
import us.myles_selim.starota.wrappers.StarotaServer;

public class CommandEvents extends StarotaCommand {

	public CommandEvents() {
		super("events", "Displays upcoming events.");
	}

	@Override
	public EnumSet<Permissions> getCommandPermissions() {
		return EnumSet.of(Permissions.SEND_MESSAGES, Permissions.EMBED_LINKS,
				Permissions.USE_EXTERNAL_EMOJIS, Permissions.ADD_REACTIONS, Permissions.MANAGE_MESSAGES);
	}

	@Override
	public void execute(String[] args, IMessage message, StarotaServer server, IChannel channel)
			throws Exception {
		if (!EventData.areEventsLoaded())
			new EventReactionMessage().editMessage(channel,
					channel.sendMessage(EventData.LOADING_EMBED));
		else
			new EventReactionMessage().sendMessage(channel);
	}

}
