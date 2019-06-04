package us.myles_selim.starota.commands;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IRole;
import us.myles_selim.starota.commands.registry.java.JavaCommand;
import us.myles_selim.starota.misc.data_types.BotServer;
import us.myles_selim.starota.wrappers.StarotaServer;

public class BotCommand<S extends BotServer> extends JavaCommand {

	public BotCommand(String name) {
		super(name);
	}

	public BotCommand(String name, String description) {
		super(name, description);
	}

	@Override
	public final void execute(String[] args, IMessage message, IGuild guild, IChannel channel)
			throws Exception {
		if (guild == null)
			return;
		execute(args, message, BotServer.getServer(this.getCommandHandler().getDiscordClient(), guild),
				channel);
	}

	public void execute(String[] args, IMessage message, S server, IChannel channel) throws Exception {}

	@Override
	public final IRole requiredRole(IGuild guild) {
		return requiredRole(StarotaServer.getServer(guild));
	}

	public IRole requiredRole(StarotaServer server) {
		return null;
	}

}
