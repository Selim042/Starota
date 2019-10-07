package us.myles_selim.starota.commands;

import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.MessageChannel;
import discord4j.core.object.entity.Role;
import us.myles_selim.starota.commands.registry.CommandException;
import us.myles_selim.starota.commands.registry.java.JavaCommand;
import us.myles_selim.starota.misc.data_types.BotServer;
import us.myles_selim.starota.wrappers.StarotaServer;

public abstract class BotCommand<S extends BotServer> extends JavaCommand {

	public BotCommand(String name) {
		super(name);
	}

	public BotCommand(String name, String description) {
		super(name, description);
	}

	@Override
	public final void execute(String[] args, Message message, Guild guild, MessageChannel channel)
			throws CommandException {
		if (guild == null)
			return;
		execute(args, message, BotServer.getServer(this.getCommandHandler().getDiscordClient(), guild),
				channel);
	}

	public abstract void execute(String[] args, Message message, S server, MessageChannel channel)
			throws CommandException;

	@Override
	public final Role requiredRole(Guild guild) {
		return requiredRole(StarotaServer.getServer(guild));
	}

	public Role requiredRole(StarotaServer server) {
		return null;
	}

}
