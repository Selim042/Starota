package us.myles_selim.starota.search.events;

import us.myles_selim.starota.leek_duck.events.StarotaEvent;
import us.myles_selim.starota.search.SearchOperator;
import us.myles_selim.starota.search.SearchOperator.LlambadaSearchOperator;

public class EventOperators {

	public static final SearchOperator<StarotaEvent> LONG_TERM = new LlambadaSearchOperator<StarotaEvent>(
			StarotaEvent.class, e -> !e.longTerm, "long", "longTerm");
	public static final SearchOperator<StarotaEvent> RAID_ORIENTED = new LlambadaSearchOperator<StarotaEvent>(
			StarotaEvent.class, e -> !e.raidOriented, "raidOriented", "raidFocused");
	public static final SearchOperator<StarotaEvent> RAIDS_CHANGED = new LlambadaSearchOperator<StarotaEvent>(
			StarotaEvent.class, e -> !e.raidsChanged, "raidsChanged", "bossesChanged");
	public static final SearchOperator<StarotaEvent> SPECIAL_RESEARCH = new LlambadaSearchOperator<StarotaEvent>(
			StarotaEvent.class, e -> !e.newSpecialResearch, "specialResearch");

	private static boolean registered = false;

	public static void registerOperators() {
		if (registered)
			return;
		registered = true;
	}

}
