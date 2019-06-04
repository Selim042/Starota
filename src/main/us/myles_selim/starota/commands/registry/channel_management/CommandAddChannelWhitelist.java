package us.myles_selim.starota.commands.registry.channel_management;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.Permissions;
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.commands.BotCommand;
import us.myles_selim.starota.wrappers.StarotaServer;

public class CommandAddChannelWhitelist extends BotCommand<StarotaServer> {

	public CommandAddChannelWhitelist() {
		super("addChannelWhitelist");
	}

	@Override
	public Permissions requiredUsePermission() {
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
		ChannelCommandManager.addWhitelist(server, cmdCategory, target);
		channel.sendMessage("Added " + target.mention() + " to whitelist for \"" + cmdCategory + "\".");
	}

}
