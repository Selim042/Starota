package us.myles_selim.starota.weather;

import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.MessageChannel;
import us.myles_selim.starota.commands.BotCommand;
import us.myles_selim.starota.commands.registry.CommandException;
import us.myles_selim.starota.enums.EnumWeather;
import us.myles_selim.starota.misc.utils.EmbedBuilder;
import us.myles_selim.starota.misc.utils.MiscUtils;
import us.myles_selim.starota.weather.api.WeatherLocation;
import us.myles_selim.starota.wrappers.StarotaServer;

public class CommandWeather extends BotCommand<StarotaServer> {

	public CommandWeather() {
		super("weather", "Shows the upcoming PoGo weather forecasts for your area.");
	}

	@Override
	public void execute(String[] args, Message message, StarotaServer server, MessageChannel channel)
			throws CommandException {
		if (!server.isWeatherSetup()) {
			channel.createMessage("Weather is not configured for this server.").block();
			return;
		}
		WeatherLocation loc = server.getLocation();
		if (loc == null) {
			channel.createEmbed((e) -> e.setDescription("No weather forecast found")).block();
			return;
		}
		StringBuilder out = new StringBuilder();
		StringBuilder line = new StringBuilder();
		for (int i = 0; i < 10; i++) {
			if (i == 0)
				out.append("**Now**: ");
			else
				out.append("**+" + i + " hr**: ");
			EnumWeather[] boosts = server.getPossibleBoosts(i);
			if (boosts == null || boosts.length == 0)
				line.append("No weather forecast found  ");
			for (EnumWeather weather : boosts)
				line.append(weather
						+ (weather == null ? "" : MiscUtils.getEmojiDisplay(weather.getEmoji())) + ", ");
			out.append(line.subSequence(0, line.length() - 2) + "\n");
			line.setLength(0);
		}
		EmbedBuilder embed = new EmbedBuilder();
		embed.withTitle(String.format("Weather Forecast for %s", loc.getNameWithAreas()));
		if (out.length() > out.indexOf(" ") + 4)
			embed.appendDesc(out.toString());
		else
			embed.appendDesc("No weather forecast found");

		embed.appendDesc("\n\n**Note**: This weather prediction is by no means 100% accurate.  "
				+ "Due to the nature of how Niantic gets weather data and the nature of weather itself, "
				+ "this can never be 100% accurate.");
		channel.createEmbed(embed.build()).block();
	}

}
