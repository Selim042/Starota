package us.myles_selim.starota.commands;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import us.myles_selim.starota.wrappers.StarotaServer;

public class CommandTest extends StarotaCommand {

	public CommandTest() {
		super("test");
	}

	@Override
	public void execute(String[] args, IMessage message, StarotaServer server, IChannel channel) {

	}

}
