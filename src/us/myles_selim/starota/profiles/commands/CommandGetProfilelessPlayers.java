package us.myles_selim.starota.profiles.commands;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.handle.obj.Permissions;
import sx.blah.discord.util.EmbedBuilder;
import us.myles_selim.starota.commands.registry.Command;
import us.myles_selim.starota.profiles.PlayerProfile;
import us.myles_selim.starota.profiles.ProfileManager;

public class CommandGetProfilelessPlayers extends Command {

	public CommandGetProfilelessPlayers() {
		super("getProfilelessPlayers");
	}

	@Override
	public Permissions requiredPermission() {
		return Permissions.ADMINISTRATOR;
	}

	@Override
	public void execute(String[] args, IMessage message, IGuild guild, IChannel channel) {
		List<PlayerProfile> profiles = ProfileManager.getProfiles(guild);
		List<String> players = new LinkedList<>();
		for (IUser user : guild.getUsers()) {
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
				players.add(user.getDisplayName(guild) + " (_" + user.getName() + "#"
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
