package us.myles_selim.starota.commands.registry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.squiddev.cobalt.LuaError;

import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.impl.obj.ReactionEmoji;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.Permissions;
import sx.blah.discord.util.EmbedBuilder;
import sx.blah.discord.util.RequestBuffer;
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.commands.registry.channel_management.ChannelCommandManager;
import us.myles_selim.starota.modules.StarotaModule;
import us.myles_selim.starota.wrappers.StarotaServer;

public class PrimaryCommandHandler {

	private static final List<ICommandHandler> COMMAND_HANDLERS = new CopyOnWriteArrayList<>();
	private static String DEFAULT_PREFIX = ".";

	public static final String PREFIX_KEY = "cmd_prefix";
	public static final String DEFAULT_CATEGORY = "Uncategorized";
	public static final int DEFAULT_SUGGESTION_COUNT = 10;

	public static String getPrefix(IGuild guild) {
		StarotaServer server = StarotaServer.getServer(guild);
		if (server.hasKey(PREFIX_KEY))
			return String.valueOf(server.getValue(PREFIX_KEY));
		return DEFAULT_PREFIX;
	}

	public static void setPrefix(IGuild guild, String prefix) {
		StarotaServer server = StarotaServer.getServer(guild);
		server.setValue(PREFIX_KEY, prefix);
	}

	public static void registerCommandHandler(ICommandHandler handler) {
		if (!COMMAND_HANDLERS.contains(handler))
			COMMAND_HANDLERS.add(handler);
	}

	@EventSubscriber
	public void onMessageEvent(MessageReceivedEvent event) {
		if (event.getAuthor().isBot())
			return;
		IGuild server = event.getGuild();
		if (server == null)
			return;
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
		boolean cmdFound = false;
		try {
			for (ICommandHandler h : COMMAND_HANDLERS)
				if (h.executeCommand(args, message, server, channel)) {
					cmdFound = true;
					continue;
				}
		} catch (Throwable e) {
			cmdFound = true;
			if (channel.getModifiedPermissions(Starota.getOurUser()).contains(Permissions.SEND_MESSAGES))
				RequestBuffer.request(() -> {
					message.reply("There was an error encountered while executing your command: "
							+ e.getClass().getName() + ": " + e.getLocalizedMessage() + "\n"
							+ e.getStackTrace()[0]);
				});
			System.err.println("executed command: " + message.getContent());
			e.printStackTrace();
			if (!(e instanceof LuaError))
				Starota.submitError(" on server " + server.getName(), e);
		}
		if (!cmdFound) {
			EmbedBuilder builder = new EmbedBuilder();
			builder.withTitle("Did you mean...?");
			for (ICommand cmd : getSuggestions(server, channel, args[0], 5)) {
				if (cmd != null) {
					String desciption = cmd.getDescription();
					builder.appendDesc("- " + prefix + cmd.getName()
							+ (desciption == null ? "" : ", _" + cmd.getDescription() + "_") + "\n");
				}
			}
			channel.sendMessage(builder.build());
		}
		RequestBuffer.request(() -> {
			channel.setTypingStatus(false);
		});
	}

	public static List<ICommand> getCommandsByCategory(IGuild server, String category) {
		if (category == null || category.isEmpty())
			return getAllCommands(server);
		if (!StarotaModule.isCategoryEnabled(StarotaServer.getServer(server), category))
			return Collections.emptyList();
		List<ICommand> ret = new ArrayList<>();
		for (ICommandHandler h : COMMAND_HANDLERS)
			for (ICommand c : h.getCommandsByCategory(server, category))
				if (category.equalsIgnoreCase(c.getCategory()))
					ret.add(c);
		return Collections.unmodifiableList(ret);
	}

	public static List<ICommand> getAllCommands(IGuild server) {
		List<ICommand> ret = new ArrayList<>();
		for (ICommandHandler h : COMMAND_HANDLERS)
			ret.addAll(h.getAllCommands(server));
		return ret;
	}

	public static ICommand findCommand(IGuild server, String name) {
		for (ICommandHandler h : COMMAND_HANDLERS) {
			ICommand c = h.findCommand(server, name);
			if (c != null)
				return c;
		}
		return null;
	}

	public static ICommand[] getSuggestions(IGuild server, String input) {
		return getSuggestions(server, null, input, DEFAULT_SUGGESTION_COUNT);
	}

	public static ICommand[] getSuggestions(IGuild server, String input, int count) {
		return getSuggestions(server, null, input, count);
	}

	public static ICommand[] getSuggestions(IGuild server, IChannel channel, String input) {
		return getSuggestions(server, channel, input, DEFAULT_SUGGESTION_COUNT);
	}

	public static ICommand[] getSuggestions(IGuild server, IChannel channel, String input, int count) {
		List<DistancedCommand> suggestions = new ArrayList<>();
		for (ICommandHandler ch : COMMAND_HANDLERS) {
			for (ICommand cmd : ch.getAllCommands(server)) {
				if (channel != null && !ChannelCommandManager
						.isAllowedHere(StarotaServer.getServer(server), cmd.getCategory(), channel))
					continue;
				for (String a : cmd.getAliases()) {
					DistancedCommand dc = new DistancedCommand(calculateDistance(a, input), cmd);
					if (!suggestions.contains(dc))
						suggestions.add(dc);
				}
			}
		}
		suggestions.sort(null);
		ICommand[] out = new ICommand[count];
		for (int i = 0; i < count; i++)
			out[i] = suggestions.get(i).cmd;
		return out;
	}

	private static class DistancedCommand implements Comparable<DistancedCommand> {

		public int dist;
		public ICommand cmd;

		public DistancedCommand(int dist, ICommand cmd) {
			this.dist = dist;
			this.cmd = cmd;
		}

		@Override
		public int compareTo(DistancedCommand o) {
			return Integer.compare(dist, o.dist);
		}

		@Override
		public boolean equals(Object o) {
			if (!(o instanceof DistancedCommand))
				return false;
			return cmd.equals(((DistancedCommand) o).cmd);
		}
	}

	private static int calculateDistance(String x, String y) {
		int[][] dp = new int[x.length() + 1][y.length() + 1];
		for (int i = 0; i <= x.length(); i++) {
			for (int j = 0; j <= y.length(); j++) {
				if (i == 0) {
					dp[i][j] = j;
				} else if (j == 0) {
					dp[i][j] = i;
				} else {
					dp[i][j] = min(
							dp[i - 1][j - 1] + costOfSubstitution(x.charAt(i - 1), y.charAt(j - 1)),
							dp[i - 1][j] + 1, dp[i][j - 1] + 1);
				}
			}
		}
		return dp[x.length()][y.length()];
	}

	private static int costOfSubstitution(char a, char b) {
		return a == b ? 0 : 1;
	}

	private static int min(int... numbers) {
		return Arrays.stream(numbers).min().orElse(Integer.MAX_VALUE);
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
