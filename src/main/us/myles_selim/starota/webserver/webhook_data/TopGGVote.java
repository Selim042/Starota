package us.myles_selim.starota.webserver.webhook_data;

import discord4j.core.object.entity.User;
import discord4j.core.object.util.Snowflake;
import us.myles_selim.starota.Starota;

public class TopGGVote {

	private long bot;
	private long user;
	private String type;
	private boolean isWeekend;
	private String query;

	public long getBot() {
		return bot;
	}

	public long getUser() {
		return user;
	}

	public String getType() {
		return type;
	}

	public boolean isWeekend() {
		return isWeekend;
	}

	public String getQuery() {
		return query;
	}

	public User getUserObject() {
		if (Starota.FULLY_STARTED)
			return Starota.getClient().getUserById(Snowflake.of(user)).onErrorReturn(null).block();
		return null;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TopGGVote [bot=");
		builder.append(bot);
		builder.append(", user=");
		builder.append(user);
		builder.append(", username=");
		User user = getUserObject();
		builder.append(user == null ? null
				: String.format("%s#%s", user.getUsername(), user.getDiscriminator()));
		builder.append(", type=");
		builder.append(type);
		builder.append(", isWeekend=");
		builder.append(isWeekend);
		builder.append(", query=");
		builder.append(query);
		builder.append("]");
		return builder.toString();
	}

}
