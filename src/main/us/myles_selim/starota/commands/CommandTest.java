package us.myles_selim.starota.commands;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.EmbedBuilder;
import us.myles_selim.starota.EggEntry;
import us.myles_selim.starota.EmojiServerHelper;
import us.myles_selim.starota.silph_road.SilphRoadData;
import us.myles_selim.starota.wrappers.StarotaServer;

public class CommandTest extends StarotaCommand {

	public CommandTest() {
		super("test");
	}

	@Override
	public void execute(String[] args, IMessage message, StarotaServer server, IChannel channel) {
		// try {
		// RaidReactionMessage rMsg = new RaidReactionMessage(6, "9:30",
		// "Chess Pieces - UW-Platteville");
		// Field bossField = RaidReactionMessage.class.getDeclaredField("boss");
		// bossField.setAccessible(true);
		// bossField.set(rMsg, new RaidBoss(EnumPokemon.KYOGRE, null, 5));
		// Field pokeField =
		// RaidReactionMessage.class.getDeclaredField("pokemon");
		// pokeField.setAccessible(true);
		// pokeField.set(rMsg, EnumPokemon.KYOGRE);
		// Method method =
		// RaidReactionMessage.class.getDeclaredMethod("getEmbed",
		// StarotaServer.class);
		// method.setAccessible(true);
		// EmbedObject embed = (EmbedObject) method.invoke(rMsg, server);
		// embed.image = new EmbedObject.ImageObject(String.format(
		// "https://api.mapbox.com/styles/v1/mapbox/satellite-streets-v11/static/pin-s+%06X(-90.4879800,42.733048)/-90.4879800,42.733048,16.5,0,0/600x300@2x?logo=false&access_token=pk.eyJ1Ijoic2VsaW0wNDIiLCJhIjoiY2pyOXpmM2g1MG16cTQzbndqZXk5dHNndCJ9.vsh20BzsPBgTcBBcKWBqQw",
		// EnumTeam.INSTINCT.getColor()), null, 0, 0);
		// channel.sendMessage(embed);
		// } catch (Exception e) {
		// e.printStackTrace();
		// }

		// channel.sendMessage(TimeZone.getTimeZone(args[1]).toString());

		// WebhookPokemon hook = new WebhookPokemon();
		// hook.pokemon_id = 1;
		// hook.weight = 8.99;
		// hook.height = 0.84;
		// channel.sendMessage(
		// "weight" " + hook.getWeightModifier() + "\nheight" " +
		// hook.getHeightModifier());

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
		channel.sendMessage(builder.build());

		// channel.sendMessage(new EmbedBuilder().withTitle("Egg Distance List")
		// .withUrl("https://thesilphroad.com/egg-distances").withColor(-1)
		// .withAuthorName("The Silph Road")
		// .withAuthorIcon("https://thesilphroad.com/favicon.ico?1476895361")
		// .withAuthorUrl("https://thesilphroad.com/")
		// .appendField("**2k Eggs**: <:2kEgg:560658970038239242>",
		// "**Non-Shinies**: Abra, Chimchar, Piplup, Turtwig, Slowpoke, Mudkip,
		// Torchic, Starly, Kricketot\n\n**Shinies**: Swablu, Wailmer, Machop,
		// Swinub, Magikarp, Gastly, Aron, Squirtle, Spoink, Misdreavus,
		// Chikorita, Bulbasaur, Charmander, Treecko, Totodile, Cyndaquil,
		// Luvdisc",
		// false)
		// .appendField("**5k Eggs**: <:5kEgg:560658819512926209>",
		// "**Non-Shinies**: Sneasel, Croagunk, Stunky, Eletrike, Buizel,
		// Phanpy, Glameow, Gligar, Onix, Yanma, Snover, Buneary, Rhyhorn,
		// Carvahna, Poliwag, Scyther, Tangela, Lickitung, Anorith, Horsea,
		// Nosepass, Lileep, Lotad, Cacnea\n\n**Shinies**: Eevee, Magnemite,
		// Growlithe, Houndour, Pineco, Duskull, Snorunt",
		// false)
		// .appendField("**7k Eggs**: <:7kEgg:560658890207789080>",
		// "**Non-Shinies**: Tyrogue, Igglybuff, Munchlax, Happiny, Alolan
		// Diglett, Alolan Grimer, Alolan Geodude, Alolan Vulpix, Alolan Mewoth,
		// Alolan Sandshrew, Mantyke, Bonsly, Riolu, Chingling\n\n**Shinies**:
		// Azurill, Pichu, Togepi, Smoochum, Cleffa, Elekid, Budew, Wynaut,
		// Magby",
		// false)
		// .appendField("**10k Eggs**: <:10kEgg:560658949792333824>",
		// "**Non-Shinies**: Slackoth, Porygon, Ralts, Sheildon, Cranidos,
		// Skorupi, Bagon, Miltank, Riolu\n\n**Shinies**: Dratini, Mareep,
		// Beldum, Feebas, Larvitar, Shinx, Mawile, Drifloon, Absol, Aerodactyl,
		// Trapinch",
		// false)
		// .build());
	}

}
