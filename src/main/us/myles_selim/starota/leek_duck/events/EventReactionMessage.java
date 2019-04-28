package us.myles_selim.starota.leek_duck.events;

import sx.blah.discord.api.internal.json.objects.EmbedObject;
import sx.blah.discord.handle.impl.obj.ReactionEmoji;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IReaction;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.EmbedBuilder;
import sx.blah.discord.util.RequestBuffer;
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.enums.EnumPokemon;
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
	public void onEdit(StarotaServer server, IChannel channel, IMessage msg) {
		onSend(server, channel, msg);
	}

	@Override
	public void onReactionAdded(StarotaServer server, IChannel channel, IMessage msg, IUser user,
			IReaction react) {
		if (!react.getUserReacted(Starota.getOurUser()))
			return;
		int eventsSize = EventData.getRunningUpcomingEvents().length;
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
		StarotaEvent[] events = EventData.getRunningUpcomingEvents();
		if (events.length == 0) {
			EmbedBuilder builder = new EmbedBuilder();
			builder.appendDesc("No upcoming events");
			builder.withFooterText("Last updated").withTimestamp(EventData.getEventsCacheTime());
			return builder.build();
		}
		if (eventIndex >= events.length || eventIndex < 0)
			eventIndex = 0;
		StarotaEvent event = events[eventIndex];
		EmbedBuilder builder = new EmbedBuilder();
		// builder.withAuthorIcon("https://leekduck.com/assets/img/favicon/favicon-32x32.png");
		// builder.withAuthorName("Leek Duck");
		// builder.withAuthorUrl("http://leekduck.com");

		builder.withColor(event.color).withImage(event.image);
		builder.withTitle(event.name).withDesc(event.description);
		if (event.getTimeLeft() != null)
			builder.appendDesc("\n\n**" + event.getTimeLeft() + "**");
		if (event.link != null)
			builder.appendDesc(String.format("\n\n[%s](%s)", event.linkTitle, event.link));
		
		String featPoke = "";
		for (int id : event.featuredPokemon) {
			EnumPokemon poke = EnumPokemon.getPokemon(id);
		}

		builder.withFooterText("Event " + (eventIndex + 1) + "/" + events.length + " | Last updated")
				.withTimestamp(EventData.getEventsCacheTime());
		return builder.build();
	}

}
