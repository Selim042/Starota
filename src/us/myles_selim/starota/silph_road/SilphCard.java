package us.myles_selim.starota.silph_road;

public class SilphCard {

	public SilphData data;

	public static class SilphData {

		public String card_id;
		public String xp;
		public String home_region;
		public String pokedex_count;
		public String raid_average;
		public String is_public;

		public int[] top_6_pokemon;

		public String team;
		public String playstyle;
		public String modified;
		public String goal;
		public int trainer_level;
		public int nest_migrations;
		public String title;
		public String avatar;
		public String in_game_username;
		public String joined;

		public SilphBadgeData[] badges;
		public SilphCheckin[] checkins;

		public int handshakes;
		public SilphSocial[] socials;

	}

	public static class SilphBadgeData {

		public String count;
		public String awarded;
		public SilphBadge Badge;

	}

	public static class SilphBadge {

		public String slug;
		public String name;
		public String description;
		public String image;

	}

	public static class SilphCheckin {

		public String name;
		public String description;
		public String image;
		public String is_global;
		public String start;
		public String end;
		public SilphCheckinTime EventCheckin;

	}

	public static class SilphCheckinTime {

		public String created;

	}

	public static class SilphSocial {

		public String username;
		public String vendor;

	}

}
