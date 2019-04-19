package us.myles_selim.starota.reaction_messages;

import java.util.HashMap;

import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageDeleteEvent;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageEditEvent;
import sx.blah.discord.handle.impl.events.guild.channel.message.reaction.ReactionAddEvent;
import sx.blah.discord.handle.impl.events.guild.channel.message.reaction.ReactionRemoveEvent;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import us.myles_selim.ebs.EBStorage;
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.reaction_messages.PersistReactionMessage.DataTypePReactionMessage;
import us.myles_selim.starota.wrappers.StarotaServer;

public class ReactionMessageRegistry {

	public static final String PERSIST_KEY = "persistReactions";

	protected static final HashMap<String, ReactionMessage> MESSAGES = new HashMap<>();
	private static boolean inited = false;

	public static void init() {
		if (inited)
			return;
		inited = true;

		for (IGuild g : Starota.getClient().getGuilds())
			deserializeAll(StarotaServer.getServer(g));
	}

	@EventSubscriber
	public void onReactAdd(ReactionAddEvent event) {
		if (event.getUser().isBot())
			return;
		IMessage msg = event.getMessage();
		if (!MESSAGES.containsKey(msg.getStringID()))
			return;
		ReactionMessage rMsg = MESSAGES.get(msg.getStringID());
		rMsg.onReactionAdded(StarotaServer.getServer(event.getGuild()), event.getChannel(), msg,
				event.getUser(), event.getReaction());
	}

	@EventSubscriber
	public void onReactRemove(ReactionRemoveEvent event) {
		if (event.getUser().isBot())
			return;
		IMessage msg = event.getMessage();
		if (!MESSAGES.containsKey(msg.getStringID()))
			return;
		ReactionMessage rMsg = MESSAGES.get(msg.getStringID());
		rMsg.onReactionRemoved(StarotaServer.getServer(event.getGuild()), event.getChannel(), msg,
				event.getUser(), event.getReaction());
	}

	@EventSubscriber
	public void onDelete(MessageDeleteEvent event) {
		String stringId = Long.toString(event.getMessageID());
		if (!MESSAGES.containsKey(stringId))
			return;
		ReactionMessage rMsg = MESSAGES.remove(stringId);
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
		if (!MESSAGES.containsKey(event.getMessage().getStringID()))
			return;
		ReactionMessage rMessage = MESSAGES.get(event.getMessage().getStringID());
		if (!(rMessage instanceof PersistReactionMessage))
			return;
		serialize(StarotaServer.getServer(event.getGuild()), event.getMessage(),
				(PersistReactionMessage) rMessage);
	}

	protected static void serialize(StarotaServer server, IMessage message,
			PersistReactionMessage rMessage) {
		EBStorage storage = server.getData();
		if (!storage.containsKey(PERSIST_KEY))
			storage.set(PERSIST_KEY, new EBStorage().registerType(new DataTypePReactionMessage()));
		EBStorage persist = storage.get(PERSIST_KEY, EBStorage.class);
		persist.set(message.getStringID(), rMessage);
		storage.set(PERSIST_KEY, persist);
	}

	private static void deserializeAll(StarotaServer server) {
		EBStorage storage = server.getData();
		if (!storage.containsKey(PERSIST_KEY))
			return;
		EBStorage persist = storage.get(PERSIST_KEY, EBStorage.class);
		for (String key : persist.getKeys()) {
			ReactionMessage rMsg = persist.get(key, PersistReactionMessage.class);
			MESSAGES.put(key, rMsg);
		}
	}

	public static ReactionMessage getMessage(long msg) {
		return MESSAGES.get(Long.toString(msg));
	}

}
