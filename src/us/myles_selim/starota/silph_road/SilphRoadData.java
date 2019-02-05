package us.myles_selim.starota.silph_road;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import sx.blah.discord.api.internal.json.objects.EmbedObject;
import sx.blah.discord.util.EmbedBuilder;
import us.myles_selim.starota.CachedData;
import us.myles_selim.starota.EmojiServerHelper;
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.enums.EnumPokemon;
import us.myles_selim.starota.trading.forms.FormSet.Form;

public class SilphRoadData {

	public static final EmbedObject LOADING_EMBED;

	static {
		EmbedBuilder builder = new EmbedBuilder();
		builder.appendDesc("Loading Silph Road... " + EmojiServerHelper.getEmoji("loading"));
		LOADING_EMBED = builder.build();
	}

	private static final String BOSSES_URL = "https://thesilphroad.com/raid-bosses";

	private static final Pattern BOSS_GENERAL_PATTERN = Pattern
			.compile("<h4>.*?<\\/h4>.*?(\"raid-boss-tier-wrap\"|\"footer\")");
	private static final Pattern BOSS_TIER_PATTERN = Pattern.compile("<h4>.*?<\\/h4>");
	private static final Pattern BOSS_NAME_PATTERN = Pattern.compile("\"boss-name\">.*?<\\/div>");

	private static CachedData<List<RaidBoss>> BOSSES;
	private static List<RaidBoss>[] TIERED_BOSSES;

	public static List<RaidBoss> getBosses() {
		if (BOSSES != null && !BOSSES.hasPassed(86400000L)) // 1 day
			return Collections.unmodifiableList(BOSSES.getValue());
		List<RaidBoss> newBosses = new LinkedList<>();
		try {
			URL url = new URL(BOSSES_URL);
			URLConnection conn = url.openConnection();
			conn.setRequestProperty("User-Agent", Starota.HTTP_USER_AGENT);
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String html = "";
			String line = null;
			while ((line = in.readLine()) != null)
				html += line;
			Matcher generalMatcher = BOSS_GENERAL_PATTERN.matcher(html);
			while (generalMatcher.find()) {
				String match = generalMatcher.group();
				// System.out.println(match);
				// if (match != null)
				// break;
				Matcher tierMatcher = BOSS_TIER_PATTERN.matcher(match);
				Matcher nameMatcher = BOSS_NAME_PATTERN.matcher(match);
				if (tierMatcher.find()) {
					String tierName = tierMatcher.group();
					int tier;
					switch (tierName) {
					case "<h4>EX Raids</h4>":
						tier = 6;
						break;
					case "<h4>Tier 5</h4>":
						tier = 5;
						break;
					case "<h4>Tier 4</h4>":
						tier = 4;
						break;
					case "<h4>Tier 3</h4>":
						tier = 3;
						break;
					case "<h4>Tier 2</h4>":
						tier = 2;
						break;
					case "<h4>Tier 1</h4>":
						tier = 1;
						break;
					default:
						continue;
					}
					while (nameMatcher.find()) {
						String match2 = nameMatcher.group();
						String[] pokemonName = splitName(match2.substring(12, match2.length() - 6));
						EnumPokemon pokemon = EnumPokemon.getPokemon(pokemonName[0]);
						Form form = null;
						if (pokemon.getFormSet() != null)
							form = pokemon.getFormSet().getForm(pokemonName[1]);
						newBosses.add(new RaidBoss(pokemon, form, tier));
					}
				}
			}
			BOSSES = new CachedData<>(newBosses);
			setupTiers();
			return Collections.unmodifiableList(newBosses);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static boolean areBossesLoaded(int tier) {
		if (BOSSES != null && !BOSSES.hasPassed(86400000L)) // 1 day
			return TIERED_BOSSES[tier - 1] != null;
		return false;
	}

	public static List<RaidBoss> getBosses(int tier) {
		getBosses();
		return TIERED_BOSSES[tier - 1];
	}

	public static RaidBoss getBoss(EnumPokemon pokemon, Form form) {
		getBosses();
		for (RaidBoss b : BOSSES.getValue())
			if (b.pokemon.equals(pokemon) && (b.form == null || b.form.equals(form)))
				return b;
		return null;
	}

	@SuppressWarnings("unchecked")
	private static void setupTiers() {
		TIERED_BOSSES = new ArrayList[6];
		for (RaidBoss b : BOSSES.getValue()) {
			List<RaidBoss> tier = TIERED_BOSSES[b.tier - 1];
			if (tier == null)
				tier = new ArrayList<>();
			tier.add(b);
			TIERED_BOSSES[b.tier - 1] = tier;
		}
	}

	private static String[] splitName(String name) {
		String[] parts = new String[2];
		if (name.matches(".*? \\(.*?\\)")) {
			int index = name.indexOf("(");
			parts[0] = name.substring(0, index - 1);
			parts[1] = name.substring(index + 1, name.lastIndexOf(" "));
			return parts;
		} else if (name.matches(".*? .*?")) {
			String[] iParts = name.split(" ");
			parts[0] = iParts[1];
			parts[1] = iParts[0];
			return parts;
		} else
			parts[0] = name;
		return parts;
	}

	public static class RaidBoss {

		private final EnumPokemon pokemon;
		private final Form form;
		private final int tier;

		public RaidBoss(EnumPokemon pokemon, Form form, int tier) {
			this.pokemon = pokemon;
			this.form = form;
			this.tier = tier;
		}

		public EnumPokemon getPokemon() {
			return this.pokemon;
		}

		public Form getForm() {
			return this.form;
		}

		public int getTier() {
			return this.tier;
		}

		public final int getColor() {
			return getColor(tier, pokemon);
		}

		public static int getColor(int tier, EnumPokemon pokemon) {
			switch (tier) {
			case 6:
			case 5:
				return 0xa9a2de;
			case 4:
			case 3:
				return 0xfbee74;
			case 2:
			case 1:
				return 0xf79eee;
			default:
				if (pokemon == null)
					return 0x000000;
				return pokemon.getType1().getColor();
			}
		}

	}

}
