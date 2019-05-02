package us.myles_selim.starota.search.events;

import java.util.Collection;
import java.util.EnumSet;
import java.util.List;

import sx.blah.discord.api.internal.json.objects.EmbedObject;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.Permissions;
import sx.blah.discord.util.EmbedBuilder;
import us.myles_selim.starota.commands.StarotaCommand;
import us.myles_selim.starota.leek_duck.events.EventData;
import us.myles_selim.starota.leek_duck.events.StarotaEvent;
import us.myles_selim.starota.misc.utils.IndexHolder;
import us.myles_selim.starota.reaction_messages.ReactionMessage;
import us.myles_selim.starota.search.SearchEngine;
import us.myles_selim.starota.search.SearchOperator;
import us.myles_selim.starota.wrappers.StarotaServer;

public class CommandSearchEvents extends StarotaCommand {

	public CommandSearchEvents() {
		super("searchEvents", "Searches event information.");
	}

	@Override
	public EnumSet<Permissions> getCommandPermissions() {
		return EnumSet.of(Permissions.SEND_MESSAGES, Permissions.EMBED_LINKS,
				Permissions.USE_EXTERNAL_EMOJIS, Permissions.ADD_REACTIONS, Permissions.MANAGE_MESSAGES);
	}

	@Override
	public String getDescription() {
		String desc = super.getDescription();
		desc += "_\n\nSearch Operators:\n```\n";
		for (String[] ops : SearchOperator.getOperatorTerms(StarotaEvent.class)) {
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
	public List<String> getAliases() {
		List<String> aliases = super.getAliases();
		aliases.add("searchEvent");
		return aliases;
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
		Collection<StarotaEvent> results = SearchEngine.search(EventData.getRunningUpcomingEvents(),
				search);
		if (results.isEmpty())
			channel.sendMessage("No matches");
		else
			new SearchResultReactionMessage(results).sendMessage(channel);
	}

	private class SearchResultReactionMessage extends ReactionMessage {

		private IndexHolder pageIndex = new IndexHolder();

		private final Collection<StarotaEvent> results;

		public SearchResultReactionMessage(Collection<StarotaEvent> results) {
			this.results = results;
		}

		@Override
		public void onSend(StarotaServer server, IChannel channel, IMessage msg) {
			this.addPageButtons(pageIndex, results.size());
		}

		@Override
		protected EmbedObject getEmbed(StarotaServer server) {
			EmbedBuilder builder = new EmbedBuilder();
			builder.withAuthorName(String.format("Search results: (%d)", results.size()));
			return results.toArray(new StarotaEvent[0])[pageIndex.value].toEmbed(pageIndex.value + 1,
					results.size(), server);
		}

	}

}
