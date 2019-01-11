package us.myles_selim.starota.enums;

import sx.blah.discord.handle.obj.IEmoji;
import sx.blah.discord.handle.obj.IGuild;
import us.myles_selim.starota.Starota;

public enum EnumWeather {
	EXTREME,
	CLEAR_SUNNY("clear", EnumPokemonType.FIRE, EnumPokemonType.GRASS, EnumPokemonType.GROUND),
	PARTY_CLOUDY("partycloudy", EnumPokemonType.NORMAL, EnumPokemonType.ROCK),
	CLOUDY(EnumPokemonType.FAIRY, EnumPokemonType.FIGHTING, EnumPokemonType.POISON),
	RAIN(EnumPokemonType.WATER, EnumPokemonType.ELECTRIC, EnumPokemonType.BUG),
	SNOW(EnumPokemonType.ICE, EnumPokemonType.STEEL),
	FOG("fogweather", EnumPokemonType.DARK, EnumPokemonType.GHOST),
	WINDY(EnumPokemonType.DRAGON, EnumPokemonType.FLYING, EnumPokemonType.PSYCHIC);

	private String name;
	private String emojiName;
	private EnumPokemonType[] types;

	EnumWeather(EnumPokemonType... types) {
		this(null, types);
	}

	EnumWeather(String emojiName, EnumPokemonType... types) {
		this.name = this.name();
		this.name = name.substring(0, 1).toUpperCase() + name.substring(1, name.length()).toLowerCase();
		if (emojiName == null)
			this.emojiName = name().toLowerCase();
		else
			this.emojiName = emojiName;
		this.types = types;
	}

	public EnumPokemonType[] getBoostedTypes() {
		return this.types;
	}

	@Override
	public String toString() {
		return this.name;
	}

	private static final long EMOJI_SERVER_ID = 408997776672948224L;
	private static IGuild EMOJI_SERVER;

	public IEmoji getEmoji() {
		if (EMOJI_SERVER == null)
			EMOJI_SERVER = Starota.getGuild(EMOJI_SERVER_ID);
		return EMOJI_SERVER.getEmojiByName(emojiName);
	}

}
