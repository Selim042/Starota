package us.myles_selim.starota.commands;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.Permissions;
import sx.blah.discord.util.EmbedBuilder;
import us.myles_selim.starota.DebugServer;
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.wrappers.StarotaServer;

public class CommandInvite extends StarotaCommand {

	private static final String INVITE_TEMPLATE = "https://discordapp.com/oauth2/"
			+ "authorize?client_id=%d&scope=bot&permissions=%d";

	public CommandInvite() {
		super("invite", "Provides an invite link to add Starota to your server.");
	}

	@Override
	public void execute(String[] args, IMessage message, StarotaServer server, IChannel channel)
			throws Exception {
		EmbedBuilder builder = new EmbedBuilder();
		builder.withTitle("Thanks for showing an interest in Starota!");
		builder.withUrl(String.format(INVITE_TEMPLATE, Starota.getOurUser().getLongID(),
				Permissions.generatePermissionsNumber(DebugServer.USED_PERMISSIONS)));
		builder.appendDesc("Click on the link above to invite Starota to your server. "
				+ "The permissions suggested there will enable full functionality and there will be an "
				+ "announcement on the support server when new features "
				+ "are added that require additional permissions.");
		channel.sendMessage(builder.build());
	}

}
