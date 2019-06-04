package us.myles_selim.starota.commands;

import java.util.TimeZone;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.RequestBuffer;
import us.myles_selim.starota.wrappers.StarotaServer;

public class CommandTest extends BotCommand<StarotaServer> {

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

		// String in = "";
		// for (int i = 1; i < args.length; i++)
		// in += args[i] + " ";
		// in = in.substring(0, in.length() - 1);
		// List<EnumPokemon> mons = new ArrayList<>();
		// in.chars().forEach((ci) -> {
		// char c = (char) ci;
		// if (c == ' ') {
		// mons.add(null);
		// return;
		// }
		// for (EnumPokemon p : EnumPokemon.values()) {
		// if (p.getName().startsWith(Character.toString(c).toUpperCase()) &&
		// !mons.contains(p)) {
		// mons.add(p);
		// break;
		// }
		// }
		// });
		// String out = "";
		// for (EnumPokemon p : mons)
		// if (p == null)
		// out += "\n";
		// else
		// out += p.getName() + "\n";
		// channel.sendMessage("```\n" + out + "\n```");

		// IMessage msg = channel.sendMessage(new
		// EmbedBuilder().withTitle("Random New Pokestop")
		// .withUrl("https://discordapp.com").withDesc("Test Pokestop
		// submission.")
		// .withTimestamp(OffsetDateTime.parse("2019-01-25T12:37:44.111Z").toLocalDateTime()
		// .toInstant(ZoneOffset.of("Z")))
		// .withFooterText("Date
		// Submitted").withThumbnail("https://i.imgur.com/A2MYdKC.jpg")
		// .withImage(
		// "https://api.mapbox.com/styles/v1/mapbox/satellite-streets-v11/static/pin-s(-91.491881,42.7413049)/-91.491881,42.7413049,16.5,0,0/600x300@2x?logo=false&access_token=pk.eyJ1Ijoic2VsaW0wNDIiLCJhIjoiY2pyOXpmM2g1MG16cTQzbndqZXk5dHNndCJ9.vsh20BzsPBgTcBBcKWBqQw")
		// .withAuthorName("Selim")
		// .withAuthorIcon(
		// "https://cdn.discordapp.com/avatars/134855940938661889/a_12ab0e8755696845e6750488b6940e93.gif")
		// .withAuthorUrl("https://discordapp.com")
		// .appendField("Location:",
		// "[Google
		// Maps](https://www.google.com/maps/search/?api=1&query=42.7313049,-90.481881)
		// | "
		// + "[Apple
		// Maps](https://www.google.com/maps/search/?api=1&query=42.7313049,-90.481881)",
		// false)
		// .build());
		// RequestBuffer.request(() ->
		// msg.addReaction(ReactionEmoji.of("⬅"))).get();
		// RequestBuffer.request(() ->
		// msg.addReaction(ReactionEmoji.of("➡"))).get();
		// RequestBuffer.request(() ->
		// msg.addReaction(EmojiConstants.getBooleanEmoji(true))).get();
		// RequestBuffer.request(() ->
		// msg.addReaction(EmojiConstants.getBooleanEmoji(false))).get();

		// if (message.getAttachments().isEmpty()) {
		// channel.sendMessage("No screenshot found");
		// return;
		// }
		// BufferedImage image =
		// ImageHelper.getImage(message.getAttachments().get(0).getUrl());

		// BadgeData badge = OcrHelper.getBadgeValue(server, image);
		// if (badge != null)
		// channel.sendMessage(badge.name + ": " + badge.value);
		// else
		// channel.sendMessage("Bad OCR. If this is a gold badge, please try
		// taking a new screenshot. "
		// + "The gold sparkles can interfere.");

		// List<IJournalEntry> entries = OcrHelper.getJournalEntries(server,
		// image);
		// StringBuilder out = new StringBuilder("```\n");
		// for (IJournalEntry entry : entries)
		// out.append(entry + "\n");
		// channel.sendMessage(out.append("```").toString());

		channel.sendMessage(server.getTimezone().toString());
		String msg = "";
		for (String id : TimeZone.getAvailableIDs()) {
			if (msg.length() + id.length() > 1500) {
				String fMsg = msg;
				RequestBuffer.request(() -> {
					channel.sendMessage(fMsg);
				}).get();
				msg = id + '\n';
			}
			msg += id + '\n';
		}
		RequestBuffer.request(() -> channel.sendMessage("done"));
	}

}
