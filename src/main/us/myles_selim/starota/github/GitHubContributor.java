package us.myles_selim.starota.github;

public class GitHubContributor {

	private String login;
	private long id;
	private String node_id;
	private String avatar_url;
	private String gravatar_url;
	private String url;
	private String html_url;

	private String followers_url;
	private String following_url;

	private String gists_url;
	private String starred_url;
	private String subscriptions_url;
	private String organizations_url;
	private String repos_url;
	private String events_url;
	private String recieved_events_url;

	private String type;
	private boolean site_admin;
	private int contributions;

	private GitHubContributor() {}

	public String getLogin() {
		return login;
	}

	public long getId() {
		return id;
	}

	public String getNode_id() {
		return node_id;
	}

	public String getAvatar_url() {
		return avatar_url;
	}

	public String getGravatar_url() {
		return gravatar_url;
	}

	public String getUrl() {
		return url;
	}

	public String getHtmlUrl() {
		return html_url;
	}

	public String getFollowersUrl() {
		return followers_url;
	}

	public String getFollowingUrl() {
		return following_url;
	}

	public String getGistsUrl() {
		return gists_url;
	}

	public String getStarredUrl() {
		return starred_url;
	}

	public String getSubscriptionsUrl() {
		return subscriptions_url;
	}

	public String getOrganizationsUrl() {
		return organizations_url;
	}

	public String getReposUrl() {
		return repos_url;
	}

	public String getEventsUrl() {
		return events_url;
	}

	public String getRecievedEventsUrl() {
		return recieved_events_url;
	}

	public String getType() {
		return type;
	}

	public boolean isSiteAdmin() {
		return site_admin;
	}

	public int getContributions() {
		return contributions;
	}

}
