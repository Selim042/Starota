package us.myles_selim.starota.commands.settings;

import java.util.EnumSet;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.Permissions;
import sx.blah.discord.util.EmbedBuilder;
import us.myles_selim.starota.commands.BotCommand;
import us.myles_selim.starota.commands.settings.SettingSet.EnumReturnSetStatus;
import us.myles_selim.starota.wrappers.StarotaServer;

public class CommandSettings extends BotCommand<StarotaServer> {

	public CommandSettings() {
		super("settings", "Manages server settings.");
	}

	@Override
	public Permissions requiredUsePermission() {
		return Permissions.ADMINISTRATOR;
	}

	@Override
	public EnumSet<Permissions> getCommandPermissions() {
		return EnumSet.of(Permissions.SEND_MESSAGES, Permissions.EMBED_LINKS);
	}

	@Override
	public String getAdminUsage() {
		return "<settingName> <newValue>";
	}

	@Override
	public void execute(String[] args, IMessage message, StarotaServer server, IChannel channel)
			throws Exception {
		if (args.length == 1) {
			sendSettingsEmbed(server, channel);
			return;
		}
		EnumReturnSetStatus status = server.setSetting(args[1], args[2]);
		switch (status) {
		case NOT_FOUND:
			channel.sendMessage("Setting \"" + args[1] + "\" not found");
			break;
		case NOT_SET:
			channel.sendMessage("Setting \"" + args[1] + "\" not set, did you provide a valid value?");
			break;
		case SUCCESS:
			channel.sendMessage("Setting \"" + args[1] + "\" set to " + server.getSetting(args[1]));
			break;
		}
	}

	private void sendSettingsEmbed(StarotaServer server, IChannel channel) {
		EmbedBuilder builder = new EmbedBuilder();

		builder.withTitle(server.getName() + " Options:");
		server.forEachSetting((setting) -> {
			builder.appendDesc(
					String.format(" - %s: %s\n", setting.getName(), setting.getValue().toString()));
			if (setting.getDescription() != null)
				builder.appendDesc(String.format("%s\n\n", setting.getDescription()));
			else
				builder.appendDesc("\n");
		});

		channel.sendMessage(builder.build());
	}

}
