package us.myles_selim.starota.commands;

import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.MessageChannel;
import discord4j.core.object.util.Permission;
import discord4j.core.object.util.PermissionSet;
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.commands.registry.CommandException;
import us.myles_selim.starota.commands.registry.java.JavaCommand;
import us.myles_selim.starota.misc.utils.EmbedBuilder;
import us.myles_selim.starota.misc.utils.MiscUtils;
import us.myles_selim.starota.misc.utils.StarotaConstants;

public class CommandCredits extends JavaCommand {

	public CommandCredits() {
		super("credits", "Displays the credits for " + Starota.BOT_NAME + ".");
	}

	@Override
	public PermissionSet getCommandPermission() {
		return PermissionSet.of(Permission.SEND_MESSAGES, Permission.EMBED_LINKS);
	}

	@Override
	public void execute(String[] args, Message message, Guild guild, MessageChannel channel)
			throws CommandException {
		EmbedBuilder builder = new EmbedBuilder();
		builder.withTitle("Credits for " + Starota.BOT_NAME + " v" + StarotaConstants.VERSION);

		builder.appendDesc("**General Development**: Selim_042: [GitHub](http://github.com/Selim042) | "
				+ "[Twitter](http://twitter.com/Selim_042)\n");
		builder.appendDesc("**Discord4J**: [austinv11](https://github.com/austinv11)\n");
		builder.appendDesc("**Pokemon Go Database**: [Pokemon Go Hub](https://pokemongohub.net/)\n");
		builder.appendDesc(
				"**Raid Boss, Field Research, and Egg Information**: [The Silph Road](https://thesilphroad.com/)\n");
		builder.appendDesc("**Ditto Information**: [Leek Duck](https://leekduck.com)\n");
		builder.appendDesc("**Weather**: [AccuWeather](https://accuweather.com/)\n");

		StringBuilder editors = new StringBuilder();
		editors.append("**Article Editors**:\n");
		Guild supportServer = Starota.getSupportServer();
		for (Member u : MiscUtils.getMembersByRole(supportServer, StarotaConstants.EDITOR_ROLE_ID))
			editors.append(String.format(" - %s#%s\n", u.getUsername(), u.getDiscriminator()));
		builder.appendDesc(editors.toString());

		channel.createEmbed(builder.build()).block();
	}

}
