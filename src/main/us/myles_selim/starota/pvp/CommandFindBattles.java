package us.myles_selim.starota.pvp;

import java.util.List;

import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.MessageChannel;
import discord4j.core.object.util.Permission;
import discord4j.core.object.util.PermissionSet;
import us.myles_selim.starota.commands.BotCommand;
import us.myles_selim.starota.commands.registry.CommandException;
import us.myles_selim.starota.commands.registry.PrimaryCommandHandler;
import us.myles_selim.starota.misc.data_types.Pair;
import us.myles_selim.starota.misc.utils.EmbedBuilder;
import us.myles_selim.starota.profiles.PlayerProfile;
import us.myles_selim.starota.wrappers.StarotaServer;

public class CommandFindBattles extends BotCommand<StarotaServer> {

	private static final int PLAYERS_PER_PAGE = 10;

	public CommandFindBattles() {
		super("findBattles", "Lists all users that are battle ready.");
	}

	@Override
	public PermissionSet getCommandPermission() {
		return PermissionSet.of(Permission.SEND_MESSAGES, Permission.EMBED_LINKS);
	}

	@Override
	public void execute(String[] args, Message message, StarotaServer server, MessageChannel channel)
			throws CommandException {
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
			channel.createEmbed(builder.build()).block();
			return;
		}
		for (int i = page - 1; i < page * PLAYERS_PER_PAGE && i < battlers.size(); i++) {
			Pair<PlayerProfile, Long> battler = battlers.get(i);
			PlayerProfile profile = battler.left;
			builder.appendDesc("**" + profile.getPoGoName() + "**\n");
			Member user = profile.getDiscordMember(server.getDiscordGuild());
			String nickname = user.getDisplayName();
			builder.appendDesc(" - **Discord**: " + (nickname == null
					? user.getUsername() + "#" + user.getDiscriminator()
					: nickname + " (_" + user.getUsername() + "#" + user.getDiscriminator() + "_)")
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
		channel.createEmbed(builder.build()).block();
	}

}
