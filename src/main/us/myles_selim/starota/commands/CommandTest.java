package us.myles_selim.starota.commands;

import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.MessageChannel;
import us.myles_selim.starota.commands.registry.CommandException;
import us.myles_selim.starota.wrappers.StarotaServer;

public class CommandTest extends BotCommand<StarotaServer> {

	public CommandTest() {
		super("test");
	}

	@Override
	public void execute(String[] args, Message message, StarotaServer server, MessageChannel channel)
			throws CommandException {
		// Message msg = channel.createMessage(new
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
		// msg.addReaction(ReactionEmoji.unicode("⬅"))).get();
		// RequestBuffer.request(() ->
		// msg.addReaction(ReactionEmoji.unicode("➡"))).get();
		// RequestBuffer.request(() ->
		// msg.addReaction(EmojiConstants.getBooleanEmoji(true))).get();
		// RequestBuffer.request(() ->
		// msg.addReaction(EmojiConstants.getBooleanEmoji(false))).get();
	}

}
