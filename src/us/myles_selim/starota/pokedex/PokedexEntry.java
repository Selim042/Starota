package us.myles_selim.starota.pokedex;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import sx.blah.discord.api.internal.json.objects.EmbedObject;
import sx.blah.discord.util.EmbedBuilder;
import us.myles_selim.starota.enums.EnumPokemon;
import us.myles_selim.starota.enums.EnumPokemonType;
import us.myles_selim.starota.enums.EnumWeather;

public class PokedexEntry {

	// private static final float[] LVL_MULT = { 0.094f, 0.135137432f,
	// 0.16639787f, 0.192650919f,
	// 0.21573247f, 0.236572661f, 0.25572005f, 0.273530381f, 0.29024988f,
	// 0.306057377f, 0.3210876f,
	// 0.335445036f, 0.34921268f, 0.362457751f, 0.37523559f, 0.387592406f,
	// 0.39956728f,
	// 0.411193551f, 0.42250001f, 0.432926419f, 0.44310755f, 0.4530599578f,
	// 0.46279839f,
	// 0.472336083f, 0.48168495f, 0.4908558f, 0.49985844f, 0.508701765f,
	// 0.51739395f, 0.525942511f,
	// 0.53435433f, 0.542635767f, 0.55079269f, 0.558830576f, 0.56675452f,
	// 0.574569153f, 0.58227891f,
	// 0.589887917f, 0.59740001f, 0.604818814f, 0.61215729f, 0.619399365f,
	// 0.62656713f,
	// 0.633644533f, 0.64065295f, 0.647576426f, 0.65443563f, 0.661214806f,
	// 0.667934f, 0.674577537f,
	// 0.68116492f, 0.687680648f, 0.69414365f, 0.700538673f, 0.70688421f,
	// 0.713164996f, 0.71939909f,
	// 0.725571552f, 0.7317f, 0.734741009f, 0.73776948f, 0.740785574f,
	// 0.74378943f, 0.746781211f,
	// 0.74976104f, 0.752729087f, 0.75568551f, 0.758630378f, 0.76156384f,
	// 0.764486065f, 0.76739717f,
	// 0.770297266f, 0.7731865f, 0.776064962f, 0.77893275f, 0.781790055f,
	// 0.78463697f, 0.787473578f,
	// 0.79030001f };

	// general stats
	public int id;
	public String name;
	public String form;
	public Form[] forms;
	public EnumPokemonType type1;
	public EnumPokemonType type2;
	public int maxcp;
	public int atk;
	public int sta;
	public int def;

	public int isMythical;
	public int isLegendary;
	public int generation;
	public int candyToEvolve;
	public int kmBuddyDistance;
	public float baseCaptureRate;
	private String description;
	private boolean switched = false;

	public String getDescription() {
		if (!switched)
			description = description.replaceAll("â€™", "'").replaceAll("Ã©", "é");
		switched = true;
		return description;
	}

	public float weight;
	public float height;
	public float baseFleeRate;
	public int kmDistanceToHatch;
	public int thirdMoveStardust;
	public int thirdMoveCandy;
	public EnumWeather[] weatherInfluences;

	public float male;
	public float female;
	public int genderless;

	// types
	public TypeEffectiveness[] typeChart;
	private TypeEffectiveness[] resist;
	private TypeEffectiveness[] weak;

	public TypeEffectiveness[] getResistances() {
		if (resist != null)
			return resist;
		List<TypeEffectiveness> temp = new ArrayList<>();
		for (TypeEffectiveness te : typeChart)
			if (te.status.equals("adv"))
				temp.add(te);
		resist = temp.toArray(new TypeEffectiveness[0]);
		return resist;
	}

	public TypeEffectiveness[] getWeaknesses() {
		if (weak != null)
			return weak;
		List<TypeEffectiveness> temp = new ArrayList<>();
		for (TypeEffectiveness te : typeChart)
			if (te.status.equals("dis"))
				temp.add(te);
		weak = temp.toArray(new TypeEffectiveness[0]);
		return weak;
	}

