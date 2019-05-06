package us.myles_selim.starota.misc.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.GuildEmoji;
import discord4j.core.object.util.Image;
import us.myles_selim.starota.Starota;

public class EmojiServerHelper {

	private static final long[] NO_UPLOAD = new long[] { //
			408997776672948224L, // Bot Emojis
	};
	private static final long[] EMOJI_SERVERS = new long[] { //
			408997776672948224L, // Bot Emojis
			533454014990385162L, // Emoji Server #1
			533472551146618880L, // Emoji Server #2
			533816836609802240L, // Emoji Server #3
			542428050134925314L, // Emoji Server #4
			561385227977752578L, // Emoji Server #5
	};

	public static boolean isEmojiServer(Guild guild) {
		return arrCont(EMOJI_SERVERS, guild.getId().asLong());
	}

	public static int getNumberServers() {
		return EMOJI_SERVERS.length;
	}

	public static GuildEmoji getEmoji(String name) {
		final String name2 = name.replaceAll("-", "_");
		for (long id : EMOJI_SERVERS) {
			Guild guild = Starota.getGuild(id);
			GuildEmoji emoji = guild.getEmojis().filter((e) -> e.getName().equalsIgnoreCase(name2))
					.blockFirst();
			if (emoji != null)
				return emoji;
		}
		return null;
	}

	public static GuildEmoji getEmoji(String name, String fallback) {
		final String name2 = name.replaceAll("-", "_");
		GuildEmoji emoji = getEmoji(name);
		if (emoji != null)
			return emoji;
		Image img = getImage(name, fallback);
		for (int i = 0; i < EMOJI_SERVERS.length; i++) {
			if (arrCont(NO_UPLOAD, EMOJI_SERVERS[i]))
				continue;
			Guild guild = Starota.getGuild(EMOJI_SERVERS[i]);
			emoji = guild.createEmoji((e) -> e.setName(name2).setImage(img)).block();
			if (emoji != null) {
				System.out.println(
						"uploading missing emoji " + name + " to " + guild.getName() + ", " + fallback);
				return emoji;
			}
		}
		Starota.getUser(StarotaConstants.SELIM_USER_ID.asLong()).getPrivateChannel().block()
				.createMessage(
						"Emoji servers are full, trying to upload " + name + " with image " + fallback);
		return null;
	}

	private static Image getImage(String name, String url) {
		try {
			URL url2 = new URL(url);
			URLConnection conn = url2.openConnection();
			conn.addRequestProperty("User-Agent", StarotaConstants.HTTP_USER_AGENT);
			return Image.ofRaw(toByteArray(conn.getInputStream()), getImageFormat(url));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	private static final boolean arrCont(long[] arr, long val) {
		for (long l : arr)
			if (l == val)
				return true;
		return false;
	}

	private static byte[] toByteArray(InputStream stream) {
		try {
			byte[] arr = new byte[stream.available()];
			stream.read(arr);
			return arr;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new byte[0];
	}

	private static Image.Format getImageFormat(String url) {
		for (Image.Format f : Image.Format.values())
			if (url.matches("(?i).*\\." + f.getExtension() + ".*"))
				return f;
		return null;
	}

}
