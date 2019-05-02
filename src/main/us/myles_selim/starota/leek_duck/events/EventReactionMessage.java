package us.myles_selim.starota.leek_duck.events;

import sx.blah.discord.api.internal.json.objects.EmbedObject;
import sx.blah.discord.handle.impl.obj.ReactionEmoji;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.EmbedBuilder;
import sx.blah.discord.util.RequestBuffer;
import us.myles_selim.starota.misc.utils.IndexHolder;
import us.myles_selim.starota.reaction_messages.ReactionMessage;
import us.myles_selim.starota.wrappers.StarotaServer;

public class EventReactionMessage extends ReactionMessage {

	private static final String REFRESH_EMOJI = "🔄";

	private IndexHolder eventIndex = new IndexHolder();

	public EventReactionMessage() {}

	@Override
	public void onSend(StarotaServer server, IChannel channel, IMessage msg) {
		this.addPageButtons(eventIndex, EventData.getRunningUpcomingEvents().length);
		this.addButton(new ReactionButton(ReactionEmoji.of(REFRESH_EMOJI), (message, user, added) -> {
			RequestBuffer.request(() -> this.editMessage(channel, msg));
			RequestBuffer.request(() -> msg.removeReaction(user, ReactionEmoji.of(REFRESH_EMOJI)));
			return true;
		}));
		RequestBuffer.request(() -> msg.addReaction(ReactionEmoji.of(REFRESH_EMOJI)));
	}

	@Override
	public void onEdit(StarotaServer server, IChannel channel, IMessage msg) {
		onSend(server, channel, msg);
	}

	@Override
	protected EmbedObject getEmbed(StarotaServer server) {
		StarotaEvent[] events = EventData.getRunningUpcomingEvents();
		if (events.length == 0) {
			EmbedBuilder builder = new EmbedBuilder();
			builder.appendDesc("No upcoming events");
			builder.withFooterText("Last updated").withTimestamp(EventData.getEventsCacheTime());
			return builder.build();
		}
		if (eventIndex.value >= events.length || eventIndex.value < 0)
			eventIndex.value = 0;
		StarotaEvent event = events[eventIndex.value];
		return event.toEmbed(eventIndex.value + 1, events.length, server);
	}

}
