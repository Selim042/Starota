package us.myles_selim.starota.commands;

import java.util.EnumSet;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.Permissions;
import sx.blah.discord.util.EmbedBuilder;
import us.myles_selim.starota.wrappers.StarotaServer;

public class CommandInvite extends StarotaCommand {

	private String botName;
	private long botId;
	private int permsUsed;

	private static final String INVITE_TEMPLATE = "https://discordapp.com/oauth2/"
			+ "authorize?client_id=%d&scope=bot&permissions=%d";

	public CommandInvite(String botName, long botId, int permsUsed) {
		super("invite", "Provides an invite link to add " + botName + " to your own server.");
		this.botName = botName;
		this.botId = botId;
		this.permsUsed = permsUsed;
	}

	@Override
	public EnumSet<Permissions> getCommandPermissions() {
		return EnumSet.of(Permissions.SEND_MESSAGES, Permissions.EMBED_LINKS);
	}

	@Override
	public void execute(String[] args, IMessage message, StarotaServer server, IChannel channel)
			throws Exception {
		EmbedBuilder builder = new EmbedBuilder();
		builder.withTitle("Thanks for showing an interest in " + this.botName + "!");
		builder.withUrl(
				String.format(INVITE_TEMPLATE, this.botId, this.permsUsed));
		builder.appendDesc("Click on the link above to invite " + this.botName + " to your server. "
				+ "The permissions suggested there will enable full functionality and there will be an "
				+ "announcement on the support server when new features "
				+ "are added that require additional permissions.");
		channel.sendMessage(builder.build());
	}

}
