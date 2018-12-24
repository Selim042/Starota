package us.myles_selim.starota.commands;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import us.myles_selim.starota.profiles.PlayerProfile;
import us.myles_selim.starota.wrappers.StarotaServer;

public class CommandTest extends StarotaCommand {

	public CommandTest() {
		super("test");
	}

	@Override
	public void execute(String[] args, IMessage message, StarotaServer server, IChannel channel) {
		PlayerProfile profile = server.profiles.get(message.getAuthor().getStringID(),
				PlayerProfile.class);
		if (profile == null)
			channel.sendMessage("null");
		else
			channel.sendMessage(profile.toEmbed(server));
		
		profile = server.getProfile(message.getAuthor());
	}

}
