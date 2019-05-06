package us.myles_selim.starota.pvp;

import java.util.concurrent.TimeUnit;

import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.TextChannel;
import us.myles_selim.starota.commands.StarotaCommand;
import us.myles_selim.starota.profiles.PlayerProfile;
import us.myles_selim.starota.wrappers.StarotaServer;

public class CommandBattleReady extends StarotaCommand {

	public CommandBattleReady() {
		super("battleReady", "Marks yourself as battle ready.");
	}

	@Override
	public String getGeneralUsage() {
		return "<time>";
	}

	@Override
	public void execute(String[] args, Message message, StarotaServer server, TextChannel channel)
			throws Exception {
		PlayerProfile profile = server.getProfile(message.getAuthor());
		if (profile == null) {
			channel.createMessage("You must have a profile to be marked as battle ready, please use `"
					+ server.getPrefix() + "sRegister` to create a profile, or contact an admin");
			return;
		}
		long time = 3600000;
		if (args.length > 1) {
			if (!args[1].matches("[0-1]?\\d:[0-5]\\d")) {
				channel.createMessage("Time provided must be in the format of hh:mm");
				return;
			}
			String[] split = args[1].split(":");
			time = TimeUnit.HOURS.toMillis(Long.parseLong(split[0]))
					+ TimeUnit.MINUTES.toMillis(Long.parseLong(split[1]));
		}
		int hours = (int) ((time / 3600000) % 24);
		int minutes = (int) ((time / 60000) % 60);
		server.setReady(profile.getPoGoName(), time);
		channel.createMessage(message.getAuthor() + " has been marked as battle ready for the next "
				+ hours + " hours and " + minutes + " minutes.");
	}

}
