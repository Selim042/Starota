package us.myles_selim.starota.pvp;

import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.TextChannel;
import us.myles_selim.starota.commands.StarotaCommand;
import us.myles_selim.starota.profiles.PlayerProfile;
import us.myles_selim.starota.wrappers.StarotaServer;

public class CommandNotReady extends StarotaCommand {

	public CommandNotReady() {
		super("notReady", "Marks yourself as not battle ready.");
	}

	@Override
	public void execute(String[] args, Message message, StarotaServer server, TextChannel channel)
			throws Exception {
		PlayerProfile profile = server.getProfile(message.getAuthor());
		if (profile == null || server.isBattleReady(profile.getPoGoName()) < 0)
			channel.createMessage("You are not battle ready.");
		else {
			server.notReady(profile.getPoGoName());
			channel.createMessage("You are no longer marked as battle ready.");
		}
	}

}
