package us.myles_selim.starota.commands.registry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

import org.squiddev.cobalt.LuaError;

import discord4j.core.DiscordClient;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Channel;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.GuildChannel;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.MessageChannel;
import discord4j.core.object.entity.PrivateChannel;
import discord4j.core.object.entity.User;
import discord4j.core.object.reaction.ReactionEmoji;
import discord4j.core.object.util.Permission;
import discord4j.core.object.util.PermissionSet;
import discord4j.core.spec.EmbedCreateSpec;
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.commands.registry.channel_management.ChannelCommandManager;
import us.myles_selim.starota.misc.utils.EmbedBuilder;
import us.myles_selim.starota.misc.utils.EventListener;
import us.myles_selim.starota.misc.utils.MiscUtils;
import us.myles_selim.starota.modules.StarotaModule;
import us.myles_selim.starota.wrappers.StarotaServer;

public class PrimaryCommandHandler implements EventListener {

	public static final String DEFAULT_PREFIX = ".";
	public static final String PREFIX_KEY = "cmd_prefix";
	public static final String DEFAULT_CATEGORY = "Uncategorized";
	public static final int DEFAULT_SUGGESTION_COUNT = 10;

	private final List<ICommandHandler> COMMAND_HANDLERS = new CopyOnWriteArrayList<>();
	private final IShouldExecuteCallback shouldExecute;
	private final DiscordClient client;

	public PrimaryCommandHandler(DiscordClient client) {
		this(client, (Channel ch) -> !(ch instanceof PrivateChannel));
	}

	public PrimaryCommandHandler(DiscordClient client, IShouldExecuteCallback callback) {
		this.shouldExecute = callback;
		this.client = client;
	}

	public static String getPrefix(Guild guild) {
		if (guild == null)
			return DEFAULT_PREFIX;
		return StarotaServer.getServer(guild).getPrefix();
	}

	public static void setPrefix(Guild guild, String prefix) {
		if (guild == null)
			return;
		StarotaServer server = StarotaServer.getServer(guild);
		server.setDataValue(PREFIX_KEY, prefix);
	}

	public <H extends ICommandHandler> H registerCommandHandler(H handler) {
		if (handler == null)
			throw new IllegalArgumentException("handler cannot be null");
		if (!COMMAND_HANDLERS.contains(handler))
			COMMAND_HANDLERS.add(handler);
		return handler;
	}

