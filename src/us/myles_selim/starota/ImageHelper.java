package us.myles_selim.starota;

import us.myles_selim.starota.enums.EnumPokemon;
import us.myles_selim.starota.trading.forms.FormSet.Form;

public class ImageHelper {

	public static String getOfficalArtwork(EnumPokemon pokemon) {
		return getOfficalArtwork(pokemon, null);
	}

	public static String getOfficalArtwork(EnumPokemon pokemon, Form form) {
		return getOfficalArtwork(pokemon, form == null ? -1 : form.getGoHubId(pokemon));
	}

	public static String getOfficalArtwork(EnumPokemon pokemon, int goHubFormId) {
		return String.format("https://db.pokemongohub.net/images/official/full/%03d"
				+ (goHubFormId != 0 && goHubFormId != -1 ? "_f" + (goHubFormId + 1) : "") + ".png",
				pokemon.getId());
	}

}
