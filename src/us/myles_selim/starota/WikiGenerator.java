package us.myles_selim.starota;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import us.myles_selim.starota.commands.registry.ICommand;
import us.myles_selim.starota.commands.registry.PrimaryCommandHandler;
import us.myles_selim.starota.modules.StarotaModule;

public class WikiGenerator {

	public static final String WIKI_URL = "https://github.com/Selim042/Starota/wiki/";

	public static void generate() {
		System.out.println("starting wiki generation");
		if (!Starota.FULLY_STARTED)
			Starota.BaseModules.registerModules();
		try {
			FileWriter homeWriter = new FileWriter(new File("wiki/Home.md"));
			homeWriter.append("Welcome to the Starota wiki!  "
					+ "This wiki is currently a WIP and I will attempt to update it as new features are updated and added.\n"
					+ "The wiki currently auto generates to maintain an up-to-date status.\n");
			homeWriter.append("\n\nCurrent Modules:\n");
			for (StarotaModule module : StarotaModule.getAllModules()) {
				String properName = getProperName(module.getName());
				homeWriter
						.append(String.format("* [%s](%s)\n", properName, WIKI_URL + module.getName()));

				FileWriter moduleWriter = new FileWriter(new File("wiki/" + module.getName() + ".md"));
				moduleWriter.append("## About " + properName + "\n");
				if (module.getDescription() != null)
					moduleWriter.append(module.getDescription() + "\n\n");
				else {
					moduleWriter.append("\n");
					System.out.println("module " + module.getName() + " has no description");
				}
				String out = "";
				out += "## Commands\n\n_all sample commands use the default command prefix, `.`_\n\n---\n";
				for (ICommand cmd : PrimaryCommandHandler.getCommandsByCategory(
						Starota.getGuild(DebugServer.DEBUG_SERVER_ID), module.getCommandCategory())) {
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
					if (cmd.requiredPermission() != null)
						out += "**Required Permission to Use**: " + cmd.requiredPermission().name()
								+ "\n\n";
					out += "---\n";
				}
				moduleWriter.append(out);
				moduleWriter.close();
			}
			homeWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("finished wiki generation, pushing to repo");
		try {
			String[][] cmds = new String[][] { new String[] { "git", "add", "-A" },
					new String[] { "git", "commit", "-m", "\"Auto-generated updates\"" },
					new String[] { "git", "push" } };
			for (String[] cmd : cmds) {
				Process pr = new ProcessBuilder(cmd)
						.directory(new File(System.getProperty("user.dir") + "/wiki")).start();
				InputStream stream = pr.getInputStream();
				while (stream.available() != 0)
					System.out.print((char) stream.read());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("pushed to repo");
	}

	private static String getProperName(String name) {
		if (name.equals("PvP"))
			return name;
		String out = "";
		for (int i = 0; i < name.length() - 1; i++) {
			if (i != 0 && Character.isUpperCase(name.charAt(i)))
				out += " ";
			out += name.charAt(i);
		}
		out += name.charAt(name.length() - 1);
		return out;
	}

	public static void main(String... args) {
		generate();
	}

}
