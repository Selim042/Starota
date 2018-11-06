package us.myles_selim.starota.trading;

public enum EnumPokemonType {
	NORMAL(0xa8a878),
	FIRE(0xf08030),
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
	BUG(0xa8b820),
	DRAGON(0x7038f8),
	GHOST(0x705898),
	DARK(0x705848),
	STEEL(0xb8b8d0),
	FAIRY(0xee99ac);

	private String name;
	private int color;

	EnumPokemonType(int color) {
		name = this.name();
		name = name.substring(0, 1).toUpperCase() + name.substring(1, name.length());
		this.color = color;
	}

	@Override
	public String toString() {
		return this.name;
	}

	public int getColor() {
		return this.color;
	}

}
