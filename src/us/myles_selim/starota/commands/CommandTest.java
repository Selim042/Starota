package us.myles_selim.starota.commands;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.EmbedBuilder;
import us.myles_selim.starota.EnumTeam;
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.commands.registry.Command;
import us.myles_selim.starota.profiles.PlayerProfile;

public class CommandTest extends Command {

	public CommandTest() {
		super("test");
	}

	@Override
	public void execute(String[] args, IMessage message, IGuild guild, IChannel channel) {
		if (channel.getLongID() != 353267642410336256l)
			return;
		PlayerProfile profile = new PlayerProfile().setRealName("Myles").setPoGoName("042Selim")
				.setLevel(37).setDiscordId(134855940938661889l).setTeam(EnumTeam.INSTINCT)
				.setTrainerCode(123456789012l);
		EmbedBuilder builder = new EmbedBuilder();
		builder.withColor(profile.getTeam().getColor());
		builder.appendDesc("**Profile for " + profile.getPoGoName() + "**\n\n");
		IUser user = Starota.getUser(profile.getDiscordId());
		builder.appendDesc("**Trainer Level**: " + profile.getLevel() + "\n");
		builder.appendDesc("**Team**: " + profile.getTeam().getName() + "\n");
		if (user != null)
			builder.appendDesc("**Discord User**: " + user.getNicknameForGuild(guild) + " (_"
					+ user.getName() + "#" + user.getDiscriminator() + "_)\n");
		if (profile.getRealName() != null)
			builder.appendDesc("**Real Name**: " + profile.getRealName() + "\n");
		if (profile.getTrainerCode() != -1)
			builder.appendDesc("**Trainer Code**: " + profile.getTrainerCodeString() + "\n");
		channel.sendMessage(builder.build());

		// ProfileManager.setProfile(guild, user, profile);
	}

}
