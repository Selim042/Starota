package us.myles_selim.starota.commands;

import java.util.List;

import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.TextChannel;
import discord4j.core.object.util.Permission;
import discord4j.core.object.util.PermissionSet;
import discord4j.core.object.util.Snowflake;
import us.myles_selim.starota.commands.registry.java.JavaCommand;

public class CommandSupportBot extends JavaCommand {

	private String botName;
	private Snowflake botId;

	public CommandSupportBot(String botName, Snowflake botId) {
		super("support" + botName, "Information on how to help support " + botName + ".");
		this.botName = botName;
		this.botId = botId;
	}

	@Override
	public PermissionSet getCommandPermission() {
		return PermissionSet.of(Permission.SEND_MESSAGES, Permission.EMBED_LINKS);
	}

	@Override
	public List<String> getAliases() {
		List<String> aliases = super.getAliases();
		aliases.add("support");
		return aliases;
	}

	@Override
	public void execute(String[] args, Message message, Guild guild, TextChannel channel) {
		channel.createEmbed((e) -> e.setDescription("Thank you for your interest in helping support "
				+ this.botName + "!\n" + "If you visit the Patreon below you can support " + this.botName
				+ " and its developers.\n"
				+ "Every penny counts, but don't feel like you must donate if you use the bot.\n"
				+ "Supporting Starota may allow access to additional features.  More information on Patreon.\n"
				+ "https://patreon.com/Selim_042\n\n" + "You can also support " + this.botName
				+ " by voting for it to raise it in the rankings.\n" + "https://discordbots.org/bot/"
				+ this.botId));
	}

}