	@EventSubscriber
	public void onMessageEvent(MessageCreateEvent event) {
		User author = event.getMessage().getAuthor().get();
		if (author == null || author.isBot() || !Starota.FULLY_STARTED)
			return;
		Guild guild = event.getGuild().block();
		// if (guild == null)
		// return;
		MessageChannel channel = event.getMessage().getChannel().block();
		if (!shouldExecute.shouldExecute(channel))
			return;
		Message message = event.getMessage();
		String cmdS = message.getContent().get();
		String prefix = getPrefix(guild);
		if (!cmdS.startsWith(prefix)
				&& !cmdS.startsWith(client.getSelf().block().getMention().replaceAll("@!", "@") + " "))
			return;
		String[] args = getArgs(message, guild);
		// User ourUser = Starota.getOurUser();
		// if (!channel.t.getTypingStatus()
		// &&
		// channel.getModifiedPermission(ourUser).contains(Permission.SEND_MESSAGES))
		// RequestBuffer.request(() -> channel.setTypingStatus(true));
		if (Starota.DEBUG)
			message.addReaction(ReactionEmoji.unicode("�?"));
		boolean cmdFound = false;
		try {
			PermissionSet hasPerms;
			if (channel instanceof GuildChannel)
				hasPerms = ((GuildChannel) channel).getEffectivePermissions(client.getSelfId().get())
						.block();
			else
				hasPerms = PermissionSet.of(Permission.SEND_MESSAGES);
			if (!hasPerms.contains(Permission.SEND_MESSAGES))
				return;
			for (ICommandHandler h : COMMAND_HANDLERS) {
				ICommand cmd = h.findCommand(guild, message, args[0]);
				if (cmd == null
						|| !ChannelCommandManager.isAllowedHere(StarotaServer.getServer(guild),
								cmd.getCategory(), channel)
						|| (cmd.requiredUsePermission() != null && guild != null
								&& !message.getAuthorAsMember().block().getBasePermissions().block()
										.contains(cmd.requiredUsePermission())))
					continue;
				PermissionSet reqPerms = cmd.getCommandPermission();
				if (!hasPerms.containsAll(reqPerms)) {
					reqPerms.removeAll(hasPerms);
					channel.createEmbed(getPermissionError(reqPerms));
					cmdFound = true;
					continue;
				}
				Starota.EXECUTOR.execute(new Runnable() {

					@Override
					public void run() {
						try {
							cmd.execute(args, message, guild, channel);
						} catch (Exception e) {
							handleException(e, guild, channel, message);
						}
					}
				});
				cmdFound = true;
				continue;
				// if (h.executeCommand(args, message, server, channel)) {
				// cmdFound = true;
				// continue;
				// }
			}
		} catch (Throwable e) {
			cmdFound = true;
			handleException(e, guild, channel, message);
		}
		if (!cmdFound) {
			EmbedBuilder builder = new EmbedBuilder();
			builder.withTitle("Did you mean...?");
			for (ICommand cmd : getSuggestions(guild, message, args[0], 5)) {
				if (cmd == null
						|| !ChannelCommandManager.isAllowedHere(StarotaServer.getServer(guild),
								cmd.getCategory(), channel)
						|| (cmd.requiredUsePermission() != null && guild != null
								&& !message.getAuthorAsMember().block().getBasePermissions().block()
										.contains(cmd.requiredUsePermission())))
					continue;
				String desciption = cmd.getDescription();
				builder.appendDesc("- " + prefix + cmd.getName()
						+ (desciption == null ? "" : ", _" + cmd.getDescription() + "_") + "\n");
			}
			channel.createEmbed(builder.build()).block();
		}
		// if (channel.getTypingStatus()
		// &&
		// channel.getModifiedPermission(ourUser).contains(Permission.SEND_MESSAGES))
		// RequestBuffer.request(() -> channel.setTypingStatus(false));
	}

	private Consumer<? super EmbedCreateSpec> getPermissionError(PermissionSet reqPerms) {
		EmbedBuilder builder = new EmbedBuilder();
		builder.withTitle("This command cannot be used here");
		builder.withDesc(
				"This command requires the following missing permissions in the channel:\n```\n");
		for (Permission p : reqPerms)
			builder.appendDesc(" - " + p + "\n");
		builder.appendDesc("\n```\nPlease contact a server moderator to fix this.");
		return builder.build();
	}

	private void handleException(Throwable th, Guild guild, MessageChannel channel, Message message) {
		// if
		// (channel.getModifiedPermission(Starota.getOurUser()).contains(Permission.SEND_MESSAGES))
		// RequestBuffer.request(() -> {
		channel.createMessage(message.getAuthor().get().getMention()
				+ ", There was an error encountered while executing your command: "
				+ th.getClass().getName() + ": " + th.getLocalizedMessage() + "\n"
				+ th.getStackTrace()[0]);
		// });
		System.err.println("executed command: " + message.getContent());
		th.printStackTrace();
		if (!(th instanceof LuaError))
			Starota.submitError(" on server " + guild.getName(), th);
	}

	public List<ICommand> getCommandsByCategory(Guild guild, String category) {
		if (category == null || category.isEmpty())
			return Collections.emptyList();
		if (!StarotaModule.isCategoryEnabled(StarotaServer.getServer(guild), category))
			return Collections.emptyList();
		List<ICommand> ret = new ArrayList<>();
		for (ICommandHandler h : COMMAND_HANDLERS)
			for (ICommand c : h.getCommandsByCategory(guild, category))
				if (category.equalsIgnoreCase(c.getCategory()))
					ret.add(c);
		return Collections.unmodifiableList(ret);
	}

