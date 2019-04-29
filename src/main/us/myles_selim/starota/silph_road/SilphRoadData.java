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
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.enums.EnumPokemon;
import us.myles_selim.starota.misc.data_types.EggEntry;
import us.myles_selim.starota.misc.data_types.RaidBoss;
import us.myles_selim.starota.misc.data_types.ResearchTask;
import us.myles_selim.starota.misc.data_types.cache.CachedData;
import us.myles_selim.starota.misc.data_types.cache.ClearCache;
import us.myles_selim.starota.misc.utils.EmojiServerHelper;
import us.myles_selim.starota.misc.utils.StarotaConstants;
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
	private static final Pattern BOSS_NAME_PATTERN = Pattern
			.compile("<div class=\"pokemonOption.*?\"boss-name\">.*?<\\/div>");

	private static CachedData<List<RaidBoss>> BOSSES;
	private static List<RaidBoss>[] TIERED_BOSSES;

	public static List<RaidBoss> getBosses() {
		if (BOSSES != null && !BOSSES.hasPassed(86400000L)) // 1 day
			return Collections.unmodifiableList(BOSSES.getValue());
		List<RaidBoss> newBosses = new LinkedList<>();
		try {
			URL url = new URL(BOSSES_URL);
			URLConnection conn = url.openConnection();
			conn.setRequestProperty("User-Agent", StarotaConstants.HTTP_USER_AGENT);
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
						String[] pokemonName = splitName(match2
								.substring(match2.indexOf("boss-name\">") + 11, match2.length() - 6));
						EnumPokemon pokemon = EnumPokemon.getPokemon(pokemonName[0]);
						if (pokemon == null) {
							Starota.submitError("Cannot find raid boss named " + pokemonName[0],
									new IllegalArgumentException(
											"Cannot find raid boss named " + pokemonName[0]));
							continue;
						}
						boolean shinyable = match2.contains("shiny");
						Form form = null;
						if (pokemon.getFormSet() != null)
							form = pokemon.getFormSet().getForm(pokemonName[1]);
						newBosses.add(new RaidBoss(pokemon, form, tier, shinyable));
					}
				}
			}
			BOSSES = new CachedData<>(newBosses);
			setupBossTiers();
			return Collections.unmodifiableList(newBosses);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static boolean areBossesLoaded(int tier) {
		if (BOSSES != null && !BOSSES.hasPassed(86400000L)) // 1 day
			return TIERED_EGGS[tier - 1] != null;
		return false;
	}

	public static List<RaidBoss> getBosses(int tier) {
		getBosses();
		return TIERED_BOSSES[tier - 1];
	}

	public static RaidBoss getBoss(EnumPokemon pokemon, Form form) {
		getBosses();
		for (RaidBoss b : BOSSES.getValue())
			if (b.getPokemon().equals(pokemon)
					&& ((form == null && b.getForm() == null) || b.getForm().equals(form)))
				return b;
		return null;
	}

	public static long getBossCacheTime() {
		if (BOSSES == null)
			return -1;
		return BOSSES.getCreationTime();
	}

	@SuppressWarnings("unchecked")
	private static void setupBossTiers() {
		TIERED_BOSSES = new ArrayList[6];
		for (RaidBoss b : BOSSES.getValue()) {
			List<RaidBoss> tier = TIERED_BOSSES[b.getTier() - 1];
			if (tier == null)
				tier = new ArrayList<>();
			tier.add(b);
			TIERED_BOSSES[b.getTier() - 1] = tier;
		}
	}

	private static final String EGGS_URL = "https://thesilphroad.com/egg-distances";

	private static final Pattern EGG_GENERAL_PATTERN = Pattern.compile(
			"gColumn\" style=\"background:#[0-9a-f]{8};\">.*?(<div class=\"eg|<div id=\"footer)");
	private static final Pattern EGG_TIER_PATTERN = Pattern.compile("\">[0-9]{1,2}KM Eggs");
	private static final Pattern EGG_WRAPPER_PATTERN = Pattern
			.compile("data-species-num.*?(\"speciesWrap|\"foote)");
	private static final Pattern EGG_NAME_PATTERN = Pattern
			.compile("<p class=\"speciesName\">.*?<\\/p>");

	private static CachedData<List<EggEntry>> EGGS;
	private static List<EggEntry>[] TIERED_EGGS;

	public static List<EggEntry> getEggs() {
		if (EGGS != null && !EGGS.hasPassed(86400000L)) // 1 day
			return Collections.unmodifiableList(EGGS.getValue());
		List<EggEntry> newEggs = new LinkedList<>();
		try {
			URL url = new URL(EGGS_URL);
			URLConnection conn = url.openConnection();
			conn.setRequestProperty("User-Agent", StarotaConstants.HTTP_USER_AGENT);
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String html = "";
			String line = null;
			while ((line = in.readLine()) != null)
				html += line;
			Matcher generalMatcher = EGG_GENERAL_PATTERN.matcher(html);
			// System.out.println(generalMatcher.groupCount());
			while (generalMatcher.find()) {
				String match = generalMatcher.group();
				// if (match != null)
				// break;
				Matcher tierMatcher = EGG_TIER_PATTERN.matcher(match);
				Matcher wrapperMatcher = EGG_WRAPPER_PATTERN.matcher(match);
				if (tierMatcher.find()) {
					String tierName = tierMatcher.group();
					int tier;
					switch (tierName) {
					case "\">2KM Eggs":
						tier = 2;
						break;
					case "\">5KM Eggs":
						tier = 5;
						break;
					case "\">7KM Eggs":
						tier = 7;
						break;
					case "\">10KM Eggs":
						tier = 10;
						break;
					default:
						continue;
					}
					// System.out.println(tier);
					while (wrapperMatcher.find()) {
						String wrapperMatch = wrapperMatcher.group();
						Matcher nameMatcher = EGG_NAME_PATTERN.matcher(wrapperMatch);
						if (!nameMatcher.find())
							continue;
						String nameMatch = nameMatcher.group();
						String[] pokemonName = splitName(
								nameMatch.substring(47, nameMatch.length() - 26));
						// System.out.println(pokemonName[0] + " " +
						// pokemonName[1]);
						EnumPokemon pokemon = EnumPokemon.getPokemon(pokemonName[0]);
						if (pokemon == null) {
							Starota.submitError("Cannot find egg hatch named " + pokemonName[0],
									new IllegalArgumentException(
											"Cannot find egg hatch named " + pokemonName[0]));
							continue;
						}
						Form form = null;
						if (pokemon.getFormSet() != null)
							form = pokemon.getFormSet().getForm(pokemonName[1]);
						newEggs.add(
								new EggEntry(pokemon, form, tier, wrapperMatch.contains("shinyIcon")));
					}
				}
			}
			EGGS = new CachedData<>(newEggs);
			setupEggTiers();
			return Collections.unmodifiableList(newEggs);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static boolean areEggsLoaded(int dist) {
		if (EGGS != null && !EGGS.hasPassed(86400000L)) // 1 day
			return TIERED_EGGS[getDistanceIndex(dist)] != null;
		return false;
	}

	public static List<EggEntry> getEggs(int dist) {
		getEggs();
		return TIERED_EGGS[getDistanceIndex(dist)];
	}

	public static EggEntry getEgg(EnumPokemon pokemon, Form form) {
		getEggs();
		for (EggEntry b : EGGS.getValue())
			if (b.getPokemon().equals(pokemon)
					&& ((form == null && b.getForm() == null) || b.getForm().equals(form)))
				return b;
		return null;
	}

	public static long getEggCacheTime() {
		if (EGGS == null)
			return -1;
		return EGGS.getCreationTime();
	}

	@SuppressWarnings("unchecked")
	private static void setupEggTiers() {
		TIERED_EGGS = new ArrayList[4];
		for (EggEntry e : EGGS.getValue()) {
			List<EggEntry> tier = TIERED_EGGS[getDistanceIndex(e.getDistance())];
			if (tier == null)
				tier = new ArrayList<>();
			tier.add(e);
			TIERED_EGGS[getDistanceIndex(e.getDistance())] = tier;
		}
	}

	private static int getDistanceIndex(int dist) {
		switch (dist) {
		case 2:
			return 0;
		case 5:
			return 1;
		case 7:
			return 2;
		case 10:
			return 3;
		default:
			return -1;
		}
	}

	private static final String TASKS_URL = "https://thesilphroad.com/raid-bosses";

	private static final Pattern TASK_GROUP_PATTERN = Pattern
			.compile("up group[1-9]{1,2}(\"|\')>.*?(<div class=(\"|\')task-gro|<p class=\"text)");
	private static final Pattern TASK_TIER_PATTERN = Pattern.compile("<h4>.*?<\\/h4>");
	private static final Pattern TASK_NAME_PATTERN = Pattern
			.compile("<div class=\"pokemonOption.*?\"boss-name\">.*?<\\/div>");

	private static CachedData<List<ResearchTask>> TASKS;
//	private static List<ResearchTask>[] TIERED_TASKS;

	public static List<ResearchTask> getTasks() {
		if (TASKS != null && !TASKS.hasPassed(86400000L)) // 1 day
			return Collections.unmodifiableList(TASKS.getValue());
		List<ResearchTask> newTasks = new LinkedList<>();
		try {
			URL url = new URL(TASKS_URL);
			URLConnection conn = url.openConnection();
			conn.setRequestProperty("User-Agent", StarotaConstants.HTTP_USER_AGENT);
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String html = "";
			String line = null;
			while ((line = in.readLine()) != null)
				html += line;
			Matcher generalMatcher = TASK_GROUP_PATTERN.matcher(html);
			while (generalMatcher.find()) {
				String match = generalMatcher.group();
				 System.out.println(match);
				 if (match != null)
				 break;
//				Matcher tierMatcher = TASK_TIER_PATTERN.matcher(match);
//				Matcher nameMatcher = TASK_NAME_PATTERN.matcher(match);
//				if (tierMatcher.find()) {
//					String tierName = tierMatcher.group();
//					int tier;
//					switch (tierName) {
//					case "<h4>EX Raids</h4>":
//						tier = 6;
//						break;
//					case "<h4>Tier 5</h4>":
//						tier = 5;
//						break;
//					case "<h4>Tier 4</h4>":
//						tier = 4;
//						break;
//					case "<h4>Tier 3</h4>":
//						tier = 3;
//						break;
//					case "<h4>Tier 2</h4>":
//						tier = 2;
//						break;
//					case "<h4>Tier 1</h4>":
//						tier = 1;
//						break;
//					default:
//						continue;
//					}
//					while (nameMatcher.find()) {
//						String match2 = nameMatcher.group();
//						String[] pokemonName = splitName(match2
//								.substring(match2.indexOf("boss-name\">") + 11, match2.length() - 6));
//						EnumPokemon pokemon = EnumPokemon.getPokemon(pokemonName[0]);
//						if (pokemon == null) {
//							Starota.submitError("Cannot find raid boss named " + pokemonName[0],
//									new IllegalArgumentException(
//											"Cannot find raid boss named " + pokemonName[0]));
//							continue;
//						}
//						boolean shinyable = match2.contains("shiny");
//						Form form = null;
//						if (pokemon.getFormSet() != null)
//							form = pokemon.getFormSet().getForm(pokemonName[1]);
//						newTasks.add(new ResearchTask(pokemon, form, tier, shinyable));
//					}
//				}
			}
			TASKS = new CachedData<>(newTasks);
//			setupTaskTiers();
			return Collections.unmodifiableList(newTasks);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static boolean areTasksLoaded() {
		if (TASKS != null && !TASKS.hasPassed(86400000L)) // 1 day
			return true;
		return false;
	}

//	public static List<ResearchTask> getTasks(int tier) {
//		getTasks();
//		return TIERED_TASKS[tier - 1];
//	}

//	public static ResearchTask getTask(EnumPokemon pokemon, Form form) {
//		getTasks();
//		for (ResearchTask b : TASKS.getValue())
//			if (b.getPokemon().equals(pokemon)
//					&& ((form == null && b.getForm() == null) || b.getForm().equals(form)))
//				return b;
//		return null;
//	}

	public static long getTaskCacheTime() {
		if (TASKS == null)
			return -1;
		return TASKS.getCreationTime();
	}

//	@SuppressWarnings("unchecked")
//	private static void setupTaskTiers() {
//		TIERED_TASKS = new ArrayList[6];
//		for (ResearchTask b : TASKS.getValue()) {
//			List<ResearchTask> tier = TIERED_TASKS[b.getTier() - 1];
//			if (tier == null)
//				tier = new ArrayList<>();
//			tier.add(b);
//			TIERED_TASKS[b.getTier() - 1] = tier;
//		}
//	}

	@ClearCache("silphroaddata")
	public static void dumpCaches() {
		BOSSES = null;
		EGGS = null;
		TASKS = null;
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

}
