package us.myles_selim.starota.commands.registry;

import java.util.List;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IRole;
import sx.blah.discord.handle.obj.Permissions;

public interface ICommand extends Comparable<ICommand> {

	void setCategory(String category);

	public String getCategory();

	public String getDescription();

	public Permissions requiredPermission();

	public IRole requiredRole(IGuild guild);

	public String getName();

	public List<String> getAliases();

	public String getGeneralUsage();

	public void execute(String[] args, IMessage message, IGuild guild, IChannel channel);

}
