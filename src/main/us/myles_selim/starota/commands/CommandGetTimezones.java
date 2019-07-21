package us.myles_selim.starota.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.function.Consumer;

import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.MessageChannel;
import discord4j.core.object.entity.TextChannel;
import discord4j.core.object.util.Permission;
import discord4j.core.spec.EmbedCreateSpec;
import us.myles_selim.starota.commands.registry.CommandException;
import us.myles_selim.starota.misc.utils.EmbedBuilder;
import us.myles_selim.starota.misc.utils.IndexHolder;
import us.myles_selim.starota.reaction_messages.ReactionMessage;
import us.myles_selim.starota.wrappers.StarotaServer;

public class CommandGetTimezones extends BotCommand<StarotaServer> {

	private static final List<String> REGIONS = new ArrayList<>();
	private static final Map<String, List<String>> TIMEZONES = new HashMap<>();

	public CommandGetTimezones() {
		super("getTimezones", "Displays all available timezones.");
	}

	@Override
	public Permission requiredUsePermission() {
		return Permission.ADMINISTRATOR;
	}

	@Override
	public void execute(String[] args, Message message, StarotaServer server, MessageChannel channel)
			throws CommandException {
		if (TIMEZONES.isEmpty())
			setupTimezones();
		new TimezoneReactionMessage().createMessage((TextChannel) channel);
	}

	private class TimezoneReactionMessage extends ReactionMessage {

		private IndexHolder pageIndex = new IndexHolder();

		@Override
		public void onSend(StarotaServer server, TextChannel channel, Message msg) {
			this.addPageButtons(pageIndex, REGIONS.size());
		}

		@Override
		protected Consumer<? super EmbedCreateSpec> getEmbed(StarotaServer server) {
			EmbedBuilder builder = new EmbedBuilder();
			String region = REGIONS.get(pageIndex.value);
			builder.withTitle(region + ":").appendDesc(
					"To use these with the timezone setting, prefix the area with the region\n");
			List<String> areas = TIMEZONES.get(region);
			if (areas != null)
				for (String area : areas)
					builder.appendDesc(String.format(" - %s\n", area));
			builder.withFooterText(String.format("Page: %d/%d", pageIndex.value + 1, REGIONS.size()));
			return builder.build();
		}

	}

	private static void setupTimezones() {
		for (String id : TimeZone.getAvailableIDs()) {
			if (id.matches(".*?/.*?")) {
				String[] parts = id.split("/");
				List<String> areas = TIMEZONES.get(parts[0]);
				if (areas == null) {
					areas = new ArrayList<>();
					TIMEZONES.put(parts[0], areas);
				}
				areas.add(parts[1]);
			}
			List<String> areas = TIMEZONES.get("Misc");
			if (areas == null) {
				areas = new ArrayList<>();
				TIMEZONES.put("Misc", areas);
			}
			areas.add(id);
		}
		REGIONS.addAll(TIMEZONES.keySet());
		REGIONS.sort((String s1, String s2) -> {
			if (s1.equalsIgnoreCase("Misc") || s1.equalsIgnoreCase("Etc"))
				return 1;
			if (s2.equalsIgnoreCase("Misc") || s2.equalsIgnoreCase("Etc"))
				return -1;
			return s1.compareToIgnoreCase(s2);
		});
	}

}
