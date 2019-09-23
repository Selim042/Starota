package us.myles_selim.starota.profiles.commands;

import java.util.function.Consumer;

import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.MessageChannel;
import discord4j.core.object.entity.PrivateChannel;
import discord4j.core.object.entity.User;
import discord4j.core.spec.EmbedCreateSpec;
import us.myles_selim.starota.misc.utils.EmojiServerHelper;
import us.myles_selim.starota.profiles.PlayerProfile;
import us.myles_selim.starota.reaction_messages.ReactionMessage;
import us.myles_selim.starota.wrappers.StarotaServer;

public class ProfileReactionMessage extends ReactionMessage {

	private final PlayerProfile profile;

	public ProfileReactionMessage(PlayerProfile profile) {
		this.profile = profile;
	}

	@Override
	public void onSend(StarotaServer server, MessageChannel channel, Message msg) {
		if (profile.getTrainerCode() != -1) {
			this.addButton(new ReactionButton(EmojiServerHelper.getEmoji("qr"),
					(Message message, User user, boolean added) -> {
						if (!added)
							return false;
						PrivateChannel pm = user.getPrivateChannel().block();
						pm.createEmbed((e) -> {
							User profileUser = profile.getDiscordUser();
							e.setAuthor(profileUser.getUsername(), null, profileUser.getAvatarUrl());
							e.setTitle(String.format("%s Friend Code", profile.getPoGoName()));
							e.setImage(String.format(
									"https://zxing.org/w/chart?cht=qr&chs=500x500&chld=H&chl=%s",
									profile.getTrainerCode()));
							e.setDescription(profile.getTrainerCodeString());
						}).block();
						return true;
					}));
		}
	}

	@Override
	protected Consumer<? super EmbedCreateSpec> getEmbed(StarotaServer server) {
		return profile.toEmbed(server);
	}

}
