package us.myles_selim.starota.commands.settings;

import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.TextChannel;
import discord4j.core.object.util.Permission;
import discord4j.core.object.util.PermissionSet;
import us.myles_selim.starota.commands.StarotaCommand;
import us.myles_selim.starota.commands.settings.SettingSet.EnumReturnSetStatus;
import us.myles_selim.starota.misc.data_types.EmbedBuilder;
import us.myles_selim.starota.wrappers.StarotaServer;

public class CommandSettings extends StarotaCommand {

	public CommandSettings() {
		super("settings", "Manages server settings.");
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
	public String getAdminUsage() {
		return "<settingName> <newValue>";
	}

	@Override
	public void execute(String[] args, Message message, StarotaServer server, TextChannel channel)
			throws Exception {
		if (args.length == 1) {
			sendSettingsEmbed(server, channel);
			return;
		}
		EnumReturnSetStatus status = server.setSetting(args[1], args[2]);
		switch (status) {
		case NOT_FOUND:
			channel.createMessage("Setting \"" + args[1] + "\" not found");
			break;
		case NOT_SET:
			channel.createMessage("Setting \"" + args[1] + "\" not set, did you provide a valid value?");
			break;
		case SUCCESS:
			channel.createMessage("Setting \"" + args[1] + "\" set to " + server.getSetting(args[1]));
			break;
		}
	}

	private void sendSettingsEmbed(StarotaServer server, TextChannel channel) {
		EmbedBuilder builder = new EmbedBuilder();

		builder.setTitle(server.getDiscordGuild().getName() + " Options:");
		server.forEachSetting((setting) -> {
			builder.appendDesc(
					String.format(" - %s: %s\n", setting.getName(), setting.getValue().toString()));
			if (setting.getDescription() != null)
				builder.appendDesc(String.format("%s\n\n", setting.getDescription()));
			else
				builder.appendDesc("\n");
		});

		channel.createEmbed(builder.build());
	}

}
