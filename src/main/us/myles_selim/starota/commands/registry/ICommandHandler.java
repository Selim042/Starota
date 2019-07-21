package us.myles_selim.starota.commands.registry;

import java.util.List;

import discord4j.core.DiscordClient;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.TextChannel;

public interface ICommandHandler {

	public boolean executeCommand(String[] args, Message message, Guild guild, TextChannel channel)
			throws Exception;

	public List<ICommand> getAllCommands(Guild server);

	public List<ICommand> getCommandsByCategory(Guild server, String category);

	public List<String> getAllCategories(Guild server);

	public ICommand findCommand(Guild server, Message msg, String name);

	public DiscordClient getDiscordClient();

}
