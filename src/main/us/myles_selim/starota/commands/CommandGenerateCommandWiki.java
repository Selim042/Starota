package us.myles_selim.starota.commands;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.commands.registry.ICommand;
import us.myles_selim.starota.commands.registry.java.JavaCommand;
import us.myles_selim.starota.modules.StarotaModule;

public class CommandGenerateCommandWiki extends JavaCommand {

	public CommandGenerateCommandWiki() {
		super("genwiki");
	}

	@Override
	public void execute(String[] args, IMessage message, IGuild guild, IChannel channel) {
		StarotaModule module = null;
		for (StarotaModule m : StarotaModule.getAllModules())
			if (m.getCommandCategory() != null && m.getCommandCategory().equals(args[1]))
				module = m;
		String out = "";
		out += "## Commands\n\n_all sample commands use the default command prefix, `.`_\n\n---\n";
		for (ICommand cmd : Starota.COMMAND_HANDLER.getCommandsByCategory(guild, args[1])) {
			out += "### " + cmd.getName() + "\n\n";
			if (cmd.getGeneralUsage() == null)
				out += "**Usage**: ." + cmd.getName() + "\n\n";
			else
				out += "**Usage**: ." + cmd.getName() + " " + cmd.getGeneralUsage() + "\n\n";
			if (cmd.getDescription() == null)
				out += "**Description**: ADD A DESCRIPTION\n\n";
			else
				out += "**Description**: " + cmd.getDescription() + "\n\n";
			if (cmd.getAliases().size() != 1) {
				String aliases = "**Aliases**: ";
				for (String alias : cmd.getAliases())
					if (!alias.equals(cmd.getName()))
						aliases += "_" + alias + "_, ";
				out += aliases.substring(0, aliases.length() - 2) + "\n\n";
			}
			out += "**Category**: " + cmd.getCategory() + "\n\n";
			if (module != null)
				out += "**Module**: " + module.getName() + "\n\n";
			if (cmd.requiredUsePermission() != null)
				out += "**Required Permission to Use**: " + cmd.requiredUsePermission().name() + "\n\n";
			out += "---\n";
		}
		channel.sendMessage("```" + out + "```");
	}

}
