package us.myles_selim.starota.misc.data_types;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.function.Consumer;

import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.IShard;
import sx.blah.discord.handle.audio.IAudioManager;
import sx.blah.discord.handle.audit.ActionType;
import sx.blah.discord.handle.audit.AuditLog;
import sx.blah.discord.handle.obj.ICategory;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IEmoji;
import sx.blah.discord.handle.obj.IExtendedInvite;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IRegion;
import sx.blah.discord.handle.obj.IRole;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.handle.obj.IVoiceChannel;
import sx.blah.discord.handle.obj.IWebhook;
import sx.blah.discord.handle.obj.VerificationLevel;
import sx.blah.discord.util.Ban;
import sx.blah.discord.util.Image;
import us.myles_selim.ebs.EBStorage;
import us.myles_selim.ebs.IOHelper;
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.commands.settings.Setting;
import us.myles_selim.starota.commands.settings.SettingSet;
import us.myles_selim.starota.commands.settings.SettingSet.EnumReturnSetStatus;
import us.myles_selim.starota.misc.utils.MiscUtils;
import us.myles_selim.starota.misc.utils.ServerDataHelper;
import us.myles_selim.starota.misc.utils.StarotaConstants;
import us.myles_selim.starota.wrappers.StarotaServer.DataTypeServerSettings;

public abstract class BotServer implements IGuild {

	private static final Map<IDiscordClient, Class<?>> SERVER_TYPES = new HashMap<>();

	private final IDiscordClient client;
	private final IGuild guild;

	public BotServer(IDiscordClient client, IGuild guild) {
		this.client = client;
		this.guild = guild;
		if (!client.equals(guild.getClient()))
			throw new IllegalArgumentException("guild client does not match supplied client");
	}

	public static <S extends BotServer> void registerServerType(IDiscordClient client,
			Class<S> serverClass) {
		try {
			serverClass.getDeclaredConstructor(IGuild.class);
		} catch (NoSuchMethodException | SecurityException e) {
			throw new IllegalArgumentException(String.format(
					"serverClass (%s) must include a constructor including only a IGuild param",
					serverClass.getName()));
		}
		SERVER_TYPES.put(client, serverClass);
	}

	@SuppressWarnings("unchecked")
	public static <S extends BotServer> S getServer(IDiscordClient client, IGuild guild) {
		if (!SERVER_TYPES.containsKey(client))
			return null;
		try {
			Constructor<?> constr = SERVER_TYPES.get(client).getDeclaredConstructor(IGuild.class);
			boolean accessible = constr.isAccessible();
			constr.setAccessible(true);
			S ret = (S) constr.newInstance(guild);
			constr.setAccessible(accessible);
			return ret;
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
			return null;
		}
	}

	// start data stuffs
	protected abstract File getOptionsFile();

	private EBStorage getDataInternal() {
		return ServerDataHelper.getEBSFromFolder(this, getOptionsFile())
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
				getLongID() + IOHelper.EBS_EXTENSION);
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
		return ((SettingSet) this.getDataValue(SETTINGS_KEY)).setServer(this);
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

	public TimeZone getTimezone() {
		SettingSet settings = getSettings();
		if (settings.isEmpty(StarotaConstants.Settings.TIMEZONE))
			return MiscUtils.getTimezone(getRegion());
		return null;
	}
	// send settings stuffs

	/**
	 * @deprecated Use the overridden methods rather than getting the guild
	 *             itself
	 */
	@Deprecated
	public IGuild getDiscordGuild() {
		return guild;
	}

	@Override
	public IDiscordClient getClient() {
		return guild.getClient();
	}

	@Override
	public IShard getShard() {
		return guild.getShard();
	}

	@Override
	public abstract IGuild copy();

	@Override
	public long getLongID() {
		return guild.getLongID();
	}

	@Override
	public long getOwnerLongID() {
		return guild.getOwnerLongID();
	}

	@Override
	public IUser getOwner() {
		return guild.getOwner();
	}

	@Override
	public String getIcon() {
		return guild.getIcon();
	}

	@Override
	public String getIconURL() {
		return guild.getIconURL();
	}

