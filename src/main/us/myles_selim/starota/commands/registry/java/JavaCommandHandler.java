package us.myles_selim.starota.commands.registry.java;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.TextChannel;
import discord4j.core.object.util.Permission;
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
		DebugServer.addPermission(Permission.VIEW_CHANNEL);

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
			for (Permission p : cmd.getCommandPermission())
				DebugServer.addPermission(p);
		}
	}

	@Override
	public boolean executeCommand(String[] args, Message message, Guild guild, TextChannel channel)
			throws Exception {
		ICommand cmd = findCommand(guild, message, args[0]);
		if (cmd == null)
			return false;
		if (!ChannelCommandManager.isAllowedHere(StarotaServer.getServer(guild), cmd.getCategory(),
				channel)
				|| (cmd.requiredUsePermission() != null && guild != null
						&& !message.getAuthor().get().asMember(guild.getId()).block()
								.getBasePermissions().block().contains(cmd.requiredUsePermission())))
			return false;
		cmd.execute(args, message, guild, channel);
		return true;
	}

	@Override
	public List<ICommand> getAllCommands(Guild server) {
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
	public List<ICommand> getCommandsByCategory(Guild guild, String category) {
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
	public ICommand findCommand(Guild server, Message msg, String name) {
		for (JavaCommand c : COMMANDS) {
			if (!StarotaModule.isCategoryEnabled(StarotaServer.getServer(server), c.getCategory()))
				continue;
			if (c.hasRequiredRole(server, msg.getAuthor().get().asMember(server.getId()).block())
					&& c.isRequiredChannel(server, msg.getChannel().block())) {
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
	public List<String> getAllCategories(Guild server) {
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
