package us.myles_selim.starota.pokedex;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import sx.blah.discord.api.internal.json.objects.EmbedObject;
import sx.blah.discord.handle.obj.IEmoji;
import sx.blah.discord.util.EmbedBuilder;
import us.myles_selim.starota.enums.EnumPokemon;
import us.myles_selim.starota.enums.EnumPokemonType;
import us.myles_selim.starota.enums.EnumWeather;
import us.myles_selim.starota.misc.utils.EmojiServerHelper;
import us.myles_selim.starota.misc.utils.ImageHelper;
import us.myles_selim.starota.misc.utils.MiscUtils;
import us.myles_selim.starota.reaction_messages.ReactionMessage;

public class PokedexEntry extends ReactionMessage {

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
	public DexForm[] forms;
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
			description = MiscUtils.fixCharacters(description);
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

	public DexCPs CPs;

	// types
	public DexTypeEffectiveness[] typeChart;
	private DexTypeEffectiveness[] resist;
	private DexTypeEffectiveness[] weak;

	public DexTypeEffectiveness[] getResistances() {
		if (resist != null)
			return resist;
		List<DexTypeEffectiveness> temp = new ArrayList<>();
		for (DexTypeEffectiveness te : typeChart)
			if (te.status.equals("adv"))
				temp.add(te);
		resist = temp.toArray(new DexTypeEffectiveness[0]);
		return resist;
	}

	public DexTypeEffectiveness[] getWeaknesses() {
		if (weak != null)
			return weak;
		List<DexTypeEffectiveness> temp = new ArrayList<>();
		for (DexTypeEffectiveness te : typeChart)
			if (te.status.equals("dis"))
				temp.add(te);
		weak = temp.toArray(new DexTypeEffectiveness[0]);
		return weak;
	}

	// form handling
	private Map<String, PokedexEntry> formData = new ConcurrentHashMap<>();

	public PokedexEntry getFormData(String form) {
		if (form.equals("Normal") && !getPokemon().equals(EnumPokemon.ARCEUS))
			return this;
		if (formData.containsKey(form))
			return formData.get(form);
		PokedexEntry entry = GoHubDatabase.getEntry(getPokemon(), form);
		formData.put(form, entry);
		return entry;
	}

	// moves
	/**
	 * @deprecated Do not use directly, for internal use only
	 */
	@Deprecated
	private DexMove[] moves;
	private DexMove[] fast;
	private DexMove[] charged;

	public DexMove[] getMoves(String form) {
		if (moves != null)
			return moves;
		moves = GoHubDatabase.getMoves(getPokemon(), form);
		return moves;
	}

	public DexMove[] getFastMoves() {
		if (fast != null)
			return fast;
		List<DexMove> temp = new ArrayList<>();
		for (DexMove m : getMoves(form))
			if (m.isQuickMove == 1)
				temp.add(m);
		fast = temp.toArray(new DexMove[0]);
		return fast;
	}

	public DexMove[] getChargedMoves() {
		if (charged != null)
			return charged;
		List<DexMove> temp = new ArrayList<>();
		for (DexMove m : getMoves(form))
			if (m.isQuickMove != 1)
				temp.add(m);
		charged = temp.toArray(new DexMove[0]);
		return charged;
	}

	// movesets
	/**
	 * @deprecated Do not use directly, for internal use only
	 */
	@Deprecated
	private DexMoveset[] movesets;
	private DexMoveset[] topMovesets;

	public DexMoveset[] getMovesets() {
		if (movesets != null)
			return movesets;
		movesets = GoHubDatabase.getMovesets(getPokemon(), form);
		return movesets;
	}

	public DexMoveset[] getTopMovesets() {
		if (topMovesets != null)
			return topMovesets;
		List<DexMoveset> setsL = Arrays.asList(getMovesets());
		setsL.sort(new Comparator<DexMoveset>() {

			@Override
			public int compare(DexMoveset o1, DexMoveset o2) {
				return -Float.compare(o1.weaveDPS, o2.weaveDPS);
			}
		});
		int max = 6;
		if (max > setsL.size())
			max = setsL.size();
		topMovesets = setsL.subList(0, max).toArray(new DexMoveset[0]);
		return topMovesets;
	}

	// counters
	/**
	 * @deprecated Do not use directly, for internal use only
	 */
	@Deprecated
	private DexCounter[] counters;
	private DexCounter[] topCounters;

