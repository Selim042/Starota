package us.myles_selim.starota.commands.registry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import sx.blah.discord.handle.impl.obj.ReactionEmoji;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.RequestBuffer;
import us.myles_selim.starota.ServerOptions;
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.commands.registry.channel_management.ChannelCommandManager;
import us.myles_selim.starota.commands.registry.channel_management.CommandAddChannelWhitelist;
import us.myles_selim.starota.commands.registry.channel_management.CommandGetWhitelist;
import us.myles_selim.starota.commands.registry.channel_management.CommandRemoveChannelWhitelist;

public class CommandRegistry {

	private static final List<Command> COMMANDS = new CopyOnWriteArrayList<>();
	private static final List<String> CATEGORIES = new CopyOnWriteArrayList<>();
	private static String DEFAULT_PREFIX = ".";

	public static final String PREFIX_KEY = "cmd_prefix";
	public static final String DEFAULT_CATEGORY = "Uncategorized";

	static {
		registerCommand("Help", new CommandHelp());

		registerCommand("Commands", new CommandAddChannelWhitelist());
		registerCommand("Commands", new CommandGetWhitelist());
		registerCommand("Commands", new CommandSetPrefix());
		registerCommand("Commands", new CommandRemoveChannelWhitelist());
	}

	public static String getPrefix(IGuild server) {
		if (ServerOptions.hasKey(server, PREFIX_KEY))
			return String.valueOf(ServerOptions.getValue(server, PREFIX_KEY));
		return DEFAULT_PREFIX;
	}

	public static void setPrefix(IGuild server, String prefix) {
		ServerOptions.setValue(server, PREFIX_KEY, prefix);
	}

	public static void registerCommand(Command cmd) {
		registerCommand(DEFAULT_CATEGORY, cmd);
	}

	public static void registerCommand(String category, Command cmd) {
		if (!COMMANDS.contains(cmd)) {
			cmd.setCategory(category);
			COMMANDS.add(cmd);
			if (!CATEGORIES.contains(category))
				CATEGORIES.add(category);
		}
	}

	public static boolean executeCommand(IMessage message, IGuild guild, IChannel channel) {
		String cmdS = message.getContent();
		String prefix = getPrefix(guild);
		if (!cmdS.startsWith(prefix))
			return false;
		cmdS = cmdS.substring(prefix.length());
		List<String> argsL = new ArrayList<>();
		String longArg = "";
		for (String arg : cmdS.split(" ")) {
			if (arg.startsWith("\"") && arg.endsWith("\""))
				argsL.add(arg);
			else if (arg.startsWith("\""))
				longArg += arg;
			else if (longArg != "" && arg.endsWith("\"")) {
				String longArgFull = longArg + " " + arg;
				argsL.add(longArgFull.substring(1, longArgFull.length() - 1));
				longArg = "";
			} else if (longArg != "")
				longArg += " " + arg;
			else
				argsL.add(arg);
		}
		if (longArg != "")
			for (String s : longArg.split(" "))
				argsL.add(s);
		String[] args = argsL.toArray(new String[0]);
		// for (String s : args)
		// System.out.println(s);
		Command cmd = findCommand(args[0]);
		if (cmd == null)
			return false;
		if (!ChannelCommandManager.isAllowedHere(guild, cmd.getCategory(), channel)
				|| (cmd.requiredPermission() != null && guild != null && !message.getAuthor()
						.getPermissionsForGuild(guild).contains(cmd.requiredPermission())))
			return false;
		RequestBuffer.request(() -> {
			channel.setTypingStatus(true);
		});
		if (Starota.DEBUG)
			message.addReaction(ReactionEmoji.of("�?"));
		try {
			cmd.execute(args, message, guild, channel);
		} catch (Throwable e) {
			RequestBuffer.request(() -> {
				message.reply("There was an error encountered while executing your command: "
						+ e.getStackTrace()[0] + e.getLocalizedMessage());
			});
			System.err.println("executed command: " + cmdS);
			e.printStackTrace();
		}
		RequestBuffer.request(() -> {
			channel.setTypingStatus(false);
		});
		return true;
	}

	protected static List<Command> getAllCommands() {
		return Collections.unmodifiableList(COMMANDS);
	}

	public static List<String> getAllCategories() {
		return Collections.unmodifiableList(CATEGORIES);
	}

	protected static List<Command> getCommandsByCategory(String category) {
		if (category == null)
			return getAllCommands();
		List<Command> cmds = new ArrayList<>();
		for (Command c : COMMANDS)
			if (category.equalsIgnoreCase(c.getCategory()))
				cmds.add(c);
		return Collections.unmodifiableList(cmds);
	}

	public static Command findCommand(String name) {
		for (Command c : COMMANDS) {
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
