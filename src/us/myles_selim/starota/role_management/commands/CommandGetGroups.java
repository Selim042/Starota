package us.myles_selim.starota.role_management.commands;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IRole;
import sx.blah.discord.util.EmbedBuilder;
import us.myles_selim.starota.commands.registry.Command;
import us.myles_selim.starota.role_management.EnumGroup;

public class CommandGetGroups extends Command {

	public CommandGetGroups() {
		super("getGroups", "Shows a list of groups.");
	}

	@Override
	public void execute(String[] args, IMessage message, IGuild guild, IChannel channel) {
		EmbedBuilder builder = new EmbedBuilder();
		builder.appendDesc("**Available Roles:**\n");
		EnumGroup[] groups = EnumGroup.values();
		for (int i = 0; i < groups.length; i++) {
			IRole role = guild.getRoleByID(groups[i].id);
			builder.appendDesc(role.getName());
			if (i != groups.length - 1)
				builder.appendDesc("\n");
		}
		channel.sendMessage(builder.build());
	}

}