	public DexCounter[] getCounters() {
		if (counters != null)
			return counters;
		counters = GoHubDatabase.getCounters(getPokemon(), form);
		return counters;
	}

	public DexCounter[] getTopCounters() {
		if (topCounters != null)
			return topCounters;
		int length = 6;
		if (getCounters() == null)
			return new DexCounter[0];
		if (length > getCounters().length)
			length = getCounters().length;
		topCounters = new DexCounter[length];
		DexCounter[] allCounters = getCounters();
		int index = 0;
		for (DexCounter c : allCounters) {
			if (index >= length)
				break;
			if (!EnumPokemon.getPokemon(c.pokemonId).isAvailable())
				continue;
			topCounters[index++] = c;
		}
		return topCounters;
	}

	public EnumPokemon getPokemon() {
		return EnumPokemon.getPokemon(id);
	}

	// embeds
	private final Map<String, EmbedObject> embeds = new ConcurrentHashMap<>();

	public boolean hasEmbedPrepared(String form) {
		if (form == null)
			form = "Normal";
		return embeds.containsKey(form);
	}

	public EmbedObject toEmbed() {
		return toEmbed("Normal");
	}

	public EmbedObject toEmbed(String form) {
		if (form == null)
			form = "Normal";
		if (embeds.containsKey(form))
			return embeds.get(form);
		int formId = getFormId(form);
		PokedexEntry entry = getFormData(form);

		EmbedBuilder builder = new EmbedBuilder();
		builder.withColor(entry.type1.getColor());
		builder.withAuthorName("Pokémon Go Hub Database");
		builder.withAuthorIcon("https://db.pokemongohub.net/images/icons/favicon-32x32.png");
		builder.withAuthorUrl("https://db.pokemongohub.net/");
		String pokeName = entry.name;
		// if (entry.form == null && entry.forms.length > 1)
		// entry.form = forms[0].value;
		String embForm = entry.form;
		if (entry.form == null)
			embForm = "Normal";
		if (entry.forms.length > 1)
			pokeName += String.format(" (%s)", embForm);
		builder.withTitle(String.format("%s #%d", pokeName, entry.id));
		builder.withUrl(String.format("https://db.pokemongohub.net/pokemon/%d", entry.id));
		builder.withThumbnail(entry.getPokemon().getArtwork(formId));
		builder.appendDesc(entry.getDescription());

		// stats
		builder.appendField("Type:",
				(entry.type2 != null ? entry.type1.getEmoji() + "" + entry.type2.getEmoji()
						: entry.type1.getEmoji() + ""),
				true);
		String weatherString = "";
		for (EnumWeather w : entry.weatherInfluences)
			weatherString += w.getEmoji();
		builder.appendField("Weather Boosts:", weatherString, true);
		if (entry.forms.length != 1) {
			String formString = "";
			for (int i = 0; i < entry.forms.length; i++) {
				DexForm f = entry.forms[i];
				formString += f.name + ", ";
			}
			builder.appendField("Forms:", formString.substring(0, formString.length() - 2), false);
		}
		builder.appendField("Details:",
				String.format("Generation: %d\nCatch Rate: %d%%\nFlee Rate: %d%%\nBuddy Distance: %dkm",
						entry.generation, (int) (entry.baseCaptureRate * 100),
						(int) (entry.baseFleeRate * 100), entry.kmBuddyDistance),
				true);
		builder.appendField("Stats:", String.format("Max CP: %d\nAttack: %d\nDefense: %d\nStamina: %d",
				entry.maxcp, entry.atk, entry.def, entry.sta), true);

		// types
		String resistString = "";
		for (DexTypeEffectiveness te : entry.getResistances())
			resistString += te + "\n";
		builder.appendField("Type Resistances:", resistString, true);
		String weakString = "";
		for (DexTypeEffectiveness te : entry.getWeaknesses())
			weakString += te + "\n";
		builder.appendField("Type Weaknesses:", weakString, true);

		// moves
		String fastString = "";
		for (DexMove m : entry.getFastMoves())
			fastString += m.toString(entry) + "\n";
		if (!fastString.isEmpty())
			builder.appendField("Fast Moves:",
					fastString.substring(0, fastString.length() > 1024 ? 1024 : fastString.length()),
					true);
		String chargedString = "";
		for (DexMove m : entry.getChargedMoves())
			chargedString += m.toString(entry) + "\n";
		if (!chargedString.isEmpty())
			builder.appendField("Charged Moves:", chargedString.substring(0,
					chargedString.length() > 1024 ? 1024 : chargedString.length()), true);

		// movesets
		String movesetString = "";
		for (DexMoveset ms : entry.getTopMovesets())
			movesetString += ms.toString(entry) + "\n";
		if (!movesetString.isEmpty())
			builder.appendField("Best Movesets:", movesetString.substring(0,
					movesetString.length() > 1024 ? 1024 : movesetString.length()), false);

		// counters
		String counterString = "";
		int rank = 1;
		for (DexCounter c : entry.getTopCounters())
			if (c != null)
				counterString += String.format("#%d %s", rank++, c);
		if (!counterString.isEmpty())
			builder.appendField("Counters:", counterString, false);

		builder.withFooterText(DexMove.STAB_MARKER.replaceAll("\\\\", "") + " denotes a STAB move, "
				+ DexMove.LEGACY_MARKER.replaceAll("\\\\", "") + " for legacy moves, and "
				+ DexMove.EXCLUSIVE_MARKER.replaceAll("\\\\", "") + " for exclusive moves");

		if (entry.forms.length > 1)
			builder.appendField("Reaction Usage:",
					"React with a form of the Pokemon shown to get information about the given form.",
					false);

		EmbedObject embed = builder.build();
		embeds.put(form, embed);
		return embed;
	}

