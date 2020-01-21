package us.myles_selim.starota.silph_road.arena_stats;

import java.util.ArrayList;
import java.util.List;

public class SilphArenaData {

	public final Data data;
	private float t_level;

	private SilphArenaData() {
		data = null;
	}

	public float getLevel() {
		return t_level;
	}

	@Override
	public String toString() {
		return String.format("SilphArenaData [DataObject=%s, t_level=%s]", data, t_level);
	}

	public class Data {

		private String id;
		private String user_id;
		private String trainer_type_id;
		private String avatar_id;
		private String gender;
		private String skintone;
		private String team;
		private String xp;
		private TopSixPokemon Top_6_pokemon;
		private String badge_sort;
		private String country;
		private String home_region;
		private String pokedex_count;
		private String playstyle;
		private String raid_average;
		private String current_goal;
		private String is_public;
		private String show_trainer_name;
		private String show_discords;
		private String show_reddit;
		private String created;
		private String modified;
		// private Avatar Avatar;
		private String in_game_username;
		private String dechex;
		private String avatar_title;
		private String avatar_filename;
		private String avatar_gender;
		private String avatar_skintone;
		private int has_top_6;
		private float arenaStatus;
		private String arenaGlobalRank;
		private String arenaGlobalRankPercentile;
		private float arenaWL;
		private int arenaTotalRankedMatchups;
		private int arenaUniqueWins;
		private String arenaPlayerTier;
		private String arenaPlayerTierSlug;
		private float arenaPlayerTierProgress;
		private String discord_username;
		private String reddit_username;
		private float trainerLevelProgressPercent;
		private float pokedexCountProgressPercent;
		private int trainer_level;
		private String migrations_reported;
		private int available_pokemon;
		private int travelers_met_count;
		private String joined;
		private String playDescriptor;
		private String teamText;
		private String teamClass;
		private List<Badge> placeholderBadges = new ArrayList<Badge>();
		private List<Badge> badges = new ArrayList<Badge>();
		private List<Badge> displayedBadges = new ArrayList<Badge>();
		private int meetups_attended;

		private Data() {}

		public String getId() {
			return id;
		}

		public String getUserId() {
			return user_id;
		}

		public String getTrainerTypeId() {
			return trainer_type_id;
		}

		public String getAvatarId() {
			return avatar_id;
		}

		public String getGender() {
			return gender;
		}

		public String getSkintone() {
			return skintone;
		}

		public String getTeam() {
			return team;
		}

		public String getXp() {
			return xp;
		}

		public TopSixPokemon getTop6Pokemon() {
			return Top_6_pokemon;
		}

		public String getBadgeSort() {
			return badge_sort;
		}

		public String getCountry() {
			return country;
		}

		public String getHomeRegion() {
			return home_region;
		}

		public String getPokedexCount() {
			return pokedex_count;
		}

		public String getPlaystyle() {
			return playstyle;
		}

		public String getRaidAverage() {
			return raid_average;
		}

		public String getCurrentGoal() {
			return current_goal;
		}

		public String getIsPublic() {
			return is_public;
		}

		public String getShowTrainerName() {
			return show_trainer_name;
		}

		public String getShowDiscords() {
			return show_discords;
		}

		public String getShowReddit() {
			return show_reddit;
		}

		public String getCreated() {
			return created;
		}

		public String getModified() {
			return modified;
		}

		// public Avatar getAvatar() {
		// return Avatar;
		// }

		public String getInGameUsername() {
			return in_game_username;
		}

		public String getDechex() {
			return dechex;
		}

		public String getAvatarTitle() {
			return avatar_title;
		}

		public String getAvatarFilename() {
			return avatar_filename;
		}

		public String getAvatarGender() {
			return avatar_gender;
		}

		public String getAvatarSkintone() {
			return avatar_skintone;
		}

		public boolean getHasTop6() {
			return has_top_6 == 1;
		}

		public float getArenaStatus() {
			return arenaStatus;
		}

		public String getArenaGlobalRank() {
			return arenaGlobalRank;
		}

		private String arenaGlobalRankReal;

