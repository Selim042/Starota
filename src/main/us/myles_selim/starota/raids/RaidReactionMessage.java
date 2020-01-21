package us.myles_selim.starota.raids;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Consumer;

import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.MessageChannel;
import discord4j.core.object.entity.User;
import discord4j.core.object.reaction.ReactionEmoji;
import discord4j.core.spec.EmbedCreateSpec;
import us.myles_selim.starota.enums.EnumPokemon;
import us.myles_selim.starota.enums.EnumWeather;
import us.myles_selim.starota.forms.Form;
import us.myles_selim.starota.misc.data_types.RaidBoss;
import us.myles_selim.starota.misc.utils.EmbedBuilder;
import us.myles_selim.starota.misc.utils.EmojiServerHelper;
import us.myles_selim.starota.misc.utils.ImageHelper;
import us.myles_selim.starota.misc.utils.MiscUtils;
import us.myles_selim.starota.modules.BaseModules;
import us.myles_selim.starota.modules.StarotaModule;
import us.myles_selim.starota.pokedex.GoHubDatabase;
import us.myles_selim.starota.pokedex.PokedexEntry;
import us.myles_selim.starota.pokedex.PokedexEntry.DexCounter;
import us.myles_selim.starota.raids.raid_party.RaidParty;
import us.myles_selim.starota.raids.raid_party.RaidPartyAPI;
import us.myles_selim.starota.reaction_messages.IHelpReactionMessage;
import us.myles_selim.starota.reaction_messages.ReactionMessage;
import us.myles_selim.starota.silph_road.SilphRoadData;
import us.myles_selim.starota.wrappers.BotServer;
import us.myles_selim.starota.wrappers.StarotaServer;

public class RaidReactionMessage extends ReactionMessage implements IHelpReactionMessage {

	private static final String EX_RAID_EMOJI = "ex_raid";
	private static final String RAID_EMOJI = "raid";
	private static final String[] EMOJI_NAMES = new String[] { "1⃣", "2⃣", "3⃣", "4⃣", "5⃣", "here",
			"🚫" };

	private int tier;
	private String time;
	private String location;
	private RaidBoss boss;
	private EnumPokemon pokemon;
	private Form form;
	private final Map<Member, ReactionEmoji> attending = new HashMap<>();
	private final Map<Member, ReactionEmoji> here = new HashMap<>();
	private boolean sentHereMessage = false;

	public RaidReactionMessage(EnumPokemon pokemon, Form form, int tier, String time, String location) {
		this.boss = new RaidBoss(pokemon, form, tier);
		this.pokemon = pokemon;
		this.form = form;
		this.tier = tier;
		this.time = time;
		this.location = location;
	}

	public RaidReactionMessage(int tier, String time, String location) {
		List<RaidBoss> bosses = SilphRoadData.getBosses(tier);
		if (bosses.size() == 1) {
			this.boss = bosses.get(0);
			this.pokemon = boss.getPokemon();
			this.form = boss.getForm();
		}
		this.tier = tier;
		this.time = time;
		this.location = location;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onReactionAdded(StarotaServer server, MessageChannel channel, Message msg, User user,
			ReactionEmoji react) {
		msg.removeReaction(react, user.getId()).block();
		if (user.isBot())
			return;
		String emojiName = MiscUtils.getEmojiName(react);
		if (pokemon != null && !MiscUtils.arrContains(EMOJI_NAMES, emojiName))
			return;
		if (pokemon == null) {
			String[] parts = react.asCustomEmoji().get().getName().split("_");
			pokemon = EnumPokemon.getPokemon(parts[0]);
			form = getForm(parts);
			boss = SilphRoadData.getBoss(pokemon, form);
			if (boss == null) {
				pokemon = null;
				form = null;
				return;
			}
			msg.removeAllReactions().block();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) { /* */ }
			this.addHelpButton(this, server);
			for (int i = 0; i < EMOJI_NAMES.length - 2; i++) {
				int iF = i;
				msg.addReaction(ReactionEmoji.unicode(EMOJI_NAMES[iF])).block();
			}
			msg.addReaction(EmojiServerHelper.getEmoji(EMOJI_NAMES[5])).block();
			msg.addReaction(ReactionEmoji.unicode(EMOJI_NAMES[6])).block();
			if (!GoHubDatabase.isEntryLoaded(pokemon)) {
				msg.edit((m) -> m.setEmbed(GoHubDatabase.LOADING_EMBED)).block();
				GoHubDatabase.getEntry(pokemon, form == null ? null : (form.getGoHubFormName()));
			}

			// TODO: create dummy raid party
			EnumWeather[] forecasts = server.getCurrentPossibleBoosts();
			RaidParty party = RaidPartyAPI.createRaidParty(boss,
					forecasts.length == 0 ? EnumWeather.NO_WEATHER : forecasts[0]);
			System.out.println("created dummy raid party #" + (party == null ? "null" : party.getId()));
		} else {
			// here emoji
			if (emojiName.equals(EMOJI_NAMES[5])) {
				ReactionEmoji emoji;
				if (attending.containsKey(user))
					emoji = attending.get(user);
				else
					emoji = ReactionEmoji.unicode(EMOJI_NAMES[0]);
				here.put(user.asMember(server.getDiscordGuild().getId()).block(), emoji);
				if (attending.containsKey(user))
					attending.remove(user);

				// if everyone is here, send message
				if (!sentHereMessage && attending.isEmpty()) {
					StringBuilder mentionsMessage = new StringBuilder();
					for (Entry<Member, ReactionEmoji> entry : here.entrySet())
						mentionsMessage.append(entry.getKey().getMention() + ", ");
					if (mentionsMessage.length() > 2) {
						mentionsMessage.append("everyone appears to be at " + location);
						sentHereMessage = true;
						channel.createMessage(mentionsMessage.toString()).block();
					}
				}
				// not attending emoji
			} else if (emojiName.equals(EMOJI_NAMES[6])) {
				if (here.containsKey(user))
					here.remove(user);
				if (attending.containsKey(user))
					attending.remove(user);
				// should be one of the numbered emoji
			} else {
				if (here.containsKey(user))
					here.remove(user);
				attending.put(user.asMember(server.getDiscordGuild().getId()).block(), react);
				// someone is no longer marked as here (both new people and
				// changed status)
				if (sentHereMessage) {
					StringBuilder mentionsMessage = new StringBuilder();
					for (Entry<Member, ReactionEmoji> entry : here.entrySet())
						mentionsMessage.append(entry.getKey().getMention() + ", ");
					if (mentionsMessage.length() > 2) {
						mentionsMessage.append(user.getMention() + " is no longer marked as here");
						sentHereMessage = false;
						channel.createMessage(mentionsMessage.toString()).block();
					}
				}
			}
		}
		msg.edit((m) -> m.setEmbed(getEmbed(server))).block();
	}

