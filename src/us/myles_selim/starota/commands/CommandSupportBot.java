package us.myles_selim.starota.commands;

import java.util.List;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.EmbedBuilder;
import sx.blah.discord.util.RequestBuffer;
import us.myles_selim.starota.commands.registry.java.JavaCommand;

public class CommandSupportBot extends JavaCommand {

	private String botName;
	private long botId;

	public CommandSupportBot(String botName, long botId) {
		super("support" + botName, "Information on how to help support " + botName + ".");
		this.botName = botName;
		this.botId = botId;
	}

	@Override
	public List<String> getAliases() {
		List<String> aliases = super.getAliases();
		aliases.add("support");
		return aliases;
	}

	@Override
	public void execute(String[] args, IMessage message, IGuild guild, IChannel channel) {
		EmbedBuilder builder = new EmbedBuilder();
		builder.appendDesc("Thank you for your interest in helping support " + this.botName + "!\n"
				+ "If you visit the Patreon below you can support " + this.botName
				+ " and its developers.\n"
				+ "Every penny counts, but don't feel like you must donate if you use the bot.\n"
				+ "Supporting Starota may allow access to additional features.  More information on Patreon.\n"
				+ "https://patreon.com/Selim_042\n\n" + "You can also support " + this.botName
				+ " by voting for it to raise it in the rankings.\n" + "https://discordbots.org/bot/"
				+ this.botId);
		RequestBuffer.request(() -> channel.sendMessage(builder.build()));
	}

}
