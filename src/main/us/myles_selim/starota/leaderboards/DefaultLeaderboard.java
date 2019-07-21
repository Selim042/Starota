package us.myles_selim.starota.leaderboards;

import discord4j.core.object.entity.Guild;

public class DefaultLeaderboard extends Leaderboard {

	public DefaultLeaderboard(IDefaultLeaderboard defaultBoard) {
		super(null, defaultBoard.getDisplayName());
		this.setType(EnumUpdateType.OCR);
		this.setEnabled(false);
		for (String al : defaultBoard.getAliases())
			this.addAlias(al);
		this.setColor(defaultBoard.getColor());
	}

	public Leaderboard toGuildLeaderboard(Guild guild) {
		Leaderboard newBoard = new Leaderboard(guild, this.getDisplayName());
		newBoard.setValue(this);
		return newBoard;
	}

	@Override
	public Leaderboard updateEntry(LeaderboardEntry entry) {
		return this;
	}

	public static enum EnumBadgeLeaderboard implements IDefaultLeaderboard {
		// normal badges
		JOGGER("Jogger", -1),
		KANTO_POKEDEX("Kanto Pokedex", -1, "kanto"),
		COLLECTOR("Collector", -1, "caught"),
		SCIENTIST("Scientist", -1, "evolve", "evolved"),
		BREEDER("Breeder", -1, "hatch", "hatched"),
		BACKPACKER("Backpacker", -1, "pokestop", "pokestops"),
		BATTLE_GIRL("Battle Girl", -1, "gym_battle", "battle", "gym_battles", "battles"),
		JOHTO_POKEDEX("Johto Pokedex", -1, "johto"),
		BERRY_MASTER("Berry Master", -1, "berry", "feed_berry", "berries"),
		GYM_LEADER("Gym Leader", -1, "gym_time"),
		HOENN_POKEDEX("Hoenn Pokedex", -1, "hoenn"),
		POKEMON_RANGER("Pokemon Ranger", -1, "tasks", "research"),
		IDOL("Idol", -1, "best_friends"),
		PILOT("Pilot", -1, "distance"),
		FISHERMAN("Fisherman", -1, "magikarp", "karp"),
		ACE_TRAINER("Ace Trainer", -1, "train"),
		YOUNGSTER("Youngster", -1, "rattata"),
		PIKACHU_FAN("Pikachu Fan", -1, "piakchu"),
		CHAMPION("Champion", -1, "raid", "raids"),
		BATTLE_LEGEND("Battle Legend", -1, "legendary", "legendary_raid"),
		GENTLEMAN("Gentleman", -1, "trade", "trades"),
		SINNOH_POKEDEX("Sinnoh Pokedex", -1, "sinnoh"),
		UNOWN_CAUGHT("Unown Caught", -1, "unown"),
		GREAT_LEAGUE_VETERAN("Great League Veteran", -1, "great", "great_league"),
		ULTRA_LEAGUE_VETERAN("Ultra League Veteran", -1, "ultra", "ultra_league"),
		CAMERAMAN("Cameraman", -1, "snapshot", "photobomb", "photo"),
		MASTER_LEAGUE_VETERAN("Master League Veteran", -1, "master", "master_league"),
		// type caught badges
		SCHOOLKID("Schoolkid", -1, "normal"),
		BLACK_BELT("Black Belt", -1, "fighting"),
		BIRD_KEEPER("Bird Keeper", -1, "flying"),
		PUNK_GIRL("Punk Girl", -1, "poison"),
		RUIN_MANIAC("Ruin Maniac", -1, "ground"),
		HIKER("Hiker", -1, "rock"),
		BUG_CATCHER("Bug Catcher", -1, "bug"),
		HEX_MANIAC("Hex Maniac", -1, "ghost"),
		DEPOT_AGENT("Depot Agent", -1, "steel"),
		KINDLER("Kindler", -1, "fire"),
		SWIMMER("Swimmer", -1, "water"),
		GARDENER("Gardener", -1, "grass"),
		ROCKER("Rocker", -1, "electric"),
		PSYCHIC("Psychic", -1, "psychic"),
		SKIER("Skier", -1, "skier"),
		DRAGON_TAMER("Dragon Tamer", -1, "dragon"),
		DELINQUENT("Delinquent", -1, "dark"),
		FAIRY_TALE_GIRL("Fairy Tale Girl", -1, "fairy");

		private final String displayName;
		private final String[] aliases;
		private final int color;

		EnumBadgeLeaderboard(String displayName, int color, String... aliases) {
			this.displayName = displayName;
			this.aliases = aliases;
			this.color = color;
		}

		@Override
		public String getDisplayName() {
			return this.displayName;
		}

		@Override
		public String[] getAliases() {
			return this.aliases;
		}

		@Override
		public int getColor() {
			return this.color;
		}
	}

	public static interface IDefaultLeaderboard {

		public String getDisplayName();

		public String[] getAliases();

		public int getColor();

	}

}
