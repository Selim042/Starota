package us.myles_selim.starota.trading.commands;

import java.util.EnumSet;
import java.util.List;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.Permissions;
import sx.blah.discord.util.EmbedBuilder;
import us.myles_selim.starota.commands.StarotaCommand;
import us.myles_selim.starota.wrappers.StarotaServer;

public class CommandTradeboardHelp extends StarotaCommand {

	public CommandTradeboardHelp() {
		super("tradeboardHelp", "Displays tradeboard help information.");
	}

	@Override
	public EnumSet<Permissions> getCommandPermissions() {
		return EnumSet.of(Permissions.SEND_MESSAGES, Permissions.EMBED_LINKS);
	}

	@Override
	public List<String> getAliases() {
		List<String> aliases = super.getAliases();
		aliases.add("tradeHelp");
		return aliases;
	}

	@Override
	public void execute(String[] args, IMessage message, StarotaServer server, IChannel channel) {
		EmbedBuilder builder = new EmbedBuilder();
		builder.withTitle("Tradeboard Help");
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
		channel.sendMessage(builder.build());
	}

}
