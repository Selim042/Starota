package us.myles_selim.starota.reaction_messages;

import java.util.HashMap;
import java.util.Map;

import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.IShard;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageDeleteEvent;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageEditEvent;
import sx.blah.discord.handle.impl.events.guild.channel.message.reaction.ReactionAddEvent;
import sx.blah.discord.handle.impl.events.guild.channel.message.reaction.ReactionRemoveEvent;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;
import us.myles_selim.ebs.EBStorage;
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.reaction_messages.PersistReactionMessage.DataTypePReactionMessage;
import us.myles_selim.starota.wrappers.StarotaServer;

public class ReactionMessageRegistry {

	public static final String PERSIST_KEY = "persistReactions";

	private static final Map<Long, ReactionMessageRegistry> REGISTRIES = new HashMap<>();

	protected final HashMap<String, ReactionMessage> messages = new HashMap<>();
	protected final IUser bot;

	public static ReactionMessageRegistry getRegistry(IShard shard) {
		return getRegistry(shard.getClient().getOurUser().getLongID());
	}

	public static ReactionMessageRegistry getRegistry(long botId) {
		if (!REGISTRIES.containsKey(botId))
			return null;
		return REGISTRIES.get(botId);
	}

	public ReactionMessageRegistry(IDiscordClient client) {
		this.bot = client.getOurUser();
		REGISTRIES.put(this.bot.getLongID(), this);
		for (IGuild g : Starota.getClient().getGuilds())
			deserializeAll(StarotaServer.getServer(g));
	}

	@EventSubscriber
	public void onReactAdd(ReactionAddEvent event) {
		if (event.getUser().isBot())
			return;
		IMessage msg = event.getMessage();
		if (!messages.containsKey(msg.getStringID()))
			return;
		// Starota.EXECUTOR.execute(new Runnable() {
		//
		// @Override
		// public void run() {
		ReactionMessage rMsg = messages.get(msg.getStringID());
		rMsg.onReactionAdded(StarotaServer.getServer(event.getGuild()), event.getChannel(), msg,
				event.getUser(), event.getReaction());
		// }
		// });
	}

	@EventSubscriber
	public void onReactRemove(ReactionRemoveEvent event) {
		if (event.getUser().isBot())
			return;
		IMessage msg = event.getMessage();
		if (!messages.containsKey(msg.getStringID()))
			return;
		// Starota.EXECUTOR.execute(new Runnable() {
		//
		// @Override
		// public void run() {
		ReactionMessage rMsg = messages.get(msg.getStringID());
		rMsg.onReactionRemoved(StarotaServer.getServer(event.getGuild()), event.getChannel(), msg,
				event.getUser(), event.getReaction());
		// }
		// });
	}

	@EventSubscriber
	public void onDelete(MessageDeleteEvent event) {
		String stringId = Long.toString(event.getMessageID());
		if (!messages.containsKey(stringId))
			return;
		ReactionMessage rMsg = messages.remove(stringId);
		if (rMsg instanceof PersistReactionMessage) {
			StarotaServer sserver = StarotaServer.getServer(event.getGuild());
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
	public void onEdit(MessageEditEvent event) {
		if (!messages.containsKey(event.getMessage().getStringID()))
			return;
		ReactionMessage rMessage = messages.get(event.getMessage().getStringID());
		if (!(rMessage instanceof PersistReactionMessage))
			return;
		serialize(StarotaServer.getServer(event.getGuild()), event.getMessage(),
				(PersistReactionMessage) rMessage);
	}

	public IUser getBot() {
		return bot;
	}

	protected void serialize(StarotaServer server, IMessage message, PersistReactionMessage rMessage) {
		EBStorage storage = server.getData();
		if (!storage.containsKey(PERSIST_KEY))
			storage.set(PERSIST_KEY, new EBStorage().registerType(new DataTypePReactionMessage()));
		EBStorage persist = storage.get(PERSIST_KEY, EBStorage.class);
		persist.set(message.getStringID(), rMessage);
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
