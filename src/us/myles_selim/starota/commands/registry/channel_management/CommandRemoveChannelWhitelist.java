package us.myles_selim.starota.commands.registry.channel_management;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.Permissions;
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.commands.StarotaCommand;
import us.myles_selim.starota.wrappers.StarotaServer;

public class CommandRemoveChannelWhitelist extends StarotaCommand {

	public CommandRemoveChannelWhitelist() {
		super("removeChannelWhitelist");
	}

	@Override
	public Permissions requiredPermission() {
		return Permissions.ADMINISTRATOR;
	}

	@Override
	public void execute(String[] args, IMessage message, StarotaServer server, IChannel channel) {
		if (args.length < 3) {
			channel.sendMessage(
					"**Usage**: " + server.getPrefix() + this.getName() + " <cmdCategory> <channel>");
			return;
		}
		boolean found = false;
		String cmdCategory = args[1];
		for (String cat : this.getCommandHandler().getAllCategories(server.getDiscordGuild())) {
			if (cmdCategory.equalsIgnoreCase(cat)) {
				cmdCategory = cat;
				found = true;
				break;
			}
		}
		if (cmdCategory == null || !found) {
			channel.sendMessage("Unknown category \"" + args[1] + "\"");
			return;
		}
		IChannel target = Starota.findChannel(server.getDiscordGuild().getLongID(), args[2]);
		if (target == null) {
			channel.sendMessage("Channel \"" + args[2] + "\" not found");
			return;
		}
		if (ChannelCommandManager.removeWhitelist(server, cmdCategory, target))
			channel.sendMessage(
					"Removed " + target.mention() + " to whitelist for \"" + cmdCategory + "\".");
		else
			channel.sendMessage(
					"Channel " + target.mention() + " is not in the whitelist \"" + cmdCategory + "\".");
	}

}
