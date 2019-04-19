package us.myles_selim.starota.commands;

import java.util.EnumSet;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.Permissions;
import sx.blah.discord.util.EmbedBuilder;
import us.myles_selim.ebs.DataType;
import us.myles_selim.ebs.EBStorage;
import us.myles_selim.starota.wrappers.StarotaServer;

public class CommandSettings extends StarotaCommand {

	public static final String SETTINGS_KEY = "settings";

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
		EBStorage settings = server.getData().get(SETTINGS_KEY, EBStorage.class);
		if (!settings.containsKey(args[1])) {
			channel.sendMessage("Setting \"" + args[1] + "\" not found");
			return;
		}

		Object value = null;
		if (message.getChannelMentions().size() >= 1)
			value = message.getChannelMentions().get(0);

		settings.set(args[1], value);
		channel.sendMessage("Setting \"" + args[1] + "\" set to " + value);
	}

	private void sendSettingsEmbed(StarotaServer server, IChannel channel) {
		EmbedBuilder builder = new EmbedBuilder();
		EBStorage settings = server.getData().get(SETTINGS_KEY, EBStorage.class);
		// EBStorage settings = server.getOptions();
		if (settings == null) {
			settings = getDefaultSettings();
			server.getData().set(SETTINGS_KEY, settings);
		}

		builder.withTitle(server.getDiscordGuild().getName() + " Options:");
		for (String k : settings.getKeys())
			builder.appendDesc(String.format(" - %s: %s\n", k, settings.get(k).toString()));

		channel.sendMessage(builder.build());
	}

	// static stuff
	private final static EBStorage DEFAULT_SETTINGS = new EBStorage();

	public static <V> void setDefaultValue(String name, DataType<V> type, V value) {
		DEFAULT_SETTINGS.registerType(type);
		DEFAULT_SETTINGS.set(name, value);
	}

	public static EBStorage getDefaultSettings() {
		return new EBStorage(DEFAULT_SETTINGS);
	}

}
