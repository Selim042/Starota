package us.myles_selim.starota;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

import sx.blah.discord.handle.obj.IEmoji;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IRole;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.Image;

public class EmojiServerHelper {

	private static final long[] NO_UPLOAD = new long[] { //
			408997776672948224L, // Bot Emojis
	};
	private static final long[] EMOJI_SERVERS = new long[] { //
			408997776672948224L, // Bot Emojis
			533454014990385162L, // Emoji Server #1
			533472551146618880L, // Emoji Server #2
			533816836609802240L, // Emoji Server #3
	};

	public static IEmoji getEmoji(String name) {
		name = name.replaceAll("-", "_");
		for (long id : EMOJI_SERVERS) {
			IGuild guild = Starota.getGuild(id);
			IEmoji emoji = guild.getEmojiByName(name);
			if (emoji != null)
				return emoji;
		}
		return null;
	}

	public static IEmoji getEmoji(String name, String fallback) {
		name = name.replaceAll("-", "_");
		IEmoji emoji = getEmoji(name);
		if (emoji != null)
			return emoji;
		Image img = getImage(name, fallback);
		for (int i = 0; i < EMOJI_SERVERS.length; i++) {
			if (arrCont(NO_UPLOAD, EMOJI_SERVERS[i]))
				continue;
			IGuild guild = Starota.getGuild(EMOJI_SERVERS[i]);
			try {
				emoji = guild.createEmoji(name, img, new IRole[0]);
			} catch (DiscordException e) {
				if (e.getErrorMessage().contains("Maximum number of emojis reached")) {
					img = getImage(name, fallback);
					continue;
				} else
					throw e;
			}
			if (emoji != null) {
				System.out.println(
						"uploading missing emoji " + name + " to " + guild.getName() + ", " + fallback);
				return emoji;
			}
		}
		Starota.getUser(Starota.SELIM_USER_ID).getOrCreatePMChannel().sendMessage(
				"Emoji servers are full, trying to upload " + name + " with image " + fallback);
		return null;
	}

	private static Image getImage(String name, String url) {
		try {
			URL url2 = new URL(url);
			URLConnection conn = url2.openConnection();
			conn.addRequestProperty("User-Agent", Starota.HTTP_USER_AGENT);
			return Image.forStream(name, conn.getInputStream());
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

}