	private Form getForm(String[] parts) {
		if (parts.length == 1)
			return pokemon.getData().getDefaultForm();
		return pokemon.getData().getFormSet().getForm(parts[1]);
	}

	@Override
	protected Consumer<? super EmbedCreateSpec> getEmbed(StarotaServer server) {
		EmbedBuilder builder = new EmbedBuilder();
		PokedexEntry entry = null;
		if (pokemon != null && StarotaModule.isModuleEnabled(server, BaseModules.POKEDEX))
			entry = GoHubDatabase.getEntry(pokemon, form == null ? null : (form.getGoHubFormName()));
		if (pokemon != null) {
			String titleString = (pokemon.getFormSet().isDefaultOnly() ? "" : form.getName() + " ")
					+ pokemon.getData().getName() + " Raid ";
			if (boss.getTier() == 6)
				titleString += MiscUtils.getEmojiDisplay(EmojiServerHelper.getEmoji(EX_RAID_EMOJI));
			else {
				String raidEmojiDisplay = MiscUtils
						.getEmojiDisplay(EmojiServerHelper.getEmoji(RAID_EMOJI));
				for (int i = 0; i < boss.getTier(); i++)
					titleString += raidEmojiDisplay;
			}
			builder.withTitle(titleString);
			builder.withColor(boss.getColor());
			builder.withThumbnail(ImageHelper.getOfficalArtwork(pokemon, form));
			if (entry != null) {
				String boostedString = "";
				for (EnumWeather w : entry.weatherInfluences)
					if (w != null && w.getEmoji() != null)
						boostedString += w.getEmoji().asFormat();
				builder.appendField("Boss Weather Boosts:", boostedString, true);
			}
		}

		// forecasted weather boosts
		if (server.isWeatherSetup()) {
			// current boosts
			StringBuilder forecastedWeather = new StringBuilder();
			EnumWeather[] forecastedBoosts = server.getCurrentPossibleBoosts();
			boolean isDaylight = server.isDaylight();
			SimpleDateFormat hourFormat = server.getHourFormat();
			long time = System.currentTimeMillis();
			if (forecastedBoosts.length > 0) {
				forecastedWeather.append(String.format("**%s**: ", hourFormat.format(time)));
				for (EnumWeather weather : forecastedBoosts)
					forecastedWeather.append(MiscUtils.getEmojiDisplay(weather.getEmoji(isDaylight)));
			} else
				forecastedWeather.append(
						(String.format("**%s**: No weather forecast found", hourFormat.format(time))));

			// boosts for the next hour
			EnumWeather[] nextForecastedBoosts = server.getPossibleBoosts(1);
			boolean isDaylightNext = server.isDaylight(1);
			time += 3600000;
			if (nextForecastedBoosts.length > 0) {
				forecastedWeather.append(String.format("\n**%s**: ", hourFormat.format(time)));
				for (EnumWeather weather : nextForecastedBoosts)
					forecastedWeather
							.append(MiscUtils.getEmojiDisplay(weather.getEmoji(isDaylightNext)));
			} else
				forecastedWeather.append(
						(String.format("\n**%s**: No weather forecast found", hourFormat.format(time))));

			builder.appendField("Weather Forecasts:", forecastedWeather.toString(), false);
		}

		builder.appendField("Time:", time, true);
		builder.appendField("Location:", location, true);

		String attendingString = "";
		for (Entry<Member, ReactionEmoji> e : attending.entrySet()) {
			if (server.hasProfile(e.getKey()))
				attendingString += MiscUtils.getEmojiName(e.getValue()) + " "
						+ e.getKey().getDisplayName() + " "
						+ MiscUtils.getEmojiDisplay(server.getProfile(e.getKey()).getTeam().getEmoji())
						+ "\n";
			else
				attendingString += MiscUtils.getEmojiName(e.getValue()) + " "
						+ e.getKey().getDisplayName() + "\n";
		}
		int numAttending = 0;
		for (Entry<Member, ReactionEmoji> e : attending.entrySet())
			numAttending += getNumberFromEmoji(e.getValue());
		if (!attendingString.isEmpty())
			builder.appendField(String.format("Attending: (%d)", numAttending), attendingString, false);

		String hereString = "";
		for (Entry<Member, ReactionEmoji> e : here.entrySet()) {
			if (server.hasProfile(e.getKey()))
				hereString += MiscUtils.getEmojiName(e.getValue()) + " " + e.getKey().getDisplayName()
						+ " "
						+ MiscUtils.getEmojiDisplay(server.getProfile(e.getKey()).getTeam().getEmoji())
						+ "\n";
			else
				hereString += MiscUtils.getEmojiName(e.getValue()) + " " + e.getKey().getDisplayName()
						+ "\n";
		}
		int numHere = 0;
		for (Entry<Member, ReactionEmoji> e : here.entrySet())
			numHere += getNumberFromEmoji(e.getValue());
		if (!hereString.isEmpty())
			builder.appendField(String.format("Here: (%d/%d)", numHere, numHere + numAttending),
					hereString, false);

		if (entry != null) {
			String cpsString = "";
			cpsString += "**Max CP**: " + entry.CPs.max + "\n";
			cpsString += "**Catch CP**: " + entry.CPs.raidCaptureMin + "-" + entry.CPs.raidCaptureMax
					+ "\n";
			cpsString += "**Boosted Catch CP**: " + entry.CPs.raidCaptureBoostMin + "-"
					+ entry.CPs.raidCaptureBoostMax;
			builder.appendField("Important CPs:", cpsString, false);

			String counterString = "";
			int rank = 1;
			for (DexCounter c : entry.getTopCounters())
				if (c != null)
					counterString += String.format("#%d %s", rank++, c);
			if (!counterString.isEmpty())
				builder.appendField("Counters:", counterString, false);
		}
		return builder.build();
	}

