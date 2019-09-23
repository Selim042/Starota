package us.myles_selim.starota.reaction_messages;

import java.util.HashMap;
import java.util.Map;

import discord4j.core.DiscordClient;
import discord4j.core.event.domain.message.MessageDeleteEvent;
import discord4j.core.event.domain.message.MessageUpdateEvent;
import discord4j.core.event.domain.message.ReactionAddEvent;
import discord4j.core.event.domain.message.ReactionRemoveAllEvent;
import discord4j.core.event.domain.message.ReactionRemoveEvent;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.MessageChannel;
import discord4j.core.object.entity.User;
import us.myles_selim.ebs.EBStorage;
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.misc.utils.EventListener;
import us.myles_selim.starota.reaction_messages.PersistReactionMessage.DataTypePReactionMessage;
import us.myles_selim.starota.wrappers.StarotaServer;

public class ReactionMessageRegistry implements EventListener {

	public static final String PERSIST_KEY = "persistReactions";

	private static final Map<Long, ReactionMessageRegistry> REGISTRIES = new HashMap<>();

	protected final HashMap<String, ReactionMessage> messages = new HashMap<>();
	protected final User bot;

	public static ReactionMessageRegistry getRegistry(DiscordClient client) {
		return getRegistry(client.getSelfId().get().asLong());
	}

	public static ReactionMessageRegistry getRegistry(long botId) {
		if (!REGISTRIES.containsKey(botId))
			return null;
		return REGISTRIES.get(botId);
	}

	public ReactionMessageRegistry(DiscordClient client) {
		this.bot = client.getSelf().block();
		REGISTRIES.put(this.bot.getId().asLong(), this);
		for (Guild g : Starota.getClient().getGuilds().collectList().block())
			deserializeAll(StarotaServer.getServer(g));
	}

	@EventSubscriber
	public void onReactAdd(ReactionAddEvent event) {
		if (event.getUser().block().isBot())
			return;
		if (!messages.containsKey(event.getMessageId().asString()))
			return;
		// restart restart timer
		Starota.restartRestartTimer();
		Message msg = event.getMessage().block();
		// Starota.EXECUTOR.execute(new Runnable() {
		//
		// @Override
		// public void run() {
		boolean inGuild = event.getGuildId().isPresent();
		ReactionMessage rMsg = messages.get(msg.getId().asString());
		rMsg.onReactionAdded(inGuild ? StarotaServer.getServer(event.getGuild().block()) : null,
				(MessageChannel) event.getChannel().block(), msg, event.getUser().block(),
				event.getEmoji());
		// }
		// });
	}

	@EventSubscriber
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
		boolean inGuild = event.getGuildId().isPresent();
		ReactionMessage rMsg = messages.get(msg.getId().asString());
		rMsg.onReactionRemoved(inGuild ? StarotaServer.getServer(event.getGuild().block()) : null,
				(MessageChannel) event.getChannel().block(), msg, event.getUser().block(),
				event.getEmoji());
		rMsg.removeButton(event.getEmoji());
		// }
		// });
	}

	@EventSubscriber
	public void onReactRemove(ReactionRemoveAllEvent event) {
		Message msg = event.getMessage().block();
		if (!messages.containsKey(msg.getId().asString()))
			return;
		ReactionMessage rMsg = messages.get(msg.getId().asString());
		rMsg.clearButtons();
	}

	@EventSubscriber
	public void onDelete(MessageDeleteEvent event) {
		String stringId = event.getMessageId().asString();
		if (!messages.containsKey(stringId))
			return;
		ReactionMessage rMsg = messages.remove(stringId);
		if (rMsg instanceof PersistReactionMessage) {
			StarotaServer sserver = StarotaServer.getServer(event.getMessage().get().getGuild().block());
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

	@EventSubscriber
	public void onEdit(MessageUpdateEvent event) {
		if (!messages.containsKey(event.getMessageId().asString()))
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
