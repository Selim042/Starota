package us.myles_selim.starota.wrappers;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;

import javax.annotation.Nonnull;

import org.discordbots.api.client.entity.SimpleUser;

import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.Role;
import discord4j.core.object.util.Snowflake;
import us.myles_selim.ebs.EBList;
import us.myles_selim.ebs.EBStorage;
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.commands.registry.PrimaryCommandHandler;
import us.myles_selim.starota.commands.settings.Setting;
import us.myles_selim.starota.commands.settings.SettingSet;
import us.myles_selim.starota.commands.settings.SettingSet.DataTypeSettingSet;
import us.myles_selim.starota.enums.EnumDonorPerm;
import us.myles_selim.starota.enums.EnumGender;
import us.myles_selim.starota.enums.EnumPokemon;
import us.myles_selim.starota.enums.EnumWeather;
import us.myles_selim.starota.leaderboards.DefaultLeaderboard;
import us.myles_selim.starota.leaderboards.DefaultLeaderboard.EnumBadgeLeaderboard;
import us.myles_selim.starota.leaderboards.Leaderboard;
import us.myles_selim.starota.misc.data_types.BotServer;
import us.myles_selim.starota.misc.data_types.MaxQueue;
import us.myles_selim.starota.misc.data_types.Pair;
import us.myles_selim.starota.misc.data_types.cache.CachedData;
import us.myles_selim.starota.misc.utils.MiscUtils;
import us.myles_selim.starota.misc.utils.RolePermHelper;
import us.myles_selim.starota.misc.utils.ServerDataHelper;
import us.myles_selim.starota.misc.utils.StarotaConstants;
import us.myles_selim.starota.modules.BaseModules;
import us.myles_selim.starota.modules.StarotaModule;
import us.myles_selim.starota.profiles.PlayerProfile;
import us.myles_selim.starota.silph_road.SilphCard;
import us.myles_selim.starota.silph_road.SilphRoadCardUtils;
import us.myles_selim.starota.trading.TradeboardPost;
import us.myles_selim.starota.trading.forms.FormSet;
import us.myles_selim.starota.trading.forms.FormSet.Form;
import us.myles_selim.starota.weather.api.AccuWeatherAPI;
import us.myles_selim.starota.weather.api.WeatherForecast;

public class StarotaServer extends BotServer {

	private static final File PROFILES = new File(Starota.DATA_FOLDER, "profiles");
	private static final File OPTIONS = new File(Starota.DATA_FOLDER, "options");
	private static final File TRADEBOARD = new File(Starota.DATA_FOLDER, "tradeboard");
	private static final File LEADERBOARDS = new File(Starota.DATA_FOLDER, "leaderboard");

	public static final String TRADE_ID_KEY = "trade_id";

	// private EBStorage profiles;
	// private EBStorage data;
	// private EBList<TradeboardPost> tradeboard;
	// private EBStorage leaderboards;
	private Map<String, Long> battleTimers = new ConcurrentHashMap<>();

	// private int numDays;
	// private int day;
	// private Map<EnumUsageType, int[]> avgUsage;
	// private Map<EnumUsageType, int[]> todayUsage;

	private StarotaServer(Snowflake id) {
		super(Starota.getClient(), id);
	}

	public String getPrefix() {
		if (hasDataKey(PrimaryCommandHandler.PREFIX_KEY))
			return String.valueOf(getDataValue(PrimaryCommandHandler.PREFIX_KEY));
		return PrimaryCommandHandler.DEFAULT_PREFIX;
	}

	public void setPrefix(String prefix) {
		setDataValue(PrimaryCommandHandler.PREFIX_KEY, prefix);
	}

	@Override
	protected File getOptionsFile() {
		return OPTIONS;
	}

	public BotServer copy() {
		return new StarotaServer(getDiscordGuild().getId());
	}

	// start profile stuffs
	private EBStorage getProfilesInternal() {
		return ServerDataHelper.getEBSFromFolder(getDiscordGuild(), PROFILES)
				.registerType(new PlayerProfile.DataTypePlayerProfile());
	}

	public boolean hasProfile(Snowflake id) {
		if (!StarotaModule.isModuleEnabled(this, BaseModules.PROFILES))
			return false;
		return getProfilesInternal().containsKey(id.asString());
	}

