package us.myles_selim.starota.pvp.pokebattler;

public enum EnumPokemonConditionType {

	WITH_POKEMON_CP_LIMIT("withPokemonCpLimit", PokemonConditionCPLimit.class),
	WITH_POKEMON_TYPE("withPokemonType", PokemonConditionType.class),
	WITH_POKEMON_CATEGORY("withPokemonCategory", PokemonConditionCategory.class);

	private String dataName;
	private Class<?> typeClass;

	private EnumPokemonConditionType(String dataName, Class<?> typeClass) {
		this.dataName = dataName;
		this.typeClass = typeClass;
	}

	public String getDataName() {
		return this.dataName;
	}

	public Class<?> getTypeClass() {
		return typeClass;
	}

}
