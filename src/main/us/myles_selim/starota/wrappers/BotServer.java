package us.myles_selim.starota.wrappers;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

import discord4j.core.DiscordClient;
import discord4j.core.object.PermissionOverwrite;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.GuildChannel;
import discord4j.core.object.entity.VoiceChannel;
import discord4j.core.object.util.Permission;
import discord4j.core.object.util.PermissionSet;
import discord4j.core.object.util.Snowflake;
import us.myles_selim.ebs.EBList;
import us.myles_selim.ebs.EBStorage;
import us.myles_selim.ebs.IOHelper;
import us.myles_selim.ebs.callbacks.OnWriteCallback;
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.commands.settings.Setting;
import us.myles_selim.starota.commands.settings.SettingSet;
import us.myles_selim.starota.commands.settings.SettingSet.EnumReturnSetStatus;
import us.myles_selim.starota.commands.settings.types.ServerSetting;
import us.myles_selim.starota.info_channels.InfoChannel;
import us.myles_selim.starota.misc.data_types.cache.CachedData;
import us.myles_selim.starota.misc.utils.MiscUtils;
import us.myles_selim.starota.misc.utils.ServerDataHelper;
import us.myles_selim.starota.misc.utils.StarotaConstants;
import us.myles_selim.starota.weather.api.AccuWeatherAPI;
import us.myles_selim.starota.weather.api.WeatherLocation;
import us.myles_selim.starota.weather.api.WeatherTimezone;
import us.myles_selim.starota.wrappers.StarotaServer.DataTypeServerSettings;

public abstract class BotServer {

	private static final Map<DiscordClient, Class<?>> SERVER_TYPES = new HashMap<>();

	private final DiscordClient client;
	private final Guild guild;

	public BotServer(DiscordClient client, Snowflake id) {
		this.client = client;
		this.guild = client.getGuildById(id).block();
		// if (!client.equals(guild.getClient()))
		// throw new IllegalArgumentException("guild client does not match
		// supplied client");
	}

	public static <S extends BotServer> void registerServerType(DiscordClient client,
			Class<S> serverClass) {
		try {
			serverClass.getDeclaredConstructor(Snowflake.class);
		} catch (NoSuchMethodException | SecurityException e) {
			throw new IllegalArgumentException(String.format(
					"serverClass (%s) must include a constructor including only a Snowflake param",
					serverClass.getName()));
		}
		SERVER_TYPES.put(client, serverClass);
	}

	private static final Map<Snowflake, List<BotServer>> SERVERS = new ConcurrentHashMap<>();

