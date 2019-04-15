package us.myles_selim.starota.commands;

import java.util.ArrayList;
import java.util.List;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import us.myles_selim.starota.enums.EnumPokemon;
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

		// SilphRoadData.getTasks();

		String in = "";
		for (int i = 1; i < args.length; i++)
			in += args[i] + " ";
		in = in.substring(0, in.length() - 1);
		List<EnumPokemon> mons = new ArrayList<>();
		in.chars().forEach((ci) -> {
			char c = (char) ci;
			if (c == ' ') {
				mons.add(null);
				return;
			}
			for (EnumPokemon p : EnumPokemon.values()) {
				if (p.getName().startsWith(Character.toString(c).toUpperCase()) && !mons.contains(p)) {
					mons.add(p);
					break;
				}
			}
		});
		String out = "";
		for (EnumPokemon p : mons)
			if (p == null)
				out += "\n";
			else
				out += p.getName() + "\n";
		channel.sendMessage("```\n" + out + "\n```");
	}

}