	public boolean hasProfile(Member user) {
		if (!StarotaModule.isModuleEnabled(this, BaseModules.PROFILES))
			return false;
		return getProfilesInternal().containsKey(user.getId().asString());
	}

	public PlayerProfile getProfile(Member user) {
		if (!StarotaModule.isModuleEnabled(this, BaseModules.PROFILES))
			return null;
		if (hasProfile(user)) {
			PlayerProfile profile = getProfilesInternal().get(user.getId().asString(),
					PlayerProfile.class);
			updateLevel(profile);
			return profile;
		}
		return null;
	}

	public void deleteProfile(Snowflake id) {
		getProfilesInternal().clearKey(id.asString());
	}

	public PlayerProfile setProfile(Member user, PlayerProfile profile) {
		if (!StarotaModule.isModuleEnabled(this, BaseModules.PROFILES))
			return null;
		if ((boolean) this.getSetting(StarotaConstants.Settings.PROFILE_NICKNAME))
			try {
				user.edit((mem) -> mem.setNickname(profile.getPoGoName())).block();
			} catch (Exception e) { /*
									 * catch errors, mainly user has higher role
									 */ }
		getProfilesInternal().set(user.getId().asString(), profile);
		return profile;
	}

	public List<PlayerProfile> getProfiles() {
		if (!StarotaModule.isModuleEnabled(this, BaseModules.PROFILES))
			return null;
		List<PlayerProfile> profilesL = new LinkedList<>();
		EBStorage profsInt = getProfilesInternal();
		for (String k : profsInt.getKeys()) {
			PlayerProfile profile = profsInt.get(k, PlayerProfile.class);
			// updateLevel(profile);
			profilesL.add(profile);
		}
		return Collections.unmodifiableList(profilesL);
	}

	public PlayerProfile getProfile(String pogoName) {
		if (!StarotaModule.isModuleEnabled(this, BaseModules.PROFILES))
			return null;
		for (String k : getProfilesInternal().getKeys()) {
			PlayerProfile profile = getProfilesInternal().get(k, PlayerProfile.class);
			if (profile.getPoGoName().equalsIgnoreCase(pogoName)) {
				updateLevel(profile);
				return profile;
			}
		}
		return null;
	}

	private void updateLevel(PlayerProfile profile) {
		if (SilphRoadCardUtils.hasCard(profile.getPoGoName())) {
			SilphCard card = SilphRoadCardUtils.getCard(profile.getPoGoName());
			if (card.data.trainer_level > profile.getLevel()) {
				profile.setLevel(SilphRoadCardUtils.getCard(profile.getPoGoName()).data.trainer_level);
				setProfile(profile.getDiscordMember(getDiscordGuild()), profile);
			}
		}
	}
	// end profile stuffs

	// start tradeboard stuffs
	private EBList<TradeboardPost> getTradeboardInternal() {
		return ServerDataHelper.getEBListFromFolder(getDiscordGuild(), TRADEBOARD, new TradeboardPost());
	}

	public void addPost(PlayerProfile profile, TradeboardPost post) {
		if (!StarotaModule.isModuleEnabled(this, BaseModules.TRADEBOARD))
			return;
		getTradeboardInternal().add(post);
	}

	public List<TradeboardPost> getPosts() {
		if (!StarotaModule.isModuleEnabled(this, BaseModules.TRADEBOARD))
			return null;
		return getTradeboardInternal().values();
	}

	public List<TradeboardPost> getPostsByMember(Member member) {
		if (!StarotaModule.isModuleEnabled(this, BaseModules.TRADEBOARD))
			return null;
		List<TradeboardPost> ret = new LinkedList<>();
		for (TradeboardPost p : getTradeboardInternal().values())
			if (p.getOwner() == member.getId().asLong())
				ret.add(p);
		return Collections.unmodifiableList(ret);
	}

	public List<TradeboardPost> findPosts(boolean lookingFor, Guild server, EnumPokemon pokemon) {
		return findPosts(lookingFor, pokemon, false);
	}

