package us.myles_selim.starota.commands;

import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.Role;
import discord4j.core.object.entity.TextChannel;
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
	public final void execute(String[] args, Message message, Guild guild, TextChannel channel)
			throws Exception {
		if (guild == null)
			return;
		execute(args, message, StarotaServer.getServer(guild), channel);
	}

	public void execute(String[] args, Message message, StarotaServer server, TextChannel channel)
			throws Exception {}

	@Override
	public final Role requiredRole(Guild guild) {
		return requiredRole(StarotaServer.getServer(guild));
	}

	public Role requiredRole(StarotaServer server) {
		return null;
	}

}
