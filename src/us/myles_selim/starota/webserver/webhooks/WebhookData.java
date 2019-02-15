package us.myles_selim.starota.webserver.webhooks;

import sx.blah.discord.api.internal.json.objects.EmbedObject;
import sx.blah.discord.util.EmbedBuilder;
import us.myles_selim.starota.ImageHelper;
import us.myles_selim.starota.enums.EnumPokemon;
import us.myles_selim.starota.webserver.webhooks.types.ILocationWebhook;
import us.myles_selim.starota.webserver.webhooks.types.IPokemonWebhook;
import us.myles_selim.starota.webserver.webhooks.types.ISpecificPokemonWebhook;

public class WebhookData {

	public EmbedObject toEmbed() {
		EmbedBuilder builder = new EmbedBuilder();
		int color = 0x000000;

		if (this instanceof IPokemonWebhook) {
			IPokemonWebhook pokeHook = (IPokemonWebhook) this;
			EnumPokemon pokemon = pokeHook.getPokemon();
			builder.withTitle(String.format("A wild %s has spawned!", pokemon.getName()));
			builder.withThumbnail(ImageHelper.getOfficalArtwork(pokemon, pokeHook.getForm()));
			builder.withColor(pokemon.getType1().getColor());
			color = pokemon.getType1().getColor();
			builder.appendField("Type:", pokemon.getType1().getEmoji().toString()
					+ (pokemon.getType2() != null ? pokemon.getType2().getEmoji() : ""), true);
		}

		if (this instanceof ISpecificPokemonWebhook) {
			ISpecificPokemonWebhook sPokeHook = (ISpecificPokemonWebhook) this;
			builder.appendDesc("CP: " + sPokeHook.getCP());
			if (sPokeHook.hasIVs())
				builder.appendDesc(String.format("IV: %.1f, Atk: %n, Def: %n, Sta: %n",
						sPokeHook.getIVPercent(), sPokeHook.getAttackIV(), sPokeHook.getDefenseIV(),
						sPokeHook.getStaminaIV()));
		}

		if (this instanceof ILocationWebhook) {
			ILocationWebhook locHook = (ILocationWebhook) this;
			builder.appendField("Directions:",
					String.format(
							"[Google Maps](https://www.google.com/maps/search/?api=1&query=%1$f,%2$f) | "
									+ "[Apple Maps](http://maps.apple.com/?daddr=%1$f,%2$f)",
							locHook.getLatitude(), locHook.getLongitude()),
					false);
			builder.withImage(String.format(
					"https://api.mapbox.com/styles/v1/mapbox/satellite-streets-v11/static/pin-s+%06X(%2$f,%3$f)/%2$f,%3$f,16.5,0,0/600x300@2x?logo=false&access_token=pk.eyJ1Ijoic2VsaW0wNDIiLCJhIjoiY2pyOXpmM2g1MG16cTQzbndqZXk5dHNndCJ9.vsh20BzsPBgTcBBcKWBqQw",
					color, locHook.getLongitude(), locHook.getLatitude()));
		}
		return builder.build();
	}

}
