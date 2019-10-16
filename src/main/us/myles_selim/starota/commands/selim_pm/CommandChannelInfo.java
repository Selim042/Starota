package us.myles_selim.starota.commands.selim_pm;

import discord4j.command.util.CommandException;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.MessageChannel;
import discord4j.core.object.entity.TextChannel;
import discord4j.core.object.util.Image.Format;
import discord4j.core.object.util.Permission;
import discord4j.core.object.util.PermissionSet;
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.commands.registry.PrimaryCommandHandler;
import us.myles_selim.starota.commands.registry.java.JavaCommand;
import us.myles_selim.starota.debug_server.DebugServer;
import us.myles_selim.starota.misc.utils.EmbedBuilder;
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
	public void execute(String[] args, Message message, Guild guild, MessageChannel channel)
			throws CommandException {
		if (args.length < 2) {
			this.sendUsage(PrimaryCommandHandler.DEFAULT_PREFIX, channel);
			return;
		}
		TextChannel targetChannel;
		try {
			targetChannel = Starota.getChannel(Long.parseLong(args[1]));
		} catch (NumberFormatException e) {
			targetChannel = null;
		}
		if (targetChannel == null) {
			channel.createMessage("Channel with id \"" + args[1] + "\" not found.").block();
			return;
		}

		Guild targetGuild = targetChannel.getGuild().block();
		StarotaServer server = StarotaServer.getServer(targetGuild);
		EmbedBuilder builder = new EmbedBuilder();

		Member targetOwner = targetGuild.getOwner().block();
		builder.withAuthorIcon(targetOwner.getAvatarUrl()).withAuthorName(targetOwner.getDisplayName());
		builder.withTitle(targetGuild.getName() + ": " + targetChannel.getName());
		builder.withThumbnail(targetGuild.getIconUrl(Format.GIF)
				.orElse(targetGuild.getIconUrl(Format.PNG).orElse(targetGuild.getIconUrl(Format.PNG)
						.orElse(targetGuild.getIconUrl(Format.WEB_P).get()))));
		builder.appendDesc(targetChannel.getMention() + "\n");

		String permsString = "";
		PermissionSet hasPerms = targetChannel.getEffectivePermissions(Starota.getOurUser().getId())
				.block();
		for (Permission p : DebugServer.getUsedPermission())
			if (!hasPerms.contains(p))
				permsString += " - " + p + "\n";
		if (!permsString.isEmpty())
			builder.appendField("Missing Perms:", permsString, false);
		else
			builder.appendField("Missing Perms:", "None", false);

		channel.createEmbed(builder.build()).block();
	}

}
