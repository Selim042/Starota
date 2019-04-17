package us.myles_selim.starota.commands.selim_pm;

import com.google.gson.Gson;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import us.myles_selim.starota.commands.registry.java.JavaCommand;
import us.myles_selim.starota.misc.data_types.EmbedHolder;

public class CommandPreviewOwnerMessage extends JavaCommand {

	private static final Gson GSON = new Gson();

	public CommandPreviewOwnerMessage() {
		super("poMsg", "Preview a message to all Starota server owners.");
	}

	@Override
	public String getGeneralUsage() {
		return "<message> (in ``` followed by message type, json for embed json, anything else for normal message)";
	}

	@Override
	public void execute(String[] args, IMessage message, IGuild guild, IChannel channel)
			throws Exception {
		if (guild != null)
			return;
		String text = message.getContent().substring(message.getContent().indexOf(" ") + 1);
		int firstLineIndex = text.indexOf("\n");
		String type = text.substring(text.indexOf("`") + 3, firstLineIndex);
		text = text.substring(firstLineIndex, text.length() - 3);
		String[] lines = text.split("\n");

		if (type.equalsIgnoreCase("json")) {
			EmbedHolder embed = GSON.fromJson(text, EmbedHolder.class);
			if (embed != null) {
				if (embed.content != null && !embed.content.isEmpty())
					channel.sendMessage(embed.content, embed.embed);
				else
					channel.sendMessage(embed.embed);
				return;
			}
		}

		String out = "";
		for (String l : lines)
			out += l;
		if (!out.isEmpty())
			channel.sendMessage(out);
	}

}