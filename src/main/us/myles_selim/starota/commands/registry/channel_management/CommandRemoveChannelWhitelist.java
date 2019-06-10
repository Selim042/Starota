package us.myles_selim.starota.commands.registry.channel_management;

import discord4j.core.object.entity.Channel;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.TextChannel;
import discord4j.core.object.util.Permission;
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.commands.BotCommand;
import us.myles_selim.starota.wrappers.StarotaServer;

public class CommandRemoveChannelWhitelist extends BotCommand<StarotaServer> {

	public CommandRemoveChannelWhitelist() {
		super("removeChannelWhitelist");
	}

	@Override
	public Permission requiredUsePermission() {
		return Permission.ADMINISTRATOR;
	}

	@Override
	public void execute(String[] args, Message message, StarotaServer server, TextChannel channel) {
		if (args.length < 3) {
			channel.createMessage(
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
			channel.createMessage("Unknown category \"" + args[1] + "\"");
			return;
		}
		Channel target = Starota.findChannel(server.getDiscordGuild().getId().asLong(), args[2]);
		if (target == null) {
			channel.createMessage("Channel \"" + args[2] + "\" not found");
			return;
		}
		if (ChannelCommandManager.removeWhitelist(server, cmdCategory, target))
			channel.createMessage(
					"Removed " + target.getMention() + " to whitelist for \"" + cmdCategory + "\".");
		else
			channel.createMessage(
					"Channel " + target.getMention() + " is not in the whitelist \"" + cmdCategory + "\".");
	}

}
