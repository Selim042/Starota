package us.myles_selim.starota.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.MessageChannel;
import discord4j.core.object.util.Permission;
import discord4j.core.object.util.PermissionSet;
import us.myles_selim.starota.commands.registry.CommandException;
import us.myles_selim.starota.enums.EnumTeam;
import us.myles_selim.starota.misc.utils.EventListener;
import us.myles_selim.starota.misc.utils.StarotaConstants;
import us.myles_selim.starota.modules.BaseModules;
import us.myles_selim.starota.modules.StarotaModule;
import us.myles_selim.starota.permissions.holders.PermissionHolder;
import us.myles_selim.starota.wrappers.StarotaServer;

public class CommandCatcherCupAddPoints extends BotCommand<StarotaServer> {

	public static final String HOUSE_CUP_DATA_KEY = "house_cup";

	private static final String SUCCESS_TEMPLATE = "%,d points for %s!";

	public CommandCatcherCupAddPoints() {
		super("addPoints", "Manually adds points for the specified team");
	}

	@Override
	public String getGeneralUsage() {
		return "[clear/team] [points]";
	}

	@Override
	public PermissionSet getCommandPermission() {
		return PermissionSet.of(Permission.SEND_MESSAGES, Permission.EMBED_LINKS);
	}

	@Override
	public Permission requiredUsePermission() {
		return Permission.ADMINISTRATOR;
	}

	@Override
	public void execute(String[] args, Message message, StarotaServer server, MessageChannel channel)
			throws CommandException {
		if ((args.length == 2 && !args[1].equalsIgnoreCase("clear")) && args.length < 3) {
			this.sendUsage(server.getPrefix(), channel);
			return;
		}
		if (args[1].equalsIgnoreCase("clear")) {
			server.clearTeamPoints();
			channel.createMessage("The Catcher Cup board has been wiped clean!").block();
			return;
		}
		EnumTeam team = EnumTeam.getTeam(args[1]);
		if (team == null) {
			channel.createMessage("Invalid team \"" + args[1] + "\"").block();
			return;
		}
		int points;
		try {
			points = Integer.parseInt(args[2]);
		} catch (NumberFormatException e) {
			channel.createMessage("Invalid number \"" + args[2] + "\"").block();
			return;
		}
		server.addTeamPoints(team, points);
		channel.createMessage(String.format(SUCCESS_TEMPLATE, points, team.getName())).block();
	}

	private static final Pattern NUMBER_PATTERN = Pattern.compile("(-?\\d+)");
	private static final Pattern TEAM_PATTERN = Pattern.compile("(?i)(instinct|mystic|valor)");

	public class EasyEntryHandler implements EventListener {

		@EventSubscriber
		public void onMessage(MessageCreateEvent event) {
			if (!event.getGuildId().isPresent())
				return;
			StarotaServer server = StarotaServer.getServer(event.getGuildId().get());
			if (!((boolean) server.getSetting(StarotaConstants.Settings.EASY_HOUSE_CUP_ENTRY)))
				return;
			if (StarotaModule.isModuleEnabled(server, BaseModules.CATCHER_CUP))
				return;
			Message message = event.getMessage();
			MessageChannel channel = message.getChannel().block();
			Member author = message.getAuthorAsMember().block();
			if (author == null || author.isBot())
				return;
			if (!PermissionHolder.getNewHolderMember(server.getDiscordGuild(), author)
					.hasPermission(channel, CommandCatcherCupAddPoints.this.getStarotaPermission()))
				return;
			String messageS = message.getContent().orElse("");
			messageS = messageS.replaceAll("<([@#:]|(a?:.+?:))\\d{18}>", "");
			if (isMessageValid(messageS)) {
				int points = getNumPoints(messageS);
				EnumTeam team = getTeam(messageS);
				server.addTeamPoints(team, points);
				channel.createMessage(String.format(SUCCESS_TEMPLATE, points, team.getName())).block();
			}
		}

		private boolean isMessageValid(String message) {
			if (!message.toLowerCase().contains("points"))
				return false;
			Matcher teamMatcher = TEAM_PATTERN.matcher(message);
			Matcher numMatcher = NUMBER_PATTERN.matcher(message);
			if (!teamMatcher.find() || teamMatcher.groupCount() != 1)
				return false;
			if (!numMatcher.find() || numMatcher.groupCount() != 1)
				return false;
			return true;
		}

		private int getNumPoints(String message) {
			Matcher matcher = NUMBER_PATTERN.matcher(message);
			matcher.find();
			String numS = matcher.group();
			return Integer.parseInt(numS);
		}

		private EnumTeam getTeam(String message) {
			Matcher matcher = TEAM_PATTERN.matcher(message);
			matcher.find();
			String teamS = matcher.group();
			return EnumTeam.getTeam(teamS);
		}

	}

}
