package us.myles_selim.starota.commands;

import discord4j.command.util.CommandException;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.MessageChannel;
import discord4j.core.object.util.Permission;
import discord4j.core.object.util.PermissionSet;
import discord4j.core.object.util.Snowflake;
import us.myles_selim.starota.misc.utils.EmbedBuilder;
import us.myles_selim.starota.wrappers.StarotaServer;

public class CommandVote extends BotCommand<StarotaServer> {

	private String botName;
	private Snowflake botId;

	private static final String VOTE_URL = "https://discordbots.org/bot/";

	public CommandVote(String botName, Snowflake botId) {
		super("vote", "Provides a link to vote for " + botName + " on Discord Bot List.");
		this.botName = botName;
		this.botId = botId;
	}

	@Override
	public PermissionSet getCommandPermission() {
		return PermissionSet.of(Permission.SEND_MESSAGES, Permission.EMBED_LINKS);
	}

	@Override
	public void execute(String[] args, Message message, StarotaServer server, MessageChannel channel)
			throws CommandException {
		EmbedBuilder builder = new EmbedBuilder();
		builder.withTitle("Thanks for helping " + this.botName + "!");
		builder.withUrl(VOTE_URL + botId.asLong());
		builder.appendDesc("Click on the link above to vote for " + this.botName
				+ " on Discord Bot List. " + "Voting helps support " + this.botName
				+ " and if enough people vote on your server, you can unlock cool perks! "
				+ "Every user can vote once per 12 hours and each vote counts for double on the weekends.");
		channel.createEmbed(builder.build()).block();
	}

}
