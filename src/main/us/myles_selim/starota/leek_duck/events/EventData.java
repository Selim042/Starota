package us.myles_selim.starota.leek_duck.events;

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import sx.blah.discord.api.internal.json.objects.EmbedObject;
import sx.blah.discord.util.EmbedBuilder;
import us.myles_selim.starota.enums.EnumPokemon;
import us.myles_selim.starota.misc.data_types.cache.CachedData;
import us.myles_selim.starota.misc.data_types.cache.ClearCache;
import us.myles_selim.starota.misc.utils.EmojiServerHelper;
import us.myles_selim.starota.misc.utils.StarotaConstants;

public class EventData {

	public static final EmbedObject LOADING_EMBED;

	static {
		EmbedBuilder builder = new EmbedBuilder();
		builder.appendDesc("Loading event data... " + EmojiServerHelper.getEmoji("loading"));
		LOADING_EMBED = builder.build();
	}

	private static final Gson GSON;

	static {
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(StarotaEvent.ResearchReward.class,
				new JsonDeserializer<StarotaEvent.ResearchReward>() {

					@Override
					public StarotaEvent.ResearchReward deserialize(JsonElement json, Type typeOfT,
							JsonDeserializationContext context) throws JsonParseException {
						if (!json.isJsonObject())
							return null;
						JsonObject obj = json.getAsJsonObject();
						if (obj.has("pokemon"))
							return new StarotaEvent.PokemonEntry(
									EnumPokemon.valueOf(obj.get("pokemon").getAsString()),
									obj.get("shiny").getAsBoolean());
						else if (obj.has("itemName"))
							return new StarotaEvent.ItemReward(obj.get("itemName").getAsString(),
									obj.get("num").getAsInt());
						return null;
					}
				});
		GSON = builder.create();
	}

	private static final String EVENTS_URL = "https://raw.githubusercontent.com/Selim042/Misc-PoGo-Stuffs/master/events.json";

	private static CachedData<StarotaEvent[]> EVENT_CACHE;
	private static StarotaEvent[] RUN_UP_EVENTS;

	public static StarotaEvent[] getEvents() {
		if (EVENT_CACHE != null && !EVENT_CACHE.hasPassed(3600000)) // 1 hour
			return EVENT_CACHE.getValue();
		try {
			URL url = new URL(EVENTS_URL);
			URLConnection conn = url.openConnection();
			conn.setRequestProperty("User-Agent", StarotaConstants.HTTP_USER_AGENT);
			conn.setRequestProperty("Cache-Control", "no-cache,no-store,must-revalidate");
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
		runUps.sort(new Comparator<StarotaEvent>() {

			@Override
			public int compare(StarotaEvent arg0, StarotaEvent arg1) {
				return Long.compare(arg0.startTime, arg1.startTime);
			}
		});
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
