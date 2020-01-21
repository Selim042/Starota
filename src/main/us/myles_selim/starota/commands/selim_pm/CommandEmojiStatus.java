package us.myles_selim.starota.commands.selim_pm;

import discord4j.command.util.CommandException;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.GuildEmoji;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.MessageChannel;
import discord4j.core.object.util.Snowflake;
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.commands.registry.java.JavaCommand;
import us.myles_selim.starota.misc.utils.EmbedBuilder;
import us.myles_selim.starota.misc.utils.EmojiServerHelper;
import us.myles_selim.starota.misc.utils.MiscUtils;

public class CommandEmojiStatus extends JavaCommand {

	public CommandEmojiStatus() {
		super("emojiStatus", "Checks how many open emoji slots are available.");
	}

	@Override
	public void execute(String[] args, Message message, Guild guild, MessageChannel channel)
			throws CommandException {
		int usedNormal = 0;
		int totalNormal = 0;
		int usedAnimated = 0;
		int totalAnimated = 0;
		long[] noUpload = EmojiServerHelper.getNoUploadEmojiServers();
		for (long id : EmojiServerHelper.getEmojiServers()) {
			if (MiscUtils.arrContains(noUpload, id))
				continue;
			Guild emojiGuild = Starota.getClient().getGuildById(Snowflake.of(id)).block();
			for (GuildEmoji emoji : emojiGuild.getEmojis().collectList().block())
				if (emoji.isAnimated())
					usedAnimated++;
				else
					usedNormal++;
			totalNormal += 50;
			totalAnimated += 50;
		}

		EmbedBuilder embed = new EmbedBuilder().withTitle("Emoji Servers Status:");
		embed.appendField("Normal Emoji:", String.format("%d/%d", usedNormal, totalNormal), false);
		embed.appendField("Animated Emoji:", String.format("%d/%d", usedAnimated, totalAnimated), false);
		channel.createEmbed(embed.build()).block();
	}

}
