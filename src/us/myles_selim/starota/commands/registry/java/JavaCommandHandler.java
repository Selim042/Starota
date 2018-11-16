package us.myles_selim.starota.commands.registry.java;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import us.myles_selim.starota.commands.registry.ICommandHandler;
import us.myles_selim.starota.commands.registry.PrimaryCommandHandler;
import us.myles_selim.starota.commands.registry.channel_management.ChannelCommandManager;

public class JavaCommandHandler implements ICommandHandler<JavaCommand> {

	public static final JavaCommandHandler INSTANCE;

	private static final List<JavaCommand> COMMANDS = new CopyOnWriteArrayList<>();
	private static final List<String> CATEGORIES = new CopyOnWriteArrayList<>();

	static {
		INSTANCE = new JavaCommandHandler();
		PrimaryCommandHandler.registerCommandHandler(INSTANCE);
	}

	public static void registerCommand(JavaCommand cmd) {
		registerCommand(PrimaryCommandHandler.DEFAULT_CATEGORY, cmd);
	}

	public static void registerCommand(String category, JavaCommand cmd) {
		if (!COMMANDS.contains(cmd)) {
			cmd.setCategory(category);
			COMMANDS.add(cmd);
			if (!CATEGORIES.contains(category))
				CATEGORIES.add(category);
		}
	}

	@Override
	public boolean executeCommand(String[] args, IMessage message, IGuild guild, IChannel channel) {
		JavaCommand cmd = findCommand(args[0]);
		if (cmd == null)
			return false;
		if (!ChannelCommandManager.isAllowedHere(guild, cmd.getCategory(), channel)
				|| (cmd.requiredPermission() != null && guild != null && !message.getAuthor()
						.getPermissionsForGuild(guild).contains(cmd.requiredPermission())))
			return false;
		cmd.execute(args, message, guild, channel);
		return true;
	}

	@Override
	public List<JavaCommand> getAllCommands(IGuild server) {
		return Collections.unmodifiableList(COMMANDS);
	}

	public static List<String> getAllCategories() {
		return Collections.unmodifiableList(CATEGORIES);
	}

	public List<JavaCommand> getCommandsByCategory(String category) {
		if (category == null)
			return getAllCommands(null);
		List<JavaCommand> cmds = new ArrayList<>();
		for (JavaCommand c : COMMANDS)
			if (category.equalsIgnoreCase(c.getCategory()))
				cmds.add(c);
		return Collections.unmodifiableList(cmds);
	}

	public static JavaCommand findCommand(String name) {
		for (JavaCommand c : COMMANDS) {
			if (c != null && c.getName() != null && c.getName().equalsIgnoreCase(name))
				return c;
			for (String a : c.getAliases())
				if (a != null && a.equalsIgnoreCase(name))
					return c;
		}
		return null;
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
