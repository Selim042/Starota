package us.myles_selim.starota.commands.registry;

import java.util.ArrayList;
import java.util.List;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IRole;
import sx.blah.discord.handle.obj.Permissions;

public abstract class AbstractCommand implements ICommand {

	private final String name;
	private final String description;

	public AbstractCommand(String name) {
		this(name, null);
	}

	public AbstractCommand(String name, String description) {
		this.name = name;
		this.description = description;
	}

	public String getDescription() {
		return this.description;
	}

	public Permissions requiredPermission() {
		return null;
	}

	public IRole requiredRole(IGuild guild) {
		return null;
	}

	public final String getName() {
		return this.name;
	}

	public List<String> getAliases() {
		List<String> aliases = new ArrayList<>();
		aliases.add(this.name);
		return aliases;
	}

	public String getGeneralUsage() {
		return null;
	}

	public abstract void execute(String[] args, IMessage message, IGuild guild, IChannel channel);

	@Override
	public int compareTo(ICommand o) {
		if (this.category.equals(o.getCategory()))
			return name.compareTo(o.getName());
		return this.category.compareTo(o.getCategory());
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

}
