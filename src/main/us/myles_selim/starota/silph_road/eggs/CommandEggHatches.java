package us.myles_selim.starota.silph_road.eggs;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.MessageChannel;
import discord4j.core.object.reaction.ReactionEmoji;
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

	private static final String REGIONAL_DISCLAIMER = //
			"Regional exclusive Pokemon can only be hatched from eggs picked up in their respective regions.\n"
					+ "There is no way to obtain regional %dkm eggs in any other way outside of picking them "
					+ "up from a Pokestop in their respective regions.";

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

		boolean foundRegional = false;
		StringBuilder regionalHatches = new StringBuilder();
		regionalHatches.append(String.format(REGIONAL_DISCLAIMER, dist) + "\n\n");
		for (EggEntry b : SilphRoadData.getEggs(dist)) {
			if (b.getPokemon().getData().isRegional()) {
				foundRegional = true;
				regionalHatches.append(b.getPokemon()
						+ (b.getForm() == null ? "" : " (" + b.getForm() + ") ")
						+ (b.isShinyable()
								? MiscUtils.getEmojiDisplay(EmojiServerHelper.getEmoji("shiny")) + "\n"
								: "\n"));
			} else
				builder.appendDescription(b.getPokemon()
						+ (b.getForm() == null ? "" : " (" + b.getForm() + ") ")
						+ (b.isShinyable()
								? MiscUtils.getEmojiDisplay(EmojiServerHelper.getEmoji("shiny")) + "\n"
								: "\n"));
		}
		if (foundRegional)
			builder.appendField("Regional Hatches:", regionalHatches.toString(), false);

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

		List<EggData> sortedEggs = getSortedEggs();
		for (EggData data : sortedEggs) {
			StringBuilder titleBuilder = new StringBuilder(
					data.distance + "k Eggs" + (data.regional ? " (Regionals): " : ": "));
			titleBuilder.append(
					MiscUtils.getEmojiDisplay(EmojiServerHelper.getEmoji(data.distance + "kEgg")));
			if (data.regional)
				titleBuilder.append(MiscUtils.getEmojiDisplay(ReactionEmoji.unicode("🗺")));

			StringBuilder bodyBuilder = new StringBuilder();
			if (data.regional)
				bodyBuilder.append(String.format(REGIONAL_DISCLAIMER, data.distance) + "\n\n");
			bodyBuilder.append("**Non-Shinies**:\n");
			if (data.builder.length() > 2)
				bodyBuilder.append(data.builder.substring(0, data.builder.length() - 2));
			else
				bodyBuilder.append("No hatches found");
			bodyBuilder.append("\n\n**Shinies**:\n");
			if (data.shinyBuilder.length() > 2)
				bodyBuilder.append(data.shinyBuilder.substring(0, data.shinyBuilder.length() - 2));
			else
				bodyBuilder.append("No hatches found");

			builder.appendField(titleBuilder.toString(), bodyBuilder.toString(), false);
		}

		builder.withFooterText("Last updated").withTimestamp(SilphRoadData.getEggCacheTime());
		return builder.build();
	}

	private static List<EggData> getSortedEggs() {
		List<EggData> sortedEggs = new ArrayList<>();
		for (EggEntry e : SilphRoadData.getEggs()) {
			EggData data = null;
			for (EggData eData : sortedEggs) {
				if (eData.matches(e.getDistance(), e.getPokemon().getData().isRegional()))
					data = eData;
			}
			if (data == null) {
				data = new EggData(e.getDistance(), e.getPokemon().getData().isRegional());
				sortedEggs.add(data);
			}
			if (e.isShinyable())
				data.shinyBuilder.append((e.getPokemon().getFormSet().isDefaultForm(e.getForm())
						? e.getPokemon().getData().getName()
						: String.format("%s (%s)", e.getPokemon().getData().getName(),
								e.getForm().getName()))
						+ ", ");
			else
				data.builder.append((e.getPokemon().getFormSet().isDefaultForm(e.getForm())
						? e.getPokemon().getData().getName()
						: String.format("%s (%s)", e.getPokemon().getData().getName(),
								e.getForm().getName()))
						+ ", ");
		}
		sortedEggs.sort(null);
		return sortedEggs;
	}

	private static class EggData implements Comparable<EggData> {

		public int distance;
		public boolean regional;
		public final StringBuilder builder = new StringBuilder();
		public final StringBuilder shinyBuilder = new StringBuilder();

		public EggData(int distance, boolean regional) {
			this.distance = distance;
			this.regional = regional;
		}

		public boolean matches(int distance, boolean regional) {
			return this.distance == distance && this.regional == regional;
		}

		@Override
		public int compareTo(EggData data) {
			if (this.distance == data.distance)
				return Boolean.compare(this.regional, data.regional);
			return Integer.compare(this.distance, data.distance);
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + distance;
			result = prime * result + (regional ? 1231 : 1237);
			return result;
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("EggData [distance=");
			builder.append(distance);
			builder.append(", regional=");
			builder.append(regional);
			builder.append("]");
			return builder.toString() + "=" + this.builder.toString();
		}

	}

}
