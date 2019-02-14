package us.myles_selim.starota.commands.registry.java;

import java.util.ArrayList;
import java.util.List;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IRole;
import sx.blah.discord.handle.obj.Permissions;
import us.myles_selim.starota.commands.registry.ICommand;
import us.myles_selim.starota.commands.registry.ICommandHandler;
import us.myles_selim.starota.commands.registry.PrimaryCommandHandler;

public class JavaCommand implements ICommand {

	private final String name;
	private final String description;

	public JavaCommand(String name) {
		this(name, null);
	}

	public JavaCommand(String name, String description) {
		this.name = name;
		this.description = description;
	}

	@Override
	public String getDescription() {
		return this.description;
	}

	@Override
	public Permissions requiredPermission() {
		return null;
	}

	@Override
	public IRole requiredRole(IGuild guild) {
		return null;
	}

	@Override
	public final String getName() {
		return this.name;
	}

	@Override
	public List<String> getAliases() {
		List<String> aliases = new ArrayList<>();
		aliases.add(this.name);
		return aliases;
	}

	@Override
	public String getGeneralUsage() {
		return null;
	}

	@Override
	public void execute(String[] args, IMessage message, IGuild guild, IChannel channel)
			throws Exception {}

	@Override
	public int compareTo(ICommand o) {
		if (this.category.equals(o.getCategory()))
			return name.compareTo(o.getName());
		return this.category.compareTo(o.getCategory());
	}

	protected void sendUsage(String prefix, IChannel channel) {
		channel.sendMessage(String.format("**Usage**: %s%s %s", prefix, getName(), getGeneralUsage()));
	}

	private String category;

	@Override
	public final void setCategory(String category) {
		if (this.category == null)
			this.category = category;
	}

	@Override
	public final String getCategory() {
		if (this.category == null)
			return PrimaryCommandHandler.DEFAULT_CATEGORY;
		return this.category;
	}

	private ICommandHandler handler;

	@Override
	public void setCommandHandler(ICommandHandler handler) {
		if (this.handler == null)
			this.handler = handler;
	}

	@Override
	public final ICommandHandler getCommandHandler() {
		return this.handler;
	}

}
