package us.myles_selim.starota.commands.tutorial;

import discord4j.core.object.entity.User;

public class TutorialChapter {

	private final String name;
	private final String description;

	public TutorialChapter(String name, String description) {
		this.name = name;
		this.description = description;
	}

	public final String getName() {
		return name;
	}

	public final String getDescription() {
		return description;
	}

	public void execute(User user, ChapterProgress progress) throws Exception {}

}
