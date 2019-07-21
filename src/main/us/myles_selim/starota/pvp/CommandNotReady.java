package us.myles_selim.starota.pvp;

import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.MessageChannel;
import us.myles_selim.starota.commands.BotCommand;
import us.myles_selim.starota.commands.registry.CommandException;
import us.myles_selim.starota.profiles.PlayerProfile;
import us.myles_selim.starota.wrappers.StarotaServer;

public class CommandNotReady extends BotCommand<StarotaServer> {

	public CommandNotReady() {
		super("notReady", "Marks yourself as not battle ready.");
	}

	@Override
	public void execute(String[] args, Message message, StarotaServer server, MessageChannel channel)
			throws CommandException {
		PlayerProfile profile = server.getProfile(message.getAuthorAsMember().block());
		if (profile == null || server.isBattleReady(profile.getPoGoName()) < 0)
			channel.createMessage("You are not battle ready.").block();
		else {
			server.notReady(profile.getPoGoName());
			channel.createMessage("You are no longer marked as battle ready.").block();
		}
	}

}
