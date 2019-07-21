package us.myles_selim.starota.profiles.commands;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;

import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.MessageChannel;
import discord4j.core.object.util.Permission;
import discord4j.core.object.util.PermissionSet;
import us.myles_selim.starota.commands.BotCommand;
import us.myles_selim.starota.commands.registry.CommandException;
import us.myles_selim.starota.misc.utils.EmbedBuilder;
import us.myles_selim.starota.profiles.PlayerProfile;
import us.myles_selim.starota.wrappers.StarotaServer;

public class CommandGetProfilelessPlayers extends BotCommand<StarotaServer> {

	public CommandGetProfilelessPlayers() {
		super("getProfilelessPlayers");
	}

	@Override
	public Permission requiredUsePermission() {
		return Permission.ADMINISTRATOR;
	}

	@Override
	public PermissionSet getCommandPermission() {
		return PermissionSet.of(Permission.SEND_MESSAGES, Permission.EMBED_LINKS);
	}

	@Override
	public void execute(String[] args, Message message, StarotaServer server, MessageChannel channel)
			throws CommandException {
		List<PlayerProfile> profiles = server.getProfiles();
		List<String> players = new LinkedList<>();
		for (Member user : server.getDiscordGuild().getMembers().collectList().block()) {
			boolean found = false;
			if (profiles != null) {
				for (PlayerProfile p : profiles) {
					if (p != null && p.getDiscordId() == user.getId().asLong()) {
						found = true;
						break;
					}
				}
			}
			if (!found)
				players.add(user.getDisplayName() + "\n");
		}
		players.sort(null);
		channel.createMessage("Players without a profile:");
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
			channel.createEmbed(builder.build()).block();
			try {
				Thread.sleep(2500);
			} catch (InterruptedException e) {}
		}
	}

}
