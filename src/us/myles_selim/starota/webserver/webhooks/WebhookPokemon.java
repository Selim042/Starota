package us.myles_selim.starota.webserver.webhooks;

import us.myles_selim.starota.enums.EnumPokemon;
import us.myles_selim.starota.pokedex.GoHubDatabase;
import us.myles_selim.starota.pokedex.PokedexEntry.DexMove;
import us.myles_selim.starota.webserver.webhooks.types.ILocationWebhook;
import us.myles_selim.starota.webserver.webhooks.types.ISpecificPokemonWebhook;

public class WebhookPokemon extends WebhookData implements ILocationWebhook, ISpecificPokemonWebhook {

	public static final String TYPE = "pokemon";
	public static final EnumWebhookType TYPE_ENUM = EnumWebhookType.POKEMON;

	public String spawnpoint_id;
	public String pokestop_id;
	public String encounter_id;
	public int pokemon_id;

	public double latitude;
	public double longitude;

	public long disappear_time;
	public long first_seen;

	public boolean verified;
	public long last_modified_time;

	public int gender;
	public int cp;
	public int form;
	public int costume;

	public int individual_attack;
	public int individual_defense;
	public int individual_stamina;

	public int move_1;
	public int move_2;

	public double weight;
	public double height;

	public int weather;

	@Override
	public EnumPokemon getPokemon() {
		return EnumPokemon.getPokemon(pokemon_id);
	}

	@Override
	public int getForm() {
		return this.form;
	}

	@Override
	public DexMove getFastMove() {
		return GoHubDatabase.getMove(move_1);
	}

	@Override
	public DexMove getChargedMove() {
		return GoHubDatabase.getMove(move_2);
	}

	// TODO: finish nagging Shawn for this too
	@Override
	public boolean isWeatherBoosted() {
		return false;
	}

	@Override
	public int getCP() {
		return this.cp;
	}

	@Override
	public int getAttackIV() {
		return this.individual_attack;
	}

	@Override
	public int getDefenseIV() {
		return this.individual_defense;
	}

	@Override
	public int getStaminaIV() {
		return this.individual_stamina;
	}

	@Override
	public double getWeight() {
		return this.weight;
	}

	@Override
	public double getHeight() {
		return this.height;
	}

	@Override
	public double getLatitude() {
		return this.latitude;
	}

	@Override
	public double getLongitude() {
		return this.longitude;
	}

}
