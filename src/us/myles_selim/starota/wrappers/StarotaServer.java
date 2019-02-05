package us.myles_selim.starota.wrappers;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;

import org.discordbots.api.client.entity.SimpleUser;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IUser;
import us.myles_selim.ebs.EBList;
import us.myles_selim.ebs.EBStorage;
import us.myles_selim.ebs.IOHelper;
import us.myles_selim.starota.Pair;
import us.myles_selim.starota.ServerDataHelper;
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.Starota.BaseModules;
import us.myles_selim.starota.commands.registry.PrimaryCommandHandler;
import us.myles_selim.starota.enums.EnumGender;
import us.myles_selim.starota.enums.EnumPokemon;
import us.myles_selim.starota.geofence.GeoPoint;
import us.myles_selim.starota.geofence.GeoRegion;
import us.myles_selim.starota.geofence.GeoRegion.DataTypeGeoRegion;
import us.myles_selim.starota.leaderboards.Leaderboard;
import us.myles_selim.starota.modules.StarotaModule;
import us.myles_selim.starota.profiles.PlayerProfile;
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
	private EBStorage options;
	private EBList<TradeboardPost> tradeboard;
	private EBStorage leaderboards;
	private Map<String, Long> battleTimers = new ConcurrentHashMap<>();
	private EBStorage regions;

	private StarotaServer(IGuild server) {
		this.guild = server;
	}

	public IGuild getDiscordGuild() {
		return this.guild;
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
		return hasProfile(user) ? profiles.get(user.getStringID(), PlayerProfile.class) : null;
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
		for (String k : profiles.getKeys())
			profilesL.add(profiles.get(k, PlayerProfile.class));
		return Collections.unmodifiableList(profilesL);
	}

	public PlayerProfile getProfile(String pogoName) {
		if (!StarotaModule.isModuleEnabled(this, BaseModules.PROFILES))
			return null;
		for (String k : profiles.getKeys()) {
			PlayerProfile profile = profiles.get(k, PlayerProfile.class);
			if (profile.getPoGoName().equalsIgnoreCase(pogoName))
				return profile;
		}
		return null;
	}
	// end profile stuffs

	// start options stuffs
	public EBStorage getOptions() {
		return options;
	}

	public boolean hasKey(String key) {
		return options.containsKey(key);
	}

	public boolean hasKey(String key, Class<?> type) {
		return options.containsKey(key) && type != null && type.isInstance(options.get(key));
	}

	public Object getValue(String key) {
		return options.get(key);
	}

	public void setValue(String key, Object val) {
		options.set(key, val);
	}

	public void clearValue(String key) {
		options.clearKey(key);
	}

	public void clearOptions() {
		for (String key : options.getKeys())
			options.clearKey(key);
		File optionsFile = new File(new File(Starota.DATA_FOLDER, "options"),
				guild.getLongID() + IOHelper.EBS_EXTENSION);
		if (optionsFile.exists())
			optionsFile.delete();
	}
	// end options stuffs

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
		if (hasKey(TRADE_ID_KEY))
			nextPostId = (int) getValue(TRADE_ID_KEY);
		if (nextPostId >= 9999) {
			IUser serverOwner = guild.getOwner();
			System.out.println("Server " + guild.getName() + ", run by " + serverOwner.getName() + "#"
					+ serverOwner.getDiscriminator() + " has hit the 9,999 trade post limit");
			return null;
		}
		TradeboardPost post = new TradeboardPost(nextPostId, lookingFor, owner, pokemon, form, shiny,
				gender, legacy);
		setValue(TRADE_ID_KEY, nextPostId + 1);
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
		if (leaderboards.getKeys().size() >= Starota.getMaxLeaderboards(guild))
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

	// start other stuff
	@SuppressWarnings("deprecation")
	public float getVoterPercent() {
		try {
			Calendar today = Calendar.getInstance();
			List<SimpleUser> voters = Starota.getBotListAPI()
					.getVoters(Long.toString(Starota.STAROTA_ID)).toCompletableFuture().get();
			int numVoters = 0;
			for (SimpleUser su : voters)
				if (guild.getUsersByName(su.getUsername()) != null)
					numVoters++;
			return numVoters * 100.0f / (guild.getUsers().size() * today.get(Calendar.DATE));
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		return 0;
	}
	// end other stuff

	private static final Map<Long, StarotaServer> SERVERS = new ConcurrentHashMap<>();

	public static StarotaServer getServer(IGuild guild) {
		if (guild == null)
			return null;
		if (SERVERS.containsKey(guild.getLongID()))
			return SERVERS.get(guild.getLongID());
		StarotaServer server = new StarotaServer(guild);

		server.profiles = ServerDataHelper.getEBSFromFolder(guild, PROFILES)
				.registerType(new PlayerProfile.DataTypePlayerProfile());
		server.options = ServerDataHelper.getEBSFromFolder(guild, OPTIONS);
		server.tradeboard = ServerDataHelper.getEBListFromFolder(guild, TRADEBOARD,
				new TradeboardPost());
		server.leaderboards = ServerDataHelper.getEBSFromFolder(guild, LEADERBOARDS)
				.registerType(new Leaderboard());
		server.regions = ServerDataHelper.getEBSFromFolder(guild, REGIONS)
				.registerType(new DataTypeGeoRegion());

		SERVERS.put(guild.getLongID(), server);
		return server;
	}

}