	public List<TradeboardPost> findPosts(boolean lookingFor, EnumPokemon pokemon, Form form) {
		return findPosts(lookingFor, pokemon, form, false, EnumGender.EITHER, false);
	}

	public List<TradeboardPost> findPosts(boolean lookingFor, EnumPokemon pokemon, boolean shiny) {
		FormSet fSet = pokemon.getFormSet();
		return findPosts(lookingFor, pokemon, fSet == null ? null : fSet.getDefaultForm(), shiny,
				EnumGender.EITHER, false);
	}

	public List<TradeboardPost> findPosts(boolean lookingFor, EnumPokemon pokemon, Form form,
			boolean shiny, EnumGender gender, boolean legacy) {
		if (!StarotaModule.isModuleEnabled(this, BaseModules.TRADEBOARD))
			return null;
		EBList<TradeboardPost> tradeboard = getTradeboardInternal();
		if (tradeboard == null)
			return Collections.emptyList();
		List<TradeboardPost> matching = new LinkedList<>();
		FormSet fSet = pokemon.getFormSet();
		for (TradeboardPost p : tradeboard.values())
			if (p.isLookingFor() != lookingFor && p.getPokemon().equals(pokemon)
					&& (fSet == null || p.getForm() == form || form.equals(p.getForm()))
					&& (!shiny || p.isShiny() == shiny) && (p.getGender() == EnumGender.EITHER
							|| gender == EnumGender.EITHER || gender == p.getGender())
					&& (!legacy || p.isLegacy()))
				matching.add(p);
		return matching;
	}

	public List<TradeboardPost> findPosts(EnumPokemon pokemon) {
		return findPosts(pokemon, false);
	}

	public List<TradeboardPost> findPosts(EnumPokemon pokemon, Form form) {
		return findPosts(pokemon, form, false, EnumGender.EITHER, false);
	}

	public List<TradeboardPost> findPosts(EnumPokemon pokemon, boolean shiny) {
		FormSet fSet = pokemon.getFormSet();
		return findPosts(pokemon, fSet == null ? null : fSet.getDefaultForm(), shiny, EnumGender.EITHER,
				false);
	}

	public List<TradeboardPost> findPosts(EnumPokemon pokemon, Form form, boolean shiny,
			EnumGender gender, boolean legacy) {
		if (!StarotaModule.isModuleEnabled(this, BaseModules.TRADEBOARD))
			return null;
		List<TradeboardPost> matching = new LinkedList<>();
		for (TradeboardPost p : getTradeboardInternal().values())
			if (p.getPokemon().equals(pokemon) && (form == null || p.getForm() == form)
					&& (!shiny || p.isShiny() == shiny)
					&& (gender == EnumGender.EITHER || gender == p.getGender())
					&& (!legacy || p.isLegacy()))
				matching.add(p);
		return matching;
	}

	public TradeboardPost newPost(boolean lookingFor, long owner, EnumPokemon pokemon) {
		return newPost(lookingFor, owner, pokemon, null, false, EnumGender.EITHER, false);
	}

	public TradeboardPost newPost(boolean lookingFor, long owner, EnumPokemon pokemon, boolean shiny) {
		return newPost(lookingFor, owner, pokemon, null, shiny, EnumGender.EITHER, false);
	}

	public TradeboardPost newPost(boolean lookingFor, long owner, EnumPokemon pokemon, Form form) {
		return newPost(lookingFor, owner, pokemon, form, false, EnumGender.EITHER, false);
	}

	public TradeboardPost newPost(boolean lookingFor, long owner, EnumPokemon pokemon, Form form,
			boolean shiny, EnumGender gender, boolean legacy) {
		if (!StarotaModule.isModuleEnabled(this, BaseModules.TRADEBOARD))
			return null;
		int nextPostId = 1;
		if (hasDataKey(TRADE_ID_KEY))
			nextPostId = (int) getDataValue(TRADE_ID_KEY);
		if (nextPostId >= 9999) {
			Member serverOwner = getDiscordGuild().getOwner().block();
			System.out.println("Server " + getDiscordGuild().getName() + ", run by "
					+ serverOwner.getUsername() + "#" + serverOwner.getDiscriminator()
					+ " has hit the 9,999 trade post limit");
			return null;
		}
		TradeboardPost post = new TradeboardPost(nextPostId, lookingFor, owner, pokemon, form, shiny,
				gender, legacy);
		setDataValue(TRADE_ID_KEY, nextPostId + 1);
		getTradeboardInternal().addWrapped(post);
		return post;
	}

