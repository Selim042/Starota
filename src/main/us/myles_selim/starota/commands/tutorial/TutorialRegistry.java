package us.myles_selim.starota.commands.tutorial;

import java.util.ArrayList;
import java.util.List;

import sx.blah.discord.api.internal.json.objects.EmbedObject;
import sx.blah.discord.util.EmbedBuilder;

public class TutorialRegistry {

	private static final List<TutorialChapter> NORMAL_TUTORIALS = new ArrayList<>();
	private static final List<TutorialChapter> ADMIN_TUTORIALS = new ArrayList<>();

	public static List<TutorialChapter> getTutorials() {
		return getTutorials(false);
	}

	public static List<TutorialChapter> getTutorials(boolean includeAdmin) {
		List<TutorialChapter> ret = new ArrayList<>();
		ret.addAll(NORMAL_TUTORIALS);
		if (includeAdmin)
			ret.addAll(ADMIN_TUTORIALS);
		return ret;
	}

	public static EmbedObject getTableEmbed() {
		return getTableEmbed(false);
	}

	public static EmbedObject getTableEmbed(boolean includeAdmin) {
		EmbedBuilder builder = new EmbedBuilder();
		builder.withTitle("Tutorial Table of Contents:");
		List<TutorialChapter> chs = getTutorials(includeAdmin);
		if (chs.isEmpty())
			builder.appendDesc("No tutorial chapters available.");
		else
			for (int i = 0; i < chs.size(); i++) {
				TutorialChapter ch = chs.get(i);
				builder.appendDesc(
						String.format("%n) %s, _%s_", i + 1, ch.getName(), ch.getDescription()));
			}
		return builder.build();
	}

}
