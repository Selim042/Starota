package us.myles_selim.starota.search;

import java.util.Collection;
import java.util.EnumSet;

import sx.blah.discord.api.internal.json.objects.EmbedObject;
import sx.blah.discord.handle.impl.obj.ReactionEmoji;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IReaction;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.handle.obj.Permissions;
import sx.blah.discord.util.EmbedBuilder;
import sx.blah.discord.util.RequestBuffer;
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.commands.StarotaCommand;
import us.myles_selim.starota.enums.EnumPokemon;
import us.myles_selim.starota.reaction_messages.ReactionMessage;
import us.myles_selim.starota.wrappers.StarotaServer;

public class CommandSearchPoke extends StarotaCommand {

	public CommandSearchPoke() {
		super("searchPoke", "Searches Pokedex information.");
	}

	@Override
	public EnumSet<Permissions> getCommandPermissions() {
		return EnumSet.of(Permissions.SEND_MESSAGES, Permissions.EMBED_LINKS,
				Permissions.USE_EXTERNAL_EMOJIS, Permissions.ADD_REACTIONS, Permissions.MANAGE_MESSAGES);
	}

	@Override
	public String getDescription() {
		String desc = super.getDescription();
		desc += "\n\nSearch Operators:\n```\n";
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
		desc += "\n```";
		return desc;
	}

	@Override
	public String getGeneralUsage() {
		return "<searchTerms>";
	}

	@Override
	public void execute(String[] args, IMessage message, StarotaServer server, IChannel channel)
			throws Exception {
		String search = "";
		for (int i = 1; i < args.length; i++)
			search += " " + args[i];
		Collection<EnumPokemon> results = SearchEngine.search(EnumPokemon.class, search);
		if (results.isEmpty())
			channel.sendMessage("No matches");
		else
			new SearchResultReactionMessage(results).sendMessage(channel);
	}

	private class SearchResultReactionMessage extends ReactionMessage {

		private static final String LEFT_EMOJI = "⬅";
		private static final String RIGHT_EMOJI = "➡";
		private static final int RESULTS_PER_PAGE = 10;

		private int pageIndex = 0;

		private final Collection<EnumPokemon> results;

		public SearchResultReactionMessage(Collection<EnumPokemon> results) {
			this.results = results;
		}

		@Override
		public void onSend(StarotaServer server, IChannel channel, IMessage msg) {
			if (results.size() / RESULTS_PER_PAGE == 0)
				return;
			RequestBuffer.request(() -> msg.addReaction(ReactionEmoji.of(LEFT_EMOJI))).get();
			RequestBuffer.request(() -> msg.addReaction(ReactionEmoji.of(RIGHT_EMOJI)));
		}

		@Override
		public void onReactionAdded(StarotaServer server, IChannel channel, IMessage msg, IUser user,
				IReaction react) {
			if (!react.getUserReacted(Starota.getOurUser()))
				return;
			int totalPages = results.size() / RESULTS_PER_PAGE;
			boolean changed = false;
			if (react.getEmoji().getName().equals(LEFT_EMOJI)) {
				if (pageIndex <= 0)
					pageIndex = totalPages;
				else
					pageIndex--;
				changed = true;
			} else if (react.getEmoji().getName().equals(RIGHT_EMOJI)) {
				if (pageIndex >= totalPages)
					pageIndex = 0;
				else
					pageIndex++;
				changed = true;
			}
			if (changed)
				RequestBuffer.request(() -> this.editMessage(channel, msg));
			RequestBuffer.request(() -> msg.removeReaction(user, react));
		}

		@Override
		protected EmbedObject getEmbed(StarotaServer server) {
			EmbedBuilder builder = new EmbedBuilder();
			builder.withTitle(String.format("Search results: (%d)", results.size()));
			int maxVal = Math.min(pageIndex * RESULTS_PER_PAGE + RESULTS_PER_PAGE, results.size());
			int i = 0;
			for (EnumPokemon p : results) {
				if (i >= maxVal)
					break;
				if (i >= pageIndex * RESULTS_PER_PAGE) {
					String typeString = p.getType1().getEmoji().toString();
					if (p.getType2() != null)
						typeString += p.getType2().getEmoji();
					builder.appendDesc(String.format(" - %s %s\n", p.getName(), typeString));
				}
				i++;
			}
			builder.withFooterText(
					String.format("Page: %d/%d", pageIndex + 1, results.size() / RESULTS_PER_PAGE + 1));
			return builder.build();
		}

	}

}
