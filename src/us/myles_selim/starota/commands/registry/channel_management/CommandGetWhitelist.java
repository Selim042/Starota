package us.myles_selim.starota.commands.registry.channel_management;

import java.util.List;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.Permissions;
import sx.blah.discord.util.EmbedBuilder;
import us.myles_selim.starota.commands.registry.Command;
import us.myles_selim.starota.commands.registry.CommandRegistry;

public class CommandGetWhitelist extends Command {

	public CommandGetWhitelist() {
		super("getWhitelist");
	}

	@Override
	public Permissions requiredPermission() {
		return Permissions.ADMINISTRATOR;
	}

	@Override
	public void execute(String[] args, IMessage message, IGuild guild, IChannel channel) {
		if (args.length < 2) {
			channel.sendMessage("**Usage**: " + CommandRegistry.getPrefix(guild) + this.getName()
					+ " <cmdCategory>");
			return;
		}
		boolean found = false;
		String cmdCategory = args[1];
		for (String cat : CommandRegistry.getAllCategories()) {
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
		List<IChannel> whitelist = ChannelCommandManager.getWhitelist(guild, cmdCategory);
		if (whitelist == null || whitelist.isEmpty())
			channel.sendMessage("The whitelist for \"" + cmdCategory + "\" is empty");
		else {
			EmbedBuilder builder = new EmbedBuilder();
			builder.appendDesc("Whitelist for **" + cmdCategory + "**:\n");
			for (IChannel c : whitelist)
				builder.appendDesc(c.mention() + "\n");
			channel.sendMessage(builder.build());
		}
	}

}
