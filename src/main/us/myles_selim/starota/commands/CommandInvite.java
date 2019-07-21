package us.myles_selim.starota.commands;

import discord4j.command.util.CommandException;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.MessageChannel;
import discord4j.core.object.util.Permission;
import discord4j.core.object.util.PermissionSet;
import discord4j.core.object.util.Snowflake;
import us.myles_selim.starota.misc.utils.EmbedBuilder;
import us.myles_selim.starota.wrappers.StarotaServer;

public class CommandInvite extends BotCommand<StarotaServer> {

	private String botName;
	private Snowflake botId;
	private long permsUsed;

	private static final String INVITE_TEMPLATE = "https://discordapp.com/oauth2/"
			+ "authorize?client_id=%d&scope=bot&permissions=%d";

	public CommandInvite(String botName, Snowflake botId, long permsUsed) {
		super("invite", "Provides an invite link to add " + botName + " to your own server.");
		this.botName = botName;
		this.botId = botId;
		this.permsUsed = permsUsed;
	}

	@Override
	public PermissionSet getCommandPermission() {
		return PermissionSet.of(Permission.SEND_MESSAGES, Permission.EMBED_LINKS);
	}

	@Override
	public void execute(String[] args, Message message, StarotaServer server, MessageChannel channel)
			throws CommandException {
		EmbedBuilder builder = new EmbedBuilder();
		builder.withTitle("Thanks for showing an interest in " + this.botName + "!");
		builder.withUrl(String.format(INVITE_TEMPLATE, this.botId.asLong(), this.permsUsed));
		builder.appendDesc("Click on the link above to invite " + this.botName + " to your server. "
				+ "The permissions suggested there will enable full functionality and there will be an "
				+ "announcement on the support server when new features "
				+ "are added that require additional permissions.");
		channel.createEmbed(builder.build()).block();
	}

}
