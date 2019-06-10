package us.myles_selim.starota.misc.utils;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.imageio.ImageIO;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import us.myles_selim.starota.enums.EnumPokemon;
import us.myles_selim.starota.enums.EnumTeam;
import us.myles_selim.starota.misc.utils.IJournalEntry.AdventureSyncEntry;
import us.myles_selim.starota.misc.utils.IJournalEntry.BattleLoseEntry;
import us.myles_selim.starota.misc.utils.IJournalEntry.BattleWinEntry;
import us.myles_selim.starota.misc.utils.IJournalEntry.CandyEntry;
import us.myles_selim.starota.misc.utils.IJournalEntry.GiftOpenEntry;
import us.myles_selim.starota.misc.utils.IJournalEntry.GiftSendEntry;
import us.myles_selim.starota.misc.utils.IJournalEntry.PokemonCatchEntry;
import us.myles_selim.starota.misc.utils.IJournalEntry.PokemonHatchEntry;
import us.myles_selim.starota.misc.utils.IJournalEntry.PokemonRunEntry;
import us.myles_selim.starota.misc.utils.IJournalEntry.PokemonTradeEntry;
import us.myles_selim.starota.misc.utils.IJournalEntry.PokestopSpinEntry;
import us.myles_selim.starota.misc.utils.IJournalEntry.RaidWinEntry;
import us.myles_selim.starota.profiles.PlayerProfile;
import us.myles_selim.starota.wrappers.StarotaServer;

public class OcrHelper {

	public static final Tesseract OCR_ENGINE = new Tesseract();

	public static PlayerProfile getProfile(StarotaServer server, BufferedImage image) {
		image = image.getSubimage(0, 0, image.getWidth(), (int) (image.getWidth() * 1.5f));
		float nameStartHeight = 0.20f;
		float nameEndHeight = 0.24f;
		float levelStartHeight = 0.75f;
		float levelEndHeight = 0.81f;
		try {
			PlayerProfile profile = new PlayerProfile();
			String name = OCR_ENGINE
					.doOCR(image,
							new Rectangle((int) (image.getWidth() * 0.025f),
									(int) (image.getHeight() * nameStartHeight),
									(int) (image.getWidth() * 0.45f),
									(int) (image.getHeight() * nameEndHeight)
											- (int) (image.getHeight() * nameStartHeight)))
					.split("\n")[0];
			String levelS = OCR_ENGINE
					.doOCR(image,
							new Rectangle((int) (image.getWidth() * 0.025f),
									(int) (image.getHeight() * levelStartHeight),
									(int) (image.getWidth() * 0.15f),
									(int) (image.getHeight() * levelEndHeight)
											- (int) (image.getHeight() * levelStartHeight)))
					.split("\n")[0];
			int level = Integer.parseInt(levelS);
			profile.setPoGoName(name).setLevel(level);

			int[] pixels = new int[25];
			pixels = image.getRGB(0, (int) (image.getHeight() * 0.5f), 5, 5, pixels, 0, 0);
			Color color = new Color(pixels[0]);
			if (color.getRed() > 190 && color.getGreen() < 30 && color.getBlue() < 70)
				profile.setTeam(EnumTeam.VALOR);
			else if (color.getRed() < 75 && color.getGreen() < 150 && color.getBlue() > 200)
				profile.setTeam(EnumTeam.MYSTIC);
			else if (color.getRed() > 155 && color.getGreen() > 155 && color.getBlue() < 50)
				profile.setTeam(EnumTeam.INSTINCT);
			else
				profile.setTeam(EnumTeam.NO_TEAM);
			return profile;
		} catch (NumberFormatException e) {
			// return null;
			throw e;
		} catch (TesseractException e) {
			throw new RuntimeException(e);
		}
	}

