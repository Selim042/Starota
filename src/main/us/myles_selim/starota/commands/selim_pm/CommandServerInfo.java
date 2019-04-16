package us.myles_selim.starota.commands.selim_pm;

import java.util.List;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.EmbedBuilder;
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
	public void execute(String[] args, IMessage message, IGuild guild, IChannel channel)
			throws Exception {
		if (args.length < 2) {
			this.sendUsage(PrimaryCommandHandler.DEFAULT_PREFIX, channel);
			return;
		}
		String enteredServerName = "";
		boolean byName = true;
		IGuild targetGuild = null;
		try {
			targetGuild = Starota.getGuild(Long.parseLong(args[1]));
			byName = false;
		} catch (NumberFormatException e) {
			for (int i = 1; i < args.length; i++)
				enteredServerName += args[i] + " ";
			enteredServerName = enteredServerName.substring(0, enteredServerName.length() - 1);
			for (IGuild g : Starota.getClient().getGuilds()) {
				if (g.getName().equalsIgnoreCase(enteredServerName)) {
					targetGuild = g;
					break;
				}
			}
		}
		if (targetGuild == null && byName) {
			List<IGuild> guilds = Starota.getClient().getGuilds();
			String[] possibleGuilds = new String[guilds.size()];
			for (int i = 0; i < guilds.size(); i++)
				possibleGuilds[i] = guilds.get(i).getName();
			String[] suggestions = MiscUtils.getSuggestions(possibleGuilds, enteredServerName, 5);
			EmbedBuilder builder = new EmbedBuilder();
			builder.withTitle("Did you mean...?");
			for (String s : suggestions)
				builder.appendDesc(" - " + s + "\n");
			channel.sendMessage(builder.build());
			return;
		}
		if (targetGuild == null) {
			channel.sendMessage("Guild with id \"" + args[1] + "\" not found.");
			return;
		}

		StarotaServer server = StarotaServer.getServer(targetGuild);
		EmbedBuilder builder = new EmbedBuilder();

		IUser targetOwner = targetGuild.getOwner();
		builder.withAuthorIcon(targetOwner.getAvatarURL()).withAuthorName(targetOwner.getName());
		builder.withTitle(targetGuild.getName());
		builder.withThumbnail(targetGuild.getIconURL());

		builder.appendField("Users:", Integer.toString(targetGuild.getTotalMemberCount()), true);
		builder.appendField("Voter Ratio:", server.getVoterPercent() + "%", true);

		String modulesString = "";
		for (StarotaModule m : StarotaModule.getDisabledModules(server))
			modulesString += m.getName() + ", ";
		if (!modulesString.isEmpty())
			builder.appendField("Disabled Modules:",
					modulesString.substring(0, modulesString.length() - 2), false);
		else
			builder.appendField("Disabled Modules:", "None", false);

		channel.sendMessage(builder.build());
	}

}
