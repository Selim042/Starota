package us.myles_selim.starota.leek_duck.events;

import sx.blah.discord.api.internal.json.objects.EmbedObject;
import sx.blah.discord.handle.impl.obj.ReactionEmoji;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.EmbedBuilder;
import sx.blah.discord.util.RequestBuffer;
import us.myles_selim.starota.enums.EnumPokemon;
import us.myles_selim.starota.enums.EnumPokemonType;
import us.myles_selim.starota.leek_duck.events.StarotaEvent.EventFieldResearch;
import us.myles_selim.starota.leek_duck.events.StarotaEvent.EventTieredBonus;
import us.myles_selim.starota.leek_duck.events.StarotaEvent.ResearchReward;
import us.myles_selim.starota.misc.utils.EmojiConstants;
import us.myles_selim.starota.misc.utils.EmojiServerHelper;
import us.myles_selim.starota.misc.utils.ImageHelper;
import us.myles_selim.starota.misc.utils.IndexHolder;
import us.myles_selim.starota.misc.utils.MiscUtils;
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
		EmbedBuilder builder = new EmbedBuilder();
		// builder.withAuthorIcon("https://leekduck.com/assets/img/favicon/favicon-32x32.png");
		// builder.withAuthorName("Leek Duck");
		// builder.withAuthorUrl("http://leekduck.com");

		builder.withColor(event.color).withImage(event.image);
		builder.withTitle(
				event.location != null && !event.location.isEmpty() ? event.name + ": " + event.location
						: event.name)
				.withDesc(event.description);
		String timeLeft = event.getTimeLeft(server.getTimezone());
		if (timeLeft != null)
			builder.appendDesc("\n\n**" + timeLeft + "**");
		if (event.link != null)
			builder.appendDesc(String.format("\n\n[%s](%s)", event.linkTitle, event.link));

		if (event.featuredPokemon.length == 1)
			builder.withThumbnail(
					ImageHelper.getOfficalArtwork(EnumPokemon.getPokemon(event.featuredPokemon[0])));
		StringBuilder featPoke = new StringBuilder();
		for (int id : event.featuredPokemon) {
			EnumPokemon poke = EnumPokemon.getPokemon(id);
			if (MiscUtils.arrContains(event.newShinies, id))
				featPoke.append(poke.getName() + " " + EmojiServerHelper.getEmoji("shiny") + ", ");
			else
				featPoke.append(poke.getName() + ", ");
		}
		if (featPoke.length() != 0)
			builder.appendField("Featured Pokemon:", featPoke.substring(0, featPoke.length() - 2),
					false);

		StringBuilder featType = new StringBuilder();
		for (int id : event.featuredTypes) {
			EnumPokemonType type = EnumPokemonType.fromOrdinal(id);
			featType.append(type.name().substring(0, 1) + type.name().toLowerCase().substring(1) + ", ");
		}
		if (featType.length() != 0)
			builder.appendField("Featured Types:", featType.substring(0, featType.length() - 2), false);

		if (event.raidsChanged || event.raidOriented) {
			builder.appendField("Raids:",
					"Raids Changed: " + EmojiConstants.getBooleanEmoji(event.raidsChanged)
							+ "\nRaid Oriented: " + EmojiConstants.getBooleanEmoji(event.raidOriented),
					false);
		}

		StringBuilder bonuses = new StringBuilder();
		for (String b : event.bonuses)
			bonuses.append(b + ", ");
		if (bonuses.length() != 0)
			builder.appendField("Bonuses:", bonuses.toString().substring(0, bonuses.length() - 2),
					false);

		StringBuilder tieredBonuses = new StringBuilder();
		for (EventTieredBonus tb : event.tierdBonuses) {
			tieredBonuses.append("**" + tb.name + "**:\n");
			for (String b : tb.bonuses)
				tieredBonuses.append(" - " + b + "\n");
		}
		if (tieredBonuses.length() != 0)
			builder.appendField("Tiered Bonuses:", tieredBonuses.toString(), false);

		StringBuilder research = new StringBuilder();
		for (EventFieldResearch fr : event.fieldResearch) {
			research.append("**" + fr.name + "**:\n");
			for (ResearchReward b : fr.rewards) {
				research.append(" - " + b + "\n");
			}
		}
		if (research.length() != 0)
			builder.appendField("Field Research:", research.toString(), false);

		builder.withFooterText(
				"Event " + (eventIndex.value + 1) + "/" + events.length + " | Last updated")
				.withTimestamp(EventData.getEventsCacheTime());
		return builder.build();
	}

}
