package us.myles_selim.starota.modules;

import java.util.EnumSet;
import java.util.List;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.Permissions;
import sx.blah.discord.util.EmbedBuilder;
import us.myles_selim.starota.commands.BotCommand;
import us.myles_selim.starota.wrappers.StarotaServer;

public class CommandModules extends BotCommand<StarotaServer> {

	public CommandModules() {
		super("modules", "Manage your enabled modules.");
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
	public List<String> getAliases() {
		List<String> aliases = super.getAliases();
		aliases.add("module");
		return aliases;
	}

	@Override
	public String getGeneralUsage() {
		return "[enable/disable/info] [module]";
	}

	@Override
	public void execute(String[] args, IMessage message, StarotaServer server, IChannel channel)
			throws Exception {
		if (args.length == 2 && args[1].equalsIgnoreCase("info")) {
			EmbedBuilder builder = new EmbedBuilder();
			builder.withTitle("Modules:");
			for (StarotaModule m : StarotaModule.getAllModules()) {
				String out = "";
				if (m.getCommandCategory() != null)
					out += "Commands: " + m.getCommandCategory() + "\n";
				boolean deps = false;
				for (StarotaModule d : m.getDependencies()) {
					if (!deps) {
						out += "**Dependencies**:\n";
						deps = true;
					}
					out += "- " + d.getName() + "\n";
				}
				if (out.isEmpty())
					out += "no info to display";
				builder.appendField(m.getName(), out, false);
			}
			channel.sendMessage(builder.build());
			return;
		}
		if (args.length < 3) {
			channel.sendMessage(
					"**Usage**: " + server.getPrefix() + getName() + " " + getGeneralUsage());
			return;
		}
		StarotaModule module = StarotaModule.getModule(args[2]);
		if (module == null) {
			channel.sendMessage("Module \"" + args[2] + "\" not found");
			return;
		}
		boolean status;
		switch (args[1].toLowerCase()) {
		case "enable":
		case "on":
			status = StarotaModule.enableModule(server, module);
			if (status)
				channel.sendMessage("Enabled the \"" + module.getName() + "\" module.");
			else
				channel.sendMessage("The \"" + module.getName() + "\" module is already enabled.");
			break;
		case "disable":
		case "off":
			status = StarotaModule.disableModule(server, module);
			if (status)
				channel.sendMessage("Disabled the \"" + module.getName() + "\" module.");
			else
				channel.sendMessage("The \"" + module.getName() + "\" module is already disabled.");
			break;
		default:
			channel.sendMessage(
					"**Usage**: " + server.getPrefix() + getName() + " " + getGeneralUsage());
			break;
		}
	}

}
