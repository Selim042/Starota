package us.myles_selim.starota.commands.selim_pm;

import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.MessageChannel;
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.commands.registry.CommandException;
import us.myles_selim.starota.commands.registry.java.JavaCommand;
import us.myles_selim.starota.misc.utils.StarotaConstants;
import us.myles_selim.starota.wrappers.StarotaServer;

public class CommandMapPoints extends JavaCommand {

	public CommandMapPoints() {
		super("mapPoints");
	}

	@Override
	public void execute(String[] args, Message message, Guild guild, MessageChannel channel)
			throws CommandException {
		StringBuilder builder = new StringBuilder("```\nhttp://dwtkns.com/pointplotter/\n");
		Starota.getClient().getGuilds().map(StarotaServer::getServer)
				.filter(StarotaServer::isWeatherSetup)
				.map(s -> (String) s.getSetting(StarotaConstants.Settings.COORDS)).all(coord -> {
					String[] parts = coord.split(",");
					builder.append(parts[1] + "," + parts[0] + "\n");
					return true;
				}).block();
		builder.append("\n```");
		channel.createMessage(builder.toString()).block();
	}

}
