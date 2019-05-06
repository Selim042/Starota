package us.myles_selim.starota.raids;

import discord4j.core.object.entity.Channel;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.TextChannel;
import discord4j.core.object.util.Permission;
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
	public Permission requiredUsePermission() {
		return Permission.ADMINISTRATOR;
	}

	@Override
	public String getGeneralUsage() {
		return "[postChannel] [embedChannel]";
	}

	@Override
	public void execute(String[] args, Message message, StarotaServer server, TextChannel channel)
			throws Exception {
		if (args.length < 3) {
			this.sendUsage(server.getPrefix(), channel);
			return;
		}
		Channel targetChannel = Starota.findChannel(server.getDiscordGuild().getId().asLong(), args[1]);
		Channel embedChannel = Starota.findChannel(server.getDiscordGuild().getId().asLong(), args[2]);
		if (targetChannel == null || (embedChannel == null && !args[2].equals("-1"))) {
			channel.createMessage("Channel not found");
			return;
		}
		EBStorage options = server.getData();
		EBStorage channels = options.get(CHANNELS_KEY, EBStorage.class);
		if (channels == null)
			channels = new EBStorage().registerPrimitives();
		if (args[2].equals("-1")) {
			channels.clearKey(targetChannel.getId().asString());
			channel.createMessage("Cleared embed channel for " + targetChannel);
		} else {
			channels.set(targetChannel.getId().asString(), embedChannel.getId().asLong());
			channel.createMessage("Set embed channel for " + targetChannel + " to " + embedChannel);
		}
		options.set(CHANNELS_KEY, channels);
	}

}
