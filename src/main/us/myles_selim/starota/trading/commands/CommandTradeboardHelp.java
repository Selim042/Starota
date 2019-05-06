package us.myles_selim.starota.trading.commands;

import java.util.List;

import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.TextChannel;
import discord4j.core.object.util.Permission;
import discord4j.core.object.util.PermissionSet;
import us.myles_selim.starota.commands.StarotaCommand;
import us.myles_selim.starota.misc.data_types.EmbedBuilder;
import us.myles_selim.starota.wrappers.StarotaServer;

public class CommandTradeboardHelp extends StarotaCommand {

	public CommandTradeboardHelp() {
		super("tradeboardHelp", "Displays tradeboard help information.");
	}

	@Override
	public PermissionSet getCommandPermission() {
		return PermissionSet.of(Permission.SEND_MESSAGES, Permission.EMBED_LINKS);
	}

	@Override
	public List<String> getAliases() {
		List<String> aliases = super.getAliases();
		aliases.add("tradeHelp");
		return aliases;
	}

	@Override
	public void execute(String[] args, Message message, StarotaServer server, TextChannel channel) {
		EmbedBuilder builder = new EmbedBuilder();
		builder.setTitle("Tradeboard Help");
		String prefix = server.getPrefix();
		builder.appendDesc(
				" - To look for a Pokemon that is on the tradeboard, you can use the command \"**"
						+ prefix
						+ "lookingFor**\".  It will post a searching trade if an existing trade is not found.\n"
						+ " - To post a Pokemon you want to trade, you can use the command \"**" + prefix
						+ "forTrade**\".  Before posting, it will check if any searching trade posts.\n"
						+ " - To check for specific trades without creating a new trade, you can use \"**"
						+ prefix + "findTrade**\".\n" + " - To remove a trade, you can use \"**" + prefix
						+ "removeTrade**\".\n\n" + "The \"**" + prefix
						+ "help [commandName]**\" command is always helpful if you are unsure.");
		channel.createEmbed(builder.build());
	}

}
