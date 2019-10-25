package us.myles_selim.starota.search.pokemon;

import java.util.Collection;
import java.util.function.Consumer;

import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.MessageChannel;
import discord4j.core.object.entity.TextChannel;
import discord4j.core.object.util.Permission;
import discord4j.core.object.util.PermissionSet;
import discord4j.core.spec.EmbedCreateSpec;
import us.myles_selim.starota.commands.BotCommand;
import us.myles_selim.starota.commands.registry.CommandException;
import us.myles_selim.starota.enums.EnumPokemon;
import us.myles_selim.starota.misc.utils.EmbedBuilder;
import us.myles_selim.starota.misc.utils.IndexHolder;
import us.myles_selim.starota.misc.utils.MiscUtils;
import us.myles_selim.starota.reaction_messages.ReactionMessage;
import us.myles_selim.starota.search.SearchEngine;
import us.myles_selim.starota.search.SearchOperator;
import us.myles_selim.starota.wrappers.StarotaServer;

public class CommandSearchPoke extends BotCommand<StarotaServer> {

	public CommandSearchPoke() {
		super("searchPoke", "Searches Pokedex information.");
	}

	@Override
	public PermissionSet getCommandPermission() {
		return PermissionSet.of(Permission.SEND_MESSAGES, Permission.EMBED_LINKS,
				Permission.USE_EXTERNAL_EMOJIS, Permission.ADD_REACTIONS, Permission.MANAGE_MESSAGES);
	}

	@Override
	public String getDescription() {
		String desc = super.getDescription();
		desc += "_\n\nSearch Operators:\n```\n";
		for (String[] ops : SearchOperator.getOperatorTerms(EnumPokemon.class)) {
			if (ops.length == 0)
				continue;
			if (ops.length == 1) {
				desc += " - " + ops[0] + "\n";
				continue;
			}
			String opString = " - ";
			for (String op : ops)
				opString += op + ", ";
			desc += opString.substring(0, opString.length() - 2) + "\n";
		}
		desc += "\n```_";
		return desc;
	}

	@Override
	public String getGeneralUsage() {
		return "<searchTerms>";
	}

	@Override
	public void execute(String[] args, Message message, StarotaServer server, MessageChannel channel)
			throws CommandException {
		String search = "";
		for (int i = 1; i < args.length; i++)
			search += " " + args[i];
		Collection<EnumPokemon> results = SearchEngine.search(EnumPokemon.class, search);
		if (results.isEmpty())
			channel.createMessage("No matches").block();
		else
			new SearchResultReactionMessage(results).createMessage((TextChannel) channel);
	}

	private class SearchResultReactionMessage extends ReactionMessage {

		private static final int RESULTS_PER_PAGE = 10;

		private IndexHolder pageIndex = new IndexHolder();

		private final Collection<EnumPokemon> results;

		public SearchResultReactionMessage(Collection<EnumPokemon> results) {
			this.results = results;
		}

		@Override
		public void onSend(StarotaServer server, MessageChannel channel, Message msg) {
			if (results.size() / RESULTS_PER_PAGE == 0)
				return;
			this.addPageButtons(pageIndex, results.size() / RESULTS_PER_PAGE);
		}

		@Override
		protected Consumer<? super EmbedCreateSpec> getEmbed(StarotaServer server) {
			EmbedBuilder builder = new EmbedBuilder();
			builder.withTitle(String.format("Search results: (%d)", results.size()));
			int maxVal = Math.min(pageIndex.value * RESULTS_PER_PAGE + RESULTS_PER_PAGE, results.size());
			int i = 0;
			for (EnumPokemon p : results) {
				if (i >= maxVal)
					break;
				if (i >= pageIndex.value * RESULTS_PER_PAGE) {
					String typeString = MiscUtils.getEmojiDisplay(p.getData().getType1().getEmoji());
					if (p.getData().getType2() != null)
						typeString += MiscUtils.getEmojiDisplay(p.getData().getType2().getEmoji());
					builder.appendDesc(String.format(" - %s %s\n", p.getData().getName(), typeString));
				}
				i++;
			}
			builder.withFooterText(String.format("Page: %d/%d", pageIndex.value + 1,
					results.size() / RESULTS_PER_PAGE + 1));
			return builder.build();
		}

	}

}
