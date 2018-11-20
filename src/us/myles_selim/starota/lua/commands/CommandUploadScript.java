package us.myles_selim.starota.lua.commands;

import java.util.List;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IMessage.Attachment;
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.commands.registry.PrimaryCommandHandler;
import us.myles_selim.starota.commands.registry.java.JavaCommand;
import us.myles_selim.starota.lua.ScriptManager;

public class CommandUploadScript extends JavaCommand {

	public CommandUploadScript() {
		super("uploadScript", "Uploads a Lua script to Starota.");
	}

	@Override
	public String getGeneralUsage() {
		return "[event/command/remove]";
	}

	@Override
	public void execute(String[] args, IMessage message, IGuild guild, IChannel channel) {
		if (!Starota.canUseLua(guild)) {
			channel.sendMessage("This server cannot use Lua as it is a paid feature.");
			return;
		}
		if (args.length < 2) {
			channel.sendMessage("**Usage**: " + PrimaryCommandHandler.getPrefix(guild) + getName() + " "
					+ getGeneralUsage());
			return;
		}
		List<Attachment> attachS = message.getAttachments();
		if (attachS == null || attachS.size() != 1) {
			channel.sendMessage("Only uploading one script at a time is supported");
			return;
		}
		Attachment attach = attachS.get(0);
		if (!attach.getFilename().toLowerCase().endsWith(".lua")) {
			channel.sendMessage("Only `.lua` files are accepted");
			return;
		}
		switch (args[1].toLowerCase()) {
		case "event":
		case "eventhandler":
		case "e":
			break;
		case "command":
		case "cmd":
		case "c":
			break;
		case "remove":
		case "delete":
		case "rem":
		case "del":
			break;
		}
		if (ScriptManager.saveScript(guild, "test", attach))
			channel.sendMessage("Saved your new script");
		else
			channel.sendMessage("Failed to save your script");
	}

}