	public TradeboardPost getPost(int id) {
		if (!StarotaModule.isModuleEnabled(this, BaseModules.TRADEBOARD))
			return null;
		if (id == -1)
			return null;
		for (TradeboardPost t : getTradeboardInternal().values())
			if (t != null && t.getId() == id)
				return t;
		return null;
	}

	public List<TradeboardPost> getPosts(Member user) {
		if (!StarotaModule.isModuleEnabled(this, BaseModules.TRADEBOARD))
			return null;
		List<TradeboardPost> posts = new LinkedList<>();
		for (TradeboardPost t : getTradeboardInternal().values())
			if (t != null && t.getOwner() == user.getId().asLong())
				posts.add(t);
		return Collections.unmodifiableList(posts);
	}

	public TradeboardPost removePost(int id) {
		if (!StarotaModule.isModuleEnabled(this, BaseModules.TRADEBOARD))
			return null;
		TradeboardPost post = null;
		EBList<TradeboardPost> tradeboard = getTradeboardInternal();
		for (TradeboardPost t : tradeboard.values())
			if (t != null && t.getId() == id) {
				post = t;
				tradeboard.remove(t);
				break;
			}
		return post;
	}
	// end tradeboard stuffs

	// start leaderboard stuffs
	private EBStorage getLeaderboardsInternal() {
		return ServerDataHelper.getEBSFromFolder(getDiscordGuild(), LEADERBOARDS)
				.registerType(new Leaderboard());
	}

	public Leaderboard newLeaderboard(String name) {
		if (!StarotaModule.isModuleEnabled(this, BaseModules.LEADERBOARDS))
			return null;
		Leaderboard testBoard = getLeaderboard(name);
		if (testBoard != null)
			return null;
		int activeBoards = 0;
		EBStorage leaderboards = getLeaderboardsInternal();
		for (String key : leaderboards.getKeys())
			if (leaderboards.get(key, Leaderboard.class).isActive())
				activeBoards++;
		if (activeBoards >= RolePermHelper.getMaxLeaderboards(getDiscordGuild()))
			return null;
		Leaderboard board = new Leaderboard(getDiscordGuild(), name);
		leaderboards.set(name, board);
		return board;
	}

	public void setLeaderboard(String name, Leaderboard board) {
		getLeaderboardsInternal().set(name, board);
	}

	public int getLeaderboardCount() {
		if (!StarotaModule.isModuleEnabled(this, BaseModules.LEADERBOARDS))
			return 0;
		return getLeaderboardsInternal().getKeys().size();
	}

	public List<Leaderboard> getLeaderboards() {
		if (!StarotaModule.isModuleEnabled(this, BaseModules.LEADERBOARDS))
			return null;
		updateDefaultLeaderboards();
		List<Leaderboard> boards = new ArrayList<>();
		EBStorage leaderboards = getLeaderboardsInternal();
		for (String key : leaderboards.getKeys())
			boards.add(leaderboards.get(key, Leaderboard.class));
		return Collections.unmodifiableList(boards);
	}

	public List<Leaderboard> getLeaderboardsActive() {
		if (!StarotaModule.isModuleEnabled(this, BaseModules.LEADERBOARDS))
			return null;
		updateDefaultLeaderboards();
		List<Leaderboard> boards = new ArrayList<>();
		EBStorage leaderboards = getLeaderboardsInternal();
		for (String key : leaderboards.getKeys()) {
			Leaderboard board = leaderboards.get(key, Leaderboard.class);
			if (board.isActive())
				boards.add(board);
		}
		return Collections.unmodifiableList(boards);
	}

