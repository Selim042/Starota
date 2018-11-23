package us.myles_selim.starota.commands.registry.channel_management;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.Permissions;
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.commands.registry.PrimaryCommandHandler;
import us.myles_selim.starota.commands.registry.java.JavaCommand;
import us.myles_selim.starota.commands.registry.java.JavaCommandHandler;

public class CommandRemoveChannelWhitelist extends JavaCommand {

	public CommandRemoveChannelWhitelist() {
		super("removeChannelWhitelist");
	}

	@Override
	public Permissions requiredPermission() {
		return Permissions.ADMINISTRATOR;
	}

	@Override
	public void execute(String[] args, IMessage message, IGuild guild, IChannel channel) {
		if (args.length < 3) {
			channel.sendMessage("**Usage**: " + PrimaryCommandHandler.getPrefix(guild) + this.getName()
					+ " <cmdCategory> <channel>");
			return;
		}
		boolean found = false;
		String cmdCategory = args[1];
		for (String cat : JavaCommandHandler.getAllCategories()) {
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
		IChannel target = Starota.findChannel(guild.getLongID(), args[2]);
		if (target == null) {
			channel.sendMessage("Channel \"" + args[2] + "\" not found");
			return;
		}
		if (ChannelCommandManager.removeWhitelist(guild, cmdCategory, target))
			channel.sendMessage(
					"Removed " + target.mention() + " to whitelist for \"" + cmdCategory + "\".");
		else
			channel.sendMessage(
					"Channel " + target.mention() + " is not in the whitelist \"" + cmdCategory + "\".");
	}

}
