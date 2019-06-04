package us.myles_selim.starota.profiles.commands;

import java.awt.Color;
import java.util.EnumSet;
import java.util.LinkedList;
import java.util.List;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.handle.obj.Permissions;
import sx.blah.discord.util.EmbedBuilder;
import us.myles_selim.starota.commands.BotCommand;
import us.myles_selim.starota.profiles.PlayerProfile;
import us.myles_selim.starota.wrappers.StarotaServer;

public class CommandGetProfilelessPlayers extends BotCommand<StarotaServer> {

	public CommandGetProfilelessPlayers() {
		super("getProfilelessPlayers");
	}

	@Override
	public Permissions requiredUsePermission() {
		return Permissions.ADMINISTRATOR;
	}

	@Override
	public EnumSet<Permissions> getCommandPermissions() {
		return EnumSet.of(Permissions.SEND_MESSAGES, Permissions.EMBED_LINKS);
	}

	@Override
	public void execute(String[] args, IMessage message, StarotaServer server, IChannel channel) {
		List<PlayerProfile> profiles = server.getProfiles();
		List<String> players = new LinkedList<>();
		for (IUser user : server.getDiscordGuild().getUsers()) {
			boolean found = false;
			if (profiles != null) {
				for (PlayerProfile p : profiles) {
					if (p != null && p.getDiscordId() == user.getLongID()) {
						found = true;
						break;
					}
				}
			}
			if (!found)
				players.add(user.getDisplayName(server.getDiscordGuild()) + " (_" + user.getName() + "#"
						+ user.getDiscriminator() + "_)\n");
		}
		players.sort(null);
		channel.sendMessage("Players without a profile:");
		List<String> groups = new LinkedList<>();
		String line = "";
		for (String l : players) {
			if (line.length() + l.length() > 2048) {
				groups.add(line);
				line = l;
			} else
				line += l;
		}
		groups.add(line);
		for (String g : groups) {
			EmbedBuilder builder = new EmbedBuilder();
			builder.appendDesc(g).withColor(Color.getHSBColor(g.length() / 2048f, 0.5f, 1.0f));
			channel.sendMessage(builder.build());
			try {
				Thread.sleep(2500);
			} catch (InterruptedException e) {}
		}
	}

}
