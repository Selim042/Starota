package us.myles_selim.starota.commands;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.RequestBuffer;
import us.myles_selim.starota.commands.registry.Command;
import us.myles_selim.starota.profiles.SilphRoadUtils;

public class CommandTest extends Command {

	public CommandTest() {
		super("test");
	}

	@Override
	public void execute(String[] args, IMessage message, IGuild guild, IChannel channel) {
		if (args.length != 1)
			return;
		boolean hasCard = SilphRoadUtils.hasCard(args[1]);
		RequestBuffer.request(() -> {
			channel.sendMessage(args[1] + " has card: " + hasCard);
			return;
		});
		if (!hasCard)
			return;
		RequestBuffer.request(() -> {
			channel.sendMessage(SilphRoadUtils.getCard(args[1]));
			return;
		});
		RequestBuffer.request(() -> {
			channel.sendMessage(SilphRoadUtils.getCardAvatar(args[1]));
			return;
		});
	}

}
