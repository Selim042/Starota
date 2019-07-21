package us.myles_selim.starota.misc.data_types;

import discord4j.core.DiscordClient;
import discord4j.core.object.entity.Channel;
import discord4j.core.object.util.Snowflake;
import reactor.core.publisher.Mono;

public class NullChannel {

	public static final Channel NULL_TEXT_CHANNEL = new Channel() {

		@Override
		public Snowflake getId() {
			return null;
		}

		@Override
		public DiscordClient getClient() {
			return null;
		}

		@Override
		public Type getType() {
			return null;
		}

		@Override
		public Mono<Void> delete(String reason) {
			return null;
		}

	};

}
