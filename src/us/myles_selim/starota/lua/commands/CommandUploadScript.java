package us.myles_selim.starota.lua.commands;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IMessage.Attachment;
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.commands.registry.Command;

public class CommandUploadScript extends Command {

	public CommandUploadScript() {
		super("uploadScript", "Uploads a Lua script to Starota.");
	}

	@Override
	public String getGeneralUsage() {
		return super.getGeneralUsage();
	}

	@Override
	public void execute(String[] args, IMessage message, IGuild guild, IChannel channel) {
		if (!Starota.canUseLua(guild)) {
			channel.sendMessage("This server cannot use Lua as it is a paid feature.");
			return;
		}
		for (Attachment a : message.getAttachments())
			System.out.println(a.getFilename());
	}

}
