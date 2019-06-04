package us.myles_selim.starota.commands;

import java.util.EnumSet;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.Permissions;
import sx.blah.discord.util.EmbedBuilder;
import us.myles_selim.starota.wrappers.StarotaServer;

public class CommandVote extends BotCommand<StarotaServer> {

	private String botName;
	private long botId;

	private static final String VOTE_URL = "https://discordbots.org/bot/";

	public CommandVote(String botName, long botId) {
		super("vote", "Provides a link to vote for " + botName + " on Discord Bot List.");
		this.botName = botName;
		this.botId = botId;
	}

	@Override
	public EnumSet<Permissions> getCommandPermissions() {
		return EnumSet.of(Permissions.SEND_MESSAGES, Permissions.EMBED_LINKS);
	}

	@Override
	public void execute(String[] args, IMessage message, StarotaServer server, IChannel channel)
			throws Exception {
		EmbedBuilder builder = new EmbedBuilder();
		builder.withTitle("Thanks for helping " + this.botName + "!");
		builder.withUrl(VOTE_URL + botId);
		builder.appendDesc("Click on the link above to vote for " + this.botName
				+ " on Discord Bot List. " + "Voting helps support " + this.botName
				+ " and if enough people vote on your server, you can unlock cool perks! "
				+ "Every user can vote once per 12 hours and each vote counts for double on the weekends.");
		channel.sendMessage(builder.build());
	}

}
