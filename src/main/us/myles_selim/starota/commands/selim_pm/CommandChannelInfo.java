package us.myles_selim.starota.commands.selim_pm;

import java.util.EnumSet;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.handle.obj.Permissions;
import sx.blah.discord.util.EmbedBuilder;
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
	public void execute(String[] args, IMessage message, IGuild guild, IChannel channel)
			throws Exception {
		if (args.length < 2) {
			this.sendUsage(PrimaryCommandHandler.DEFAULT_PREFIX, channel);
			return;
		}
		IChannel targetChannel = null;
		try {
			targetChannel = Starota.getChannel(Long.parseLong(args[1]));
		} catch (NumberFormatException e) {}
		if (targetChannel == null) {
			channel.sendMessage("Channel with id \"" + args[1] + "\" not found.");
			return;
		}

		IGuild targetGuild = targetChannel.getGuild();
		StarotaServer server = StarotaServer.getServer(targetGuild);
		EmbedBuilder builder = new EmbedBuilder();

		IUser targetOwner = targetGuild.getOwner();
		builder.withAuthorIcon(targetOwner.getAvatarURL()).withAuthorName(targetOwner.getName());
		builder.withTitle(targetGuild.getName() + ": " + targetChannel.getName());
		builder.withThumbnail(targetGuild.getIconURL());
		builder.appendDesc(targetChannel.mention() + "\n");

		String permsString = "";
		EnumSet<Permissions> hasPerms = targetChannel.getModifiedPermissions(Starota.getOurUser());
		for (Permissions p : DebugServer.getUsedPermissions())
			if (!hasPerms.contains(p))
				permsString += " - " + p + "\n";
		if (!permsString.isEmpty())
			builder.appendField("Missing Perms:", permsString, false);
		else
			builder.appendField("Missing Perms:", "None", false);

		String disallowedCats = "";
		for (String c : Starota.COMMAND_HANDLER.getAllCategories(targetGuild))
			if (!ChannelCommandManager.isAllowedHere(server, c, targetChannel))
				disallowedCats += " - " + c + "\n";
		if (!disallowedCats.isEmpty())
			builder.appendField("Disallowed Categories:", disallowedCats, false);
		else
			builder.appendField("Disallowed Categories:", "None", false);

		channel.sendMessage(builder.build());
	}

}
