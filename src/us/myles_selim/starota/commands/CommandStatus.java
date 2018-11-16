package us.myles_selim.starota.commands;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.handle.obj.Permissions;
import sx.blah.discord.util.EmbedBuilder;
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.commands.registry.java.JavaCommand;
import us.myles_selim.starota.webserver.WebServer;

public class CommandStatus extends JavaCommand {

	public CommandStatus() {
		super("status");
	}

	@Override
	public Permissions requiredPermission() {
		return Permissions.ADMINISTRATOR;
	}

	@Override
	public void execute(String[] args, IMessage message, IGuild guild, IChannel channel) {
		IUser sender = message.getAuthor();
		if (sender.getPermissionsForGuild(guild).contains(Permissions.ADMINISTRATOR)) {
			EmbedBuilder builder = new EmbedBuilder();
			builder.withTitle(Starota.getClient().getOurUser().getDisplayName(guild) + " Status");
			builder.appendField("Discord:", Starota.getClient().isReady() ? "Online" : "Offline", true);
			builder.appendField("Webserver:", WebServer.isRunning() ? "Online" : "Offline", true);
			channel.sendMessage(builder.build());
		}
	}

}
