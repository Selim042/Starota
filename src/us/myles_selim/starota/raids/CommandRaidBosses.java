package us.myles_selim.starota.raids;

import java.util.List;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.EmbedBuilder;
import sx.blah.discord.util.RequestBuffer;
import us.myles_selim.starota.EmojiServerHelper;
import us.myles_selim.starota.ImageHelper;
import us.myles_selim.starota.commands.StarotaCommand;
import us.myles_selim.starota.silph_road.SilphRoadData;
import us.myles_selim.starota.silph_road.SilphRoadData.RaidBoss;
import us.myles_selim.starota.wrappers.StarotaServer;

public class CommandRaidBosses extends StarotaCommand {

	public CommandRaidBosses() {
		super("raidBosses", "Gets information on raid bosses for a specific tier.");
	}

	@Override
	public List<String> getAliases() {
		List<String> aliases = super.getAliases();
		aliases.add("bosses");
		return aliases;
	}

	@Override
	public String getGeneralUsage() {
		return "[tier]";
	}

	@Override
	public void execute(String[] args, IMessage message, StarotaServer server, IChannel channel)
			throws Exception {
		if (args.length < 2) {
			sendUsage(server.getPrefix(), channel);
			return;
		}
		int tier;
		if (args[1].equalsIgnoreCase("ex"))
			tier = 6;
		try {
			tier = Integer.parseInt(args[1]);
		} catch (NumberFormatException e) {
			sendUsage(server.getPrefix(), channel);
			return;
		}
		IMessage msg = null;
		if (!SilphRoadData.areBossesLoaded(tier))
			msg = RequestBuffer.request(() -> {
				return channel.sendMessage(SilphRoadData.LOADING_EMBED);
			}).get();
		EmbedBuilder builder = new EmbedBuilder();
		if (tier != 6)
			builder.withTitle("Tier " + tier + " Raid Bosses:");
		else
			builder.withTitle("EX Raid Bosses:");
		builder.withDescription("");
		for (RaidBoss b : SilphRoadData.getBosses(tier)) {
			String postfix = b.getForm() == null ? "" : "_" + b.getForm();
			builder.appendDescription(
					b.getPokemon() + (b.getForm() == null ? "" : " (" + b.getForm() + ") ")
							+ EmojiServerHelper.getEmoji(b.getPokemon() + postfix,
									ImageHelper.getOfficalArtwork(b.getPokemon(), b.getForm()))
							+ "\n");
		}
		IMessage msgF = msg;
		if (msgF != null)
			RequestBuffer.request(() -> msgF.edit(builder.build()));
		else
			RequestBuffer.request(() -> channel.sendMessage(builder.build()));
	}

}
