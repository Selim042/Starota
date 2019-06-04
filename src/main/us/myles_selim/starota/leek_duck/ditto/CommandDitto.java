package us.myles_selim.starota.leek_duck.ditto;

import java.util.EnumSet;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.Permissions;
import sx.blah.discord.util.EmbedBuilder;
import us.myles_selim.starota.commands.BotCommand;
import us.myles_selim.starota.enums.EnumPokemon;
import us.myles_selim.starota.leek_duck.LeekDuckData;
import us.myles_selim.starota.wrappers.StarotaServer;

public class CommandDitto extends BotCommand<StarotaServer> {

	public CommandDitto() {
		super("ditto", "Lists all Pokemon Ditto can take the form of.");
	}

	@Override
	public EnumSet<Permissions> getCommandPermissions() {
		return EnumSet.of(Permissions.SEND_MESSAGES, Permissions.EMBED_LINKS);
	}

	@Override
	public void execute(String[] args, IMessage message, StarotaServer server, IChannel channel)
			throws Exception {
		EmbedBuilder builder = new EmbedBuilder();
		builder.withAuthorIcon("https://leekduck.com/assets/img/favicon/favicon-32x32.png");
		builder.withAuthorName("Leek Duck").withColor(0xDAA0F7);
		builder.withAuthorUrl("https://leekduck.com");

		builder.withTitle("Dittoable Pokemon:").withUrl("https://leekduck.com/FindDitto/");
		builder.withThumbnail(EnumPokemon.DITTO.getArtwork(0));

		IMessage oldMessage = null;
		if (!LeekDuckData.areDittoablesLoaded())
			oldMessage = channel.sendMessage(LeekDuckData.LOADING_EMBED);
		for (EnumPokemon p : LeekDuckData.getDittoablePokemon())
			builder.appendDesc(" - " + p.getName() + "\n");

		builder.withFooterText("Last updated");
		builder.withTimestamp(LeekDuckData.getDittoableCacheTime());

		if (oldMessage != null)
			oldMessage.edit(builder.build());
		else
			channel.sendMessage(builder.build());
	}

}