	public static List<IJournalEntry> getJournalEntries(StarotaServer server, BufferedImage image) {
		float widthAdj = 0.75f;
		List<IJournalEntry> events = new ArrayList<>();
		String out;
		try {
			out = OcrHelper.OCR_ENGINE.doOCR(image,
					new Rectangle((int) (image.getWidth() * (1 - widthAdj)), 0,
							(int) (image.getWidth() * widthAdj), image.getHeight()));
		} catch (TesseractException e) {
			throw new RuntimeException(e);
		}
		for (String line : out.split("\n")) {
			if (line.matches(".*? was caught!"))
				events.add(new PokemonCatchEntry(
						EnumPokemon.getPokemon(line.substring(0, line.length() - 12))));
			else if (line.matches(".*? was hatched!"))
				events.add(new PokemonHatchEntry(
						EnumPokemon.getPokemon(line.substring(0, line.length() - 13))));
			else if (line.matches("Received .*? items from raid."))
				events.add(new RaidWinEntry());
			else if (line.matches("Received .*? items from PokéStop."))
				events.add(new PokestopSpinEntry());
			else if (line.matches("Won a battle against .*?."))
				events.add(new BattleWinEntry(
						line.substring(line.lastIndexOf(" ") + 1, line.length() - 1)));
			else if (line.matches("Battled against .*?."))
				events.add(new BattleLoseEntry(
						line.substring(line.lastIndexOf(" ") + 1, line.length() - 1)));
			else if (line.matches("Sent a Gift to .*?."))
				events.add(
						new GiftSendEntry(line.substring(line.lastIndexOf(" ") + 1, line.length() - 1)));
			else if (line.matches("Opened a Gift from .*?."))
				events.add(
						new GiftOpenEntry(line.substring(line.lastIndexOf(" ") + 1, line.length() - 1)));
			else if (line.matches(".*? found a Candy!"))
				events.add(new CandyEntry());
			else if (line.matches(".*?Adventure Sync.*?"))
				events.add(new AdventureSyncEntry());
			else if (line.matches("Traded .*?")) {
				String[] words = line.split(" ");
				EnumPokemon recP = EnumPokemon.getPokemon(words[1]);
				EnumPokemon senP = EnumPokemon.getPokemon(words[3]);
				events.add(new PokemonTradeEntry(recP, senP));
			} else if (line.matches(".*? ran away."))
				events.add(new PokemonRunEntry(
						EnumPokemon.getPokemon(line.substring(0, line.length() - 10))));
		}
		return Collections.unmodifiableList(events);
	}

