package us.myles_selim.starota.commands.registry.channel_management;

import java.util.List;

import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.MessageChannel;
import discord4j.core.object.entity.TextChannel;
import discord4j.core.object.util.Permission;
import discord4j.core.object.util.PermissionSet;
import us.myles_selim.starota.commands.BotCommand;
import us.myles_selim.starota.misc.utils.EmbedBuilder;
import us.myles_selim.starota.wrappers.StarotaServer;

public class CommandGetWhitelist extends BotCommand<StarotaServer> {

	public CommandGetWhitelist() {
		super("getWhitelist");
	}

	@Override
	public Permission requiredUsePermission() {
		return Permission.ADMINISTRATOR;
	}

	@Override
	public PermissionSet getCommandPermission() {
		return PermissionSet.of(Permission.SEND_MESSAGES, Permission.EMBED_LINKS);
	}

	@Override
	public void execute(String[] args, Message message, StarotaServer server, MessageChannel channel) {
		if (args.length < 2) {
			channel.createMessage(
					"**Usage**: " + server.getPrefix() + this.getName() + " <cmdCategory>");
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
		List<TextChannel> whitelist = ChannelCommandManager.getWhitelist(server, cmdCategory);
		if (whitelist == null || whitelist.isEmpty())
			channel.createMessage("The whitelist for \"" + cmdCategory + "\" is empty");
		else {
			EmbedBuilder builder = new EmbedBuilder();
			builder.appendDesc("Whitelist for **" + cmdCategory + "**:\n");
			for (TextChannel c : whitelist)
				builder.appendDesc(c.getMention() + "\n");
			channel.createEmbed(builder.build());
		}
	}

}
