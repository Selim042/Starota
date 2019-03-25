package us.myles_selim.starota.assistants;

import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.Permissions;
import sx.blah.discord.util.EmbedBuilder;
import us.myles_selim.starota.commands.StarotaCommand;
import us.myles_selim.starota.wrappers.StarotaServer;

public class CommandInviteAssistants extends StarotaCommand {

	public CommandInviteAssistants() {
		super("inviteAssistants", "Gets invite links for all assistants.");
	}

	@Override
	public Permissions requiredPermission() {
		return Permissions.ADMINISTRATOR;
	}

	@Override
	public void execute(String[] args, IMessage message, StarotaServer server, IChannel channel)
			throws Exception {
		EmbedBuilder builder = new EmbedBuilder();
		builder.withTitle("Use the following links to invite all assistants");
		for (IDiscordClient client : StarotaAssistants.getClients())
			builder.appendDesc(String.format("[%s](%s)\n", client.getOurUser().getName(),
					String.format("https://discordapp.com/oauth2/authorize?client_id=%s&scope=bot",
							client.getApplicationClientID())));
		channel.sendMessage(builder.build());
	}

}
