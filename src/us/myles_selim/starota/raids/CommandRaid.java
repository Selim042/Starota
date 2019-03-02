package us.myles_selim.starota.raids;

import java.util.List;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import us.myles_selim.ebs.EBStorage;
import us.myles_selim.starota.commands.StarotaCommand;
import us.myles_selim.starota.reaction_messages.ReactionMessage;
import us.myles_selim.starota.reaction_messages.ReactionMessageRegistry;
import us.myles_selim.starota.webserver.webhooks.WebhookRaid;
import us.myles_selim.starota.webserver.webhooks.reaction_messages.WebhookRaidReactionMessage;
import us.myles_selim.starota.wrappers.StarotaServer;

public class CommandRaid extends StarotaCommand {

	// Matches WebhookRaidReactionMessage.TOPIC_TEMPLATE
	private static final String TOPIC_REGEX = "";

	public CommandRaid() {
		super("raid", "Makes a new raid post.");
	}

	@Override
	public List<String> getAliases() {
		List<String> aliases = super.getAliases();
		for (int i = 1; i <= 6; i++)
			aliases.add("raid" + i);
		aliases.add("raidex");
		return aliases;
	}

	@Override
	public String getGeneralUsage() {
		return "[time] [location]";
	}

	@Override
	public void execute(String[] args, IMessage message, StarotaServer server, IChannel channel)
			throws Exception {
		if (args[0].equalsIgnoreCase("raid")) {
			boolean needsTier = true;
			String topic = channel.getTopic();
			if (topic.contains("Key: ")) {
				System.out.println("key found");
				long key = -1;
				int index = topic.indexOf("(Key: 0x");
				try {
					key = Long.parseLong(topic.substring(index + 8, index + 24), 16);
					needsTier = false;
				} catch (NumberFormatException e) {
					needsTier = true;
				}
				if (key == -1)
					needsTier = true;
				if (!needsTier) {
					if (args.length < 2) {
						channel.sendMessage(
								"**Usage**: " + server.getPrefix() + this.getName() + " [time]");
						return;
					}
					ReactionMessage rMsg = ReactionMessageRegistry.getMessage(key);
					if (rMsg instanceof WebhookRaidReactionMessage) {
						WebhookRaid raidData = ((WebhookRaidReactionMessage) rMsg).getRaidData();
						new RaidReactionMessage(raidData.getPokemon(), raidData.getForm(),
								raidData.level, args[1], raidData.getGymName()).sendMessage(channel);
						return;
					} else
						needsTier = true;
				}
			}
			if (needsTier) {
				channel.sendMessage("Please specify raid tier in command name. Example: `"
						+ server.getPrefix() + "raid5 " + getGeneralUsage() + "`");
				return;
			}
		}
		if (args.length < 3) {
			channel.sendMessage("**Usage**: " + server.getPrefix() + args[0] + " " + getGeneralUsage());
			return;
		}
		String location = "";
		for (int i = 2; i < args.length; i++)
			location += args[i] + " ";
		int tier;
		switch (args[0].toLowerCase()) {
		case "raid1":
			tier = 1;
			break;
		case "raid2":
			tier = 2;
			break;
		case "raid3":
			tier = 3;
			break;
		case "raid4":
			tier = 4;
			break;
		case "raid5":
			tier = 5;
			break;
		case "raid6":
		case "raidex":
			tier = 6;
			break;
		default:
			channel.sendMessage("Failed to get raid tier");
			return;
		}
		IChannel sendChannel = getSendChannel(server, channel);
		new RaidReactionMessage(tier, args[1], location).sendMessage(sendChannel);
		if (!sendChannel.equals(channel))
			channel.sendMessage("Posted raid in " + sendChannel + ".");
	}

	private static IChannel getSendChannel(StarotaServer server, IChannel msgChannel) {
		if (!server.getOptions().containsKey(CommandSetRaidEChannel.CHANNELS_KEY))
			return msgChannel;
		EBStorage channels = server.getOptions().get(CommandSetRaidEChannel.CHANNELS_KEY,
				EBStorage.class);
		if (channels.containsKey(msgChannel.getStringID())) {
			long channelId = channels.get(msgChannel.getStringID(), Long.class);
			if (channelId == -1)
				return msgChannel;
			return server.getDiscordGuild().getChannelByID(channelId);
		}
		return msgChannel;
	}

}
