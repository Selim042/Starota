package us.myles_selim.starota.weather;

import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.MessageChannel;
import us.myles_selim.starota.commands.BotCommand;
import us.myles_selim.starota.commands.registry.CommandException;
import us.myles_selim.starota.enums.EnumWeather;
import us.myles_selim.starota.info_channels.InfoChannel;
import us.myles_selim.starota.wrappers.StarotaServer;

public class CommandCreateWeatherChannel extends BotCommand<StarotaServer> {

	public static final String WEATHER_CHANNEL_KEY = "starota_weather";

	public CommandCreateWeatherChannel() {
		super("createWeatherChannel", "Creates the weather info channel.");
	}

	@Override
	public void execute(String[] args, Message message, StarotaServer server, MessageChannel channel)
			throws CommandException {
		if (server.hasInfoChannel(WEATHER_CHANNEL_KEY))
			channel.createMessage("Weather info channel already created.").block();
		else {
			server.getOrCreateInfoChannel(WEATHER_CHANNEL_KEY);
			updateChannelName(server);
			channel.createMessage("Weather info channel has now been created.").block();
		}
	}

	public static void updateChannelName(StarotaServer server) {
		if (!server.hasInfoChannel(WEATHER_CHANNEL_KEY))
			return;
		InfoChannel iCh = server.getOrCreateInfoChannel(WEATHER_CHANNEL_KEY);
		EnumWeather[] boosts = server.getCurrentPossibleBoosts();
		iCh.setName("Weather: " + (boosts.length > 0 ? boosts[0] : "None"));
	}

}
