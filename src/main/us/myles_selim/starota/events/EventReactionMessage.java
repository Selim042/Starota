package us.myles_selim.starota.events;

import java.util.List;

import sx.blah.discord.api.internal.json.objects.EmbedObject;
import sx.blah.discord.handle.impl.obj.ReactionEmoji;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IReaction;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.EmbedBuilder;
import sx.blah.discord.util.RequestBuffer;
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.leek_duck.LeekDuckData;
import us.myles_selim.starota.leek_duck.LeekDuckData.LeekEvent;
import us.myles_selim.starota.reaction_messages.ReactionMessage;
import us.myles_selim.starota.wrappers.StarotaServer;

public class EventReactionMessage extends ReactionMessage {

	private static final String LEFT_EMOJI = "⬅";
	private static final String RIGHT_EMOJI = "➡";
	private static final String REFRESH_EMOJI = "🔄";

	private int eventIndex = 0;

	@Override
	public void onSend(StarotaServer server, IChannel channel, IMessage msg) {
		RequestBuffer.request(() -> msg.addReaction(ReactionEmoji.of(LEFT_EMOJI))).get();
		RequestBuffer.request(() -> msg.addReaction(ReactionEmoji.of(RIGHT_EMOJI))).get();
		RequestBuffer.request(() -> msg.addReaction(ReactionEmoji.of(REFRESH_EMOJI)));
	}

	@Override
	public void onReactionAdded(StarotaServer server, IChannel channel, IMessage msg, IUser user,
			IReaction react) {
		if (!react.getUserReacted(Starota.getOurUser()))
			return;
		int eventsSize = LeekDuckData.getRunningUpcomingEvents().size();
		if (react.getEmoji().getName().equals(LEFT_EMOJI)) {
			if (eventIndex <= 0)
				eventIndex = eventsSize;
			else
				eventIndex--;
		} else if (react.getEmoji().getName().equals(RIGHT_EMOJI)) {
			if (eventIndex >= eventsSize)
				eventIndex = 0;
			else
				eventIndex++;
		}
		RequestBuffer.request(() -> this.editMessage(channel, msg));
		RequestBuffer.request(() -> msg.removeReaction(user, react));
	}

	@Override
	protected EmbedObject getEmbed(StarotaServer server) {
		List<LeekEvent> events = LeekDuckData.getRunningUpcomingEvents();
		if (eventIndex >= events.size() || eventIndex < 0)
			eventIndex = 0;
		LeekEvent event = events.get(eventIndex);
		EmbedBuilder builder = new EmbedBuilder();
		builder.withColor(event.getColor());
		builder.withImage(event.getThumbnail());
		builder.withAuthorName(event.getTitle());
		builder.withTitle(event.getDuration());
		builder.withDesc(event.getDescription());
		if (event.getTimeLeft() != null)
			builder.appendDesc("\n\n**" + event.getTimeLeft() + "**");
		if (event.getLink() != null)
			builder.appendDesc(String.format("\n\n[%s](%s)", event.getLinkTitle(), event.getLink()));

		builder.withFooterText("Event " + (eventIndex + 1) + "/" + events.size());
		return builder.build();
	}

}