	public List<String> getAllCategories(Guild guild) {
		List<String> ret = new ArrayList<>();
		for (ICommandHandler h : COMMAND_HANDLERS)
			ret.addAll(h.getAllCategories(guild));
		return ret;
	}

	public List<ICommand> getAllCommands(Guild guild) {
		List<ICommand> ret = new ArrayList<>();
		for (ICommandHandler h : COMMAND_HANDLERS)
			ret.addAll(h.getAllCommands(guild));
		return ret;
	}

	public ICommand findCommand(Guild guild, Message msg, String name) {
		for (ICommandHandler h : COMMAND_HANDLERS) {
			ICommand c = h.findCommand(guild, msg, name);
			if (c != null)
				return c;
		}
		return null;
	}

	public ICommand[] getSuggestions(Guild guild, String input) {
		return getSuggestions(guild, null, input, DEFAULT_SUGGESTION_COUNT);
	}

	public ICommand[] getSuggestions(Guild guild, String input, int count) {
		return getSuggestions(guild, null, input, count);
	}

	public ICommand[] getSuggestions(Guild guild, Message message, String input) {
		return getSuggestions(guild, message, input, DEFAULT_SUGGESTION_COUNT);
	}

	public ICommand[] getSuggestions(Guild guild, Message message, String input, int count) {
		List<DistancedCommand> suggestions = new ArrayList<>();
		MessageChannel channel = message.getChannel().block();
		for (ICommandHandler ch : COMMAND_HANDLERS) {
			for (ICommand cmd : ch.getAllCommands(guild)) {
				if (channel != null && !ChannelCommandManager
						.isAllowedHere(StarotaServer.getServer(guild), cmd.getCategory(), channel))
					continue;
				if (!cmd.hasRequiredRole(guild, message.getAuthorAsMember().block())
						|| !cmd.isRequiredChannel(guild, message.getChannel().block()))
					continue;
				for (String a : cmd.getAliases()) {
					DistancedCommand dc = new DistancedCommand(calculateDistance(a, input), cmd);
					suggestions.add(dc);
				}
			}
		}
		suggestions.sort(null);
		ICommand[] out = new ICommand[count];
		int index = 0;
		for (DistancedCommand cmd : suggestions) {
			if (index >= count)
				break;
			ICommand sug = cmd.cmd;
			if (!MiscUtils.arrContains(out, sug))
				out[index++] = sug;
		}
		return out;
	}

	private String[] getArgs(Message message, Guild guild) {
		String cmdS = message.getContent().get();
		String prefix = getPrefix(guild);
		boolean mentionPrefix = cmdS
				.startsWith(client.getSelf().block().getMention().replaceAll("@!", "@") + " ");
		if (!mentionPrefix && !cmdS.startsWith(prefix))
			return new String[0];
		cmdS = cmdS.substring(prefix.length());
		List<String> argsL = new ArrayList<>();
		String longArg = "";
		for (String arg : cmdS.split(" ")) {
			if (arg.startsWith("\"") && arg.endsWith("\""))
				argsL.add(arg);
			else if (arg.startsWith("\""))
				longArg += arg;
			else if (!longArg.isEmpty() && arg.endsWith("\"")) {
				String longArgFull = longArg + " " + arg;
				argsL.add(longArgFull.substring(1, longArgFull.length() - 1));
				longArg = "";
			} else if (!longArg.isEmpty())
				longArg += " " + arg;
			else
				argsL.add(arg);
		}
		if (!longArg.isEmpty())
			for (String s : longArg.split(" "))
				argsL.add(s);
		if (mentionPrefix && !argsL.isEmpty())
			argsL.remove(0);
		return argsL.toArray(new String[0]);
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

	public static interface IShouldExecuteCallback {

		public boolean shouldExecute(Channel channel);

	}

}
