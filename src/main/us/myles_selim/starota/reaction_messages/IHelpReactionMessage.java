package us.myles_selim.starota.reaction_messages;

import java.util.function.Consumer;

import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.PrivateChannel;
import discord4j.core.object.entity.User;
import discord4j.core.object.reaction.ReactionEmoji;
import discord4j.core.spec.EmbedCreateSpec;
import us.myles_selim.starota.misc.data_types.BotServer;

public interface IHelpReactionMessage {

	Consumer<? super EmbedCreateSpec> getHelpEmbed(BotServer server);

	/**
	 * Call, don't override
	 */
	@Deprecated
	public default void addHelpButton(ReactionMessage msg, BotServer server) {
		msg.addButton(msg.new ReactionButton(ReactionEmoji.unicode("❓"),
				(Message message, User user, boolean added) -> {
					PrivateChannel pm = user.getPrivateChannel().block();
					pm.createEmbed(((IHelpReactionMessage) this).getHelpEmbed(server)).block();
					return true;
				}));
	}

}
