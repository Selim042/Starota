package us.myles_selim.starota.silph_road.eggs;

import java.util.EnumSet;
import java.util.List;

import sx.blah.discord.api.internal.json.objects.EmbedObject;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.Permissions;
import sx.blah.discord.util.EmbedBuilder;
import sx.blah.discord.util.RequestBuffer;
import us.myles_selim.starota.commands.BotCommand;
import us.myles_selim.starota.misc.data_types.EggEntry;
import us.myles_selim.starota.misc.utils.EmojiServerHelper;
import us.myles_selim.starota.silph_road.SilphRoadData;
import us.myles_selim.starota.wrappers.StarotaServer;

public class CommandEggHatches extends BotCommand<StarotaServer> {

	public CommandEggHatches() {
		super("eggHatches", "Gets information on what can currently hatch from eggs.");
	}

	@Override
	public EnumSet<Permissions> getCommandPermissions() {
		return EnumSet.of(Permissions.SEND_MESSAGES, Permissions.EMBED_LINKS,
				Permissions.USE_EXTERNAL_EMOJIS);
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
	public void execute(String[] args, IMessage message, StarotaServer server, IChannel channel)
			throws Exception {
		if (args.length == 1) {
			channel.sendMessage(getAllDists());
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
		IMessage msg = null;
		if (!SilphRoadData.areEggsLoaded(dist))
			msg = RequestBuffer.request(() -> {
				return channel.sendMessage(SilphRoadData.LOADING_EMBED);
			}).get();
		EmbedBuilder builder = new EmbedBuilder();
		builder.withTitle(
				String.format("%dk Eggs: %s", dist, EmojiServerHelper.getEmoji(dist + "kEgg")));
		for (EggEntry b : SilphRoadData.getEggs(dist))
			builder.appendDescription(
					b.getPokemon() + (b.getForm() == null ? "" : " (" + b.getForm() + ") ")
							+ (b.isShinyable() ? EmojiServerHelper.getEmoji("shiny") + "\n" : "\n"));

		builder.withUrl("https://thesilphroad.com/raid-bosses");
		builder.withAuthorName("The Silph Road");
		builder.withAuthorIcon("https://thesilphroad.com/favicon.ico?1476895361");
		builder.withAuthorUrl("https://thesilphroad.com/");
		builder.withFooterText("Last updated");
		builder.withTimestamp(SilphRoadData.getEggCacheTime());
		IMessage msgF = msg;
		if (msgF != null)
			RequestBuffer.request(() -> msgF.edit(builder.build()));
		else
			RequestBuffer.request(() -> channel.sendMessage(builder.build()));
	}

	private static EmbedObject getAllDists() {
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
					shinies2k += e.getPokemon().getName() + ", ";
				else
					nonShinies2k += e.getPokemon().getName() + ", ";
				break;
			case 5:
				if (e.isShinyable())
					shinies5k += e.getPokemon().getName() + ", ";
				else
					nonShinies5k += e.getPokemon().getName() + ", ";
				break;
			case 7:
				if (e.isShinyable())
					shinies7k += e.getPokemon().getName() + ", ";
				else
					nonShinies7k += e.getPokemon().getName() + ", ";
				break;
			case 10:
				if (e.isShinyable())
					shinies10k += e.getPokemon().getName() + ", ";
				else
					nonShinies10k += e.getPokemon().getName() + ", ";
				break;
			}
		}
		builder.appendField("**2k Eggs**: " + EmojiServerHelper.getEmoji("2kEgg"),
				"**Non-Shinies**: " + nonShinies2k.substring(0, nonShinies2k.length() - 2) + "\n\n"
						+ "**Shinies**: " + shinies2k.substring(0, shinies2k.length() - 2),
				false);
		builder.appendField("**5k Eggs**: " + EmojiServerHelper.getEmoji("5kEgg"),
				"**Non-Shinies**: " + nonShinies5k.substring(0, nonShinies5k.length() - 2) + "\n\n"
						+ "**Shinies**: " + shinies5k.substring(0, shinies5k.length() - 2),
				false);
		builder.appendField("**7k Eggs**: " + EmojiServerHelper.getEmoji("7kEgg"),
				"**Non-Shinies**: " + nonShinies7k.substring(0, nonShinies7k.length() - 2) + "\n\n"
						+ "**Shinies**: " + shinies7k.substring(0, shinies7k.length() - 2),
				false);
		builder.appendField("**10k Eggs**: " + EmojiServerHelper.getEmoji("10kEgg"),
				"**Non-Shinies**: " + nonShinies10k.substring(0, nonShinies10k.length() - 2) + "\n\n"
						+ "**Shinies**: " + shinies10k.substring(0, shinies10k.length() - 2),
				false);

		builder.withFooterText("Last updated").withTimestamp(SilphRoadData.getEggCacheTime());
		return builder.build();
	}

}
