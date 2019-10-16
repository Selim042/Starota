package us.myles_selim.starota.commands.selim_pm;

import java.util.List;

import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.MessageChannel;
import discord4j.core.object.entity.User;
import discord4j.core.object.util.Image;
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.commands.registry.CommandException;
import us.myles_selim.starota.commands.registry.PrimaryCommandHandler;
import us.myles_selim.starota.commands.registry.java.JavaCommand;
import us.myles_selim.starota.misc.utils.EmbedBuilder;
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
	public void execute(String[] args, Message message, Guild guild, MessageChannel channel)
			throws CommandException {
		if (args.length < 2) {
			this.sendUsage(PrimaryCommandHandler.DEFAULT_PREFIX, channel);
			return;
		}
		String enteredServerName = "";
		boolean byName = true;
		Guild targetGuild = null;
		List<Guild> guilds = Starota.getClient().getGuilds().collectList().block();
		try {
			targetGuild = Starota.getGuild(Long.parseLong(args[1]));
			byName = false;
		} catch (NumberFormatException e) {
			for (int i = 1; i < args.length; i++)
				enteredServerName += args[i] + " ";
			enteredServerName = enteredServerName.substring(0, enteredServerName.length() - 1);
			for (Guild g : guilds) {
				if (g.getName().equalsIgnoreCase(enteredServerName)) {
					targetGuild = g;
					break;
				}
			}
		}
		if (targetGuild == null && byName) {
			String[] possibleGuilds = new String[guilds.size()];
			for (int i = 0; i < guilds.size(); i++)
				possibleGuilds[i] = guilds.get(i).getName();
			String[] suggestions = MiscUtils.getSuggestions(possibleGuilds, enteredServerName, 5);
			EmbedBuilder builder = new EmbedBuilder();
			builder.withTitle("Did you mean...?");
			for (String s : suggestions)
				builder.appendDesc(" - " + s + "\n");
			channel.createEmbed(builder.build()).block();
			return;
		}
		if (targetGuild == null) {
			channel.createMessage("Guild with id \"" + args[1] + "\" not found.").block();
			return;
		}

		StarotaServer server = StarotaServer.getServer(targetGuild);
		EmbedBuilder builder = new EmbedBuilder();

		User targetOwner = targetGuild.getOwner().block();
		builder.withAuthorIcon(targetOwner.getAvatarUrl()).withAuthorName(targetOwner.getUsername());
		builder.withTitle(targetGuild.getName());
		builder.withThumbnail(targetGuild.getIconUrl(Image.Format.GIF).orElse(
				targetGuild.getIconUrl(Image.Format.JPEG).orElse(targetGuild.getIconUrl(Image.Format.PNG)
						.orElse(targetGuild.getIconUrl(Image.Format.WEB_P).get()))));

		builder.appendField("Users:", Integer.toString(targetGuild.getMemberCount().getAsInt()), true);
		builder.appendField("Voter Ratio:", server.getVoterPercent() + "%", true);

		String modulesString = "";
		for (StarotaModule m : StarotaModule.getDisabledModules(server))
			modulesString += m.getName() + ", ";
		if (!modulesString.isEmpty())
			builder.appendField("Disabled Modules:",
					modulesString.substring(0, modulesString.length() - 2), false);
		else
			builder.appendField("Disabled Modules:", "None", false);

		channel.createEmbed(builder.build()).block();
	}

}