	// moves
	/**
	 * @deprecated Do not use directly, for internal use only
	 */
	@Deprecated
	private Move[] moves;
	private Move[] fast;
	private Move[] charged;

	public Move[] getMoves() {
		if (moves != null)
			return moves;
		moves = GoHubDatabase.getMoves(getPokemon());
		return moves;
	}

	public Move[] getFastMoves() {
		if (fast != null)
			return fast;
		List<Move> temp = new ArrayList<>();
		for (Move m : getMoves())
			if (m.isQuickMove == 1)
				temp.add(m);
		fast = temp.toArray(new Move[0]);
		return fast;
	}

	public Move[] getChargedMoves() {
		if (charged != null)
			return charged;
		List<Move> temp = new ArrayList<>();
		for (Move m : getMoves())
			if (m.isQuickMove != 1)
				temp.add(m);
		charged = temp.toArray(new Move[0]);
		return charged;
	}

	// movesets
	/**
	 * @deprecated Do not use directly, for internal use only
	 */
	@Deprecated
	private Moveset[] movesets;
	private Moveset[] topMovesets;

	public Moveset[] getMovesets() {
		if (movesets != null)
			return movesets;
		movesets = GoHubDatabase.getMovesets(getPokemon());
		return movesets;
	}

	public Moveset[] getTopMovesets() {
		if (topMovesets != null)
			return topMovesets;
		List<Moveset> setsL = Arrays.asList(getMovesets());
		setsL.sort(new Comparator<Moveset>() {

			@Override
			public int compare(Moveset o1, Moveset o2) {
				return -Float.compare(o1.weaveDPS, o2.weaveDPS);
			}
		});
		int max = 6;
		if (max > setsL.size())
			max = setsL.size();
		topMovesets = setsL.subList(0, max).toArray(new Moveset[0]);
		return topMovesets;
	}

	// counters
	/**
	 * @deprecated Do not use directly, for internal use only
	 */
	@Deprecated
	private Counter[] counters;
	private Counter[] topCounters;

	public Counter[] getCounters() {
		if (counters != null)
			return counters;
		counters = GoHubDatabase.getCounters(getPokemon());
		return counters;
	}

	public Counter[] getTopCounters() {
		if (topCounters != null)
			return topCounters;
		int length = 6;
		if (length > getCounters().length)
			length = getCounters().length;
		topCounters = new Counter[length];
		for (int i = 0; i < 6 && i < length; i++)
			topCounters[i] = getCounters()[i];
		return topCounters;
	}

	public EnumPokemon getPokemon() {
		return EnumPokemon.getPokemon(id);
	}

