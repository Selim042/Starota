package us.myles_selim.starota.trading;

public enum EnumPokemonType {
	NORMAL,
	FIRE,
	FIGHTING,
	WATER,
	FLYING,
	GRASS,
	POISON,
	ELECTRIC,
	GROUND,
	PSYCHIC,
	ROCK,
	ICE,
	BUG,
	DRAGON,
	GHOST,
	DARK,
	STEEL,
	FAIRY;

	private String name;

	EnumPokemonType() {
		name = this.name();
		name = name.substring(0, 1).toUpperCase() + name.substring(1, name.length());
	}

	@Override
	public String toString() {
		return this.name;
	}

}
