package us.myles_selim.starota.leek_duck.events;

import java.util.function.Consumer;

import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.TextChannel;
import discord4j.core.object.reaction.ReactionEmoji;
import discord4j.core.spec.EmbedCreateSpec;
import us.myles_selim.starota.misc.utils.EmbedBuilder;
import us.myles_selim.starota.misc.utils.IndexHolder;
import us.myles_selim.starota.reaction_messages.ReactionMessage;
import us.myles_selim.starota.wrappers.StarotaServer;

public class EventReactionMessage extends ReactionMessage {

	private static final String REFRESH_EMOJI = "🔄";

	private IndexHolder eventIndex = new IndexHolder();

	public EventReactionMessage() {}

	@Override
	public void onSend(StarotaServer server, TextChannel channel, Message msg) {
		this.addPageButtons(eventIndex, EventData.getRunningUpcomingEvents().length);
		this.addButton(
				new ReactionButton(ReactionEmoji.unicode(REFRESH_EMOJI), (message, user, added) -> {
					this.editMessage(channel, msg);
					msg.removeReaction(ReactionEmoji.unicode(REFRESH_EMOJI), user.getId()).block();
					return true;
				}));
		msg.addReaction(ReactionEmoji.unicode(REFRESH_EMOJI)).block();
	}

	@Override
	public void onEdit(StarotaServer server, TextChannel channel, Message msg) {
		onSend(server, channel, msg);
	}

	@Override
	protected Consumer<? super EmbedCreateSpec> getEmbed(StarotaServer server) {
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
