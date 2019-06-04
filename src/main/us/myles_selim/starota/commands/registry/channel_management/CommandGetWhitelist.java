package us.myles_selim.starota.commands.registry.channel_management;

import java.util.EnumSet;
import java.util.List;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.Permissions;
import sx.blah.discord.util.EmbedBuilder;
import us.myles_selim.starota.commands.BotCommand;
import us.myles_selim.starota.wrappers.StarotaServer;

public class CommandGetWhitelist extends BotCommand<StarotaServer> {

	public CommandGetWhitelist() {
		super("getWhitelist");
	}

	@Override
	public Permissions requiredUsePermission() {
		return Permissions.ADMINISTRATOR;
	}

	@Override
	public EnumSet<Permissions> getCommandPermissions() {
		return EnumSet.of(Permissions.SEND_MESSAGES, Permissions.EMBED_LINKS);
	}

	@Override
	public void execute(String[] args, IMessage message, StarotaServer server, IChannel channel) {
		if (args.length < 2) {
			channel.sendMessage("**Usage**: " + server.getPrefix() + this.getName() + " <cmdCategory>");
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
		List<IChannel> whitelist = ChannelCommandManager.getWhitelist(server, cmdCategory);
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