	public Leaderboard getLeaderboard(String name) {
		if (!StarotaModule.isModuleEnabled(this, BaseModules.LEADERBOARDS))
			return null;
		String[] names = new String[] { name, name.replaceAll(" ", "_"), name.replaceAll(" ", "-"),
				name.replaceAll(" ", "") };
		EBStorage leaderboards = getLeaderboardsInternal();
		if (leaderboards.containsKey(name))
			return leaderboards.get(name, Leaderboard.class);
		for (String key : leaderboards.getKeys()) {
			Leaderboard board = leaderboards.get(key, Leaderboard.class);
			if (MiscUtils.stringArrayContainsIgnoreCase(names, board.getDisplayName()))
				return board;
			for (String alias : board.getAliases())
				if (MiscUtils.stringArrayContainsIgnoreCase(names, alias))
					return board;
		}
		return null;
	}

	public Leaderboard getLeaderboardActive(String name) {
		if (!StarotaModule.isModuleEnabled(this, BaseModules.LEADERBOARDS))
			return null;
		Leaderboard board = getLeaderboard(name);
		if (board == null || !board.isActive())
			return null;
		return board;
	}

	private void updateDefaultLeaderboards() {
		EBStorage leaderboards = getLeaderboardsInternal();
		for (DefaultLeaderboard board : DEFAULT_LEADERBOARDS)
			if (!leaderboards.containsKey(board.getDisplayName()))
				leaderboards.set(board.getDisplayName(), board.toGuildLeaderboard(getDiscordGuild()));
	}

	// static leaderboard stuffs
	private static final List<DefaultLeaderboard> DEFAULT_LEADERBOARDS = new LinkedList<>();

	public static void registerDefaultLeaderboards() {
		if (!DEFAULT_LEADERBOARDS.isEmpty())
			return;

		for (EnumBadgeLeaderboard badge : EnumBadgeLeaderboard.values())
			DEFAULT_LEADERBOARDS.add(new DefaultLeaderboard(badge));
	}

	public static List<DefaultLeaderboard> getDefaultLeaderboards() {
		return Collections.unmodifiableList(DEFAULT_LEADERBOARDS);
	}
	// end leaderboard stuffs

	// start pvp stuffs
	public long isBattleReady(String user) {
		if (battleTimers.containsKey(user) && battleTimers.get(user) > System.currentTimeMillis())
			return battleTimers.get(user) - System.currentTimeMillis();
		else
			battleTimers.remove(user);
		return -1;
	}

	public void setReady(String user, Long duration) {
		battleTimers.put(user, System.currentTimeMillis() + duration);
	}

	public void notReady(String user) {
		battleTimers.remove(user);
	}

	public List<Pair<PlayerProfile, Long>> findBattle() {
		pruneTimers();
		List<Pair<PlayerProfile, Long>> profiles = new ArrayList<>();
		for (Entry<String, Long> e : battleTimers.entrySet())
			profiles.add(new Pair<>(getProfile(e.getKey()), e.getValue() - System.currentTimeMillis()));
		return Collections.unmodifiableList(profiles);
	}

	private void pruneTimers() {
		for (Entry<String, Long> e : battleTimers.entrySet())
			if (e.getValue() < System.currentTimeMillis())
				battleTimers.remove(e.getKey());
	}
	// end pvp stuffs

	// start vote stuff
	private CachedData<List<SimpleUser>> votes;

