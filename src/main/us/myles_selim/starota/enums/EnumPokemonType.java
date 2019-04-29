package us.myles_selim.starota.enums;

import sx.blah.discord.handle.obj.IEmoji;
import sx.blah.discord.handle.obj.IGuild;
import us.myles_selim.starota.Starota;

public enum EnumPokemonType {
	NORMAL(0xa8a878),
	FIRE(0xf08030, "firetype"),
	FIGHTING(0xc03028),
	WATER(0x6890f0),
	FLYING(0xa890f0),
	GRASS(0x78c850),
	POISON(0xa040a0),
	ELECTRIC(0xf8d030),
	GROUND(0xe0c068),
	PSYCHIC(0xf85888),
	ROCK(0xb8a038),
	ICE(0x98d8d8),
	BUG(0xa8b820, "bugtype"),
	DRAGON(0x7038f8, "dragontype"),
	GHOST(0x705898, "ghosttype"),
	DARK(0x705848),
	STEEL(0xb8b8d0),
	FAIRY(0xee99ac);

	private String name;
	private int color;
	private String emojiName;

	EnumPokemonType(int color) {
		this(color, null);
	}

	EnumPokemonType(int color, String emojiName) {
		this.name = this.name();
		this.name = name.substring(0, 1).toUpperCase() + name.substring(1, name.length()).toLowerCase();
		this.color = color;
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

	private static final long EMOJI_SERVER_ID = 408997776672948224L;
	private static IGuild EMOJI_SERVER;

	public IEmoji getEmoji() {
		if (EMOJI_SERVER == null)
			EMOJI_SERVER = Starota.getGuild(EMOJI_SERVER_ID);
		return EMOJI_SERVER.getEmojiByName(emojiName);
	}

	public static EnumPokemonType fromOrdinal(int ordinal) {
		if (ordinal < 0 || ordinal >= values().length)
			return null;
		return EnumPokemonType.values()[ordinal];
	}

}
