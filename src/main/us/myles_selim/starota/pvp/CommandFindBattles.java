package us.myles_selim.starota.pvp;

import java.util.EnumSet;
import java.util.List;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.handle.obj.Permissions;
import sx.blah.discord.util.EmbedBuilder;
import us.myles_selim.starota.commands.StarotaCommand;
import us.myles_selim.starota.commands.registry.PrimaryCommandHandler;
import us.myles_selim.starota.misc.data_types.Pair;
import us.myles_selim.starota.profiles.PlayerProfile;
import us.myles_selim.starota.wrappers.StarotaServer;

public class CommandFindBattles extends StarotaCommand {

	private static final int PLAYERS_PER_PAGE = 10;

	public CommandFindBattles() {
		super("findBattles", "Lists all users that are battle ready.");
	}

	@Override
	public EnumSet<Permissions> getCommandPermissions() {
		return EnumSet.of(Permissions.SEND_MESSAGES, Permissions.EMBED_LINKS);
	}

	@Override
	public void execute(String[] args, IMessage message, StarotaServer server, IChannel channel)
			throws Exception {
		int page = 1;
		if (args.length > 1) {
			try {
				page = Integer.parseInt(args[1]);
			} catch (NumberFormatException e) {}
		}
		EmbedBuilder builder = new EmbedBuilder();
		builder.withTitle("Available Trainers:");
		List<Pair<PlayerProfile, Long>> battlers = server.findBattle();
		if (battlers.isEmpty()) {
			builder.withDesc("There are not currently any trainers available for battle.");
			channel.sendMessage(builder.build());
			return;
		}
		for (int i = page - 1; i < page * PLAYERS_PER_PAGE && i < battlers.size(); i++) {
			Pair<PlayerProfile, Long> battler = battlers.get(i);
			PlayerProfile profile = battler.left;
			builder.appendDesc("**" + profile.getPoGoName() + "**\n");
			IUser user = profile.getDiscordUser();
			String nickname = user.getNicknameForGuild(server.getDiscordGuild());
			builder.appendDesc(" - **Discord**: "
					+ (nickname == null ? user.getName() + "#" + user.getDiscriminator()
							: nickname + " (_" + user.getName() + "#" + user.getDiscriminator() + "_)")
					+ "\n");
			if (profile.getTrainerCode() != -1)
				builder.appendDesc(" - **Trainer Code**: " + profile.getTrainerCodeString() + "\n");
			int hours = (int) ((battler.right / 3600000) % 24);
			int minutes = (int) ((battler.right / 60000) % 60);
			builder.appendDesc(String.format(" - **Time Remaining**: %01d:%02d\n", hours, minutes));
		}
		builder.appendDesc("\nFrom this list, find any trainers that you are ultra/best friends with "
				+ "and contact them directly to arrange a battle!\n");
		if (page < battlers.size() / PLAYERS_PER_PAGE)
			builder.appendDesc("\nTo view the next page, use `"
					+ PrimaryCommandHandler.getPrefix(server.getDiscordGuild()) + getName() + " "
					+ (page + 1) + "`");
		builder.appendDesc("\n**Page**: " + page + "/" + ((battlers.size() / PLAYERS_PER_PAGE) + 1));
		channel.sendMessage(builder.build());
	}

}
