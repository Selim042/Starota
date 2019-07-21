package us.myles_selim.starota.commands;

import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.MessageChannel;
import discord4j.core.object.util.Permission;
import discord4j.core.object.util.PermissionSet;
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.commands.registry.CommandException;
import us.myles_selim.starota.wrappers.StarotaServer;

public class CommandPing extends BotCommand<StarotaServer> {

	public CommandPing() {
		super("ping", "Gets " + Starota.BOT_NAME + "s approx. ping to the Discord API.");
	}

	@Override
	public PermissionSet getCommandPermission() {
		return PermissionSet.of(Permission.SEND_MESSAGES);
	}

	@Override
	public void execute(String[] args, Message message, StarotaServer server, MessageChannel channel)
			throws CommandException {
		Message msg = channel.createMessage("Pong!").block();
		msg.edit((m) -> m.setContent("Pong! ("
				+ (msg.getTimestamp().toEpochMilli() - message.getTimestamp().toEpochMilli()) + "ms)"))
				.block();
	}

}