	@Override
	public List<IChannel> getChannels() {
		return guild.getChannels();
	}

	@Override
	public IChannel getChannelByID(long id) {
		return guild.getChannelByID(id);
	}

	@Override
	public List<IUser> getUsers() {
		return guild.getUsers();
	}

	@Override
	public IUser getUserByID(long id) {
		return guild.getUserByID(id);
	}

	@Override
	public List<IChannel> getChannelsByName(String name) {
		return guild.getChannelsByName(name);
	}

	@Override
	public List<IVoiceChannel> getVoiceChannelsByName(String name) {
		return guild.getVoiceChannelsByName(name);
	}

	@Override
	public List<IUser> getUsersByName(String name) {
		return guild.getUsersByName(name);
	}

	@Override
	public List<IUser> getUsersByName(String name, boolean includeNicknames) {
		return guild.getUsersByName(name, includeNicknames);
	}

	@Override
	public List<IUser> getUsersByRole(IRole role) {
		return guild.getUsersByRole(role);
	}

	@Override
	public String getName() {
		return guild.getName();
	}

	@Override
	public List<IRole> getRoles() {
		return guild.getRoles();
	}

	@Override
	public List<IRole> getRolesForUser(IUser user) {
		return guild.getRolesForUser(user);
	}

	@Override
	public IRole getRoleByID(long id) {
		return guild.getRoleByID(id);
	}

	@Override
	public List<IRole> getRolesByName(String name) {
		return guild.getRolesByName(name);
	}

	@Override
	public List<IVoiceChannel> getVoiceChannels() {
		return guild.getVoiceChannels();
	}

	@Override
	public IVoiceChannel getVoiceChannelByID(long id) {
		return guild.getVoiceChannelByID(id);
	}

	@Override
	public IVoiceChannel getConnectedVoiceChannel() {
		return guild.getConnectedVoiceChannel();
	}

	@Override
	public IVoiceChannel getAFKChannel() {
		return guild.getAFKChannel();
	}

	@Override
	public int getAFKTimeout() {
		return guild.getAFKTimeout();
	}

	@Override
	public IRole createRole() {
		return guild.createRole();
	}

	@Override
	public List<IUser> getBannedUsers() {
		return guild.getBannedUsers();
	}

	@Override
	public List<Ban> getBans() {
		return guild.getBans();
	}

	@Override
	public void banUser(IUser user) {
		guild.banUser(user);
	}

	@Override
	public void banUser(IUser user, int deleteMessagesForDays) {
		guild.banUser(user, deleteMessagesForDays);
	}

	@Override
	public void banUser(IUser user, String reason) {
		guild.banUser(user, reason);
	}

	@Override
	public void banUser(IUser user, String reason, int deleteMessagesForDays) {
		guild.banUser(user, reason, deleteMessagesForDays);
	}

	@Override
	public void banUser(long userID) {
		guild.banUser(userID);
	}

	@Override
	public void banUser(long userID, int deleteMessagesForDays) {
		guild.banUser(userID, deleteMessagesForDays);
	}

	@Override
	public void banUser(long userID, String reason) {
		guild.banUser(userID, reason);
	}

	@Override
	public void banUser(long userID, String reason, int deleteMessagesForDays) {
		guild.banUser(userID, reason, deleteMessagesForDays);
	}

	@Override
	public void pardonUser(long userID) {
		guild.pardonUser(userID);
	}

	@Override
	public void kickUser(IUser user) {
		guild.kickUser(user);
	}

	@Override
	public void kickUser(IUser user, String reason) {
		guild.kickUser(user, reason);
	}

	@Override
	public void editUserRoles(IUser user, IRole[] roles) {
		guild.editUserRoles(user, roles);
	}

	@Override
	public void setDeafenUser(IUser user, boolean deafen) {
		guild.setDeafenUser(user, deafen);
	}

	@Override
	public void setMuteUser(IUser user, boolean mute) {
		guild.setMuteUser(user, mute);
	}

	@Override
	public void setUserNickname(IUser user, String nick) {
		guild.setUserNickname(user, nick);
	}

	@Override
	public void edit(String name, IRegion region, VerificationLevel level, Image icon,
			IVoiceChannel afkChannel, int afkTimeout) {
		guild.edit(name, region, level, icon, afkChannel, afkTimeout);
	}

