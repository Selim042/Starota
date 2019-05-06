package us.myles_selim.starota.reaction_messages;

import java.util.HashMap;
import java.util.Map;

import discord4j.core.DiscordClient;
import discord4j.core.event.domain.message.MessageDeleteEvent;
import discord4j.core.event.domain.message.MessageUpdateEvent;
import discord4j.core.event.domain.message.ReactionAddEvent;
import discord4j.core.event.domain.message.ReactionRemoveEvent;
import discord4j.core.object.entity.Channel;
import discord4j.core.object.entity.GuildChannel;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.TextChannel;
import discord4j.core.object.entity.User;
import us.myles_selim.ebs.EBStorage;
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.misc.utils.MiscUtils;
import us.myles_selim.starota.reaction_messages.PersistReactionMessage.DataTypePReactionMessage;
import us.myles_selim.starota.wrappers.StarotaServer;

public class ReactionMessageRegistry {

	public static final String PERSIST_KEY = "persistReactions";

	private static final Map<Long, ReactionMessageRegistry> REGISTRIES = new HashMap<>();

	protected final HashMap<String, ReactionMessage> messages = new HashMap<>();
	protected final User bot;

	public static ReactionMessageRegistry getRegistry(DiscordClient client) {
		return getRegistry(client.getSelf().block().getId().asLong());
	}

	public static ReactionMessageRegistry getRegistry(long botId) {
		if (!REGISTRIES.containsKey(botId))
			return null;
		return REGISTRIES.get(botId);
	}

	public ReactionMessageRegistry(DiscordClient client) {
		this.bot = client.getSelf().block();
		REGISTRIES.put(this.bot.getId().asLong(), this);
		MiscUtils.forEach(Starota.getClient().getGuilds(),
				(g) -> deserializeAll(StarotaServer.getServer(g)));
	}

	public void onReactAdd(ReactionAddEvent event) {
		if (event.getUser().block().isBot())
			return;
		Message msg = event.getMessage().block();
		if (!messages.containsKey(msg.getId().asString()))
			return;
		// Starota.EXECUTOR.execute(new Runnable() {
		//
		// @Override
		// public void run() {
		ReactionMessage rMsg = messages.get(msg.getId().asString());
		rMsg.onReactionAdded(StarotaServer.getServer(event.getGuild().block()),
				(TextChannel) event.getChannel().block(), msg,
				event.getUser().block().asMember(event.getGuildId().get()).block(), event.getEmoji());
		// }
		// });
	}

	public void onReactRemove(ReactionRemoveEvent event) {
		if (event.getUser().block().isBot())
			return;
		Message msg = event.getMessage().block();
		if (!messages.containsKey(msg.getId().asString()))
			return;
		// Starota.EXECUTOR.execute(new Runnable() {
		//
		// @Override
		// public void run() {
		ReactionMessage rMsg = messages.get(msg.getId().asString());
		rMsg.onReactionRemoved(StarotaServer.getServer(event.getGuild().block()),
				(TextChannel) event.getChannel().block(), msg,
				event.getUser().block().asMember(event.getGuildId().get()).block(), event.getEmoji());
		// }
		// });
	}

	public void onDelete(MessageDeleteEvent event) {
		String stringId = event.getMessageId().toString();
		if (!messages.containsKey(stringId))
			return;
		ReactionMessage rMsg = messages.remove(stringId);
		if (rMsg instanceof PersistReactionMessage
				&& event.getChannel().block().getType().equals(Channel.Type.GUILD_TEXT)) {
			StarotaServer sserver = StarotaServer
					.getServer(((GuildChannel) event.getChannel().block()).getGuild().block());
			EBStorage storage = sserver.getData();
			if (!storage.containsKey(PERSIST_KEY))
				return;
			EBStorage persist = storage.get(PERSIST_KEY, EBStorage.class);
			if (persist.containsKey(stringId)) {
				persist.clearKey(stringId);
				storage.set(PERSIST_KEY, persist);
			}
		}
	}

	public void onEdit(MessageUpdateEvent event) {
		if (!messages.containsKey(event.getMessage().block().getId().asString()))
			return;
		ReactionMessage rMessage = messages.get(event.getMessage().block().getId().asString());
		if (!(rMessage instanceof PersistReactionMessage))
			return;
		serialize(StarotaServer.getServer(event.getGuild().block()), event.getMessage().block(),
				(PersistReactionMessage) rMessage);
	}

	public User getBot() {
		return bot;
	}

	protected void serialize(StarotaServer server, Message message, PersistReactionMessage rMessage) {
		EBStorage storage = server.getData();
		if (!storage.containsKey(PERSIST_KEY))
			storage.set(PERSIST_KEY, new EBStorage().registerType(new DataTypePReactionMessage()));
		EBStorage persist = storage.get(PERSIST_KEY, EBStorage.class);
		persist.set(message.getId().asString(), rMessage);
		storage.set(PERSIST_KEY, persist);
	}

	private void deserializeAll(StarotaServer server) {
		EBStorage storage = server.getData();
		if (!storage.containsKey(PERSIST_KEY))
			return;
		EBStorage persist = storage.get(PERSIST_KEY, EBStorage.class);
		for (String key : persist.getKeys()) {
			ReactionMessage rMsg = persist.get(key, PersistReactionMessage.class);
			messages.put(key, rMsg);
		}
	}

	public ReactionMessage getMessage(long msg) {
		return messages.get(Long.toString(msg));
	}

}
