package us.myles_selim.starota.misc.data_types;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.time.Instant;
import java.util.EnumSet;
import java.util.List;

import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.IShard;
import sx.blah.discord.api.internal.json.objects.EmbedObject;
import sx.blah.discord.handle.obj.ICategory;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IExtendedInvite;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IInvite;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IRole;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.handle.obj.IWebhook;
import sx.blah.discord.handle.obj.PermissionOverride;
import sx.blah.discord.handle.obj.Permissions;
import sx.blah.discord.util.AttachmentPartEntry;
import sx.blah.discord.util.Image;
import sx.blah.discord.util.MessageBuilder;
import sx.blah.discord.util.MessageHistory;
import sx.blah.discord.util.cache.LongMap;

public class NullChannel {

	public static final IChannel NULL_CHANNEL = new IChannel() {

		@Override
		public String toString() {
			return "null";
		}

		@Override
		public IDiscordClient getClient() {
			throw new RuntimeException("unsupported, \"null\" channel");
		}

		@Override
		public IShard getShard() {
			throw new RuntimeException("unsupported, \"null\" channel");
		}

		@Override
		public IChannel copy() {
			return NULL_CHANNEL;
		}

		@Override
		public long getLongID() {
			return -1;
		}

		@Override
		public String getName() {
			throw new RuntimeException("unsupported, \"null\" channel");
		}

		@Override
		public MessageHistory getMessageHistory() {
			throw new RuntimeException("unsupported, \"null\" channel");
		}

		@Override
		public MessageHistory getMessageHistory(int messageCount) {
			throw new RuntimeException("unsupported, \"null\" channel");
		}

		@Override
		public MessageHistory getMessageHistoryFrom(Instant startDate) {
			throw new RuntimeException("unsupported, \"null\" channel");
		}

		@Override
		public MessageHistory getMessageHistoryFrom(Instant startDate, int maxMessageCount) {
			throw new RuntimeException("unsupported, \"null\" channel");
		}

		@Override
		public MessageHistory getMessageHistoryFrom(long id) {
			throw new RuntimeException("unsupported, \"null\" channel");
		}

		@Override
		public MessageHistory getMessageHistoryFrom(long id, int maxMessageCount) {
			throw new RuntimeException("unsupported, \"null\" channel");
		}

		@Override
		public MessageHistory getMessageHistoryTo(Instant endDate) {
			throw new RuntimeException("unsupported, \"null\" channel");
		}

		@Override
		public MessageHistory getMessageHistoryTo(Instant endDate, int maxMessageCount) {
			throw new RuntimeException("unsupported, \"null\" channel");
		}

		@Override
		public MessageHistory getMessageHistoryTo(long id) {
			throw new RuntimeException("unsupported, \"null\" channel");
		}

		@Override
		public MessageHistory getMessageHistoryTo(long id, int maxMessageCount) {
			throw new RuntimeException("unsupported, \"null\" channel");
		}

		@Override
		public MessageHistory getMessageHistoryIn(Instant startDate, Instant endDate) {
			throw new RuntimeException("unsupported, \"null\" channel");
		}

		@Override
		public MessageHistory getMessageHistoryIn(Instant startDate, Instant endDate,
				int maxMessageCount) {
			throw new RuntimeException("unsupported, \"null\" channel");
		}

		@Override
		public MessageHistory getMessageHistoryIn(long beginID, long endID) {
			throw new RuntimeException("unsupported, \"null\" channel");
		}

		@Override
		public MessageHistory getMessageHistoryIn(long beginID, long endID, int maxMessageCount) {
			throw new RuntimeException("unsupported, \"null\" channel");
		}

		@Override
		public MessageHistory getFullMessageHistory() {
			throw new RuntimeException("unsupported, \"null\" channel");
		}

		@Override
		public List<IMessage> bulkDelete() {
			throw new RuntimeException("unsupported, \"null\" channel");
		}

		@Override
		public List<IMessage> bulkDelete(List<IMessage> messages) {
			throw new RuntimeException("unsupported, \"null\" channel");
		}

		@Override
		public int getMaxInternalCacheCount() {
			throw new RuntimeException("unsupported, \"null\" channel");
		}

		@Override
		public int getInternalCacheCount() {
			throw new RuntimeException("unsupported, \"null\" channel");
		}

		@Override
		public IMessage getMessageByID(long messageID) {
			throw new RuntimeException("unsupported, \"null\" channel");
		}

		@Override
		public IMessage fetchMessage(long messageID) {
			throw new RuntimeException("unsupported, \"null\" channel");
		}

		@Override
		public IGuild getGuild() {
			throw new RuntimeException("unsupported, \"null\" channel");
		}

		@Override
		public boolean isPrivate() {
			throw new RuntimeException("unsupported, \"null\" channel");
		}

		@Override
		public boolean isNSFW() {
			throw new RuntimeException("unsupported, \"null\" channel");
		}

		@Override
		public String getTopic() {
			throw new RuntimeException("unsupported, \"null\" channel");
		}

		@Override
		public String mention() {
			return "null";
		}

		@Override
		public IMessage sendMessage(String content) {
			throw new RuntimeException("unsupported, \"null\" channel");
		}

		@Override
		public IMessage sendMessage(EmbedObject embed) {
			throw new RuntimeException("unsupported, \"null\" channel");
		}

		@Override
		public IMessage sendMessage(String content, boolean tts) {
			throw new RuntimeException("unsupported, \"null\" channel");
		}

		@Override
		public IMessage sendMessage(String content, EmbedObject embed) {
			throw new RuntimeException("unsupported, \"null\" channel");
		}

		@Override
		public IMessage sendMessage(String content, EmbedObject embed, boolean tts) {
			throw new RuntimeException("unsupported, \"null\" channel");
		}

		@Override
		public IMessage sendFile(File file) throws FileNotFoundException {
			throw new RuntimeException("unsupported, \"null\" channel");
		}

		@Override
		public IMessage sendFiles(File... files) throws FileNotFoundException {
			throw new RuntimeException("unsupported, \"null\" channel");
		}

		@Override
		public IMessage sendFile(String content, File file) throws FileNotFoundException {
			throw new RuntimeException("unsupported, \"null\" channel");
		}

		@Override
		public IMessage sendFiles(String content, File... files) throws FileNotFoundException {
			throw new RuntimeException("unsupported, \"null\" channel");
		}

		@Override
		public IMessage sendFile(EmbedObject embed, File file) throws FileNotFoundException {
			throw new RuntimeException("unsupported, \"null\" channel");
		}

		@Override
		public IMessage sendFiles(EmbedObject embed, File... files) throws FileNotFoundException {
			throw new RuntimeException("unsupported, \"null\" channel");
		}

		@Override
		public IMessage sendFile(String content, InputStream file, String fileName) {
			throw new RuntimeException("unsupported, \"null\" channel");
		}

		@Override
		public IMessage sendFiles(String content, AttachmentPartEntry... entries) {
			throw new RuntimeException("unsupported, \"null\" channel");
		}

		@Override
		public IMessage sendFile(EmbedObject embed, InputStream file, String fileName) {
			throw new RuntimeException("unsupported, \"null\" channel");
		}

		@Override
		public IMessage sendFiles(EmbedObject embed, AttachmentPartEntry... entries) {
			throw new RuntimeException("unsupported, \"null\" channel");
		}

		@Override
		public IMessage sendFile(String content, boolean tts, InputStream file, String fileName) {
			throw new RuntimeException("unsupported, \"null\" channel");
		}

		@Override
		public IMessage sendFiles(String content, boolean tts, AttachmentPartEntry... entries) {
			throw new RuntimeException("unsupported, \"null\" channel");
		}

		@Override
		public IMessage sendFile(String content, boolean tts, InputStream file, String fileName,
				EmbedObject embed) {
			throw new RuntimeException("unsupported, \"null\" channel");
		}

		@Override
		public IMessage sendFiles(String content, boolean tts, EmbedObject embed,
				AttachmentPartEntry... entries) {
			throw new RuntimeException("unsupported, \"null\" channel");
		}

		@Override
		public IMessage sendFile(MessageBuilder builder, InputStream file, String fileName) {
			throw new RuntimeException("unsupported, \"null\" channel");
		}

		@Override
		public IInvite createInvite(int maxAge, int maxUses, boolean temporary, boolean unique) {
			throw new RuntimeException("unsupported, \"null\" channel");
		}

		@Override
		public void toggleTypingStatus() {
			throw new RuntimeException("unsupported, \"null\" channel");
		}

		@Override
		public void setTypingStatus(boolean typing) {
			throw new RuntimeException("unsupported, \"null\" channel");
		}

		@Override
		public boolean getTypingStatus() {
			throw new RuntimeException("unsupported, \"null\" channel");
		}

		@Override
		public void edit(String name, int position, String topic) {
			throw new RuntimeException("unsupported, \"null\" channel");
		}

		@Override
		public void changeName(String name) {
			throw new RuntimeException("unsupported, \"null\" channel");
		}

		@Override
		public void changePosition(int position) {
			throw new RuntimeException("unsupported, \"null\" channel");
		}

		@Override
		public void changeTopic(String topic) {
			throw new RuntimeException("unsupported, \"null\" channel");
		}

		@Override
		public void changeNSFW(boolean isNSFW) {
			throw new RuntimeException("unsupported, \"null\" channel");
		}

		@Override
		public int getPosition() {
			throw new RuntimeException("unsupported, \"null\" channel");
		}

		@Override
		public void delete() {
			throw new RuntimeException("unsupported, \"null\" channel");
		}

		@Override
		public LongMap<PermissionOverride> getUserOverrides() {
			throw new RuntimeException("unsupported, \"null\" channel");
		}

		@Override
		public LongMap<PermissionOverride> getRoleOverrides() {
			throw new RuntimeException("unsupported, \"null\" channel");
		}

		@Override
		public EnumSet<Permissions> getModifiedPermissions(IUser user) {
			throw new RuntimeException("unsupported, \"null\" channel");
		}

		@Override
		public EnumSet<Permissions> getModifiedPermissions(IRole role) {
			throw new RuntimeException("unsupported, \"null\" channel");
		}

		@Override
		public void removePermissionsOverride(IUser user) {
			throw new RuntimeException("unsupported, \"null\" channel");
		}

		@Override
		public void removePermissionsOverride(IRole role) {
			throw new RuntimeException("unsupported, \"null\" channel");
		}

		@Override
		public void overrideRolePermissions(IRole role, EnumSet<Permissions> toAdd,
				EnumSet<Permissions> toRemove) {
			throw new RuntimeException("unsupported, \"null\" channel");
		}

		@Override
		public void overrideUserPermissions(IUser user, EnumSet<Permissions> toAdd,
				EnumSet<Permissions> toRemove) {
			throw new RuntimeException("unsupported, \"null\" channel");
		}

		@Override
		public List<IExtendedInvite> getExtendedInvites() {
			throw new RuntimeException("unsupported, \"null\" channel");
		}

		@Override
		public List<IUser> getUsersHere() {
			throw new RuntimeException("unsupported, \"null\" channel");
		}

		@Override
		public List<IMessage> getPinnedMessages() {
			throw new RuntimeException("unsupported, \"null\" channel");
		}

		@Override
		public void pin(IMessage message) {
			throw new RuntimeException("unsupported, \"null\" channel");
		}

		@Override
		public void unpin(IMessage message) {
			throw new RuntimeException("unsupported, \"null\" channel");
		}

		@Override
		public List<IWebhook> getWebhooks() {
			throw new RuntimeException("unsupported, \"null\" channel");
		}

		@Override
		public IWebhook getWebhookByID(long id) {
			throw new RuntimeException("unsupported, \"null\" channel");
		}

		@Override
		public List<IWebhook> getWebhooksByName(String name) {
			throw new RuntimeException("unsupported, \"null\" channel");
		}

		@Override
		public IWebhook createWebhook(String name) {
			throw new RuntimeException("unsupported, \"null\" channel");
		}

		@Override
		public IWebhook createWebhook(String name, Image avatar) {
			throw new RuntimeException("unsupported, \"null\" channel");
		}

		@Override
		public IWebhook createWebhook(String name, String avatar) {
			throw new RuntimeException("unsupported, \"null\" channel");
		}

		@Override
		public boolean isDeleted() {
			throw new RuntimeException("unsupported, \"null\" channel");
		}

		@Override
		public void changeCategory(ICategory category) {
			throw new RuntimeException("unsupported, \"null\" channel");
		}

		@Override
		public ICategory getCategory() {
			throw new RuntimeException("unsupported, \"null\" channel");
		}

	};

}
