package us.myles_selim.starota.leek_duck;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import sx.blah.discord.api.internal.json.objects.EmbedObject;
import sx.blah.discord.util.EmbedBuilder;
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.enums.EnumPokemon;
import us.myles_selim.starota.misc.data_types.cache.CachedData;
import us.myles_selim.starota.misc.data_types.cache.ClearCache;
import us.myles_selim.starota.misc.utils.EmojiServerHelper;
import us.myles_selim.starota.misc.utils.MiscUtils;

public class LeekDuckData {

	public static final EmbedObject LOADING_EMBED;

	static {
		EmbedBuilder builder = new EmbedBuilder();
		builder.appendDesc("Loading Leek Duck... " + EmojiServerHelper.getEmoji("loading"));
		LOADING_EMBED = builder.build();
	}

	private static final String LEEK_URL = "https://leekduck.com";
	private static final String EVENTS_URL = "https://leekduck.com/events/";
	private static final String DITTO_URL = "https://leekduck.com/FindDitto/";

	private static final Pattern EVENT_GENERAL_PATTERN = Pattern
			.compile("<div class=\"event-item\".*?img class.*?<br.*?<\\/div>");
	private static final Pattern COLOR_PATTERN = Pattern.compile("solid #[0-9A-F]{6}");
	private static final Pattern THUMBNAIL_PATTERN = Pattern.compile("event-img\" src=\".*?\"");
	private static final Pattern TITLE_PATTERN = Pattern.compile("event-title\">.*?<");
	private static final Pattern DURATION_PATTERN = Pattern.compile("event-duration\">.*?<");
	private static final Pattern DESCRIPTION_PATTERN = Pattern.compile("event-desc\">.*?<");
	private static final Pattern END_COUNTDOWN_PATTERN = Pattern.compile("data-countdown=\"[0-9]*?\">");
	private static final Pattern START_COUNTDOWN_PATTERN = Pattern
			.compile("data-countdown-start=\"[0-9]*?\">");
	private static final Pattern LINK_PATTERN = Pattern.compile("event-details-link\" href=\".*?\"");

	private static CachedData<List<LeekEvent>> EVENT_CACHE;
	private static List<LeekEvent> RUN_UP_EVENTS;

