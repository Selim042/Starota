package us.myles_selim.starota.commands;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IRole;
import us.myles_selim.starota.commands.registry.java.JavaCommand;
import us.myles_selim.starota.wrappers.StarotaServer;

public class StarotaCommand extends JavaCommand {

	public StarotaCommand(String name) {
		super(name);
	}

	public StarotaCommand(String name, String description) {
		super(name, description);
	}

	@Override
	public final void execute(String[] args, IMessage message, IGuild guild, IChannel channel)
			throws Exception {
		if (guild == null)
			return;
		execute(args, message, StarotaServer.getServer(guild), channel);
	}

	public void execute(String[] args, IMessage message, StarotaServer server, IChannel channel)
			throws Exception {}

	@Override
	public final IRole requiredRole(IGuild guild) {
		return requiredRole(StarotaServer.getServer(guild));
	}

	public IRole requiredRole(StarotaServer server) {
		return null;
	}

}
