package us.myles_selim.starota.raids;

import java.util.List;
import java.util.function.Consumer;

import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.TextChannel;
import discord4j.core.object.util.Permission;
import discord4j.core.object.util.PermissionSet;
import discord4j.core.spec.EmbedCreateSpec;
import us.myles_selim.starota.commands.StarotaCommand;
import us.myles_selim.starota.misc.data_types.EmbedBuilder;
import us.myles_selim.starota.misc.data_types.RaidBoss;
import us.myles_selim.starota.misc.utils.EmojiServerHelper;
import us.myles_selim.starota.misc.utils.ImageHelper;
import us.myles_selim.starota.silph_road.SilphRoadData;
import us.myles_selim.starota.wrappers.StarotaServer;

public class CommandRaidBosses extends StarotaCommand {

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
	public void execute(String[] args, Message message, StarotaServer server, TextChannel channel)
			throws Exception {
		if (args.length == 1) {
			channel.createEmbed(getAllTiers());
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
			builder.setTitle("Tier " + tier + " Raid " + bossPlural + ":");
		else
			builder.setTitle("EX Raid " + bossPlural + ":");
		builder.withDescription("");
		for (RaidBoss b : SilphRoadData.getBosses(tier)) {
			String postfix = b.getForm() == null ? "" : "_" + b.getForm();
			builder.appendDescription(
					b.getPokemon() + (b.getForm() == null ? "" : " (" + b.getForm() + ") ")
							+ EmojiServerHelper.getEmoji(b.getPokemon() + postfix,
									ImageHelper.getOfficalArtwork(b.getPokemon(), b.getForm()))
							+ (b.isShinyable() ? EmojiServerHelper.getEmoji("shiny") + "\n" : "\n"));
		}

		builder.withUrl("https://thesilphroad.com/raid-bosses");
		builder.withAuthorName("The Silph Road");
		builder.withAuthorIcon("https://thesilphroad.com/favicon.ico?1476895361");
		builder.withAuthorUrl("https://thesilphroad.com/");
		builder.withFooterText("Last updated");
		builder.withTimestamp(SilphRoadData.getBossCacheTime());
		Message msgF = msg;
		if (msgF != null)
			msgF.edit((m) -> m.setEmbed(builder.build()));
		else
			channel.createEmbed(builder.build());
	}

	private static Consumer<EmbedCreateSpec> getAllTiers() {
		EmbedBuilder builder = new EmbedBuilder();
		builder.setTitle("Raid Bosses:");
		for (int tier = 6; tier > 0; tier--) {
			String fieldTitle;
			if (tier == 6)
				fieldTitle = "**EX Raid Boss";
			else
				fieldTitle = "**Tier " + tier + " Raid Boss";
			String fieldDesc = "";
			int numBosses = 0;
			for (RaidBoss b : SilphRoadData.getBosses(tier)) {
				numBosses++;
				String postfix = b.getForm() == null ? "" : "_" + b.getForm();
				fieldDesc += b.getPokemon() + (b.getForm() == null ? "" : " (" + b.getForm() + ")")
						+ EmojiServerHelper.getEmoji(b.getPokemon() + postfix,
								ImageHelper.getOfficalArtwork(b.getPokemon(), b.getForm()))
						+ (b.isShinyable() ? EmojiServerHelper.getEmoji("shiny").asFormat() + "\n"
								: "\n");
			}
			if (numBosses > 1)
				fieldTitle += "es**: ";
			else
				fieldTitle += "**: ";
			if (tier == 6)
				fieldTitle += EmojiServerHelper.getEmoji("ex_raid").asFormat();
			else {
				for (int i = 0; i < tier; i++)
					fieldTitle += EmojiServerHelper.getEmoji("raid").asFormat();
			}
			builder.addField(fieldTitle, fieldDesc, true);
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
