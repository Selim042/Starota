package us.myles_selim.starota.misc.utils;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.GuildEmoji;
import discord4j.core.object.reaction.ReactionEmoji;
import discord4j.core.object.util.Image;
import discord4j.core.object.util.Image.Format;
import discord4j.core.object.util.Snowflake;
import reactor.netty.http.client.HttpClient;
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
			602545528630411264L, // Emoji Server #6
			625149217186381850L, // Emoji Server #7
			625149512360525846L, // Emoji Server #8
			629422842462601216L, // Emoji Server #9
			629422895424339978L, // Emoji Server #10
			629422934951460885L, // Emoji Server #11
			629423110914834476L, // Emoji Server #12
	};

	public static long[] getEmojiServers() {
		return Arrays.copyOf(EMOJI_SERVERS, EMOJI_SERVERS.length);
	}

	public static long[] getNoUploadEmojiServers() {
		return Arrays.copyOf(NO_UPLOAD, NO_UPLOAD.length);
	}

	public static boolean isEmojiServer(Guild guild) {
		return isEmojiServer(guild.getId());
	}

	public static boolean isEmojiServer(Snowflake id) {
		return arrCont(EMOJI_SERVERS, id.asLong());
	}

	public static int getNumberServers() {
		return EMOJI_SERVERS.length;
	}

	public static GuildEmoji getGuildEmoji(String name) {
		if (!Starota.FULLY_STARTED)
			return null;
		final String name2 = name.replaceAll("-", "_");
		for (long id : EMOJI_SERVERS) {
			Guild guild = Starota.getGuild(id);
			List<GuildEmoji> emojis = guild.getEmojis()
					.collect(LinkedList::new, (LinkedList<GuildEmoji> l, GuildEmoji e) -> {
						if (e.getName().equals(name2))
							l.add(e);
					}).block();
			if (emojis.size() >= 1)
				return emojis.get(0);
		}
		return null;
	}

	public static ReactionEmoji.Custom getEmoji(String name) {
		if (!Starota.FULLY_STARTED)
			return null;
		return ReactionEmoji.custom(getGuildEmoji(name));
	}

	public static GuildEmoji getGuildEmoji(String name, String fallback) {
		if (!Starota.FULLY_STARTED)
			return null;
		String namef = name.replaceAll(" ", "_").replaceAll("('|\\.)", "").replaceAll("-", "_")
				.replaceAll("♂", "M").replaceAll("♀", "F");
		GuildEmoji emoji = getGuildEmoji(namef);
		if (emoji != null)
			return emoji;
		Image img = getImage(fallback);
		for (int i = 0; i < EMOJI_SERVERS.length; i++) {
			if (arrCont(NO_UPLOAD, EMOJI_SERVERS[i]))
				continue;
			Guild guild = Starota.getGuild(EMOJI_SERVERS[i]);
			GuildEmoji gemoji = null;
			if (guild.getEmojiIds().size() >= 50)
				continue;
			gemoji = guild.createEmoji((e) -> {
				e.setName(namef);
				e.setImage(img);
			}).block();
			if (gemoji != null) {
				System.out.println(
						"uploading missing emoji " + namef + " to " + guild.getName() + ", " + fallback);
				return gemoji;
			}
		}
		Starota.getClient().getUserById(StarotaConstants.SELIM_USER_ID).block().getPrivateChannel()
				.block()
				.createMessage(
						"Emoji servers are full, trying to upload " + namef + " with image " + fallback)
				.block();
		return null;
	}

	public static ReactionEmoji.Custom getEmoji(String name, String fallback) {
		return ReactionEmoji.custom(getGuildEmoji(name, fallback));
	}

	private static Image getImage(String url) {
		return HttpClient.create().get().uri(url)
				.responseSingle((res, body) -> body.asByteArray().map(image -> {
					String formatName = res.responseHeaders().get("Content-Type").replace("image/", "");
					Format format = null;
					for (Image.Format f : Image.Format.values())
						if (f.name().equalsIgnoreCase(formatName))
							format = f;
					if (format == null) {
						System.out.println("[[[ ERROR ]]] cannot find image format " + formatName);
						return null;
					}
					return Image.ofRaw(image, format);
				})).block();
	}

	private static final boolean arrCont(long[] arr, long val) {
		for (long l : arr)
			if (l == val)
				return true;
		return false;
	}

}
