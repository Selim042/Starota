package us.myles_selim.starota.research;

import sx.blah.discord.handle.obj.IUser;
import us.myles_selim.starota.Starota;

public class Researcher implements Comparable<Researcher> {

	private final long user;
	private int posts;

	public Researcher(long user) {
		this(user, 0);
	}

	public Researcher(long user, int posts) {
		this.user = user;
		this.posts = posts;
	}

	public IUser getDiscordUser() {
		return Starota.getUser(this.user);
	}

	public long getLongId() {
		return this.user;
	}

	public int incrementPosts() {
		this.posts++;
		return this.posts;
	}

	public int getPosts() {
		return this.posts;
	}

	@Override
	public int compareTo(Researcher o) {
		return -Integer.compare(this.posts, o.posts);
	}

}
