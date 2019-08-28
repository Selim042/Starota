package us.myles_selim.starota.commands.tutorial;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import discord4j.core.spec.EmbedCreateSpec;
import us.myles_selim.starota.misc.utils.EmbedBuilder;

public class TutorialRegistry {

	public static List<TutorialChapter> getTutorials() {
		return getTutorials(false);
	}

	public static List<TutorialChapter> getTutorials(boolean includeAdmin) {
		List<TutorialChapter> ret = new ArrayList<>();
//		ret.addAll(NORMAL_TUTORIALS);
//		if (includeAdmin)
//			ret.addAll(ADMIN_TUTORIALS);
		return ret;
	}

	public static Consumer<? super EmbedCreateSpec> getTableEmbed() {
		return getTableEmbed(false);
	}

	public static Consumer<? super EmbedCreateSpec> getTableEmbed(boolean includeAdmin) {
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
