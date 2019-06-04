package us.myles_selim.starota.commands.tutorial.commands;

import java.util.List;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import us.myles_selim.starota.commands.BotCommand;
import us.myles_selim.starota.commands.tutorial.TutorialRegistry;
import us.myles_selim.starota.misc.utils.StarotaConstants;
import us.myles_selim.starota.wrappers.StarotaServer;

public class CommandTutorial extends BotCommand<StarotaServer> {

	private static final long TUTORIAL_CHANNEL_ID = 569938166988013588L;

	public CommandTutorial() {
		super("tutStart", "Starts the tutorial.");
	}

	@Override
	public List<String> getAliases() {
		List<String> aliases = super.getAliases();
		aliases.add("tutorialStart");
		aliases.add("tutorial");
		return aliases;
	}

	@Override
	public void execute(String[] args, IMessage message, StarotaServer server, IChannel channel)
			throws Exception {
		if (server.getDiscordGuild().getLongID() != StarotaConstants.SUPPORT_SERVER) {
			channel.sendMessage("To use the tutorial, please join the offical server here: "
					+ StarotaConstants.SUPPORT_SERVER_LINK);
			return;
		}
		if (channel.getLongID() != TUTORIAL_CHANNEL_ID) {
			channel.sendMessage("To use the tutorial, please start in the "
					+ server.getDiscordGuild().getChannelByID(TUTORIAL_CHANNEL_ID).mention()
					+ " channel.");
			return;
		}
		if (args.length > 1 && args[1].equalsIgnoreCase("chapters")) {
			channel.sendMessage(TutorialRegistry.getTableEmbed());
			return;
		}
	}

}
