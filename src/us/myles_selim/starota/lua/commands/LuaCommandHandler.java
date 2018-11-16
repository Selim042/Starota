package us.myles_selim.starota.lua.commands;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import us.myles_selim.starota.commands.registry.ICommandHandler;

public class LuaCommandHandler implements ICommandHandler {

	@Override
	public boolean executeCommand(String[] args, IMessage message, IGuild guild, IChannel channel) {
		return false;
	}

}
