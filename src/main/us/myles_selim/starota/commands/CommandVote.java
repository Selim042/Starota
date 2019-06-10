package us.myles_selim.starota.commands;

import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.TextChannel;
import discord4j.core.object.util.Permission;
import discord4j.core.object.util.PermissionSet;
import discord4j.core.object.util.Snowflake;
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
	public void execute(String[] args, Message message, StarotaServer server, TextChannel channel)
			throws Exception {
		channel.createEmbed((e) -> {
			e.setTitle("Thanks for helping " + this.botName + "!");
			e.setUrl(VOTE_URL + botId);
			e.setDescription("Click on the link above to vote for " + this.botName
					+ " on Discord Bot List. " + "Voting helps support " + this.botName
					+ " and if enough people vote on your server, you can unlock cool perks! "
					+ "Every user can vote once per 12 hours and each vote counts for double on the weekends.");
		});
	}

}
