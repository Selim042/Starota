package us.myles_selim.starota.profiles.commands;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.EmbedBuilder;
import us.myles_selim.starota.commands.StarotaCommand;
import us.myles_selim.starota.wrappers.StarotaServer;

public class CommandProfileHelp extends StarotaCommand {

	public CommandProfileHelp() {
		super("profileHelp", "Displays profile help information.");
	}

	@Override
	public void execute(String[] args, IMessage message, StarotaServer server, IChannel channel) {
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
		channel.sendMessage(builder.build());
	}

}
