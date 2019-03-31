package us.myles_selim.starota.raids;

import java.util.List;

import sx.blah.discord.api.internal.json.objects.EmbedObject;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.EmbedBuilder;
import sx.blah.discord.util.RequestBuffer;
import us.myles_selim.starota.EmojiServerHelper;
import us.myles_selim.starota.ImageHelper;
import us.myles_selim.starota.RaidBoss;
import us.myles_selim.starota.commands.StarotaCommand;
import us.myles_selim.starota.silph_road.SilphRoadData;
import us.myles_selim.starota.wrappers.StarotaServer;

public class CommandRaidBosses extends StarotaCommand {

	public CommandRaidBosses() {
		super("raidBosses", "Gets information on raid bosses for a specific tier.");
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
	public void execute(String[] args, IMessage message, StarotaServer server, IChannel channel)
			throws Exception {
		if (args.length == 1) {
			channel.sendMessage(getAllTiers());
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
		IMessage msg = null;
		if (!SilphRoadData.areBossesLoaded(tier))
			msg = RequestBuffer.request(() -> {
				return channel.sendMessage(SilphRoadData.LOADING_EMBED);
			}).get();
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
		IMessage msgF = msg;
		if (msgF != null)
			RequestBuffer.request(() -> msgF.edit(builder.build()));
		else
			RequestBuffer.request(() -> channel.sendMessage(builder.build()));
	}

	private static EmbedObject getAllTiers() {
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
						+ EmojiServerHelper.getEmoji(b.getPokemon() + postfix,
								ImageHelper.getOfficalArtwork(b.getPokemon(), b.getForm()))
						+ (b.isShinyable() ? EmojiServerHelper.getEmoji("shiny") + "\n" : "\n");
			}
			if (numBosses > 1)
				fieldTitle += "es**: ";
			else
				fieldTitle += "**: ";
			if (i == 6)
				fieldTitle += EmojiServerHelper.getEmoji("ex_raid");
			else {
				for (int i2 = 0; i2 < i; i2++)
					fieldTitle += EmojiServerHelper.getEmoji("raid");
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
