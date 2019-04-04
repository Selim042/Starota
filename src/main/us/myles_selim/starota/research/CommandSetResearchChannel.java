package us.myles_selim.starota.research;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.Permissions;
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.commands.StarotaCommand;
import us.myles_selim.starota.wrappers.StarotaServer;

public class CommandSetResearchChannel extends StarotaCommand {

	public CommandSetResearchChannel() {
		super("setResearchChannel", "Sets channels for research functionality.");
	}

	@Override
	public Permissions requiredUsePermission() {
		return Permissions.ADMINISTRATOR;
	}

	@Override
	public void execute(String[] args, IMessage message, StarotaServer server, IChannel channel) {
		if (args.length < 3) {
			String channelStrings = "";
			for (EnumResearchChannel c : EnumResearchChannel.values())
				channelStrings += c.getName() + "/";
			channel.sendMessage("**Usage**: " + server.getPrefix() + this.getName() + " <"
					+ channelStrings.substring(0, channelStrings.length() - 1) + "> <channel>");
			return;
		}
		EnumResearchChannel rChannel = EnumResearchChannel.getByName(args[1]);
		if (rChannel == null) {
			channel.sendMessage("Unknown channel type \"" + args[1] + "\"");
			return;
		}
		IChannel target = Starota.findChannel(server.getDiscordGuild().getLongID(), args[2]);
		if (target == null) {
			channel.sendMessage("Channel \"" + args[2] + "\" not found");
			return;
		}
		IChannel prev = ResearchTracker.getResearchChannel(server, rChannel);
		ResearchTracker.setResearchChannel(server, rChannel, target);
		if (prev == null)
			channel.sendMessage("Set " + rChannel.getName() + " to " + target.mention());
		else
			channel.sendMessage("Changed " + rChannel.getName() + " to " + target.mention() + " from "
					+ prev.mention());
	}

	public static enum EnumResearchChannel {
		MONITOR("monitor", "research_monitor"),
		REPORT("report", "research_report");

		private String name;
		private String key;

		EnumResearchChannel(String name, String key) {
			this.name = name;
			this.key = key;
		}

		public String getName() {
			return this.name;
		}

		public String getKey() {
			return this.key;
		}

		public static EnumResearchChannel getByName(String name) {
			for (EnumResearchChannel c : values())
				if (c.name.equalsIgnoreCase(name))
					return c;
			return null;
		}
	}

}
