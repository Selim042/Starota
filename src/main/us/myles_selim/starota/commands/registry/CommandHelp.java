package us.myles_selim.starota.commands.registry;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.LinkedList;
import java.util.List;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.handle.obj.Permissions;
import sx.blah.discord.util.EmbedBuilder;
import us.myles_selim.starota.commands.registry.java.JavaCommand;

public class CommandHelp extends JavaCommand {

	public static final int CMDS_PER_PAGE = 10;

	public CommandHelp() {
		super("help", "Displays the help menu.");
	}

	@Override
	public EnumSet<Permissions> getCommandPermissions() {
		return EnumSet.of(Permissions.SEND_MESSAGES, Permissions.EMBED_LINKS);
	}

	@Override
	public List<String> getAliases() {
		List<String> aliases = new ArrayList<>();
		aliases.add(getName());
		aliases.add("?");
		aliases.add("commands");
		aliases.add("cmds");
		return aliases;
	}

	@Override
	public void execute(String[] args, IMessage message, IGuild guild, IChannel channel) {
		IUser author = message.getAuthor();

		if (args.length >= 2) {
			ICommand cmd = this.getCommandHandler().findCommand(guild, message, args[1]);
			if (cmd != null) {
				EmbedBuilder builder = new EmbedBuilder();
				builder.withTitle("Help: " + cmd.getName());
				builder.appendField("Category:", cmd.getCategory(), true);

				if (cmd.getGeneralUsage() != null && !cmd.getGeneralUsage().isEmpty())
					builder.appendField("Usage:", cmd.getGeneralUsage(), true);
				if (cmd.getAdminUsage() != null && !cmd.getAdminUsage().isEmpty()
						&& channel.getModifiedPermissions(author).contains(Permissions.ADMINISTRATOR))
					builder.appendField("Admin Usage:", cmd.getAdminUsage(), true);

				if (cmd.getDescription() != null && !cmd.getDescription().isEmpty())
					builder.appendField("Description:", cmd.getDescription(), true);
				List<String> aliases = cmd.getAliases();
				if (aliases.size() != 1) {
					String aliasesS = "";
					for (int i = 0; i < aliases.size(); i++) {
						String alias = aliases.get(i);
						if (!alias.equalsIgnoreCase(cmd.getName()))
							aliasesS += alias + ", ";
					}
					builder.appendField("Aliases:", aliasesS.substring(0, aliasesS.length() - 2), false);
				}
				// IMessage helpMessage =
				channel.sendMessage(builder.build());

				// Thread deleteHelp = new Thread() {
				//
				// @Override
				// public void run() {
				// try {
				// Thread.sleep(10000);
				// } catch (InterruptedException e) {
				// e.printStackTrace();
				// }
				// message.delete();
				// helpMessage.delete();
				// }
				//
				// };
				// if (Starota.getOurUser().getPermissionsForGuild(guild)
				// .contains(Permissions.MANAGE_MESSAGES))
				// deleteHelp.start();
				return;
			}
		}

		String category = null;
		int page = 0;
		if (args.length >= 2) {
			try {
				page = Integer.parseInt(args[1]) - 1;
			} catch (NumberFormatException e) {
				category = args[1];
				if (args.length > 2)
					try {
						page = Integer.parseInt(args[2]) - 1;
					} catch (NumberFormatException e1) {}
			}
		}

		List<ICommand> cmds = this.getCommandHandler().getCommandsByCategory(guild, category);
		List<ICommand> disp = new LinkedList<>();
		for (ICommand cmd : cmds) {
			if (guild == null)
				disp.add(cmd);
			if (guild != null && (author.getPermissionsForGuild(guild)
					.contains(Permissions.ADMINISTRATOR)
					|| ((author.getPermissionsForGuild(guild).contains(cmd.requiredUsePermission())
							|| cmd.requiredUsePermission() == null) && cmd.hasRequiredRole(guild, author)
							&& cmd.isRequiredChannel(guild, channel))))
				disp.add(cmd);
		}
		disp.sort(null);
		if (page > disp.size() / CMDS_PER_PAGE)
			page = 0;

		// IMessage helpMessage;
		if (disp.isEmpty())
			// helpMessage =
			channel.sendMessage("No commands found");
		else {
			String prevCategory = null;
			EmbedBuilder builder = new EmbedBuilder().appendDescription("**Available Commands:**\n\n");
			String prefix = PrimaryCommandHandler.getPrefix(guild);
			for (int i = 0; i < CMDS_PER_PAGE && (CMDS_PER_PAGE * page) + i < disp.size(); i++) {
				ICommand cmd = disp.get((CMDS_PER_PAGE * page) + i);
				if (prevCategory == null || !prevCategory.equals(cmd.getCategory())) {
					builder.appendDesc("**" + cmd.getCategory() + "**" + "\n");
					prevCategory = cmd.getCategory();
				}
				if (cmd != null) {
					String desciption = cmd.getDescription();
					builder.appendDesc("- " + prefix + cmd.getName()
							+ (desciption == null ? "" : ", _" + cmd.getDescription() + "_") + "\n");
				}
			}
			if (page < disp.size() / CMDS_PER_PAGE)
				builder.appendDesc("\nTo view the next page, use `"
						+ PrimaryCommandHandler.getPrefix(guild) + getName() + " " + (page + 2) + "`");
			builder.appendDesc("\n**Page**: " + (page + 1) + "/" + ((disp.size() / CMDS_PER_PAGE) + 1));
			// helpMessage =
			channel.sendMessage(builder.build());
		}
		// Thread deleteHelp = new Thread() {
		//
		// @Override
		// public void run() {
		// try {
		// Thread.sleep(10000);
		// } catch (InterruptedException e) {
		// e.printStackTrace();
		// }
		// message.delete();
		// helpMessage.delete();
		// }
		//
		// };
		// if
		// (Starota.getOurUser().getPermissionsForGuild(guild).contains(Permissions.MANAGE_MESSAGES))
		// deleteHelp.start();
	}

}
