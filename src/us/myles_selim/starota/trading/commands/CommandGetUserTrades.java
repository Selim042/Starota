package us.myles_selim.starota.trading.commands;

import java.util.List;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.RequestBuffer;
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.commands.registry.Command;
import us.myles_selim.starota.commands.registry.CommandRegistry;
import us.myles_selim.starota.profiles.PlayerProfile;
import us.myles_selim.starota.profiles.ProfileManager;
import us.myles_selim.starota.trading.Tradeboard;
import us.myles_selim.starota.trading.TradeboardPost;

public class CommandGetUserTrades extends Command {

	public CommandGetUserTrades() {
		super("getUserTrades", "Get trades either you posted or a specific user.");
	}

	@Override
	public String getGeneralUsage() {
		return "<target>";
	}

	@Override
	public void execute(String[] args, IMessage message, IGuild guild, IChannel channel) {
		IUser target;
		if (args.length == 1)
			target = message.getAuthor();
		else {
			if (args.length != 2) {
				channel.sendMessage(
						"**Usage**: " + CommandRegistry.getPrefix(guild) + this.getName() + " [target]");
				return;
			}
			target = Starota.findUser(args[1]);
			if (target == null) {
				PlayerProfile profile = ProfileManager.getProfile(guild, args[1]);
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
		List<TradeboardPost> posts = Tradeboard.getPosts(guild, target);
		channel.sendMessage(
				target.getDisplayName(guild) + " has " + posts.size() + " active trade posts");
		for (TradeboardPost p : posts)
			RequestBuffer.request(() -> {
				channel.sendMessage(Tradeboard.getPostEmbed(guild, p));
			});
	}

}