	@SuppressWarnings("unchecked")
	public static <S extends BotServer> S getServer(DiscordClient client, Snowflake id) {
		if (!SERVER_TYPES.containsKey(client))
			return null;
		if (SERVERS.containsKey(id)) {
			List<BotServer> servers = SERVERS.get(id);
			for (BotServer s : servers)
				if (SERVER_TYPES.get(client).isInstance(s))
					return (S) s;
		}
		try {
			Constructor<?> constr = SERVER_TYPES.get(client).getDeclaredConstructor(Snowflake.class);
			boolean accessible = constr.isAccessible();
			constr.setAccessible(true);
			S ret = (S) constr.newInstance(id);
			constr.setAccessible(accessible);
			List<BotServer> servers = SERVERS.get(id);
			if (servers == null) {
				servers = new ArrayList<>();
				SERVERS.put(id, servers);
			}
			servers.add(ret);
			return ret;
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static BotServer getServer(DiscordClient client, Guild guild) {
		if (guild == null)
			return null;
		return getServer(client, guild.getId());
	}

	// start data stuffs
	protected abstract File getOptionsFile();

	private EBStorage getDataInternal() {
		return ServerDataHelper.getEBSFromFolder(getDiscordGuild(), getOptionsFile())
				.registerType(new DataTypeServerSettings());
	}

	public EBStorage getData() {
		return getDataInternal();
	}

	public boolean hasDataKey(String key) {
		return getDataInternal().containsKey(key);
	}

	public boolean hasDataKey(String key, Class<?> type) {
		EBStorage data = getDataInternal();
		return data.containsKey(key) && type != null && type.isInstance(data.get(key));
	}

	public Object getDataValue(String key) {
		EBStorage ebs = getDataInternal();
		Object obj = ebs.get(key);
		if (obj instanceof EBStorage)
			((EBStorage) obj).setOnWriteCallback(new OnWriteCallback() {

				@Override
				public void onWriteEBS(EBStorage ebsInner) {
					ebs.flush();
				}
			});
		if (obj instanceof EBList)
			((EBList<?>) obj).setOnWriteCallback(new OnWriteCallback() {

				@Override
				public void onWriteEBL(EBList<?> eblInner) {
					ebs.flush();
				}
			});
		if (obj instanceof SettingSet)
			((SettingSet) obj).setWriteCallback(() -> ebs.flush());
		return obj;
	}

	public <V> V getDataValue(String key, Class<V> type) {
		return getDataInternal().get(key, type);
	}

	public void setDataValue(String key, Object val) {
		getDataInternal().set(key, val);
	}

	public void clearDataValue(String key) {
		getDataInternal().clearKey(key);
	}

	public void clearDataOptions() {
		EBStorage data = getDataInternal();
		for (String key : data.getKeys())
			data.clearKey(key);
		File optionsFile = new File(new File(Starota.DATA_FOLDER, "options"),
				getDiscordGuild().getId().asString() + IOHelper.EBS_EXTENSION);
		if (optionsFile.exists())
			optionsFile.delete();
	}
	// end data stuffs

	// start settings stuffs
	public static final String SETTINGS_KEY = "setting_set";

	protected abstract SettingSet getDefaultSettings(BotServer server);

	private SettingSet getSettings() {
		if (!this.hasDataKey(SETTINGS_KEY, SettingSet.class))
			this.setDataValue(SETTINGS_KEY, getDefaultSettings(this));
		return ((SettingSet) this.getDataValue(SETTINGS_KEY)).setServer(this.getDiscordGuild().getId());
	}

	public void forEachSetting(Consumer<Setting<?>> consumer) {
		for (Setting<?> s : getSettings())
			consumer.accept(s);
	}

	public <V> V getSetting(String name) {
		return getSettings().getSetting(name);
	}

	public String getSettingString(String name) {
		for (Setting<?> s : getSettings())
			if (s.getName().equalsIgnoreCase(name))
				if (s instanceof ServerSetting)
					return ((ServerSetting<?>) s).getValueString(this);
				else
					return s.getValueString();
		return "null";
	}

	public EnumReturnSetStatus setSetting(String name, String value) {
		return getSettings().setSetting(this.getDiscordGuildId(), name, value);
	}

	public <T> EnumReturnSetStatus setSetting(String name, T value) {
		return getSettings().setSetting(this.getDiscordGuildId(), name, value);
	}

	public TimeZone getTimezone() {
		WeatherLocation location = getLocation();
		if (location != null) {
			WeatherTimezone wtz = location.getTimeZone();
			// int hours = (int) Math.floor(wtz.getGmtOffset());
			// String offset = String.format("GMT%s%d:%02d", wtz.getGmtOffset()
			// > 0 ? "+" : "-", hours,
			// (int) ((wtz.getGmtOffset() - hours) * 60));
			// TimeZone ret = TimeZone.getTimeZone(offset);
			TimeZone ret = TimeZone.getTimeZone(wtz.getName());
			if (!ret.getID().equals("GMT") || location.getTimeZone().getCode().equals("GMT"))
				return ret;
		}
		SettingSet settings = getSettings();
		if (settings.isEmpty(StarotaConstants.Settings.TIMEZONE))
			return MiscUtils.getTimezone(getDiscordGuild().getRegion().block());
		return null;
	}

	private static final SimpleDateFormat DATE_FORMAT_12H = new SimpleDateFormat("ha");
	private static final SimpleDateFormat DATE_FORMAT_24H = new SimpleDateFormat("k");

	public SimpleDateFormat getHourFormat() {
		SimpleDateFormat ret;
		if ((boolean) getSetting(StarotaConstants.Settings.CLOCK_24H))
			ret = ((SimpleDateFormat) DATE_FORMAT_24H.clone());
		else
			ret = ((SimpleDateFormat) DATE_FORMAT_12H.clone());
		ret.setTimeZone(getTimezone());
		return ret;
	}
	// send settings stuffs

	// start weather stuffs
	private CachedData<WeatherLocation> location;

	public WeatherLocation getLocation() {
		// 12 hrs
		if (location != null && !location.hasPassed(43200000) && location.getValue() != null)
			return location.getValue();
		String coords = getSetting("coords");
		location = new CachedData<>(AccuWeatherAPI.getLocation(this, coords));
		return location.getValue();
	}
	// end weather stuffs

	// start info channels
	private static final String INFO_CHANNEL_KEY = "info_channels";

	public boolean hasInfoChannel(String key) {
		EBStorage ebs = getDataInternal();
		EBStorage infoChannels = ebs.get(INFO_CHANNEL_KEY, EBStorage.class);
		if (infoChannels == null)
			return false;
		return infoChannels.containsKey(key)
				&& guild.getChannelById(Snowflake.of((long) infoChannels.get(key))).block() != null;
	}

	public InfoChannel getOrCreateInfoChannel(String key) {
		EBStorage ebs = getDataInternal();
		EBStorage infoChannels = ebs.get(INFO_CHANNEL_KEY, EBStorage.class);
		if (infoChannels == null) {
			ebs.set(INFO_CHANNEL_KEY, new EBStorage());
			infoChannels = ebs.get(INFO_CHANNEL_KEY, EBStorage.class);
		}
		if (infoChannels.containsKey(key)) {
			GuildChannel ch = guild.getChannelById(Snowflake.of(infoChannels.get(key, Long.class)))
					.block();
			if (ch instanceof VoiceChannel)
				return new InfoChannel((VoiceChannel) ch);
		}
		return createInfoChannel(key);
	}

	private InfoChannel createInfoChannel(String key) {
		EBStorage ebs = getDataInternal();
		EBStorage infoChannels = ebs.get(INFO_CHANNEL_KEY, EBStorage.class);
		if (infoChannels == null) {
			ebs.set(INFO_CHANNEL_KEY, new EBStorage().registerPrimitives());
			infoChannels = ebs.get(INFO_CHANNEL_KEY, EBStorage.class);
		}
		VoiceChannel ch = guild.createVoiceChannel(
				(chSpec) -> chSpec.setName(key).setPermissionOverwrites(getInfoChannelOverwrite()))
				.block();
		long id = ch.getId().asLong();
		if (!infoChannels.acceptsValue(id))
			infoChannels.registerPrimitives();
		infoChannels.set(key, ch.getId().asLong());
		return new InfoChannel(ch);
	}

	private Set<PermissionOverwrite> getInfoChannelOverwrite() {
		Set<PermissionOverwrite> overwrites = new HashSet<>();
		overwrites.add(PermissionOverwrite.forMember(client.getSelfId().get(), PermissionSet.all(),
				PermissionSet.none()));
		overwrites.add(PermissionOverwrite.forRole(guild.getId(), PermissionSet.none(),
				PermissionSet.of(Permission.CONNECT)));
		return overwrites;
	}
	// end info channels

	// start misc stuffs
	public void clearPermissions() {
		// TODO: for proper permission system
		// PermissionHolder.dumpPerms(getDiscordGuild());
	}
	// end misc stuffs

	public Guild getDiscordGuild() {
		return guild;
	}

	public Snowflake getDiscordGuildId() {
		return guild.getId();
	}

	public DiscordClient getClient() {
		return client;
	}

}
