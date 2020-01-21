package us.myles_selim.starota.raids.raid_train;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Consumer;

import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.MessageChannel;
import discord4j.core.object.entity.User;
import discord4j.core.object.reaction.ReactionEmoji;
import discord4j.core.spec.EmbedCreateSpec;
import us.myles_selim.starota.enums.EnumWeather;
import us.myles_selim.starota.misc.utils.EmbedBuilder;
import us.myles_selim.starota.misc.utils.EmojiServerHelper;
import us.myles_selim.starota.misc.utils.MiscUtils;
import us.myles_selim.starota.reaction_messages.IHelpReactionMessage;
import us.myles_selim.starota.reaction_messages.ReactionMessage;
import us.myles_selim.starota.wrappers.BotServer;
import us.myles_selim.starota.wrappers.StarotaServer;

public class RaidTrainReactionMessage extends ReactionMessage implements IHelpReactionMessage {

	private static final String[] EMOJI_NAMES = new String[] { "1⃣", "2⃣", "3⃣", "4⃣", "5⃣", "here",
			"🚫", "⏩" };

	private final String time;
	private final String[] locs;

	private int locIndex = 0;

	private final Map<Member, ReactionEmoji> attending = new HashMap<>();
	private final Map<Member, ReactionEmoji> here = new HashMap<>();
	private boolean sentHereMessage = false;

	public RaidTrainReactionMessage(String time, String[] locs) {
		this.time = time;
		this.locs = locs;
	}

	@Override
	public void onReactionAdded(StarotaServer server, MessageChannel channel, Message msg, User user,
			ReactionEmoji react) {
		msg.removeReaction(react, user.getId()).block();
		if (user.isBot())
			return;
		String emojiName = MiscUtils.getEmojiName(react);
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
					mentionsMessage.append("everyone appears to at " + locs[locIndex]);
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
			// next raid location
		} else if (emojiName.equals(EMOJI_NAMES[7])) {
			if (locIndex < locs.length - 1) {
				locIndex++;
				here.forEach((m, e) -> attending.put(m, e));
				here.clear();
				sentHereMessage = false;
			}
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
		msg.edit((m) -> m.setEmbed(getEmbed(server))).block();
	}

	@Override
	protected Consumer<? super EmbedCreateSpec> getEmbed(StarotaServer server) {
		EmbedBuilder builder = new EmbedBuilder().withTitle("Raid Train:");

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
		StringBuilder locations = new StringBuilder();
		for (int i = 0; i < locs.length; i++) {
			locations.append(locs[i]);
			if (i == locIndex)
				locations.append(
						" :" + MiscUtils.getEmojiName(ReactionEmoji.unicode("arrow_left")) + ":");
			locations.append("\n");
		}
		builder.appendField("Locations:", locations.toString(), false);

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
		return builder.build();
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onSend(StarotaServer server, MessageChannel channel, Message msg) {
		this.addHelpButton(this, server);
		for (int i = 0; i < 5; i++)
			msg.addReaction(ReactionEmoji.unicode(EMOJI_NAMES[i])).block();
		msg.addReaction(EmojiServerHelper.getEmoji(EMOJI_NAMES[5])).block();
		msg.addReaction(ReactionEmoji.unicode(EMOJI_NAMES[6])).block();
		msg.addReaction(ReactionEmoji.unicode(EMOJI_NAMES[7])).block();
	}

	@Override
	public Consumer<? super EmbedCreateSpec> getHelpEmbed(BotServer server) {
		EmbedBuilder builder = new EmbedBuilder();
		builder.appendDescription("**Reaction Usage**:\n"
				+ "React with a numbered emoji to indicate how many people you will be bringing to the raid.\n"
				+ "React with " + MiscUtils.getEmojiDisplay(EmojiServerHelper.getEmoji("here"))
				+ " to indicate that you and your other people are at the raid location.\n"
				+ "React with " + ReactionEmoji.unicode(EMOJI_NAMES[6]).getRaw()
				+ " if you are no longer able to attend.\n" + "React with "
				+ ReactionEmoji.unicode("fast_forward").getRaw() + " to continue to the next location.");
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
