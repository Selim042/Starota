package us.myles_selim.starota.wrappers;

import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IUser;
import us.myles_selim.starota.profiles.PlayerProfile;

public class StarotaUser {

	private final StarotaServer server;
	private final IUser user;

	// private SubscriptionOptions subOptions;

	protected StarotaUser(StarotaServer server, IUser user) {
		this.server = server;
		this.user = user;
	}

	public StarotaServer getServer() {
		return this.server;
	}

	public IGuild getDiscordGuild() {
		return this.server.getDiscordGuild();
	}

	public IUser getDiscordUser() {
		return this.user;
	}

	public PlayerProfile getProfile() {
		return this.server.getProfile(user);
	}

}
