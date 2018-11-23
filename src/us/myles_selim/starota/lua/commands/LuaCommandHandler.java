package us.myles_selim.starota.lua.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IRole;
import sx.blah.discord.handle.obj.Permissions;
import us.myles_selim.starota.commands.registry.ICommand;
import us.myles_selim.starota.commands.registry.ICommandHandler;
import us.myles_selim.starota.lua.ScriptManager;

public class LuaCommandHandler implements ICommandHandler {

	@Override
	public boolean executeCommand(String[] args, IMessage message, IGuild guild, IChannel channel) throws Exception {
		if (args.length < 1)
			return false;
		ICommand cmd = findCommand(guild, args[0]);
		if (cmd == null)
			return false;
		cmd.execute(args, message, guild, channel);
		return true;
	}

	@Override
	public List<ICommand> getAllCommands(IGuild server) {
		List<ICommand> ret = new ArrayList<>();
		for (String n : ScriptManager.getCommandScripts(server))
			ret.add(new LuaCommand(server, n));
		return Collections.unmodifiableList(ret);
	}

	@Override
	public ICommand findCommand(IGuild server, String name) {
		for (ICommand c : getAllCommands(server))
			if (c.getName().equalsIgnoreCase(name))
				return c;
		return null;
	}

	private static class LuaCommand implements ICommand {

		private static final String CATEGORY = "Lua";

		private final IGuild server;
		private final String name;

		public LuaCommand(IGuild server, String name) {
			this.server = server;
			this.name = name;
		}

		@Override
		public void execute(String[] args, IMessage message, IGuild guild, IChannel channel)
				throws Exception {
			ScriptManager.executeCommandScript(this.server, this.name, message, channel, args);
		}

		@Override
		public int compareTo(ICommand o) {
			if (CATEGORY.equals(o.getCategory()))
				return name.compareTo(o.getName());
			return CATEGORY.compareTo(o.getCategory());
		}

		@Override
		public void setCategory(String category) {}

		@Override
		public String getCategory() {
			return CATEGORY;
		}

		@Override
		public String getDescription() {
			return null;
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
		public String getName() {
			return this.name;
		}

		@Override
		public List<String> getAliases() {
			return Collections.singletonList(this.name);
		}

		@Override
		public String getGeneralUsage() {
			return null;
		}
	}

}
