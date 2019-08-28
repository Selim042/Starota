package us.myles_selim.starota.enums;

import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.GuildEmoji;
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.misc.utils.EmojiServerHelper;

public enum EnumPokemonType {
	NORMAL(0xa8a878, 1.0f),
	FIRE(0xf08030, 1.25f, "firetype"),
	FIGHTING(0xc03028, 1.5f),
	WATER(0x6890f0, 1.25f),
	FLYING(0xa890f0, 1.0f),
	GRASS(0x78c850, 1.25f),
	POISON(0xa040a0, 1.0f),
	ELECTRIC(0xf8d030, 1.25f),
	GROUND(0xe0c068, 1.25f),
	PSYCHIC(0xf85888, 1.5f),
	ROCK(0xb8a038, 1.25f),
	ICE(0x98d8d8, 1.5f),
	BUG(0xa8b820, 1.0f, "bugtype"),
	DRAGON(0x7038f8, 1.75f, "dragontype"),
	GHOST(0x705898, 1.5f, "ghosttype"),
	DARK(0x705848, 1.5f),
	STEEL(0xb8b8d0, 1.75f),
	FAIRY(0xee99ac, 1.75f);

	private String name;
	private int color;
	private float pointMult;
	private String emojiName;

	EnumPokemonType(int color, float pointMult) {
		this(color, pointMult, null);
	}

	EnumPokemonType(int color, float pointMult, String emojiName) {
		this.name = this.name();
		this.name = name.substring(0, 1).toUpperCase() + name.substring(1, name.length()).toLowerCase();
		this.color = color;
		this.pointMult = pointMult;
		if (emojiName != null)
			this.emojiName = emojiName;
		else
			this.emojiName = name.toLowerCase();
	}

	@Override
	public String toString() {
		return this.name;
	}

	public int getColor() {
		return this.color;
	}

	public float getPointMult() {
		return this.pointMult;
	}

	private static final long EMOJI_SERVER_ID = 408997776672948224L;
	private static Guild EMOJI_SERVER;

	public GuildEmoji getEmoji() {
		if (EMOJI_SERVER == null)
			EMOJI_SERVER = Starota.getGuild(EMOJI_SERVER_ID);
		return EMOJI_SERVER.getEmojis().filter((e) -> e.getName().equals(emojiName)).blockFirst();
	}

	public GuildEmoji getEmojiDoubleEffective() {
		if (EMOJI_SERVER == null)
			EMOJI_SERVER = Starota.getGuild(EMOJI_SERVER_ID);
		return EmojiServerHelper.getGuildEmoji(emojiName + "_2x");
	}

	public static EnumPokemonType fromOrdinal(int ordinal) {
		if (ordinal < 0 || ordinal >= values().length)
			return null;
		return EnumPokemonType.values()[ordinal];
	}

}
