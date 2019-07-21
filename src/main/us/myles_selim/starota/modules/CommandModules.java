package us.myles_selim.starota.modules;

import java.util.List;

import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.MessageChannel;
import discord4j.core.object.util.Permission;
import discord4j.core.object.util.PermissionSet;
import us.myles_selim.starota.commands.BotCommand;
import us.myles_selim.starota.commands.registry.CommandException;
import us.myles_selim.starota.misc.utils.EmbedBuilder;
import us.myles_selim.starota.wrappers.StarotaServer;

public class CommandModules extends BotCommand<StarotaServer> {

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
	public void execute(String[] args, Message message, StarotaServer server, MessageChannel channel)
			throws CommandException {
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
			channel.createEmbed(builder.build()).block();
			return;
		}
		if (args.length < 3) {
			channel.createMessage(
					"**Usage**: " + server.getPrefix() + getName() + " " + getGeneralUsage()).block();
			return;
		}
		StarotaModule module = StarotaModule.getModule(args[2]);
		if (module == null) {
			channel.createMessage("Module \"" + args[2] + "\" not found").block();
			return;
		}
		boolean status;
		switch (args[1].toLowerCase()) {
		case "enable":
		case "on":
			status = StarotaModule.enableModule(server, module);
			if (status)
				channel.createMessage("Enabled the \"" + module.getName() + "\" module.").block();
			else
				channel.createMessage("The \"" + module.getName() + "\" module is already enabled.")
						.block();
			break;
		case "disable":
		case "off":
			status = StarotaModule.disableModule(server, module);
			if (status)
				channel.createMessage("Disabled the \"" + module.getName() + "\" module.").block();
			else
				channel.createMessage("The \"" + module.getName() + "\" module is already disabled.")
						.block();
			break;
		default:
			channel.createMessage(
					"**Usage**: " + server.getPrefix() + getName() + " " + getGeneralUsage()).block();
			break;
		}
	}

}
