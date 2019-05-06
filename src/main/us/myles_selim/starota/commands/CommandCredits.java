package us.myles_selim.starota.commands;

import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.TextChannel;
import discord4j.core.object.util.Permission;
import discord4j.core.object.util.PermissionSet;
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.commands.registry.java.JavaCommand;
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
	public void execute(String[] args, Message message, Guild guild, TextChannel channel) {
		channel.createEmbed((e) -> {
			e.setTitle("Credits for " + Starota.BOT_NAME + " v" + StarotaConstants.VERSION);

			StringBuilder desc = new StringBuilder();
			desc.append("**General Development**: Selim_042: [GitHub](http://github.com/Selim042) | "
					+ "[Twitter](http://twitter.com/Selim_042)\n");
			desc.append("**Discord4J**: [austinv11](https://github.com/austinv11)\n");
			desc.append("**Pokemon Go Database**: [Pokemon Go Hub](https://pokemongohub.net/)\n");
			desc.append(
					"**Raid Boss, Field Research, and Egg Information**: [The Silph Road](https://thesilphroad.com/)\n");
			desc.append("**Ditto Information**: [Leek Duck](https://leekduck.com)\n");
			desc.append("**Maps**: [MapBox](https://www.mapbox.com/)\n");

			desc.append("**Article Editors**:\n");
			Guild supportServer = Starota.getSupportServer();
			for (Member m : supportServer.getMembers()
					.filter((m) -> m.getRoles()
							.any((r) -> r.getId().equals(StarotaConstants.EDITOR_ROLE_ID)).block())
					.collectList().block())
				desc.append(String.format(" - %s#%s\n", m.getUsername(), m.getDiscriminator()));
			e.setDescription(desc.toString());
		});
	}

}
