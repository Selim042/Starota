package us.myles_selim.starota.commands;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.Permissions;
import us.myles_selim.starota.ServerOptions;
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.commands.registry.Command;
import us.myles_selim.starota.commands.registry.CommandRegistry;

public class CommandChangelogChannel extends Command {

	public static final String CHANGES_CHANNEL = "changesChannel";

	public CommandChangelogChannel() {
		super("changelogChannel", "Sets the channel for changelogs to be displayed in.");
	}

	@Override
	public Permissions requiredPermission() {
		return Permissions.ADMINISTRATOR;
	}

	@Override
	public void execute(String[] args, IMessage message, IGuild guild, IChannel channel) {
		if (args.length < 2) {
			channel.sendMessage(
					"**Usage**: " + CommandRegistry.getPrefix(guild) + this.getName() + " <channel>");
			return;
		}

		if (args[1].contentEquals("none")) {
			ServerOptions.clearValue(guild, CHANGES_CHANNEL);
			channel.sendMessage("Changelog channel cleared.");
			return;
		}

		IChannel target = Starota.findChannel(guild.getLongID(), args[1]);
		if (target == null) {
			channel.sendMessage("Channel \"" + args[1] + "\" not found");
			return;
		}
		ServerOptions.setValue(guild, CHANGES_CHANNEL, target.getLongID());
		channel.sendMessage("Set changelog channel to \"" + target.mention() + "\".");
	}

}
