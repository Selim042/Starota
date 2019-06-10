package us.myles_selim.starota.commands;

import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.TextChannel;
import discord4j.core.object.util.Permission;
import discord4j.core.object.util.PermissionSet;
import us.myles_selim.starota.Starota;
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
	public void execute(String[] args, Message message, StarotaServer server, TextChannel channel) {
		channel.createMessage("Pong!")
				.map((m) -> m.edit((e) -> e.setContent("Pong! ("
						+ (m.getTimestamp().toEpochMilli() - message.getTimestamp().toEpochMilli())
						+ "ms)")));
	}

}