		public String getArenaGlobalRankReal() {
			if (arenaGlobalRankReal != null)
				return arenaGlobalRankReal;
			int index = arenaGlobalRank.lastIndexOf('>');
			if (index == -1) {
				arenaGlobalRankReal = arenaGlobalRank;
				return arenaGlobalRankReal;
			}
			arenaGlobalRankReal = "#" + arenaGlobalRank.substring(index + 1, arenaGlobalRank.length());
			return arenaGlobalRankReal;
		}

		public String getArenaGlobalRankPercentile() {
			return arenaGlobalRankPercentile;
		}

		public float getArenaWL() {
			return arenaWL;
		}

		public int getArenaTotalRankedMatchups() {
			return arenaTotalRankedMatchups;
		}

		public int getArenaUniqueWins() {
			return arenaUniqueWins;
		}

		public String getArenaPlayerTier() {
			return arenaPlayerTier;
		}

		public String getArenaPlayerTierSlug() {
			return arenaPlayerTierSlug;
		}

		public float getArenaPlayerTierProgress() {
			return arenaPlayerTierProgress;
		}

		public String getDiscordUsername() {
			return discord_username;
		}

		public String getRedditUsername() {
			return reddit_username;
		}

		public float getTrainerLevelProgressPercent() {
			return trainerLevelProgressPercent;
		}

		public float getPokedexCountProgressPercent() {
			return pokedexCountProgressPercent;
		}

		public float getTrainerLevel() {
			return trainer_level;
		}

		public String getMigrationsReported() {
			return migrations_reported;
		}

		public float getAvailablePokemon() {
			return available_pokemon;
		}

		public float getTravelersMetCount() {
			return travelers_met_count;
		}

		public String getJoined() {
			return joined;
		}

		public String getPlayDescriptor() {
			return playDescriptor;
		}

		public String getTeamText() {
			return teamText;
		}

		public String getTeamClass() {
			return teamClass;
		}

		public List<Badge> getPlaceholderBadges() {
			return placeholderBadges;
		}

		public List<Badge> getBadges() {
			return badges;
		}

		public List<Badge> getDisplayedBadges() {
			return displayedBadges;
		}

		public int getMeetupsAttended() {
			return meetups_attended;
		}

		@Override
		public String toString() {
			return String.format(
					"Data [id=%s, user_id=%s, trainer_type_id=%s, avatar_id=%s, gender=%s, skintone=%s, team=%s, xp=%s, Top_6_pokemon=%s, badge_sort=%s, country=%s, home_region=%s, pokedex_count=%s, playstyle=%s, raid_average=%s, current_goal=%s, is_public=%s, show_trainer_name=%s, show_discords=%s, show_reddit=%s, created=%s, modified=%s, in_game_username=%s, dechex=%s, avatar_title=%s, avatar_filename=%s, avatar_gender=%s, avatar_skintone=%s, has_top_6=%s, arenaStatus=%s, arenaGlobalRank=%s, arenaGlobalRankPercentile=%s, arenaWL=%s, arenaTotalRankedMatchups=%s, arenaUniqueWins=%s, arenaPlayerTier=%s, arenaPlayerTierSlug=%s, arenaPlayerTierProgress=%s, discord_username=%s, reddit_username=%s, trainerLevelProgressPercent=%s, pokedexCountProgressPercent=%s, trainer_level=%s, migrations_reported=%s, available_pokemon=%s, travelers_met_count=%s, joined=%s, playDescriptor=%s, teamText=%s, teamClass=%s, placeholderBadges=%s, badges=%s, displayedBadges=%s, meetups_attended=%s]",
					id, user_id, trainer_type_id, avatar_id, gender, skintone, team, xp, Top_6_pokemon,
					badge_sort, country, home_region, pokedex_count, playstyle, raid_average,
					current_goal, is_public, show_trainer_name, show_discords, show_reddit, created,
					modified, in_game_username, dechex, avatar_title, avatar_filename, avatar_gender,
					avatar_skintone, has_top_6, arenaStatus, arenaGlobalRank, arenaGlobalRankPercentile,
					arenaWL, arenaTotalRankedMatchups, arenaUniqueWins, arenaPlayerTier,
					arenaPlayerTierSlug, arenaPlayerTierProgress, discord_username, reddit_username,
					trainerLevelProgressPercent, pokedexCountProgressPercent, trainer_level,
					migrations_reported, available_pokemon, travelers_met_count, joined, playDescriptor,
					teamText, teamClass, placeholderBadges, badges, displayedBadges, meetups_attended);
		}

	}

}