package us.myles_selim.starota.commands.registry;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.impl.obj.ReactionEmoji;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.RequestBuffer;
import us.myles_selim.starota.ServerOptions;
import us.myles_selim.starota.Starota;

public class PrimaryCommandHandler {

	private static final List<ICommandHandler> COMMAND_HANDLERS = new CopyOnWriteArrayList<>();
	private static String DEFAULT_PREFIX = ".";

	public static final String PREFIX_KEY = "cmd_prefix";
	public static final String DEFAULT_CATEGORY = "Uncategorized";

	public static String getPrefix(IGuild server) {
		if (ServerOptions.hasKey(server, PREFIX_KEY))
			return String.valueOf(ServerOptions.getValue(server, PREFIX_KEY));
		return DEFAULT_PREFIX;
	}

	public static void setPrefix(IGuild server, String prefix) {
		ServerOptions.setValue(server, PREFIX_KEY, prefix);
	}

	public static void registerCommandHandler(ICommandHandler handler) {
		if (!COMMAND_HANDLERS.contains(handler))
			COMMAND_HANDLERS.add(handler);
	}

	@EventSubscriber
	public void onMessageEvent(MessageReceivedEvent event) {
		IGuild server = event.getGuild();
		IChannel channel = event.getChannel();
		IMessage message = event.getMessage();
		String cmdS = message.getContent();
		String prefix = getPrefix(server);
		if (!cmdS.startsWith(prefix))
			return;
		String[] args = getArgs(message, server);
		RequestBuffer.request(() -> {
			channel.setTypingStatus(true);
		});
		if (Starota.DEBUG)
			message.addReaction(ReactionEmoji.of("�?"));
		try {
			for (ICommandHandler h : COMMAND_HANDLERS)
				if (h.executeCommand(args, message, server, channel))
					continue;
		} catch (Throwable e) {
			RequestBuffer.request(() -> {
				message.reply("There was an error encountered while executing your command: "
						+ e.getStackTrace()[0] + e.getLocalizedMessage());
			});
			System.err.println("executed command: " + message.getContent());
			e.printStackTrace();
		}
		RequestBuffer.request(() -> {
			channel.setTypingStatus(false);
		});
	}

	public static List<ICommand> getCommandsByCategory(IGuild server, String category) {
		if (category == null || category.isEmpty())
			return getAllCommands(server);
		List<ICommand> ret = new ArrayList<>();
		for (ICommandHandler h : COMMAND_HANDLERS)
			for (ICommand c : h.getAllCommands(server))
				if (category.equalsIgnoreCase(c.getCategory()))
					ret.add(c);
		return ret;
	}

	public static List<ICommand> getAllCommands(IGuild server) {
		List<ICommand> ret = new ArrayList<>();
		for (ICommandHandler h : COMMAND_HANDLERS)
			ret.addAll(h.getAllCommands(server));
		return ret;
	}

	private static String[] getArgs(IMessage message, IGuild guild) {
		String cmdS = message.getContent();
		String prefix = getPrefix(guild);
		if (!cmdS.startsWith(prefix))
			return new String[0];
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
		return argsL.toArray(new String[0]);
	}

}
