package us.myles_selim.starota.leek_duck.ditto;

import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.MessageChannel;
import discord4j.core.object.util.Permission;
import discord4j.core.object.util.PermissionSet;
import us.myles_selim.starota.commands.BotCommand;
import us.myles_selim.starota.commands.registry.CommandException;
import us.myles_selim.starota.enums.EnumPokemon;
import us.myles_selim.starota.leek_duck.LeekDuckData;
import us.myles_selim.starota.misc.utils.EmbedBuilder;
import us.myles_selim.starota.misc.utils.ImageHelper;
import us.myles_selim.starota.wrappers.StarotaServer;

public class CommandDitto extends BotCommand<StarotaServer> {

	public CommandDitto() {
		super("ditto", "Lists all Pokemon Ditto can take the form of.");
	}

	@Override
	public PermissionSet getCommandPermission() {
		return PermissionSet.of(Permission.SEND_MESSAGES, Permission.EMBED_LINKS);
	}

	@Override
	public void execute(String[] args, Message message, StarotaServer server, MessageChannel channel)
			throws CommandException {
		EmbedBuilder builder = new EmbedBuilder();
		builder.withAuthorIcon("https://leekduck.com/assets/img/favicon/favicon-32x32.png");
		builder.withAuthorName("Leek Duck").withColor(0xDAA0F7);
		builder.withAuthorUrl("https://leekduck.com");

		builder.withTitle("Dittoable Pokemon:").withUrl("https://leekduck.com/FindDitto/");
		builder.withThumbnail(ImageHelper.getOfficalArtwork(EnumPokemon.DITTO, 0));

		Message oldMessage = null;
		if (!LeekDuckData.areDittoablesLoaded())
			oldMessage = channel.createEmbed(LeekDuckData.LOADING_EMBED).block();
		for (EnumPokemon p : LeekDuckData.getDittoablePokemon())
			builder.appendDesc(" - " + p.getData().getName() + "\n");

		builder.withFooterText("Last updated");
		builder.withTimestamp(LeekDuckData.getDittoableCacheTime());

		if (oldMessage != null)
			oldMessage.edit((msg) -> msg.setEmbed(builder.build())).block();
		else
			channel.createEmbed(builder.build()).block();
	}

}
