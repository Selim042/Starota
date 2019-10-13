package us.myles_selim.starota.pvp.battle_discovery;

import java.util.concurrent.TimeUnit;

import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.MessageChannel;
import us.myles_selim.starota.commands.BotCommand;
import us.myles_selim.starota.commands.registry.CommandException;
import us.myles_selim.starota.profiles.PlayerProfile;
import us.myles_selim.starota.wrappers.StarotaServer;

public class CommandBattleReady extends BotCommand<StarotaServer> {

	public CommandBattleReady() {
		super("battleReady", "Marks yourself as battle ready.");
	}

	@Override
	public String getGeneralUsage() {
		return "<time>";
	}

	@Override
	public void execute(String[] args, Message message, StarotaServer server, MessageChannel channel)
			throws CommandException {
		PlayerProfile profile = server.getProfile(message.getAuthorAsMember().block());
		if (profile == null) {
			channel.createMessage("You must have a profile to be marked as battle ready, please use `"
					+ server.getPrefix() + "sRegister` to create a profile, or contact an admin")
					.block();
			return;
		}
		long time = 3600000;
		if (args.length > 1) {
			if (!args[1].matches("[0-1]?\\d:[0-5]\\d")) {
				channel.createMessage("Time provided must be in the format of hh:mm").block();
				return;
			}
			String[] split = args[1].split(":");
			time = TimeUnit.HOURS.toMillis(Long.parseLong(split[0]))
					+ TimeUnit.MINUTES.toMillis(Long.parseLong(split[1]));
		}
		int hours = (int) ((time / 3600000) % 24);
		int minutes = (int) ((time / 60000) % 60);
		server.setReady(profile.getPoGoName(), time);
		channel.createMessage(
				message.getAuthor().get().getMention() + " has been marked as battle ready for the next "
						+ hours + " hours and " + minutes + " minutes.")
				.block();
	}

}
