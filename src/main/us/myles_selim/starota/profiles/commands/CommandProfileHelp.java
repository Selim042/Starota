package us.myles_selim.starota.profiles.commands;

import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.MessageChannel;
import discord4j.core.object.util.Permission;
import discord4j.core.object.util.PermissionSet;
import us.myles_selim.starota.commands.BotCommand;
import us.myles_selim.starota.commands.registry.CommandException;
import us.myles_selim.starota.misc.utils.EmbedBuilder;
import us.myles_selim.starota.wrappers.StarotaServer;

public class CommandProfileHelp extends BotCommand<StarotaServer> {

	public CommandProfileHelp() {
		super("profileHelp", "Displays profile help information.");
	}

	@Override
	public PermissionSet getCommandPermission() {
		return PermissionSet.of(Permission.SEND_MESSAGES, Permission.EMBED_LINKS);
	}

	@Override
	public void execute(String[] args, Message message, StarotaServer server, MessageChannel channel)
			throws CommandException {
		EmbedBuilder builder = new EmbedBuilder();
		builder.withTitle("Profile Help");
		String prefix = server.getPrefix();
		builder.appendDesc(" - To assign yourself a profile, you can use the command \"**" + prefix
				+ "sRegister**\".\n" + " - To view someone's profile, you can use the command \"**"
				+ prefix + "profile**\".\n"
				+ " - To update parts of your profile, you can use the command \"**" + prefix
				+ "updateProfile**\".  This allows you to update your real name, level, and more.\n\n"
				+ "The \"**" + prefix
				+ "help [commandName]**\" command is always helpful if you are unsure.");
		channel.createEmbed(builder.build()).block();
	}

}
