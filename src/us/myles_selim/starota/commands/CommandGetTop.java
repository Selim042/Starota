package us.myles_selim.starota.commands;

import java.util.List;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.Permissions;
import sx.blah.discord.util.EmbedBuilder;
import us.myles_selim.starota.commands.registry.Command;
import us.myles_selim.starota.research.ResearchTracker;
import us.myles_selim.starota.research.Researcher;

public class CommandGetTop extends Command {

	public CommandGetTop() {
		super("gettop");
	}

	@Override
	public Permissions requiredPermission() {
		return Permissions.ADMINISTRATOR;
	}

	@Override
	public void execute(String[] args, IMessage message, IGuild guild, IChannel channel) {
		List<Researcher> top = ResearchTracker.getTopPosters(guild);
		EmbedBuilder builder = new EmbedBuilder();
		builder.appendDesc("**Top " + top.size() + " Researchers for the Week**:\n");
		for (int i = 0; i < top.size(); i++) {
			Researcher r = top.get(i);
			builder.appendDesc("**" + (i + 1) + ")** " + r.getDiscordUser().getDisplayName(guild) + " ("
					+ r.getPosts() + ")\n");
		}
		channel.sendMessage(builder.build());
	}

}
