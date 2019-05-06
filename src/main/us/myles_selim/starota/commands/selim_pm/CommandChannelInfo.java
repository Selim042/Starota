package us.myles_selim.starota.commands.selim_pm;

import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.TextChannel;
import discord4j.core.object.util.Image;
import discord4j.core.object.util.Permission;
import discord4j.core.object.util.PermissionSet;
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.commands.registry.PrimaryCommandHandler;
import us.myles_selim.starota.commands.registry.channel_management.ChannelCommandManager;
import us.myles_selim.starota.commands.registry.java.JavaCommand;
import us.myles_selim.starota.debug_server.DebugServer;
import us.myles_selim.starota.wrappers.StarotaServer;

public class CommandChannelInfo extends JavaCommand {

	public CommandChannelInfo() {
		super("channelInfo", "Displays information about the given channel.");
	}

	@Override
	public String getGeneralUsage() {
		return "<channelId>";
	}

	@Override
	public void execute(String[] args, Message message, Guild guild, TextChannel channel)
			throws Exception {
		if (args.length < 2) {
			this.sendUsage(PrimaryCommandHandler.DEFAULT_PREFIX, channel);
			return;
		}
		TextChannel targetChannel = null;
		try {
			targetChannel = (TextChannel) Starota.getChannel(Long.parseLong(args[1]));
		} catch (NumberFormatException e) {}
		if (targetChannel == null) {
			channel.createMessage("Channel with id \"" + args[1] + "\" not found.");
			return;
		}

		Guild targetGuild = targetChannel.getGuild().block();
		StarotaServer server = StarotaServer.getServer(targetGuild);
		final TextChannel fTargetChannel = targetChannel;
		channel.createEmbed((e) -> {
			Member targetOwner = targetGuild.getOwner().block();
			e.setAuthor(targetOwner.getUsername(), null, targetOwner.getAvatarUrl());
			e.setTitle(targetGuild.getName() + ": " + fTargetChannel.getName());
			e.setThumbnail(targetGuild.getIconUrl(Image.Format.PNG).get());
			StringBuilder desc = new StringBuilder();
			desc.append(fTargetChannel.getMention() + "\n");

			String permsString = "";
			PermissionSet hasPerms = fTargetChannel.getEffectivePermissions(Starota.getSelf().getId())
					.block();
			for (Permission p : DebugServer.getUsedPermission())
				if (!hasPerms.contains(p))
					permsString += " - " + p + "\n";
			if (!permsString.isEmpty())
				e.addField("Missing Perms:", permsString, false);
			else
				e.addField("Missing Perms:", "None", false);

			String disallowedCats = "";
			for (String c : Starota.COMMAND_HANDLER.getAllCategories(targetGuild))
				if (!ChannelCommandManager.isAllowedHere(server, c, fTargetChannel))
					disallowedCats += " - " + c + "\n";
			if (!disallowedCats.isEmpty())
				e.addField("Disallowed Categories:", disallowedCats, false);
			else
				e.addField("Disallowed Categories:", "None", false);
			e.setDescription(desc.toString());
		});
	}

}
