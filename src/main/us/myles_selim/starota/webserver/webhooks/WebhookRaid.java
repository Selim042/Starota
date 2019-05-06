package us.myles_selim.starota.webserver.webhooks;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.function.Consumer;

import discord4j.core.spec.EmbedCreateSpec;
import us.myles_selim.starota.enums.EnumPokemon;
import us.myles_selim.starota.misc.data_types.EmbedBuilder;
import us.myles_selim.starota.misc.data_types.RaidBoss;
import us.myles_selim.starota.misc.utils.ImageHelper;
import us.myles_selim.starota.pokedex.GoHubDatabase;
import us.myles_selim.starota.pokedex.PokedexEntry.DexMove;
import us.myles_selim.starota.silph_road.SilphRoadData;
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
	public int getFormId() {
		return this.form;
	}

	public boolean hasHatched() {
		return this.pokemon_id != 0;
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

	private static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("hh:mm");

	@Override
	public Consumer<EmbedCreateSpec> toEmbed() {
		if (hasHatched())
			throw new IllegalArgumentException("egg has already hatched");
		EmbedBuilder builder = new EmbedBuilder();
		builder.setTitle("Tier " + this.level + " raid at " + this.gym_name);
		builder.withThumbnail(ImageHelper.getRaidEgg(this.level));
		builder.withColor(RaidBoss.getColor(this.level, null));
		builder.appendDesc("\n**Time Left Until Hatch**: " + getTimeRemainingHatch(this));
		TIME_FORMAT.setTimeZone(TimeZone.getTimeZone("CST"));
		builder.appendDesc("\n**Hatch Time**: " + TIME_FORMAT.format(new Date(this.start)));

		List<RaidBoss> bosses = SilphRoadData.getBosses(this.level);
		String bossesString = "";
		for (RaidBoss b : bosses)
			bossesString += (b.getForm() == null ? "" : b.getForm() + " ") + b.getPokemon() + "\n";
		builder.addField("Possible Bosses:", bossesString, false);

		builder.addField("Directions:",
				String.format(
						"[Google Maps](https://www.google.com/maps/search/?api=1&query=%1$f,%2$f) | "
								+ "[Apple Maps](http://maps.apple.com/?daddr=%1$f,%2$f)",
						this.latitude, this.longitude),
				false);
		builder.withImage(String.format(
				"https://api.mapbox.com/styles/v1/mapbox/satellite-streets-v11/static/pin-s+%06X(%2$f,%3$f)/%2$f,%3$f,16.5,0,0/600x300@2x?logo=false&access_token=pk.eyJ1Ijoic2VsaW0wNDIiLCJhIjoiY2pyOXpmM2g1MG16cTQzbndqZXk5dHNndCJ9.vsh20BzsPBgTcBBcKWBqQw",
				this.getTeam().getColor(), this.longitude, this.latitude));
		return builder.build();
	}

	private static String getTimeRemainingHatch(WebhookRaid raidData) {
		long rem = raidData.getTimeRemainingHatch();
		int hours = (int) rem / 360000;
		rem = rem % 3600;
		int mins = (int) rem / 36000;
		rem = rem % 360;
		int seconds = (int) rem / 6000;
		String ret = "";
		boolean cont = false;
		if (hours > 0) {
			ret += hours + " hours, ";
			cont = true;
		}
		if (mins > 0 || cont) {
			ret += mins + " minutes, ";
			cont = true;
		}
		if (seconds > 0 || cont)
			ret += seconds + " seconds, ";
		if (ret.isEmpty())
			return ret;
		return ret.substring(0, ret.length() - 2);
	}

}
