package us.myles_selim.starota.wrappers;

import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.User;
import us.myles_selim.starota.profiles.PlayerProfile;

public class StarotaUser {

	private final StarotaServer server;
	private final User user;

	// private SubscriptionOptions subOptions;

	protected StarotaUser(StarotaServer server, User user) {
		this.server = server;
		this.user = user;
	}

	public StarotaServer getServer() {
		return this.server;
	}

	public Guild getDiscordGuild() {
		return this.server.getDiscordGuild();
	}

	public User getDiscordUser() {
		return this.user;
	}

	public PlayerProfile getProfile() {
		return this.server.getProfile(user);
	}

}
