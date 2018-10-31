package us.myles_selim.starota.commands;

import java.util.List;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.commands.registry.Command;

public class CommandChangelog extends Command {

	public CommandChangelog() {
		super("changelog");
	}

	@Override
	public List<String> getAliases() {
		List<String> aliases = super.getAliases();
		aliases.add("changes");
		aliases.add("whatsnew");
		return aliases;
	}

	@Override
	public void execute(String[] args, IMessage message, IGuild guild, IChannel channel) {
		channel.sendMessage("```" + Starota.CHANGELOG + "```");
	}

}
