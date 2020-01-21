package us.myles_selim.starota.enums;

import us.myles_selim.starota.forms.Form;
import us.myles_selim.starota.forms.FormManager;
import us.myles_selim.starota.forms.FormSet;
import us.myles_selim.starota.silph_road.SilphRoadData;

public class PokemonData {

	private final EnumPokemon pokemon;
	private final String name;
	private final EnumPokemonStage stage;
	private final EnumPokemonType type1;
	private final EnumPokemonType type2;
	private final EnumGender gender;
	private final EnumPokemon[] family;
	private final int generation;
	private final boolean isStarter;
	private final boolean evolvesWithItem;
	private final String region;

	private PokemonData(PokemonDataBuilder builder) {
		if (builder.pokemon == null)
			throw new IllegalArgumentException("no Pokemon set");
		this.pokemon = builder.pokemon;
		if (builder.name == null)
			this.name = builder.pokemon.name().substring(0, 1).toUpperCase()
					+ builder.pokemon.name().substring(1, builder.pokemon.name().length()).toLowerCase();
		else
			this.name = builder.name;
		if (builder.stage == null)
			throw new IllegalArgumentException("no stage set");
		this.stage = builder.stage;
		if (builder.type1 == null)
			throw new IllegalArgumentException("no type1 set");
		this.type1 = builder.type1;
		this.type2 = builder.type2;
		if (builder.gender == null)
			throw new IllegalArgumentException("no gender set");
		this.gender = builder.gender;
		this.family = builder.family;
		if (builder.generation == null)
			throw new IllegalArgumentException("no generation set");
		this.generation = builder.generation;
		this.isStarter = builder.isStarter;
		this.evolvesWithItem = builder.evolvesWithItem;
		this.region = builder.region;
	}

	public EnumPokemon getPokemon() {
		return pokemon;
	}

	public String getName() {
		return name;
	}

	public EnumPokemonStage getStage() {
		return stage;
	}

	public EnumPokemonType getType1() {
		return type1;
	}

	public EnumPokemonType getType2() {
		return type2;
	}

	public FormSet getFormSet() {
		return FormManager.getForms(pokemon);
	}

	public Form getDefaultForm() {
		return getFormSet().getDefaultForm();
	}

	public EnumGender getGenderPossible() {
		return gender;
	}

	/**
	 * @deprecated Not functional yet
	 */
	@Deprecated
	public EnumPokemon[] getFamily() {
		return family;
	}

	public final int getId() {
		return getPokemon().ordinal() + 1;
	}

	public final boolean isTradable() {
		if (!SilphRoadData.isAvailable(getPokemon()))
			return false;
		switch (getPokemon()) {
		case MELTAN:
		case MELMETAL:
			return true;
		default:
			return !isMythical();
		}
	}

	public final boolean isLegendary() {
		return getStage() == EnumPokemonStage.LEGEND;
	}

	public final boolean isMythical() {
		return getStage() == EnumPokemonStage.MYTHIC;
	}

	public final int getGeneration() {
		return this.generation;
	}

	public final boolean isStarter() {
		return this.isStarter;
	}

	public final boolean isBaby() {
		return getStage() == EnumPokemonStage.BABY;
	}

	public final boolean evolvesWithItem() {
		return this.evolvesWithItem;
	}

	public final boolean isRegional() {
		return this.region != null;
	}

	public final String getRegion() {
		return this.region;
	}

	// public final String getArtwork(int formId) {
	// return ImageHelper.getOfficalArtwork(getPokemon(), formId);
	// }

	public static class PokemonDataBuilder {

		private EnumPokemon pokemon;
		private String name;
		private EnumPokemonStage stage;
		private EnumPokemonType type1;
		private EnumPokemonType type2;
		private EnumGender gender;
		private EnumPokemon[] family;
		private Integer generation;
		private boolean isStarter;
		private boolean evolvesWithItem;
		private String region;

		public PokemonDataBuilder() {}

		/**
		 * Required
		 */
		public PokemonDataBuilder withPokemon(EnumPokemon pokemon) {
			this.pokemon = pokemon;
			return this;
		}

		/**
		 * Optional, defaults to enum name
		 */
		public PokemonDataBuilder withName(String name) {
			this.name = name;
			return this;
		}

		/**
		 * Required
		 */
		public PokemonDataBuilder withStage(EnumPokemonStage stage) {
			this.stage = stage;
			return this;
		}

		/**
		 * Required
		 */
		public PokemonDataBuilder withType1(EnumPokemonType type1) {
			this.type1 = type1;
			return this;
		}

		/**
		 * Optional
		 */
		public PokemonDataBuilder withType2(EnumPokemonType type2) {
			this.type2 = type2;
			return this;
		}

		/**
		 * Optional, defaults to {@link EnumGender.EITHER}
		 */
		public PokemonDataBuilder withGender(EnumGender gender) {
			this.gender = gender;
			return this;
		}

		/**
		 * Optional, defaults to no family
		 */
		public PokemonDataBuilder withFamily(EnumPokemon[] family) {
			this.family = family;
			return this;
		}

		/**
		 * Required
		 */
		public PokemonDataBuilder withGeneration(int generation) {
			this.generation = generation;
			return this;
		}

		/**
		 * Optional, defaults to false
		 */
		public PokemonDataBuilder isStarter(boolean isStarter) {
			this.isStarter = isStarter;
			return this;
		}

		/**
		 * Optional, defaults to false
		 */
		public PokemonDataBuilder evolvesWithItem(boolean withItem) {
			this.evolvesWithItem = withItem;
			return this;
		}

		/**
		 * Optional, defaults to false
		 */
		public PokemonDataBuilder withRegion(String region) {
			this.region = region;
			return this;
		}

		public PokemonData build() {
			return new PokemonData(this);
		}

	}

}
