package us.myles_selim.starota.raids;

import java.util.List;

import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.TextChannel;
import discord4j.core.object.util.Permission;
import discord4j.core.object.util.PermissionSet;
import discord4j.core.object.util.Snowflake;
import us.myles_selim.ebs.EBStorage;
import us.myles_selim.starota.commands.StarotaCommand;
import us.myles_selim.starota.reaction_messages.ReactionMessage;
import us.myles_selim.starota.reaction_messages.ReactionMessageRegistry;
import us.myles_selim.starota.webserver.webhooks.WebhookRaid;
import us.myles_selim.starota.webserver.webhooks.reaction_messages.WebhookRaidReactionMessage;
import us.myles_selim.starota.wrappers.StarotaServer;

public class CommandRaid extends StarotaCommand {

	public CommandRaid() {
		super("raid", "Makes a new raid post.");
	}

	@Override
	public PermissionSet getCommandPermission() {
		return PermissionSet.of(Permission.SEND_MESSAGES, Permission.EMBED_LINKS,
				Permission.USE_EXTERNAL_EMOJIS, Permission.ADD_REACTIONS, Permission.MANAGE_MESSAGES);
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
	public void execute(String[] args, Message message, StarotaServer server, TextChannel channel)
			throws Exception {
		if (args[0].equalsIgnoreCase("raid")) {
			boolean needsTier = true;
			String topic = channel.getTopic().orElse(null);
			if (topic != null && topic.contains("Key: ")) {
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
						channel.createMessage(
								"**Usage**: " + server.getPrefix() + this.getName() + " [time]");
						return;
					}
					ReactionMessage rMsg = ReactionMessageRegistry.getRegistry(channel.getClient())
							.getMessage(key);
					if (rMsg instanceof WebhookRaidReactionMessage) {
						WebhookRaid raidData = ((WebhookRaidReactionMessage) rMsg).getRaidData();
						new RaidReactionMessage(raidData.getPokemon(), raidData.getForm(),
								raidData.level, args[1], raidData.getGymName()).createMessage(channel);
						return;
					} else
						needsTier = true;
				}
			}
			if (needsTier) {
				channel.createMessage("Please specify raid tier in command name. Example: `"
						+ server.getPrefix() + "raid5 " + getGeneralUsage() + "`");
				return;
			}
		}
		if (args.length < 3) {
			channel.createMessage(
					"**Usage**: " + server.getPrefix() + args[0] + " " + getGeneralUsage());
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
			channel.createMessage("Failed to get raid tier");
			return;
		}
		TextChannel sendChannel = getSendChannel(server, channel);
		new RaidReactionMessage(tier, args[1], location).createMessage(sendChannel);
		if (!sendChannel.equals(channel))
			channel.createMessage("Posted raid in " + sendChannel + ".");
	}

	private static TextChannel getSendChannel(StarotaServer server, TextChannel msgChannel) {
		if (!server.getData().containsKey(CommandSetRaidEChannel.CHANNELS_KEY))
			return msgChannel;
		EBStorage channels = server.getData().get(CommandSetRaidEChannel.CHANNELS_KEY, EBStorage.class);
		if (channels.containsKey(msgChannel.getId().asString())) {
			long channelId = channels.get(msgChannel.getId().asString(), Long.class);
			if (channelId == -1)
				return msgChannel;
			return (TextChannel) server.getDiscordGuild().getChannelById(Snowflake.of(channelId))
					.block();
		}
		return msgChannel;
	}

}
