package us.myles_selim.starota.modules;

import java.util.List;

import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.TextChannel;
import discord4j.core.object.util.Permission;
import discord4j.core.object.util.PermissionSet;
import us.myles_selim.starota.commands.StarotaCommand;
import us.myles_selim.starota.misc.data_types.EmbedBuilder;
import us.myles_selim.starota.wrappers.StarotaServer;

public class CommandModules extends StarotaCommand {

	public CommandModules() {
		super("modules", "Manage your enabled modules.");
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
	public void execute(String[] args, Message message, StarotaServer server, TextChannel channel)
			throws Exception {
		if (args.length == 2 && args[1].equalsIgnoreCase("info")) {
			EmbedBuilder builder = new EmbedBuilder();
			builder.setTitle("Modules:");
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
				builder.addField(m.getName(), out, false);
			}
			channel.createEmbed(builder.build());
			return;
		}
		if (args.length < 3) {
			channel.createMessage(
					"**Usage**: " + server.getPrefix() + getName() + " " + getGeneralUsage());
			return;
		}
		StarotaModule module = StarotaModule.getModule(args[2]);
		if (module == null) {
			channel.createMessage("Module \"" + args[2] + "\" not found");
			return;
		}
		boolean status;
		switch (args[1].toLowerCase()) {
		case "enable":
		case "on":
			status = StarotaModule.enableModule(server, module);
			if (status)
				channel.createMessage("Enabled the \"" + module.getName() + "\" module.");
			else
				channel.createMessage("The \"" + module.getName() + "\" module is already enabled.");
			break;
		case "disable":
		case "off":
			status = StarotaModule.disableModule(server, module);
			if (status)
				channel.createMessage("Disabled the \"" + module.getName() + "\" module.");
			else
				channel.createMessage("The \"" + module.getName() + "\" module is already disabled.");
			break;
		default:
			channel.createMessage(
					"**Usage**: " + server.getPrefix() + getName() + " " + getGeneralUsage());
			break;
		}
	}

}