	public static BadgeData getBadgeValue(StarotaServer server, BufferedImage image) {
		BufferedImage nameImage = image.getSubimage(0, 0, image.getWidth(),
				(int) (image.getWidth() * 1.5f));
		// RescaleOp op = new RescaleOp(1.2f, 15, null);
		// op.filter(image, image);
		float nameEdgeOffset = 0.15f;
		float valueEdgeOffset = 0.20f;
		float nameStartHeight = 0.11f;
		float nameEndHeight = 0.05f;
		float[] valueStartHeight = new float[] { 0.06f, 0.31f, 0.36f };
		float[] valueEndHeight = new float[] { 0.14f, 0.08f, 0.08f };
		int colorIndex = 0;
		Color[] colors = new Color[] { Color.RED, Color.BLUE, Color.GREEN, Color.ORANGE, Color.CYAN };
		// try {
		// Graphics graphics = image.getGraphics();
		// graphics.setColor(colors[colorIndex++]);
		// graphics.drawRect((int) (image.getWidth() * edgeOffset),
		// (int) (image.getHeight() * nameStartHeight),
		// (int) (image.getWidth() * (1.0f - (edgeOffset * 2))),
		// (int) (image.getHeight() * nameEndHeight));
		// for (int i = 0; i < valueStartHeight.length && i <
		// valueEndHeight.length; i++) {
		// graphics.setColor(colors[colorIndex++]);
		// graphics.drawRect((int) (image.getWidth() * edgeOffset),
		// (int) (image.getHeight() * valueStartHeight[i]),
		// (int) (image.getWidth() * (1.0f - (edgeOffset * 2))),
		// (int) (image.getHeight() * valueEndHeight[i]));
		// }
		// ImageIO.write(image, "PNG", new File("test.png"));
		// } catch (IOException e) {}
		try {
			String name = OCR_ENGINE.doOCR(nameImage,
					new Rectangle((int) (nameImage.getWidth() * nameEdgeOffset),
							(int) (nameImage.getHeight() * nameStartHeight),
							(int) (nameImage.getWidth() * (1.0f - (nameEdgeOffset * 2))),
							(int) (nameImage.getHeight() * nameEndHeight)))
					.split("\n")[0];
			long value = -1;
			BufferedImage valImage = image.getSubimage((int) (image.getWidth() * valueEdgeOffset),
					(int) (image.getHeight() * 0.38f),
					(int) (image.getWidth() * (1.0f - (valueEdgeOffset * 2))),
					(int) (image.getHeight() * 0.35f));
			for (int i = 0; i < valueStartHeight.length && i < valueEndHeight.length; i++) {
				Graphics graphic = valImage.getGraphics();
				graphic.setColor(colors[colorIndex++]);
				int start = (int) (valImage.getHeight() * valueStartHeight[i]);
				int end = (int) (valImage.getHeight() * valueEndHeight[i]);

				graphic.drawRect(0, start, valImage.getWidth(), end);
				BufferedImage valImageCrop = valImage.getSubimage(0, start, valImage.getWidth(), end);
				try {
					ImageIO.write(valImageCrop, "PNG", new File("test-crop.png"));
				} catch (IOException e) {}
				if (i >= 1)
					valImageCrop = fixBadgeValue(valImageCrop);
				try {
					ImageIO.write(valImageCrop, "PNG", new File("test-crop-edit.png"));
				} catch (IOException e) {}
				String valueS = OCR_ENGINE.doOCR(valImageCrop).split("\n")[0].replaceAll("[^\\d\\.]",
						"");
				if (valueS.isEmpty())
					continue;
				int first = valueS.indexOf(" ");
				int last = valueS.lastIndexOf(" ");
				if (first != last)
					valueS = valueS.substring(first, last);
				else if (first > valueS.length() / 2)
					valueS = valueS.substring(0, first);
				else if (first != -1)
					valueS = valueS.substring(first);
				try {
					value = (long) Double.parseDouble(valueS);
					break;
				} catch (NumberFormatException e) {}
			}
			try {
				ImageIO.write(valImage, "PNG", new File("test-center-crop.png"));
			} catch (IOException e) {}
			if (value == -1)
				return null;
			return new BadgeData(name, value);
		} catch (NumberFormatException e) {
			// return null;
			throw e;
		} catch (TesseractException e) {
			throw new RuntimeException(e);
		}
	}

	private static BufferedImage fixBadgeValue(BufferedImage img) {
		BufferedImage ret = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
		for (int y = 0; y < img.getHeight(); y++) {
			for (int x = 0; x < img.getWidth(); x++) {
				Color color = new Color(img.getRGB(x, y));
				if ((color.getRed() + color.getGreen() + color.getBlue()) / 3 > 180)
					ret.setRGB(x, y, Color.BLACK.getRGB());
				else
					ret.setRGB(x, y, Color.WHITE.getRGB());
			}
			for (int x = 0; x < img.getWidth() / 2; x++) {
				Color color = new Color(ret.getRGB(x, y));
				if (color.getRGB() > Color.GRAY.getRGB())
					break;
				ret.setRGB(x, y, Color.WHITE.getRGB());
			}
			for (int x = img.getWidth() - 1; x > 0; x--) {
				Color color = new Color(ret.getRGB(x, y));
				if (color.getRGB() > Color.GRAY.getRGB())
					break;
				ret.setRGB(x, y, Color.WHITE.getRGB());
			}
		}
		return ret;
	}

	public static class BadgeData {

		public final String name;
		public final long value;

		private BadgeData(String name, long value) {
			this.name = name;
			this.value = value;
		}
	}

}
