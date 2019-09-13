package us.myles_selim.starota.leek_duck.events;

import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.TimeZone;
import java.util.function.Consumer;

import discord4j.core.spec.EmbedCreateSpec;
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.enums.EnumPokemon;
import us.myles_selim.starota.enums.EnumPokemonType;
import us.myles_selim.starota.misc.utils.EmbedBuilder;
import us.myles_selim.starota.misc.utils.EmojiConstants;
import us.myles_selim.starota.misc.utils.EmojiServerHelper;
import us.myles_selim.starota.misc.utils.ImageHelper;
import us.myles_selim.starota.misc.utils.MiscUtils;
import us.myles_selim.starota.wrappers.StarotaServer;

public class StarotaEvent {

	public String name;
	public String description;
	public String link;
	public String linkTitle;
	public String image;
	public int color;

	public long startTime;
	public long endTime;
	public boolean longTerm;

	public boolean localized;
	public boolean regional;
	public String location;

	public boolean raidsChanged;
	public boolean raidOriented;

	public int[] featuredPokemon;
	public int[] featuredTypes;
	public int[] newShinies;

	public boolean newSpecialResearch;
	public EventFieldResearch[] fieldResearch;
	public String[] bonuses;
	public EventTieredBonus[] tierdBonuses;

	public boolean isOver() {
		return endTime <= System.currentTimeMillis();
	}

	public boolean hasStarted() {
		return startTime <= System.currentTimeMillis();
	}

	public long getAdjustedStart(TimeZone timezone) {
		if (startTime == 0)
			return 0;
		if (localized && timezone != null)
			return startTime + timezone.getOffset(startTime) + timezone.getDSTSavings();
		return startTime;
	}

	public long getAdjustedEnd(TimeZone timezone) {
		if (endTime == 0)
			return 0;
		if (localized)
			return endTime + timezone.getOffset(endTime) + timezone.getDSTSavings();
		return endTime;
	}

	public String getTimeLeft(TimeZone timezone) {
		String output = null;
		long diff;
		if (this.localized)
			diff = getAdjustedStart(timezone) - System.currentTimeMillis();
		else if (this.regional) {
			output += "Regional, countdown not fully implemented...\n";
			diff = this.startTime - System.currentTimeMillis();
		} else
			diff = this.startTime - System.currentTimeMillis();
		if (!this.hasStarted()) {
			if (output == null)
				output = "";
			output += "Starts in ";
		}
		// diff -= 60 * 60 * 1000; // TODO: subtract an hour? (maybe dst)
		if (output == null || output.endsWith("\n")) {
			if (output == null)
				output = "";
			output += "Ends in ";
			diff = getAdjustedEnd(timezone) - System.currentTimeMillis();
		}
		long diffMinutes = diff / (60 * 1000) % 60;
		long diffHours = diff / (60 * 60 * 1000) % 24;
		long diffDays = diff / (24 * 60 * 60 * 1000);
		if (diffDays > 0)
			output += diffDays + (diffDays == 1 ? " day " : " days ");
		if (diffHours > 0)
			output += diffHours + (diffHours == 1 ? " hour " : " hours ");
		if (diffMinutes > 0)
			output += diffMinutes + (diffMinutes == 1 ? " minute " : " minutes ");
		if (output.endsWith("in "))
			return null;
		return output;
	}

