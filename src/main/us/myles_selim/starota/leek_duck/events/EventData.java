package us.myles_selim.starota.leek_duck.events;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import sx.blah.discord.api.internal.json.objects.EmbedObject;
import sx.blah.discord.util.EmbedBuilder;
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.misc.data_types.cache.CachedData;
import us.myles_selim.starota.misc.data_types.cache.ClearCache;
import us.myles_selim.starota.misc.utils.EmojiServerHelper;

public class EventData {

	public static final EmbedObject LOADING_EMBED;

	static {
		EmbedBuilder builder = new EmbedBuilder();
		builder.appendDesc("Loading event data... " + EmojiServerHelper.getEmoji("loading"));
		LOADING_EMBED = builder.build();
	}

	private static final Gson GSON = new Gson();

	private static final String EVENTS_URL = "https://raw.githubusercontent.com/Selim042/Misc-PoGo-Stuffs/master/events.json";

	private static CachedData<StarotaEvent[]> EVENT_CACHE;
	private static StarotaEvent[] RUN_UP_EVENTS;

	public static StarotaEvent[] getEvents() {
		if (EVENT_CACHE != null && !EVENT_CACHE.hasPassed(3600000)) // 1 hour
			return EVENT_CACHE.getValue();
		try {
			URL url = new URL(EVENTS_URL);
			URLConnection conn = url.openConnection();
			conn.setRequestProperty("User-Agent", Starota.HTTP_USER_AGENT);
			StarotaEvent[] events = GSON.fromJson(new InputStreamReader(conn.getInputStream()),
					StarotaEvent[].class);
			EVENT_CACHE = new CachedData<>(events);
			return events;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static StarotaEvent[] getRunningUpcomingEvents() {
		if (EVENT_CACHE != null && !EVENT_CACHE.hasPassed(3600000) // 1 hour
				&& RUN_UP_EVENTS != null)
			return RUN_UP_EVENTS;
		List<StarotaEvent> runUps = new ArrayList<>();
		for (StarotaEvent e : getEvents())
			if (!e.isOver())
				runUps.add(e);
		RUN_UP_EVENTS = runUps.toArray(new StarotaEvent[0]);
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

	@ClearCache("events")
	public static void clearCache() {
		EVENT_CACHE = null;
	}

}
