package us.myles_selim.starota.commands;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.Permissions;
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.wrappers.StarotaServer;

public class CommandChangelogChannel extends StarotaCommand {

	public static final String CHANGES_CHANNEL = "changesChannel";

	public CommandChangelogChannel() {
		super("changelogChannel", "Sets the channel for changelogs to be displayed in.");
	}

	@Override
	public Permissions requiredPermission() {
		return Permissions.ADMINISTRATOR;
	}

	@Override
	public void execute(String[] args, IMessage message, StarotaServer server, IChannel channel) {
		if (args.length < 2) {
			channel.sendMessage("**Usage**: " + server.getPrefix() + this.getName() + " <channel>");
			return;
		}

		if (args[1].contentEquals("none")) {
			server.clearValue(CHANGES_CHANNEL);
			channel.sendMessage("Changelog channel cleared.");
			return;
		}

		IChannel target = Starota.findChannel(server.getDiscordGuild().getLongID(), args[1]);
		if (target == null) {
			channel.sendMessage("Channel \"" + args[1] + "\" not found");
			return;
		}
		server.setValue(CHANGES_CHANNEL, target.getLongID());
		channel.sendMessage("Set changelog channel to \"" + target.mention() + "\".");
	}

}
