package us.myles_selim.starota.trading.commands;

import java.util.List;

import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.MessageChannel;
import discord4j.core.object.util.Permission;
import discord4j.core.object.util.PermissionSet;
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.commands.BotCommand;
import us.myles_selim.starota.commands.registry.CommandException;
import us.myles_selim.starota.profiles.PlayerProfile;
import us.myles_selim.starota.trading.TradeboardPost;
import us.myles_selim.starota.wrappers.StarotaServer;

public class CommandGetUserTrades extends BotCommand<StarotaServer> {

	public CommandGetUserTrades() {
		super("getUserTrades", "Get trades either you posted or a specific user.");
	}

	@Override
	public PermissionSet getCommandPermission() {
		return PermissionSet.of(Permission.SEND_MESSAGES, Permission.EMBED_LINKS);
	}

	@Override
	public String getGeneralUsage() {
		return "<target>";
	}

	@Override
	public void execute(String[] args, Message message, StarotaServer server, MessageChannel channel)
			throws CommandException {
		Member target;
		if (args.length == 1)
			target = message.getAuthorAsMember().block();
		else {
			if (args.length != 2) {
				channel.createMessage("**Usage**: " + server.getPrefix() + this.getName() + " [target]")
						.block();
				return;
			}
			target = Starota.findUser(args[1]).asMember(server.getDiscordGuild().getId()).block();
			if (target == null) {
				PlayerProfile profile = server.getProfile(args[1]);
				if (profile == null) {
					channel.createMessage("User \"" + args[1] + "\" not found").block();
					return;
				} else
					target = profile.getDiscordMember(server.getDiscordGuild());
			}
		}
		if (target == null) {
			channel.createMessage("User \"" + args[1] + "\" not found").block();
			return;
		}
		List<TradeboardPost> posts = server.getPosts(target);
		channel.createMessage(target.getUsername() + " has " + posts.size() + " active trade posts");
		for (TradeboardPost p : posts)
			channel.createEmbed(p.getPostEmbed(server)).block();
	}

}
