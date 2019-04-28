package us.myles_selim.starota.pvp;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import us.myles_selim.starota.commands.StarotaCommand;
import us.myles_selim.starota.profiles.PlayerProfile;
import us.myles_selim.starota.wrappers.StarotaServer;

public class CommandNotReady extends StarotaCommand {

	public CommandNotReady() {
		super("notReady", "Marks yourself as not battle ready.");
	}

	@Override
	public void execute(String[] args, IMessage message, StarotaServer server, IChannel channel)
			throws Exception {
		PlayerProfile profile = server.getProfile(message.getAuthor());
		if (profile == null || server.isBattleReady(profile.getPoGoName()) < 0)
			channel.sendMessage("You are not battle ready.");
		else {
			server.notReady(profile.getPoGoName());
			channel.sendMessage("You are no longer marked as battle ready.");
		}
	}

}
