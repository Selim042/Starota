package us.myles_selim.starota.commands;

import java.util.List;

import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
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
	public Permissions requiredUsePermission() {
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

		IChannel target = findChannel(server.getDiscordGuild(), args[1]);
		if (target == null) {
			channel.sendMessage("Channel \"" + args[1] + "\" not found");
			return;
		}
		server.setValue(CHANGES_CHANNEL, target.getLongID());
		channel.sendMessage("Set changelog channel to \"" + target.mention() + "\".");
	}

	private static IChannel findChannel(IGuild guild, String name) {
		if (name == null)
			return null;
		if (name.matches("<#\\d*>")) {
			try {
				long id = Long.parseLong(name.substring(2, name.length() - 1));
				IChannel idChannel = guild.getChannelByID(id);
				if (idChannel != null)
					return idChannel;
			} catch (NumberFormatException e) {}
		}
		name = name.substring(1);
		IDiscordClient client = Starota.getClient();
		List<IChannel> channels;
		if (guild != null)
			channels = guild.getChannelsByName(name);
		else {
			channels = client.getChannels(true);
			for (IChannel ch : channels)
				if (ch.getName().equalsIgnoreCase(name))
					return ch;
		}
		if (guild != null)
			for (IChannel ch : guild.getChannels())
				if (ch.getName().equalsIgnoreCase(name))
					return ch;
		return null;
	}

}
