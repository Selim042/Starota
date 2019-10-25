package us.myles_selim.starota.forms;

import us.myles_selim.starota.enums.EnumPokemon;
import us.myles_selim.starota.enums.EnumPokemonType;
import us.myles_selim.starota.silph_road.SilphRoadData;

public class Form {

	private String name;
	private int goHubFormId;
	private String goHubFormName;
	private String image;
	private EnumPokemonType type1;
	private EnumPokemonType type2;
	private boolean shinyable;
	private String emojiPostfix;

	protected Form() {}

	public String getName() {
		return name;
	}

	public int getGoHubFormId() {
		return goHubFormId;
	}

	public String getGoHubFormName() {
		return goHubFormName;
	}

	public String getImage() {
		return image;
	}

	public EnumPokemonType getType1() {
		return type1;
	}

	public EnumPokemonType getType2() {
		return type2;
	}

	public boolean isShinyable() {
		return shinyable;
	}

	public String getEmojiPostfix() {
		return emojiPostfix;
	}

	/**
	 * Just for conversion from old setup
	 */
	protected void setName(String name) {
		this.name = name;
	}

	/**
	 * Just for conversion from old setup
	 */
	protected void setGoHubFormId(int goHubFormId) {
		this.goHubFormId = goHubFormId;
	}

	/**
	 * Just for conversion from old setup
	 */
	protected void setGoHubFormName(String goHubFormName) {
		this.goHubFormName = goHubFormName;
	}

	/**
	 * Just for conversion from old setup
	 */
	protected void setImage(String image) {
		this.image = image;
	}

	/**
	 * Just for conversion from old setup
	 */
	protected void setType1(EnumPokemonType type1) {
		this.type1 = type1;
	}

	/**
	 * Just for conversion from old setup
	 */
	protected void setType2(EnumPokemonType type2) {
		this.type2 = type2;
	}

	/**
	 * Just for conversion from old setup
	 */
	protected void setShinyable(boolean shinyable) {
		this.shinyable = shinyable;
	}

	/**
	 * Just for conversion from old setup
	 */
	protected void setEmojiPostfix(String emojiPostfix) {
		this.emojiPostfix = emojiPostfix;
	}

	protected static final class NormalDefaultForm extends Form {

		private final EnumPokemon pokemon;

		public NormalDefaultForm(EnumPokemon pokemon) {
			this.pokemon = pokemon;
		}

		@Override
		public String getName() {
			return "Normal";
		}

		@Override
		public int getGoHubFormId() {
			return 0;
		}

		@Override
		public String getGoHubFormName() {
			return null;
		}

		@Override
		public String getImage() {
			return super.getImage();
		}

		@Override
		public EnumPokemonType getType1() {
			return pokemon.getData().getType1();
		}

		@Override
		public EnumPokemonType getType2() {
			return pokemon.getData().getType2();
		}

		@Override
		public boolean isShinyable() {
			return SilphRoadData.isShinyable(pokemon);
		}

		@Override
		public String getEmojiPostfix() {
			return null;
		}

	}

}