	@SuppressWarnings("deprecation")
	public float getVoterPercent() {
		try {
			Calendar today = Calendar.getInstance();
			List<SimpleUser> voters;
			if (votes == null || votes.hasPassed(900000L)) // 15 mins
				votes = new CachedData<>(Starota.getBotListAPI()
						.getVoters(StarotaConstants.STAROTA_ID.asString()).toCompletableFuture().get());
			voters = votes.getValue();
			int numVoters = 0;
			List<Snowflake> members = getDiscordGuild().getMembers().map((m) -> m.getId()).collectList()
					.onErrorReturn(Collections.emptyList()).block();
			for (SimpleUser su : voters) {
				if (members.contains(Snowflake.of(su.getId())))
					numVoters++;
			}
			if (members.isEmpty())
				return 0;
			return numVoters * 100.0f / (members.size() * today.get(Calendar.DATE));
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public int getEarnedVotePoints() {
		Snowflake owner = getDiscordGuild().getOwnerId();
		int sum = 0;
		int vals = 0;
		for (Guild g : Starota.getClient().getGuilds().collectList().block()) {
			if (g.getOwnerId().equals(owner)) {
				sum += StarotaServer.getServer(g).getEarnedVotePointsInternal();
				vals++;
			}
		}
		if (vals == 0)
			return sum;
		return sum / vals;
	}

	private int getEarnedVotePointsInternal() {
		float per = getVoterPercent();
		if (per > 98.0f)
			return EnumDonorPerm.getMaxPoints();
		int points = 0;
		if (per > 1.50f)
			points++;
		points += per / 5.00f;
		return points > EnumDonorPerm.getMaxPoints() ? EnumDonorPerm.getMaxPoints() : points;
	}

	public int getUsedVotePoints() {
		int points = 0;
		for (EnumDonorPerm p : getVoteRewards())
			if (p.getPointsRequired() > 0)
				points += p.getPointsRequired();
		return points;
	}

	public List<EnumDonorPerm> getVoteRewards() {
		List<EnumDonorPerm> donorPerms = new ArrayList<>();
		Guild supportServer = Starota.getGuild(StarotaConstants.SUPPORT_SERVER.asLong());
		if (!supportServer.getMembers().any((m) -> m.getId().equals(getDiscordGuild().getOwnerId()))
				.block())
			return Collections.emptyList();
		Member owner = supportServer.getMemberById(getDiscordGuild().getOwnerId()).block();
		for (Role role : owner.getRoles().collectList().block()) {
			EnumDonorPerm perm = EnumDonorPerm.getPermForRole(role);
			if (perm != null)
				donorPerms.add(perm);
		}
		return Collections.unmodifiableList(donorPerms);
	}

	/**
	 * Returns if reward was added
	 */
	public boolean addVoteReward(EnumDonorPerm perm) {
		int currentPoints = getUsedVotePoints();
		int earnedPoints = getEarnedVotePoints();
		if (getDiscordGuild().getOwner().block().getRoleIds().contains(perm.getRoleId())
				|| perm.getPointsRequired() < 0
				|| currentPoints + perm.getPointsRequired() > earnedPoints
				|| !hasRequiredVoteRewards(perm))
			return false;
		getDiscordGuild().getOwner().block().addRole(perm.getRoleId());
		return true;
	}

	/**
	 * Returns if reward was removed
	 */
	public boolean removeVoteReward(EnumDonorPerm perm) {
		Member owner = getDiscordGuild().getOwner().block().asMember(StarotaConstants.SUPPORT_SERVER)
				.block();
		if (getUsedVotePoints() <= 0 || !owner.getRoleIds().contains(perm.getRoleId()))
			return false;
		owner.removeRole(perm.getRoleId());
		List<EnumDonorPerm> toRemove = new ArrayList<>();
		for (EnumDonorPerm p : getVoteRewards())
			if (!hasRequiredVoteRewards(p))
				toRemove.add(p);
		for (EnumDonorPerm p : toRemove)
			removeVoteReward(p);
		return true;
	}

	public boolean hasRequiredVoteRewards(EnumDonorPerm perm) {
		return getVoteRewards().containsAll(Arrays.asList(perm.getPreReqs()));
	}
	// end vote stuff

	// start usage stuff
	// public void used(EnumUsageType type) {
	// OffsetDateTime instant = Instant.now().atOffset(ZoneOffset.UTC);
	// updateUsage();
	// int[] used = todayUsage.get(type);
	// if (used == null) {
	// used = new int[24];
	// todayUsage.put(type, used);
	// }
	// LocalTime timeOfDay = instant.toLocalTime();
	// used[timeOfDay.getHour()]++;
	// }
	//
	// private void updateUsage() {
	// OffsetDateTime instant = Instant.now().atOffset(ZoneOffset.UTC);
	// if (day == instant.getDayOfYear())
	// return;
	// numDays++;
	// day = instant.getDayOfYear();
	//
	// for (EnumUsageType type : EnumUsageType.values()) {
	// if (!avgUsage.containsKey(type) && !todayUsage.containsKey(type))
	// continue;
	// int[] avgUsed = avgUsage.get(type);
	// if (avgUsed == null) {
	// avgUsed = new int[24];
	// avgUsage.put(type, avgUsed);
	// }
	// int[] todayUsed = todayUsage.get(type);
	// if (todayUsed == null)
	// todayUsed = new int[24];
	// for (int i = 0; i < 24; i++)
	// avgUsed[i] += todayUsed[i] / 2;
	// }
	// todayUsage.clear();
	// }
	//
	// public int getAvgUsage(EnumUsageType type, int hour) {
	// updateUsage();
	// int[] used = avgUsage.get(type);
	// if (used != null)
	// return used[hour];
	// return 0;
	// }
	//
	// public int getDays() {
	// return this.numDays;
	// }
	// end usage stuff

	// start settings stuff
	private final static SettingSet DEFAULT_SETTINGS = new SettingSet();

	@Override
	protected SettingSet getDefaultSettings(BotServer server) {
		return new SettingSet(DEFAULT_SETTINGS);
	}

	public static <V> void setDefaultValue(Setting<V> setting) {
		DEFAULT_SETTINGS.addSetting(setting);
	}

	public static SettingSet getDefaultSettings() {
		return new SettingSet(DEFAULT_SETTINGS);
	}

	public static SettingSet getDefaultSettings(StarotaServer server) {
		return new SettingSet(server.getDiscordGuild().getId(), DEFAULT_SETTINGS);
	}

	public static SettingSet getDefaultSettings(Snowflake server) {
		return new SettingSet(server, DEFAULT_SETTINGS);
	}
	// end settings stuff

	// start weatger stuff
	private final Queue<WeatherForecast[]> forecasts = new MaxQueue<>(8);

	/**
	 * Only to be called by the loop in {@link us.myles_selim.starota.Starota}
	 */
	@Deprecated
	public void updateWeather() {
		WeatherForecast[] forecasts = AccuWeatherAPI.getForecast(this);
		if (forecasts.length > 0)
			this.forecasts.add(forecasts);
	}

	public boolean isWeatherSetup() {
		return !getSetting(StarotaConstants.Settings.WEATHER_API_TOKEN).equals("null")
				&& !getSetting(StarotaConstants.Settings.COORDS).equals("null");
	}

	public Queue<WeatherForecast[]> getForecasts() {
		return this.forecasts;
	}

	public EnumWeather[] getCurrentPossibleBoosts() {
		return getPossibleBoosts(0);
	}

	public EnumWeather[] getPossibleBoosts(int hourOffset) {
		if (!isWeatherSetup())
			return new EnumWeather[0];
		List<EnumWeather> boosts = new ArrayList<>();
		// rounding to previous hour
		long currentHour = ((System.currentTimeMillis() / 1000) / 3600) * 3600 + (hourOffset * 3600);
		for (WeatherForecast[] forecasts : this.forecasts) {
			for (WeatherForecast f : forecasts) {
				if (f.getEpochDateTime() == currentHour) {
					EnumWeather pogoWeather = f.getPoGoWeather();
					if (!boosts.contains(pogoWeather))
						boosts.add(pogoWeather);
				}
			}
		}
		return boosts.toArray(new EnumWeather[0]);
	}
	// end weather stuff

	// private static final Map<Snowflake, StarotaServer> SERVERS = new
	// ConcurrentHashMap<>();
	private static final Map<Snowflake, StarotaUser> USERS = new ConcurrentHashMap<>();

	public static StarotaServer getServer(Snowflake id) {
		if (id == null)
			return null;
		return BotServer.getServer(Starota.getClient(), id);
	}

	public static StarotaServer getServer(Guild guild) {
		if (guild == null)
			return null;
		return BotServer.getServer(Starota.getClient(), guild);
	}

	public StarotaUser getUser(Member user) {
		if (user == null)
			return null;
		if (USERS.containsKey(user.getId()))
			return USERS.get(user.getId());
		StarotaUser sUser = new StarotaUser(this, user);

		return sUser;
	}

	public static class DataTypeServerSettings extends DataTypeSettingSet {

		public DataTypeServerSettings() {
			super();
		}

		public DataTypeServerSettings(@Nonnull SettingSet settings) {
			super(settings);
		}

		@Override
		protected SettingSet getDefaultSettings() {
			return StarotaServer.getDefaultSettings();
		}

	}

}
