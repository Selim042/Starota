package us.myles_selim.starota.commands;

import java.util.EnumSet;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.Permissions;
import sx.blah.discord.util.EmbedBuilder;
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.commands.registry.java.JavaCommand;

public class CommandCredits extends JavaCommand {

	public CommandCredits() {
		super("credits", "Displays the credits for " + Starota.BOT_NAME + ".");
	}

	@Override
	public EnumSet<Permissions> getCommandPermissions() {
		return EnumSet.of(Permissions.SEND_MESSAGES, Permissions.EMBED_LINKS);
	}

	@Override
	public void execute(String[] args, IMessage message, IGuild guild, IChannel channel) {
		EmbedBuilder builder = new EmbedBuilder();
		builder.withTitle("Credits for " + Starota.BOT_NAME + " v" + Starota.VERSION);

		builder.appendDesc("**General Development**: Selim_042: [GitHub](http://github.com/Selim042) | "
				+ "[Twitter](http://twitter.com/Selim_042)\n");
		builder.appendDesc("**Discord4J**: [austinv11](https://github.com/austinv11)\n");
		builder.appendDesc("**Pokemon Go Database**: [Pokemon Go Hub](https://pokemongohub.net/)\n");
		builder.appendDesc(
				"**Raid Boss, Field Research, and Egg Information**: [The Silph Road](https://thesilphroad.com/)\n");
		builder.appendDesc("**Event Information**: [Leek Duck](https://leekduck.com)\n");
		builder.appendDesc("**Maps**: [MapBox](https://www.mapbox.com/)\n");

		channel.sendMessage(builder.build());
	}

}
