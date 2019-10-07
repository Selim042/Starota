package us.myles_selim.starota.enums;

import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.GuildEmoji;
import us.myles_selim.starota.Starota;

public enum EnumWeather {

	EXTREME,
	CLEAR("clear", EnumPokemonType.FIRE, EnumPokemonType.GRASS, EnumPokemonType.GROUND),
	PARTLY_CLOUDY("partlycloudy", EnumPokemonType.NORMAL, EnumPokemonType.ROCK),
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

	public boolean isBoosted(EnumPokemon pokemon) {
		for (EnumPokemonType t : this.types)
			if (t.equals(pokemon.getType1()) || t.equals(pokemon.getType2()))
				return true;
		return false;
	}

	@Override
	public String toString() {
		return this.name.replaceAll("_", " ");
	}

	private static final long EMOJI_SERVER_ID = 408997776672948224L;
	private static Guild EMOJI_SERVER;

	public GuildEmoji getEmoji() {
		if (EMOJI_SERVER == null)
			EMOJI_SERVER = Starota.getGuild(EMOJI_SERVER_ID);
		return EMOJI_SERVER.getEmojis().filter((e) -> e.getName().equals(emojiName)).blockFirst();
	}

}