	@Override
	public void onSend(StarotaServer server, MessageChannel channel, Message msg) {
		if (boss != null) {
			for (int i = 0; i < EMOJI_NAMES.length - 2; i++)
				msg.addReaction(ReactionEmoji.unicode(EMOJI_NAMES[i])).block();
			msg.addReaction(EmojiServerHelper.getEmoji(EMOJI_NAMES[5])).block();
			msg.addReaction(ReactionEmoji.unicode(EMOJI_NAMES[6])).block();
			return;
		}
		List<RaidBoss> bosses = SilphRoadData.getBosses(tier);
		for (RaidBoss b : bosses) {
			if (boss != null)
				break;
			String postfix = b.getForm() == null || b.getPokemon().getFormSet().isDefaultOnly() ? ""
					: "_" + b.getForm().getEmojiPostfix();
			msg.addReaction(EmojiServerHelper.getEmoji(b.getPokemon().getData().getName() + postfix,
					ImageHelper.getOfficalArtwork(b.getPokemon(), b.getForm()))).block();
		}
	}

	@Override
	public Consumer<? super EmbedCreateSpec> getHelpEmbed(BotServer server) {
		EmbedBuilder builder = new EmbedBuilder();
		if (pokemon == null)
			builder.appendDescription("**Reaction Usage**:\n"
					+ "React with the raid Pokemon to indicate what Pokemon the raid is for.");
		else
			builder.appendDescription("**Reaction Usage**:\n"
					+ "React with a numbered emoji to indicate how many people you will be bringing to the raid.\n"
					+ "React with " + MiscUtils.getEmojiDisplay(EmojiServerHelper.getEmoji("here"))
					+ " to indicate that you and your other people are at the raid location.\n"
					+ "React with " + ReactionEmoji.unicode(EMOJI_NAMES[6]).getRaw()
					+ " if you are no longer able to attend.");
		return builder.build();
	}

	private int getNumberFromEmoji(ReactionEmoji emoji) {
		String emojiName = MiscUtils.getEmojiName(emoji);
		for (int i = 0; i < 5; i++)
			if (emojiName != null && emojiName.equals(EMOJI_NAMES[i]))
				return i + 1;
		return 0;
	}

}