	private int getFormId(String form) {
		if (form == null || this.forms == null || this.forms.length == 1)
			return 0;
		for (int i = 0; i < this.forms.length; i++)
			if (form.equals(this.forms[i].name))
				return i;
		return 0;
	}

	// classes
	public static class DexForm {

		public String name;
		public String value;

		public IEmoji getEmoji(PokedexEntry entry) {
			if (entry.getPokemon().equals(EnumPokemon.ARCEUS)) {
				EnumPokemonType type = EnumPokemonType.valueOf(name.toUpperCase());
				return type.getEmoji();
			}
			return EmojiServerHelper.getEmoji(entry.name + "_" + name,
					ImageHelper.getOfficalArtwork(entry.getPokemon(), entry.getFormId(name)));
		}

	}

	public static class DexTypeEffectiveness {

		public EnumPokemonType type;
		public String status;
		public String statusModifier;
		public float effectiveness;

		@Override
		public String toString() {
			return String.format("%s: %.1f%%", type.getEmoji(), effectiveness * 100);
		}

	}

	public static class DexMove {

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
		public String form;
		public int isLegacy;
		public String isLegacySince;
		public int isExclusive;
		public String isExclusiveSince;
		public DexTypeEffectiveness[] typeChart;
		public String[] weather;

		public boolean isSTAB(PokedexEntry entry) {
			return type.equals(entry.type1) || type.equals(entry.type2);
		}

		public static final String STAB_MARKER = "\\*";
		public static final String LEGACY_MARKER = "†";
		public static final String EXCLUSIVE_MARKER = "‡"; // �

		public String toString(PokedexEntry entry) {
			boolean marked = false;
			String out = name;
			if (isSTAB(entry)) {
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

		@Override
		public String toString() {
			boolean marked = false;
			String out = name;
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

	public static class DexMoveset {

		public DexMove quickMove;
		public DexMove chargeMove;
		public float weaveDPS;
		public float tdo;

		public String toString(PokedexEntry entry) {
			return quickMove.toString(entry) + "/" + chargeMove.toString(entry);
		}

		@Override
		public String toString() {
			return quickMove + "/" + chargeMove;
		}

	}

	public static class DexCPs {

		public int max;
		public int wildMax;
		public int wildMin;
		public int weatherMax;
		public int weatherMin;
		public int raidBossTier1;
		public int raidBossTier2;
		public int raidBossTier3;
		public int raidBossTier4;
		public int raidBossTier5;
		public int raidBossTier6;
		public int raidCaptureMax;
		public int raidCaptureMin;
		public int raidCaptureBoostMax;
		public int raidCaptureBoostMin;
		public int eggMax;
		public int eggMin;
		public int questMax;
		public int questMin;

	}

	public static class DexCounter {

		public int pokemonId;
		public String name;
		public String formName;
		public DexMove quickMove;
		public DexMove chargedMove;
		public int deaths;
		public float ttw;
		public float score;

		@Override
		public String toString() {
			return String.format("**%s%s**: %s/%s\n", formName == null ? "" : formName + " ", name,
					quickMove, chargedMove);
		}

	}

}
