package us.myles_selim.starota.commands.selim_pm;

import java.util.List;

import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.TextChannel;
import discord4j.core.object.util.Image;
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.commands.registry.PrimaryCommandHandler;
import us.myles_selim.starota.commands.registry.java.JavaCommand;
import us.myles_selim.starota.misc.utils.MiscUtils;
import us.myles_selim.starota.modules.StarotaModule;
import us.myles_selim.starota.wrappers.StarotaServer;

public class CommandServerInfo extends JavaCommand {

	public CommandServerInfo() {
		super("serverInfo", "Displays information about the given server.");
	}

	@Override
	public String getGeneralUsage() {
		return "<server (id or name)>";
	}

	@Override
	public void execute(String[] args, Message message, Guild guild, TextChannel channel)
			throws Exception {
		if (args.length < 2) {
			this.sendUsage(PrimaryCommandHandler.DEFAULT_PREFIX, channel);
			return;
		}
		String enteredServerName = "";
		boolean byName = true;
		Guild targetGuild = null;
		try {
			targetGuild = Starota.getGuild(Long.parseLong(args[1]));
			byName = false;
		} catch (NumberFormatException e) {
			for (int i = 1; i < args.length; i++)
				enteredServerName += args[i] + " ";
			enteredServerName = enteredServerName.substring(0, enteredServerName.length() - 1);
			for (Guild g : Starota.getClient().getGuilds().collectList().block()) {
				if (g.getName().equalsIgnoreCase(enteredServerName)) {
					targetGuild = g;
					break;
				}
			}
		}
		if (targetGuild == null && byName) {
			List<Guild> guilds = Starota.getClient().getGuilds().collectList().block();
			String[] possibleGuilds = new String[guilds.size()];
			for (int i = 0; i < guilds.size(); i++)
				possibleGuilds[i] = guilds.get(i).getName();
			String[] suggestions = MiscUtils.getSuggestions(possibleGuilds, enteredServerName, 5);
			channel.createEmbed((e) -> {
				e.setTitle("Did you mean...?");
				StringBuilder desc = new StringBuilder();
				for (String s : suggestions)
					desc.append(" - " + s + "\n");
				e.setDescription(desc.toString());
			});
			return;
		}
		if (targetGuild == null) {
			channel.createMessage("Guild with id \"" + args[1] + "\" not found.");
			return;
		}

		final Guild fTargetGuild = targetGuild;
		StarotaServer server = StarotaServer.getServer(targetGuild);
		channel.createEmbed((e) -> {
			Member targetOwner = fTargetGuild.getOwner().block();
			e.setAuthor(targetOwner.getUsername(), null, targetOwner.getAvatarUrl());
			e.setTitle(fTargetGuild.getName());
			e.setThumbnail(fTargetGuild.getIconUrl(Image.Format.PNG).orElse(null));

			e.addField("Users:", Integer.toString(fTargetGuild.getMemberCount().orElse(-1)), true);
			e.addField("Voter Ratio:", server.getVoterPercent() + "%", true);

			String modulesString = "";
			for (StarotaModule m : StarotaModule.getDisabledModules(server))
				modulesString += m.getName() + ", ";
			if (!modulesString.isEmpty())
				e.addField("Disabled Modules:", modulesString.substring(0, modulesString.length() - 2),
						false);
			else
				e.addField("Disabled Modules:", "None", false);
		});
	}

}
