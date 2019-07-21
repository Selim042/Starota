package us.myles_selim.starota.raids;

import java.util.List;
import java.util.function.Consumer;

import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.MessageChannel;
import discord4j.core.object.util.Permission;
import discord4j.core.object.util.PermissionSet;
import discord4j.core.spec.EmbedCreateSpec;
import us.myles_selim.starota.commands.BotCommand;
import us.myles_selim.starota.commands.registry.CommandException;
import us.myles_selim.starota.misc.data_types.RaidBoss;
import us.myles_selim.starota.misc.utils.EmbedBuilder;
import us.myles_selim.starota.misc.utils.EmojiServerHelper;
import us.myles_selim.starota.misc.utils.ImageHelper;
import us.myles_selim.starota.misc.utils.MiscUtils;
import us.myles_selim.starota.silph_road.SilphRoadData;
import us.myles_selim.starota.wrappers.StarotaServer;

public class CommandRaidBosses extends BotCommand<StarotaServer> {

	public CommandRaidBosses() {
		super("raidBosses", "Gets information on raid bosses for a specific tier.");
	}

	@Override
	public PermissionSet getCommandPermission() {
		return PermissionSet.of(Permission.SEND_MESSAGES, Permission.EMBED_LINKS,
				Permission.USE_EXTERNAL_EMOJIS);
	}

	@Override
	public List<String> getAliases() {
		List<String> aliases = super.getAliases();
		aliases.add("bosses");
		return aliases;
	}

	@Override
	public String getGeneralUsage() {
		return "[tier]";
	}

	@Override
	public void execute(String[] args, Message message, StarotaServer server, MessageChannel channel)
			throws CommandException {
		if (args.length == 1) {
			channel.createEmbed(getAllTiers()).block();
			return;
		}
		if (args.length < 2) {
			sendUsage(server.getPrefix(), channel);
			return;
		}
		int tier;
		if (args[1].equalsIgnoreCase("ex"))
			tier = 6;
		try {
			tier = Integer.parseInt(args[1]);
		} catch (NumberFormatException e) {
			sendUsage(server.getPrefix(), channel);
			return;
		}
		if (tier < 1 || tier > 6) {
			sendUsage(server.getPrefix(), channel);
			return;
		}
		Message msg = null;
		if (!SilphRoadData.areBossesLoaded(tier))
			msg = channel.createEmbed(SilphRoadData.LOADING_EMBED).block();
		EmbedBuilder builder = new EmbedBuilder();
		String bossPlural = "Boss";
		if (SilphRoadData.getBosses(tier).size() > 1)
			bossPlural += "es";
		if (tier != 6)
			builder.withTitle("Tier " + tier + " Raid " + bossPlural + ":");
		else
			builder.withTitle("EX Raid " + bossPlural + ":");
		builder.withDescription("");
		for (RaidBoss b : SilphRoadData.getBosses(tier)) {
			String postfix = b.getForm() == null ? "" : "_" + b.getForm();
			builder.appendDescription(b.getPokemon()
					+ (b.getForm() == null ? "" : " (" + b.getForm() + ") ")
					+ MiscUtils.getEmojiDisplay(EmojiServerHelper.getEmoji(b.getPokemon() + postfix,
							ImageHelper.getOfficalArtwork(b.getPokemon(), b.getForm())))
					+ (b.isShinyable()
							? MiscUtils.getEmojiDisplay(EmojiServerHelper.getEmoji("shiny")) + "\n"
							: "\n"));
		}

		builder.withUrl("https://thesilphroad.com/raid-bosses");
		builder.withAuthorName("The Silph Road");
		builder.withAuthorIcon("https://thesilphroad.com/favicon.ico?1476895361");
		builder.withAuthorUrl("https://thesilphroad.com/");
		builder.withFooterText("Last updated");
		builder.withTimestamp(SilphRoadData.getBossCacheTime());
		Message msgF = msg;
		if (msgF != null)
			msgF.edit((m) -> m.setEmbed(builder.build())).block();
		else
			channel.createEmbed(builder.build()).block();
	}

	private static Consumer<? super EmbedCreateSpec> getAllTiers() {
		EmbedBuilder builder = new EmbedBuilder();
		builder.withTitle("Raid Bosses:");
		for (int i = 6; i > 0; i--) {
			String fieldTitle;
			if (i == 6)
				fieldTitle = "**EX Raid Boss";
			else
				fieldTitle = "**Tier " + i + " Raid Boss";
			String fieldDesc = "";
			int numBosses = 0;
			for (RaidBoss b : SilphRoadData.getBosses(i)) {
				numBosses++;
				String postfix = b.getForm() == null ? "" : "_" + b.getForm();
				fieldDesc += b.getPokemon() + (b.getForm() == null ? "" : " (" + b.getForm() + ")")
						+ MiscUtils.getEmojiDisplay(EmojiServerHelper.getEmoji(b.getPokemon() + postfix,
								ImageHelper.getOfficalArtwork(b.getPokemon(), b.getForm())))
						+ (b.isShinyable()
								? MiscUtils.getEmojiDisplay(EmojiServerHelper.getEmoji("shiny")) + "\n"
								: "\n");
			}
			if (numBosses > 1)
				fieldTitle += "es**: ";
			else
				fieldTitle += "**: ";
			if (i == 6)
				fieldTitle += MiscUtils.getEmojiDisplay(EmojiServerHelper.getEmoji("ex_raid"));
			else {
				for (int i2 = 0; i2 < i; i2++)
					fieldTitle += MiscUtils.getEmojiDisplay(EmojiServerHelper.getEmoji("raid"));
			}
			builder.appendField(fieldTitle, fieldDesc, true);
		}

		builder.withUrl("https://thesilphroad.com/raid-bosses");
		builder.withAuthorName("The Silph Road");
		builder.withAuthorIcon("https://thesilphroad.com/favicon.ico?1476895361");
		builder.withAuthorUrl("https://thesilphroad.com/");
		builder.withFooterText("Last updated");
		builder.withTimestamp(SilphRoadData.getBossCacheTime());
		return builder.build();
	}

}
