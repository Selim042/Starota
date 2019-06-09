package us.myles_selim.starota.assistants.points;

import javax.annotation.Nonnull;

import sx.blah.discord.api.internal.json.objects.EmbedObject;
import sx.blah.discord.util.EmbedBuilder;

public class PointQuest<V extends QuestType<P>, P> {

	private QuestType<P> type;
	private P param;
	private long expireTime;

	public PointQuest(@Nonnull QuestType<P> type, P param, long expireTime) {
		this.type = type;
		this.param = param;
		this.expireTime = expireTime;
	}

	@Nonnull
	public QuestType<P> getType() {
		return this.type;
	}

	public P getParam() {
		return this.param;
	}

	public long getExpireTime() {
		return this.expireTime;
	}

	public boolean hasExpired() {
		return System.currentTimeMillis() >= this.expireTime;
	}

	public EmbedObject toEmbed() {
		EmbedBuilder builder = new EmbedBuilder();
		builder.withTitle(toString());
		builder.withThumbnail(getType().getImage(getParam()));
		builder.withFooterText("Expires at").withTimestamp(expireTime);
		if (this.param != null)
			builder.appendDesc(String.format(this.getType().getDescription(), this.param));
		else
			builder.appendDesc(this.getType().getDescription());
		int points = this.getType().getPoints(this.getParam());
		if (points == 1)
			builder.appendDesc(
					String.format("\n\nCompleting this quest will reward you with %d point.", points));
		else
			builder.appendDesc(
					String.format("\n\nCompleting this quest will reward you with %d points.", points));
		return builder.build();
	}

	@Override
	public String toString() {
		if (this.param != null)
			return String.format(this.getType().getDisplayName(), this.param);
		return this.getType().getDisplayName();
	}

	public static class PointQuestNull<V extends QuestType<Void>> extends PointQuest<V, Void> {

		public PointQuestNull(QuestType<Void> type, long expireTime) {
			super(type, null, expireTime);
		}

	}

}
