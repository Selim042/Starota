package us.myles_selim.starota.commands.registry;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.TextChannel;
import discord4j.core.object.util.Permission;
import discord4j.core.object.util.PermissionSet;
import us.myles_selim.starota.commands.registry.java.JavaCommand;

public class CommandHelp extends JavaCommand {

	public static final int CMDS_PER_PAGE = 10;

	public CommandHelp() {
		super("help", "Displays the help menu.");
	}

	@Override
	public PermissionSet getCommandPermission() {
		return PermissionSet.of(Permission.SEND_MESSAGES, Permission.EMBED_LINKS);
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
	public void execute(String[] args, Message message, Guild guild, TextChannel channel) {
		Member author = message.getAuthor().get().asMember(guild.getId()).block();

		if (args.length >= 2) {
			ICommand cmd = this.getCommandHandler().findCommand(guild, message, args[1]);
			if (cmd != null) {
				channel.createEmbed((e) -> {
					e.setTitle("Help: " + cmd.getName());
					e.addField("Category:", cmd.getCategory(), true);

					if (cmd.getGeneralUsage() != null && !cmd.getGeneralUsage().isEmpty())
						e.addField("Usage:", cmd.getGeneralUsage(), true);
					if (cmd.getAdminUsage() != null && !cmd.getAdminUsage().isEmpty()
							&& channel.getEffectivePermissions(author.getId()).block()
									.contains(Permission.ADMINISTRATOR))
						e.addField("Admin Usage:", cmd.getAdminUsage(), true);

					if (cmd.getDescription() != null && !cmd.getDescription().isEmpty())
						e.addField("Description:", cmd.getDescription(), true);
					List<String> aliases = cmd.getAliases();
					if (aliases.size() != 1) {
						String aliasesS = "";
						for (int i = 0; i < aliases.size(); i++) {
							String alias = aliases.get(i);
							if (!alias.equalsIgnoreCase(cmd.getName()))
								aliasesS += alias + ", ";
						}
						e.addField("Aliases:", aliasesS.substring(0, aliasesS.length() - 2), false);
					}
				}).doOnError((throwable) -> {
					System.out.println("ERROR");
				}).block();
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
			if (guild != null && (author.getBasePermissions().block().contains(Permission.ADMINISTRATOR)
					|| ((author.getBasePermissions().block().contains(cmd.requiredUsePermission())
							|| cmd.requiredUsePermission() == null) && cmd.hasRequiredRole(guild, author)
							&& cmd.isRequiredChannel(guild, channel))))
				disp.add(cmd);
		}
		disp.sort(null);
		if (page > disp.size() / CMDS_PER_PAGE)
			page = 0;

		if (disp.isEmpty())
			channel.createMessage("No commands found");
		else {
			final int fPage = page;
			channel.createEmbed((e) -> {
				String prevCategory = null;
				StringBuilder desc = new StringBuilder();
				desc.append("**Available Commands:**\n\n");
				String prefix = PrimaryCommandHandler.getPrefix(guild);
				for (int i = 0; i < CMDS_PER_PAGE && (CMDS_PER_PAGE * fPage) + i < disp.size(); i++) {
					ICommand cmd = disp.get((CMDS_PER_PAGE * fPage) + i);
					if (prevCategory == null || !prevCategory.equals(cmd.getCategory())) {
						desc.append("**" + cmd.getCategory() + "**" + "\n");
						prevCategory = cmd.getCategory();
					}
					if (cmd != null) {
						String desciption = cmd.getDescription();
						desc.append("- " + prefix + cmd.getName()
								+ (desciption == null ? "" : ", _" + cmd.getDescription() + "_") + "\n");
					}
				}
				if (fPage < disp.size() / CMDS_PER_PAGE)
					desc.append("\nTo view the next page, use `" + PrimaryCommandHandler.getPrefix(guild)
							+ getName() + " " + (fPage + 2) + "`");
				desc.append("\n**Page**: " + (fPage + 1) + "/" + ((disp.size() / CMDS_PER_PAGE) + 1));
				e.setDescription(desc.toString());
			}).block();
		}
	}

}
