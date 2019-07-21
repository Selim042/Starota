package us.myles_selim.starota.leek_duck.events;

import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.MessageChannel;
import discord4j.core.object.entity.TextChannel;
import discord4j.core.object.util.Permission;
import discord4j.core.object.util.PermissionSet;
import us.myles_selim.starota.commands.BotCommand;
import us.myles_selim.starota.commands.registry.CommandException;
import us.myles_selim.starota.wrappers.StarotaServer;

public class CommandEvents extends BotCommand<StarotaServer> {

	public CommandEvents() {
		super("events", "Displays upcoming events.");
	}

	@Override
	public PermissionSet getCommandPermission() {
		return PermissionSet.of(Permission.SEND_MESSAGES, Permission.EMBED_LINKS,
				Permission.USE_EXTERNAL_EMOJIS, Permission.ADD_REACTIONS, Permission.MANAGE_MESSAGES);
	}

	@Override
	public void execute(String[] args, Message message, StarotaServer server, MessageChannel channel)
			throws CommandException {
		if (!EventData.areEventsLoaded())
			new EventReactionMessage().editMessage((TextChannel) channel,
					channel.createEmbed(EventData.LOADING_EMBED).block());
		else
			new EventReactionMessage().createMessage((TextChannel) channel);
	}

}
