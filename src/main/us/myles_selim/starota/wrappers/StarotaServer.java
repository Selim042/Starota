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
import java.util.TimeZone;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;

import javax.annotation.Nonnull;

import org.discordbots.api.client.entity.SimpleUser;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IRole;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.RequestBuffer;
import us.myles_selim.ebs.EBList;
import us.myles_selim.ebs.EBStorage;
import us.myles_selim.ebs.IOHelper;
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.commands.registry.PrimaryCommandHandler;
import us.myles_selim.starota.commands.settings.Setting;
import us.myles_selim.starota.commands.settings.SettingSet;
import us.myles_selim.starota.commands.settings.SettingSet.DataTypeSettingSet;
import us.myles_selim.starota.commands.settings.SettingSet.EnumReturnSetStatus;
import us.myles_selim.starota.commands.settings.types.SettingBoolean;
import us.myles_selim.starota.commands.settings.types.SettingString;
import us.myles_selim.starota.enums.EnumDonorPerm;
import us.myles_selim.starota.enums.EnumGender;
import us.myles_selim.starota.enums.EnumPokemon;
import us.myles_selim.starota.geofence.GeoPoint;
import us.myles_selim.starota.geofence.GeoRegion;
import us.myles_selim.starota.geofence.GeoRegion.DataTypeGeoRegion;
import us.myles_selim.starota.leaderboards.Leaderboard;
import us.myles_selim.starota.misc.data_types.Pair;
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
import us.myles_selim.starota.webserver.webhooks.EnumWebhookType;

public class StarotaServer {

	private static final File PROFILES = new File(Starota.DATA_FOLDER, "profiles");
	private static final File OPTIONS = new File(Starota.DATA_FOLDER, "options");
	private static final File TRADEBOARD = new File(Starota.DATA_FOLDER, "tradeboard");
	private static final File LEADERBOARDS = new File(Starota.DATA_FOLDER, "leaderboard");
	private static final File REGIONS = new File(Starota.DATA_FOLDER, "regions");

	public static final String TRADE_ID_KEY = "trade_id";

	private final IGuild guild;
	public EBStorage profiles;
	private EBStorage data;
	private EBList<TradeboardPost> tradeboard;
	private EBStorage leaderboards;
	private Map<String, Long> battleTimers = new ConcurrentHashMap<>();
	private EBStorage regions;

	// private int numDays;
	// private int day;
	// private Map<EnumUsageType, int[]> avgUsage;
	// private Map<EnumUsageType, int[]> todayUsage;

	private StarotaServer(IGuild server) {
		this.guild = server;
	}

	public IGuild getDiscordGuild() {
		return Starota.getGuild(this.guild.getLongID());
		// return this.guild;
	}

	public String getPrefix() {
		return PrimaryCommandHandler.getPrefix(guild);
	}

	// start profile stuffs
	public boolean hasProfile(IUser user) {
		if (!StarotaModule.isModuleEnabled(this, BaseModules.PROFILES))
			return false;
		return profiles.containsKey(user.getStringID());
	}

	public PlayerProfile getProfile(IUser user) {
		if (!StarotaModule.isModuleEnabled(this, BaseModules.PROFILES))
			return null;
		if (hasProfile(user)) {
			PlayerProfile profile = profiles.get(user.getStringID(), PlayerProfile.class);
			updateLevel(profile);
			return profile;
		}
		return null;
	}

	public PlayerProfile setProfile(IUser user, PlayerProfile profile) {
		if (!StarotaModule.isModuleEnabled(this, BaseModules.PROFILES))
			return null;
		profiles.set(user.getStringID(), profile);
		return profile;
	}

	public List<PlayerProfile> getProfiles() {
		if (!StarotaModule.isModuleEnabled(this, BaseModules.PROFILES))
			return null;
		List<PlayerProfile> profilesL = new LinkedList<>();
		for (String k : profiles.getKeys()) {
			PlayerProfile profile = profiles.get(k, PlayerProfile.class);
			updateLevel(profile);
			profilesL.add(profile);
		}
		return Collections.unmodifiableList(profilesL);
	}

