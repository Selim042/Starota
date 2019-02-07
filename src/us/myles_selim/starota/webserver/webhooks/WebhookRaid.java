package us.myles_selim.starota.webserver.webhooks;

import us.myles_selim.starota.enums.EnumPokemon;
import us.myles_selim.starota.enums.EnumTeam;
import us.myles_selim.starota.pokedex.GoHubDatabase;
import us.myles_selim.starota.pokedex.PokedexEntry.DexMove;
import us.myles_selim.starota.webserver.webhooks.types.IGymWebhook;
import us.myles_selim.starota.webserver.webhooks.types.IPokemonWebhook;

public class WebhookRaid extends WebhookData implements IGymWebhook, IPokemonWebhook {

	public static final String TYPE = "raid";
	public static final EnumWebhookType TYPE_ENUM = EnumWebhookType.RAID;

	public String gym_id;
	public String gym_name;
	public double latitude;
	public double longitude;

	public int team_id;
	public long spawn;
	public long start;
	public long end;

	public int level;
	public int pokemon_id;
	public int cp;
	public int form;

	public int move_1;
	public int move_2;
	public boolean sponsor_id;

	public boolean is_exclusive;

	@Override
	public EnumPokemon getPokemon() {
		if (!hasHatched())
			return null;
		return EnumPokemon.getPokemon(pokemon_id);
	}

	@Override
	public int getForm() {
		return this.form;
	}

	public boolean hasHatched() {
		return this.pokemon_id != 1;
	}

	@Override
	public EnumTeam getTeam() {
		switch (team_id) {
		case 1:
			return EnumTeam.INSTINCT;
		case 2:
			return EnumTeam.MYSTIC;
		case 3:
			return EnumTeam.VALOR;
		default:
			return EnumTeam.NO_TEAM;
		}
	}

	@Override
	public DexMove getFastMove() {
		return GoHubDatabase.getMove(move_1);
	}

	@Override
	public DexMove getChargedMove() {
		return GoHubDatabase.getMove(move_2);
	}

	public long getTimeRemainingDespawn() {
		return this.end - System.currentTimeMillis();
	}

	public long getTimeRemainingHatch() {
		return this.start - System.currentTimeMillis();
	}

	@Override
	public String getGymId() {
		return this.gym_id;
	}

	@Override
	public String getGymName() {
		return this.gym_name;
	}

	@Override
	public int getTeamId() {
		return this.team_id;
	}

	@Override
	public boolean isSponsor() {
		return this.sponsor_id;
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
