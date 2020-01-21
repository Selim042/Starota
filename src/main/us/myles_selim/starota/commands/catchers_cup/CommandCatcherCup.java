package us.myles_selim.starota.commands.catchers_cup;

import java.util.List;
import java.util.function.Consumer;

import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.MessageChannel;
import discord4j.core.object.util.Permission;
import discord4j.core.object.util.PermissionSet;
import discord4j.core.spec.EmbedCreateSpec;
import us.myles_selim.starota.commands.BotCommand;
import us.myles_selim.starota.commands.registry.CommandException;
import us.myles_selim.starota.enums.EnumTeam;
import us.myles_selim.starota.misc.data_types.Pair;
import us.myles_selim.starota.misc.utils.EmbedBuilder;
import us.myles_selim.starota.misc.utils.MiscUtils;
import us.myles_selim.starota.wrappers.StarotaServer;

public class CommandCatcherCup extends BotCommand<StarotaServer> {

	public CommandCatcherCup() {
		super("catcherCup", "Displays current state of the Catcher Cup on this server.");
	}

	@Override
	public List<String> getAliases() {
		List<String> aliases = super.getAliases();
		aliases.add("cc");
		return aliases;
	}

	@Override
	public PermissionSet getCommandPermission() {
		return PermissionSet.of(Permission.SEND_MESSAGES, Permission.EMBED_LINKS);
	}

	@Override
	public void execute(String[] args, Message message, StarotaServer server, MessageChannel channel)
			throws CommandException {
		channel.createEmbed(getEmbed(server)).block();
	}

	private Consumer<? super EmbedCreateSpec> getEmbed(StarotaServer server) {
		EmbedBuilder builder = new EmbedBuilder().withTitle("Catcher Cup Stats:")
				.withColor(server.getHighestTeam().getColor());
		for (Pair<EnumTeam, Integer> ranking : server.getRankings())
			builder.appendDesc(
					String.format("%s %s: %,d\n", MiscUtils.getEmojiDisplay(ranking.left.getEmoji()),
							ranking.left.getName(), ranking.right));
		return builder.build();
	}

}