	public PlayerProfile getProfile(String pogoName) {
		if (!StarotaModule.isModuleEnabled(this, BaseModules.PROFILES))
			return null;
		for (String k : profiles.getKeys()) {
			PlayerProfile profile = profiles.get(k, PlayerProfile.class);
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
				setProfile(profile.getDiscordUser(), profile);
			}
		}
	}
	// end profile stuffs

	// start data stuffs
	public EBStorage getData() {
		return data;
	}

	public boolean hasDataKey(String key) {
		return data.containsKey(key);
	}

	public boolean hasDataKey(String key, Class<?> type) {
		return data.containsKey(key) && type != null && type.isInstance(data.get(key));
	}

	public Object getDataValue(String key) {
		return data.get(key);
	}

	public <V> V getDataValue(String key, Class<V> type) {
		return data.get(key, type);
	}

	public void setDataValue(String key, Object val) {
		data.set(key, val);
	}

	public void clearDataValue(String key) {
		data.clearKey(key);
	}

	public void clearDataOptions() {
		for (String key : data.getKeys())
			data.clearKey(key);
		File optionsFile = new File(new File(Starota.DATA_FOLDER, "options"),
				guild.getLongID() + IOHelper.EBS_EXTENSION);
		if (optionsFile.exists())
			optionsFile.delete();
	}
	// end data stuffs

	// start settings stuffs

	// end settings stufs

	// start tradeboard stuffs
	public void addPost(PlayerProfile profile, TradeboardPost post) {
		if (!StarotaModule.isModuleEnabled(this, BaseModules.TRADEBOARD))
			return;
		tradeboard.add(post);
	}

	public List<TradeboardPost> getPosts() {
		if (!StarotaModule.isModuleEnabled(this, BaseModules.TRADEBOARD))
			return null;
		return tradeboard.values();
	}

	public List<TradeboardPost> findPosts(boolean lookingFor, IGuild server, EnumPokemon pokemon) {
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
		if (tradeboard == null)
			return Collections.emptyList();
		List<TradeboardPost> matching = new LinkedList<>();
		FormSet fSet = pokemon.getFormSet();
		for (TradeboardPost p : tradeboard.values())
			if (p.isLookingFor() != lookingFor && p.getPokemon().equals(pokemon)
					&& (fSet == null || p.getForm() == null || form.equals(p.getForm()))
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
		for (TradeboardPost p : tradeboard.values())
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
			IUser serverOwner = guild.getOwner();
			System.out.println("Server " + guild.getName() + ", run by " + serverOwner.getName() + "#"
					+ serverOwner.getDiscriminator() + " has hit the 9,999 trade post limit");
			return null;
		}
		TradeboardPost post = new TradeboardPost(nextPostId, lookingFor, owner, pokemon, form, shiny,
				gender, legacy);
		setDataValue(TRADE_ID_KEY, nextPostId + 1);
		tradeboard.addWrapped(post);
		return post;
	}

	public TradeboardPost getPost(int id) {
		if (!StarotaModule.isModuleEnabled(this, BaseModules.TRADEBOARD))
			return null;
		if (id == -1)
			return null;
		for (TradeboardPost t : tradeboard.values())
			if (t != null && t.getId() == id)
				return t;
		return null;
	}

	public List<TradeboardPost> getPosts(IUser user) {
		if (!StarotaModule.isModuleEnabled(this, BaseModules.TRADEBOARD))
			return null;
		List<TradeboardPost> posts = new LinkedList<>();
		for (TradeboardPost t : tradeboard.values())
			if (t != null && t.getOwner() == user.getLongID())
				posts.add(t);
		return Collections.unmodifiableList(posts);
	}

	public TradeboardPost removePost(int id) {
		if (!StarotaModule.isModuleEnabled(this, BaseModules.TRADEBOARD))
			return null;
		TradeboardPost post = null;
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
	public Leaderboard newLeaderboard(String name) {
		if (!StarotaModule.isModuleEnabled(this, BaseModules.LEADERBOARDS))
			return null;
		Leaderboard testBoard = getLeaderboard(name);
		if (testBoard != null)
			return null;
		if (leaderboards.getKeys().size() >= RolePermHelper.getMaxLeaderboards(guild))
			return null;
		Leaderboard board = new Leaderboard(guild, name);
		board.addAlias(name.replaceAll(" ", "_"));
		leaderboards.set(name, board);
		return board;
	}

	public void setLeaderboard(String name, Leaderboard board) {
		leaderboards.set(name, board);
	}

	public int getLeaderboardCount() {
		if (!StarotaModule.isModuleEnabled(this, BaseModules.LEADERBOARDS))
			return 0;
		return leaderboards.getKeys().size();
	}

	public List<Leaderboard> getLeaderboards() {
		if (!StarotaModule.isModuleEnabled(this, BaseModules.LEADERBOARDS))
			return null;
		List<Leaderboard> boards = new ArrayList<>();
		for (String key : leaderboards.getKeys())
			boards.add(leaderboards.get(key, Leaderboard.class));
		return Collections.unmodifiableList(boards);
	}

	public List<Leaderboard> getLeaderboardsActive() {
		if (!StarotaModule.isModuleEnabled(this, BaseModules.LEADERBOARDS))
			return null;
		List<Leaderboard> boards = new ArrayList<>();
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
		if (leaderboards.containsKey(name))
			return leaderboards.get(name, Leaderboard.class);
		for (String key : leaderboards.getKeys()) {
			Leaderboard board = leaderboards.get(key, Leaderboard.class);
			if (stringArrayContainsIgnoreCase(names, board.getDisplayName()))
				return board;
			for (String alias : board.getAliases())
				if (stringArrayContainsIgnoreCase(names, alias))
					return board;
		}
		return null;
	}

	public Leaderboard getLeaderboardActive(String name) {
		if (!StarotaModule.isModuleEnabled(this, BaseModules.LEADERBOARDS))
			return null;
		Leaderboard board = getLeaderboard(name);
		if (!board.isActive())
			return null;
		return board;
	}

	private boolean stringArrayContainsIgnoreCase(String[] arr, String val) {
		for (String v : arr)
			if (v == val || v.equalsIgnoreCase(val))
				return true;
		return false;
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

	// start region stuffs
	public String getRegion(GeoPoint point) {
		for (String key : regions.getKeys())
			if (regions.get(key, GeoRegion.class).coordinateInRegion(point))
				return key;
		return null;
	}

	public IChannel getChannel(EnumWebhookType type, GeoPoint point) {
		for (String key : regions.getKeys()) {
			GeoRegion region = regions.get(key, GeoRegion.class);
			long channelId = region.getHookChannel(type);
			if (channelId != -1 && region.coordinateInRegion(point))
				return Starota.getChannel(channelId);
		}
		return null;
	}

	public GeoRegion defineRegion(String name, GeoRegion region) {
		regions.set(name, region);
		return region;
	}

	public boolean deleteRegion(String name) {
		if (regions.containsKey(name)) {
			regions.clearKey(name);
			return true;
		}
		return false;
	}
	// end region stuffs

	// start vote stuff
	@SuppressWarnings("deprecation")
	public float getVoterPercent() {
		try {
			Calendar today = Calendar.getInstance();
			List<SimpleUser> voters = Starota.getBotListAPI()
					.getVoters(Long.toString(StarotaConstants.STAROTA_ID)).toCompletableFuture().get();
			int numVoters = 0;
			for (SimpleUser su : voters) {
				List<IUser> user = guild.getUsersByName(su.getUsername());
				if (user != null && !user.isEmpty())
					numVoters++;
			}
			return numVoters * 100.0f / (guild.getUsers().size() * today.get(Calendar.DATE));
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public int getEarnedVotePoints() {
		IUser owner = guild.getOwner();
		int sum = 0;
		int vals = 0;
		for (IGuild g : Starota.getClient().getGuilds()) {
			if (g.getOwner().equals(owner)) {
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
		// return 2;
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
		for (IRole role : Starota.getSupportServer()
				.getRolesForUser(this.getDiscordGuild().getOwner())) {
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
		if (this.getDiscordGuild().getOwner().hasRole(perm.getRole()) || perm.getPointsRequired() < 0
				|| currentPoints + perm.getPointsRequired() > earnedPoints
				|| !hasRequiredVoteRewards(perm))
			return false;
		RequestBuffer.request(() -> this.getDiscordGuild().getOwner().addRole(perm.getRole())).get();
		return true;
	}

	/**
	 * Returns if reward was removed
	 */
	public boolean removeVoteReward(EnumDonorPerm perm) {
		if (getUsedVotePoints() <= 0 || !this.getDiscordGuild().getOwner().hasRole(perm.getRole()))
			return false;
		RequestBuffer.request(() -> this.getDiscordGuild().getOwner().removeRole(perm.getRole())).get();
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
	public static final String SETTINGS_KEY = "setting_set";

	private SettingSet getSettings() {
		if (!this.hasDataKey(SETTINGS_KEY, SettingSet.class))
			this.setDataValue(SETTINGS_KEY, getDefaultSettings(this));
		return (SettingSet) this.getDataValue(SETTINGS_KEY);
	}

	public void forEachSetting(Consumer<Setting<?>> consumer) {
		for (Setting<?> s : getSettings())
			consumer.accept(s);
	}

	public <V> V getSetting(String name) {
		return getSettings().getSetting(name);
	}

	public EnumReturnSetStatus setSetting(String name, String value) {
		return getSettings().setSetting(name, value);
	}

	public <T> EnumReturnSetStatus setSetting(String name, T value) {
		return getSettings().setSetting(name, value);
	}

	private static final String TIMEZONE = "timezone";

	public TimeZone getTimezone() {
		SettingSet settings = getSettings();
		if (settings.isEmpty(TIMEZONE)) {
			return MiscUtils.getTimezone(guild.getRegion());
		}
		return null;
	}

	// static settings stuff
	private final static SettingSet DEFAULT_SETTINGS = new SettingSet();

	public static <V> void setDefaultValue(Setting<V> setting) {
		DEFAULT_SETTINGS.addSetting(setting);
	}

	public static SettingSet getDefaultSettings() {
		return new SettingSet(DEFAULT_SETTINGS);
	}

	public static SettingSet getDefaultSettings(StarotaServer server) {
		return new SettingSet(server, DEFAULT_SETTINGS);
	}
	// end settings stuff

	// start other stuff
	public static final String WEBHOOK_SECRET_KEY = "webhook_secret";

	public String getWebhookSecret() {
		if (!data.containsKey(WEBHOOK_SECRET_KEY))
			return null;
		return data.get(WEBHOOK_SECRET_KEY, String.class);
	}
	// end other stuff

	private static final Map<Long, StarotaServer> SERVERS = new ConcurrentHashMap<>();
	private static final Map<Long, StarotaUser> USERS = new ConcurrentHashMap<>();

	public static StarotaServer getServer(IGuild guild) {
		if (guild == null)
			return null;
		if (SERVERS.containsKey(guild.getLongID()))
			return SERVERS.get(guild.getLongID());
		StarotaServer server = new StarotaServer(guild);

		server.profiles = ServerDataHelper.getEBSFromFolder(guild, PROFILES)
				.registerType(new PlayerProfile.DataTypePlayerProfile());
		server.data = ServerDataHelper.getEBSFromFolder(guild, OPTIONS)
				.registerType(new DataTypeServerSettings());
		server.tradeboard = ServerDataHelper.getEBListFromFolder(guild, TRADEBOARD,
				new TradeboardPost());
		server.leaderboards = ServerDataHelper.getEBSFromFolder(guild, LEADERBOARDS)
				.registerType(new Leaderboard());
		server.regions = ServerDataHelper.getEBSFromFolder(guild, REGIONS)
				.registerType(new DataTypeGeoRegion());

		SERVERS.put(guild.getLongID(), server);
		return server;
	}

	public StarotaUser getUser(IUser user) {
		if (user == null)
			return null;
		if (USERS.containsKey(user.getLongID()))
			return USERS.get(user.getLongID());
		StarotaUser sUser = new StarotaUser(this, user);

		return sUser;
	}

	public static void main(String... args) {
		System.out.println("\ndefaults before adding defaults");
		for (Setting<?> s : StarotaServer.DEFAULT_SETTINGS) {
			System.out.println(s.getName() + ": " + s.getValue());
		}
		StarotaServer.setDefaultValue(new SettingString("testString", "value1"));
		StarotaServer.setDefaultValue(new SettingBoolean("testBoolean", false));
		System.out.println("\ndefaults after adding defaults");
		for (Setting<?> s : StarotaServer.DEFAULT_SETTINGS) {
			System.out.println(s.getName() + ": " + s.getValue());
		}
		SettingSet set1 = StarotaServer.getDefaultSettings();
		set1.setSetting("testBoolean", true);
		System.out.println("\nset1 after changing testBoolean");
		for (Setting<?> s : set1) {
			System.out.println(s.getName() + ": " + s.getValue());
		}

		EBStorage ebs1 = new EBStorage().registerType(new DataTypeServerSettings());
		ebs1.set(SETTINGS_KEY, set1);
		byte[] serEbs1 = ebs1.serialize();
		EBStorage ebs2 = EBStorage.deserialize(serEbs1);
		System.out.println("\nebs2 after deserialize");
		for (Setting<?> s : ebs2.get(SETTINGS_KEY, SettingSet.class)) {
			System.out.println(s.getName() + ": " + s.getValue());
		}
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
