package us.myles_selim.starota.commands.registry;

import java.util.List;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;

public interface ICommandHandler {

	public boolean executeCommand(String[] args, IMessage message, IGuild guild, IChannel channel);

	public List<ICommand> getAllCommands(IGuild server);

	public ICommand findCommand(IGuild server, String name);

}
