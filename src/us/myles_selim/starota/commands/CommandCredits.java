package us.myles_selim.starota.commands;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.EmbedBuilder;
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.commands.registry.java.JavaCommand;

public class CommandCredits extends JavaCommand {

	public CommandCredits() {
		super("credits", "Displays the credits for " + Starota.BOT_NAME + ".");
	}

	@Override
	public void execute(String[] args, IMessage message, IGuild guild, IChannel channel) {
		EmbedBuilder builder = new EmbedBuilder();
		builder.withTitle("Credits for " + Starota.BOT_NAME + " v" + Starota.VERSION);

		builder.appendDesc("**General Development**: Selim_042 (http://github.com/Selim042)\n");
		builder.appendDesc("**Discord4J**: austinv11 (https://github.com/austinv11)\n");
		builder.appendDesc("**Pokemon Sprites**: PokeAPI (https://github.com/PokeAPI/sprites)\n");
		builder.appendDesc("**Pokemon Go Database**: Pokemon Go Hub (https://pokemongohub.net/)\n");

		channel.sendMessage(builder.build());
	}

}
