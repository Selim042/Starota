package us.myles_selim.starota.webserver.webhooks.other;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import us.myles_selim.starota.RaidBoss;
import us.myles_selim.starota.enums.EnumPokemon;

public class SubscriptionOptions {

	private final Map<String, List<EnumPokemon>> pokemonSubs = new ConcurrentHashMap<>();
	private final Map<String, List<RaidBoss>> raidSubs = new ConcurrentHashMap<>();

	public boolean isSubscribed(String region, EnumPokemon pokemon) {
		return pokemonSubs.containsKey(region) && pokemonSubs.get(region).contains(pokemon);
	}

	public boolean isSubscribed(String region, RaidBoss boss) {
		return raidSubs.containsKey(region) && raidSubs.get(region).contains(boss);
	}

}
