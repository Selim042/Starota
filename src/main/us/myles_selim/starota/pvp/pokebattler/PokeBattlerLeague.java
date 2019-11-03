package us.myles_selim.starota.pvp.pokebattler;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import com.google.gson.Gson;
import com.google.gson.JsonParser;

import us.myles_selim.starota.misc.utils.StarotaConstants;

public class PokeBattlerLeague {

	private static final JsonParser PARSER = new JsonParser();
	private static final Gson GSON = new Gson();

	private static final String WELL_ROUNDED_RANKINGS_ENDPOINT = "https://fight.pokebattler.com/pvp/rankings/"
			+ "defenders/leagues/%s/strategies/"
			+ "PVP/PVP?sort=WIN&filterType=COUNTERS_PVP&filterValue=1000&"
			+ "shieldStrategy=%s&meta=DUAL_MOVE";

	private static final String COUNTERS_RANKINGS_ENDPOINT = "https://fight.pokebattler.com/pvp/rankings/"
			+ "attackers/leagues/%s/strategies/"
			+ "PVP/PVP?sort=WIN&filterType=TOP_DEFENDER_PVP&filterValue=NONE&"
			+ "shieldStrategy=%s&meta=DUAL_MOVE";

	private String title;
	private boolean enabled;
	private PokemonCondition[] pokemonCondition;
	private String iconUrl;
	private int pokemonCount;

	private String badgeType;
	private String combatLeagueType;
	private String baseCombatLeagueType;
	private String silphType;

	private String[] bannedPokemon;
	private String[] currentPokemon;

	private PokeBattlerAttacker[] wellRounded;
	private PokeBattlerAttacker[] counters;

	public PokeBattlerAttacker[] getWellRounded(EnumShieldStrategy shield) {
		if (wellRounded != null)
			return wellRounded;
		try {
			URL url = new URL(String.format(WELL_ROUNDED_RANKINGS_ENDPOINT, getCombatLeagueType(),
					shield.toString()));
			URLConnection conn = url.openConnection();
			conn.setRequestProperty("User-Agent", StarotaConstants.HTTP_USER_AGENT);
			wellRounded = GSON.fromJson(PARSER.parse(new InputStreamReader(conn.getInputStream()))
					.getAsJsonObject().get("attackers"), PokeBattlerAttacker[].class);
			return wellRounded;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public PokeBattlerAttacker[] getCounters(EnumShieldStrategy shield) {
		if (counters != null)
			return counters;
		try {
			URL url = new URL(
					String.format(COUNTERS_RANKINGS_ENDPOINT, getCombatLeagueType(), shield.toString()));
			URLConnection conn = url.openConnection();
			conn.setRequestProperty("User-Agent", StarotaConstants.HTTP_USER_AGENT);
			counters = GSON.fromJson(PARSER.parse(new InputStreamReader(conn.getInputStream()))
					.getAsJsonObject().get("attackers"), PokeBattlerAttacker[].class);
			return counters;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getTitle() {
		switch (title) {
		case "default":
			return "Default";
		case "combat_great_league":
			return "Great League";
		case "combat_ultra_league":
			return "Ultra League";
		case "combat_master_league":
			return "Master League";
		}
		return title;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public int getPokemonCount() {
		return pokemonCount;
	}

	public String getBadgeType() {
		return badgeType;
	}

	public String getCombatLeagueType() {
		return combatLeagueType;
	}

	public String getBaseCombatLeagueType() {
		return baseCombatLeagueType;
	}

	public String getSilphType() {
		return silphType;
	}

	public String[] getBannedPokemon() {
		return bannedPokemon;
	}

	public String[] getCurrentPokemon() {
		return currentPokemon;
	}

	public PokemonCondition[] getPokemonCondition() {
		return pokemonCondition;
	}

}
