package us.myles_selim.starota.misc.utils;

import sx.blah.discord.handle.impl.obj.ReactionEmoji;

public class EmojiConstants {

	public static final String GREEN_CHECK = "✅";
	public static final String RED_X = "❌";

	public static final String LEFT_ARROW = "⬅";
	public static final String RIGHT_ARROW = "➡";

	public static ReactionEmoji getBooleanEmoji(boolean tf) {
		return ReactionEmoji.of(tf ? GREEN_CHECK : RED_X);
	}

	public static ReactionEmoji getLeftArrowEmoji() {
		return ReactionEmoji.of(LEFT_ARROW);
	}

	public static ReactionEmoji getRightArrowEmoji() {
		return ReactionEmoji.of(RIGHT_ARROW);
	}

}
