package us.myles_selim.starota.vote_rewards;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.Permissions;
import us.myles_selim.starota.commands.StarotaCommand;
import us.myles_selim.starota.wrappers.StarotaServer;

public class CommandVotePerks extends StarotaCommand {

	public CommandVotePerks() {
		super("votePerks", "Configure vote perks.");
	}

	@Override
	public Permissions requiredPermission() {
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
