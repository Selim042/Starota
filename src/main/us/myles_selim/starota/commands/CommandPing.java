package us.myles_selim.starota.commands;

import java.util.EnumSet;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.Permissions;
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.wrappers.StarotaServer;

public class CommandPing extends StarotaCommand {

	public CommandPing() {
		super("ping", "Gets " + Starota.BOT_NAME + "s approx. ping to the Discord API.");
	}

	@Override
	public EnumSet<Permissions> getCommandPermissions() {
		return EnumSet.of(Permissions.SEND_MESSAGES);
	}

	@Override
	public void execute(String[] args, IMessage message, StarotaServer server, IChannel channel) {
		IMessage msg = channel.sendMessage("Pong!");
		msg.edit("Pong! (" + (msg.getTimestamp().toEpochMilli() - message.getTimestamp().toEpochMilli())
				+ "ms)");
	}

}
