package us.myles_selim.starota.misc.utils;

import sx.blah.discord.handle.impl.obj.ReactionEmoji;

public class EmojiConstants {

	public static final String GREEN_CHECK = "✅";
	public static final String RED_X = "❌";

	public static ReactionEmoji getBooleanEmoji(boolean tf) {
		return ReactionEmoji.of(tf ? GREEN_CHECK : RED_X);
	}

}
