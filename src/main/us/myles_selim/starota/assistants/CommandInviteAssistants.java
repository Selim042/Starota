package us.myles_selim.starota.assistants;

import discord4j.core.DiscordClient;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.TextChannel;
import discord4j.core.object.util.Permission;
import discord4j.core.object.util.PermissionSet;
import us.myles_selim.starota.commands.StarotaCommand;
import us.myles_selim.starota.wrappers.StarotaServer;

public class CommandInviteAssistants extends StarotaCommand {

	public CommandInviteAssistants() {
		super("inviteAssistants", "Gets invite links for all assistants.");
	}

	@Override
	public Permission requiredUsePermission() {
		return Permission.ADMINISTRATOR;
	}

	@Override
	public PermissionSet getCommandPermission() {
		return PermissionSet.of(Permission.SEND_MESSAGES, Permission.EMBED_LINKS);
	}

	@Override
	public void execute(String[] args, Message message, StarotaServer server, TextChannel channel)
			throws Exception {
		channel.createEmbed((e) -> {
			e.setTitle("Use the following links to invite all assistants");
			StringBuilder desc = new StringBuilder();
			for (DiscordClient client : StarotaAssistants.getClients())
				desc.append(String.format("[%s](%s)\n", client.getSelf().block().getUsername(),
						String.format("https://discordapp.com/oauth2/authorize?client_id=%s&scope=bot",
								client.getSelf().block().getId().asString())));
			e.setDescription(desc.toString());
		});
	}

}
