package us.myles_selim.starota.commands.tutorial;

public class ChapterProgress {

	private final TutorialChapter chapter;
	private int progress;

	public ChapterProgress(TutorialChapter chapter, int progress) {
		this.chapter = chapter;
		this.progress = progress;
	}

	public TutorialChapter getChapter() {
		return this.chapter;
	}

	public int getProgress() {
		return this.progress;
	}

}
