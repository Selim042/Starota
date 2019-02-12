package us.myles_selim.starota.lua.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IRole;
import sx.blah.discord.handle.obj.Permissions;
import us.myles_selim.starota.Starota.BaseModules;
import us.myles_selim.starota.commands.registry.ICommand;
import us.myles_selim.starota.commands.registry.ICommandHandler;
import us.myles_selim.starota.lua.ScriptManager;
import us.myles_selim.starota.modules.StarotaModule;
import us.myles_selim.starota.wrappers.StarotaServer;

public class LuaCommandHandler implements ICommandHandler {

	private static final String CATEGORY = "Lua";

	@Override
	public boolean executeCommand(String[] args, IMessage message, IGuild guild, IChannel channel)
			throws Exception {
		if (args.length < 1)
			return false;
		ICommand cmd = findCommand(guild, args[0]);
		if (cmd == null)
			return false;
		cmd.execute(args, message, guild, channel);
		return true;
	}

	@Override
	public List<ICommand> getAllCommands(IGuild guild) {
		StarotaServer server = StarotaServer.getServer(guild);
		if (!StarotaModule.isModuleEnabled(server, BaseModules.LUA))
			return Collections.emptyList();
		List<ICommand> ret = new ArrayList<>();
		for (String n : ScriptManager.getCommandScripts(server))
			ret.add(new LuaCommand(server, n));
		return Collections.unmodifiableList(ret);
	}

	@Override
	public List<ICommand> getCommandsByCategory(IGuild server, String category) {
		return getAllCommands(server);
	}

	@Override
	public ICommand findCommand(IGuild server, String name) {
		for (ICommand c : getAllCommands(server))
			if (c.getName().equalsIgnoreCase(name))
				return c;
		return null;
	}

	@Override
	public List<String> getAllCategories(IGuild server) {
		return Collections.singletonList(CATEGORY);
	}

	private class LuaCommand implements ICommand {

		private final StarotaServer server;
		private final String name;

		public LuaCommand(StarotaServer server, String name) {
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

		@Override
		public void setCommandHandler(ICommandHandler handler) {}

		@Override
		public ICommandHandler getCommandHandler() {
			return LuaCommandHandler.this;
		}
	}

}
