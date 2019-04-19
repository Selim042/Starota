package us.myles_selim.starota.raids;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.Permissions;
import us.myles_selim.ebs.EBStorage;
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.commands.StarotaCommand;
import us.myles_selim.starota.wrappers.StarotaServer;

public class CommandSetRaidEChannel extends StarotaCommand {

	public static final String CHANNELS_KEY = "raid_channels";

	public CommandSetRaidEChannel() {
		super("setRaidChannel",
				"Sets the channel that raid embeds should be posted to for a specific channel. "
						+ "Set to -1 to set to normal functionality.");
	}

	@Override
	public Permissions requiredUsePermission() {
		return Permissions.ADMINISTRATOR;
	}

	@Override
	public String getGeneralUsage() {
		return "[postChannel] [embedChannel]";
	}

	@Override
	public void execute(String[] args, IMessage message, StarotaServer server, IChannel channel)
			throws Exception {
		if (args.length < 3) {
			this.sendUsage(server.getPrefix(), channel);
			return;
		}
		IChannel targetChannel = Starota.findChannel(server.getDiscordGuild().getLongID(), args[1]);
		IChannel embedChannel = Starota.findChannel(server.getDiscordGuild().getLongID(), args[2]);
		if (targetChannel == null || (embedChannel == null && !args[2].equals("-1"))) {
			channel.sendMessage("Channel not found");
			return;
		}
		EBStorage options = server.getData();
		EBStorage channels = options.get(CHANNELS_KEY, EBStorage.class);
		if (channels == null)
			channels = new EBStorage().registerPrimitives();
		if (args[2].equals("-1")) {
			channels.clearKey(targetChannel.getStringID());
			channel.sendMessage("Cleared embed channel for " + targetChannel);
		} else {
			channels.set(targetChannel.getStringID(), embedChannel.getLongID());
			channel.sendMessage("Set embed channel for " + targetChannel + " to " + embedChannel);
		}
		options.set(CHANNELS_KEY, channels);
	}

}
