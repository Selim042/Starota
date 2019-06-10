package us.myles_selim.starota.trading.commands;

import java.util.List;

import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.TextChannel;
import discord4j.core.object.util.Permission;
import discord4j.core.object.util.PermissionSet;
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.commands.BotCommand;
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
	public void execute(String[] args, Message message, StarotaServer server, TextChannel channel) {
		Member target;
		if (args.length == 1)
			target = message.getAuthor().get().asMember(server.getDiscordGuild().getId()).block();
		else {
			if (args.length != 2) {
				channel.createMessage("**Usage**: " + server.getPrefix() + this.getName() + " [target]");
				return;
			}
			target = Starota.findUser(args[1]).asMember(server.getDiscordGuild().getId()).block();
			if (target == null) {
				PlayerProfile profile = server.getProfile(args[1]);
				if (profile == null) {
					channel.createMessage("User \"" + args[1] + "\" not found");
					return;
				} else
					target = profile.getDiscordUser().asMember(server.getDiscordGuild().getId()).block();
			}
		}
		if (target == null) {
			channel.createMessage("User \"" + args[1] + "\" not found");
			return;
		}
		List<TradeboardPost> posts = server.getPosts(target);
		channel.createMessage(target.getDisplayName() + " has " + posts.size() + " active trade posts");
		for (TradeboardPost p : posts)
			channel.createEmbed(p.getPostEmbed(server));
	}

}
