package us.myles_selim.starota.commands;

import java.util.List;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.EmbedBuilder;
import sx.blah.discord.util.RequestBuffer;
import sx.blah.discord.util.RequestBuffer.IVoidRequest;
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.commands.registry.Command;

public class CommandSupportStarota extends Command {

	public CommandSupportStarota() {
		super("supportStarota", "Information on how to help support Starota.");
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
		builder.appendDesc("Thank you for your interest in helping support " + Starota.BOT_NAME + "!\n"
				+ "If you visit the Patreon below you can support " + Starota.BOT_NAME
				+ " and its developers.\n"
				+ "Every penny counts, but don't feel like you must donate if you use the bot.\n"
				+ "https://patreon.com/Selim_042");
		RequestBuffer.request((IVoidRequest) () -> {
			channel.sendMessage(builder.build());
		});
	}

}
