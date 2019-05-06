package us.myles_selim.starota.commands.tutorial.commands;

import java.util.List;

import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.TextChannel;
import discord4j.core.object.util.Snowflake;
import us.myles_selim.starota.commands.StarotaCommand;
import us.myles_selim.starota.commands.tutorial.TutorialRegistry;
import us.myles_selim.starota.misc.utils.StarotaConstants;
import us.myles_selim.starota.wrappers.StarotaServer;

public class CommandTutorial extends StarotaCommand {

	private static final Snowflake TUTORIAL_CHANNEL_ID = Snowflake.of(569938166988013588L);

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
	public void execute(String[] args, Message message, StarotaServer server, TextChannel channel)
			throws Exception {
		if (!server.getDiscordGuild().getId().equals(StarotaConstants.SUPPORT_SERVER)) {
			channel.createMessage("To use the tutorial, please join the offical server here: "
					+ StarotaConstants.SUPPORT_SERVER_LINK);
			return;
		}
		if (channel.getId().equals(TUTORIAL_CHANNEL_ID)) {
			channel.createMessage("To use the tutorial, please start in the "
					+ server.getDiscordGuild().getChannelById(TUTORIAL_CHANNEL_ID).block().getMention()
					+ " channel.");
			return;
		}
		if (args.length > 1 && args[1].equalsIgnoreCase("chapters")) {
			channel.createEmbed(TutorialRegistry.getTableEmbed());
			return;
		}
	}

}
