package us.myles_selim.starota.commands;

import sx.blah.discord.api.internal.json.objects.EmbedObject;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.handle.obj.Permissions;
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.commands.registry.Command;

public class CommandStatus extends Command {

	public CommandStatus() {
		super("status");
	}

	@Override
	public Permissions requiredPermission() {
		return Permissions.ADMINISTRATOR;
	}

	@Override
	public void execute(String[] args, IMessage message, IGuild guild, IChannel channel) {
		IUser sender = message.getAuthor();
		if (sender.getPermissionsForGuild(guild).contains(Permissions.ADMINISTRATOR)) {
			channel.sendMessage(new EmbedObject(
					Starota.getClient().getOurUser().getDisplayName(guild) + " Status", null,
					"Discord: " + (Starota.getClient().isReady() ? "Online" : "Offline"), null, null,
					0x000000, null, null, null, null, null, null, null));
		}
	}

}
