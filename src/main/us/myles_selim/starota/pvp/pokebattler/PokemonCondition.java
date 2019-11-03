package us.myles_selim.starota.pvp.pokebattler;

public abstract class PokemonCondition {

	protected PokemonCondition() {}

	private EnumPokemonConditionType type;

	public EnumPokemonConditionType getType() {
		return type;
	}

}
