package us.myles_selim.starota.vote_rewards;

import discord4j.command.util.CommandException;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.MessageChannel;
import discord4j.core.object.entity.TextChannel;
import discord4j.core.object.util.Permission;
import discord4j.core.object.util.PermissionSet;
import us.myles_selim.starota.commands.BotCommand;
import us.myles_selim.starota.wrappers.StarotaServer;

public class CommandVotePerks extends BotCommand<StarotaServer> {

	public CommandVotePerks() {
		super("votePerks", "Configure vote perks.");
	}

	@Override
	public PermissionSet getCommandPermission() {
		return PermissionSet.of(Permission.SEND_MESSAGES, Permission.EMBED_LINKS,
				Permission.USE_EXTERNAL_EMOJIS, Permission.ADD_REACTIONS, Permission.MANAGE_MESSAGES);
	}

	@Override
	public Permission requiredUsePermission() {
		return Permission.ADMINISTRATOR;
	}

	@Override
	public void execute(String[] args, Message message, StarotaServer server, MessageChannel channel)
			throws CommandException {
		if (!server.getDiscordGuild().getOwnerId().equals(message.getAuthor().get().getId())) {
			channel.createMessage("Only the server owner can allocate vote points.").block();
			return;
		}
		new VoteRewardsReactionMessage().createMessage((TextChannel) channel);
	}

}