	@Override
	public void changeName(String name) {
		guild.changeName(name);
	}

	@Override
	public void changeRegion(IRegion region) {
		guild.changeRegion(region);
	}

	@Override
	public void changeVerificationLevel(VerificationLevel verification) {
		guild.changeVerificationLevel(verification);
	}

	@Override
	public void changeIcon(Image icon) {
		guild.changeIcon(icon);
	}

	@Override
	public void changeAFKChannel(IVoiceChannel channel) {
		guild.changeAFKChannel(channel);
	}

	@Override
	public void changeAFKTimeout(int timeout) {
		guild.changeAFKTimeout(timeout);
	}

	@Override
	public void leave() {
		guild.leave();
	}

	@Override
	public IChannel createChannel(String name) {
		return guild.createChannel(name);
	}

	@Override
	public IVoiceChannel createVoiceChannel(String name) {
		return guild.createVoiceChannel(name);
	}

	@Override
	public IRegion getRegion() {
		return guild.getRegion();
	}

	@Override
	public VerificationLevel getVerificationLevel() {
		return guild.getVerificationLevel();
	}

	@Override
	public IRole getEveryoneRole() {
		return guild.getEveryoneRole();
	}

	@Override
	public IChannel getDefaultChannel() {
		return guild.getDefaultChannel();
	}

	@Override
	public List<IExtendedInvite> getExtendedInvites() {
		return guild.getExtendedInvites();
	}

	@Override
	public void reorderRoles(IRole... rolesInOrder) {
		guild.reorderRoles(rolesInOrder);
	}

	@Override
	public int getUsersToBePruned(int days) {
		return guild.getUsersToBePruned(days);
	}

	@Override
	public int pruneUsers(int days) {
		return guild.pruneUsers(days);
	}

	@Override
	public boolean isDeleted() {
		return guild.isDeleted();
	}

	@Override
	public IAudioManager getAudioManager() {
		return guild.getAudioManager();
	}

	@Override
	public Instant getJoinTimeForUser(IUser user) {
		return guild.getJoinTimeForUser(user);
	}

	@Override
	public IMessage getMessageByID(long id) {
		return guild.getMessageByID(id);
	}

	@Override
	public List<IEmoji> getEmojis() {
		return guild.getEmojis();
	}

	@Override
	public IEmoji getEmojiByID(long id) {
		return guild.getEmojiByID(id);
	}

	@Override
	public IEmoji getEmojiByName(String name) {
		return guild.getEmojiByName(name);
	}

	@Override
	public IEmoji createEmoji(String name, Image image, IRole[] roles) {
		return guild.createEmoji(name, image, roles);
	}

	@Override
	public IWebhook getWebhookByID(long id) {
		return guild.getWebhookByID(id);
	}

	@Override
	public List<IWebhook> getWebhooksByName(String name) {
		return guild.getWebhooksByName(name);
	}

	@Override
	public List<IWebhook> getWebhooks() {
		return guild.getWebhooks();
	}

	@Override
	public int getTotalMemberCount() {
		return guild.getTotalMemberCount();
	}

	@Override
	public AuditLog getAuditLog() {
		return guild.getAuditLog();
	}

	@Override
	public AuditLog getAuditLog(ActionType actionType) {
		return guild.getAuditLog(actionType);
	}

	@Override
	public AuditLog getAuditLog(IUser user) {
		return guild.getAuditLog(user);
	}

	@Override
	public AuditLog getAuditLog(IUser user, ActionType actionType) {
		return guild.getAuditLog(user, actionType);
	}

	@Override
	public ICategory createCategory(String name) {
		return guild.createCategory(name);
	}

	@Override
	public List<ICategory> getCategories() {
		return guild.getCategories();
	}

	@Override
	public ICategory getCategoryByID(long id) {
		return guild.getCategoryByID(id);
	}

	@Override
	public List<ICategory> getCategoriesByName(String name) {
		return guild.getCategoriesByName(name);
	}

	@Override
	public IChannel getSystemChannel() {
		return guild.getSystemChannel();
	}

}
