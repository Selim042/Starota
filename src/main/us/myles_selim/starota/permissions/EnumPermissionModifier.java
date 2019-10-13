package us.myles_selim.starota.permissions;

import java.util.HashMap;
import java.util.Map;

import discord4j.core.object.reaction.ReactionEmoji;

public enum EnumPermissionModifier {

	GRANT("+", "✅"),
	REVOKE("-", "❌"),
	NEUTRAL("", "⚪"),;

	private static final Map<String, EnumPermissionModifier> CHARS_MAPPING = new HashMap<>();

	static {
		for (EnumPermissionModifier mod : values())
			CHARS_MAPPING.put(mod.chars, mod);
	}

	public static EnumPermissionModifier fromChars(String chars) {
		return CHARS_MAPPING.get(chars);
	}

	private final String chars;
	private final String emojiRaw;

	EnumPermissionModifier(String chars, String emojiName) {
		this.chars = chars;
		this.emojiRaw = emojiName;
	}

	public String getChars() {
		return chars;
	}

	public ReactionEmoji getEmoji() {
		return ReactionEmoji.unicode(emojiRaw);
	}

}