	public Consumer<? super EmbedCreateSpec> toEmbed(int index, int max, StarotaServer server) {
		EmbedBuilder builder = new EmbedBuilder();

		builder.withColor(this.color).withImage(this.image);
		builder.withTitle(
				this.location != null && !this.location.isEmpty() ? this.name + ": " + this.location
						: this.name)
				.withDesc(MiscUtils.fixCharacters(this.description));
		String timeLeft = this.getTimeLeft(server.getTimezone());
		if (timeLeft != null)
			builder.appendDesc("\n\n**" + timeLeft + "**");
		if (this.link != null)
			builder.appendDesc(String.format("\n\n[%s](%s)", this.linkTitle, this.link));

		if (this.featuredPokemon.length == 1)
			builder.withThumbnail(
					ImageHelper.getOfficalArtwork(EnumPokemon.getPokemon(this.featuredPokemon[0])));
		StringBuilder featPoke = new StringBuilder();
		for (int id : this.featuredPokemon) {
			EnumPokemon poke = EnumPokemon.getPokemon(id);
			if (MiscUtils.arrContains(this.newShinies, id))
				featPoke.append(poke.getName() + " "
						+ MiscUtils.getEmojiDisplay(EmojiServerHelper.getEmoji("shiny")) + ", ");
			else
				featPoke.append(poke.getName() + ", ");
		}
		if (featPoke.length() != 0)
			builder.appendField("Featured Pokemon:", featPoke.substring(0, featPoke.length() - 2),
					false);

		StringBuilder featType = new StringBuilder();
		for (int id : this.featuredTypes) {
			EnumPokemonType type = EnumPokemonType.fromOrdinal(id);
			featType.append(type.name().substring(0, 1) + type.name().toLowerCase().substring(1) + ", ");
		}
		if (featType.length() != 0)
			builder.appendField("Featured Types:", featType.substring(0, featType.length() - 2), false);

		if (this.raidsChanged || this.raidOriented) {
			builder.appendField("Raids:", "Raids Changed: "
					+ MiscUtils.getEmojiDisplay(EmojiConstants.getBooleanEmoji(this.raidsChanged))
					+ "\nRaid Oriented: "
					+ MiscUtils.getEmojiDisplay(EmojiConstants.getBooleanEmoji(this.raidOriented)),
					false);
		}

		StringBuilder bonuses = new StringBuilder();
		for (String b : this.bonuses)
			bonuses.append(String.format(" - %s\n", b));
		if (bonuses.length() != 0)
			builder.appendField("Bonuses:",
					MiscUtils.fixCharacters(bonuses.toString().substring(0, bonuses.length() - 1)),
					false);

		StringBuilder tieredBonuses = new StringBuilder();
		for (EventTieredBonus tb : this.tierdBonuses) {
			tieredBonuses.append("**" + tb.name + "**:\n");
			for (String b : tb.bonuses)
				tieredBonuses.append(" - " + b + "\n");
		}
		if (tieredBonuses.length() != 0)
			builder.appendField("Tiered Bonuses:", MiscUtils.fixCharacters(tieredBonuses.toString()),
					false);

		StringBuilder research = new StringBuilder();
		for (EventFieldResearch fr : this.fieldResearch) {
			research.append("**" + fr.name + "**:\n");
			for (ResearchReward b : fr.rewards) {
				research.append(" - " + b + "\n");
			}
		}
		if (research.length() != 0)
			builder.appendField("Field Research:", MiscUtils.fixCharacters(research.toString()), false);

		if (index != -1)
			builder.withFooterText("Event " + index + "/" + max + " | Last updated")
					.withTimestamp(EventData.getEventsCacheTime());
		return builder.build();
	}

	public static class EventFieldResearch {

		public final String name;
		public final List<ResearchReward> rewards;

		public EventFieldResearch(String name, Enumeration<ResearchReward> rewards) {
			this.name = name;
			this.rewards = Collections.unmodifiableList(Collections.list(rewards));
		}

		@Override
		public String toString() {
			String ret = name + ": ";
			for (ResearchReward r : rewards)
				ret += r + ", ";
			return ret.substring(0, ret.length() - 2);
		}
	}

	public abstract static class ResearchReward {

		public static ResearchReward getReward(String input) {
			if (input.endsWith("Encounter") || input.endsWith("Encounter, Shinyable")) {
				EnumPokemon pokemon = EnumPokemon
						.getPokemon(input.substring(0, input.indexOf("Encounter") - 1));
				return new PokemonEntry(pokemon, input.endsWith(", Shinyable"));
			} else if (input.matches(".*?x[0-9]{1,6}"))
				return new ItemReward(input.substring(0, input.indexOf("x") - 1),
						Integer.parseInt(input.substring(input.indexOf("x") + 1)));
			else
				return null;
		}

	}

	public static class PokemonEntry extends ResearchReward {

		public final EnumPokemon pokemon;
		public boolean shiny;

		public PokemonEntry(EnumPokemon pokemon) {
			this(pokemon, false);
		}

		public PokemonEntry(EnumPokemon pokemon, boolean newShiny) {
			this.pokemon = pokemon;
			this.shiny = newShiny;
		}

		@Override
		public String toString() {
			if (pokemon == null)
				return "null";
			if (Starota.FULLY_STARTED) {
				if (!shiny)
					return pokemon.toString();
				return pokemon.toString() + " " + EmojiServerHelper.getEmoji("shiny");
			}
			if (!shiny)
				return pokemon.toString();
			return pokemon.toString() + ", shiny";
		}
	}

	public static class ItemReward extends ResearchReward {

		public final String itemName;
		public final int num;

		public ItemReward(String itemName, int num) {
			this.itemName = itemName;
			this.num = num;
		}

		@Override
		public String toString() {
			return itemName + " x" + num;
		}
	}

	public static class EventTieredBonus {

		public final String name;
		public final List<String> bonuses;

		public EventTieredBonus(String name, Enumeration<String> bonuses) {
			this.name = name;
			this.bonuses = Collections.unmodifiableList(Collections.list(bonuses));
		}

		@Override
		public String toString() {
			String ret = name + ": ";
			for (String b : bonuses)
				ret += b + ",";
			ret = ret.substring(0, ret.length() - 1);
			return ret;
		}
	}

}
