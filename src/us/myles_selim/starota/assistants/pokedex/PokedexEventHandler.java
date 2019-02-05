package us.myles_selim.starota.assistants.pokedex;

import java.util.List;

import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.GuildCreateEvent;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IPrivateChannel;
import sx.blah.discord.handle.obj.IUser;
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.commands.StarotaCommand;
import us.myles_selim.starota.pokedex.CommandPokedex;
import us.myles_selim.starota.wrappers.StarotaServer;

public class PokedexEventHandler {

	private StarotaCommand dexCmd;

	@EventSubscriber
	public void onMessageRecieved(MessageReceivedEvent event) {
		if (event.getAuthor().isBot())
			return;
		String text = event.getMessage().getContent();
		IChannel channel = event.getChannel();
		IUser starota = Starota.getUser(Starota.STAROTA_ID);
		IUser starotaDev = Starota.getUser(Starota.STAROTA_DEV_ID);
		List<IUser> users = channel.getUsersHere();
		if (channel instanceof IPrivateChannel || users.contains(starota) || users.contains(starotaDev))
			return;
		StarotaServer server = StarotaServer.getServer(event.getGuild());
		String prefix = server.getPrefix();
		if (text.startsWith(prefix + "dex")) {
			text = text.substring(prefix.length());
			String[] args = text.split(" ");
			if (dexCmd == null)
				dexCmd = new CommandPokedex();
			try {
				dexCmd.execute(args, event.getMessage(), server, channel);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@EventSubscriber
	public void onServerJoin(GuildCreateEvent event) {
		Starota.submitStats();
	}

}
