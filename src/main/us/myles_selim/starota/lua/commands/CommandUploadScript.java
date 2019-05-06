package us.myles_selim.starota.lua.commands;

import java.io.File;
import java.util.Set;

import discord4j.core.object.entity.Attachment;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.MessageChannel;
import discord4j.core.object.entity.TextChannel;
import discord4j.core.object.util.Permission;
import us.myles_selim.starota.commands.StarotaCommand;
import us.myles_selim.starota.lua.LuaUtils;
import us.myles_selim.starota.lua.ScriptManager;
import us.myles_selim.starota.misc.utils.RolePermHelper;
import us.myles_selim.starota.wrappers.StarotaServer;

public class CommandUploadScript extends StarotaCommand {

	public CommandUploadScript() {
		super("uploadScript", "Uploads a Lua script to Starota.");
	}

	@Override
	public Permission requiredUsePermission() {
		return Permission.ADMINISTRATOR;
	}

	@Override
	public String getGeneralUsage() {
		return "[event/command/remove]";
	}

	@Override
	public void execute(String[] args, Message message, StarotaServer server, TextChannel channel) {
		if (!RolePermHelper.canUseLua(server.getDiscordGuild())) {
			channel.createMessage("This server cannot use Lua as it is a paid feature.");
			return;
		}
		if (args.length < 2) {
			channel.createMessage(
					"**Usage**: " + server.getPrefix() + getName() + " " + getGeneralUsage());
			return;
		}
		boolean uploaded = false;
		switch (args[1].toLowerCase()) {
		case "event":
		case "eventhandler":
		case "e":
			LuaUtils.clearEventHandlers(server);
			uploaded = ScriptManager.saveScript(server, "eventHandler" + ScriptManager.LUA_EXENSION,
					getAttachment(channel, message));
			break;
		case "command":
		case "cmd":
		case "c":
			if (args.length < 3) {
				channel.createMessage(
						"**Usage**: " + server.getPrefix() + getName() + " command [cmdName]");
				return;
			}
			uploaded = ScriptManager.saveScript(server,
					"commands" + File.separator + args[2] + ScriptManager.LUA_EXENSION,
					getAttachment(channel, message));
			break;
		case "remove":
		case "delete":
		case "rem":
		case "del":
			if (args.length < 3) {
				channel.createMessage(
						"**Usage**: " + server.getPrefix() + getName() + " remove [event/cmdName]");
				return;
			}
			String scriptName;
			switch (args[2].toLowerCase()) {
			case "event":
			case "eventHandler":
				scriptName = "eventHandler";
				break;
			default:
				scriptName = "commands" + File.separator + args[2];
				break;
			}
			if (ScriptManager.removeScript(server, scriptName + ScriptManager.LUA_EXENSION)) {
				channel.createMessage("Successfully deleted script \"" + scriptName + "\"");
				if (scriptName.equalsIgnoreCase("eventHandler"))
					LuaUtils.clearEventHandlers(server);
				return;
			} else {
				channel.createMessage("Failed to remove script \"" + scriptName + "\"");
				return;
			}
		default:
			channel.createMessage(
					"**Usage**: " + server.getPrefix() + getName() + " " + getGeneralUsage());
			return;
		}
		if (uploaded) {
			channel.createMessage("Saved your new script");
			ScriptManager.executeEventScript(server);
		} else
			channel.createMessage("Failed to save your script");
	}

	private Attachment getAttachment(MessageChannel channel, Message message) {
		Set<Attachment> attachS = message.getAttachments();
		if (attachS == null || attachS.size() != 1) {
			channel.createMessage("Only uploading one script at a time is supported");
			return null;
		}
		Attachment attach = null;
		for (Attachment a : attachS) {
			attach = a;
			break;
		}
		if (!attach.getFilename().toLowerCase().endsWith(ScriptManager.LUA_EXENSION)) {
			channel.createMessage("Only `.lua` files are accepted");
			return null;
		}
		return attach;
	}

}
