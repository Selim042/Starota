package us.myles_selim.starota.misc.utils;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.imageio.ImageIO;

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
		switch (pokemon) {
		case MEWTWO:
			return String.format(
					"https://db.pokemongohub.net/images/official/full/%03d"
							+ (goHubFormId < 0 || goHubFormId > 0 ? "_f" + 4 : "") + ".png",
					pokemon.getId());
		case WORMADAM:
		case ROTOM:
		case ARCEUS:
			return String.format("https://db.pokemongohub.net/images/official/full/%03d" + ".png",
					pokemon.getId());
		default:
			return String.format("https://db.pokemongohub.net/images/official/full/%03d"
					+ (goHubFormId != 0 && goHubFormId != -1 ? "_f" + (goHubFormId + 1) : "") + ".png",
					pokemon.getId());
		}
	}

	public static String getRaidEgg(int level) {
		return String.format("http://assets.myles-selim.us/starota/raids/eggs/%d.png", level);
	}

	public static BufferedImage getImage(String url) {
		try {
			return getImage(new URL(url));
		} catch (MalformedURLException e) {
			return null;
		}
	}

	public static BufferedImage getImage(URL url) {
		try {
			URLConnection urlCon = url.openConnection();
			urlCon.setRequestProperty("User-Agent", StarotaConstants.HTTP_USER_AGENT);
			urlCon.connect();
			return ImageIO.read(urlCon.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static BufferedImage invertImage(BufferedImage img) {
		BufferedImage invImg = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
		for (int x = 0; x < img.getWidth(); x++)
			for (int y = 0; y < img.getHeight(); y++)
				invImg.setRGB(x, y, 0xFFFFFF - img.getRGB(x, y));
		invImg.getAlphaRaster().setRect(img.getAlphaRaster());
		return invImg;
	}

}