	public EmbedObject toEmbed() {
		EmbedBuilder builder = new EmbedBuilder();
		builder.withColor(this.type1.getColor());
		builder.withAuthorName("Pokémon Go Hub Database");
		builder.withAuthorIcon("https://db.pokemongohub.net/images/icons/favicon-32x32.png");
		builder.withAuthorUrl("https://db.pokemongohub.net/");
		builder.withTitle(String.format("%s #%d", this.name, this.id));
		builder.withUrl(String.format("https://db.pokemongohub.net/pokemon/%d", this.id));
		builder.withThumbnail(
				String.format("https://db.pokemongohub.net/images/official/full/%03d.png", this.id));
		builder.appendDesc(this.getDescription());

		builder.appendField("Type:",
				(this.type2 != null ? this.type1.getEmoji() + "/" + this.type2.getEmoji()
						: this.type1.getEmoji() + ""),
				true);
		String weatherString = "";
		for (EnumWeather w : this.weatherInfluences)
			weatherString += w.getEmoji();
		builder.appendField("Weather Boosts:", weatherString, true);
		if (this.forms.length != 1) {
			String formString = "";
			for (Form f : this.forms)
				formString += f.name + ", ";
			builder.appendField("Forms:", formString.substring(0, formString.length() - 2), false);
		}
		builder.appendField("Details:",
				String.format("Generation: %d\nCatch Rate: %d%%\nFlee Rate: %d%%\nBuddy Distance: %dkm",
						this.generation, (int) (this.baseCaptureRate * 100),
						(int) (this.baseFleeRate * 100), this.kmBuddyDistance),
				true);
		builder.appendField("Stats:", String.format("Max CP: %d\nAttack: %d\nDefense: %d\nStamina: %d",
				this.maxcp, this.atk, this.def, this.sta), true);

		String resistString = "";
		for (TypeEffectiveness te : this.getResistances())
			resistString += te + "\n";
		builder.appendField("Type Resistances:", resistString, true);
		String weakString = "";
		for (TypeEffectiveness te : this.getWeaknesses())
			weakString += te + "\n";
		builder.appendField("Type Weaknesses:", weakString, true);

		String fastString = "";
		for (Move m : this.getFastMoves())
			fastString += m + "\n";
		if (!fastString.isEmpty())
			builder.appendField("Fast Moves:", fastString, true);
		String chargedString = "";
		for (Move m : this.getChargedMoves())
			chargedString += m + "\n";
		if (!chargedString.isEmpty())
			builder.appendField("Charged Moves:", chargedString, true);

		String movesetString = "";
		for (Moveset ms : this.getTopMovesets())
			movesetString += ms + "\n";
		if (!movesetString.isEmpty())
			builder.appendField("Best Movesets:", movesetString, false);

		String counterString = "";
		int rank = 1;
		for (Counter c : this.getTopCounters())
			counterString += String.format("#%d %s", rank++, c);
		if (!counterString.isEmpty())
			builder.appendField("Counters:", counterString, false);

		builder.withFooterText(Move.STAB_MARKER + " denotes a STAB move, " + Move.LEGACY_MARKER
				+ " for legacy moves, and " + Move.EXCLUSIVE_MARKER + " for exclusive moves");

		return builder.build();
	}

	// classes
	public static class Form {

		public String name;
		public String value;

	}

	public static class TypeEffectiveness {

		public EnumPokemonType type;
		public String status;
		public String statusModifier;
		public float effectiveness;

		@Override
		public String toString() {
			return String.format("%s: %.1f%%", type.getEmoji(), effectiveness * 100);
		}

	}

	public static class Move {

		public int id;
		public String name;
		public EnumPokemonType type;
		public int power;
		public int duration;
		public int energy;
		public int damageWindow;
		public int energyBars;
		public int critPercentage;
		public int isQuickMove;
		public int damageWindowStart;
		public int damageWindowEnd;
		public int pvpPower;
		public int pvpEnergy;
		public int pvpDuration;
		public int pokemonId;
		public int moveId;
		public Form form;
		public int isLegacy;
		public String isLegacySince;
		public int isExclusive;
		public String isExclusiveSince;
		public TypeEffectiveness[] typeChart;
		public String[] weather;

		public boolean isSTAB() {
			EnumPokemon pokemon = EnumPokemon.getPokemon(pokemonId);
			if (pokemon == null)
				return false;
			return type.equals(pokemon.getType1()) || type.equals(pokemon.getType2());
		}

		public static final String STAB_MARKER = "*";
		public static final String LEGACY_MARKER = "†";
		public static final String EXCLUSIVE_MARKER = "‡";

		@Override
		public String toString() {
			boolean marked = false;
			String out = name;
			if (isSTAB()) {
				if (marked)
					out += " ";
				out += STAB_MARKER;
				marked = true;
			}
			if (isLegacy == 1) {
				if (marked)
					out += " ";
				out += LEGACY_MARKER;
				marked = true;
			}
			if (isExclusive == 1) {
				if (marked)
					out += " ";
				out += EXCLUSIVE_MARKER;
				marked = true;
			}
			out += type.getEmoji();
			return out;
		}

	}

	public static class Moveset {

		public Move quickMove;
		public Move chargeMove;
		public float weaveDPS;
		public float tdo;

		@Override
		public String toString() {
			return quickMove + "/" + chargeMove;
		}

	}

}