	public static List<LeekEvent> getEvents() {
		if (EVENT_CACHE != null && !EVENT_CACHE.hasPassed(3600000)) // 1 hour
			return EVENT_CACHE.getValue();
		List<LeekEvent> newEvents = new ArrayList<>();
		try {
			URL url = new URL(EVENTS_URL);
			URLConnection conn = url.openConnection();
			conn.setRequestProperty("User-Agent", Starota.HTTP_USER_AGENT);
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String html = "";
			String line = null;
			while ((line = in.readLine()) != null)
				html += line;
			Matcher generalMatcher = EVENT_GENERAL_PATTERN.matcher(html);
			while (generalMatcher.find()) {
				String match = generalMatcher.group();
				// System.out.println(match);
				LeekEvent event = new LeekEvent();

				Matcher colorMatcher = COLOR_PATTERN.matcher(match);
				if (colorMatcher.find()) {
					String colorString = colorMatcher.group();
					event.color = Integer.parseInt(colorString.substring(7), 16);
				}

				Matcher thumbMatcher = THUMBNAIL_PATTERN.matcher(match);
				if (thumbMatcher.find()) {
					String thumbnail = thumbMatcher.group();
					event.thumbnail = LEEK_URL + thumbnail.substring(16, thumbnail.length() - 1);
				}

				Matcher titleMatcher = TITLE_PATTERN.matcher(match);
				if (titleMatcher.find()) {
					String title = titleMatcher.group();
					event.title = title.substring(13, title.length() - 1);
				}

				Matcher durationMatcher = DURATION_PATTERN.matcher(match);
				if (durationMatcher.find()) {
					String duration = durationMatcher.group();
					event.duration = duration.substring(16, duration.length() - 1);
				}

				Matcher descriptionMatcher = DESCRIPTION_PATTERN.matcher(match);
				if (descriptionMatcher.find()) {
					String description = descriptionMatcher.group();
					event.description = MiscUtils
							.fixCharacters(description.substring(16, description.length() - 1));
				}

				Matcher endCountdownMatcher = END_COUNTDOWN_PATTERN.matcher(match);
				if (endCountdownMatcher.find()) {
					String countdownS = endCountdownMatcher.group();
					countdownS = countdownS.substring(16, countdownS.length() - 2);
					event.endCountdown = Long.parseLong(countdownS);
				}

				Matcher startCountdownMatcher = START_COUNTDOWN_PATTERN.matcher(match);
				if (startCountdownMatcher.find()) {
					String countdownS = startCountdownMatcher.group();
					countdownS = countdownS.substring(22, countdownS.length() - 2);
					event.startCountdown = Long.parseLong(countdownS);
				}

				Matcher linkMatcher = LINK_PATTERN.matcher(match);
				if (linkMatcher.find()) {
					String link = linkMatcher.group();
					event.link = link.substring(26, link.length() - 1);
					if (link.contains("twitter"))
						event.linkTitle = "Offical Tweet";
					else if (link.contains("pokemongolive"))
						event.linkTitle = "Offical Post";
					else
						event.linkTitle = "More Information";
				}
				newEvents.add(event);
			}
			EVENT_CACHE = new CachedData<>(newEvents);
			return newEvents;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static List<LeekEvent> getRunningUpcomingEvents() {
		if (EVENT_CACHE != null && !EVENT_CACHE.hasPassed(3600000)) // 1 hour
			return RUN_UP_EVENTS;
		RUN_UP_EVENTS = new ArrayList<>();
		for (LeekEvent e : getEvents())
			if (!e.isOver())
				RUN_UP_EVENTS.add(e);
		return RUN_UP_EVENTS;
	}

	public static boolean areEventsLoaded() {
		if (EVENT_CACHE != null && !EVENT_CACHE.hasPassed(3600000)) // 1 hour
			return true;
		return false;
	}

	public static long getEventsCacheTime() {
		if (EVENT_CACHE == null)
			return -1;
		return EVENT_CACHE.getCreationTime();
	}

	public static class LeekEvent {

		private int color;
		private String thumbnail;
		private String title;
		private String duration;
		private String description;
		private long endCountdown;
		private long startCountdown;
		private String link;
		private String linkTitle;

		public int getColor() {
			return this.color;
		}

		public String getThumbnail() {
			return this.thumbnail;
		}

		public String getTitle() {
			return this.title;
		}

		public String getDuration() {
			return this.duration;
		}

		public String getDescription() {
			return this.description;
		}

		public long getCountdown() {
			return this.endCountdown;
		}

		public boolean isOver() {
			if (this.endCountdown == 0)
				return false;
			Date date = new Date(this.endCountdown);
			return date.before(new Date());
		}

		public boolean hasStarted() {
			if (this.startCountdown == 0)
				return !isOver();
			Date date = new Date(this.endCountdown);
			return date.before(new Date());
		}

		public String getLink() {
			return this.link;
		}

		public String getLinkTitle() {
			return this.linkTitle;
		}

		public String getTimeLeft() {
			String output = null;
			long diff = this.endCountdown - System.currentTimeMillis();
			if (this.endCountdown == 0) {
				if (this.startCountdown == 0)
					return "Event has not started yet";
				output = "Starts in ";
				diff = this.startCountdown - System.currentTimeMillis();
			}
			if (output == null)
				output = "Ends in ";
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

	}

	private static final Pattern DITTOABLE_PATTERN = Pattern.compile("listlabel\">.*?<");
	private static CachedData<List<EnumPokemon>> DITTOABLES;

	public static List<EnumPokemon> getDittoablePokemon() {
		if (DITTOABLES != null && !DITTOABLES.hasPassed(86400000)) // 1 day
			return DITTOABLES.getValue();
		List<EnumPokemon> newDittos = new ArrayList<>();
		try {
			URL url = new URL(DITTO_URL);
			URLConnection conn = url.openConnection();
			conn.setRequestProperty("User-Agent", Starota.HTTP_USER_AGENT);
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String html = "";
			String line = null;
			while ((line = in.readLine()) != null)
				html += line;
			Matcher generalMatcher = DITTOABLE_PATTERN.matcher(html);
			while (generalMatcher.find()) {
				String match = generalMatcher.group();
				String pokemonName = match.substring(11, match.length() - 1);
				EnumPokemon pokemon = EnumPokemon.getPokemon(pokemonName);
				if (pokemon == null) {
					Starota.submitError("Cannot find dittoable named " + pokemonName,
							new IllegalArgumentException("Cannot find dittoable named " + pokemonName));
					continue;
				}
				newDittos.add(pokemon);
			}
			DITTOABLES = new CachedData<>(newDittos);
			return newDittos;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static boolean areDittoablesLoaded() {
		if (DITTOABLES != null && !DITTOABLES.hasPassed(86400000)) // 1 day
			return true;
		return false;
	}

	public static long getDittoableCacheTime() {
		if (DITTOABLES == null)
			return -1;
		return DITTOABLES.getCreationTime();
	}

	@ClearCache("leekduck")
	public static void clearCache() {
		EVENT_CACHE = null;
		DITTOABLES = null;
	}

}
