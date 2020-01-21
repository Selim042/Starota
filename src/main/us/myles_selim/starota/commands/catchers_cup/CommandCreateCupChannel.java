package us.myles_selim.starota.commands.catchers_cup;

import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.MessageChannel;
import us.myles_selim.starota.commands.BotCommand;
import us.myles_selim.starota.commands.registry.CommandException;
import us.myles_selim.starota.enums.EnumTeam;
import us.myles_selim.starota.info_channels.InfoChannel;
import us.myles_selim.starota.wrappers.StarotaServer;

public class CommandCreateCupChannel extends BotCommand<StarotaServer> {

	public static final String CATCHERS_CUP_CHANNEL_KEY = "starota_catchers_cup_channel";

	public CommandCreateCupChannel() {
		super("createCupChannel", "Creates the Catcher's Cup info channel.");
	}

	@Override
	public void execute(String[] args, Message message, StarotaServer server, MessageChannel channel)
			throws CommandException {
		if (server.hasInfoChannel(CATCHERS_CUP_CHANNEL_KEY))
			channel.createMessage("Catcher's Cup channel already created.").block();
		else {
			server.getOrCreateInfoChannel(CATCHERS_CUP_CHANNEL_KEY);
			updateChannelName(server);
			channel.createMessage("Catcher's Cup info channel has now been created.").block();
		}
	}

	public static void updateChannelName(StarotaServer server) {
		if (!server.hasInfoChannel(CATCHERS_CUP_CHANNEL_KEY))
			return;
		InfoChannel iCh = server.getOrCreateInfoChannel(CATCHERS_CUP_CHANNEL_KEY);
		EnumTeam winningTeam = server.getHighestTeam();
		String teamHalf;
		if (winningTeam == EnumTeam.NO_TEAM)
			teamHalf = "Tied";
		else
			teamHalf = String.format("%s-%,d", winningTeam, server.getTeamPoints(winningTeam));

		String name;
		if (teamHalf.length() > 10)
			name = "CC: " + teamHalf;
		else
			name = "Catcher's Cup: " + teamHalf;
		iCh.setName(name);
	}

}
