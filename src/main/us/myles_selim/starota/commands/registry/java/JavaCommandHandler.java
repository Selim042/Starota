package us.myles_selim.starota.commands.registry.java;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.Permissions;
import us.myles_selim.starota.commands.registry.CommandHelp;
import us.myles_selim.starota.commands.registry.CommandSetPrefix;
import us.myles_selim.starota.commands.registry.ICommand;
import us.myles_selim.starota.commands.registry.ICommandHandler;
import us.myles_selim.starota.commands.registry.PrimaryCommandHandler;
import us.myles_selim.starota.commands.registry.channel_management.ChannelCommandManager;
import us.myles_selim.starota.commands.registry.channel_management.CommandAddChannelWhitelist;
import us.myles_selim.starota.commands.registry.channel_management.CommandGetWhitelist;
import us.myles_selim.starota.commands.registry.channel_management.CommandRemoveChannelWhitelist;
import us.myles_selim.starota.debug_server.DebugServer;
import us.myles_selim.starota.modules.StarotaModule;
import us.myles_selim.starota.wrappers.StarotaServer;

public class JavaCommandHandler implements ICommandHandler {

	private final List<JavaCommand> COMMANDS = new CopyOnWriteArrayList<>();
	private final List<String> CATEGORIES = new CopyOnWriteArrayList<>();

	public void registerDefaultCommands() {
		DebugServer.addPermission(Permissions.READ_MESSAGES);

		registerCommand("Help", new CommandHelp());

		registerCommand("Commands", new CommandAddChannelWhitelist());
		registerCommand("Commands", new CommandGetWhitelist());
		registerCommand("Commands", new CommandSetPrefix());
		registerCommand("Commands", new CommandRemoveChannelWhitelist());
	}

	public void registerCommand(JavaCommand cmd) {
		registerCommand(PrimaryCommandHandler.DEFAULT_CATEGORY, cmd);
	}

	public void registerCommand(String category, JavaCommand cmd) {
		if (!COMMANDS.contains(cmd)) {
			cmd.setCategory(category);
			COMMANDS.add(cmd);
			cmd.setCommandHandler(this);
			if (!CATEGORIES.contains(category))
				CATEGORIES.add(category);
			for (Permissions p : cmd.getCommandPermissions())
				DebugServer.addPermission(p);
		}
	}

	@Override
	public boolean executeCommand(String[] args, IMessage message, IGuild guild, IChannel channel)
			throws Exception {
		ICommand cmd = findCommand(guild, message, args[0]);
		if (cmd == null)
			return false;
		if (!ChannelCommandManager.isAllowedHere(StarotaServer.getServer(guild), cmd.getCategory(),
				channel)
				|| (cmd.requiredUsePermission() != null && guild != null && !message.getAuthor()
						.getPermissionsForGuild(guild).contains(cmd.requiredUsePermission())))
			return false;
		cmd.execute(args, message, guild, channel);
		return true;
	}

	@Override
	public List<ICommand> getAllCommands(IGuild server) {
		List<ICommand> toRemove = new ArrayList<>();
		for (ICommand c : COMMANDS)
			if (!StarotaModule.isCategoryEnabled(StarotaServer.getServer(server), c.getCategory()))
				toRemove.add(c);
		List<ICommand> ret = new ArrayList<>(COMMANDS);
		ret.removeAll(toRemove);
		return Collections.unmodifiableList(ret);
	}

	public List<String> getAllCategories() {
		return Collections.unmodifiableList(CATEGORIES);
	}

	@Override
	public List<ICommand> getCommandsByCategory(IGuild guild, String category) {
		if (category == null)
			return getAllCommands(null);
		if (!StarotaModule.isCategoryEnabled(StarotaServer.getServer(guild), category))
			return Collections.emptyList();
		List<JavaCommand> cmds = new ArrayList<>();
		for (JavaCommand c : COMMANDS)
			if (category.equalsIgnoreCase(c.getCategory()))
				cmds.add(c);
		return Collections.unmodifiableList(cmds);
	}

	@Override
	public ICommand findCommand(IGuild server, IMessage msg, String name) {
		for (JavaCommand c : COMMANDS) {
			if (!StarotaModule.isCategoryEnabled(StarotaServer.getServer(server), c.getCategory()))
				continue;
			if (c.hasRequiredRole(server, msg.getAuthor())
					&& c.isRequiredChannel(server, msg.getChannel())) {
				if (c != null && c.getName() != null && c.getName().equalsIgnoreCase(name))
					return c;
				for (String a : c.getAliases())
					if (a != null && a.equalsIgnoreCase(name))
						return c;
			}
		}
		return null;
	}

	@Override
	public List<String> getAllCategories(IGuild server) {
		return getAllCategories();
	}

	// public static void main(String... argsM) {
	// String cmdS = "register \"Selim042 (Myles)\" 2";
	// List<String> argsL = new ArrayList<>();
	// String longArg = "";
	// for (String arg : cmdS.split(" ")) {
	// if (arg.startsWith("\"") && arg.endsWith("\""))
	// argsL.add(arg);
	// else if (arg.startsWith("\""))
	// longArg += arg;
	// else if (longArg != "" && arg.endsWith("\"")) {
	// String longArgFull = longArg + " " + arg;
	// argsL.add(longArgFull.substring(1, longArgFull.length() - 1));
	// longArg = "";
	// } else if (longArg != "")
	// longArg += " " + arg;
	// else
	// argsL.add(arg);
	// }
	// if (longArg != "")
	// for (String s : longArg.split(" "))
	// argsL.add(s);
	// String[] args = argsL.toArray(new String[0]);
	// for (String s : args)
	// System.out.println(s);
	// }

}
