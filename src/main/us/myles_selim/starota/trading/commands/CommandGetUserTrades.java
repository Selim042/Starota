package us.myles_selim.starota.trading.commands;

import java.util.EnumSet;
import java.util.List;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.handle.obj.Permissions;
import sx.blah.discord.util.RequestBuffer;
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
	public EnumSet<Permissions> getCommandPermissions() {
		return EnumSet.of(Permissions.SEND_MESSAGES, Permissions.EMBED_LINKS);
	}

	@Override
	public String getGeneralUsage() {
		return "<target>";
	}

	@Override
	public void execute(String[] args, IMessage message, StarotaServer server, IChannel channel) {
		IUser target;
		if (args.length == 1)
			target = message.getAuthor();
		else {
			if (args.length != 2) {
				channel.sendMessage("**Usage**: " + server.getPrefix() + this.getName() + " [target]");
				return;
			}
			target = Starota.findUser(args[1]);
			if (target == null) {
				PlayerProfile profile = server.getProfile(args[1]);
				if (profile == null) {
					channel.sendMessage("User \"" + args[1] + "\" not found");
					return;
				} else
					target = profile.getDiscordUser();
			}
		}
		if (target == null) {
			channel.sendMessage("User \"" + args[1] + "\" not found");
			return;
		}
		List<TradeboardPost> posts = server.getPosts(target);
		channel.sendMessage(target.getDisplayName(server.getDiscordGuild()) + " has " + posts.size()
				+ " active trade posts");
		for (TradeboardPost p : posts)
			RequestBuffer.request(() -> channel.sendMessage(p.getPostEmbed(server)));
	}

}
