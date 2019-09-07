package us.myles_selim.starota.silph_road.eggs;

import java.util.List;
import java.util.function.Consumer;

import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.MessageChannel;
import discord4j.core.object.util.Permission;
import discord4j.core.object.util.PermissionSet;
import discord4j.core.spec.EmbedCreateSpec;
import us.myles_selim.starota.commands.BotCommand;
import us.myles_selim.starota.commands.registry.CommandException;
import us.myles_selim.starota.misc.data_types.EggEntry;
import us.myles_selim.starota.misc.utils.EmbedBuilder;
import us.myles_selim.starota.misc.utils.EmojiServerHelper;
import us.myles_selim.starota.misc.utils.MiscUtils;
import us.myles_selim.starota.silph_road.SilphRoadData;
import us.myles_selim.starota.wrappers.StarotaServer;

public class CommandEggHatches extends BotCommand<StarotaServer> {

	public CommandEggHatches() {
		super("eggHatches", "Gets information on what can currently hatch from eggs.");
	}

	@Override
	public PermissionSet getCommandPermission() {
		return PermissionSet.of(Permission.SEND_MESSAGES, Permission.EMBED_LINKS,
				Permission.USE_EXTERNAL_EMOJIS);
	}

	@Override
	public List<String> getAliases() {
		List<String> aliases = super.getAliases();
		aliases.add("hatches");
		return aliases;
	}

	@Override
	public String getGeneralUsage() {
		return "[distance]";
	}

	@Override
	public void execute(String[] args, Message message, StarotaServer server, MessageChannel channel)
			throws CommandException {
		if (args.length == 1) {
			channel.createEmbed(getAllDists()).block();
			return;
		}
		if (args.length < 2) {
			sendUsage(server.getPrefix(), channel);
			return;
		}
		int dist;
		try {
			dist = Integer.parseInt(args[1]);
		} catch (NumberFormatException e) {
			sendUsage(server.getPrefix(), channel);
			return;
		}
		if (dist != 2 && dist != 5 && dist != 7 && dist != 10) {
			sendUsage(server.getPrefix(), channel);
			return;
		}
		Message msg = null;
		if (!SilphRoadData.areEggsLoaded(dist))
			msg = channel.createEmbed(SilphRoadData.LOADING_EMBED).block();
		EmbedBuilder builder = new EmbedBuilder();
		builder.withTitle(String.format("%dk Eggs: %s", dist,
				MiscUtils.getEmojiDisplay(EmojiServerHelper.getEmoji(dist + "kEgg"))));
		for (EggEntry b : SilphRoadData.getEggs(dist))
			builder.appendDescription(b.getPokemon()
					+ (b.getForm() == null ? "" : " (" + b.getForm() + ") ")
					+ (b.isShinyable()
							? MiscUtils.getEmojiDisplay(EmojiServerHelper.getEmoji("shiny")) + "\n"
							: "\n"));

		builder.withUrl("https://thesilphroad.com/raid-bosses");
		builder.withAuthorName("The Silph Road");
		builder.withAuthorIcon("https://thesilphroad.com/favicon.ico?1476895361");
		builder.withAuthorUrl("https://thesilphroad.com/");
		builder.withFooterText("Last updated");
		builder.withTimestamp(SilphRoadData.getEggCacheTime());
		Message msgF = msg;
		if (msgF != null)
			msgF.edit((m) -> m.setEmbed(builder.build())).block();
		else
			channel.createEmbed(builder.build()).block();
	}

	private static Consumer<? super EmbedCreateSpec> getAllDists() {
		EmbedBuilder builder = new EmbedBuilder().withTitle("Egg Distance List:").withColor(-1);
		builder.withUrl("https://thesilphroad.com/egg-distances");
		builder.withAuthorName("The Silph Road");
		builder.withAuthorIcon("https://thesilphroad.com/favicon.ico?1476895361");
		builder.withAuthorUrl("https://thesilphroad.com/");

		String nonShinies2k = "";
		String shinies2k = "";
		String nonShinies5k = "";
		String shinies5k = "";
		String nonShinies7k = "";
		String shinies7k = "";
		String nonShinies10k = "";
		String shinies10k = "";
		for (EggEntry e : SilphRoadData.getEggs()) {
			switch (e.getDistance()) {
			case 2:
				if (e.isShinyable())
					shinies2k += (e.getForm() == null ? e.getPokemon().getName()
							: String.format("%s (%s)", e.getPokemon().getName(), e.getForm().toString()))
							+ ", ";
				else
					nonShinies2k += (e.getForm() == null ? e.getPokemon().getName()
							: String.format("%s (%s)", e.getPokemon().getName(), e.getForm().toString()))
							+ ", ";
				break;
			case 5:
				if (e.isShinyable())
					shinies5k += (e.getForm() == null ? e.getPokemon().getName()
							: String.format("%s (%s)", e.getPokemon().getName(), e.getForm().toString()))
							+ ", ";
				else
					nonShinies5k += (e.getForm() == null ? e.getPokemon().getName()
							: String.format("%s (%s)", e.getPokemon().getName(), e.getForm().toString()))
							+ ", ";
				break;
			case 7:
				if (e.isShinyable())
					shinies7k += (e.getForm() == null ? e.getPokemon().getName()
							: String.format("%s (%s)", e.getPokemon().getName(), e.getForm().toString()))
							+ ", ";
				else
					nonShinies7k += (e.getForm() == null ? e.getPokemon().getName()
							: String.format("%s (%s)", e.getPokemon().getName(), e.getForm().toString()))
							+ ", ";
				break;
			case 10:
				if (e.isShinyable())
					shinies10k += (e.getForm() == null ? e.getPokemon().getName()
							: String.format("%s (%s)", e.getPokemon().getName(), e.getForm().toString()))
							+ ", ";
				else
					nonShinies10k += (e.getForm() == null ? e.getPokemon().getName()
							: String.format("%s (%s)", e.getPokemon().getName(), e.getForm().toString()))
							+ ", ";
				break;
			}
		}
		builder.appendField(
				"**2k Eggs**: " + MiscUtils.getEmojiDisplay(EmojiServerHelper.getEmoji("2kEgg")),
				"**Non-Shinies**: " + nonShinies2k.substring(0, nonShinies2k.length() - 2) + "\n\n"
						+ "**Shinies**: " + shinies2k.substring(0, shinies2k.length() - 2),
				false);
		builder.appendField(
				"**5k Eggs**: " + MiscUtils.getEmojiDisplay(EmojiServerHelper.getEmoji("5kEgg")),
				"**Non-Shinies**: " + nonShinies5k.substring(0, nonShinies5k.length() - 2) + "\n\n"
						+ "**Shinies**: " + shinies5k.substring(0, shinies5k.length() - 2),
				false);
		builder.appendField(
				"**7k Eggs**: " + MiscUtils.getEmojiDisplay(EmojiServerHelper.getEmoji("7kEgg")),
				"**Non-Shinies**: " + nonShinies7k.substring(0, nonShinies7k.length() - 2) + "\n\n"
						+ "**Shinies**: " + shinies7k.substring(0, shinies7k.length() - 2),
				false);
		builder.appendField(
				"**10k Eggs**: " + MiscUtils.getEmojiDisplay(EmojiServerHelper.getEmoji("10kEgg")),
				"**Non-Shinies**: " + nonShinies10k.substring(0, nonShinies10k.length() - 2) + "\n\n"
						+ "**Shinies**: " + shinies10k.substring(0, shinies10k.length() - 2),
				false);

		builder.withFooterText("Last updated").withTimestamp(SilphRoadData.getEggCacheTime());
		return builder.build();
	}

}
