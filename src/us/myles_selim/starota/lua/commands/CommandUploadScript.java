package us.myles_selim.starota.lua.commands;

import java.io.File;
import java.util.List;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IMessage.Attachment;
import sx.blah.discord.handle.obj.IRole;
import sx.blah.discord.handle.obj.Permissions;
import us.myles_selim.starota.RolePermHelper;
import us.myles_selim.starota.commands.StarotaCommand;
import us.myles_selim.starota.lua.LuaUtils;
import us.myles_selim.starota.lua.ScriptManager;
import us.myles_selim.starota.wrappers.StarotaServer;

public class CommandUploadScript extends StarotaCommand {

	public CommandUploadScript() {
		super("uploadScript", "Uploads a Lua script to Starota.");
	}

	@Override
	public Permissions requiredPermission() {
		return Permissions.ADMINISTRATOR;
	}

	@Override
	public IRole requiredRole(StarotaServer server) {
		if (server.getDiscordGuild().getLongID() == 481646364716040202L) // test
																			// server
			return server.getDiscordGuild().getRoleByID(489249119311888385L); // Starota
																				// test
																				// role
		return super.requiredRole(server);
	}

	@Override
	public String getGeneralUsage() {
		return "[event/command/remove]";
	}

	@Override
	public void execute(String[] args, IMessage message, StarotaServer server, IChannel channel) {
		if (!RolePermHelper.canUseLua(server.getDiscordGuild())) {
			channel.sendMessage("This server cannot use Lua as it is a paid feature.");
			return;
		}
		if (args.length < 2) {
			channel.sendMessage(
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
				channel.sendMessage(
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
				channel.sendMessage(
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
				channel.sendMessage("Successfully deleted script \"" + scriptName + "\"");
				if (scriptName.equalsIgnoreCase("eventHandler"))
					LuaUtils.clearEventHandlers(server);
				return;
			} else {
				channel.sendMessage("Failed to remove script \"" + scriptName + "\"");
				return;
			}
		default:
			channel.sendMessage(
					"**Usage**: " + server.getPrefix() + getName() + " " + getGeneralUsage());
			return;
		}
		if (uploaded) {
			channel.sendMessage("Saved your new script");
			ScriptManager.executeEventScript(server);
		} else
			channel.sendMessage("Failed to save your script");
	}

	private Attachment getAttachment(IChannel channel, IMessage message) {
		List<Attachment> attachS = message.getAttachments();
		if (attachS == null || attachS.size() != 1) {
			channel.sendMessage("Only uploading one script at a time is supported");
			return null;
		}
		Attachment attach = attachS.get(0);
		if (!attach.getFilename().toLowerCase().endsWith(ScriptManager.LUA_EXENSION)) {
			channel.sendMessage("Only `.lua` files are accepted");
			return null;
		}
		return attach;
	}

}
