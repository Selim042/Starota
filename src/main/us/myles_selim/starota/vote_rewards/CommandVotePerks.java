package us.myles_selim.starota.vote_rewards;

import java.util.EnumSet;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.Permissions;
import us.myles_selim.starota.commands.BotCommand;
import us.myles_selim.starota.wrappers.StarotaServer;

public class CommandVotePerks extends BotCommand<StarotaServer> {

	public CommandVotePerks() {
		super("votePerks", "Configure vote perks.");
	}

	@Override
	public EnumSet<Permissions> getCommandPermissions() {
		return EnumSet.of(Permissions.SEND_MESSAGES, Permissions.EMBED_LINKS,
				Permissions.USE_EXTERNAL_EMOJIS, Permissions.ADD_REACTIONS, Permissions.MANAGE_MESSAGES);
	}

	@Override
	public Permissions requiredUsePermission() {
		return Permissions.ADMINISTRATOR;
	}

	@Override
	public void execute(String[] args, IMessage message, StarotaServer server, IChannel channel)
			throws Exception {
		if (server.getDiscordGuild().getOwnerLongID() != message.getAuthor().getLongID()) {
			channel.sendMessage("Only the server owner can allocate vote points.");
			return;
		}
		new VoteRewardsReactionMessage().sendMessage(channel);
	}

}
